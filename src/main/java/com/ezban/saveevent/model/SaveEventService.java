package com.ezban.saveevent.model;

import com.ezban.event.model.Event;
import com.ezban.event.model.EventRepository;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("saveEventService")
public class SaveEventService {

    @Autowired
    SaveEventRepository saveEventRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 更新收藏狀態，如果沒有收藏記錄則新增收藏記錄
    public SaveEvent changtSaveStatus(AddSaveEventDTO addSaveEventDTO) {
        SaveEvent saveEvent;

        Optional<SaveEvent> saveEvents = saveEventRepository.findSaveEventByMemberAndEvent(addSaveEventDTO.getMemberNo(), addSaveEventDTO.getEventNo());

        if (saveEvents.isEmpty()) {
            saveEvent = new SaveEvent();
            Event event = eventRepository.findById(addSaveEventDTO.getEventNo()).orElseThrow(() -> new RuntimeException("找不到活動"));
            saveEvent.setEvent(event);
            Member member = memberRepository.findById(addSaveEventDTO.getMemberNo()).orElseThrow(() -> new RuntimeException("找不到會員"));
            saveEvent.setMember(member);
            saveEvent.setSaveStatus((byte) 1);

            saveEventRepository.save(saveEvent);
        } else {
            saveEvent = saveEvents.get();
            saveEvent.setSaveStatus((byte) (saveEvent.getSaveStatus() == 1 ? 0 : 1));
            saveEventRepository.save(saveEvent);
        }

        return saveEvent;
    }

    // 查詢某個會員的所有收藏活動，返回收藏狀態為 1 的活動
    public List<SaveEventDTO> findOnlyFavoritesByMember(Integer memberNo) {
        List<SaveEvent> saveEvents = saveEventRepository.findByMember(memberNo);
        return saveEvents.stream()
                .filter(saveEvent -> saveEvent.getSaveStatus() == 1)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 查詢某個會員的所有收藏活動，返回收藏狀態為 1 和 0 的活動
    public List<SaveEventDTO> findSaveEventDTOsByMember(Integer memberNo) {
        List<SaveEvent> saveEvents = saveEventRepository.findByMember(memberNo);
        return saveEvents.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 將 SaveEvent 轉換為 SaveEventDTO
    private SaveEventDTO convertToDto(SaveEvent saveEvent) {
        Event event = saveEvent.getEvent();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new SaveEventDTO(
                event.getEventNo(),
                event.getEventName(),
                event.getEventCity(),
                event.getEventDetailedAddress(),
                convertTimestampToLocalDateTime(event.getEventStartTime()),
                convertTimestampToLocalDateTime(event.getEventEndTime()),
                saveEvent.getSaveStatus(),
                convertTimestampToLocalDateTime(event.getEventStartTime()).format(formatter),
                convertTimestampToLocalDateTime(event.getEventEndTime()).format(formatter)
        );
    }

    // Timestamp 轉成 LocalDateTime
    private LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
