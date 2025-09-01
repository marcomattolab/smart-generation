package it.elca.generate.template.resouces;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;

public class TemplateLiquidbaseAuthoritiesCSV extends AbstractResourceTemplate{
	
	public TemplateLiquidbaseAuthoritiesCSV(DataBase database) {
		super(database);
	}
	
	public String getTypeTemplate() {
		String typeTemplate = Utils.replace(ConfigCreateProject.getIstance().getResConfigLiquibaseDataFolder(),".","/");
		return typeTemplate;
	}
	
	public String getTypeFile() {
		return "csv";
	}

	public String getBody() {
		// https://www.buildmystring.com/
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		
		String[] profiles = conf.getProfiles();
		String profilesList = profiles!=null && profiles.length>0 
							? buildProfiles(profiles) 
							: buildDefaultProfiles();
		
		String body = "name\n" + profilesList;
		return body;
	}

	private String buildProfiles(String[] profiles) {
		String res = "";
		for (int i = 0; i < profiles.length; i++) {
			res+= profiles[i] + "\n";
		}
		return res;
	}

	private String buildDefaultProfiles() {
		String res = "ROLE_ADMIN\nROLE_USER\n";
		return res;
	}
	public String getClassName() {
		return "authorities";
	}
	
	public String getSourceFolder() {
		return "src/main/resources/";
	}

}
