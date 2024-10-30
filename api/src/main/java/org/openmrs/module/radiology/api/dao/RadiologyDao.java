package org.openmrs.module.radiology.api.dao;

import javax.validation.constraints.NotNull;

import java.util.Optional;

import org.openmrs.module.radiology.api.model.Radiology;

public interface RadiologyDao {
	
	Optional<Radiology> get(@NotNull int id);
	
	Optional<Radiology> getRadiologyOrderByUuid(@NotNull String uuid);
	
	Radiology saveOrUpdate(@NotNull Radiology radiology);
}
