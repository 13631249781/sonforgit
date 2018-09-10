package com.hkt.rms.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SiteBean {
    @Id
    private int siteId = 1;

    private String systemPath = "";
    private String environment = "";
    private String batchJobHostName = "";

    // Email
    private String email = "";
    private String smtpServerName = "";
    private int smtpPort = 25;
    private String mailEncoding = "";
    private String smtpUser = "";
    private String smtpPassword = "";
    private String popServerName = "";
    private int popPort = 25;
    private String popUser = "";
    private String popPassword = "";
    private String allowedEmailPattern = "";

    // LDAP
    private String isLdapOn = "";
    private String ldapServerUrl = "";
    private String ldapAttributeLoginName = "";
    private String principalName = "";

    // Proxy
    private String proxyInd = "N";
    private String proxyUrl = "";
    private int proxyPort = 8080;

    // SMS SMPP Gateway Setting
    private String smppUrl = "";
    private int smppPort = 50111;
    private String smppUser = "";
    private String smppPassword = "";
    private String smsSenderAddr = "";
    private String smsDestAddr = "";
    private String smsAddrRange = "";
}
