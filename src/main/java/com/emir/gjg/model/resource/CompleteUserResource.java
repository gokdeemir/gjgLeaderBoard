package com.emir.gjg.model.resource;

import com.emir.gjg.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;

@Getter
@Setter
@Resource
public class CompleteUserResource extends MinimalUserResource {

    private String email;

    private Role role;

}
