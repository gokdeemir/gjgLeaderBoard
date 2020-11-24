package com.emir.gjg.model.dto;

import com.emir.gjg.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 11 Şub 2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    private String userName;

    private String email;

    private String name;

    private String surname;

    private String password;

    private String countryIsoCode;

}
