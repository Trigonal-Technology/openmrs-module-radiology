package org.openmrs.module.radiology.api.model;

import org.openmrs.BaseFormRecordableOpenmrsData;

import javax.persistence.*;

@Entity
@Table(name = "radiology_order_details")
public class RadiologyOrderDetail extends BaseFormRecordableOpenmrsData {

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


    @Override
    public Integer getId() {
        return getRadiologyId();
    }

    @Override
    public void setId(Integer id) {
        setRadiologyId(id);
    }

    public Integer getRadiologyId() {
        return radiologyId;
    }

    public void setRadiologyId(Integer radiologyId) {
        this.radiologyId = radiologyId;
    }
}
