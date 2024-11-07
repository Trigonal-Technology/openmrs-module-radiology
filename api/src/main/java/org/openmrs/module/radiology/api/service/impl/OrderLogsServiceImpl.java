package org.openmrs.module.radiology.api.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.radiology.api.dao.OrderLogsDao;
import org.openmrs.module.radiology.api.model.Modality;
import org.openmrs.module.radiology.api.model.OrderLogs;
import org.openmrs.module.radiology.api.model.RadiologyOrder;
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

    @Override
    public OrderLogs save(RadiologyOrder order, String hl7Request, String hl7Response, Modality modality) {
        LOG.info("Inside saveOrUpdate OrderLogs");
        OrderLogs orderLog = new OrderLogs();
        orderLog.setOrder(order);
        orderLog.setHl7Request(hl7Request);
        orderLog.setHl7Response(hl7Response);
        orderLog.setModality(modality);
    }
} 