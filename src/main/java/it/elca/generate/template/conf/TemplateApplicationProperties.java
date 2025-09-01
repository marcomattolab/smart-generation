package it.elca.generate.template.conf;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;

public class TemplateApplicationProperties extends AbstractTemplate {

	public TemplateApplicationProperties(DataBase database) {
		super(database);
	}

	public String getTypeFile() {
		return "java";
	}

	public String getBody(){
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		// https://www.buildmystring.com/
		String body = "package "+ conf.getPackageclass() + "." + conf.getSrcConfigFolder() +";\n\n" +
		"import org.springframework.boot.context.properties.ConfigurationProperties;\n\n" +
		"/**\n" +
		" * Properties specific to "+conf.getProjectName()+"\n" +
		" * <p>\n" +
		" * Properties are configured in the application.yml file.\n" +
		//" * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.\n" +
		" */\n" +
		"@ConfigurationProperties(prefix = \"application\", ignoreUnknownFields = false)\n" +
		"public class " + getClassName() + " {\n" +
		"\n}\n";
		return body;
	}

	public String getClassName(){
		return "ApplicationProperties";
	}

	@Override
	public String getTypeTemplate() {
		String typeTemplate = Utils.replace(ConfigCreateProject.getIstance().getSrcConfigFolder(),".","/");
		return typeTemplate;
	}

	@Override
	public String getSourceFolder() {
		return "src/main/java";
	}

}
