package org.openmrs.module.radiology.api.model;

import javax.persistence.*;

import org.openmrs.OrderType;

import java.util.List;

@Entity
@Table(name = "modality")
public class Modality {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "ip")
    private String ip;

    @Column(name = "port")
    private Integer port;

    @Column(name = "timeout")
    private Integer timeout;

    @ManyToOne
    @JoinColumn(name = "order_type_id")
    private OrderType orderType;

    // Getters and Setters
    // ...
}
