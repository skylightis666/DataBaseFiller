package com.polykek.database.controllers;

import com.polykek.database.services.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DatabaseController {
    private final DatabaseService databaseService;

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public ResponseEntity<String> fillData() {
        databaseService.fillData();
        return ResponseEntity.ok("База была заполнена");
    }
}
