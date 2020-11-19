package com.gaconnecte.auxilium.service;

/**
 * Created by Dell on 10/18/2017.
 */
public interface ExceptionService {

    Long log(Throwable ex, String service);
}
