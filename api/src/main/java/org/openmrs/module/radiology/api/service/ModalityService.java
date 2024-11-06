package org.openmrs.module.radiology.api.service;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import org.openmrs.module.radiology.api.model.Modality;

public interface ModalityService {
    
    Optional<Modality> get(@NotNull int id);
    
    Modality saveOrUpdate(@NotNull Modality modality);
} 