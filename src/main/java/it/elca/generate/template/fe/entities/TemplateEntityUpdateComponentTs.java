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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TemplateEntityUpdateComponentTs extends AbstractResourceTemplate {

    public TemplateEntityUpdateComponentTs(Table tabella) {
        super(tabella);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = super.getMapData();
        data.put("entityName", Utils.getEntityName(tabella));
        data.put("classNameLowerCase", Utils.getClassNameLowerCase(tabella));
        data.put("iName", Utils.getIName(tabella));

        // Date fields
        List<Map<String, Object>> dateFields = new ArrayList<>();
        for (Column column : tabella.getColumns()) {
            if (Utils.isDateField(column)) {
                Map<String, Object> field = new HashMap<>();
                field.put("fieldName", Utils.getFieldName(column));
                field.put("isLocalDate", Utils.isLocalDate(column));
                dateFields.add(field);
            }
        }
        data.put("dateFields", dateFields);

        // Relations
        if (!CollectionUtils.isEmpty(conf.getProjectRelations())) {
            Set<String> imports = new HashSet<>();
            Set<String> properties = new HashSet<>();
            Set<String> constructorParams = new HashSet<>();
            List<String> ngOnInitCode = new ArrayList<>();
            Set<String> trackByMethods = new HashSet<>();

            for (ProjectRelation rel : conf.getProjectRelations()) {
                processRelation(rel, imports, properties, constructorParams, ngOnInitCode, trackByMethods);
            }

            data.put("imports", imports);
            data.put("properties", properties);
            data.put("constructorParams", constructorParams);
            data.put("ngOnInitCode", ngOnInitCode);
            data.put("trackByMethods", trackByMethods);
        }

        return FreemarkerTemplate.process("fe/entities/entity-update.component.ts.ftl", data);
    }

    private void processRelation(ProjectRelation rel, Set<String> imports, Set<String> properties, Set<String> constructorParams, List<String> ngOnInitCode, Set<String> trackByMethods) {
        String relationType = rel.getType();
        String nomeTabellaSx = rel.getSxTable();
        String nomeRelazioneSx = rel.getSxName();
        String nomeTabellaDx = rel.getDxTable();
        String nomeTabella = tabella.getNomeTabella().toLowerCase();

        if (nomeTabellaSx != null && nomeTabellaDx != null) {
            if ((relationType.equals(Utils.OneToOne) || relationType.equals(Utils.ManyToOne)) && nomeTabellaSx.toLowerCase().equals(nomeTabella)) {
                addImports(imports, nomeTabellaDx);
                properties.add(Utils.getFirstLowerCase(nomeRelazioneSx) + "s: I" + Utils.getFirstUpperCase(nomeTabellaDx) + "[];");
                if (relationType.equals(Utils.OneToOne)) {
                    properties.add("id" + Utils.getFirstUpperCase(nomeRelazioneSx) + ": any;");
                }
                constructorParams.add("private " + Utils.getFirstLowerCase(nomeTabellaDx) + "Service: " + Utils.getFirstUpperCase(nomeTabellaDx) + "Service");
                trackByMethods.add(getTrackByMethod(nomeTabellaDx));
                ngOnInitCode.add(getNgOnInitForRelation(rel));
            } else if (relationType.equals(Utils.OneToMany) && nomeTabellaDx.toLowerCase().equals(nomeTabella)) {
                 addImports(imports, nomeTabellaSx);
                properties.add(Utils.getFirstLowerCase(rel.getDxName()) + "s: I" + Utils.getFirstUpperCase(nomeTabellaSx) + "[];");
                constructorParams.add("private " + Utils.getFirstLowerCase(nomeTabellaSx) + "Service: " + Utils.getFirstUpperCase(nomeTabellaSx) + "Service");
                trackByMethods.add(getTrackByMethod(nomeTabellaSx));
                ngOnInitCode.add(getNgOnInitForRelation(rel));
            } else if (relationType.equals(Utils.ManyToMany) && nomeTabellaSx.toLowerCase().equals(nomeTabella)) {
                addImports(imports, nomeTabellaDx);
                properties.add(Utils.getFirstLowerCase(nomeRelazioneSx) + "s: I" + Utils.getFirstUpperCase(nomeTabellaDx) + "[];");
                constructorParams.add("private " + Utils.getFirstLowerCase(nomeTabellaDx) + "Service: " + Utils.getFirstUpperCase(nomeTabellaDx) + "Service");
                trackByMethods.add(getTrackByMethod(nomeTabellaDx));
                ngOnInitCode.add(getNgOnInitForRelation(rel));
            }
        }
    }

    private void addImports(Set<String> imports, String otherTable) {
        imports.add("import { I" + Utils.getFirstUpperCase(otherTable) + " } from 'app/shared/model/" + Utils.getFirstLowerCase(otherTable) + ".model';");
        imports.add("import { " + Utils.getFirstUpperCase(otherTable) + "Service } from 'app/entities/" + Utils.getFirstLowerCase(otherTable) + "';");
    }

    private String getTrackByMethod(String otherTable) {
        String otherEntity = Utils.getFirstUpperCase(otherTable);
        return "track" + otherEntity + "ById(index: number, item: I" + otherEntity + ") {\n" +
               "        return item.id;\n" +
               "    }";
    }

    private String getNgOnInitForRelation(ProjectRelation rel) {
        String sxTable = rel.getSxTable();
        String dxTable = rel.getDxTable();
        String sxName = rel.getSxName();
        String dxName = rel.getDxName();
        String type = rel.getType();

        if (type.equals(Utils.OneToOne)) {
            return "this." + Utils.getFirstLowerCase(dxTable) + "Service.query({ filter: '" + Utils.getFirstLowerCase(dxName) + "-is-null' }).subscribe(\n" +
                   "    (res: HttpResponse<I" + Utils.getFirstUpperCase(dxTable) + "[]>) => {\n" +
                   "        if (!this." + Utils.getFirstLowerCase(sxTable) + "." + Utils.getFirstLowerCase(sxName) + "Id) {\n" +
                   "            this." + Utils.getFirstLowerCase(sxName) + "s = res.body;\n" +
                   "        } else {\n" +
                   "            this." + Utils.getFirstLowerCase(dxTable) + "Service.find(this." + Utils.getFirstLowerCase(sxTable) + "." + Utils.getFirstLowerCase(sxName) + "Id).subscribe(\n" +
                   "                (subRes: HttpResponse<I" + Utils.getFirstUpperCase(dxTable) + ">) => {\n" +
                   "                    this." + Utils.getFirstLowerCase(sxName) + "s = [subRes.body].concat(res.body);\n" +
                   "                },\n" +
                   "                (subRes: HttpErrorResponse) => this.onError(subRes.message)\n" +
                   "            );\n" +
                   "        }\n" +
                   "    },\n" +
                   "    (res: HttpErrorResponse) => this.onError(res.message)\n" +
                   ");";
        } else {
            String serviceName = type.equals(Utils.OneToMany) ? Utils.getFirstLowerCase(sxTable) : Utils.getFirstLowerCase(dxTable);
            String propertyName = type.equals(Utils.OneToMany) ? Utils.getFirstLowerCase(dxName) : Utils.getFirstLowerCase(sxName);
            String entityName = type.equals(Utils.OneToMany) ? Utils.getFirstUpperCase(sxTable) : Utils.getFirstUpperCase(dxTable);

            return "this." + serviceName + "Service.query().subscribe(\n" +
                   "    (res: HttpResponse<I" + entityName + "[]>) => {\n" +
                   "        this." + propertyName + "s = res.body;\n" +
                   "    },\n" +
                   "    (res: HttpErrorResponse) => this.onError(res.message)\n" +
                   ");";
        }
    }


    public String getClassName() {
        return Utils.getClassNameLowerCase(tabella) + "-update.component";
    }

    @Override
    public String getTypeFile() {
        return "ts";
    }

    @Override
    public String getTypeTemplate() {
        return "";
    }

    @Override
    public String getSourceFolder() {
        return "src/main/webapp/app/entities/" + Utils.getClassNameLowerCase(tabella);
    }
}
