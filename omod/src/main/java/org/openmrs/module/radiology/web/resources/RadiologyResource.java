package org.openmrs.module.radiology.web.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.openmrs.Encounter;
import org.openmrs.api.context.Context;
import org.openmrs.module.radiology.api.RadiologyService;
import org.openmrs.module.radiology.api.model.Radiology;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.CustomRepresentation;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Resource(name = RestConstants.VERSION_1 + "/radiology", supportedClass = Radiology.class, supportedOpenmrsVersions = {
        "2.6.* - 9.*" })
public class RadiologyResource extends DataDelegatingCrudResource<Radiology> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RadiologyResource.class);
	
	private RadiologyService radiologyService;
	
	public RadiologyResource() {
		this.radiologyService = Context.getService(RadiologyService.class);
	}
	
	@Override
	public Radiology getByUniqueId(String uuid) {
		LOGGER.info("Inside getByUniqueId");
		Optional<Radiology> radiology = radiologyService.getRadiologyOrderByUuid(uuid);
        return radiology.orElse(null);
	}
	
	@Override
	protected void delete(Radiology radiology, String s, RequestContext requestContext) throws ResponseException {
		
	}
	
	@Override
	public Radiology newDelegate() {
		return new Radiology();
	}
	
	@Override
	public Radiology save(Radiology radiology) {
		LOGGER.info("Inside save");
		return radiologyService.saveOrUpdate(radiology);
	}
	
	@Override
	public void purge(Radiology radiology, RequestContext requestContext) throws ResponseException {
		
	}
	
	@Override
	protected PageableResult doSearch(RequestContext requestContext) {
		String orderUuid = requestContext.getParameter("orderUuid");
		String orderTypeUuid = requestContext.getParameter("orderTypeUuid");
		
		return new NeedsPaging<>(new ArrayList<>(), requestContext);
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
		LOGGER.info("Inside getCreatableProperties");
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("patient");
		description.addProperty("encounter");
		description.addProperty("radiologyOrder");
		description.addProperty("concept");
		description.addProperty("radiologyReason");
		description.addProperty("category");
		description.addProperty("bodySite");
		description.addProperty("partOf");
		description.addProperty("startDatetime");
		description.addProperty("endDatetime");
		description.addProperty("status");
		description.addProperty("statusReason");
		description.addProperty("outcome");
		description.addProperty("location");
		description.addProperty("encounters");
		description.addProperty("participants");
		description.addProperty("radiologyResults");
		description.addProperty("radiologyReport");
		return description;
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
		LOGGER.info("Inside getRepresentationDescription");
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (representation instanceof RefRepresentation) {
			description.addProperty("uuid");
			description.addProperty("patient", Representation.REF);
			description.addProperty("radiologyOrder", Representation.REF);
			description.addProperty("concept", Representation.REF);
			description.addProperty("radiologyReason", Representation.REF);
			description.addProperty("category", Representation.REF);
			description.addProperty("bodySite", Representation.REF);
			description.addProperty("partOf", Representation.REF);
			description.addProperty("startDatetime");
			description.addProperty("endDatetime");
			description.addProperty("status");
			description.addProperty("statusReason", Representation.REF);
			description.addProperty("outcome");
			description.addProperty("radiologyReport");
			description.addProperty("location", Representation.REF);
			description.addProperty("encounters", Representation.REF);
		} else if (representation instanceof DefaultRepresentation) {
			description.addProperty("uuid");
			description.addProperty("patient", Representation.DEFAULT);
			description.addProperty("radiologyOrder", Representation.DEFAULT);
			description.addProperty("concept", Representation.DEFAULT);
			description.addProperty("radiologyReason", Representation.DEFAULT);
			description.addProperty("category", Representation.DEFAULT);
			description.addProperty("bodySite", Representation.DEFAULT);
			description.addProperty("partOf", Representation.DEFAULT);
			description.addProperty("startDatetime");
			description.addProperty("endDatetime");
			description.addProperty("status");
			description.addProperty("statusReason", Representation.DEFAULT);
			description.addProperty("outcome");
			description.addProperty("radiologyReport");
			description.addProperty("location", Representation.DEFAULT);
			description.addProperty("encounters", Representation.DEFAULT);
		} else if (representation instanceof FullRepresentation) {
			description.addProperty("uuid");
			description.addProperty("patient", Representation.REF);
			description.addProperty("radiologyOrder", Representation.FULL);
			description.addProperty("concept", Representation.FULL);
			description.addProperty("radiologyReason", Representation.REF);
			description.addProperty("category", Representation.FULL);
			description.addProperty("bodySite", Representation.FULL);
			description.addProperty("partOf", Representation.REF);
			description.addProperty("startDatetime");
			description.addProperty("endDatetime");
			description.addProperty("status");
			description.addProperty("statusReason", Representation.FULL);
			description.addProperty("outcome");
			description.addProperty("radiologyReport");
			description.addProperty("location", Representation.REF);
			description.addProperty("encounters", Representation.REF);
		} else if (representation instanceof CustomRepresentation) { // custom rep
			description = null;
		}
		return description;
	}
	
	@PropertyGetter(value = "encounters")
	public List<Encounter> getEncounters(Radiology instance) {
		LOGGER.info("Inside getEncounters");
		try {
			List<Encounter> encounters = instance.getEncounters();
			return encounters;
		}
		catch (Exception e) {
			return new ArrayList<>();
		}
	}
}
