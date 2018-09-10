package com.hkt.rms.bean.admin;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserAlert {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10)
    private String userName;
    @Column(length = 10)
    private String alertType;
}
