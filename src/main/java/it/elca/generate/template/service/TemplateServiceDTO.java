package it.elca.generate.template.service;

import java.util.Iterator;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import it.elca.generate.Column;
import it.elca.generate.ConfigCreateProject;
import it.elca.generate.ProjectRelation;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;

public class TemplateServiceDTO extends AbstractTemplate{

	public TemplateServiceDTO(Table tabella) {
		super(tabella);
	}

	public String getTypeTemplate() {
		String typeTemplate = Utils.replace(ConfigCreateProject.getIstance().getSrcServiceDtoFolder(),".","/");
		return typeTemplate;
	}
	
	public String getTypeFile() {
		return "java";
	}

	public String getBody() {
		// https://www.buildmystring.com/
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		
		String body = 
		"package "+ conf.getPackageclass() + "." + conf.getSrcServiceDtoFolder()+";\r\n\n" +
		"import java.time.Instant;\r\n" +
		"import java.time.LocalDate;\r\n" +
		"import javax.validation.constraints.*;\r\n" +
		"import java.io.Serializable;\r\n" +
		"import java.util.Objects;\r\n" +
		"import java.util.HashSet;\r\n" +
		"import java.util.Set;\r\n" +
		"import javax.persistence.Lob;\r\n" +
		"import "+ conf.getPackageclass() + "." + conf.getSrcDomainEnumerationFolder()+".*;\r\n\n" +
		"/**\r\n" +
		" * A DTO for the "+Utils.getEntityName(tabella)+" entity.\r\n" +
		" */\r\n" +
		"public class "+getClassName()+" extends AbstractAuditingDTO implements Serializable {\r\n";

		Set<?> set = tabella.getColumnNames();
		for (Iterator<?> iter = set.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			Column column = tabella.getColumn(key);
			body += Utils.generaField(column)+"\n";
		}
		
		//[Relations]
		if(!CollectionUtils.isEmpty(conf.getProjectRelations())) {
			for(ProjectRelation rel: conf.getProjectRelations()) {
				String relationType = rel.getType();
				String nomeTabellaSx = rel.getSxTable();
				String nomeRelazioneSx = rel.getSxName();
				String nomeRelazioneDx = rel.getDxName();
				String nomeSelectSx = rel.getSxSelect();
				String nomeSelectDx = rel.getDxSelect();
				String nomeTabellaDx = rel.getDxTable();
				String nomeTabella = tabella.getNomeTabella().toLowerCase();
				
				if(nomeTabellaSx!=null && nomeTabellaDx != null) {
					
					if(relationType.equals(Utils.OneToOne) || relationType.equals(Utils.ManyToOne)) {
						if( nomeTabellaSx.toLowerCase().equals(nomeTabella) ) {
							Column columnId = new Column();
							columnId.setName(nomeRelazioneSx+"Id");
							columnId.setTypeColumn(Column.corvertModelType("Long"));
							
							Column columnSelect = new Column();
							columnSelect.setName(nomeRelazioneSx+Utils.getFirstUpperCase(nomeSelectSx));
							columnSelect.setTypeColumn(Utils.getTypeColumnFromRelation(conf, nomeSelectSx, nomeTabellaDx));
		
							body += Utils.generaField(columnId, false)+"\n";
							body += Utils.generaField(columnSelect, false)+"\n";
		
							body += Utils.generaAddForBeanSimple(columnId, getClassName(), false);
							body += Utils.generaAddForBeanSimple(columnSelect, getClassName(), false);
						}
						
					} else if(relationType.equals(Utils.OneToMany)) {
						if( nomeTabellaDx.toLowerCase().equals(nomeTabella) ) {
							//DONE  autore ==> preferito2    /    nomeTabellaSx => nomeRelazioneDx
							Column columnId = new Column();
							columnId.setName(Utils.getFirstLowerCase(nomeRelazioneDx)+"Id");
							columnId.setTypeColumn(Column.corvertModelType("Long"));
							
							Column columnSelect = new Column();
							columnSelect.setName(Utils.getFirstLowerCase(nomeRelazioneDx)+Utils.getFirstUpperCase(nomeSelectDx));
							columnSelect.setTypeColumn(Utils.getTypeColumnFromRelation(conf, nomeSelectDx, nomeTabellaSx));
		
							body += Utils.generaField(columnId, false)+"\n";
							body += Utils.generaField(columnSelect, false)+"\n";
		
							body += Utils.generaAddForBeanSimple(columnId, getClassName(), false);
							body += Utils.generaAddForBeanSimple(columnSelect, getClassName(), false);
							
						}
						
					} else if(relationType.equals(Utils.ManyToMany)) {
						//Company{myKeyword(keywordCode)} to CompanyKeyword{myCompany(companyName)}
					    //private Set<CompanyKeywordDTO> myKeywords = new HashSet<>();
						if( nomeTabellaSx.toLowerCase().equals(nomeTabella) ) {
							body += "\n\tprivate Set<"+Utils.getFirstUpperCase(nomeTabellaDx)+"DTO> "+Utils.getFirstLowerCase(nomeRelazioneSx)+"s = new HashSet<>();\n\n";
							body += "    public Set<"+Utils.getFirstUpperCase(nomeTabellaDx)+"DTO> get"+Utils.getFirstUpperCase(nomeRelazioneSx)+"s() {\r\n" +
									"        return "+Utils.getFirstLowerCase(nomeRelazioneSx)+"s;\r\n" +
									"    }\r\n\n" +
									"    public void set"+Utils.getFirstUpperCase(nomeRelazioneSx)+"s(Set<"+Utils.getFirstUpperCase(nomeTabellaDx)+"DTO> "+Utils.getFirstLowerCase(nomeTabellaDx)+"s) {\r\n" +
									"        this."+Utils.getFirstLowerCase(nomeRelazioneSx)+"s = "+Utils.getFirstLowerCase(nomeTabellaDx)+"s;\r\n" +
									"    }\r\n\n";
						}
					}
			    
				}
			}
		}
		//[/Relations]
		
		set = tabella.getColumnNames();
		for (Iterator<?> iter = set.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			Column column = tabella.getColumn(key);
			body += Utils.generaAddForBeanSimple(column, getClassName());
		}
		
		body += "\n\n";
		
		body += 
		"    @Override\r\n" +
		"    public boolean equals(Object o) {\r\n" +
		"        if (this == o) {\r\n" +
		"            return true;\r\n" +
		"        }\r\n" +
		"        if (o == null || getClass() != o.getClass()) {\r\n" +
		"            return false;\r\n" +
		"        }\r\n" +
		"        "+Utils.getDTOClassName(tabella)+" "+Utils.getClassNameLowerCase(tabella)+"DTO = ("+Utils.getDTOClassName(tabella)+") o;\r\n" +
		"        if ("+Utils.getClassNameLowerCase(tabella)+"DTO.getId() == null || getId() == null) {\r\n" +
		"            return false;\r\n" +
		"        }\r\n" +
		"        return Objects.equals(getId(), "+Utils.getClassNameLowerCase(tabella)+"DTO.getId());\r\n" +
		"    }\r\n\n";
		
		body += Utils.generateHashCode(tabella);
		
		body += Utils.generateToString(tabella, Utils.getDTOClassName(tabella));
		
		body += "}\r\n";
		
		return body;
	}

	public String getClassName() {
		return Utils.getDTOClassName(tabella);
	}
	
	public String getSourceFolder() {
		return "src/main/java";
	}
	
}
