package com.gaconnecte.auxilium.service;

/**
 * Created by Dell on 10/18/2017.
 */
public interface LoggerService {

    Long log(Throwable ex, String service);
}
