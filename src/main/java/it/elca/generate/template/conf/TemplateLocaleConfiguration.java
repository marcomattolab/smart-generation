package it.elca.generate.template.conf;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;

public class TemplateLocaleConfiguration extends AbstractTemplate {

	public TemplateLocaleConfiguration(DataBase database) {
		super(database);
	}

	public String getTypeFile() {
		return "java";
	}

	public String getBody(){
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		// https://www.buildmystring.com/
		String body = "package "+ conf.getPackageclass() + "." + conf.getSrcConfigFolder() +";\r\n\n" +
		"import io.github.jhipster.config.locale.AngularCookieLocaleResolver;\r\n" +
		"import org.springframework.context.annotation.Bean;\r\n" +
		"import org.springframework.context.annotation.Configuration;\r\n" +
		"import org.springframework.web.servlet.LocaleResolver;\r\n" +
		"import org.springframework.web.servlet.config.annotation.*;\r\n" +
		"import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;\r\n\n" +
		"@Configuration\r\n" +
		"public class " + getClassName() + " implements WebMvcConfigurer {\r\n\n" +
		"    @Bean(name = \"localeResolver\")\r\n" +
		"    public LocaleResolver localeResolver() {\r\n" +
		"        AngularCookieLocaleResolver cookieLocaleResolver = new AngularCookieLocaleResolver();\r\n" +
		"        cookieLocaleResolver.setCookieName(\"NG_TRANSLATE_LANG_KEY\");\r\n" +
		"        return cookieLocaleResolver;\r\n" +
		"    }\r\n\n" +
		"    @Override\r\n" +
		"    public void addInterceptors(InterceptorRegistry registry) {\r\n" +
		"        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();\r\n" +
		"        localeChangeInterceptor.setParamName(\"language\");\r\n" +
		"        registry.addInterceptor(localeChangeInterceptor);\r\n" +
		"    }\r\n\n" +
		"}\r\n";
		return body;
	}

	public String getClassName(){
		return "LocaleConfiguration";
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
