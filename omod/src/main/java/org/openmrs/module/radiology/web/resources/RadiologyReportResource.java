package org.openmrs.module.radiology.web.resources;

import org.openmrs.api.OrderService;
import org.openmrs.api.context.Context;
import org.openmrs.module.radiology.api.model.RadiologyOrder;
import org.openmrs.module.radiology.api.model.RadiologyReport;
import org.openmrs.module.radiology.api.enums.RadiologyReportStatus;
import org.openmrs.module.radiology.api.service.RadiologyReportService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Resource(name = RestConstants.VERSION_1 + "/radiology", supportedClass = RadiologyReport.class, supportedOpenmrsVersions = {
        "2.6.* - 9.*" })
public class RadiologyReportResource extends DataDelegatingCrudResource<RadiologyReport> {

	private RadiologyReportService radiologyReportService;

	public RadiologyReportResource() {
		this.radiologyReportService = Context.getService(RadiologyReportService.class);
	}

	@Override
	public RadiologyReport getByUniqueId(String uuid) {
		Optional<RadiologyReport> report = radiologyReportService.getRadiologyReportByUuid(uuid);
		return report.orElse(null);
	}

	@Override
	protected void delete(RadiologyReport report, String reason, RequestContext context) throws ResponseException {
		// Void the report instead of deleting
		report.setVoided(true);
		report.setVoidReason(reason);
		radiologyReportService.saveOrUpdate(report);
	}

	@Override
	public RadiologyReport newDelegate() {
		return new RadiologyReport();
	}

	@Override
	public RadiologyReport save(RadiologyReport report) {
		return radiologyReportService.saveOrUpdate(report);
	}

	@Override
	public void purge(RadiologyReport report, RequestContext context) throws ResponseException {
		// Purge is typically not allowed for clinical data
		throw new ResourceDoesNotSupportOperationException("Purge is not supported for RadiologyReport");
	}

	@Override
	protected PageableResult doSearch(RequestContext requestContext) {
		String orderUuid = requestContext.getParameter("orderUuid");
		if (orderUuid != null) {
			List<RadiologyReport> reports = radiologyReportService.getReportsByOrderUuid(orderUuid);
			return new NeedsPaging<>(reports, requestContext);
		}
		// Return empty results for now - listing all reports without filters is not supported
		return new NeedsPaging<>(new ArrayList<>(), requestContext);
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("patient");
		description.addProperty("radiologyOrder");
		description.addProperty("concept");
		description.addProperty("encounter");
		description.addProperty("performer");
		description.addProperty("location");
		description.addProperty("modality");
		description.addProperty("report");
		description.addProperty("radiologyFindings"); // Frontend field name
		description.addProperty("radiologyImpressions"); // Frontend field name
		description.addProperty("radiologyRecommendations"); // Frontend field name
		description.addProperty("status");
		description.addProperty("reportDatetime");
		description.addProperty("encounters");
		return description;
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (representation instanceof RefRepresentation) {
			description.addProperty("uuid");
			description.addProperty("patient", Representation.REF);
			description.addProperty("radiologyOrder", Representation.REF);
			description.addProperty("concept", Representation.REF);
			description.addProperty("status");
			description.addProperty("reportDatetime");
		} else if (representation instanceof DefaultRepresentation) {
			description.addProperty("uuid");
			description.addProperty("patient", Representation.DEFAULT);
			description.addProperty("radiologyOrder", Representation.DEFAULT);
			description.addProperty("concept", Representation.DEFAULT);
			description.addProperty("encounter", Representation.DEFAULT);
			description.addProperty("performer", Representation.DEFAULT);
			description.addProperty("location", Representation.DEFAULT);
			description.addProperty("modality", Representation.DEFAULT);
			description.addProperty("report");
			description.addProperty("findings");
			description.addProperty("impressions");
			description.addProperty("recommendations");
			description.addProperty("status");
			description.addProperty("reportDatetime");
		} else if (representation instanceof FullRepresentation) {
			description.addProperty("uuid");
			description.addProperty("patient", Representation.FULL);
			description.addProperty("radiologyOrder", Representation.FULL);
			description.addProperty("concept", Representation.FULL);
			description.addProperty("encounter", Representation.FULL);
			description.addProperty("performer", Representation.FULL);
			description.addProperty("location", Representation.FULL);
			description.addProperty("modality", Representation.FULL);
			description.addProperty("report");
			description.addProperty("findings");
			description.addProperty("impressions");
			description.addProperty("recommendations");
			description.addProperty("status");
			description.addProperty("reportDatetime");
			description.addProperty("formNamespaceAndPath");
		} else if (representation instanceof CustomRepresentation) {
			description = null;
		}
		return description;
	}

	@Override
	public void setProperty(Object instance, String propertyName, Object value) throws ResponseException {
		RadiologyReport report = (RadiologyReport) instance;
		
		// Map frontend field names to model field names
		if ("radiologyFindings".equals(propertyName)) {
			report.setFindings((String) value);
			return;
		} else if ("radiologyImpressions".equals(propertyName)) {
			report.setImpressions((String) value);
			return;
		} else if ("radiologyRecommendations".equals(propertyName)) {
			report.setRecommendations((String) value);
			return;
		} else if ("radiologyOrder".equals(propertyName)) {
			// Handle UUID string for radiologyOrder
			if (value instanceof String) {
				OrderService orderService = Context.getOrderService();
				RadiologyOrder order = (RadiologyOrder) orderService.getOrderByUuid((String) value);
				if (order != null) {
					report.setRadiologyOrder(order);
					return;
				}
			}
		} else if ("status".equals(propertyName) && value instanceof String) {
			// Map frontend status string to enum
			String statusStr = (String) value;
			try {
				// Map "COMPLETED" to FINAL
				if ("COMPLETED".equalsIgnoreCase(statusStr)) {
					report.setStatus(RadiologyReportStatus.FINAL);
				} else {
					report.setStatus(RadiologyReportStatus.valueOf(statusStr.toUpperCase()));
				}
				return;
			} catch (IllegalArgumentException e) {
				// If enum value doesn't match, try to set it directly or use default
				report.setStatus(RadiologyReportStatus.PRELIMINARY);
				return;
			}
		} else if ("reportDatetime".equals(propertyName) && report.getReportDatetime() == null) {
			// Set report datetime if not already set
			if (value == null) {
				report.setReportDatetime(new Date());
			}
		}
		
		// For all other properties, use the default behavior
		super.setProperty(instance, propertyName, value);
	}
}
