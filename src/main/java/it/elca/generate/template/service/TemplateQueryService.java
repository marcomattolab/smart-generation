package it.elca.generate.template.service;

import it.elca.generate.Column;
import it.elca.generate.ConfigCreateProject;
import it.elca.generate.ProjectRelation;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateQueryService extends AbstractTemplate {

    public TemplateQueryService(Table tabella) {
        super(tabella);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", conf.getPackageclass());
        data.put("servicePackage", conf.getSrcServiceFolder());
        data.put("domainPackage", conf.getSrcDomainFolder());
        data.put("repositoryPackage", conf.getSrcRepositoryFolder());
        data.put("dtoPackage", conf.getSrcServiceDtoFolder());
        data.put("mapperPackage", conf.getSrcServiceMapperFolder());
        data.put("securityPackage", conf.getSrcSecurityFolder());
        data.put("entityName", Utils.getEntityName(tabella));
        data.put("className", getClassName());

        // Fields
        List<Map<String, Object>> fields = new ArrayList<>();
        for (Object o : tabella.getColumnNames()) {
            String key = (String) o;
            Column column = tabella.getColumn(key);
            if (!Utils.isBlob(column) && !Utils.isClob(column)) {
                Map<String, Object> fieldData = new HashMap<>();
                fieldData.put("capitalizedName", Utils.getFieldNameForMethod(column));
                fieldData.put("nameForSpec", Utils.getFieldNameForMethodReplace(column.getName(), true));
                fieldData.put("isString", column.getTypeColumn().getName().equals("java.lang.String") && column.getEnumeration() == null);
                fieldData.put("isNumeric", Utils.isNumericField(column) && !Utils.isPrimaryKeyID(column));
                fieldData.put("isDate", Utils.isDateField(column));
                fields.add(fieldData);
            }
        }
        data.put("fields", fields);

        // Relations
        List<Map<String, Object>> relations = new ArrayList<>();
        if (!CollectionUtils.isEmpty(conf.getProjectRelations())) {
            for (ProjectRelation rel : conf.getProjectRelations()) {
                processRelation(rel, relations);
            }
        }
        data.put("relations", relations);

        // Security
        Map<String, Object> security = new HashMap<>();
        security.put("enabled", true);
        List<String> profiles = getProfilesNormalized(conf);
        security.put("profiles", profiles);
        security.put("defaultUserProfile", getDefaultUserProfile(profiles));
        data.put("security", security);

        return FreemarkerTemplate.process("service/query-service.java.ftl", data);
    }

    private void processRelation(ProjectRelation rel, List<Map<String, Object>> relations) {
        String relationType = rel.getType();
        String sxTable = rel.getSxTable().toLowerCase();
        String dxTable = rel.getDxTable().toLowerCase();
        String currentTable = tabella.getNomeTabella().toLowerCase();

        if (sxTable.equals(currentTable) || dxTable.equals(currentTable)) {
            Map<String, Object> relData = new HashMap<>();
            String name = null, joinField = null, otherEntity = null;

            if (relationType.equals(Utils.OneToOne) && sxTable.equals(currentTable)) {
                name = rel.getSxName();
                joinField = Utils.getFirstLowerCase(name);
                otherEntity = Utils.getFirstUpperCase(dxTable);
            } else if (relationType.equals(Utils.ManyToMany)) {
                if (sxTable.equals(currentTable)) {
                    name = rel.getSxName();
                    joinField = Utils.getFirstLowerCase(name) + "s";
                    otherEntity = Utils.getFirstUpperCase(dxTable);
                } else { // dxTable.equals(currentTable)
                    name = rel.getDxName();
                    joinField = Utils.getFirstLowerCase(name) + "s";
                    otherEntity = Utils.getFirstUpperCase(sxTable);
                }
            } else if (relationType.equals(Utils.OneToMany)) {
                if (sxTable.equals(currentTable)) {
                    name = rel.getSxName();
                    joinField = Utils.getFirstLowerCase(name) + "s";
                    otherEntity = Utils.getFirstUpperCase(dxTable);
                } else { // dxTable.equals(currentTable)
                    name = rel.getDxName();
                    joinField = Utils.getFirstLowerCase(name);
                    otherEntity = Utils.getFirstUpperCase(sxTable);
                }
            } else if (relationType.equals(Utils.ManyToOne) && sxTable.equals(currentTable)) {
                name = rel.getSxName();
                joinField = Utils.getFirstLowerCase(name);
                otherEntity = Utils.getFirstUpperCase(dxTable);
            }

            if (name != null) {
                relData.put("capitalizedName", Utils.getFirstUpperCase(name));
                relData.put("joinField", joinField);
                relData.put("otherEntityName", otherEntity);
                relations.add(relData);
            }
        }
    }

    private List<String> getProfilesNormalized(ConfigCreateProject conf) {
        List<String> profilesNormalized = new ArrayList<>();
        for (String profile : conf.getProfiles()) {
            profilesNormalized.add(profile.replace("ROLE_", ""));
        }
        return profilesNormalized;
    }

    private String getDefaultUserProfile(List<String> profiles) {
        for (String profile : profiles) {
            if (!profile.equals("ADMIN") && !profile.equals("ANONYMOUS")) {
                return profile;
            }
        }
        return "USER";
    }

    public String getClassName() {
        return Utils.getQueryServiceClassName(tabella);
    }

    @Override
    public String getTypeTemplate() {
        return ConfigCreateProject.getIstance().getSrcServiceFolder().replace(".", "/");
    }

    @Override
    public String getTypeFile() {
        return "java";
    }

    @Override
    public String getSourceFolder() {
        return "src/main/java";
    }
}
