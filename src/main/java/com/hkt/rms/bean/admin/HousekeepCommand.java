package com.hkt.rms.bean.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="HOUSEKEEP_COMMAND")
public class HousekeepCommand {

    @Id
    @Column(length=50)
    private String description = "";
    
    @Column(name="sql_command",length=2000)
    private String sqlCommand ="";
    
    @Column(name="seq_No")
    private int seqNo = 0;

    @Column(name="enable_ind",length=1)
    private String enableInd = "";
    
    @Column(name="create_date")
    private Date createDate ;
    
    @Column(name="last_updated_date")
    private Date lastUpdatedDate;
    
    @Column(name="last_updated_by",length=10)
    private String lastUpdatedBy = "";
}
