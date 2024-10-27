package org.openmrs.module.radiology.api.model;

import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.ServiceOrder;

public class RadiologyOrder extends ServiceOrder {
	
	public static final long serialVersionUID = 1L;
	
	public Concept specimenType;
	
	public Concept bodySite;
	
	private RadiologyOrder relatedRadiologyOrder;
	
	private Set<Radiology> tests;
	
	public RadiologyOrder() {
	}
	
	@Override
	public RadiologyOrder copy() {
		RadiologyOrder newOrder = new RadiologyOrder();
		super.copyHelper(newOrder);
		return newOrder;
	}
	
	/**
	 * Creates a discontinuation order for this.
	 *
	 * @return the newly created order
	 * @see org.openmrs.ServiceOrder#cloneForDiscontinuing()
	 */
	@Override
	public RadiologyOrder cloneForDiscontinuing() {
		RadiologyOrder newOrder = new RadiologyOrder();
		super.cloneForDiscontinuingHelper(newOrder);
		return newOrder;
	}
	
	/**
	 * Creates a ReferralOrder for revision from this order, sets the previousOrder, action field and
	 * other test order fields.
	 *
	 * @return the newly created order
	 */
	@Override
	public RadiologyOrder cloneForRevision() {
		RadiologyOrder newOrder = new RadiologyOrder();
		super.cloneForRevisionHelper(newOrder);
		return newOrder;
	}
	
	public Concept getSpecimenType() {
		return specimenType;
	}
	
	public void setSpecimenType(Concept specimenType) {
		this.specimenType = specimenType;
	}
	
	public Concept getBodySite() {
		return bodySite;
	}
	
	public void setBodySite(Concept bodySite) {
		this.bodySite = bodySite;
	}
	
	public Set<Radiology> getTests() {
		return tests;
	}
	
	public void setTests(Set<Radiology> tests) {
		this.tests = tests;
	}
	
	public RadiologyOrder getRelatedRadiologyOrder() {
		return relatedRadiologyOrder;
	}
	
	public void setRelatedRadiologyOrder(RadiologyOrder relatedRadiologyOrder) {
		this.relatedRadiologyOrder = relatedRadiologyOrder;
	}
}
