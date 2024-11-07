package org.openmrs.module.radiology.api.service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import org.openmrs.module.radiology.api.model.Modality;
import org.openmrs.module.radiology.api.model.OrderLogs;
import org.openmrs.module.radiology.api.model.RadiologyOrder;

public interface OrderLogsService {
    
    Optional<OrderLogs> get(@NotNull int id);
    
    OrderLogs saveOrUpdate(@NotNull OrderLogs orderLogs);

    OrderLogs save(RadiologyOrder order, String hl7Request, String hl7Response, Modality modality);
}