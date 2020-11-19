package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.domain.TracedException;
import com.gaconnecte.auxilium.service.LoggerService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by Dell on 10/18/2017.
 */
@Transactional
@Component
public class LoggerServiceImpl implements LoggerService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long log(Throwable ex, String service) {
        TracedException log = new TracedException();
        log.setName(ex.toString());
        log.setService(service);
        log.setMessage(ex.getMessage());
        log.setStackTrace(ex.getStackTrace() != null ? ex.getStackTrace()[0].toString() : "");
        log.setDated(ZonedDateTime.now(ZoneId.systemDefault()));
        entityManager.persist(log);
        return log.getId();
    }
}
