package org.openmrs.module.radiology.api.dao.impl;

import static org.hibernate.criterion.Restrictions.eq;

import java.util.Optional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.module.radiology.api.dao.RadiologyDao;
import org.openmrs.module.radiology.api.model.Radiology;

public class RadiologyDaoImpl implements RadiologyDao {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public Optional<Radiology> get(int id) {
		return Optional.empty();
	}
	
	@Override
	public Optional<Radiology> getRadiologyOrderByUuid(String uuid) {
		Criteria criteria = getCurrentSession().createCriteria(Radiology.class);
		return Optional.ofNullable((Radiology) criteria.add(eq("uuid", uuid)).uniqueResult());
	}
	
	@Override
	public Radiology saveOrUpdate(Radiology radiology) {
		getCurrentSession().saveOrUpdate(radiology);
		return radiology;
	}
}
