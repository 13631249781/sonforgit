package com.hkt.rms.bean.admin;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class UserDepartment {

    @Id
    @Column(length = 10)
    private String deptId;
    @Column(length = 25)
    private String deptName;
    private Date createDate;
    private Date lastUpdDate;
    @Column(length = 10)
    private String lastUpdId;

}
