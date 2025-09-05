package ${packageClass}.${srcDomainFolder};

import io.swagger.annotations.ApiModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ${packageClass}.${srcDomainFolder}.enumeration.*;

/**
 * Entity ${className}
 */
@ApiModel(description = "Entity ${className}")
@Entity
@Table(name = "${className}")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ${className} extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

<#list columns as column>
    ${column.field}
</#list>

<#list columns as column>
    ${column.getterAndSetter}
</#list>

<#list relations as relation>
    ${relation}
</#list>

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ${className} object = (${className}) o;
        if (object.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), object.getId());
    }

    @Override
    public String toString() {
        return "${className}{" +
<#list columns as column>
            "${column.toString?js_string}"<#if column_has_next> + ", "</#if> +
</#list>
            '}';
    }
}
