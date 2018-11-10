package ua.lviv.iot.DTO;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import ua.lviv.iot.domain.Logger;

public class LoggerDTO extends ResourceSupport {

    Logger logger;

    public LoggerDTO(Logger logger, Link selfLink) {
        this.logger = logger;
        add(selfLink);
    }

    public Logger getLogger() {
        return logger;
    }
}
