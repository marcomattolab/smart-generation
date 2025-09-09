package it.elca.generate;

import java.util.List;

public class ConfigCreateProject {
    // => FIXME CHANGE_ME
    private static final boolean USE_PATHNAME_ENV_B = true; // true=>APPLE  false=>WINDOWS

    private boolean isOracle;
    private boolean isMysql;
    private boolean generateTest;
    private String urlConnection;
    private String username;
    private String password;
    private String owner;
    private String pathname;
    private String packageclass;
    private String app;
    private String driver;
    private String dataBaseName;
    private String tablePartName;
    private List<ProjectEnum> enumerations;
    private List<ProjectEntity> projectEntities;
    private List<ProjectRelation> projectRelations;

    private String srcAopLoggingFolder = "aop.logging";
    private String srcConfigFolder = "config";
    private String srcConfigTimezoneFolder = "config.timezone";
    private String srcConfigAuditFolder = "config.audit";
    private String srcDomainFolder = "domain";
    private String srcDomainEnumerationFolder = "domain.enumeration";
    private String srcRepositoryFolder = "repository";
    private String srcRepositoryTimezoneFolder = "repository.timezone";
    private String srcWorkflowFolder = "workflow";
    private String srcSecurityFolder = "security";
    private String srcSecurityJWTFolder = "security.jwt";
    private String srcServiceFolder = "service";
    private String srcReportUtilFolder = "report.util";
    private String srcServiceDtoFolder = "service.dto";
    private String srcServiceImplFolder = "service.impl";
    private String srcServiceMapperFolder = "service.mapper";
    private String srcServiceUtilFolder = "service.util";
    private String srcWebFolder = "web";
    private String srcWebRestFolder = "web.rest";
    private String srcWebRestErrorsFolder = "web.rest.errors";
    private String srcWebRestUtilFolder = "web.rest.util";
    private String srcWebRestVmFolder = "web.rest.vm";

    private String resi18nFolder = "i18n";
    private String resTemplatesFolder = "templates";
    private String resConfigFolder = "config";
    private String resConfigTksFolder = "config.tls";
    private String resConfigLiquibaseFolder = "config.liquibase";
    private String resConfigLiquibaseChangelogFolder = "config.liquibase.changelog";
    private String resConfigLiquibaseDataFolder = "config.liquibase.data";

    private String projectName;
    private String[] languages;
    private String[] profiles;

    public ConfigCreateProject(ProjectConfig jsonConf) {
        this.setGenerateTest(jsonConf.isGenerateTest());
        this.setApp(jsonConf.getApp());
        this.setDriver(jsonConf.getDriver());
        this.setOwner(jsonConf.getOwner());
        this.setDataBaseName(jsonConf.getDataBaseName());
        this.setPackageclass(jsonConf.getPackageclass());
        this.setPassword(jsonConf.getPassword());
        this.setUsername(jsonConf.getUsername());
        this.setTablePartName(jsonConf.getTablePartName());
        this.setPathname(USE_PATHNAME_ENV_B ? jsonConf.getPathnameEnvB() : jsonConf.getPathname());
        this.setProjectName(jsonConf.getProjectName());
        this.setUrlConnection(jsonConf.getUrlConnection());
        this.setEnumerations(jsonConf.getEnumerations());
        this.setLanguages(jsonConf.getLanguages().toArray(new String[jsonConf.getLanguages().size()]));
        this.setProfiles(jsonConf.getProfiles().toArray(new String[jsonConf.getProfiles().size()]));
        this.setProjectEntities(jsonConf.getEntities());
        this.setProjectRelations(jsonConf.getRelations());
        this.setMysql(jsonConf.getDriver().contains("mysql") ? true : false);
        this.setOracle(jsonConf.getDriver().contains("oracle") ? true : false);
    }

    // Getters and Setters
    public boolean isOracle() {
        return isOracle;
    }

    public void setOracle(boolean oracle) {
        isOracle = oracle;
    }

    public boolean isMysql() {
        return isMysql;
    }

    public void setMysql(boolean mysql) {
        isMysql = mysql;
    }

    public boolean isGenerateTest() {
        return generateTest;
    }

    public void setGenerateTest(boolean generateTest) {
        this.generateTest = generateTest;
    }

    public String getUrlConnection() {
        return urlConnection;
    }

    public void setUrlConnection(String urlConnection) {
        this.urlConnection = urlConnection;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPathname() {
        return pathname;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    public String getPackageclass() {
        return packageclass;
    }

    public void setPackageclass(String packageclass) {
        this.packageclass = packageclass;
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

    public String getTablePartName() {
        return tablePartName;
    }

    public void setTablePartName(String tablePartName) {
        this.tablePartName = tablePartName;
    }

    public List<ProjectEnum> getEnumerations() {
        return enumerations;
    }

    public void setEnumerations(List<ProjectEnum> enumerations) {
        this.enumerations = enumerations;
    }

    public List<ProjectEntity> getProjectEntities() {
        return projectEntities;
    }

    public void setProjectEntities(List<ProjectEntity> projectEntities) {
        this.projectEntities = projectEntities;
    }

    public List<ProjectRelation> getProjectRelations() {
        return projectRelations;
    }

    public void setProjectRelations(List<ProjectRelation> projectRelations) {
        this.projectRelations = projectRelations;
    }

    public String getSrcAopLoggingFolder() {
        return srcAopLoggingFolder;
    }

    public void setSrcAopLoggingFolder(String srcAopLoggingFolder) {
        this.srcAopLoggingFolder = srcAopLoggingFolder;
    }

    public String getSrcConfigFolder() {
        return srcConfigFolder;
    }

    public void setSrcConfigFolder(String srcConfigFolder) {
        this.srcConfigFolder = srcConfigFolder;
    }

    public String getSrcConfigTimezoneFolder() {
        return srcConfigTimezoneFolder;
    }

    public void setSrcConfigTimezoneFolder(String srcConfigTimezoneFolder) {
        this.srcConfigTimezoneFolder = srcConfigTimezoneFolder;
    }

    public String getSrcConfigAuditFolder() {
        return srcConfigAuditFolder;
    }

    public void setSrcConfigAuditFolder(String srcConfigAuditFolder) {
        this.srcConfigAuditFolder = srcConfigAuditFolder;
    }

    public String getSrcDomainFolder() {
        return srcDomainFolder;
    }

    public void setSrcDomainFolder(String srcDomainFolder) {
        this.srcDomainFolder = srcDomainFolder;
    }

    public String getSrcDomainEnumerationFolder() {
        return srcDomainEnumerationFolder;
    }

    public void setSrcDomainEnumerationFolder(String srcDomainEnumerationFolder) {
        this.srcDomainEnumerationFolder = srcDomainEnumerationFolder;
    }

    public String getSrcRepositoryFolder() {
        return srcRepositoryFolder;
    }

    public void setSrcRepositoryFolder(String srcRepositoryFolder) {
        this.srcRepositoryFolder = srcRepositoryFolder;
    }

    public String getSrcRepositoryTimezoneFolder() {
        return srcRepositoryTimezoneFolder;
    }

    public void setSrcRepositoryTimezoneFolder(String srcRepositoryTimezoneFolder) {
        this.srcRepositoryTimezoneFolder = srcRepositoryTimezoneFolder;
    }

    public String getSrcWorkflowFolder() {
        return srcWorkflowFolder;
    }

    public void setSrcWorkflowFolder(String srcWorkflowFolder) {
        this.srcWorkflowFolder = srcWorkflowFolder;
    }

    public String getSrcSecurityFolder() {
        return srcSecurityFolder;
    }

    public void setSrcSecurityFolder(String srcSecurityFolder) {
        this.srcSecurityFolder = srcSecurityFolder;
    }

    public String getSrcSecurityJWTFolder() {
        return srcSecurityJWTFolder;
    }

    public void setSrcSecurityJWTFolder(String srcSecurityJWTFolder) {
        this.srcSecurityJWTFolder = srcSecurityJWTFolder;
    }

    public String getSrcServiceFolder() {
        return srcServiceFolder;
    }

    public void setSrcServiceFolder(String srcServiceFolder) {
        this.srcServiceFolder = srcServiceFolder;
    }

    public String getSrcReportUtilFolder() {
        return srcReportUtilFolder;
    }

    public void setSrcReportUtilFolder(String srcReportUtilFolder) {
        this.srcReportUtilFolder = srcReportUtilFolder;
    }

    public String getSrcServiceDtoFolder() {
        return srcServiceDtoFolder;
    }

    public void setSrcServiceDtoFolder(String srcServiceDtoFolder) {
        this.srcServiceDtoFolder = srcServiceDtoFolder;
    }

    public String getSrcServiceImplFolder() {
        return srcServiceImplFolder;
    }

    public void setSrcServiceImplFolder(String srcServiceImplFolder) {
        this.srcServiceImplFolder = srcServiceImplFolder;
    }

    public String getSrcServiceMapperFolder() {
        return srcServiceMapperFolder;
    }

    public void setSrcServiceMapperFolder(String srcServiceMapperFolder) {
        this.srcServiceMapperFolder = srcServiceMapperFolder;
    }

    public String getSrcServiceUtilFolder() {
        return srcServiceUtilFolder;
    }

    public void setSrcServiceUtilFolder(String srcServiceUtilFolder) {
        this.srcServiceUtilFolder = srcServiceUtilFolder;
    }

    public String getSrcWebFolder() {
        return srcWebFolder;
    }

    public void setSrcWebFolder(String srcWebFolder) {
        this.srcWebFolder = srcWebFolder;
    }

    public String getSrcWebRestFolder() {
        return srcWebRestFolder;
    }

    public void setSrcWebRestFolder(String srcWebRestFolder) {
        this.srcWebRestFolder = srcWebRestFolder;
    }

    public String getSrcWebRestErrorsFolder() {
        return srcWebRestErrorsFolder;
    }

    public void setSrcWebRestErrorsFolder(String srcWebRestErrorsFolder) {
        this.srcWebRestErrorsFolder = srcWebRestErrorsFolder;
    }

    public String getSrcWebRestUtilFolder() {
        return srcWebRestUtilFolder;
    }

    public void setSrcWebRestUtilFolder(String srcWebRestUtilFolder) {
        this.srcWebRestUtilFolder = srcWebRestUtilFolder;
    }

    public String getSrcWebRestVmFolder() {
        return srcWebRestVmFolder;
    }

    public void setSrcWebRestVmFolder(String srcWebRestVmFolder) {
        this.srcWebRestVmFolder = srcWebRestVmFolder;
    }

    public String getResi18nFolder() {
        return resi18nFolder;
    }

    public void setResi18nFolder(String resi18nFolder) {
        this.resi18nFolder = resi18nFolder;
    }

    public String getResTemplatesFolder() {
        return resTemplatesFolder;
    }

    public void setResTemplatesFolder(String resTemplatesFolder) {
        this.resTemplatesFolder = resTemplatesFolder;
    }

    public String getResConfigFolder() {
        return resConfigFolder;
    }

    public void setResConfigFolder(String resConfigFolder) {
        this.resConfigFolder = resConfigFolder;
    }

    public String getResConfigTksFolder() {
        return resConfigTksFolder;
    }

    public void setResConfigTksFolder(String resConfigTksFolder) {
        this.resConfigTksFolder = resConfigTksFolder;
    }

    public String getResConfigLiquibaseFolder() {
        return resConfigLiquibaseFolder;
    }

    public void setResConfigLiquibaseFolder(String resConfigLiquibaseFolder) {
        this.resConfigLiquibaseFolder = resConfigLiquibaseFolder;
    }

    public String getResConfigLiquibaseChangelogFolder() {
        return resConfigLiquibaseChangelogFolder;
    }

    public void setResConfigLiquibaseChangelogFolder(String resConfigLiquibaseChangelogFolder) {
        this.resConfigLiquibaseChangelogFolder = resConfigLiquibaseChangelogFolder;
    }

    public String getResConfigLiquibaseDataFolder() {
        return resConfigLiquibaseDataFolder;
    }

    public void setResConfigLiquibaseDataFolder(String resConfigLiquibaseDataFolder) {
        this.resConfigLiquibaseDataFolder = resConfigLiquibaseDataFolder;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String[] getProfiles() {
        return profiles;
    }

    public void setProfiles(String[] profiles) {
        this.profiles = profiles;
    }
}
