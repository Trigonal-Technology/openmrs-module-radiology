package org.openmrs.module.radiology.api;

import javax.validation.constraints.NotNull;

import java.util.Optional;

import org.openmrs.module.radiology.api.model.Radiology;

public interface RadiologyService {
	
	Optional<Radiology> getRadiologyOrderByUuid(@NotNull String uuid);
	
	Radiology saveOrUpdate(@NotNull Radiology radiology);
	
}
