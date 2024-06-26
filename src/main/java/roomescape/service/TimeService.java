package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.TimeSlot;
import roomescape.domain.dto.TimeSlotRequest;
import roomescape.domain.dto.TimeSlotResponse;
import roomescape.repository.ReservationDao;
import roomescape.repository.TimeDao;

import java.util.List;

@Service
public class TimeService {
    private final TimeDao timeDao;
    private final ReservationDao reservationDao;

    public TimeService(TimeDao timeDao, ReservationDao reservationDao) {
        this.timeDao = timeDao;
        this.reservationDao = reservationDao;
    }

    public List<TimeSlotResponse> findAll() {
        return timeDao.findAll()
                .stream()
                .map(TimeSlotResponse::from)
                .toList();
    }

    public TimeSlotResponse findById(Long id) {
        TimeSlot timeSlot = timeDao.findById(id);
        return TimeSlotResponse.from(timeSlot);
    }

    public TimeSlotResponse create(TimeSlotRequest timeSlotRequest) {
        validateDuplicatedTime(timeSlotRequest);
        Long id = timeDao.create(timeSlotRequest);
        TimeSlot timeSlot = timeSlotRequest.toEntity(id);
        return TimeSlotResponse.from(timeSlot);
    }

    private void validateDuplicatedTime(TimeSlotRequest timeSlotRequest) {
        if (timeDao.isExist(timeSlotRequest.startAt())) {
            throw new IllegalArgumentException("[ERROR] 이미 등록된 시간입니다");
        }
    }

    public void delete(Long id) {
        validateExistReservation(id);
        timeDao.delete(id);
    }

    private void validateExistReservation(Long id) {
        if (reservationDao.isExistsTimeId(id)) {
            throw new IllegalArgumentException("[ERROR] 예약이 등록된 시간은 제거할 수 없습니다");
        }
    }
}
