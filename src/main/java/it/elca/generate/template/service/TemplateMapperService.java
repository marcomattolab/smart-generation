package it.elca.generate.template.service;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.ProjectRelation;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TemplateMapperService extends AbstractTemplate {

    public TemplateMapperService(Table tabella) {
        super(tabella);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", conf.getPackageclass());
        data.put("mapperPackage", conf.getSrcServiceMapperFolder());
        data.put("domainPackage", conf.getSrcDomainFolder());
        data.put("dtoPackage", conf.getSrcServiceDtoFolder());
        data.put("entityName", Utils.getEntityName(tabella));
        data.put("dtoName", Utils.getEntityName(tabella) + "DTO");
        data.put("className", getClassName());

        Set<String> usesMappers = new HashSet<>();
        List<String> toDtoMappings = new ArrayList<>();
        List<String> toEntityMappings = new ArrayList<>();
        boolean customToDto = false;

        if (!CollectionUtils.isEmpty(conf.getProjectRelations())) {
            for (ProjectRelation rel : conf.getProjectRelations()) {
                processRelation(rel, usesMappers, toDtoMappings, toEntityMappings);
                if (!toDtoMappings.isEmpty()) {
                    customToDto = true;
                }
            }
        }

        data.put("usesMappers", usesMappers);
        data.put("toDtoMappings", toDtoMappings);
        data.put("toEntityMappings", toEntityMappings);
        data.put("customToDto", customToDto);

        return FreemarkerTemplate.process("service/mapper.java.ftl", data);
    }

    private void processRelation(ProjectRelation rel, Set<String> usesMappers, List<String> toDtoMappings, List<String> toEntityMappings) {
        String relationType = rel.getType();
        String sxTable = rel.getSxTable().toLowerCase();
        String dxTable = rel.getDxTable().toLowerCase();
        String currentTable = tabella.getNomeTabella().toLowerCase();

        if (sxTable.equals(currentTable) || dxTable.equals(currentTable)) {
            if (relationType.equals(Utils.OneToOne) || relationType.equals(Utils.ManyToOne)) {
                if (sxTable.equals(currentTable)) {
                    usesMappers.add(Utils.getFirstUpperCase(dxTable) + "Mapper");
                    toDtoMappings.add(createMapping(rel.getSxName() + ".id", rel.getSxName() + "Id"));
                    toDtoMappings.add(createMapping(rel.getSxName() + "." + rel.getSxSelect(), rel.getSxName() + Utils.getFirstUpperCase(rel.getSxSelect())));
                    toEntityMappings.add(createMapping(rel.getSxName() + "Id", rel.getSxName()));
                }
            } else if (relationType.equals(Utils.OneToMany)) {
                if (sxTable.equals(currentTable)) {
                    toEntityMappings.add("@Mapping(target = \"" + Utils.getFirstLowerCase(rel.getSxName()) + "s\", ignore = true)");
                } else if (dxTable.equals(currentTable)) {
                    usesMappers.add(Utils.getFirstUpperCase(sxTable) + "Mapper");
                    toDtoMappings.add(createMapping(Utils.getFirstLowerCase(rel.getDxName()) + ".id", Utils.getFirstLowerCase(rel.getDxName()) + "Id"));
                    toDtoMappings.add(createMapping(Utils.getFirstLowerCase(rel.getDxName()) + "." + rel.getDxSelect(), Utils.getFirstLowerCase(rel.getDxName()) + Utils.getFirstUpperCase(rel.getDxSelect())));
                    toEntityMappings.add(createMapping(Utils.getFirstLowerCase(rel.getDxName()) + "Id", Utils.getFirstLowerCase(rel.getDxName())));
                }
            } else if (relationType.equals(Utils.ManyToMany)) {
                if (sxTable.equals(currentTable)) {
                    usesMappers.add(Utils.getFirstUpperCase(dxTable) + "Mapper");
                }
                if (dxTable.equals(currentTable)) {
                    toEntityMappings.add("@Mapping(target = \"" + Utils.getFirstLowerCase(rel.getDxName()) + "s\", ignore = true)");
                }
            }
        }
    }

    private String createMapping(String source, String target) {
        return "@Mapping(source = \"" + source + "\", target = \"" + target + "\")";
    }

    public String getClassName() {
        return Utils.getMapperClassName(tabella);
    }

    @Override
    public String getTypeTemplate() {
        return ConfigCreateProject.getIstance().getSrcServiceMapperFolder().replace(".", "/");
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
