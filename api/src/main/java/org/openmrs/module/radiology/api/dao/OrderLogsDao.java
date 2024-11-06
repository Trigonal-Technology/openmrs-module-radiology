package org.openmrs.module.radiology.api.dao;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import org.openmrs.module.radiology.api.model.OrderLogs;

public interface OrderLogsDao {
    
    Optional<OrderLogs> get(@NotNull int id);
    
    OrderLogs saveOrUpdate(@NotNull OrderLogs orderLogs);
} 