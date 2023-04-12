package by.Ahmed.jdbc.starter.service;

import by.Ahmed.jdbc.starter.dao.FlightDao;
import by.Ahmed.jdbc.starter.dto.FlightDto;

import java.util.List;
import java.util.stream.Collectors;

public class FlightService {
    private static final FlightService INSTANCE = new FlightService();
    private static final FlightDao flightDao = FlightDao.getInstance();

    public FlightService() {
    }

    public static FlightService getInstance() {
        return INSTANCE;
    }

    public List<FlightDto> findAll() {
        return flightDao.findAll().stream().map(
                flight -> new FlightDto(
                        flight.getId(),
                        """
                           %s - %s - %s
                        """.formatted(
                                flight.getDepartureAirportCode(),
                                flight.getArrivalAirportCode(),
                                flight.getStatus()
                        )
                )
        ).collect(Collectors.toList());
    }
}
