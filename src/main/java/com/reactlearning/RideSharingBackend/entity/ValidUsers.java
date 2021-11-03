package com.reactlearning.RideSharingBackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "users", uniqueConstraints = @UniqueConstraint( columnNames = {"userName"}))
public class ValidUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String userName;

    private String password;

    private String loggedIn;
}
