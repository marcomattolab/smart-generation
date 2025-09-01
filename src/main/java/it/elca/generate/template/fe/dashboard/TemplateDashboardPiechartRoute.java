package it.elca.generate.template.fe.dashboard;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;

public class TemplateDashboardPiechartRoute extends AbstractResourceTemplate {

	public TemplateDashboardPiechartRoute(DataBase database) {
		super(database);
	}

	public String getTypeFile() {
		return "ts";
	}

	public String getBody(){
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		// https://www.buildmystring.com/
		String body = 
		"import { Route } from '@angular/router';\r\n" + 
		"import { UserRouteAccessService } from '../../core';\r\n" + 
		"import { PiechartComponent } from './piechart.component';\r\n\n" + 
		"export const piechartRoute: Route = {\r\n" + 
		"    path: 'piechart',\r\n" + 
		"    component: PiechartComponent,\r\n" + 
		"    data: {\r\n" + 
		"        authorities: ["+Utils.getGlobalAuthorities(conf, Utils.APICE)+"],\r\n" + 
		"        pageTitle: 'dashboard.piechart.home.title'\r\n" + 
		"    },\r\n" + 
		"    canActivate: [UserRouteAccessService]\r\n" + 
		"};\r\n" + 
		"";
		return body;
	}

	public String getClassName(){
		return "piechart.route";
	}

	@Override
	public String getTypeTemplate() {
		String typeTemplate = "";
		return typeTemplate;
	}

	@Override
	public String getSourceFolder() {
		return "src/main/webapp/app/dashboard/piechart";
	}

}
