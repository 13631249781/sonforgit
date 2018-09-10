package com.hkt.rms.bean.admin;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserGroup {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(length = 8)
    private int groupId;
    @Column(length = 50)
    private String groupName = "";
    @Column(length = 250)
    private String description = "";
}
