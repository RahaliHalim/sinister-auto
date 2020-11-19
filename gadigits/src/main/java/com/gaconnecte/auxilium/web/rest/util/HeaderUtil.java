package com.gaconnecte.auxilium.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
/**
 * Utility class for HTTP headers creation.
 */
public final class HeaderUtil {

    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    private static final String APPLICATION_NAME = "auxiliumApp";

    private HeaderUtil() {
    }

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-auxiliumApp-alert", message);
        headers.add("X-auxiliumApp-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".created", param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".updated", param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".deleted", param);
    }

    public static HttpHeaders createEntityReopenAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".reopened", param);
    }
    public static HttpHeaders createEntityCancelAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".canceled", param);
    }
    public static HttpHeaders createEntityCloseAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".closed", param);
    }

    public static HttpHeaders createEntityBloqueAlert(String entityName, String param, Boolean blocked) {
        String blockedMsg;
        if (blocked) {
            blockedMsg = ".bloqued";
        }
        else {
            blockedMsg = ".debloqued";
        }
        return createAlert(APPLICATION_NAME + "." + entityName + blockedMsg, param);
    }


    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        log.error("Entity processing failed, {}", defaultMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-auxiliumApp-error", "error." + errorKey);
        headers.add("X-auxiliumApp-params", entityName);
        return headers;
    }
}
