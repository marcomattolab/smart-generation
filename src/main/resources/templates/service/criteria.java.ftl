package ${packageName}.${dtoPackage};

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the ${entityName} entity. This class is used in ${entityName}Resource to
 * receive all the possible filtering options from the Http GET request.
 * For example the following could be a valid request:
 * <code> /${entityFieldName}s?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ${className} implements Serializable {
    private static final long serialVersionUID = 1L;

<#list fields as field>
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
        final ${className} that = (${className}) o;
        return
<#list fields as field>
            Objects.equals(${field.name}, that.${field.name})<#if field_has_next> &&</#if>
</#list>
        ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
<#list fields as field>
            ${field.name}<#if field_has_next>,</#if>
</#list>
        );
    }

    @Override
    public String toString() {
        return "${className}{" +
<#list fields as field>
            <#if field_index = 0>
            "${field.name}=" + get${field.capitalizedName}() +
            <#else>
            ", ${field.name}=" + get${field.capitalizedName}() +
            </#if>
</#list>
            "}";
    }

}
