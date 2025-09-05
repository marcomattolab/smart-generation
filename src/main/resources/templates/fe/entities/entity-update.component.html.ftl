<div class="row justify-content-center">
    <div class="col-8">
        <form name="editForm" role="form" novalidate (ngSubmit)="save()" #editForm="ngForm">
            <h2 id="jhi-${classNameLowerCase}-heading" jhiTranslate="${projectName}App.${classNameLowerCase}.home.createOrEditLabel">Create or edit a ${entityName}</h2>
            <div>
                <#list columns as column>
                    ${column.html}
                </#list>

                <#if relations?has_content>
                    <#list relations as rel>
                        <#if rel.relationType == "OneToOne" || rel.relationType == "ManyToOne">
                            <#if rel.ownerSide>
               <!-- Add Relation: ${rel.relationType} -->
                <div class="form-group">
			<label class="form-control-label" jhiTranslate="${projectName}App.${classNameLowerCase}.${rel.relationshipName}" for="field_${rel.relationshipName}">${rel.relationshipNameCapitalized}</label>
			<select class="form-control" id="field_${rel.relationshipName}" name="${rel.relationshipName}" [(ngModel)]="${classNameLowerCase}.${rel.relationshipName}Id">
				<option [ngValue]="null"></option>
				<option [ngValue]="${rel.relationshipName}Option.id" *ngFor="let ${rel.relationshipName}Option of ${rel.relationshipName}s; trackBy: track${rel.otherEntityNameCapitalized}ById">{{${rel.relationshipName}Option.${rel.otherEntityField}}}</option>
			</select>
                </div>
                            </#if>
                        </#if>
                        <#if rel.relationType == "OneToMany">
                            <#if !rel.ownerSide>
               <!-- Add Relation  Name: ${rel.relationshipName} Type: OneToMany -->
                <div class="form-group">
			<label class="form-control-label" jhiTranslate="${projectName}App.${classNameLowerCase}.${rel.relationshipName}" for="field_${rel.relationshipName}">${rel.relationshipNameCapitalized}</label>
			<select class="form-control" id="field_${rel.relationshipName}" name="${rel.relationshipName}" [(ngModel)]="${classNameLowerCase}.${rel.relationshipName}Id">
				<option [ngValue]="null"></option>
				<option [ngValue]="${rel.relationshipName}Option.id" *ngFor="let ${rel.relationshipName}Option of ${rel.relationshipName}s; trackBy: track${rel.otherEntityNameCapitalized}ById">{{${rel.relationshipName}Option.${rel.otherEntityField}}}</option>
			</select>
                </div>
                            </#if>
                        </#if>
                        <#if rel.relationType == "ManyToMany">
                            <#if rel.ownerSide>
                <!-- Add Relation   Name: ${rel.relationshipName}   Type: ManyToMany   -->
                <div class="form-group">
			<label class="form-control-label" jhiTranslate="${projectName}App.${classNameLowerCase}.${rel.relationshipName}" for="field_${rel.relationshipName}">${rel.relationshipNameCapitalized}</label>
			<select class="form-control" id="field_${rel.relationshipName}" multiple name="${rel.relationshipName}" [(ngModel)]="${classNameLowerCase}.${rel.relationshipName}s">
				<option [ngValue]="getSelected(${classNameLowerCase}.${rel.relationshipName}s, ${rel.relationshipName}Option)" *ngFor="let ${rel.relationshipName}Option of ${rel.relationshipName}s trackBy: track${rel.otherEntityNameCapitalized}ById">{{${rel.relationshipName}Option.${rel.otherEntityField}}}</option>
			</select>
                </div>
                            </#if>
                        </#if>
                    </#list>
                </#if>
            </div>
            <div>
                <button type="button" id="cancel-save" class="btn btn-secondary"  (click)="previousState()">
                    <fa-icon [icon]="'ban'"></fa-icon>&nbsp;<span jhiTranslate="entity.action.cancel">Cancel</span>
                </button>
                <button type="submit" id="save-entity" [disabled]="editForm.form.invalid || isSaving" class="btn btn-primary">
                    <fa-icon [icon]="'save'"></fa-icon>&nbsp;<span jhiTranslate="entity.action.save">Save</span>
                </button>
            </div>
        </form>
    </div>
</div>
