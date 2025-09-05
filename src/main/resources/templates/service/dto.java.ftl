package ${packageName}.${dtoPackage};

import java.io.Serializable;
import java.util.Objects;
<#list imports as import>
import ${import};
</#list>

/**
 * A DTO for the ${entityName} entity.
 */
public class ${className} extends AbstractAuditingDTO implements Serializable {

<#list fields as field>
<#if field.annotations?has_content>
<#list field.annotations as annotation>
    ${annotation}
</#list>
</#if>
    private ${field.type} ${field.name};

</#list>
<#list fields as field>
    public ${field.type} get${field.capitalizedName}() {
        return ${field.name};
    }

    public void set${field.capitalizedName}(${field.type} ${field.name}) {
        this.${field.name} = ${field.name};
    }
</#list>

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ${className} ${entityName?uncap_first}DTO = (${className}) o;
        if (${entityName?uncap_first}DTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ${entityName?uncap_first}DTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "${className}{" +
<#list fields as field>
            ", ${field.name}='" + get${field.capitalizedName}() + "'" +
</#list>
            "}";
    }
}
