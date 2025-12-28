package org.openmrs.module.radiology.api.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.Patient;
import org.openmrs.module.radiology.api.dao.RadiologyReportDao;
import org.openmrs.module.radiology.api.model.RadiologyOrder;
import org.openmrs.module.radiology.api.model.RadiologyReport;

import java.util.List;
import java.util.Optional;

import static org.hibernate.criterion.Restrictions.eq;

public class RadiologyReportDaoImpl implements RadiologyReportDao {
	private static final Log LOG = LogFactory.getLog(RadiologyReportDaoImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Optional<RadiologyReport> getRadiologyReportByUuid(String uuid) {
		LOG.info("Fetching RadiologyReport by uuid: " + uuid);
		Criteria criteria = getCurrentSession().createCriteria(RadiologyReport.class);
		return Optional.ofNullable((RadiologyReport) criteria.add(eq("uuid", uuid)).uniqueResult());
	}

	@Override
	public RadiologyReport saveOrUpdate(RadiologyReport report) {
		LOG.info("Saving or updating RadiologyReport");
		getCurrentSession().saveOrUpdate(report);
		return report;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RadiologyReport> getReportsByOrder(RadiologyOrder order) {
		LOG.info("Fetching RadiologyReports by order");
		Criteria criteria = getCurrentSession().createCriteria(RadiologyReport.class);
		return (List<RadiologyReport>) criteria.add(eq("radiologyOrder", order)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RadiologyReport> getReportsByPatient(Patient patient) {
		LOG.info("Fetching RadiologyReports by patient");
		Criteria criteria = getCurrentSession().createCriteria(RadiologyReport.class);
		return (List<RadiologyReport>) criteria.add(eq("patient", patient)).list();
	}
}
