package com.hkt.rms.bean.admin;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserGroupMenu {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(length = 5)
    private int menuId;
    @Column(length = 5)
    private int groupId;
}


