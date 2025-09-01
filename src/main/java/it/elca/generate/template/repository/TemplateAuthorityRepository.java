package it.elca.generate.template.repository;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;

public class TemplateAuthorityRepository extends AbstractTemplate{
	private String Authority = "Authority";
	
	public TemplateAuthorityRepository(DataBase database) {
		super(database);
	}

	public String getTypeTemplate() {
		String typeTemplate = Utils.replace(ConfigCreateProject.getIstance().getSrcRepositoryFolder(),".","/");
		return typeTemplate;
	}
	
	public String getTypeFile() {
		return "java";
	}

	public String getBody() {
		// https://www.buildmystring.com/
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		String body = "package "+ conf.getPackageclass() + "." + conf.getSrcRepositoryFolder()+";\r\n\n" +
		"import "+ conf.getPackageclass() + "." + conf.getSrcDomainFolder() + "." + Authority+";\r\n" +
		"import org.springframework.data.jpa.repository.JpaRepository;\r\n\n" +
		"/**\r\n" +
		" * Spring Data JPA repository for the "+Authority+" entity.\r\n" +
		" */\r\n" +
		"public interface "+getClassName() +" extends JpaRepository<"+Authority+", String> {\r\n" +
		"}\r\n";
		return body;
	}

	public String getClassName() {
		return Utils.getRepositoryClassName(Authority);
	}
	
	public String getSourceFolder() {
		return "src/main/java";
	}

}
