package org.openmrs.module.radiology.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.Date;
import java.util.List;

import org.openmrs.BaseFormRecordableOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Patient;

@Entity
@Table(name = "radiology_order")
public class Radiology extends BaseFormRecordableOpenmrsData {
	
	public enum RadiologyStatus {
		PREPARATION,
		IN_PROGRESS,
		NOT_DONE,
		ON_HOLD,
		STOPPED,
		COMPLETED
	}
	
	public enum RadiologyOutcome {
		SUCCESSFUL,
		NOT_SUCCESSFUL,
		PARTIALLY_SUCCESSFUL
	}
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "radiology_id")
	private Integer radiologyId;
	
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name = "radiology_order_id")
	private RadiologyOrder radiologyOrder;
	
	@ManyToOne
	@JoinColumn(name = "concept")
	private Concept concept;
	
	@ManyToOne
	@JoinColumn(name = "radiology_reason")
	private Concept radiologyReason;
	
	@ManyToOne
	@JoinColumn(name = "category")
	private Concept category;
	
	@ManyToOne
	@JoinColumn(name = "body_site")
	private Concept bodySite;
	
	@ManyToOne
	@JoinColumn(name = "part_of")
	private Radiology partOf;
	
	@Column(name = "start_datetime")
	private Date startDatetime;
	
	@Column(name = "end_datetime")
	private Date endDatetime;
	
	@Enumerated(EnumType.STRING)
	private RadiologyStatus status;
	
	@ManyToOne
	@JoinColumn(name = "status_reason")
	private Concept statusReason;
	
	@Enumerated(EnumType.STRING)
	private RadiologyOutcome outcome;
	
	@Column(name = "report")
	private String radiologyReport;
	
	@ManyToOne
	@JoinColumn(name = "modality")
	public Concept modality;
	
	@OneToMany
	@JoinTable(name = "encounter_radiologys", joinColumns = @JoinColumn(name = "radiology_id"), inverseJoinColumns = @JoinColumn(name = "encounter_id"))
	private List<Encounter> encounters;
	
	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;
	
	public Integer getRadiologyId() {
		return radiologyId;
	}
	
	public void setRadiologyId(Integer radiologyId) {
		this.radiologyId = radiologyId;
	}
	
	public Patient getPatient() {
		return patient;
	}
	
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public RadiologyOrder getRadiologyOrder() {
		return radiologyOrder;
	}
	
	public void setRadiologyOrder(RadiologyOrder radiologyOrder) {
		this.radiologyOrder = radiologyOrder;
	}
	
	public Concept getConcept() {
		return concept;
	}
	
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
	public Concept getCategory() {
		return category;
	}
	
	public void setCategory(Concept category) {
		this.category = category;
	}
	
	public Concept getBodySite() {
		return bodySite;
	}
	
	public void setBodySite(Concept bodySite) {
		this.bodySite = bodySite;
	}
	
	public Radiology getPartOf() {
		return partOf;
	}
	
	public void setPartOf(Radiology partOf) {
		this.partOf = partOf;
	}
	
	public Date getStartDatetime() {
		return startDatetime;
	}
	
	public void setStartDatetime(Date startDatetime) {
		this.startDatetime = startDatetime;
	}
	
	public Date getEndDatetime() {
		return endDatetime;
	}
	
	public void setEndDatetime(Date endDatetime) {
		this.endDatetime = endDatetime;
	}
	
	public RadiologyStatus getStatus() {
		return status;
	}
	
	public void setStatus(RadiologyStatus status) {
		this.status = status;
	}
	
	public RadiologyOutcome getOutcome() {
		return outcome;
	}
	
	public void setOutcome(RadiologyOutcome outcome) {
		this.outcome = outcome;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Concept getRadiologyReason() {
		return radiologyReason;
	}
	
	public void setRadiologyReason(Concept radiologyReason) {
		this.radiologyReason = radiologyReason;
	}
	
	public Concept getStatusReason() {
		return statusReason;
	}
	
	public void setStatusReason(Concept statusReason) {
		this.statusReason = statusReason;
	}
	
	public String getRadiologyReport() {
		return radiologyReport;
	}
	
	public void setRadiologyReport(String radiologyReport) {
		this.radiologyReport = radiologyReport;
	}
	
	public Concept getModality() {
		return modality;
	}
	
	public void setModality(Concept modality) {
		this.modality = modality;
	}
	
	public List<Encounter> getEncounters() {
		return encounters;
	}
	
	public void setEncounters(List<Encounter> encounters) {
		this.encounters = encounters;
	}
	
	@Override
	public Integer getId() {
		return getRadiologyId();
	}
	
	@Override
	public void setId(Integer integer) {
		setRadiologyId(integer);
	}
}
