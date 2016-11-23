package com.chaos.hades.web.controller;

import com.chaos.hades.web.model.Message;
import com.chaos.hades.web.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by zcfrank1st on 23/11/2016.
 */
@RestController
public class AuthController {
    private final AuthorizationService authorizationService;

    @Autowired
    public AuthController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public ResponseEntity getGitlabProjectAuth (
            @RequestHeader("token") String token,
            @RequestParam("projectpath") String projectPath,
            @RequestParam("username") String userName
    ) throws IOException {
        Message message = new Message<>();
        message.setCode(0);
        message.setMessage("ok");
        message.setBody(authorizationService.getGitlabProjectAccessLevel(token, projectPath, userName));

        return ResponseEntity.ok(message);
    }
}
