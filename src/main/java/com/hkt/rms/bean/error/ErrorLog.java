package com.hkt.rms.bean.error;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "error_log")
public class ErrorLog {

    @Id
    @Column(name = "log_id",length = 15)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;
    @Column(name = "error_type",length = 2)
    private String errorType;
    @Column(name = "order_no",length = 20)
    private String orderNo;
    @Column(name = "BSN",length = 16)
    private String BSN;
    @Column(name = "service_no",length = 16)
    private String serviceNo;
    @Column(name = "status",length = 1)
    private int status;
    @Column(name = "retry_action_id",length = 3)
    private int retryActionId;
    @Column(name = "description",length = 1000)
    private String description;

    @CreatedDate
    @Column(name = "create_date",length = 7)
    private Timestamp createDate;

    @Column(name = "last_updated_by",length = 20)
    private String lastUpdatedBy;

    @LastModifiedDate
    @Column(name = "last_updated_date",length = 7)
    private Timestamp lastUpdatedDate;


    @Transient private String errorTypeDesc;
}
