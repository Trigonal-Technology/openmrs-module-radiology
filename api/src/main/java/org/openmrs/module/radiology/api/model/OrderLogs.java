package org.openmrs.module.radiology.api.model;

import javax.persistence.*;

@Entity
@Table(name = "order_logs")
public class OrderLogs {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private RadiologyOrder order;

    @ManyToOne
    @JoinColumn(name = "modality_id")
    private Modality modality;

    @Column(name = "hl7_request")
    private String hl7Request;

    @Column(name = "hl7_response")
    private String hl7Response;

    // Getters and Setters
    // ...
} 