package ${packageClass}.${srcDomainEnumerationFolder};

/**
 * The ${enumerationName} enumeration.
 */
public enum ${enumerationName} {
<#list values as value>
    ${value}<#if value_has_next>,</#if>
</#list>
}
