package com.gaconnecte.auxilium.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to JHipster.
 * <p>
 * Properties are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final ConnectionString connectionString = new ConnectionString();

    private String cheminFichier;
    private String cheminPdfReport;
    private String rootStoragePath;
    private String rootStorageContextUrl;
    private String logo;
    private String slogon;
    private String statementRoot;
   
    
    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public String getCheminPdfReport() {
        return cheminPdfReport;
    }

    public void setCheminPdfReport(String cheminPdfReport) {
        this.cheminPdfReport = cheminPdfReport;
    }

    public String getRootStoragePath() {
        return rootStoragePath;
    }

    public void setRootStoragePath(String rootStoragePath) {
        this.rootStoragePath = rootStoragePath;
    }

    public String getRootStorageContextUrl() {
        return rootStorageContextUrl;
    }

    public void setRootStorageContextUrl(String rootStorageContextUrl) {
        this.rootStorageContextUrl = rootStorageContextUrl;
    }

    public ConnectionString getConnectionString() {
        return connectionString;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSlogon() {
        return slogon;
    }

    public void setSlogon(String slogon) {
        this.slogon = slogon;
    }

    public String getStatementRoot() {
        return statementRoot;
    }

    public void setStatementRoot(String statementRoot) {
        this.statementRoot = statementRoot;
    }

    public static class ConnectionString {

        private String url;
        private String username;
        private String password;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
