package it.elca.generate.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Enumeration;
import it.elca.generate.Table;
import it.elca.generate.Utils;

public abstract class AbstractTemplate {
    protected Table tabella;
    protected DataBase database;
    protected Enumeration enumeration;
    protected ConfigCreateProject config;

    public AbstractTemplate(DataBase database, ConfigCreateProject config) {
        this.database = database;
        this.config = config;
    }

    public AbstractTemplate(Table tabella, ConfigCreateProject config) {
        this.tabella = tabella;
        this.config = config;
    }

    public AbstractTemplate(Enumeration enumeration, ConfigCreateProject config) {
        this.enumeration = enumeration;
        this.config = config;
    }

    public void generateTemplate() throws IOException {
        String relative = Utils.replace(config.getPackageclass(), ".", "/");
        String root = config.getPathname();
        String projectName = config.getProjectName();
        String sourceFolder = getSourceFolder();
        File f = new File(root + "/" + projectName + "/" + sourceFolder + "/" + relative + "/" + getTypeTemplate().toLowerCase() + "/");
        if (!f.exists()) {
            f.mkdirs();
        }
        f = new File(f.getAbsolutePath() + "/" + getClassName() + "." + getTypeFile());
        FileWriter fw = new FileWriter(f);
        fw.write(getBody());
        fw.close();
    }

    public String getTypeFile() {
        return "java";
    }

    public abstract String getTypeTemplate();

    public abstract String getBody();

    public abstract String getClassName();

    public abstract String getSourceFolder();

}
