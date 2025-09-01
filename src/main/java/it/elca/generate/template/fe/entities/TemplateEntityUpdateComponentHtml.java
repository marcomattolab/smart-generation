package it.elca.generate.template.fe.entities;

import org.springframework.util.CollectionUtils;

import it.elca.generate.Column;
import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.ProjectRelation;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;

public class TemplateEntityUpdateComponentHtml extends AbstractResourceTemplate {

	public TemplateEntityUpdateComponentHtml(Table tabella) {
		super(tabella);
	}

	public TemplateEntityUpdateComponentHtml(DataBase database, Table tabella) {
		super(database);
		this.tabella = tabella;
	}

	public String getTypeFile() {
		return "html";
	}

	public String getBody(){
		// https://www.buildmystring.com/
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		
		String Nometabella = Utils.getEntityName(tabella);
		String nometabella = Utils.getClassNameLowerCase(tabella);

		String body = 
		"<div class=\"row justify-content-center\">\r\n" +
		"    <div class=\"col-8\">\r\n" +
		"        <form name=\"editForm\" role=\"form\" novalidate (ngSubmit)=\"save()\" #editForm=\"ngForm\">\r\n" +
		"            <h2 id=\"jhi-"+nometabella+"-heading\" jhiTranslate=\""+conf.getProjectName()+"App."+nometabella+".home.createOrEditLabel\">Create or edit a "+Nometabella+"</h2>\r\n" +

		// MAIN CICLE DL - START
		"            <div>\r\n" ;
		for (Column column : tabella.getSortedColumns()) {
			body += Utils.getTemplateHtmlByType(database, column, tabella, conf);
		}
		
		//Relations management
		if(!CollectionUtils.isEmpty(conf.getProjectRelations())) {
			for(ProjectRelation rel: conf.getProjectRelations()) {
				String relationType = rel.getType();
				String nomeTabellaSx = rel.getSxTable();
				String nomeRelazioneSx = rel.getSxName();
				String nomeRelazioneDx = rel.getDxName();
				String nomeTabellaDx = rel.getDxTable();
				String nomeSelectSx = rel.getSxSelect();
				String nomeSelectDx = rel.getDxSelect();
				String nomeTabella = tabella.getNomeTabella().toLowerCase();
				
				if(nomeTabellaSx!=null && nomeTabellaDx != null) {
					if(relationType.equals(Utils.OneToOne) || relationType.equals(Utils.ManyToOne)) {
						if ( nomeTabellaSx.toLowerCase().equals(nomeTabella) ) {
						String track = (relationType.equals(Utils.OneToOne)) ? "; trackBy: track"+Utils.getFirstUpperCase(nomeRelazioneSx)+"ById" : "";
						body += "\n               <!-- Add Relation: OneToOne / ManyToOne -->";
						body += "\n                <div class=\"form-group\">\r\n" +
								"             		<label class=\"form-control-label\" jhiTranslate=\""+conf.getProjectName()+"App."+Utils.getFirstLowerCase(nomeTabellaSx)+"."+nomeRelazioneSx+"\" for=\"field_"+nomeRelazioneSx+"\">"+Utils.getFirstUpperCase(nomeRelazioneSx)+"</label>\r\n" +
								"             		<select class=\"form-control\" id=\"field_"+nomeRelazioneSx+"\" name=\""+nomeRelazioneSx+"\" [(ngModel)]=\""+Utils.getFirstLowerCase(nomeTabellaSx)+"."+nomeRelazioneSx+"Id\">\r\n" +
								"                 		<option [ngValue]=\"null\"></option>\r\n" +
								"                 		<option [ngValue]=\""+nomeRelazioneSx+"Option.id\" *ngFor=\"let "+nomeRelazioneSx+"Option of "+nomeRelazioneSx+"s"+track+"\">{{"+nomeRelazioneSx+"Option."+nomeSelectSx+"}}</option>\r\n" +
								"             		</select>\r\n" +
								"                </div>\r\n\n";
						}
						
					} else if(relationType.equals(Utils.OneToMany)) {
						if ( nomeTabellaDx.toLowerCase().equals(nomeTabella) ) {
							body += "\n               <!-- Add Relation  Name: "+nomeRelazioneDx+" Type: OneToMany -->";
							String track = "; trackBy: track"+Utils.getFirstUpperCase(nomeTabellaSx)+"ById";
							// DONE TEST   "relations"   autore  ==> preferito2   /      nomeTabellaSx ==> nomeRelazioneDx
							String selectName = Utils.getFirstLowerCase(nomeRelazioneDx)+"s";
							
							body += "\n                <div class=\"form-group\">\r\n" +
									"             		<label class=\"form-control-label\" jhiTranslate=\""+conf.getProjectName()+"App."+Utils.getFirstLowerCase(nomeTabellaDx)+"."+Utils.getFirstLowerCase(nomeRelazioneDx)+"\" for=\"field_"+Utils.getFirstLowerCase(nomeRelazioneDx)+"\">"+Utils.getFirstUpperCase(nomeRelazioneDx)+"</label>\r\n" +
									"             		<select class=\"form-control\" id=\"field_"+Utils.getFirstLowerCase(nomeRelazioneDx)+"\" name=\""+Utils.getFirstLowerCase(nomeRelazioneDx)+"\" [(ngModel)]=\""+Utils.getFirstLowerCase(nomeTabellaDx)+"."+Utils.getFirstLowerCase(nomeRelazioneDx)+"Id\">\r\n" +
									"                 		<option [ngValue]=\"null\"></option>\r\n" +
									"                 		<option [ngValue]=\""+Utils.getFirstLowerCase(nomeRelazioneDx)+"Option.id\" *ngFor=\"let "+Utils.getFirstLowerCase(nomeRelazioneDx)+"Option of "+selectName+track+"\">{{"+Utils.getFirstLowerCase(nomeRelazioneDx)+"Option."+nomeSelectDx+"}}</option>\r\n" +
									"             		</select>\r\n" +
									"                </div>\r\n\n";
							
							
							}
						
					} else if(relationType.equals(Utils.ManyToMany)) {
						if ( nomeTabellaSx.toLowerCase().equals(nomeTabella) ) {
							//Company{myKeyword(keywordCode)} to CompanyKeyword{myCompany(companyName)}
							// FIX settores ==> mysectors / nomeTabellaDx ==> nomeRelazioneSx
							body += "\n                <!-- Add Relation   Name: "+nomeRelazioneSx+"   Type: ManyToMany   -->";
							body += "\n                <div class=\"form-group\">\r\n" +
									"             		<label class=\"form-control-label\" jhiTranslate=\""+conf.getProjectName()+"App."+Utils.getFirstLowerCase(nomeTabellaSx)+"."+nomeRelazioneSx.toLowerCase()+"\" for=\"field_"+Utils.getFirstLowerCase(nomeRelazioneSx)+"\">"+Utils.getFirstUpperCase(nomeRelazioneSx)+"</label>\r\n" +
									"             		<select class=\"form-control\" id=\"field_"+Utils.getFirstLowerCase(nomeRelazioneSx)+"\" multiple name=\""+Utils.getFirstLowerCase(nomeRelazioneSx)+"\" [(ngModel)]=\""+Utils.getFirstLowerCase(nomeTabellaSx)+"."+Utils.getFirstLowerCase(nomeRelazioneSx)+"s\">\r\n" +
									"                 		<option [ngValue]=\"getSelected("+Utils.getFirstLowerCase(nomeTabellaSx)+"."+Utils.getFirstLowerCase(nomeRelazioneSx)+"s, "+Utils.getFirstLowerCase(nomeRelazioneSx)+"Option)\" *ngFor=\"let "+Utils.getFirstLowerCase(nomeRelazioneSx)+"Option of "+Utils.getFirstLowerCase(nomeRelazioneSx)+"s"+" trackBy: track"+Utils.getFirstUpperCase(nomeTabellaDx)+"ById\">{{"+Utils.getFirstLowerCase(nomeRelazioneSx)+"Option."+nomeSelectSx+"}}</option>\r\n" +
									"             		</select>\r\n" +
									"                </div>\r\n\n";
							}

					}
					
				}
				
			}
		}
		
		
         
		body +=
		"            </div>\r\n" ;
		// MAIN CICLE DL - END
		
		body +=
		"            <div>\r\n" +
		"                <button type=\"button\" id=\"cancel-save\" class=\"btn btn-secondary\"  (click)=\"previousState()\">\r\n" +
		"                    <fa-icon [icon]=\"'ban'\"></fa-icon>&nbsp;<span jhiTranslate=\"entity.action.cancel\">Cancel</span>\r\n" +
		"                </button>\r\n" +
		"                <button type=\"submit\" id=\"save-entity\" [disabled]=\"editForm.form.invalid || isSaving\" class=\"btn btn-primary\">\r\n" +
		"                    <fa-icon [icon]=\"'save'\"></fa-icon>&nbsp;<span jhiTranslate=\"entity.action.save\">Save</span>\r\n" +
		"                </button>\r\n" +
		"            </div>\r\n" +
		"        </form>\r\n" +
		"    </div>\r\n" +
		"</div>\r\n";
		return body;
	}
	
	public String getClassName(){
		return Utils.getClassNameLowerCase(tabella)+"-update.component";
	}

	@Override
	public String getTypeTemplate() {
		String typeTemplate = "";
		return typeTemplate;
	}

	@Override
	public String getSourceFolder() {
		return "src/main/webapp/app/entities/"+Utils.getClassNameLowerCase(tabella);
	}

}
