{
    "${projectName}App": {
        "${classNameLowerCase}": {
            "home": {
                "title": "${entityName}s",
                "createLabel": "Create a new ${entityName}",
                "createOrEditLabel": "Create or edit a ${entityName}"
            },
            "created": "A new ${entityName} is created with identifier {{ param }}",
            "updated": "A ${entityName} is updated with identifier {{ param }}",
            "deleted": "A ${entityName} is deleted with identifier {{ param }}",
            "delete": {
                "question": "Are you sure you want to delete ${entityName} {{ id }}?"
            },
            "detail": {
                "title": "${entityName}"
            },
<#if relations?has_content>
<#list relations as rel>
            "${rel.name}": "${rel.capitalizedName}"<#if rel_has_next || columns?has_content>,</#if>
</#list>
</#if>
<#if columns?has_content>
<#list columns as column>
            "${column.name}": "${column.capitalizedName}"<#if column_has_next>,</#if>
</#list>
</#if>
        }
    }
}
