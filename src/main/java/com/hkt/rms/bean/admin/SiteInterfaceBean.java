package com.hkt.rms.bean.admin;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name="Site_Interface")
public class SiteInterfaceBean
{
        @Id
        @Column(name="system_name",length=10)
	private String systemName = "";
        
	@Column(length=100)
        private String url = "";
	
	@Column(name="user_name",length=50)
	private String userName = "";
	
	@Column(length=50)
	private String password = "";
	
	@Column(name="x_app_key",length=50)
	private String xappKey = "";
	
	@Column(name="be_id",length=20)
	private String beId = "";
	
	@Column(name="channel_type",length=20)
	private String channelType = "";
	
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;
	
	@Column(name="last_updated_by")
	private String lastUpdatedBy = "";
	
	@Transient
	private Map<String, String> headerAttributeMap = null;

	
}