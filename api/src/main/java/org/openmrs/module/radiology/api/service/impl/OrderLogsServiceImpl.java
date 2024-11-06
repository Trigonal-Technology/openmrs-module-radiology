package org.openmrs.module.radiology.api.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.radiology.api.dao.OrderLogsDao;
import org.openmrs.module.radiology.api.model.OrderLogs;
import org.openmrs.module.radiology.api.service.OrderLogsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class OrderLogsServiceImpl implements OrderLogsService {
    private static final Log LOG = LogFactory.getLog(OrderLogsServiceImpl.class);

    private OrderLogsDao orderLogsDao;

    public void setOrderLogsDao(OrderLogsDao orderLogsDao) {
        this.orderLogsDao = orderLogsDao;
    }

    @Override
    public Optional<OrderLogs> get(int id) {
        LOG.info("Inside get OrderLogs");
        return orderLogsDao.get(id);
    }

    @Override
    public OrderLogs saveOrUpdate(OrderLogs orderLogs) {
        LOG.info("Inside saveOrUpdate OrderLogs");
        return orderLogsDao.saveOrUpdate(orderLogs);
    }
} 