package com.example.task_mis.JWTUtilConfigurations;


import com.example.task_mis.entities.User;

public class JwtResponse {

    private String jwtToken;

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }


    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
