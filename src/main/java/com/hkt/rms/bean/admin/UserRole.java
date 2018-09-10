package com.hkt.rms.bean.admin;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserRole {
    @Id
    @Column(length = 3)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int roleId;
    @Column(length = 30)
    private String roleName = "";
    @Column(length = 250)
    private String description = "";
    @Column(length = 2)
    private String addAction = "";
    @Column(length = 2)
    private String deleteAction = "";
    @Column(length = 2)
    private String updateAction = "";
    @Column(length = 2)
    private String viewAction = "";

    public String getAllowAction() {
        if (deleteAction.equals("Y") && updateAction.equals("Y"))
            return "All";
        else if (addAction.equals("Y")) return "Add";
        return "View";
    }

}
