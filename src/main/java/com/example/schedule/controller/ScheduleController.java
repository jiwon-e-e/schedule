package com.example.schedule.controller;

import com.example.schedule.dto.CreateScheduleRequest;
import com.example.schedule.dto.CreateScheduleResponse;
import com.example.schedule.dto.GetScheduleResponse;
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
}
