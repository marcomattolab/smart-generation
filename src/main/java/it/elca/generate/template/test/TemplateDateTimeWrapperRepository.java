package it.elca.generate.template.test;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;

public class TemplateDateTimeWrapperRepository extends AbstractTemplate{

	public TemplateDateTimeWrapperRepository(DataBase database) {
		super(database);
	}

	public String getTypeTemplate() {
		String typeTemplate = Utils.replace(ConfigCreateProject.getIstance().getSrcRepositoryTimezoneFolder(),".","/");
		return typeTemplate;
	}

	public String getTypeFile() {
		return "java";
	}

	public String getBody() {
		//https://www.buildmystring.com/
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		String body = "package "+ conf.getPackageclass() + "." + conf.getSrcRepositoryTimezoneFolder()+";\r\n\n" +
		"import org.springframework.data.jpa.repository.JpaRepository;\r\n" +
		"import org.springframework.stereotype.Repository;\r\n\n" +
		"/**\r\n" +
		" * Spring Data JPA repository for the DateTimeWrapper entity.\r\n" +
		" */\r\n" +
		"@Repository\r\n" +
		"public interface DateTimeWrapperRepository extends JpaRepository<DateTimeWrapper, Long> {\r\n" +
		"}\r\n";
		return body;
	}

	public String getClassName() {
		return "DateTimeWrapperRepository";
	}

	public String getSourceFolder() {
		return "src/test/java";
	}

}
