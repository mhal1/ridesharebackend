package com.reactlearning.RideSharingBackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "current_user_logged")
public class CurrentUserLogged {

    @Id
    private Integer id;

    private String name;

    private String userName;

    private String password;
}
