package org.openmrs.module.radiology.api.dao;

import java.util.List;
import java.util.Optional;

import org.openmrs.module.radiology.api.model.Radiology;
import org.openmrs.module.radiology.api.model.RadiologyOrder;
import org.openmrs.module.radiology.api.model.RadiologyOrderQueue;

import javax.validation.constraints.NotNull;

public interface RadiologyOrderQueueDao {

    List<RadiologyOrderQueue> findPendingOrFailedOrders();

    RadiologyOrderQueue saveOrUpdate(RadiologyOrderQueue queue);

    Optional<RadiologyOrderQueue> findByRadiologyOrderId(@NotNull Integer orderId);
}
