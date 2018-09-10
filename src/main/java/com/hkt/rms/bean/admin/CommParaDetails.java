package com.hkt.rms.bean.admin;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class CommParaDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10)
    private String masterAppCd;
    @Column(length = 50)
    private String appCd;
    @Column(length = 250)
    private String appDesc;
    private Date createDate;
    private Date lastUpdDate;
    @Column(length = 10)
    private String lastUpdId;
}
