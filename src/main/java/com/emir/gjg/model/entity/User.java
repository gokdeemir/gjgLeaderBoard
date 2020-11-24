package com.emir.gjg.model.entity;

import com.emir.gjg.common.AbstractEntity;
import com.emir.gjg.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.eclipse.persistence.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "userName"})})
public class User extends AbstractEntity<UUID> {

    @Index
    @NotNull
    @Email(message = "Please provide acceptable mail address")
    private String email;

    @NotNull
    @Index
    private String userName;

    @Column(length = 60)
    @NotEmpty(message = "Please provide your password")
    @Getter(onMethod = @__( @JsonIgnore))
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role = Role.BASIC_USER;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private ProfilePhoto profilePhoto;

    @Column(name = "confirmed")
    private boolean confirmed = false;

    private Double score = 0D;

    private Locale country = Locale.getDefault();

}
