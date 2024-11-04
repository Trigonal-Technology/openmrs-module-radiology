package org.openmrs.module.radiology.api.service.impl;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.openmrs.Encounter;
import org.openmrs.EncounterProvider;
import org.openmrs.Obs;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.radiology.api.dao.RadiologyOrderQueueDao;
import org.openmrs.module.radiology.api.enums.RadiologyOrderStatus;
import org.openmrs.module.radiology.api.service.RadiologyService;
import org.openmrs.module.radiology.api.dao.RadiologyDao;
import org.openmrs.module.radiology.api.model.Radiology;
import org.openmrs.module.radiology.api.model.RadiologyOrder;
import org.openmrs.module.radiology.api.model.RadiologyOrderQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Transactional
public class RadiologyServiceImpl implements RadiologyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RadiologyServiceImpl.class);

	private RadiologyDao radiologyDao;

	public void setRadiologyDao(RadiologyDao radiologyDao) {
		this.radiologyDao = radiologyDao;
	}

	
	@Override
	public Optional<Radiology> getRadiologyOrderByUuid(String uuid) {
		LOGGER.info("Inside getRadiologyOrderByUuid");
		return radiologyDao.getRadiologyOrderByUuid(uuid);
	}
	
	@Override
	public Radiology saveOrUpdate(Radiology radiology) { 
		LOGGER.info("Inside saveOrUpdate");
		List<Encounter> encounters = handleEncounter(radiology);
		radiology.setEncounters(encounters);
		radiologyDao.saveOrUpdate(radiology);

		//Add it to queue


		return radiology;
	}
	
	/**
	 * Extract the obs and encounters from the payload and persist
	 * 
	 * @param radiology
	 * @return
	 */
	private List<Encounter> handleEncounter(Radiology radiology) {
		LOGGER.info("Inside handleEncounter");
		if (radiology.getEncounters().isEmpty()) {
			return new ArrayList<>();
		}
		EncounterService service = Context.getEncounterService();
		List<Encounter> ret = new ArrayList<>();
		
		if (radiology.getEncounters() != null) {
			for (Encounter encounter : radiology.getEncounters()) {
				Encounter enc = new Encounter();
				enc.setEncounterDatetime(encounter.getEncounterDatetime());
				enc.setPatient(encounter.getPatient());
				enc.setEncounterType(encounter.getEncounterType());
				
				if (encounter.getEncounterProviders() != null) {
					Set<EncounterProvider> providers = new HashSet<>();
					for (EncounterProvider provider : encounter.getEncounterProviders()) {
						provider.setEncounter(enc);
						providers.add(provider);
					}
					enc.setEncounterProviders(providers);
				}
				
				if (encounter.getObs() != null) {
					Set<Obs> obset = new HashSet<>();
					for (Obs tobs : encounter.getObs()) {
						Obs obs = new Obs();
						obs.setPerson(encounter.getPatient());
						obs.setObsDatetime(encounter.getEncounterDatetime());
						obs.setConcept(tobs.getConcept());
						obs.setValueCoded(tobs.getValueCoded());
						obs.setEncounter(enc);
						obset.add(obs);
					}
					enc.setObs(obset);
				}
				
				service.saveEncounter(enc);
				ret.add(enc);
			}
		}
		return ret;
	}

//	private void manageRadiologyOrderQueue(Radiology radiology) {
//    // Assuming you have a method to get the RadiologyOrder from the Radiology object
//    RadiologyOrder order = radiology.getRadiologyOrder();
//
//    if (order != null) {
//        // Check if the order already exists in the queue
//        Optional<RadiologyOrderQueue> existingQueueEntry = radiologyOrderQueueDao.findByRadiologyOrderId(order.getId());
//
//        if (existingQueueEntry.isPresent()) {
//            // Update existing entry
//            RadiologyOrderQueue queueEntry = existingQueueEntry.get();
//            queueEntry.setStatus(RadiologyOrderStatus.PENDING); // Update status as needed
//            queueEntry.setUrgency(order.getUrgency());
//            radiologyOrderQueueDao.saveOrUpdate(queueEntry);
//        } else {
//            // Create new entry
//            RadiologyOrderQueue newQueueEntry = new RadiologyOrderQueue();
//            newQueueEntry.setRadiologyOrderId(order);
//            newQueueEntry.setStatus(RadiologyOrderStatus.PENDING);
//            newQueueEntry.setUrgency(order.getUrgency());
//            radiologyOrderQueueDao.saveOrUpdate(newQueueEntry);
//        }
//    }


}
