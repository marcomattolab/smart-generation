package it.elca.generate.template.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import it.elca.generate.Column;
import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.ProjectRelation;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;

public class TemplateServiceCriteria extends AbstractTemplate{

	public TemplateServiceCriteria(Table tabella) {
		super(tabella);
	}
	
	public TemplateServiceCriteria(DataBase database, Table tabella) {
		super(database);
		this.tabella = tabella;
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
		"import java.io.Serializable;\r\n" +
		"import java.util.Objects;\r\n" +
		"import "+ conf.getPackageclass() + "." + conf.getSrcDomainEnumerationFolder()+".*;\r\n\n" +
		"import io.github.jhipster.service.filter.BooleanFilter;\r\n" +
		"import io.github.jhipster.service.filter.DoubleFilter;\r\n" +
		"import io.github.jhipster.service.filter.Filter;\r\n" +
		"import io.github.jhipster.service.filter.FloatFilter;\r\n" +
		"import io.github.jhipster.service.filter.IntegerFilter;\r\n" +
		"import io.github.jhipster.service.filter.LongFilter;\r\n" +
		"import io.github.jhipster.service.filter.BigDecimalFilter;\r\n" +
		"import io.github.jhipster.service.filter.StringFilter;\r\n" +
		"import io.github.jhipster.service.filter.InstantFilter;\r\n" +
		"import io.github.jhipster.service.filter.LocalDateFilter;\r\n\n" +
		"import io.github.jhipster.service.filter.ZonedDateTimeFilter;\r\n\n" +
		
		"/**\r\n" +
		" * "+Utils.getEntityName(tabella)+" class for the "+Utils.getEntityName(tabella)+" entity. This class is used in "+Utils.getEntityName(tabella)+"Resource to\r\n" +
		" * receive all the possible filtering options from the Http GET request parameters.\r\n" +
		" * For example the following could be a valid requests:\r\n" +
		" * <code> /"+Utils.getEntityName(tabella)+"s?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>\r\n" +
		" * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use\r\n" +
		" * fix type specific filters.\r\n" +
		" */\r\n" +
		"public class "+getClassName()+" implements Serializable {\r\n\n";

		//Add Enumeration management
		//System.out.println("\n#ServiceCriteria - Enumeration for table: " + tabella.getNomeTabella() );
		HashMap<String, List<String>> enums = Utils.filterEnumeration(tabella.getNomeTabella(), null);
		Set<String> enumNames = enums.keySet();
		for (String enumName : enumNames) {
			body+= "    /**\r\n";
			body+= "     * Class for filtering "+enumName+"\r\n";
			body+= "     */\r\n";
			body+= "    public static class "+enumName+"Filter extends Filter<"+enumName+"> {\r\n" ;
			body+= "    }\r\n\n";
		}
		
		//Before RelationsStore Original List
		List<Column> extendedListCopy = new ArrayList<>(tabella.getColumns());
		
		//[Manage Relations]
		if(!CollectionUtils.isEmpty(conf.getProjectRelations())) {
			for(ProjectRelation rel: conf.getProjectRelations()) {
				String relationType = rel.getType();
				String nomeTabellaSx = rel.getSxTable();
				String nomeRelazioneSx = rel.getSxName();
				String nomeRelazioneDx = rel.getDxName();
				String nomeTabellaDx = rel.getDxTable();
				String nomeTabella = tabella.getNomeTabella().toLowerCase();
				
				if(nomeTabellaSx!=null && nomeTabellaDx != null) {
					if (relationType.equals(Utils.OneToOne) || relationType.equals(Utils.ManyToOne)) {
						if(nomeTabellaSx.toLowerCase().equals(nomeTabella)) {
							Column columnId = new Column();
							columnId.setName(nomeRelazioneSx+"Id");
							columnId.setTypeColumn(Column.corvertModelType("Long"));
							extendedListCopy.add(columnId);
						}
						
					}else if (relationType.equals(Utils.ManyToMany)) {
						if(nomeTabellaSx.toLowerCase().equals(nomeTabella)) {
							Column columnId = new Column();
							columnId.setName(nomeRelazioneSx+"Id");
							columnId.setTypeColumn(Column.corvertModelType("Long"));
							extendedListCopy.add(columnId);
						}
						if(nomeTabellaDx.toLowerCase().equals(nomeTabella)) {
							Column columnId = new Column();
							columnId.setName(nomeRelazioneDx+"Id");
							columnId.setTypeColumn(Column.corvertModelType("Long"));
							extendedListCopy.add(columnId);
						}
						
					} else if (relationType.equals(Utils.OneToMany)) {
						if(nomeTabellaSx.toLowerCase().equals(nomeTabella)) {
							Column columnId = new Column();
							columnId.setName(nomeRelazioneSx+"Id");
							columnId.setTypeColumn(Column.corvertModelType("Long"));
							extendedListCopy.add(columnId);
						}
						if(nomeTabellaDx.toLowerCase().equals(nomeTabella)) {
							Column columnId = new Column();
							columnId.setName(nomeRelazioneDx+"Id");
							columnId.setTypeColumn(Column.corvertModelType("Long"));
							extendedListCopy.add(columnId);
						}
					}
				} 
			}
		}
		//[/Manage Relations]
		 
		//FIX - CLEAN LIST REMOVE BLOB/CLOB
		List<Column> extendedList = new ArrayList<>();
		for(Column column : extendedListCopy) {
			if(!Utils.isBlob(column) && !Utils.isClob(column)) {
				extendedList.add(column);
			}
		}
		
		body+=
		"    private static final long serialVersionUID = 1L;\r\n";
		for (Column column : extendedList) {
			body += Utils.generaFieldFilter(column, false)+"\n";
		}	
		
		body+=
		"	 \n"	+
		"    public "+getClassName()+"() {\r\n" +
		"    }\r\n\n";
		
		for (Column column : extendedList) {
			String filterTypology = Utils.getFilterTypology(column);
			body += 
			"    public "+filterTypology+" get"+Utils.getFieldNameForMethod(column,false)+"() {\r\n" +
			"        return "+Utils.getFieldName(column, false)+";\r\n" +
			"    }\r\n\n" +
			"    public void set"+Utils.getFieldNameForMethod(column,false)+"("+filterTypology+" "+Utils.getFieldName(column, false)+") {\r\n" +
			"        this."+Utils.getFieldName(column, false)+" = "+Utils.getFieldName(column, false)+";\r\n" +
			"    }\r\n\n";
		}
		
		body += 
		"    @Override\r\n" +
		"    public boolean equals(Object o) {\r\n" +
		"        if (this == o) {\r\n" +
		"            return true;\r\n" +
		"        }\r\n" +
		"        if (o == null || getClass() != o.getClass()) {\r\n" +
		"            return false;\r\n" +
		"        }\r\n" +
		"        final "+getClassName()+" that = ("+getClassName()+") o;\r\n" +
		"        return\r\n";
		int count = 1;
		for (Column column : extendedList) {
			boolean isLatest = count == extendedList.size();
			body += "\t\t\tObjects.equals("+Utils.getFieldName(column,false)+", that."+Utils.getFieldName(column,false)+") ";
			body += (!isLatest ? "&&" : ";") + "\r\n";
			count++;
		}
		body +=
		"    }\r\n\n";
		
		body +=
		"    @Override\r\n" +
		"    public int hashCode() {\r\n" +
		"        return Objects.hash(\r\n";
		count = 1;
		for (Column column : extendedList) {
			boolean isLatest = count == extendedList.size();
			body += "\t\t\t"+Utils.getFieldName(column,false)+"";
			body += (!isLatest ? "," : "") + "\r\n";
			count++;
		}
		body +=
		"        );\r\n" +
		"    }\r\n\n";
		
		body +=
		"    @Override\r\n" +
		"    public String toString() {\r\n" +
		"        return \""+getClassName()+"{\" +\r\n";
		
		count = 1;
		for (Column column : extendedList) {
			boolean isLatest = count == extendedList.size();
			body += "\t\t\t("+Utils.getFieldName(column,false)+" != null ? \""+Utils.getFieldName(column,false)+"=\" + "+Utils.getFieldName(column,false)+" + \", \" : \"\") +";
			body += (!isLatest ? "" : "") + "\r\n";
			count++;
		}
		body +=
		"            \"}\";\r\n" +
		"    }\r\n" +
		"}\r\n";
		
		return body;
	}


	public String getClassName() {
		return Utils.getCriteriaClassName(tabella);
	}
	
	public String getSourceFolder() {
		return "src/main/java";
	}
	
}
