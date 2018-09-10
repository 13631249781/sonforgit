package com.hkt.rms.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class AuditTrail {
    @Id
    @Column(length = 8)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int auditId;
    @Column(length = 10)
    private int staffNum;
    @Column(length = 15)
    private String ipAddress = "";
    @Column(length = 20)
    private String status = "";
    @Column(length = 250)
    private String description = "";
    private Date startTime;
}
