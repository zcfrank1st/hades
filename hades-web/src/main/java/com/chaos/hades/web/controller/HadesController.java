package com.chaos.hades.web.controller;

import com.chaos.hades.web.model.ConfigContent;
import com.chaos.hades.web.service.HadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zcfrank1st on 22/11/2016.
 */
@RestController
public class HadesController {
    @Autowired
    private HadesService hadesService;

    @RequestMapping(value = "/config/skeleton", method = RequestMethod.POST)
    public ResponseEntity initProjectSkeleton(@RequestBody String project) throws Exception {
        hadesService.initProjectSkeleton(project);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/config/skeleton/{project}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProjectSkeleton(@PathVariable("project") String project) throws Exception {
        hadesService.deleteProjectSkeleton(project);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/config", method = RequestMethod.POST)
    public ResponseEntity addConfig(@RequestBody ConfigContent configContent) throws Exception {
        hadesService.addConfig(configContent);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/config/{project}/{env}/{key}", method = RequestMethod.DELETE)
    public ResponseEntity deleteConfig(
            @PathVariable("project") String project,
            @PathVariable("env") Integer env,
            @PathVariable("key") String key) throws Exception {
        hadesService.deleteConfig(project, env, key);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public ResponseEntity scanConfigs(@RequestParam("project") String project, @RequestParam("env") Integer env) throws Exception {
        return ResponseEntity.ok(hadesService.scanConfigs(project, env));
    }
}
