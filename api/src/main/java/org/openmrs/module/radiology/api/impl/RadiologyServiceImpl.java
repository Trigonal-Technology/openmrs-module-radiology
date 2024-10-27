package org.openmrs.module.radiology.api.impl;

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
import org.openmrs.module.radiology.api.RadiologyService;
import org.openmrs.module.radiology.api.dao.RadiologyDao;
import org.openmrs.module.radiology.api.model.Radiology;

@Transactional
public class RadiologyServiceImpl implements RadiologyService {
	
	private RadiologyDao radiologyDao;
	
	public void setRadiologyDao(RadiologyDao radiologyDao) {
		this.radiologyDao = radiologyDao;
	}
	
	@Override
	public Optional<Radiology> getRadiologyOrderByUuid(String uuid) {
		return radiologyDao.getRadiologyOrderByUuid(uuid);
	}
	
	@Override
	public Radiology saveOrUpdate(Radiology radiology) {
		List<Encounter> encounters = handleEncounter(radiology);
		radiology.setEncounters(encounters);
		return radiologyDao.saveOrUpdate(radiology);
	}
	
	/**
	 * Extract the obs and encounters from the payload and persist
	 * 
	 * @param radiology
	 * @return
	 */
	private List<Encounter> handleEncounter(Radiology radiology) {
		if (procedure.getEncounters().isEmpty()) {
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
}
