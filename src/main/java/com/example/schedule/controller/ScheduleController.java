package com.example.schedule.controller;

import com.example.schedule.dto.scheduleDto.*;
import com.example.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ValidateString v;

    @PostMapping("/schedules")
    ResponseEntity<CreateScheduleResponse> createSchedule (@RequestBody CreateScheduleRequest request){
        //request 에 담겨온 내용에 올바르지 않은 값이 있는지 미리 확인
        v.validateNullableAndLength(request.getName(), 30);
        v.validateNullableAndLength(request.getContents(), 200);
        v.validateNullable(request.getWriter());
        v.validateNullable(request.getPw());

        //throw 되지 않았다면 저장 메소드 호출하여 response 에 담기
        CreateScheduleResponse response = scheduleService.save(request);
        //201 CREATED 반환, body 는 저장한 response 로 출력
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/schedules/{id}")
    ResponseEntity<GetScheduleResponse> getOneSchedule (@PathVariable Long id){
        // PathVariable로 schedule id 를 받아와서 단건조회 메서드 호출하여 response 에 담기
        GetScheduleResponse response = scheduleService.getOneSchedule(id);
        // 200 OK 반환, body 는 저장한 response 로 출력
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/schedules")
    ResponseEntity<List<GetSchedulesResponse>> getUserSchedule (@RequestParam(required = false) String writer){
        // queryParameter 로 wrtier 를 받아와서 다건조회 메서드 호출하여 response 에 담기
        List<GetSchedulesResponse> responses = scheduleService.getUserSchedule(writer);
        // 200 OK 반환, body 는 저장한 response 로 출력
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PutMapping("/schedules/{id}")
    ResponseEntity<UpdateScheduleResponse> updateSchedule (@PathVariable Long id, @RequestBody UpdateScheduleRequest request){
        //request 에 담겨온 내용에 올바르지 않은 값이 있는지 미리 확인
        v.validateNullableAndLength(request.getName(), 30);
        v.validateNullable(request.getWriter());

        // PathVariable로 schedule id 를 받아와서 단건수정 메서드 호출하여 response 에 담기
        UpdateScheduleResponse response = scheduleService.updateSchedule(id, request);
        // 200 OK 반환, body 는 저장한 response 로 출력
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/schedules/{id}")
    ResponseEntity<Void> deleteSchedule (@PathVariable Long id, @RequestBody DeleteScheduleRequest request){
        // PathVariable로 schedule id 를 받아와서 단건삭제 메서드 호출
        scheduleService.deleteSchedule(id, request);
        // 204 NO_CONTENT , 반환값 없음
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
