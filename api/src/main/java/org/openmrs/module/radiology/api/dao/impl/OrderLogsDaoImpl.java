package org.openmrs.module.radiology.api.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.module.radiology.api.dao.OrderLogsDao;
import org.openmrs.module.radiology.api.model.OrderLogs;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderLogsDaoImpl implements OrderLogsDao {
    private static final Log LOG = LogFactory.getLog(OrderLogsDaoImpl.class);
    
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Optional<OrderLogs> get(int id) {
        LOG.info("Inside get OrderLogs");
        return Optional.ofNullable(getCurrentSession().get(OrderLogs.class, id));
    }

    @Override
    public OrderLogs saveOrUpdate(OrderLogs orderLogs) {
        LOG.info("Inside saveOrUpdate OrderLogs");
        getCurrentSession().saveOrUpdate(orderLogs);
        return orderLogs;
    }
} 