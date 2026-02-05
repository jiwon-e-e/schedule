package com.example.schedule.service;

import com.example.schedule.Exception.NonExistentException;
import com.example.schedule.Exception.WrongPasswordException;
import com.example.schedule.dto.scheduleDto.*;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    //scheduleRepository 와 commentService 에 접근할 수 있음
    private final ScheduleRepository scheduleRepository;
    private final CommentService commentService;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        // 사용자 입력 = request 에서 제목, 내용, 작성자, 비밀번호를 가져옴
        // -> Schedule 생성자에 넣음
        Schedule schedule = new Schedule(
                request.getName(),
                request.getContents(),
                request.getWriter(),
                request.getPw()
        );

        //Schedule 객체를 만들어서 Repository 에 저장
        Schedule newSchedule = scheduleRepository.save(schedule);

        //저장된 Schedule 을 기준으로 하여 반환
        //ID 와 생성일,수정일이 포함됨, PW 미포함
        return new CreateScheduleResponse(
                newSchedule.getId(),
                newSchedule.getName(),
                newSchedule.getContents(),
                newSchedule.getWriter(),
                newSchedule.getCreatedAt(),
                newSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse getOneSchedule(Long id) {
        // 조회할 일정 ID 를 매개변수로 받아와서
        // 해당 ID 를 가진 일정이 존재하는지 확인
        // 존재하지 않으면 NonExistentException, 존재하면 Response DTO 를 return
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                ()-> new NonExistentException("존재하지 않는 게시물입니다.")
        );

        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getName(),
                schedule.getContents(),
                schedule.getWriter(),
                //Schedule ID 를 기준으로 Comment list 를 찾아서 매개변수로 입력
                //형태는 List<GetCommentResponse>
                commentService.findCommentsByScheduleId(id),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetSchedulesResponse> getUserSchedule(String writer) {
        //null 또는 공백이 될 수 있는 writer 를 매개변수로 받아옴

        // schedules 에 수정일 기준으로 정렬된 Schedule List 를 저장
        List<Schedule> schedules = scheduleRepository.findAllByOrderByModifiedAtDesc();
        // dtos 이름으로 반환할 List 를 새로 선언
        List<GetSchedulesResponse> dtos = new ArrayList<>();

        // writer 가 null 또는 공백이라면 전체 리스트를 DTO 형태로 변환
        if (writer==null||writer.isEmpty()){
            for (Schedule schedule : schedules) {
                GetSchedulesResponse dto = new GetSchedulesResponse(
                        schedule.getId(),
                        schedule.getName(),
                        schedule.getContents(),
                        schedule.getWriter(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                );
                //dtos 에 추가
                dtos.add(dto);
            }
            return dtos;
        }

        // writer 가 존재한다면 해당 writer 가 작성한 일정만 DTO 형태로 반환
        for (Schedule schedule : schedules) {
            if (schedule.getWriter().equalsIgnoreCase(writer)){
                GetSchedulesResponse dto = new GetSchedulesResponse(
                        schedule.getId(),
                        schedule.getName(),
                        schedule.getContents(),
                        schedule.getWriter(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                );
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Transactional
    public UpdateScheduleResponse updateSchedule(Long id, UpdateScheduleRequest request) {
        // 조회할 일정 ID 를 매개변수로 받아와서
        // 해당 ID 를 가진 일정이 존재하는지 확인
        // 존재하지 않으면 NonExistentException, 존재하면 다음단계 실행
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                ()-> new NonExistentException("존재하지 않는 게시물입니다.")
        );

        // request DTO 에 담겨온 pw 를 기준으로 기존 pw 와 동일한지 확인
        // 동일하지 않으면 WrongPasswordException, 동일하면 update 실행
        if (!request.getPw().equalsIgnoreCase(schedule.getPw())){
            throw new WrongPasswordException("비밀번호가 일치하지 않습니다.");
        }

        //저장소에 갔다왔고 Transaction 안이므로 더티체크 수행
        schedule.update(
                request.getName(),
                request.getWriter()
        );

        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getName(),
                schedule.getWriter(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public void deleteSchedule(Long id, DeleteScheduleRequest request) {
        // 조회할 일정 ID 를 매개변수로 받아와서
        // 해당 ID 를 가진 일정이 존재하는지 확인
        // 존재하지 않으면 NonExistentException, 존재하면 다음단계 실행
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                ()-> new NonExistentException("존재하지 않는 게시물입니다.")
        );

        // request DTO 에 담겨온 pw 를 기준으로 기존 pw 와 동일한지 확인
        // 동일하지 않으면 WrongPasswordException, 동일하면 delete 실행
        if (!request.getPw().equalsIgnoreCase(schedule.getPw())){
            throw new WrongPasswordException("비밀번호가 일치하지 않습니다.");
        }

        // 반환값 없음
        scheduleRepository.deleteById(id);
    }



}
