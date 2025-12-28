package org.openmrs.module.radiology.api.dao;

import org.openmrs.Patient;
import org.openmrs.module.radiology.api.model.RadiologyOrder;
import org.openmrs.module.radiology.api.model.RadiologyReport;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface RadiologyReportDao {

	Optional<RadiologyReport> getRadiologyReportByUuid(@NotNull String uuid);

	RadiologyReport saveOrUpdate(@NotNull RadiologyReport report);

	List<RadiologyReport> getReportsByOrder(RadiologyOrder order);

	List<RadiologyReport> getReportsByPatient(Patient patient);
}
