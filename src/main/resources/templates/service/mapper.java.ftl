package ${packageName}.${mapperPackage};

import ${packageName}.${domainPackage}.*;
import ${packageName}.${dtoPackage}.${dtoName};

import org.mapstruct.*;

/**
 * Mapper for the entity ${entityName} and its DTO ${dtoName}.
 */
@Mapper(componentModel = "spring", uses = {<#list usesMappers as mapper>${mapper}.class<#if mapper_has_next>, </#if></#list>})
public interface ${className} extends EntityMapper<${dtoName}, ${entityName}> {

<#list toDtoMappings as mapping>
    ${mapping}
</#list>
<#if customToDto>
    ${dtoName} toDto(${entityName} ${entityName?uncap_first});

</#if>
<#list toEntityMappings as mapping>
    ${mapping}
</#list>
    ${entityName} toEntity(${dtoName} ${dtoName?uncap_first});

    default ${entityName} fromId(Long id) {
        if (id == null) {
            return null;
        }
        ${entityName} ${entityName?uncap_first} = new ${entityName}();
        ${entityName?uncap_first}.setId(id);
        return ${entityName?uncap_first};
    }
}
