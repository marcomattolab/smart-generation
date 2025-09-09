package it.elca.generate.template.fe.entities;

import it.elca.generate.Column;
import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Enumeration;
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

public class TemplateEntityComponentHtml extends AbstractResourceTemplate {
    private static final boolean GENERATE_SEARCH_FILTER = true;

    private static final String TH = "TH";
    private static final String TD = "TD";
    private static final String printButtonSizeMedium = "16px";

    // FILTER_COLUMN_SIZE (12/4)   ==>   3 filtri di ricerca per riga
    private static int FILTER_COLUMN_SIZE = 4;

    public TemplateEntityComponentHtml(DataBase database, Table tabella) {
        super(database);
        this.tabella = tabella;
    }

    public String getTypeFile() {
        return "html";
    }

    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("projectName", conf.getProjectName());
        data.put("Nometabella", Utils.getEntityName(tabella));
        data.put("nometabella", Utils.getClassNameLowerCase(tabella));
        data.put("columns", getColumns());
        data.put("relations", getRelations(conf));
        data.put("relationsTh", buildRelations(conf, TH));
        data.put("relationsTd", buildRelations(conf, TD));
        return FreemarkerTemplate.process("fe/entities/entity.component.html.ftl", data);
    }

    private List<Map<String, Object>> getColumns() {
        List<Map<String, Object>> columns = new ArrayList<>();
        for (Column column : tabella.getSortedColumns()) {
            Map<String, Object> columnData = new HashMap<>();
            columnData.put("name", Utils.getFieldName(column));
            columnData.put("ColumnName", Utils.getFieldNameForMethod(column));
            columnData.put("splitted", Utils.splitCamelCase(Utils.getFieldNameForMethod(column)));
            columnData.put("isString", column.getTypeColumn().getName().equals("java.lang.String"));
            columnData.put("isEnum", column.getEnumeration() != null);
            if (column.getEnumeration() != null) {
                List<Map<String, String>> enumValues = new ArrayList<>();
                for (Enumeration e : Utils.getEnumerationsByDbAndTable(database, tabella)) {
                    if (column.getEnumeration().equals(e.getNomeEnumeration())) {
                        for (String vEnum : e.getValoriEnumeration()) {
                            Map<String, String> enumValue = new HashMap<>();
                            enumValue.put("name", e.getNomeEnumeration());
                            enumValue.put("value", vEnum);
                            enumValues.add(enumValue);
                        }
                    }
                }
                columnData.put("enumValues", enumValues);
            }
            columnData.put("isDate", Utils.isDateField(column));
            if (Utils.isDateField(column)) {
                columnData.put("filterDate", Utils.isLocalDate(column) ? " date:'mediumDate'" : " date:'medium'");
            }
            columnData.put("isNumeric", Utils.isNumericField(column));
            columnData.put("isBlob", Utils.isBlob(column));
            columnData.put("isId", Utils.isPrimaryKeyID(column));
            columns.add(columnData);
        }
        return columns;
    }

    private String getRelations(ConfigCreateProject conf) {
        String result = "";
        if (GENERATE_SEARCH_FILTER) {
            if (!CollectionUtils.isEmpty(conf.getProjectRelations())) {
                for (ProjectRelation rel : conf.getProjectRelations()) {
                    String relationType = rel.getType();
                    String nomeTabellaSx = rel.getSxTable();
                    String nomeSelectSx = rel.getSxSelect();
                    String nomeSelectDx = rel.getDxSelect();
                    String nomeRelazioneSx = rel.getSxName();
                    String nomeRelazioneDx = rel.getDxName();
                    String nomeTabellaDx = rel.getDxTable();
                    String nomeTabella = tabella.getNomeTabella().toLowerCase();

                    if (nomeTabellaSx != null && nomeTabellaDx != null) {
                        // Relations OneToOne / ManyToOne
                        if (relationType.equals(Utils.OneToOne) || relationType.equals(Utils.ManyToOne)) {
                            if (nomeTabellaSx.toLowerCase().equals(nomeTabella)) {

                                //USE_FIRST_MODEL
                                result += "\n                    <!-- SearchFilter Add Relation  Name: " + nomeRelazioneSx + "   Type: " + relationType + " -->\n" +
                                        "                    <div class=\"col-md-" + FILTER_COLUMN_SIZE + "\">\r\n" +
                                        "                          <div class=\"form-group\">\r\n" +
                                        "                             <label jhiTranslate=\"" + conf.getProjectName() + "App." + Utils.getFirstLowerCase(nomeTabellaSx) + "." + Utils.getFirstLowerCase(nomeRelazioneSx) + "\">" + Utils.getFirstUpperCase(nomeRelazioneSx) + "</label>\n" +
                                        "                                <select class=\"form-control\" id=\"field_" + Utils.getFirstLowerCase(nomeRelazioneSx) + "\" formControlName=\"" + Utils.getFirstLowerCase(nomeRelazioneSx) + "Id\"  name=\"" + Utils.getFirstLowerCase(nomeRelazioneSx) + "Id\">\n" +
                                        "                 					<option [value]=\"null\"></option>\r\n" +
                                        "                 					<option [value]=\"" + nomeRelazioneSx + "Option.id\" *ngFor=\"let " + nomeRelazioneSx + "Option of " + nomeRelazioneSx + "s" + "\">{{" + nomeRelazioneSx + "Option." + nomeSelectSx + "}}</option>\n" +
                                        "                                </select>\r\n" +
                                        "                          </div>\r\n" +
                                        "			          </div>\r\n";

                            }

                        } else if (relationType.equals(Utils.OneToMany)) {
                            // Relations OneToMany
                            if (nomeTabellaDx.toLowerCase().equals(nomeTabella)) {

                                //USE_FIRST_MODEL
                                result += "\n                        <!-- SearchFilter Add Relation  Name: " + nomeRelazioneDx + "  Type: OneToMany -->\n";
                                result +=
                                        "                        <div class=\"col-md-" + FILTER_COLUMN_SIZE + "\">\r\n" +
                                                "                            <div class=\"form-group\">\r\n" +
                                                "                                <label jhiTranslate=\"" + conf.getProjectName() + "App." + Utils.getFirstLowerCase(nomeTabellaDx) + "." + Utils.getFirstLowerCase(nomeRelazioneDx) + "\">" + Utils.getFirstUpperCase(nomeRelazioneDx) + "</label>\r\n" +
                                                "                                <select class=\"form-control\" id=\"field_" + Utils.getFirstLowerCase(nomeRelazioneDx) + "\" formControlName=\"" + Utils.getFirstLowerCase(nomeRelazioneDx) + "Id\"  name=\"" + Utils.getFirstLowerCase(nomeRelazioneDx) + "Id\">\n" +
                                                "                 					<option [value]=\"null\"></option>\r\n" +
                                                "                 					<option [value]=\"" + nomeRelazioneDx + "Option.id\" *ngFor=\"let " + nomeRelazioneDx + "Option of " + nomeRelazioneDx + "s" + "\">{{" + nomeRelazioneDx + "Option." + nomeSelectDx + "}}</option>\n" +
                                                "                                </select>\r\n" +
                                                "                            </div>\r\n" +
                                                "                        </div>\r\n";

                            }

                        } else if (relationType.equals(Utils.ManyToMany)) {
                            // Relations ManyToMany  (TODO DEVELOP THIS)
                        }
                    }
                }
            }
        }
        return result;
    }


    private String buildRelations(ConfigCreateProject conf, String type) {
        String result = "";
        if (!CollectionUtils.isEmpty(conf.getProjectRelations())) {
            for (ProjectRelation rel : conf.getProjectRelations()) {
                String relationType = rel.getType();
                String nomeTabellaSx = rel.getSxTable();
                String nomeRelazioneSx = rel.getSxName();
                String nomeRelazioneDx = rel.getDxName();
                String nomeTabellaDx = rel.getDxTable();
                String nomeSelectSx = rel.getSxSelect();
                String nomeSelectDx = rel.getDxSelect();
                String nomeTabella = tabella.getNomeTabella().toLowerCase();

                if (nomeTabellaSx != null && nomeTabellaDx != null) {
                    //Relations OneToOne / ManyToOne
                    if (relationType.equals(Utils.OneToOne) || relationType.equals(Utils.ManyToOne)) {
                        if (nomeTabellaSx.toLowerCase().equals(nomeTabella)) {
                            if (TH.equals(type)) {
                                result += "			<!-- TH - OneToOne / ManyToOne -->\n";
                                result +=
                                        "			<th jhiSortBy=\"" + Utils.getFirstLowerCase(nomeRelazioneSx) + "" + Utils.getFirstUpperCase(nomeSelectSx) + "\">"
                                                + "			  <span jhiTranslate=\"" + conf.getProjectName() + "App." + Utils.getFirstLowerCase(nomeTabellaSx) + "." + Utils.getFirstLowerCase(nomeRelazioneSx) + "\">" + Utils.getFirstUpperCase(nomeRelazioneSx) + "</span>\n"
                                                + "			  <fa-icon [icon]=\"'sort'\"></fa-icon>\n"
                                                + "			</th>\r\n";
                            }
                            if (TD.equals(type)) {
                                result += "			<!-- TD - OneToOne / ManyToOne -->\n";
                                result +=
                                        "			<td>\r\n" +
                                                "			   <div *ngIf=\"" + Utils.getFirstLowerCase(nomeTabellaSx) + "." + Utils.getFirstLowerCase(nomeRelazioneSx) + "Id\">\r\n" +
                                                "                  <a [routerLink]=\"['../" + Utils.getFirstLowerCase(nomeTabellaDx) + "', " + Utils.getFirstLowerCase(nomeTabellaSx) + "." + Utils.getFirstLowerCase(nomeRelazioneSx) + "Id , 'view' ]\" >{{" + Utils.getFirstLowerCase(nomeTabellaSx) + "." + Utils.getFirstLowerCase(nomeRelazioneSx) + "" + Utils.getFirstUpperCase(nomeSelectSx) + "}}</a>\r\n" +
                                                "			   </div>\r\n" +
                                                "			</td>\r\n";
                            }
                        }

                    } else if (relationType.equals(Utils.OneToMany)) {
                        //Relations OneToMany
                        if (nomeTabellaDx.toLowerCase().equals(nomeTabella)) {
                            if (TH.equals(type)) {
                                // DONE TEST   "relations"   autore  ==> preferito2   /      nomeTabellaSx ==> nomeRelazioneDx
                                result += "			<!-- TH    Name :  " + nomeRelazioneDx + "  - Type: OneToMany -->\n";
                                result +=
                                        "			<th jhiSortBy=\"" + Utils.getFirstLowerCase(nomeRelazioneDx) + "" + Utils.getFirstUpperCase(nomeSelectDx) + "\">\n"
                                                + "			  <span jhiTranslate=\"" + conf.getProjectName() + "App." + Utils.getFirstLowerCase(nomeTabellaDx) + "." + Utils.getFirstLowerCase(nomeRelazioneDx) + "\">" + Utils.getFirstUpperCase(nomeRelazioneDx) + "</span>\n"
                                                + "			  <fa-icon [icon]=\"'sort'\"></fa-icon>\n"
                                                + "			</th>\r\n";


                            }
                            if (TD.equals(type)) {
                                result += "			<!-- TD    Name :  " + nomeRelazioneDx + "  - Type: OneToMany -->\n";
                                result +=
                                        "			<td>\r\n" +
                                                "			   <div *ngIf=\"" + Utils.getFirstLowerCase(nomeTabellaDx) + "." + Utils.getFirstLowerCase(nomeRelazioneDx) + "Id\">\r\n" +
                                                "                  <a [routerLink]=\"['../" + Utils.getFirstLowerCase(nomeTabellaSx) + "', " + Utils.getFirstLowerCase(nomeTabellaDx) + "." + Utils.getFirstLowerCase(nomeRelazioneDx) + "Id , 'view' ]\" >{{" + Utils.getFirstLowerCase(nomeTabellaDx) + "." + Utils.getFirstLowerCase(nomeRelazioneDx) + "" + Utils.getFirstUpperCase(nomeSelectDx) + "}}</a>\r\n" +
                                                "			   </div>\r\n" +
                                                "			</td>\r\n";
                            }
                        }


                    } else if (relationType.equals(Utils.ManyToMany)) {
                        //Relations ManyToMany - TODO DEVELOP THIS!
                    }
                }

            }
        }
        return result;
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
