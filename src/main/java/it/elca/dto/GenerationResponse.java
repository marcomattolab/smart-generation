package it.elca.dto;

import it.elca.generate.ProjectConfig;

public class GenerationResponse {

    private String status;
    private String message;
    private String path;
    private ProjectConfig projectDetails;

    public GenerationResponse(String status, String message, String path, ProjectConfig projectDetails) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.projectDetails = projectDetails;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ProjectConfig getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(ProjectConfig projectDetails) {
        this.projectDetails = projectDetails;
    }
}
