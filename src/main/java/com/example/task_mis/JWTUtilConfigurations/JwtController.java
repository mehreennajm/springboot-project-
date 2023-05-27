package com.example.task_mis.JWTUtilConfigurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin (origins = "*")
@RequestMapping(path = "api/")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping({"authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }
}
