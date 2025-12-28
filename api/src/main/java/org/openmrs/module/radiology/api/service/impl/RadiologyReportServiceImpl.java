package org.openmrs.module.radiology.api.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.OrderService;
import org.openmrs.api.context.Context;
import org.openmrs.module.radiology.api.dao.RadiologyReportDao;
import org.openmrs.module.radiology.api.model.RadiologyOrder;
import org.openmrs.module.radiology.api.model.RadiologyReport;
import org.openmrs.module.radiology.api.service.RadiologyReportService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
public class RadiologyReportServiceImpl implements RadiologyReportService {

	private static final Log LOG = LogFactory.getLog(RadiologyReportServiceImpl.class);

	private RadiologyReportDao radiologyReportDao;

	public void setRadiologyReportDao(RadiologyReportDao radiologyReportDao) {
		this.radiologyReportDao = radiologyReportDao;
	}

	@Override
	public Optional<RadiologyReport> getRadiologyReportByUuid(String uuid) {
		LOG.info("Getting RadiologyReport by uuid: " + uuid);
		return radiologyReportDao.getRadiologyReportByUuid(uuid);
	}

	@Override
	public RadiologyReport saveOrUpdate(RadiologyReport report) {
		LOG.info("Saving or updating RadiologyReport");
		return radiologyReportDao.saveOrUpdate(report);
	}

	@Override
	public List<RadiologyReport> getReportsByOrderUuid(String orderUuid) {
		LOG.info("Getting RadiologyReports by order uuid: " + orderUuid);
		OrderService orderService = Context.getOrderService();
		RadiologyOrder radiologyOrder = (RadiologyOrder) orderService.getOrderByUuid(orderUuid);
		if (radiologyOrder != null) {
			return radiologyReportDao.getReportsByOrder(radiologyOrder);
		}
		return new ArrayList<>();
	}
}
