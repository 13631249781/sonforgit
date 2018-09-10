package com.hkt.rms.bean.admin;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
public class UserMenu {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(length = 5)
    private int menuId;
    private String menuName = "";
    private String menuUri = "";
    @Column(length = 5)
    private Integer preMenuId;
    @Column(length = 5)
    private String isMenu;
    @Column(length = 20)
    private String actionId = "";
    @Column(length = 5)
    private int menuLevel = 1;
    @Column(length = 5)
    private int sequence = 0;
    private Date activatedDate;
    @Column(length = 250)
    private String description = "";
    @Column(length = 30)
    private String iconName = "";
}
