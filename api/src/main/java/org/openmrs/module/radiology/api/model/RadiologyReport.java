package org.openmrs.module.radiology.api.model;

import org.openmrs.BaseFormRecordableOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.module.radiology.api.enums.RadiologyReportStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "radiology_report")
public class RadiologyReport extends BaseFormRecordableOpenmrsData {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "radiology_report_id")
	private Integer radiologyReportId;

	@ManyToOne
	@JoinColumn(name = "radiology_order_id", nullable = false)
	private RadiologyOrder radiologyOrder;

	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "encounter_id")
	private Encounter encounter;

	@ManyToOne
	@JoinColumn(name = "concept_id")
	private Concept concept;

	@ManyToOne
	@JoinColumn(name = "performer_id")
	private User performer;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	@ManyToOne
	@JoinColumn(name = "modality")
	private Concept modality;

	@Column(name = "report")
	@Lob
	private String report;

	@Column(name = "findings")
	@Lob
	private String findings;

	@Column(name = "impressions")
	@Lob
	private String impressions;

	@Column(name = "recommendations")
	@Lob
	private String recommendations;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private RadiologyReportStatus status;

	@Column(name = "report_datetime")
	private Date reportDatetime;

	@Column(name = "form_namespace_and_path")
	private String formNamespaceAndPath;

	public Integer getRadiologyReportId() {
		return radiologyReportId;
	}

	public void setRadiologyReportId(Integer radiologyReportId) {
		this.radiologyReportId = radiologyReportId;
	}

	public RadiologyOrder getRadiologyOrder() {
		return radiologyOrder;
	}

	public void setRadiologyOrder(RadiologyOrder radiologyOrder) {
		this.radiologyOrder = radiologyOrder;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Encounter getEncounter() {
		return encounter;
	}

	public void setEncounter(Encounter encounter) {
		this.encounter = encounter;
	}

	public Concept getConcept() {
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	public User getPerformer() {
		return performer;
	}

	public void setPerformer(User performer) {
		this.performer = performer;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Concept getModality() {
		return modality;
	}

	public void setModality(Concept modality) {
		this.modality = modality;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getFindings() {
		return findings;
	}

	public void setFindings(String findings) {
		this.findings = findings;
	}

	public String getImpressions() {
		return impressions;
	}

	public void setImpressions(String impressions) {
		this.impressions = impressions;
	}

	public String getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(String recommendations) {
		this.recommendations = recommendations;
	}

	public RadiologyReportStatus getStatus() {
		return status;
	}

	public void setStatus(RadiologyReportStatus status) {
		this.status = status;
	}

	public Date getReportDatetime() {
		return reportDatetime;
	}

	public void setReportDatetime(Date reportDatetime) {
		this.reportDatetime = reportDatetime;
	}

	public String getFormNamespaceAndPath() {
		return formNamespaceAndPath;
	}

	public void setFormNamespaceAndPath(String formNamespaceAndPath) {
		this.formNamespaceAndPath = formNamespaceAndPath;
	}

	@Override
	public Integer getId() {
		return getRadiologyReportId();
	}

	@Override
	public void setId(Integer integer) {
		setRadiologyReportId(integer);
	}
}
