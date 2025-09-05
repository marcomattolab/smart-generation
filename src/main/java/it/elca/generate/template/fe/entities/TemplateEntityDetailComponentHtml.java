package it.elca.generate.template.fe.entities;

import it.elca.generate.Column;
import it.elca.generate.ConfigCreateProject;
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

public class TemplateEntityDetailComponentHtml extends AbstractResourceTemplate {

    public TemplateEntityDetailComponentHtml(Table tabella) {
        super(tabella);
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
        return FreemarkerTemplate.process("fe/entities/entity-detail.component.html.ftl", data);
    }

    private List<Map<String, Object>> getColumns() {
        List<Map<String, Object>> columns = new ArrayList<>();
        for (Column column : tabella.getSortedColumns()) {
            Map<String, Object> columnData = new HashMap<>();
            columnData.put("name", Utils.getFieldName(column));
            columnData.put("splitted", Utils.splitCamelCase(Utils.getFieldNameForMethod(column)));
            columnData.put("isBlob", Utils.isBlob(column));
            columnData.put("isId", Utils.isPrimaryKeyID(column));
            columns.add(columnData);
        }
        return columns;
    }

    private List<String> getRelations(ConfigCreateProject conf) {
        List<String> relations = new ArrayList<>();
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
                    if (relationType.equals(Utils.OneToOne) || relationType.equals(Utils.ManyToOne)) {
                        if (nomeTabellaSx.toLowerCase().equals(nomeTabella)) {
                            String body = "\n                <!-- Add Relation: OneToOne / ManyToOne -->\n";
                            body += "                <dt><span jhiTranslate=\"" + conf.getProjectName() + "App." + Utils.getFirstLowerCase(nomeTabellaSx) + "." + Utils.getFirstLowerCase(nomeRelazioneSx) + "\">" + Utils.getFirstUpperCase(nomeRelazioneSx) + "</span></dt>\r\n" +
                                    "                <dd>\r\n" +
                                    "                    <div *ngIf=\"" + Utils.getFirstLowerCase(nomeTabellaSx) + "." + Utils.getFirstLowerCase(nomeRelazioneSx) + "Id\">\r\n" +
                                    "                        <a [routerLink]=\"['/" + Utils.getFirstLowerCase(nomeTabellaDx) + "', " + Utils.getFirstLowerCase(nomeTabellaSx) + "." + Utils.getFirstLowerCase(nomeRelazioneSx) + "Id, 'view']\">{{" + Utils.getFirstLowerCase(nomeTabellaSx) + "." + Utils.getFirstLowerCase(nomeRelazioneSx) + "" + Utils.getFirstUpperCase(nomeSelectSx) + "}}</a>\r\n" +
                                    "                    </div>\r\n" +
                                    "                </dd>\r\n";
                            relations.add(body);

                        }

                    } else if (relationType.equals(Utils.OneToMany)) {
                        //DONE    nomeTabellaSx ==> nomeRelazioneDx    /   autore ==> preferito2
                        if (nomeTabellaDx.toLowerCase().equals(nomeTabella)) {
                            String body = "\n                <!-- Add Relation    Name: " + nomeRelazioneDx + "     Type: OneToMany -->\n";
                            body += "                <dt><span jhiTranslate=\"" + conf.getProjectName() + "App." + Utils.getFirstLowerCase(nomeTabellaDx) + "." + Utils.getFirstLowerCase(nomeRelazioneDx) + "\">" + Utils.getFirstUpperCase(nomeRelazioneDx) + "</span></dt>\r\n" +
                                    "                <dd>\r\n" +
                                    "                    <div *ngIf=\"" + Utils.getFirstLowerCase(nomeTabellaDx) + "." + Utils.getFirstLowerCase(nomeRelazioneDx) + "Id\">\r\n" +
                                    "                        <a [routerLink]=\"['/" + Utils.getFirstLowerCase(nomeTabellaSx) + "', " + Utils.getFirstLowerCase(nomeTabellaDx) + "." + Utils.getFirstLowerCase(nomeRelazioneDx) + "Id, 'view']\">{{" + Utils.getFirstLowerCase(nomeTabellaDx) + "." + Utils.getFirstLowerCase(nomeRelazioneDx) + "" + Utils.getFirstUpperCase(nomeSelectDx) + "}}</a>\r\n" +
                                    "                    </div>\r\n" +
                                    "                </dd>\r\n";
                            relations.add(body);
                        }

                    } else if (relationType.equals(Utils.ManyToMany)) {
                        if (nomeTabellaSx.toLowerCase().equals(nomeTabella)) {
                            String body = "\n                <!-- Add Relation: ManyToMany -->\n";
                            body += "                <dt><span jhiTranslate=\"" + conf.getProjectName() + "App." + Utils.getFirstLowerCase(nomeTabellaSx) + "." + Utils.getFirstLowerCase(nomeRelazioneSx) + "\">" + Utils.getFirstUpperCase(nomeRelazioneSx) + "</span></dt>\r\n" +
                                    "                <dd>\r\n" +
                                    "                    <span *ngFor=\"let " + Utils.getFirstLowerCase(nomeRelazioneSx) + " of " + Utils.getFirstLowerCase(nomeTabellaSx) + "." + Utils.getFirstLowerCase(nomeRelazioneSx) + "s; let last = last\">\n" +
                                    "                        <a [routerLink]=\"['/" + Utils.getFirstLowerCase(nomeTabellaDx) + "', " + Utils.getFirstLowerCase(nomeRelazioneSx) + "?.id, 'view']\">{{" + Utils.getFirstLowerCase(nomeRelazioneSx) + "." + Utils.getFirstLowerCase(nomeSelectSx) + "}}</a>{{last ? '' : ', '}}\n" +
                                    "                    </span>\r\n" +
                                    "                </dd>\r\n";
                            relations.add(body);
                        }
                    }
                }
            }
        }
        return relations;
    }

    public String getClassName() {
        return Utils.getClassNameLowerCase(tabella) + "-detail.component";
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
