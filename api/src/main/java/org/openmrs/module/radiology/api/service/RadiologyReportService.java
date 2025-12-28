package org.openmrs.module.radiology.api.service;

import org.openmrs.module.radiology.api.model.RadiologyReport;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface RadiologyReportService {

	Optional<RadiologyReport> getRadiologyReportByUuid(@NotNull String uuid);

	RadiologyReport saveOrUpdate(@NotNull RadiologyReport report);

	List<RadiologyReport> getReportsByOrderUuid(@NotNull String orderUuid);
}
