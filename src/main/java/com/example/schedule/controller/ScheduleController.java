package com.example.schedule.controller;

import com.example.schedule.dto.*;
import com.example.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    ResponseEntity<CreateScheduleResponse> createSchedule (@RequestBody CreateScheduleRequest request){
        CreateScheduleResponse response = scheduleService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/schedules/{id}")
    ResponseEntity<GetScheduleResponse> getOneSchedule (@PathVariable Long id){
        GetScheduleResponse response = scheduleService.getOneSchedule(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/schedules")
    ResponseEntity<List<GetScheduleResponse>> getUserSchedule (@RequestParam(required = false) String writer){
        List<GetScheduleResponse> responses = scheduleService.getUserSchedule(writer);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PutMapping("/schedules/{id}")
    ResponseEntity<UpdateScheduleResponse> updateSchedule (@PathVariable Long id, @RequestBody UpdateScheduleRequest request){
        UpdateScheduleResponse response = scheduleService.updateSchedule(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/schedules/{id}")
    ResponseEntity<Void> deleteSchedule (@PathVariable Long id, @RequestBody DeleteScheduleRequest request){
        scheduleService.deleteSchedule(id, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
