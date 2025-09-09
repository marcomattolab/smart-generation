package it.elca.generate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

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
import it.elca.generate.template.service.TemplateServiceDTO;
import it.elca.generate.template.service.TemplateServiceImpl;
import it.elca.generate.template.service.TemplateUserDTO;
import it.elca.generate.template.service.TemplateUserMapperService;
import it.elca.generate.template.service.TemplateUserService;
import it.elca.generate.template.util.TemplateCopyAll;
import it.elca.generate.template.util.TemplateDeleteTest;
import it.elca.generate.template.web.TemplateAccountResource;
import it.elca.generate.template.web.TemplateAuditResource;
import it.elca.generate.template.web.TemplateEntityAuditResource;
import it.elca.generate.template.web.TemplateLogsResource;
import it.elca.generate.template.web.TemplateReportBase;
import it.elca.generate.template.web.TemplateResource;
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

public class DataBase {
	public Map<String, Table> tabelle;
	public Map<String, List<String>> enumeration;
	private static DataBase conf;

	private DataBase(){
		tabelle = new HashMap<>();
		enumeration = new HashMap<>();
	}

	public static DataBase getInstance(){
		if(conf==null){
			conf = new DataBase();
			try {
				System.out.println("Loading database...");
				conf.init();
				System.out.println("\nDatabase has been succesfully loaded...\n\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conf; 
	}

	public Table getTables(String tabellaName) {
		return tabelle.get(tabellaName);
	}

	public Map<String, List<String>> getEnumeration() {
		return enumeration;
	}

	public Set<String> getTableName() {
		return tabelle.keySet();
	}

	public void init() throws ClassNotFoundException, SQLException {
		ConfigCreateProject ccp = ConfigCreateProject.getIstance();
			
		// Generate from Project Entities JSON
		List<ProjectEntity> prjEntities = ccp.getProjectEntities();
		
		if (!CollectionUtils.isEmpty(prjEntities)) {
			for(ProjectEntity entity : prjEntities) {
				Table table = new Table();
				String tableName = entity.getName();
				System.out.println("\n## TableName: "+tableName);
				
				table.setNomeTabellaCompleto(tableName);
				table.setNomeTabella(tableName);
				table.setProfiles(entity.getProfiles());
				this.addTable(tableName, table);
				
				int sortColumn = 0;
				for(Field field : entity.getFields()) {
					String columnName = field.getFname();
					String mTypeColumn = field.getFtype();
					boolean isRequired = field.isFrequired();
					Integer columnSize = field.getFsize()!=null ? field.getFsize() : null;
					
					//MinLenght, MaxLenght, Pattern
					Integer columnMinSize = field.getFminlength()!=null ? field.getFminlength() : null;
					Integer columnMaxSize = field.getFmaxlength()!=null ? field.getFmaxlength() : null;
					String pattern = field.getFpattern();
					
					Column column = new Column();
					column.setName(columnName);
					column.setSortColumn(sortColumn++);
					
					int iTypeColmn = Column.corvertModelType(mTypeColumn);
					column.setTypeColumn(iTypeColmn);
					
					if (!isRequired) {
						column.setNullable();
					}
					if(columnSize!=null) {
						column.setColumnSize(columnSize);
					}
					if(columnMinSize!=null) {
						column.setColumnMinSize(columnMinSize);
					}
					if(columnMaxSize!=null) {
						column.setColumnMaxSize(columnMaxSize);
					}
					if(pattern!=null) {
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
					if(table.getColumn(key)!=null) {
						table.getColumn(key).setKey();
					}
					
				}
				
			}
		}
		//Build Enumerations
		buildEnumerations(ccp);
	}

	/**
	 * Build Enumerations Stuff from JSON
	 * 
	 * @param ccp ConfigCreateProject
	 */
	private void buildEnumerations(ConfigCreateProject ccp) {
		//Generate Enumerations
		List<ProjectEnum> enums = ccp.getEnumerations();
		System.out.println("\n# Enumeration ");
		for (ProjectEnum projectEnum: enums) {
			String[] values = projectEnum.getValues().split("#");
			System.out.println("    - Define Enumeration: " + projectEnum.getName() + "  -  Values: " + Arrays.asList(values) + "");
			this.addEnumeration(projectEnum.getName(), Arrays.asList(values) );
		}
	}
	
	public void addTable(String tableName, Table table) {
		tabelle.put(tableName, table);
	}

	public void addEnumeration(String name, List<String> values) {
		enumeration.put(name, values);
	}

	// TODO => DEVELOP THIS LOGIC AND REFACTOR
	public void generateFile() {
		System.out.println("-------------------------------------------------------");
		System.out.println("Generating Project and project Files for BE and FE ...");
		
		try {
			ConfigCreateProject config = ConfigCreateProject.getIstance();
			
			//Build Enumerations for Application
			buildEnumerationsNameInColumn(this);

			//Project (statics)
			new TemplateClassPath(this).generateTemplate();
			new TemplateFactoryPath(this).generateTemplate();
			new TemplatePom(this).generateTemplate();
			new TemplateAngular(this).generateTemplate();
			new TemplatePackageLock(this).generateTemplate();
			new TemplatePackage(this).generateTemplate();
			new TemplateLoggingAspect(this).generateTemplate();
			new TemplateApplicationWebXml(this).generateTemplate();
			new ApplicationApp(this).generateTemplate();

			//Report Utils (statics)
			new TemplateReportUtils(this).generateTemplate();

			//Configuration (statics)
			new TemplateApplicationProperties(this).generateTemplate();
			new TemplateAsyncConfiguration(this).generateTemplate();
			new TemplateCacheConfiguration(this).generateTemplate(); 	
			new TemplateCloudDatabaseConfiguration(this).generateTemplate();
			new TemplateCostants(this).generateTemplate();
			new TemplateDatabaseConfiguration(this).generateTemplate();
			new TemplateDateTimeFormatConfiguration(this).generateTemplate();
			new TemplateDefaultProfileUtil(this).generateTemplate();
			new TemplateJacksonConfiguration(this).generateTemplate();
			new TemplateLiquidbaseConfiguration(this).generateTemplate();
			new TemplateLocaleConfiguration(this).generateTemplate();
			new TemplateLoggingAspectConfiguration(this).generateTemplate();
			new TemplateLoggingConfiguration(this).generateTemplate();
			new TemplateMetricsConfiguration(this).generateTemplate(); 	
			new TemplateSecurityConfiguration(this).generateTemplate();
			new TemplateWebConfigurer(this).generateTemplate();

			//Audit Config
			new TemplateAuditEventConverter(this).generateTemplate();
			new TemplateAsyncEntityAuditEventWriter(this).generateTemplate();
			new TemplateEntityAuditEventListener(this).generateTemplate();
			new TemplateEntityAuditAction(this).generateTemplate();
			new TemplateEntityAuditEventConfig(this).generateTemplate();

			//Domain (statics)
			new TemplateEntityAuditEvent(this).generateTemplate(); 	
			new TemplateAbstractAuditingEntity(this).generateTemplate();
			new TemplatePersistentAudit(this).generateTemplate();
			new TemplateAuthority(this).generateTemplate();
			new TemplateUser(this).generateTemplate();

			//Repository (statics)
			new TemplateEntityAuditEventRepository(this).generateTemplate();		
			new TemplateAuthorityRepository(this).generateTemplate();
			new TemplateCustomAuditEventRepository(this).generateTemplate();
			new TemplatePersistenceAuditEventRepository(this).generateTemplate();
			new TemplateUserRepository(this).generateTemplate();

			//RETRIEVE BY JDL/DB/PROPERTY DYNAMIC
			List<Enumeration> enumerations = buildEnumerationsMap();
			for (Enumeration cEnum : enumerations) {
				new TemplateDomainEnumeration(cEnum).generateTemplate();
			}
			
			//Workflow (statics)
			new TemplateWorkflow(this).generateTemplate();
			
			//Security (statics)
			new TemplateAuthoritiesConstants(this).generateTemplate();
			new TemplateDomainUserDetailsService(this).generateTemplate();
			new TemplateSecurityUtils(this).generateTemplate();
			new TemplateSpringSecurityAuditorAware(this).generateTemplate();
			new TemplateUserNotActivatedException(this).generateTemplate();
			//Add Security JWT
			new TemplateSecurityJWTConfigurer(this).generateTemplate();
			new TemplateSecurityJWTFilter(this).generateTemplate();
			new TemplateSecurityTokenProvider(this).generateTemplate();
			
			//Service (statics)
			new TemplateRandomUtil(this).generateTemplate();
			new TemplateMailService(this).generateTemplate();
			new TemplateUserService(this).generateTemplate();
			new TemplateAuditEventService(this).generateTemplate();
			new TemplateUserMapperService(this).generateTemplate();
			new TemplateEntityMapperService(this).generateTemplate();

			//DTO (statics)
			new TemplateUserDTO(this).generateTemplate();
			new TemplatePasswordChangeDTO(this).generateTemplate();
			new TemplateAbstractAuditingDTO(this).generateTemplate();  		

			//WEB.REST (statics)
			new TemplateEntityAuditResource(this).generateTemplate(); 	
			new TemplateAccountResource(this).generateTemplate();
			new TemplateAuditResource(this).generateTemplate();
			new TemplateUserResource(this).generateTemplate();
			// FIXME ==> new TemplateUserJWTController(this).generateTemplate();
			new TemplateLogsResource(this).generateTemplate();
			new TemplateReportBase(this).generateTemplate();	//Dynamic Jasper	

			//WEB.REST.UTILS (statics)
			new TemplateHeaderUtil(this).generateTemplate();
			new TemplatePaginationUtil(this).generateTemplate();

			//WEB.REST.VM (statics)
			new TemplateKeyAndPasswordVM(this).generateTemplate();
			new TemplateLoginVM(this).generateTemplate();
			new TemplateLoggerVM(this).generateTemplate();
			new TemplateManagedUserVM(this).generateTemplate();

			//WEB.REST.ERRORS (statics)
			new TemplateBadRequestAlertException(this).generateTemplate();
			new TemplateCustomParameterizedException(this).generateTemplate();
			new TemplateEmailAlreadyUsedException(this).generateTemplate();
			new TemplateEmailNotFoundException(this).generateTemplate();
			new TemplateErrorConstants(this).generateTemplate();
			new TemplateExceptionTranslator(this).generateTemplate();
			new TemplateFieldErrorVM(this).generateTemplate();
			new TemplateInternalServerErrorException(this).generateTemplate();
			new TemplateInvalidPasswordException(this).generateTemplate();
			new TemplateLoginAlreadyUsedException(this).generateTemplate();

			//RESOURCES START (statics)
			new TemplateBanner(this).generateTemplate();
			new TemplateApplication(this).generateTemplate();
			new TemplateApplicationProd(this).generateTemplate();
			new TemplateApplicationDev(this).generateTemplate();
			new TemplateLiquidbaseEntityAudit(this).generateTemplate(); 
			new TemplateLiquidbaseMasterInitialSchema(this).generateTemplate(); 
			new TemplateLiquidbaseMaster(this).generateTemplate(); 					
			new TemplateMessage(this).generateTemplate();

			//Resource Liquibase Data CSV
			new TemplateLiquidbaseAuthoritiesCSV(this).generateTemplate(); 					
			new TemplateLiquidbaseUsersCSV(this).generateTemplate(); 					
			new TemplateLiquidbaseUsersAuthoritiesCSV(this).generateTemplate(); 					

			
			//Copy All Template
			new TemplateCopyAll(this).generateTemplate();
			

			//Frontend
			new TemplateManifest(this).generateTemplate(); 
			new TemplateIndex(this).generateTemplate(); 
			new TemplateAppModule(this).generateTemplate(); 
			new TemplateAppMain(this).generateTemplate(); 
			new TemplateAppRoutingModule(this).generateTemplate(); 
			new TemplateAccountModule(this).generateTemplate(); 
			new TemplateAdminModule(this).generateTemplate(); 
			new TemplateAdminEntityAuditModule(this).generateTemplate(); 		//Audit Module TS
			new TemplateDashboardModule(this).generateTemplate();  				//Chart Dashboard
			new TemplateDashboardBarchartModule(this).generateTemplate();  		//Barchart Dashboard
			new TemplateDashboardBarchartRoute(this).generateTemplate();  		//Barchart Route
			new TemplateDashboardDoughnutchartModule(this).generateTemplate();   //Doughnutchart Dashboard
			new TemplateDashboardDoughnutchartRoute(this).generateTemplate();    //Doughnutchart Route
			new TemplateDashboardLinechartModule(this).generateTemplate();  	    //Linechart Dashboard
			new TemplateDashboardLinechartRoute(this).generateTemplate();  	    //Linechart Route
			new TemplateDashboardPiechartModule(this).generateTemplate();  		//Piechart Dashboard
			new TemplateDashboardPiechartRoute(this).generateTemplate();  		//Piechart Route
			new TemplateDashboardPolarareachartModule(this).generateTemplate();  //Polarareachart Dashboard
			new TemplateDashboardPolarareachartRoute(this).generateTemplate();   //Polarareachart Route
			new TemplateDashboardRadarchartModule(this).generateTemplate();  	//Radarchart Dashboard
			new TemplateDashboardRadarchartRoute(this).generateTemplate();  		//Radarchart Route
			new TemplateConfigurationService(this).generateTemplate(); 
			new TemplateErrorehandlerInterceptor(this).generateTemplate(); 
			new TemplateCoreModule(this).generateTemplate(); 
			new TemplateLanguageHelper(this).generateTemplate(); 
			new TemplateHomeModule(this).generateTemplate(); 
			new TemplateHomeComponentHtml(this).generateTemplate(); 
			new TemplateNavbarComponent(this).generateTemplate(); 				//Cicle Entities Done
			new TemplateLeftmenuComponent(this).generateTemplate(); 				//Cicle Entities Done
			new TemplateMainComponent(this).generateTemplate(); 
			new TemplateSharedModule(this).generateTemplate(); 
			new TemplateSharedLibsModule(this).generateTemplate(); 
			new TemplateSharedCommonModule(this).generateTemplate(); 
			new TemplateAlertErrorComponent(this).generateTemplate(); 
			new TemplateModule(this).generateTemplate(); 						//Cicle Entities Done
			new TemplateAccountSettingsModule(this).generateTemplate(); 
			
			

			//Building Data of All Enumerations
			List<Enumeration> enumList = new ArrayList<>();
			Map<String, List<String>> map = this.getEnumeration();
			for(String enumName: map.keySet()) {
				enumList.add( new Enumeration(enumName, map.get(enumName)) );
			}
			

			//All Other Level (dynamics) 
			System.out.println("Creating table dynamics in progress... ");
			for (Table tabella : Utils.getTables(this)) {
				System.out.println("\n# Generating table : "+tabella.getNomeTabella());
				new TemplateDomain(tabella).generateTemplate();
				new TemplateRepository(tabella).generateTemplate();
				new TemplateService(tabella).generateTemplate();   
				new TemplateServiceImpl(tabella).generateTemplate();
				new TemplateQueryService(tabella).generateTemplate();
				new TemplateMapperService(tabella).generateTemplate();
				new TemplateServiceDTO(tabella).generateTemplate();
				// FIXME => new TemplateServiceCriteria(this, tabella).generateTemplate(); 				//Added enumeration management
				new TemplateResource(tabella).generateTemplate();
				new TemplateLiquidbaseData(this, tabella).generateTemplate(); 				//DATA CSV
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
				new TemplateEntityComponentTs(this, tabella).generateTemplate(); 
				new TemplateEntityComponentHtml(this, tabella).generateTemplate(); 			
				new TemplateEntityUpdateComponentTs(tabella).generateTemplate(); 		
				new TemplateEntityUpdateComponentHtml(this, tabella).generateTemplate(); 	//DONE DEVELOP ENUMS AND DATES
				new TemplateEntityDetailComponentTs(tabella).generateTemplate(); 		
				new TemplateEntityDetailComponentHtml(tabella).generateTemplate(); 
				new TemplateEntityDeleteComponentTs(tabella).generateTemplate(); 		
				new TemplateEntityDeleteComponentHtml(tabella).generateTemplate(); 	
				new TemplateEntitySharedModel(this, tabella).generateTemplate(); 			//DONE COMPLETE ENUM
				
			}

			//MultiLanguages 
			for(String languageCode: config.getLanguages()) {
				new TemplateGlobalI18N(this, languageCode).generateTemplate();  
				new TemplateI18N(this, languageCode).generateTemplate();  
				//Enumeration I18N
				for(Enumeration e : enumList) {
					new TemplateEnumerationsI18N(e, languageCode).generateTemplate();  
				}
			}
			
			//DELETE TEST FOLDER
			if ( !config.isGenerateTest() ) {
				new TemplateDeleteTest(this).generateTemplate();
			}

			System.out.println("\nGenerating Project Files Succesfully Completed. Try It!");
			System.out.println("--------------------------------------------------------");
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * Retrieve Enumerations from external configuration file.
	 * @return List<Enumeration>
	 */
	private List<Enumeration> buildEnumerationsMap() {
		System.out.println("Build enumerations map... ");
		List<Enumeration> enumerations = new ArrayList<>();
		for ( String enumName : this.enumeration.keySet() ) {
			List<String> enumValues = this.getEnumeration().get(enumName);
			enumerations.add(new Enumeration(enumName, enumValues));
		}
		return enumerations;
	}
	
	/**
	 * Build Enumerations Name for all columns about application
	 * @param db
	 * @return db
	 */
	private DataBase buildEnumerationsNameInColumn(DataBase db) {
		DataBase dataBase = db;
		Set<?> set = dataBase.getTableName();
		for (Iterator<?> iter = set.iterator(); iter.hasNext();) {
			String tabellaName = (String) iter.next();
			Table tabella = dataBase.getTables(tabellaName);

			Set<?> cset = tabella.getColumnNames();
			for (Iterator<?> citer = cset.iterator(); citer.hasNext();) {
				String columnName = (String) citer.next();
				Column column = tabella.getColumn(columnName);
				String enumeration = findEnumerationName(tabellaName, columnName);
				if(enumeration!=null && enumeration.length()>0) {
					if (Utils.LOG_DEBUG_GENERATOR) {
						System.out.println("@ Added Enum tabellaName.columnName.enum ===> "+tabellaName+"."+columnName+"."+enumeration);
					}
					column.setEnumeration(enumeration);
				}
			}
		}
		return dataBase;
	}
	
	/**
	 * Retrieve Enumeration Name from configuration file.
	 * @param tabellaName
	 * @param columnName
	 * @return EnumerationName
	 */
	private String findEnumerationName(String tabellaName, String columnName) {
		String enumerationName = null;
		HashMap<String, List<String>> enums = Utils.filterEnumeration(tabellaName, columnName);
		if(enums!=null) {
	        for (String key : enums.keySet()) {
	            enumerationName = key;
	        }
		}
		return enumerationName;
	}
	
	@Override
	public String toString() {
		String ret ="";
		Set<String> set = tabelle.keySet();
		for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
			String tablename = (String) iter.next();
			Table table = (Table)tabelle.get(tablename);
			ret+="\n"+table;			
		}
		return ret;
	}

	/**
	 * Smart Generator Main Procedure
	 */
	public static void main(String[] args) {
		DataBase db = DataBase.getInstance();
		db.generateFile();
	}

}
