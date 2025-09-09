package it.elca.generate;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectConfig {

    @JsonProperty("generateTest")
    private boolean generateTest;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("tablePartName")
    private String tablePartName;

    @JsonProperty("app")
    private String app;

    @JsonProperty("driver")
    private String driver;

    @JsonProperty("dataBaseName")
    private String dataBaseName;

    @JsonProperty("packageclass")
    private String packageclass;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("pathname")
    private String pathname;

    @JsonProperty("pathnameEnvB")
    private String pathnameEnvB;

    @JsonProperty("projectName")
    private String projectName;

    @JsonProperty("urlConnection")
    private String urlConnection;

    @JsonProperty("languages")
    private List<String> languages;

    @JsonProperty("profiles")
    private List<String> profiles;

    @JsonProperty("enumerations")
    private List<ProjectEnum> enumerations;

    @JsonProperty("entities")
    private List<ProjectEntity> entities;

    @JsonProperty("relations")
    private List<ProjectRelation> relations;

    // Getters and Setters
    public boolean isGenerateTest() {
        return generateTest;
    }

    public void setGenerateTest(boolean generateTest) {
        this.generateTest = generateTest;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTablePartName() {
        return tablePartName;
    }

    public void setTablePartName(String tablePartName) {
        this.tablePartName = tablePartName;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getPackageclass() {
        return packageclass;
    }

    public void setPackageclass(String packageclass) {
        this.packageclass = packageclass;
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

    public String getPathname() {
        return pathname;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    public String getPathnameEnvB() {
        return pathnameEnvB;
    }

    public void setPathnameEnvB(String pathnameEnvB) {
        this.pathnameEnvB = pathnameEnvB;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUrlConnection() {
        return urlConnection;
    }

    public void setUrlConnection(String urlConnection) {
        this.urlConnection = urlConnection;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<String> profiles) {
        this.profiles = profiles;
    }

    public List<ProjectEnum> getEnumerations() {
        return enumerations;
    }

    public void setEnumerations(List<ProjectEnum> enumerations) {
        this.enumerations = enumerations;
    }

    public List<ProjectEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<ProjectEntity> entities) {
        this.entities = entities;
    }

    public List<ProjectRelation> getRelations() {
        return relations;
    }

    public void setRelations(List<ProjectRelation> relations) {
        this.relations = relations;
    }
}
