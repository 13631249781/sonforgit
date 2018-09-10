package com.hkt.rms.vo;

import lombok.Data;

@Data
public class UserAlertVo {
    private boolean userAvailability;
    private String alertId = "";
    private String alertDescription = "";
}
