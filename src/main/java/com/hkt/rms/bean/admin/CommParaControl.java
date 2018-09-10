package com.hkt.rms.bean.admin;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class CommParaControl {

    @Id
    @Column(length = 10)
    private String masterAppCode;
    @Column(length = 250)
    private String description = "";
    private Date createDate;
    private Date lastUpdDate;
    @Column(length = 10)
    private String lastUpdId = "";
    @Column(length = 1)
    private String modifyInd = "";
}
