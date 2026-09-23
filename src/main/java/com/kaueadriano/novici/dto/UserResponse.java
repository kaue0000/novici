package com.kaueadriano.novici.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Integer id;
    private String name;
    private String email;

    public UserResponse(Integer id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
