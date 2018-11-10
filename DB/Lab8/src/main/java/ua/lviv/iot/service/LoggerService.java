package ua.lviv.iot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.domain.Logger;
import ua.lviv.iot.repository.LoggerRepository;

import java.util.List;

@Service
public class LoggerService {

    @Autowired
    LoggerRepository loggerRepository;

    public List<Logger> getAllLogs() { return loggerRepository.findAll(); }
}
