{
    "${projectName}App": {
        "${enumerationName}": {
            "null": "",
<#list values as value>
            "${value}": "${value}"<#if value_has_next>,</#if>
</#list>
        }
    }
}
