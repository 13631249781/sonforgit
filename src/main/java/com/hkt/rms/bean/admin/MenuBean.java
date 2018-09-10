package com.hkt.rms.bean.admin;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenuBean {

    @Id
    private int menuId;
    private String menuName = "";
    private String menuUri = "";
    private String preMenuId = "";
    private String isMenu = "";
    private String actionId = "";
    private int menuLevel = 1;
    private int sequence = 0;
    private Date activatedDate;
    private String description = "";
    private String iconName = "";
}
