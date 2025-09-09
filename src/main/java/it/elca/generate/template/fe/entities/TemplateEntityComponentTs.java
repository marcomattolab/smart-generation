package it.elca.generate.template.fe.entities;

import it.elca.generate.Column;
import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.ProjectRelation;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateEntityComponentTs extends AbstractResourceTemplate {
    private static final String A = "A";
    private static final String DA = "Da";

    private static final String IMPORT_SECTION = "IMPORT_SECTION";
    private static final String INIT_SECTION = "INIT_SECTION";
    private static final String SEARCH = "SEARCH";
    private static final String SEARCH_FILTER = "SEARCH_FILTER";
    private static final String CONSTRUCTOR_SECTION = "CONSTRUCTOR_SECTION";
    private static final String TRACKBY_SECTION = "TRACKBY_SECTION";
    private static final String NG_ONINIT_SECTION = "NG_ONINIT_SECTION";

    public TemplateEntityComponentTs(DataBase database, Table tabella) {
        super(database);
        this.tabella = tabella;
    }

    public String getTypeFile() {
        return "ts";
    }

    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("Nometabella", Utils.getEntityName(tabella));
        data.put("nometabella", Utils.getClassNameLowerCase(tabella));
        data.put("iNometabella", Utils.getIName(tabella));
        data.put("relations", getRelations(conf));
        data.put("columns", getColumns());
        data.put("searchFilters", getSearchFilters(conf));
        return FreemarkerTemplate.process("fe/entities/entity.component.ts.ftl", data);
    }

    private List<Map<String, Object>> getColumns() {
        List<Map<String, Object>> columns = new ArrayList<>();
        for (Column column : tabella.getSortedColumns()) {
            Map<String, Object> columnData = new HashMap<>();
            columnData.put("name", Utils.getFieldName(column));
            columnData.put("isDate", Utils.isDateField(column));
            columnData.put("isNumeric", Utils.isNumericField(column));
            columnData.put("isString", Utils.isTextField(column));
            columnData.put("isEnum", column.getEnumeration() != null);
            columns.add(columnData);
        }
        return columns;
    }

    private Map<String, List<String>> getRelations(ConfigCreateProject conf) {
        Map<String, List<String>> relations = new HashMap<>();
        relations.put("imports", new ArrayList<>());
        relations.put("inits", new ArrayList<>());
        relations.put("constructors", new ArrayList<>());
        relations.put("searches", new ArrayList<>());
        relations.put("ngOnInits", new ArrayList<>());

        if (!CollectionUtils.isEmpty(conf.getProjectRelations())) {
            for (ProjectRelation rel : conf.getProjectRelations()) {
                String relationType = rel.getType();
                String nomeTabellaSx = rel.getSxTable();
                String nomeRelazioneSx = rel.getSxName();
                String nomeRelazioneDx = rel.getDxName();
                String nomeTabellaDx = rel.getDxTable();
                String nomeTabella = tabella.getNomeTabella().toLowerCase();

                if (nomeTabellaSx != null && nomeTabellaDx != null) {
                    boolean isOne2OneOrMany2One = relationType.equals(Utils.OneToOne) || relationType.equals(Utils.ManyToOne);
                    boolean isOneToMany = relationType.equals(Utils.OneToMany);

                    if (nomeTabellaSx.toLowerCase().equals(nomeTabella) && isOne2OneOrMany2One) {
                        relations.get("imports").add("import { I" + Utils.getFirstUpperCase(nomeTabellaDx) + " } from 'app/shared/model/" + Utils.getFirstLowerCase(nomeTabellaDx) + ".model';");
                        relations.get("imports").add("import { " + Utils.getFirstUpperCase(nomeTabellaDx) + "Service } from 'app/entities/" + Utils.getFirstLowerCase(nomeTabellaDx) + "';");
                        relations.get("inits").add(Utils.getFirstLowerCase(nomeRelazioneSx) + "s: I" + Utils.getFirstUpperCase(nomeTabellaDx) + "[];");
                        relations.get("constructors").add("private " + Utils.getFirstLowerCase(nomeTabellaDx) + "Service: " + Utils.getFirstUpperCase(nomeTabellaDx) + "Service,");
                        relations.get("searches").add(Utils.getFirstLowerCase(nomeRelazioneSx) + "Id: new FormControl(''),");
                        relations.get("ngOnInits").add(
                                "        // Relation Type: " + relationType + "\n" +
                                        "        this." + Utils.getFirstLowerCase(nomeTabellaDx) + "Service.query().subscribe(\n" +
                                        "        (res: HttpResponse<I" + Utils.getFirstUpperCase(nomeTabellaDx) + "[]>) => {\n" +
                                        "            this." + Utils.getFirstLowerCase(nomeRelazioneSx) + "s = res.body;\n" +
                                        "        },\n" +
                                        "        (res: HttpErrorResponse) => this.onError(res.message)\n" +
                                        "        );");
                    }
                    if (nomeTabellaDx.toLowerCase().equals(nomeTabella) && isOneToMany) {
                        relations.get("imports").add("import { I" + Utils.getFirstUpperCase(nomeTabellaSx) + " } from 'app/shared/model/" + Utils.getFirstLowerCase(nomeTabellaSx) + ".model';");
                        relations.get("imports").add("import { " + Utils.getFirstUpperCase(nomeTabellaSx) + "Service } from 'app/entities/" + Utils.getFirstLowerCase(nomeTabellaSx) + "';");
                        relations.get("inits").add(Utils.getFirstLowerCase(nomeRelazioneDx) + "s: I" + Utils.getFirstUpperCase(nomeTabellaSx) + "[];");
                        relations.get("constructors").add("private " + Utils.getFirstLowerCase(nomeTabellaSx) + "Service: " + Utils.getFirstUpperCase(nomeTabellaSx) + "Service,");
                        relations.get("searches").add(Utils.getFirstLowerCase(nomeRelazioneDx) + "Id: new FormControl(''),");
                        relations.get("ngOnInits").add(
                                "        // Relation Type: " + relationType + "\n" +
                                        "        this." + Utils.getFirstLowerCase(nomeTabellaSx) + "Service.query().subscribe(\n" +
                                        "        (res: HttpResponse<I" + Utils.getFirstUpperCase(nomeTabellaSx) + "[]>) => {\n" +
                                        "            this." + Utils.getFirstLowerCase(nomeRelazioneDx) + "s = res.body;\n" +
                                        "        },\n" +
                                        "        (res: HttpErrorResponse) => this.onError(res.message)\n" +
                                        "        );");
                    }
                }
            }
        }
        return relations;
    }

    private List<String> getSearchFilters(ConfigCreateProject conf) {
        List<String> searchFilters = new ArrayList<>();
        if (!CollectionUtils.isEmpty(conf.getProjectRelations())) {
            for (ProjectRelation rel : conf.getProjectRelations()) {
                String relationType = rel.getType();
                String nomeTabellaSx = rel.getSxTable();
                String nomeRelazioneSx = rel.getSxName();
                String nomeRelazioneDx = rel.getDxName();
                String nomeTabellaDx = rel.getDxTable();
                String nomeTabella = tabella.getNomeTabella().toLowerCase();

                if (nomeTabellaSx != null && nomeTabellaDx != null) {
                    if (relationType.equals(Utils.OneToOne) || relationType.equals(Utils.ManyToOne)) {
                        if (nomeTabellaSx.toLowerCase().equals(nomeTabella)) {
                            searchFilters.add("searchFilter = checkAndCompileSearchFilterEquals(myGroupControls, searchFilter, '" + Utils.getFirstLowerCase(nomeRelazioneSx) + "Id');");
                        }
                    }
                    if (relationType.equals(Utils.OneToMany)) {
                        if (nomeTabellaDx.toLowerCase().equals(nomeTabella)) {
                            searchFilters.add("searchFilter = checkAndCompileSearchFilterEquals(myGroupControls, searchFilter, '" + Utils.getFirstLowerCase(nomeRelazioneDx) + "Id');");
                        }
                    }
                }
            }
        }
        return searchFilters;
    }

    public String getClassName() {
        return Utils.getClassNameLowerCase(tabella) + ".component";
    }

    @Override
    public String getTypeTemplate() {
        String typeTemplate = "";
        return typeTemplate;
    }

    @Override
    public String getSourceFolder() {
        return "src/main/webapp/app/entities/" + Utils.getClassNameLowerCase(tabella);
    }
}
