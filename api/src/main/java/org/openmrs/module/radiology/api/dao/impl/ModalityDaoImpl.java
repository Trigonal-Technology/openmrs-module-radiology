package org.openmrs.module.radiology.api.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.module.radiology.api.dao.ModalityDao;
import org.openmrs.module.radiology.api.model.Modality;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ModalityDaoImpl implements ModalityDao {
    private static final Log LOG = LogFactory.getLog(ModalityDaoImpl.class);
    
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Optional<Modality> get(int id) {
        LOG.info("Inside get Modality");
        return Optional.ofNullable(getCurrentSession().get(Modality.class, id));
    }

    @Override
    public Modality saveOrUpdate(Modality modality) {
        LOG.info("Inside saveOrUpdate Modality");
        getCurrentSession().saveOrUpdate(modality);
        return modality;
    }
} 