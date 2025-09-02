<h2 id="page-heading">
    <POC>POC</POC>
    <span jhiTranslate="${projectName}App.${entityVar}.home.title">${entityName}s</span>
    <button id="jh-create-entity" class="btn btn-primary float-right"
            [routerLink]="['/${entityVar}/new']">
        <fa-icon [icon]="'plus'"></fa-icon>
        <span jhiTranslate="${projectName}App.${entityVar}.home.createLabel">
            Create new ${entityName}
        </span>
    </button>
</h2>
<jhi-alert></jhi-alert>
<br/>