package com.example.demo.service;

import com.example.demo.dto.BookingRequestDTO;
import com.example.demo.dto.SessionResponseDTO;
import com.example.demo.entity.*;
import com.example.demo.mapper.SessionMapper;
import com.example.demo.repository.*;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookingService {

    private final StationRepository stationRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final SessionRepository sessionRepository;
    private final ServiceRepository serviceRepository;
    private final SessionServiceRepository sessionServiceRepository;

    public BookingService(StationRepository stationRepository, UserRepository userRepository, CarRepository carRepository, SessionRepository sessionRepository, ServiceRepository serviceRepository, SessionServiceRepository sessionServiceRepository) {
        this.stationRepository = stationRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.sessionRepository = sessionRepository;
        this.serviceRepository = serviceRepository;
        this.sessionServiceRepository = sessionServiceRepository;
    }

    public List<Long> findAvailableTimes(BookingRequestDTO dto) {
        Station station = stationRepository.findById(dto.getStationId()).orElseThrow();
        LocalDate date = dto.getDate();
        long totalDuration = dto.getServices().stream()
                .mapToLong(s -> {
                    Service service = serviceRepository.findById(s.getServiceId()).orElseThrow();
                    return service.getDuration() * s.getQuantity();
                })
                .sum();
        DayOfWeek day = date.getDayOfWeek();
        long start = getStartTimeByDay(station, day);
        long finish = getFinishTimeByDay(station, day);
        List<Long> availableSlots = new ArrayList<>();
        for (long t = start; t + totalDuration <= finish; t += 15) {
            availableSlots.add(t);
        }
        return availableSlots;
    }

    public Optional<SessionResponseDTO> bookSession(BookingRequestDTO dto) {
        Station station = stationRepository.findById(dto.getStationId()).orElseThrow();
        User customer = userRepository.findById(dto.getCustomerId()).orElseThrow();
        Car car = carRepository.findById(dto.getCarId()).orElseThrow();
        Session session = new Session();
        session.setDate(dto.getDate());
        session.setTime(dto.getStartTime());
        session.setDuration(dto.getServices().stream()
                .mapToLong(s -> {
                    Service service = serviceRepository.findById(s.getServiceId()).orElseThrow();
                    return service.getDuration() * s.getQuantity();
                })
                .sum());
        session.setCost((float)dto.getServices().stream()
                .mapToDouble(s -> {
                    Service service = serviceRepository.findById(s.getServiceId()).orElseThrow();
                    return service.getCost() * s.getQuantity();
                })
                .sum());
        session.setStatus(Status.BOOKED);
        session.setCustomer(customer);
        session.setCar(car);
        session.setStation(station);
        session.setMaster(null);
        session.setDiscount(null);
        session.setDescription(dto.getDescription());
        Session saved = sessionRepository.save(session);
        List<SessionServices> sessionServices = dto.getServices().stream()
                .map(s -> {
                    Service service = serviceRepository.findById(s.getServiceId()).orElseThrow();
                    SessionServices ss = new SessionServices();
                    ss.setSession(saved);
                    ss.setService(service);
                    ss.setQuantity(s.getQuantity());
                    return ss;
                })
                .collect(Collectors.toList());
        sessionServiceRepository.saveAll(sessionServices);
        return Optional.of(SessionMapper.toResponseDTO(saved));
    }

    private long getStartTimeByDay(Station s, DayOfWeek d) {
        return switch (d) {
            case MONDAY -> s.getMonStart();
            case TUESDAY -> s.getTueStart();
            case WEDNESDAY -> s.getWenStart();
            case THURSDAY -> s.getThuStart();
            case FRIDAY -> s.getFriStart();
            case SATURDAY -> s.getSatStart();
            case SUNDAY -> s.getSunStart();
        };
    }

    private long getFinishTimeByDay(Station s, DayOfWeek d) {
        return switch (d) {
            case MONDAY -> s.getMonFinish();
            case TUESDAY -> s.getTueFinish();
            case WEDNESDAY -> s.getWenFinish();
            case THURSDAY -> s.getThuFinish();
            case FRIDAY -> s.getFriFinish();
            case SATURDAY -> s.getSatFinish();
            case SUNDAY -> s.getSunFinish();
        };
    }

    private boolean isMasterFree(User master, long startTime, long duration, List<Session> sessions) {
        return sessions.stream()
                .filter(s -> s.getMaster() != null && s.getMaster().getId().equals(master.getId()))
                .noneMatch(s -> overlaps(startTime, duration, s.getTime(), s.getDuration()));
    }

    private boolean overlaps(long start1, long dur1, long start2, long dur2) {
        return start1 < start2 + dur2 && start2 < start1 + dur1;
    }
}
