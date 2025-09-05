package it.elca.generate.template.conf;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateCacheConfiguration extends AbstractTemplate {

    public TemplateCacheConfiguration(DataBase database) {
        super(database);
    }

    public String getTypeFile() {
        return "java";
    }

    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageClass", conf.getPackageclass());
        data.put("srcConfigFolder", conf.getSrcConfigFolder());
        data.put("srcRepositoryFolder", conf.getSrcRepositoryFolder());
        data.put("srcDomainFolder", conf.getSrcDomainFolder());
        data.put("className", getClassName());
        data.put("tables", getTables());
        return FreemarkerTemplate.process("conf/cache.configuration.java.ftl", data);
    }

    private List<Map<String, String>> getTables() {
        List<Map<String, String>> tables = new ArrayList<>();
        for (Table table : Utils.getTables(database)) {
            Map<String, String> tableData = new HashMap<>();
            tableData.put("name", Utils.getEntityName(table));
            tables.add(tableData);
        }
        return tables;
    }

    public String getClassName() {
        return "CacheConfiguration";
    }

    @Override
    public String getTypeTemplate() {
        return Utils.replace(ConfigCreateProject.getIstance().getSrcConfigFolder(), ".", "/");
    }

    @Override
    public String getSourceFolder() {
        return "src/main/java";
    }
}
