package org.openmrs.module.radiology.api.service;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import org.openmrs.module.radiology.api.model.OrderLogs;

public interface OrderLogsService {
    
    Optional<OrderLogs> get(@NotNull int id);
    
    OrderLogs saveOrUpdate(@NotNull OrderLogs orderLogs);
} 