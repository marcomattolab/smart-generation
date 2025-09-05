import { Moment } from 'moment';
<#list imports as anImport>
${anImport}
</#list>

<#list enumerations as enum>
export const enum ${enum.name} {
<#list enum.values as value>
    ${value} = '${value}'<#if value_has_next>,</#if>
</#list>
}

</#list>
export interface ${iName} {
<#list fields as field>
    ${field.name}?: ${field.type};
</#list>
}

export class ${className} implements ${iName} {
    constructor(
<#list fields as field>
        public ${field.name}?: ${field.type}<#if field_has_next>,</#if>
</#list>
    ) {}
}
