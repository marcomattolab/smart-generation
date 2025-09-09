package it.elca.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import it.elca.generate.Column;
import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Enumeration;
import it.elca.generate.Field;
import it.elca.generate.ProjectConfig;
import it.elca.generate.ProjectEntity;
import it.elca.generate.ProjectEnum;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.project.ApplicationApp;
import it.elca.generate.project.TemplateAngular;
import it.elca.generate.project.TemplateApplicationWebXml;
import it.elca.generate.project.TemplateClassPath;
import it.elca.generate.project.TemplateFactoryPath;
import it.elca.generate.project.TemplateLoggingAspect;
import it.elca.generate.project.TemplatePackage;
import it.elca.generate.project.TemplatePackageLock;
import it.elca.generate.project.TemplatePom;
import it.elca.generate.template.conf.TemplateApplicationProperties;
import it.elca.generate.template.conf.TemplateAsyncConfiguration;
import it.elca.generate.template.conf.TemplateCacheConfiguration;
import it.elca.generate.template.conf.TemplateCloudDatabaseConfiguration;
import it.elca.generate.template.conf.TemplateCostants;
import it.elca.generate.template.conf.TemplateDatabaseConfiguration;
import it.elca.generate.template.conf.TemplateDateTimeFormatConfiguration;
import it.elca.generate.template.conf.TemplateDefaultProfileUtil;
import it.elca.generate.template.conf.TemplateJacksonConfiguration;
import it.elca.generate.template.conf.TemplateLiquidbaseConfiguration;
import it.elca.generate.template.conf.TemplateLocaleConfiguration;
import it.elca.generate.template.conf.TemplateLoggingAspectConfiguration;
import it.elca.generate.template.conf.TemplateLoggingConfiguration;
import it.elca.generate.template.conf.TemplateMetricsConfiguration;
import it.elca.generate.template.conf.TemplateSecurityConfiguration;
import it.elca.generate.template.conf.TemplateWebConfigurer;
import it.elca.generate.template.conf.audit.TemplateAsyncEntityAuditEventWriter;
import it.elca.generate.template.conf.audit.TemplateAuditEventConverter;
import it.elca.generate.template.conf.audit.TemplateEntityAuditAction;
import it.elca.generate.template.conf.audit.TemplateEntityAuditEventConfig;
import it.elca.generate.template.conf.audit.TemplateEntityAuditEventListener;
import it.elca.generate.template.domain.TemplateAbstractAuditingEntity;
import it.elca.generate.template.domain.TemplateAuthority;
import it.elca.generate.template.domain.TemplateDomain;
import it.elca.generate.template.domain.TemplateDomainEnumeration;
import it.elca.generate.template.domain.TemplateEntityAuditEvent;
import it.elca.generate.template.domain.TemplatePersistentAudit;
import it.elca.generate.template.domain.TemplateUser;
import it.elca.generate.template.fe.TemplateAppMain;
import it.elca.generate.template.fe.TemplateAppModule;
import it.elca.generate.template.fe.TemplateAppRoutingModule;
import it.elca.generate.template.fe.TemplateIndex;
import it.elca.generate.template.fe.TemplateManifest;
import it.elca.generate.template.fe.account.TemplateAccountModule;
import it.elca.generate.template.fe.account.TemplateAccountSettingsModule;
import it.elca.generate.template.fe.admin.TemplateAdminEntityAuditModule;
import it.elca.generate.template.fe.admin.TemplateAdminModule;
import it.elca.generate.template.fe.admin.TemplateConfigurationService;
import it.elca.generate.template.fe.blocks.TemplateErrorehandlerInterceptor;
import it.elca.generate.template.fe.core.TemplateCoreModule;
import it.elca.generate.template.fe.core.TemplateLanguageHelper;
import it.elca.generate.template.fe.dashboard.TemplateDashboardBarchartModule;
import it.elca.generate.template.fe.dashboard.TemplateDashboardBarchartRoute;
import it.elca.generate.template.fe.dashboard.TemplateDashboardDoughnutchartModule;
import it.elca.generate.template.fe.dashboard.TemplateDashboardDoughnutchartRoute;
import it.elca.generate.template.fe.dashboard.TemplateDashboardLinechartModule;
import it.elca.generate.template.fe.dashboard.TemplateDashboardLinechartRoute;
import it.elca.generate.template.fe.dashboard.TemplateDashboardModule;
import it.elca.generate.template.fe.dashboard.TemplateDashboardPiechartModule;
import it.elca.generate.template.fe.dashboard.TemplateDashboardPiechartRoute;
import it.elca.generate.template.fe.dashboard.TemplateDashboardPolarareachartModule;
import it.elca.generate.template.fe.dashboard.TemplateDashboardPolarareachartRoute;
import it.elca.generate.template.fe.dashboard.TemplateDashboardRadarchartModule;
import it.elca.generate.template.fe.dashboard.TemplateDashboardRadarchartRoute;
import it.elca.generate.template.fe.entities.TemplateEntityComponentHtml;
import it.elca.generate.template.fe.entities.TemplateEntityComponentTs;
import it.elca.generate.template.fe.entities.TemplateEntityDeleteComponentHtml;
import it.elca.generate.template.fe.entities.TemplateEntityDeleteComponentTs;
import it.elca.generate.template.fe.entities.TemplateEntityDetailComponentHtml;
import it.elca.generate.template.fe.entities.TemplateEntityDetailComponentTs;
import it.elca.generate.template.fe.entities.TemplateEntityIndex;
import it.elca.generate.template.fe.entities.TemplateEntityModule;
import it.elca.generate.template.fe.entities.TemplateEntityRoute;
import it.elca.generate.template.fe.entities.TemplateEntityService;
import it.elca.generate.template.fe.entities.TemplateEntityUpdateComponentHtml;
import it.elca.generate.template.fe.entities.TemplateEntityUpdateComponentTs;
import it.elca.generate.template.fe.entities.TemplateModule;
import it.elca.generate.template.fe.home.TemplateHomeComponentHtml;
import it.elca.generate.template.fe.home.TemplateHomeModule;
import it.elca.generate.template.fe.i18n.TemplateEntityI18N;
import it.elca.generate.template.fe.i18n.TemplateEnumerationsI18N;
import it.elca.generate.template.fe.i18n.TemplateGlobalI18N;
import it.elca.generate.template.fe.layouts.TemplateLeftmenuComponent;
import it.elca.generate.template.fe.layouts.TemplateMainComponent;
import it.elca.generate.template.fe.layouts.TemplateNavbarComponent;
import it.elca.generate.template.fe.shared.TemplateAlertErrorComponent;
import it.elca.generate.template.fe.shared.TemplateEntitySharedModel;
import it.elca.generate.template.fe.shared.TemplateSharedCommonModule;
import it.elca.generate.template.fe.shared.TemplateSharedLibsModule;
import it.elca.generate.template.fe.shared.TemplateSharedModule;
import it.elca.generate.template.report.TemplateReportUtils;
import it.elca.generate.template.repository.TemplateAuthorityRepository;
import it.elca.generate.template.repository.TemplateCustomAuditEventRepository;
import it.elca.generate.template.repository.TemplateEntityAuditEventRepository;
import it.elca.generate.template.repository.TemplatePersistenceAuditEventRepository;
import it.elca.generate.template.repository.TemplateRepository;
import it.elca.generate.template.repository.TemplateUserRepository;
import it.elca.generate.template.resouces.TemplateApplication;
import it.elca.generate.template.resouces.TemplateApplicationDev;
import it.elca.generate.template.resouces.TemplateApplicationProd;
import it.elca.generate.template.resouces.TemplateBanner;
import it.elca.generate.template.resouces.TemplateI18N;
import it.elca.generate.template.resouces.TemplateLiquidbaseAuthoritiesCSV;
import it.elca.generate.template.resouces.TemplateLiquidbaseChangelog;
import it.elca.generate.template.resouces.TemplateLiquidbaseChangelogConstraint;
import it.elca.generate.template.resouces.TemplateLiquidbaseData;
import it.elca.generate.template.resouces.TemplateLiquidbaseEntityAudit;
import it.elca.generate.template.resouces.TemplateLiquidbaseMaster;
import it.elca.generate.template.resouces.TemplateLiquidbaseMasterInitialSchema;
import it.elca.generate.template.resouces.TemplateLiquidbaseUsersAuthoritiesCSV;
import it.elca.generate.template.resouces.TemplateLiquidbaseUsersCSV;
import it.elca.generate.template.resouces.TemplateMessage;
import it.elca.generate.template.security.TemplateAuthoritiesConstants;
import it.elca.generate.template.security.TemplateDomainUserDetailsService;
import it.elca.generate.template.security.TemplateSecurityJWTConfigurer;
import it.elca.generate.template.security.TemplateSecurityJWTFilter;
import it.elca.generate.template.security.TemplateSecurityTokenProvider;
import it.elca.generate.template.security.TemplateSecurityUtils;
import it.elca.generate.template.security.TemplateSpringSecurityAuditorAware;
import it.elca.generate.template.security.TemplateUserNotActivatedException;
import it.elca.generate.template.service.TemplateAbstractAuditingDTO;
import it.elca.generate.template.service.TemplateAuditEventService;
import it.elca.generate.template.service.TemplateEntityMapperService;
import it.elca.generate.template.service.TemplateMailService;
import it.elca.generate.template.service.TemplateMapperService;
import it.elca.generate.template.service.TemplatePasswordChangeDTO;
import it.elca.generate.template.service.TemplateQueryService;
import it.elca.generate.template.service.TemplateRandomUtil;
import it.elca.generate.template.service.TemplateService;
import it.elca.generate.template.service.TemplateServiceCriteria;
import it.elca.generate.template.service.TemplateServiceDTO;
import it.elca.generate.template.service.TemplateServiceImpl;
import it.elca.generate.template.service.TemplateUserDTO;
import it.elca.generate.template.service.TemplateUserMapperService;
import it.elca.generate.template.service.TemplateUserService;
import it.elca.generate.template.test.TemplateAccountResourceIntTest;
import it.elca.generate.template.test.TemplateApplicationYamlTest;
import it.elca.generate.template.test.TemplateAuditResourceIntTest;
import it.elca.generate.template.test.TemplateCustomAuditEventRepositoryIntTest;
import it.elca.generate.template.test.TemplateDateTimeWrapper;
import it.elca.generate.template.test.TemplateDateTimeWrapperRepository;
import it.elca.generate.template.test.TemplateDomainUserDetailsServiceIntTest;
import it.elca.generate.template.test.TemplateExceptionTranslatorIntTest;
import it.elca.generate.template.test.TemplateExceptionTranslatorTestController;
import it.elca.generate.template.test.TemplateHibernateTimeZoneTest;
import it.elca.generate.template.test.TemplateLogsResourceIntTest;
import it.elca.generate.template.test.TemplateMailServiceIntTest;
import it.elca.generate.template.test.TemplatePaginationUtilUnitTest;
import it.elca.generate.template.test.TemplateSecurityJWTFilterTest;
import it.elca.generate.template.test.TemplateSecurityTokenProviderTest;
import it.elca.generate.template.test.TemplateSecurityUtilsUnitTest;
import it.elca.generate.template.test.TemplateTestUtil;
import it.elca.generate.template.test.TemplateTestWorkflow;
import it.elca.generate.template.test.TemplateUserResourceIntTest;
import it.elca.generate.template.test.TemplateUserServiceIntTest;
import it.elca.generate.template.test.TemplateWebConfigurerTest;
import it.elca.generate.template.test.TemplateWebConfigurerTestController;
import it.elca.generate.template.util.TemplateCopyAll;
import it.elca.generate.template.util.TemplateDeleteTest;
import it.elca.generate.template.web.TemplateAccountResource;
import it.elca.generate.template.web.TemplateAuditResource;
import it.elca.generate.template.web.TemplateEntityAuditResource;
import it.elca.generate.template.web.TemplateLogsResource;
import it.elca.generate.template.web.TemplateReportBase;
import it.elca.generate.template.web.TemplateResource;
import it.elca.generate.template.web.TemplateUserJWTController;
import it.elca.generate.template.web.TemplateUserResource;
import it.elca.generate.template.web.errors.TemplateBadRequestAlertException;
import it.elca.generate.template.web.errors.TemplateCustomParameterizedException;
import it.elca.generate.template.web.errors.TemplateEmailAlreadyUsedException;
import it.elca.generate.template.web.errors.TemplateEmailNotFoundException;
import it.elca.generate.template.web.errors.TemplateErrorConstants;
import it.elca.generate.template.web.errors.TemplateExceptionTranslator;
import it.elca.generate.template.web.errors.TemplateFieldErrorVM;
import it.elca.generate.template.web.errors.TemplateInternalServerErrorException;
import it.elca.generate.template.web.errors.TemplateInvalidPasswordException;
import it.elca.generate.template.web.errors.TemplateLoginAlreadyUsedException;
import it.elca.generate.template.web.util.TemplateHeaderUtil;
import it.elca.generate.template.web.util.TemplatePaginationUtil;
import it.elca.generate.template.web.vm.TemplateKeyAndPasswordVM;
import it.elca.generate.template.web.vm.TemplateLoggerVM;
import it.elca.generate.template.web.vm.TemplateLoginVM;
import it.elca.generate.template.web.vm.TemplateManagedUserVM;
import it.elca.generate.template.workflow.TemplateWorkflow;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class GenerationService {

    public void generate(ProjectConfig projectConfig) throws SQLException, ClassNotFoundException {
        ConfigCreateProject config = new ConfigCreateProject(projectConfig);
        DataBase db = new DataBase();
        init(db, config);
        generateFile(db, config);
    }

    private void init(DataBase db, ConfigCreateProject ccp) throws ClassNotFoundException, SQLException {
        // Generate from Project Entities JSON
        List<ProjectEntity> prjEntities = ccp.getProjectEntities();

        if (!CollectionUtils.isEmpty(prjEntities)) {
            for (ProjectEntity entity : prjEntities) {
                Table table = new Table();
                String tableName = entity.getName();
                System.out.println("\n## TableName: " + tableName);

                table.setNomeTabellaCompleto(tableName);
                table.setNomeTabella(tableName);
                table.setProfiles(entity.getProfiles());
                db.addTable(tableName, table);

                int sortColumn = 0;
                for (Field field : entity.getFields()) {
                    String columnName = field.getFname();
                    String mTypeColumn = field.getFtype();
                    boolean isRequired = field.isFrequired();
                    Integer columnSize = field.getFsize() != null ? field.getFsize() : null;

                    //MinLenght, MaxLenght, Pattern
                    Integer columnMinSize = field.getFminlength() != null ? field.getFminlength() : null;
                    Integer columnMaxSize = field.getFmaxlength() != null ? field.getFmaxlength() : null;
                    String pattern = field.getFpattern();

                    Column column = new Column();
                    column.setName(columnName);
                    column.setSortColumn(sortColumn++);

                    int iTypeColmn = Column.corvertModelType(mTypeColumn);
                    column.setTypeColumn(iTypeColmn);

                    if (!isRequired) {
                        column.setNullable();
                    }
                    if (columnSize != null) {
                        column.setColumnSize(columnSize);
                    }
                    if (columnMinSize != null) {
                        column.setColumnMinSize(columnMinSize);
                    }
                    if (columnMaxSize != null) {
                        column.setColumnMaxSize(columnMaxSize);
                    }
                    if (pattern != null) {
                        column.setPattern(pattern);
                    }

                    table.addColumn(column);

                    System.out.println("  - Column: " + columnName +
                            "  => mType:" + mTypeColumn +
                            "   iType:" + iTypeColmn +
                            "   Sort:" + sortColumn +
                            "   Type:" + column.getTypeColumn() +
                            "   Size:" + columnSize +
                            "   Minsize:" + columnMinSize +
                            "   Maxsize:" + columnMaxSize +
                            "   Pattern:" + pattern +
                            "   Required:" + isRequired);

                    //Set Primary KEY
                    String key = "id";
                    if (table.getColumn(key) != null) {
                        table.getColumn(key).setKey();
                    }
                }
            }
        }
        //Build Enumerations
        buildEnumerations(db, ccp);
    }

    private void buildEnumerations(DataBase db, ConfigCreateProject ccp) {
        //Generate Enumerations
        List<ProjectEnum> enums = ccp.getEnumerations();
        System.out.println("\n# Enumeration ");
        for (ProjectEnum projectEnum : enums) {
            String[] values = projectEnum.getValues().split("#");
            System.out.println("    - Define Enumeration: " + projectEnum.getName() + "  -  Values: " + Arrays.asList(values) + "");
            db.addEnumeration(projectEnum.getName(), Arrays.asList(values));
        }
    }

    private void generateFile(DataBase db, ConfigCreateProject config) {
		System.out.println("-------------------------------------------------------");
		System.out.println("Generating Project and project Files for BE and FE ...");

		try {
			//Build Enumerations for Application
			buildEnumerationsNameInColumn(db);

			//Project (statics)
			new TemplateClassPath(db).generateTemplate();
			new TemplateFactoryPath(db).generateTemplate();
			new TemplatePom(db).generateTemplate();
			new TemplateAngular(db).generateTemplate();
			new TemplatePackageLock(db).generateTemplate();
			new TemplatePackage(db).generateTemplate();
			new TemplateLoggingAspect(db).generateTemplate();
			new TemplateApplicationWebXml(db).generateTemplate();
			new ApplicationApp(db).generateTemplate();

			//Report Utils (statics)
			new TemplateReportUtils(db).generateTemplate();

			//Configuration (statics)
			new TemplateApplicationProperties(db).generateTemplate();
			new TemplateAsyncConfiguration(db).generateTemplate();
			new TemplateCacheConfiguration(db).generateTemplate();
			new TemplateCloudDatabaseConfiguration(db).generateTemplate();
			new TemplateCostants(db).generateTemplate();
			new TemplateDatabaseConfiguration(db).generateTemplate();
			new TemplateDateTimeFormatConfiguration(db).generateTemplate();
			new TemplateDefaultProfileUtil(db).generateTemplate();
			new TemplateJacksonConfiguration(db).generateTemplate();
			new TemplateLiquidbaseConfiguration(db).generateTemplate();
			new TemplateLocaleConfiguration(db).generateTemplate();
			new TemplateLoggingAspectConfiguration(db).generateTemplate();
			new TemplateLoggingConfiguration(db).generateTemplate();
			new TemplateMetricsConfiguration(db).generateTemplate();
			new TemplateSecurityConfiguration(db).generateTemplate();
			new TemplateWebConfigurer(db).generateTemplate();

			//Audit Config
			new TemplateAuditEventConverter(db).generateTemplate();
			new TemplateAsyncEntityAuditEventWriter(db).generateTemplate();
			new TemplateEntityAuditEventListener(db).generateTemplate();
			new TemplateEntityAuditAction(db).generateTemplate();
			new TemplateEntityAuditEventConfig(db).generateTemplate();

			//Domain (statics)
			new TemplateEntityAuditEvent(db).generateTemplate();
			new TemplateAbstractAuditingEntity(db).generateTemplate();
			new TemplatePersistentAudit(db).generateTemplate();
			new TemplateAuthority(db).generateTemplate();
			new TemplateUser(db).generateTemplate();

			//Repository (statics)
			new TemplateEntityAuditEventRepository(db).generateTemplate();
			new TemplateAuthorityRepository(db).generateTemplate();
			new TemplateCustomAuditEventRepository(db).generateTemplate();
			new TemplatePersistenceAuditEventRepository(db).generateTemplate();
			new TemplateUserRepository(db).generateTemplate();

			//RETRIEVE BY JDL/DB/PROPERTY DYNAMIC
			List<Enumeration> enumerations = buildEnumerationsMap(db);
			for (Enumeration cEnum : enumerations) {
				new TemplateDomainEnumeration(cEnum).generateTemplate();
			}

			//Workflow (statics)
			new TemplateWorkflow(db).generateTemplate();

			//Security (statics)
			new TemplateAuthoritiesConstants(db).generateTemplate();
			new TemplateDomainUserDetailsService(db).generateTemplate();
			new TemplateSecurityUtils(db).generateTemplate();
			new TemplateSpringSecurityAuditorAware(db).generateTemplate();
			new TemplateUserNotActivatedException(db).generateTemplate();
			//Add Security JWT
			new TemplateSecurityJWTConfigurer(db).generateTemplate();
			new TemplateSecurityJWTFilter(db).generateTemplate();
			new TemplateSecurityTokenProvider(db).generateTemplate();

			//Service (statics)
			new TemplateRandomUtil(db).generateTemplate();
			new TemplateMailService(db).generateTemplate();
			new TemplateUserService(db).generateTemplate();
			new TemplateAuditEventService(db).generateTemplate();
			new TemplateUserMapperService(db).generateTemplate();
			new TemplateEntityMapperService(db).generateTemplate();

			//DTO (statics)
			new TemplateUserDTO(db).generateTemplate();
			new TemplatePasswordChangeDTO(db).generateTemplate();
			new TemplateAbstractAuditingDTO(db).generateTemplate();

			//WEB.REST (statics)
			new TemplateEntityAuditResource(db).generateTemplate();
			new TemplateAccountResource(db).generateTemplate();
			new TemplateAuditResource(db).generateTemplate();
			new TemplateUserResource(db).generateTemplate();
			new TemplateUserJWTController(db).generateTemplate();
			new TemplateLogsResource(db).generateTemplate();
			new TemplateReportBase(db).generateTemplate();	//Dynamic Jasper

			//WEB.REST.UTILS (statics)
			new TemplateHeaderUtil(db).generateTemplate();
			new TemplatePaginationUtil(db).generateTemplate();

			//WEB.REST.VM (statics)
			new TemplateKeyAndPasswordVM(db).generateTemplate();
			new TemplateLoginVM(db).generateTemplate();
			new TemplateLoggerVM(db).generateTemplate();
			new TemplateManagedUserVM(db).generateTemplate();

			//WEB.REST.ERRORS (statics)
			new TemplateBadRequestAlertException(db).generateTemplate();
			new TemplateCustomParameterizedException(db).generateTemplate();
			new TemplateEmailAlreadyUsedException(db).generateTemplate();
			new TemplateEmailNotFoundException(db).generateTemplate();
			new TemplateErrorConstants(db).generateTemplate();
			new TemplateExceptionTranslator(db).generateTemplate();
			new TemplateFieldErrorVM(db).generateTemplate();
			new TemplateInternalServerErrorException(db).generateTemplate();
			new TemplateInvalidPasswordException(db).generateTemplate();
			new TemplateLoginAlreadyUsedException(db).generateTemplate();

			//RESOURCES START (statics)
			new TemplateBanner(db).generateTemplate();
			new TemplateApplication(db).generateTemplate();
			new TemplateApplicationProd(db).generateTemplate();
			new TemplateApplicationDev(db).generateTemplate();
			new TemplateLiquidbaseEntityAudit(db).generateTemplate();
			new TemplateLiquidbaseMasterInitialSchema(db).generateTemplate();
			new TemplateLiquidbaseMaster(db).generateTemplate();
			new TemplateMessage(db).generateTemplate();

			//Resource Liquibase Data CSV
			new TemplateLiquidbaseAuthoritiesCSV(db).generateTemplate();
			new TemplateLiquidbaseUsersCSV(db).generateTemplate();
			new TemplateLiquidbaseUsersAuthoritiesCSV(db).generateTemplate();


			//Copy All Template
			new TemplateCopyAll(db).generateTemplate();


			//Frontend
			new TemplateManifest(db).generateTemplate();
			new TemplateIndex(db).generateTemplate();
			new TemplateAppModule(db).generateTemplate();
			new TemplateAppMain(db).generateTemplate();
			new TemplateAppRoutingModule(db).generateTemplate();
			new TemplateAccountModule(db).generateTemplate();
			new TemplateAdminModule(db).generateTemplate();
			new TemplateAdminEntityAuditModule(db).generateTemplate(); 		//Audit Module TS
			new TemplateDashboardModule(db).generateTemplate();  				//Chart Dashboard
			new TemplateDashboardBarchartModule(db).generateTemplate();  		//Barchart Dashboard
			new TemplateDashboardBarchartRoute(db).generateTemplate();  		//Barchart Route
			new TemplateDashboardDoughnutchartModule(db).generateTemplate();   //Doughnutchart Dashboard
			new TemplateDashboardDoughnutchartRoute(db).generateTemplate();    //Doughnutchart Route
			new TemplateDashboardLinechartModule(db).generateTemplate();  	    //Linechart Dashboard
			new TemplateDashboardLinechartRoute(db).generateTemplate();  	    //Linechart Route
			new TemplateDashboardPiechartModule(db).generateTemplate();  		//Piechart Dashboard
			new TemplateDashboardPiechartRoute(db).generateTemplate();  		//Piechart Route
			new TemplateDashboardPolarareachartModule(db).generateTemplate();  //Polarareachart Dashboard
			new TemplateDashboardPolarareachartRoute(db).generateTemplate();   //Polarareachart Route
			new TemplateDashboardRadarchartModule(db).generateTemplate();  	//Radarchart Dashboard
			new TemplateDashboardRadarchartRoute(db).generateTemplate();  		//Radarchart Route
			new TemplateConfigurationService(db).generateTemplate();
			new TemplateErrorehandlerInterceptor(db).generateTemplate();
			new TemplateCoreModule(db).generateTemplate();
			new TemplateLanguageHelper(db).generateTemplate();
			new TemplateHomeModule(db).generateTemplate();
			new TemplateHomeComponentHtml(db).generateTemplate();
			new TemplateNavbarComponent(db).generateTemplate(); 				//Cicle Entities Done
			new TemplateLeftmenuComponent(db).generateTemplate(); 				//Cicle Entities Done
			new TemplateMainComponent(db).generateTemplate();
			new TemplateSharedModule(db).generateTemplate();
			new TemplateSharedLibsModule(db).generateTemplate();
			new TemplateSharedCommonModule(db).generateTemplate();
			new TemplateAlertErrorComponent(db).generateTemplate();
			new TemplateModule(db).generateTemplate(); 						//Cicle Entities Done
			new TemplateAccountSettingsModule(db).generateTemplate();


			//TEST BE Classes
			if (config.isGenerateTest()) {
				new TemplateApplicationYamlTest(db).generateTemplate();
				new TemplateWebConfigurerTest(db).generateTemplate();
				new TemplateWebConfigurerTestController(db).generateTemplate();
				new TemplateHibernateTimeZoneTest(db).generateTemplate();
				new TemplateCustomAuditEventRepositoryIntTest(db).generateTemplate();
				new TemplateDateTimeWrapperRepository(db).generateTemplate();
				new TemplateDateTimeWrapper(db).generateTemplate();
				new TemplateSecurityJWTFilterTest(db).generateTemplate();
				new TemplateSecurityTokenProviderTest(db).generateTemplate();
				new TemplateSecurityUtilsUnitTest(db).generateTemplate();
				new TemplateDomainUserDetailsServiceIntTest(db).generateTemplate();
				new TemplateMailServiceIntTest(db).generateTemplate();
				new TemplateUserServiceIntTest(db).generateTemplate();
				new TemplatePaginationUtilUnitTest(db).generateTemplate();
				new TemplateExceptionTranslatorIntTest(db).generateTemplate();
				new TemplateExceptionTranslatorTestController(db).generateTemplate();
				new TemplateTestUtil(db).generateTemplate();
				new TemplateAccountResourceIntTest(db).generateTemplate();
				new TemplateAuditResourceIntTest(db).generateTemplate();
				new TemplateLogsResourceIntTest(db).generateTemplate();
				new TemplateUserResourceIntTest(db).generateTemplate();
				new TemplateTestWorkflow(db).generateTemplate();
			}

			//Building Data of All Enumerations
			List<Enumeration> enumList = new ArrayList<>();
			Map<String, List<String>> map = db.getEnumeration();
			for(String enumName: map.keySet()) {
				enumList.add( new Enumeration(enumName, map.get(enumName)) );
			}


			//All Other Level (dynamics)
			System.out.println("Creating table dynamics in progress... ");
			for (Table tabella : Utils.getTables(db)) {
				System.out.println("\n# Generating table : "+tabella.getNomeTabella());
				new TemplateDomain(tabella).generateTemplate();
				new TemplateRepository(tabella).generateTemplate();
				new TemplateService(tabella).generateTemplate();
				new TemplateServiceImpl(tabella).generateTemplate();
				new TemplateQueryService(tabella).generateTemplate();
				new TemplateMapperService(tabella).generateTemplate();
				new TemplateServiceDTO(tabella).generateTemplate();
				new TemplateServiceCriteria(db, tabella).generateTemplate(); 				//Added enumeration management
				new TemplateResource(tabella).generateTemplate();
				new TemplateLiquidbaseData(db, tabella).generateTemplate(); 				//DATA CSV
				new TemplateLiquidbaseChangelog(tabella).generateTemplate(); 	 			//Review/COMPLETE THIS  !!
				if (Utils.havingConstraints(config, tabella)) {
					new TemplateLiquidbaseChangelogConstraint(tabella).generateTemplate();	//Review/COMPLETE THIS  !!
				}

				//MultiLanguages
				for(String languageCode: config.getLanguages()) {
					new TemplateEntityI18N(tabella, languageCode).generateTemplate();
				}

				new TemplateEntityIndex(tabella).generateTemplate();
				new TemplateEntityService(tabella).generateTemplate();  					//DONE MANAGE DATES
				new TemplateEntityRoute(tabella).generateTemplate();
				new TemplateEntityModule(tabella).generateTemplate();
				new TemplateEntityComponentTs(db, tabella).generateTemplate();
				new TemplateEntityComponentHtml(db, tabella).generateTemplate();
				new TemplateEntityUpdateComponentTs(tabella).generateTemplate();
				new TemplateEntityUpdateComponentHtml(db, tabella).generateTemplate(); 	//DONE DEVELOP ENUMS AND DATES
				new TemplateEntityDetailComponentTs(tabella).generateTemplate();
				new TemplateEntityDetailComponentHtml(tabella).generateTemplate();
				new TemplateEntityDeleteComponentTs(tabella).generateTemplate();
				new TemplateEntityDeleteComponentHtml(tabella).generateTemplate();
				new TemplateEntitySharedModel(db, tabella).generateTemplate(); 			//DONE COMPLETE ENUM

			}

			//MultiLanguages
			for(String languageCode: config.getLanguages()) {
				new TemplateGlobalI18N(db, languageCode).generateTemplate();
				new TemplateI18N(db, languageCode).generateTemplate();
				//Enumeration I18N
				for(Enumeration e : enumList) {
					new TemplateEnumerationsI18N(e, languageCode).generateTemplate();
				}
			}

			//DELETE TEST FOLDER
			if ( !config.isGenerateTest() ) {
				new TemplateDeleteTest(db).generateTemplate();
			}

			System.out.println("\nGenerating Project Files Succesfully Completed. Try It!");
			System.out.println("--------------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}

    private List<Enumeration> buildEnumerationsMap(DataBase db) {
        System.out.println("Build enumerations map... ");
        List<Enumeration> enumerations = new ArrayList<>();
        for (String enumName : db.enumeration.keySet()) {
            List<String> enumValues = db.getEnumeration().get(enumName);
            enumerations.add(new Enumeration(enumName, enumValues));
        }
        return enumerations;
    }

    private DataBase buildEnumerationsNameInColumn(DataBase db) {
        DataBase dataBase = db;
        Set<?> set = dataBase.getTableName();
        for (Iterator<?> iter = set.iterator(); iter.hasNext(); ) {
            String tabellaName = (String) iter.next();
            Table tabella = dataBase.getTables(tabellaName);

            Set<?> cset = tabella.getColumnNames();
            for (Iterator<?> citer = cset.iterator(); citer.hasNext(); ) {
                String columnName = (String) citer.next();
                Column column = tabella.getColumn(columnName);
                String enumeration = findEnumerationName(tabellaName, columnName);
                if (enumeration != null && enumeration.length() > 0) {
                    if (Utils.LOG_DEBUG_GENERATOR) {
                        System.out.println("@ Added Enum tabellaName.columnName.enum ===> " + tabellaName + "." + columnName + "." + enumeration);
                    }
                    column.setEnumeration(enumeration);
                }
            }
        }
        return dataBase;
    }

    private String findEnumerationName(String tabellaName, String columnName) {
        String enumerationName = null;
        HashMap<String, List<String>> enums = Utils.filterEnumeration(tabellaName, columnName);
        if (enums != null) {
            for (String key : enums.keySet()) {
                enumerationName = key;
            }
        }
        return enumerationName;
    }
}
