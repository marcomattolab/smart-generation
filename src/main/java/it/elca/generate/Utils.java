package it.elca.generate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public class Utils {
	public static boolean LOG_DEBUG_GENERATOR = false;
	public static String OneToOne = "OneToOne";
	public static String ManyToMany = "ManyToMany";
	public static String OneToMany = "OneToMany";
	public static String ManyToOne = "ManyToOne";
	
	public static String APICE = "'";
	public static String DATE_PATTERN = "DD/MM/YYYY"; 
	public static String DATE_PATTERN_LIQUIBASE = "YYYY-MM-DD";
	
	public static String getServiceClassName(Table table){
		return getEntityName(table)+"Service"; 
	}
	
	public static String getServiceImplClassName(Table table){
		return getEntityName(table)+"ServiceImpl"; 
	}
	
	public static String getQueryServiceClassName(Table table){
		return getEntityName(table)+"QueryService"; 
	}
	
	public static String getDTOClassName(Table table){
		return getEntityName(table)+"DTO"; 
	}
	
	public static String getCriteriaClassName(Table table){
		return getEntityName(table)+"Criteria"; 
	}
	
	public static String getResourceClassName(Table table){
		return getEntityName(table)+"Resource"; 
	}
	
	public static String getMapperClassName(Table table){
		return getEntityName(table)+"Mapper"; 
	}
	
	public static String getGPClassName(Table table){
		return getEntityName(table)+"GeneratePersistence"; 
	}
	
	public static String getMgrClassName(Table table){
		return getEntityName(table)+"Manager"; 
	}
	
	public static String getRepositoryClassName(Table tabella) {
		return getEntityName(tabella)+"Repository";
	}

	public static String getRepositoryVarName(Table tabella) {
		return getEntityName(tabella)+"_XYZ"; //FIXME
	}
	
	public static String getRepositoryClassName(String tabella) {
		return tabella+"Repository";
	}
	
	public static String getIName(Table table){
		String className = table.getNomeTabella().toLowerCase();
		className = className.substring(0,1).toUpperCase()+className.substring(1);
		return "I"+className; 
	}
	
	public static String getNameService(Table table){
		String className = table.getNomeTabella().toLowerCase();
		className = className.substring(0,1).toUpperCase()+className.substring(1);
		return className+"Service"; 
	}
	
	public static String getFieldName(Table tabella) {
		return tabella.getNomeTabella().toLowerCase();
	}
	
	public static String generaSetWithField(String label,Column column){
		return ""+label+".set"+getFieldNameForMethod(column)+"("+getFieldName(column)+");";
	}
	
	public static String getClassNameCamelCase(String name) {
		String className = name.toLowerCase();
		className = className.substring(0,1).toUpperCase()+className.substring(1);
		return className; 
	}

	public static String getFirstUpperCase(String fieldName) {
		String nameField = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
		return nameField; 
	}
	
	public static String getFirstLowerCase(String fieldName) {
		String nameField = fieldName.substring(0,1).toLowerCase()+fieldName.substring(1);
		return nameField; 
	}

	public static String getClassNameLowerCase(Table table) {
		String className = table.getNomeTabella().toLowerCase();
		return className; 
	}
	
	public static String getEntityName(Table table){
		String className = table.getNomeTabella().toLowerCase();
		className = className.substring(0,1).toUpperCase()+className.substring(1);
		return className; 
	}

	public static String getCostantName(Table table){
		String costantName = table.getNomeTabella().toUpperCase();
		return costantName; 
	}
	
	public static String getCostantNameCompleto(Table table){
		String costantName = table.getNomeTabellaCompleto().toUpperCase();
		return costantName; 
	}
	
	public static String getCostantName(Column column){
		String costantName = column.getName().toUpperCase();
		return costantName; 
	}
	
	public static String getFieldNameForMethod(Column column){
		String name = getFieldNameForMethodReplace(column.getName(), false);
		return name; 
	}

	public static String getFieldNameForMethod(Column column, boolean lowerCase){
		return getFieldNameForMethodReplace(column.getName(), false, false);
	}
	
	public static String getFieldName(Column column){
		return getFieldName(column, true);
	}

	public static String getFieldName(Column column, boolean lowerCase){
		String name = getFieldNameForMethodReplace(column.getName(), true, lowerCase);
		return name; 
	}
	
	public static String getFieldNameContentType(Column column){
		String name = column.getName().toLowerCase();
		return name + "_" + "content_type";
	}

	public static String getFieldNameForMethodReplace(String name, boolean firstLowercase, boolean toLower){
		String result = "";
		String[] parts = toLower ? name.toLowerCase().split("_") : name.split("_");
		for (int i = 0; i < parts.length; i++) {
			result += parts[i].substring(0,1).toUpperCase()+parts[i].substring(1);
		}
		if(firstLowercase) {
			result = result.substring(0,1).toLowerCase()+result.substring(1);
		}
		return result;
	}
	
	public static String getFieldNameForMethodReplace(String name, boolean firstLowercase){
		return getFieldNameForMethodReplace(name, firstLowercase, true);
	}
	
	public static String getListCostantNameKeys(Table table){
		return getListCostantName(table,true);
	}
	
	public static String getListCostantNameFields(Table table){
		return getListCostantName(table,false);
	}
	
	public static String generaToString(Column column) {
		return ""+getFieldNameForMethodReplace(column.getName(), true)+"=\"+"+getFieldNameForMethodReplace(column.getName(), true)+"+\"";
	}

	public static String setValue(Column column, String string, String string2) {
		return "\n\t\t"+string+".set"+getFieldNameForMethod(column)+"("+string2+".get"+getFieldNameForMethod(column)+"());";
	}
	
	public static String getValue(String label,Column column) {
		Class<?> typeClass = column.getTypeColumn();
		return "\n\t\t"+typeClass.getName()+" "+getFieldName(column)+" = "+label+".get"+getFieldNameForMethod(column)+"();";
	}
	
	public static String getListCostantName(Table table, boolean isKey){
		String ret = "";
		boolean first = true;
		Set<?> set = table.getColumnNames();
		for (Iterator<?> iter = set.iterator(); iter.hasNext();) {
			String columnName = (String) iter.next();
			Column column = table.getColumn(columnName);
			if((isKey && column.isKey())||(!isKey && !column.isKey())){
				if(first){
					ret = ret + " ConstantName.COL_" +getCostantName(column);
					first = false;
				}else{
					ret = ret + ", ConstantName.COL_" +getCostantName(column);
				}
			}
		}
		return ret;
	}
	
	public static String replace(String str,String oldS,String newS){
		int i = str.indexOf(oldS);
		int len = oldS.length();
		if( i<0 ) {
			return str;
		} else{
			return str.substring(0,i)+newS+replace(str.substring(i+len),oldS,newS);
		}
	}
	
	public static String generaGetAndSet(Column column){
		Class<?> typeClass = column.getTypeColumn();
		String ret  = "\n";
		ret += "\n\tpublic "+typeClass.getName()+ " get"+getFieldNameForMethod(column)+"(){";
		ret += "\n\t\treturn ("+typeClass.getName()+")hm.get(ConstantName.COL_"+getCostantName(column)+");";
		ret += "\n\t}";
		ret += "\n\t";
		ret += "\n\tpublic void set"+getFieldNameForMethod(column)+"("+typeClass.getName()+" "+getFieldName(column)+"){";
		ret += "\n\t\thm.put(ConstantName.COL_"+getCostantName(column)+","+getFieldName(column)+");";
		ret += "\n\t}";
		ret += "\n\t";
		return ret;
	}
	
	public static String generateIInterface(Table tabella, List<Column> extendedList){
		String body = 
		"export interface "+Utils.getIName(tabella)+" {\r\n";
		for (Column column: extendedList) {
			String columnname = getFieldName(column, false);
			Class<?> filterType = column.getTypeColumn();
			String typeColumnRel = column.getTypeColumnRelation();
			
			if (typeColumnRel != null && typeColumnRel.length()>0) {
				body += "      "+columnname+"?: "+typeColumnRel+"[];\n";
			} else {
				if( filterType.getName().equals("java.sql.Blob") ) {
					body += "      "+columnname+"?: "+getIInterfaceTypology(column)+";\n";
					body += "      "+columnname+"ContentType?: string;\n";
				} else {
					body += "      "+columnname+"?: "+getIInterfaceTypology(column)+";\n";
				}
			}
		}
		body += "}\r\n\n";
		return body;
	}
	
	public static String generateIClass(Table tabella, List<Column> extendedList, String classImplemented){
		String body = 
		"export class "+Utils.getEntityName(tabella)+" implements "+classImplemented+" {\r\n" +
		"    constructor(\r\n" ;
		
		int i = 1;
		for (Column column: extendedList) {
			boolean isLast = i == extendedList.size();
			String columnname = getFieldName(column, false);
			Class<?> filterType = column.getTypeColumn();
			String typeColumnRel = column.getTypeColumnRelation();
			
			if (typeColumnRel != null && typeColumnRel.length()>0) {
				body += "     public "+columnname+"?: "+typeColumnRel+"[]"+(!isLast?",":"")+"\r\n";
			} else {
				if( filterType.getName().equals("java.sql.Blob") ) {
					body += "     public "+columnname+"?: "+getIInterfaceTypology(column)+",\r\n";
					body += "     public "+columnname+"ContentType?: string "+(!isLast?",":"")+"\r\n";
				} else {
					body += "     public "+columnname+"?: "+getIInterfaceTypology(column)+(!isLast?",":"")+"\r\n";
				}
			}
			i++;
		}
		
		body +=
		"    ) {\r\n" +
		"        // this.flag = this.flag || false;\r\n" +
		"    }\r\n";
		
		return body;
	}
	
	public static String generateIClass(Table tabella, List<Column> extendedList){
		String classImplemented = Utils.getIName(tabella);
		return generateIClass(tabella, extendedList, classImplemented);
	}
	
	public static String createFileds(Table tabella){
		String ret ="";
		Set<?> set = tabella.getColumnNames();
		boolean first = true;
		for (Iterator<?> iter = set.iterator(); iter.hasNext();) {
			String columnName = (String) iter.next();
			Column column = tabella.getColumn(columnName);
			Class<?> typeClass = column.getTypeColumn();
			if(first){
				ret += typeClass.getName()+" "+getFieldName(column)+"";
				first = false;
			}else{
				ret += "," + typeClass.getName()+" "+getFieldName(column)+"";
			}
		}
		return ret;
	}
	
	public static String createFiledsWithoutType(Table tabella){
		String ret ="";
		Set<?> set = tabella.getColumnNames();
		boolean first = true;
		for (Iterator<?> iter = set.iterator(); iter.hasNext();) {
			String columnName = (String) iter.next();
			Column column = tabella.getColumn(columnName);
			if(first){
				ret += ""+getFieldName(column)+"";
				first = false;
			}else{
				ret += "," +getFieldName(column)+"";
			}
		}
		return ret;
	}
	
	public static String createFiledsKey(Table tabella){
		String ret ="";
		Set<?> set = tabella.getColumnNames();
		boolean first = true;
		for (Iterator<?> iter = set.iterator(); iter.hasNext();) {
			String columnName = (String) iter.next();
			Column column = tabella.getColumn(columnName);
			Class<?> typeClass = column.getTypeColumn();
			if(column.isKey()){
				if(first){
					ret += typeClass.getName()+" "+getFieldName(column)+"";
					first = false;
				}else{
					ret += "," + typeClass.getName()+" "+getFieldName(column)+"";
				}
			}
		}
		return ret;
	}
	
	public static String createKeysWithOutType(Table tabella){
		String body = "";
		Set<?> set2 = tabella.getColumnNames();
		boolean first = true;
		for (Iterator<?> iter = set2.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			Column column = tabella.getColumn(key);
			if(column.isKey()){
				if(first){
					body += ""+Utils.getFieldName(column);
					first = false;
				} else{
					body += ","+Utils.getFieldName(column);
				}
			}
		}
		return body;
	}
	
	public static String setGenericValue(Table tabella,String label,boolean isKey,boolean allField) {
		String ret = "";
		Set<?> set = tabella.getColumnNames();
		for (Iterator<?> iter = set.iterator(); iter.hasNext();) {
			String columnName = (String) iter.next();
			Column column = tabella.getColumn(columnName);
			if(column.isKey()==isKey){
				if(!allField){
					ret += "\n\t\tif("+getFieldName(column)+"!=null)";
					ret += "\n\t\t\t"+label+".set"+getFieldNameForMethod(column)+"("+getFieldName(column)+");";
				}else{
					ret += "\n\t\t"+label+".set"+getFieldNameForMethod(column)+"("+getFieldName(column)+");";
				}
			}
		}
		return ret;
	}
	
	public static String setGenericValue(Table tabella,String label1,String label2,boolean isKey,boolean allField) {
		String ret ="";
		Set<?> set = tabella.getColumnNames();
		for (Iterator<?> iter = set.iterator(); iter.hasNext();) {
			String columnName = (String) iter.next();
			Column column = tabella.getColumn(columnName);
			if(column.isKey()==isKey){
				if(!allField){
					ret += "\n\t\tif("+label2+"!=null && "+label2+".get"+getFieldNameForMethod(column)+"()!=null)";
					ret += "\n\t\t\t"+label1+".set"+getFieldNameForMethod(column)+"("+label2+".get"+getFieldNameForMethod(column)+"());";
				}else{
					ret += "\n\t\t"+label1+".set"+getFieldNameForMethod(column)+"("+label2+".get"+getFieldNameForMethod(column)+"());";
				}
			}
		}
		return ret;
	}

	public static String generaGetAndSetForBean(Column column) {
		Class<?> typeClass = column.getTypeColumn();
		String ret  = "\n";
		ret += "\n\tpublic "+typeClass.getName()+ " get"+getFieldNameForMethod(column)+"(){";
		ret += "\n\t\treturn this."+getFieldName(column)+";";
		ret += "\n\t}";
		ret += "\n\t";
		ret += "\n\tpublic void set"+getFieldNameForMethod(column)+"("+typeClass.getName()+" "+getFieldName(column)+"){";
		ret += "\n\t\tthis."+getFieldName(column)+" = "+getFieldName(column)+";";
		ret += "\n\t}";
		return ret;
	}
	
	public static String generaGetAndSetForBeanExt(Column column, String className) {
		String ret = "";
		ret += generaAddForBeanSimple(column, className);
		if( isBlob(column) ) {
			ret += "\n\tpublic "+ className + " " + getFieldNameForMethodReplace(column.getName(), true)+"("+"byte[] "+getFieldNameForMethodReplace(column.getName(), true)+"){";
			ret += "\n\t\tthis."+getFieldNameForMethodReplace(column.getName(), true)+" = "+getFieldNameForMethodReplace(column.getName(), true)+";";
			ret += "\n\t\treturn this;";
			ret += "\n\t}";
			ret += "\n";
			ret += "\n\tpublic "+ className + " " + getFieldNameForMethodReplace(column.getName(), true)+"ContentType("+"String "+getFieldNameForMethodReplace(column.getName(),true)+"ContentType){";
			ret += "\n\t\tthis."+getFieldNameForMethodReplace(column.getName(), true)+"ContentType = "+getFieldNameForMethodReplace(column.getName(), true)+"ContentType;";
			ret += "\n\t\treturn this;";
			ret += "\n\t}";
			ret += "\n";
		} else {
			ret += "\n\tpublic "+ className + " " + getFieldNameForMethodReplace(column.getName(), true)+"("+(isPrimaryKeyID(column)? "Long" : getTipoCampo(column))+" "+getFieldNameForMethodReplace(column.getName(), true)+"){";
			ret += "\n\t\tthis."+getFieldNameForMethodReplace(column.getName(), true)+" = "+getFieldNameForMethodReplace(column.getName(), true)+";";
			ret += "\n\t\treturn this;";
			ret += "\n\t}";
			ret += "\n";
		}
		return ret;
	}
	
	public static String generaAddForBeanSimple(Column column, String className) {
		return generaAddForBeanSimple(column, className, true);
	}
	
	public static String generaAddForBeanSimple(Column column, String className, boolean toLower) {
		String ret = "";
		if( isBlob(column) ) {
			ret += "\n\tpublic byte[] get"+getFieldNameForMethodReplace(column.getName(), false, toLower)+"(){";
			ret += "\n\t\treturn this."+getFieldNameForMethodReplace(column.getName(), true, toLower)+";";
			ret += "\n\t}";
			ret += "\n";
			ret += "\n\tpublic void set"+getFieldNameForMethod(column)+"( byte[] "+getFieldNameForMethodReplace(column.getName(), true, toLower)+"){";
			ret += "\n\t\tthis."+getFieldNameForMethodReplace(column.getName(), true, toLower)+" = "+getFieldNameForMethodReplace(column.getName(), true, toLower)+";";
			ret += "\n\t}";
			ret += "\n";
			String contentType = column.getName() + "_" + "content_type";
			ret += "\n\tpublic String get"+getFieldNameForMethodReplace(contentType, false, toLower)+"(){";
			ret += "\n\t\treturn this."+getFieldNameForMethodReplace(contentType, true, toLower)+";";
			ret += "\n\t}";
			ret += "\n";
			ret += "\n\tpublic void set"+getFieldNameForMethodReplace(contentType,false, toLower)+"( String "+getFieldNameForMethodReplace(contentType,true, toLower)+"){";
			ret += "\n\t\tthis."+getFieldNameForMethodReplace(contentType, true, toLower)+" = "+getFieldNameForMethodReplace(contentType, true, toLower)+";";
			ret += "\n\t}";
			ret += "\n";
		} else {
			ret += "\n\tpublic "+(isPrimaryKeyID(column)? "Long" : getTipoCampo(column))+ " get"+getFieldNameForMethodReplace(column.getName(), false, toLower)+"(){";
			ret += "\n\t\treturn this."+getFieldNameForMethodReplace(column.getName(), true, toLower)+";";
			ret += "\n\t}";
			ret += "\n";
			ret += "\n\tpublic void set"+getFieldNameForMethodReplace(column.getName(), false, toLower)+"("+(isPrimaryKeyID(column)? "Long" : getTipoCampo(column))+" "+getFieldNameForMethodReplace(column.getName(), true, toLower)+"){";
			ret += "\n\t\tthis."+getFieldNameForMethodReplace(column.getName(), true, toLower)+" = "+getFieldNameForMethodReplace(column.getName(), true, toLower)+";";
			ret += "\n\t}";
			ret += "\n";
		}
		return ret;
	}
	
	public static String getTipoCampo(Column column) {
		return isEnumeration(column) ? column.getEnumeration() : column.getTypeColumn().getName();
	}

	public static String getNomeCampo(Column column) {
		return isEnumeration(column) ? column.getEnumeration() : column.getName();
	}

	public static String getFilterTypology(Column column) {
		String filterTypology = "";
		Class<?> filterType = column.getTypeColumn();
		
		if (column.getEnumeration()!=null && column.getEnumeration().length()>0) {
			//Done Enumeration type
			filterTypology = column.getEnumeration()+"Filter"; 
			if (Utils.LOG_DEBUG_GENERATOR) {
				System.out.println("- filterType: "+filterType + " enumeration: "+ column.getEnumeration());
			}
		} else if (filterType.getName().equals("java.lang.String")) {
			filterTypology = "StringFilter";
		} else if(filterType.getName().equals("java.lang.Long")) {
			filterTypology = "LongFilter";
		} else if(filterType.getName().equals("java.lang.Integer")) {
			filterTypology = "IntegerFilter";
		} else if(filterType.getName().equals("java.lang.Boolean")) {
			filterTypology = "BooleanFilter";
		} else if(filterType.getName().equals("java.sql.Date") 
				|| filterType.getName().equals("java.time.LocalDate") ) {
			filterTypology = "LocalDateFilter";
		} else if(filterType.getName().equals("java.lang.Float")) {
			filterTypology = "FloatFilter";
		} else if(filterType.getName().equals("java.math.BigDecimal")) {
			filterTypology = "BigDecimalFilter";
		} else if(filterType.getName().equals("java.sql.Timestamp") 
				|| filterType.getName().equals("java.time.Instant") ) {
			filterTypology = "InstantFilter";
		} else if(filterType.getName().equals("java.time.ZonedDateTime") ) {
			filterTypology = "ZonedDateTimeFilter";
		    
		//FIX for ID (primary key of table)
		} else if( isPrimaryKeyID(column) ) {
			filterTypology = "LongFilter";
		}
		return filterTypology;
	}
	
	public static String getTemplateHtmlByType(DataBase database, Column column, Table tabella, ConfigCreateProject conf) {
		String result = "";
		boolean isEnumeration = column.getEnumeration()!=null;
		String nometabella = Utils.getClassNameLowerCase(tabella);
		Class<?> filterType = column.getTypeColumn();
		String nomeColonna = Utils.getFieldName(column);
		String NomeColonna = Utils.getFieldNameForMethod(column);
		
		//Validation: Pattern
		boolean hasPattern = column.getPattern()!=null ? true : false;
		String htmlPattern = hasPattern ? " pattern=\""+column.getPattern()+"\" " : "";
		String htmlPatternVal = hasPattern
							 ?"\t\t\t\t\t\t<small class=\"form-text text-danger\"\n"+
							  "\t\t\t\t\t\t[hidden]=\"!editForm.controls."+nomeColonna+"?.errors?.pattern\" jhiTranslate=\"entity.validation.pattern\" translateValues=\"{ pattern: '"+NomeColonna+"' }\">\n"+
							  "\t\t\t\t\t\tThis field should follow pattern for \""+NomeColonna+"\".\n"+
							  "\t\t\t\t\t\t</small>\n"
							 :""; 
		
		//Validation: Minlen
		boolean hasMinsize = column.getColumnMinSize()!=null ? true : false;
		String htmlMinsize = hasMinsize ? " minlength=\""+column.getColumnMinSize()+"\" " : "";
		String htmlMinsizeVal = hasMinsize 
				? "\t\t\t\t\t\t<small class=\"form-text text-danger\"\n"+
				  "\t\t\t\t\t\t[hidden]=\"!editForm.controls."+nomeColonna+"?.errors?.minlength\" jhiTranslate=\"entity.validation.minlength\" translateValues=\"{ min: "+column.getColumnMinSize()+" }\">\n"+
				  "\t\t\t\t\t\tThis field cannot be shorter than "+column.getColumnMinSize()+" characters.\n"+
				  "\t\t\t\t\t\t</small>\n" 
				: "";
				
		//Validation: Maxlen
		boolean hasMaxsize = column.getColumnMaxSize()!=null ? true : false;
		String htmlMaxsize = hasMaxsize ? " maxlength=\""+column.getColumnMaxSize()+"\" " : "";
		String htmlMaxsizeVal = hasMaxsize 
				? "\t\t\t\t\t\t<small class=\"form-text text-danger\"\n"+
				  "\t\t\t\t\t\t[hidden]=\"!editForm.controls."+nomeColonna+"?.errors?.maxlength\" jhiTranslate=\"entity.validation.maxlength\" translateValues=\"{ max: "+column.getColumnMaxSize()+" }\">\n"+
				  "\t\t\t\t\t\tThis field cannot be longer than "+column.getColumnMaxSize()+" characters.\n"+
				  "\t\t\t\t\t\t</small>\n" 
				: "";
		
		boolean isNullable = column.isNullable();
		
		if( isPrimaryKeyID(column) ) {
			//System.out.println("## Column primary key is "+column.getName());
			result += //TEXT ID 
					"                <jhi-alert-error></jhi-alert-error>\r\n" +
					"                <div class=\"form-group\" [hidden]=\"!"+nometabella+"."+nomeColonna+"\">\r\n" +
					"                    <label for=\""+nomeColonna+"\" jhiTranslate=\"global.field."+nomeColonna+"\">"+NomeColonna+"</label>\r\n" +
					"                    <input type=\"text\" class=\"form-control\" id=\""+nomeColonna+"\" name=\""+nomeColonna+"\"\r\n" +
					"                        [(ngModel)]=\""+nometabella+"."+nomeColonna+"\" readonly />\r\n" +
					"                </div>\r\n\n";
		} else if (filterType.getName().equals("java.lang.String") && !isEnumeration) {
			result += //TEXT
			"                <div class=\"form-group\">\r\n" +
			"                    <label class=\"form-control-label\" jhiTranslate=\""+conf.getProjectName()+"App."+nometabella+"."+nomeColonna+"\" for=\"field_"+nomeColonna+"\">"+NomeColonna+"</label>\r\n" +
			"                    <input type=\"text\" class=\"form-control\" name=\""+nomeColonna+"\" id=\"field_"+nomeColonna+"\"\r\n" +
			"                        [(ngModel)]=\""+nometabella+"."+nomeColonna+"\" "+ (!isNullable?"required":"") 
										+ htmlPattern 
										+ htmlMinsize 
										+ htmlMaxsize +"/>\r\n" +
			"                    <div [hidden]=\"!(editForm.controls."+nomeColonna+"?.dirty && editForm.controls."+nomeColonna+"?.invalid)\">\r\n" +
			"                        <small class=\"form-text text-danger\"\r\n" +
			"                        [hidden]=\"!editForm.controls."+nomeColonna+"?.errors?.required\" jhiTranslate=\"entity.validation.required\">\r\n" +
			"                        This field is required.\r\n" +
			"                        </small>\r\n" +
									 htmlPatternVal +
									 htmlMinsizeVal +
									 htmlMaxsizeVal +
			"                    </div>\r\n" +
			"                </div>\r\n\n";
		
		} else if(filterType.getName().equals("java.lang.String") && isEnumeration) {
			result += //ENUMERATION
			"                <div class=\"form-group\">\r\n" +
			"                    <label class=\"form-control-label\" jhiTranslate=\""+conf.getProjectName()+"App."+nometabella+"."+nomeColonna+"\" for=\"field_"+nomeColonna+"\">"+NomeColonna+"</label>\r\n" +
			"                    <select class=\"form-control\" name=\""+nomeColonna+"\" [(ngModel)]=\""+nometabella+"."+nomeColonna+"\" id=\"field_"+nomeColonna+"\" >\r\n";
			
			List<Enumeration> enumList = Utils.getEnumerationsByDbAndTable(database, tabella);
			for(Enumeration e : enumList) {
				if ( column.getEnumeration()!=null && column.getEnumeration().equals(e.getNomeEnumeration()) ) { 
					for(String vEnum : e.getValoriEnumeration()) {
						result +="                        <option value=\""+vEnum+"\">{{'"+conf.getProjectName()+"App."+e.getNomeEnumeration()+"."+vEnum+"' | translate}}</option>\r\n" ;
					}
				}
			}
			
			result +=
			"                    </select>\r\n" +
			"                </div>\r\n\n" ;
			

		} else if(filterType.getName().equals("java.lang.Long") || filterType.getName().equals("java.lang.Integer") || filterType.getName().equals("java.lang.Float") || filterType.getName().equals("java.math.BigDecimal")) {
			result += //NUMBER TODO MANAGE THIS
					"                <div class=\"form-group\">\r\n" +
					"                    <label class=\"form-control-label\" jhiTranslate=\""+conf.getProjectName()+"App."+nometabella+"."+nomeColonna+"\" for=\"field_"+nomeColonna+"\">"+NomeColonna+"</label>\r\n" +
					"                    <input type=\"number\" class=\"form-control\" name=\""+nomeColonna+"\" id=\"field_"+nomeColonna+"\"\r\n" +
					"                        [(ngModel)]=\""+nometabella+"."+nomeColonna+"\" "+ (!isNullable?"required":"") + htmlPattern + "/>\r\n" +
					"                    <div [hidden]=\"!(editForm.controls."+nomeColonna+"?.dirty && editForm.controls."+nomeColonna+"?.invalid)\">\r\n" +
					"                        <small class=\"form-text text-danger\"\r\n" +
					"                        [hidden]=\"!editForm.controls."+nomeColonna+"?.errors?.required\" jhiTranslate=\"entity.validation.required\">\r\n" +
					"                        This field is required.\r\n" +
					"                        </small>\r\n" +
					"                    </div>\r\n" +
					"                </div>\r\n\n";
		
		} else if(filterType.getName().equals("java.lang.Boolean")) {
			result += //FLAG BOOLEAN
			"                <div class=\"form-group\">\r\n" +
			"                    <label class=\"form-control-label\" jhiTranslate=\""+conf.getProjectName()+"App."+nometabella+"."+nomeColonna+"\" for=\"field_"+nomeColonna+"\">"+NomeColonna+"</label>\r\n" +
			"                    <input type=\"checkbox\" class=\"form-control\" name=\""+nomeColonna+"\" id=\"field_"+nomeColonna+"\"\r\n" +
			"                        [(ngModel)]=\""+nometabella+"."+nomeColonna+"\" />\r\n" +
			"                </div>\r\n\n";
		
		} else if( Utils.isDateField(column) && Utils.isLocalDate(column)) {
			result += //DATE ==> ngbDatepicker
			"                <div class=\"form-group\">\r\n" +
			"                    <label class=\"form-control-label\" jhiTranslate=\""+conf.getProjectName()+"App."+nometabella+"."+nomeColonna+"\" for=\"field_"+nomeColonna+"\">"+NomeColonna+"</label>\r\n" +
			"                    <div class=\"input-group\">\r\n" +
			"                        <input id=\"field_"+nomeColonna+"\" type=\"text\" class=\"form-control\" name=\""+nomeColonna+"\" ngbDatepicker  #"+nomeColonna+"Dp=\"ngbDatepicker\" [(ngModel)]=\""+nometabella+"."+nomeColonna+"\"\r\n" +
			"                        />\r\n" +
			"                        <span class=\"input-group-append\">\r\n" +
			"                            <button type=\"button\" class=\"btn btn-secondary\" (click)=\""+nomeColonna+"Dp.toggle()\"><fa-icon [icon]=\"'calendar-alt'\"></fa-icon></button>\r\n" +
			"                        </span>\r\n" +
			"                    </div>\r\n" +
			"                </div>\r\n\n";
		
		} else if( Utils.isDateField(column) && !Utils.isLocalDate(column)) {
			boolean isZDT = false;
			if ( isZDT ) {
				result += //DATE ==> ZonedDateTimelocal
				"                <div class=\"form-group\">\r\n" +
				"                    <label class=\"form-control-label\" jhiTranslate=\""+conf.getProjectName()+"App."+nometabella+"."+nomeColonna+"\" for=\"field_"+nomeColonna+"\">"+nomeColonna+"</label>\r\n" +
				"                    <div class=\"d-flex\">\r\n" +
				"                        <input id=\"field_"+nomeColonna+"\" type=\"datetime-local\" class=\"form-control\" name=\""+nomeColonna+"\" [(ngModel)]=\""+nomeColonna+"\"\r\n" + (!isNullable?"required":"")+
				"                        />\r\n" +
				"                    </div>\r\n" +
				"                    <div [hidden]=\"!(editForm.controls."+nomeColonna+"?.dirty && editForm.controls."+nomeColonna+"?.invalid)\">\r\n" +
				"                        <small class=\"form-text text-danger\"\r\n" +
				"                        [hidden]=\"!editForm.controls."+nomeColonna+"?.errors?.required\" jhiTranslate=\"entity.validation.required\">\r\n" +
				"                        This field is required.\r\n" +
				"                        </small>\r\n" +
				"                        <small class=\"form-text text-danger\"\r\n" +
				"                            [hidden]=\"!editForm.controls."+nomeColonna+"?.errors?.ZonedDateTimelocal\" jhiTranslate=\"entity.validation.ZonedDateTimelocal\">\r\n" +
				"                            This field should be a date and time.\r\n" +
				"                        </small>\r\n" +
				"                    </div>\r\n" +
				"                </div>\r\n\n";
			} else {
				result += //DATE ==> datetime-local
				"                <div class=\"form-group\">\r\n" +
				"                    <label class=\"form-control-label\" jhiTranslate=\""+conf.getProjectName()+"App."+nometabella+"."+nomeColonna+"\" for=\"field_"+nomeColonna+"\">"+NomeColonna+"</label>\r\n" +
				"                    <div class=\"d-flex\">\r\n" +
				"                        <input id=\"field_"+nomeColonna+"\" type=\"datetime-local\" class=\"form-control\" name=\""+nomeColonna+"\" [(ngModel)]=\""+nomeColonna+"\"\r\n" +
				"                        />\r\n" +
				"                    </div>\r\n" +
				"                </div>\r\n\n" ;
			}
		} else if( filterType.getName().equals("java.sql.Clob") ) {
			result += //TEXTAREA CLOB
					"                <div class=\"form-group\">\r\n" +
					"                    <label class=\"form-control-label\" jhiTranslate=\""+conf.getProjectName()+"App."+nometabella+"."+nomeColonna+"\" for=\"field_note\">"+NomeColonna+"</label>\r\n" +
					"                    <textarea class=\"form-control\" name=\""+nomeColonna+"\" id=\"field_"+nomeColonna+"\"\r\n" +
					"                        [(ngModel)]=\""+nometabella+"."+nomeColonna+"\" ></textarea>\r\n" +
					"                </div>\r\n\n";
		} else if( filterType.getName().equals("java.sql.Blob") ) {
			result += //TEXTAREA BLOB
			"                <div class=\"form-group\">\r\n" +
			"                    <label class=\"form-control-label\" jhiTranslate=\""+conf.getProjectName()+"App."+nometabella+"."+nomeColonna+"\" for=\"field_"+nomeColonna+"\">"+NomeColonna+"</label>\r\n" +
			"                    <div>\r\n" +
			"                        <div *ngIf=\""+nometabella+"."+nomeColonna+"\" class=\"form-text text-danger clearfix\">\r\n" +
			"                            <a class=\"pull-left\" (click)=\"openFile("+nometabella+"."+nomeColonna+"ContentType, "+nometabella+"."+nomeColonna+")\" jhiTranslate=\"entity.action.open\">open</a><br>\r\n" +
			"                            <span class=\"pull-left\">{{"+nometabella+"."+nomeColonna+"ContentType}}, {{byteSize("+nometabella+"."+nomeColonna+")}}</span>\r\n" +
			"                            <button type=\"button\" (click)=\""+nometabella+"."+nomeColonna+"=null;"+nometabella+"."+nomeColonna+"ContentType=null;\"\r\n" +
			"                                    class=\"btn btn-secondary btn-xs pull-right\">\r\n" +
			"                                <fa-icon [icon]=\"'times'\"></fa-icon>\r\n" +
			"                            </button>\r\n" +
			"                        </div>\r\n" +
			"                        <input type=\"file\" id=\"file_"+nomeColonna+"\" (change)=\"setFileData($event, "+nometabella+", '"+nomeColonna+"', false)\" jhiTranslate=\"entity.action.addblob\"/>\r\n" +
			"                    </div>\r\n" +
			"                    <input type=\"hidden\" class=\"form-control\" name=\""+nomeColonna+"\" id=\"field_"+nomeColonna+"\"\r\n" +
			"                        [(ngModel)]=\""+nometabella+"."+nomeColonna+"\" />\r\n" +
			"                    <input type=\"hidden\" class=\"form-control\" name=\""+nomeColonna+"ContentType\" id=\"field_"+nomeColonna+"ContentType\"\r\n" +
			"                        [(ngModel)]=\""+nometabella+"."+nomeColonna+"ContentType\" />\r\n" +
			"                </div>\r\n";
		}
		return result;
	}
	
	public static List<Enumeration> getEnumerationsByDbAndTable(DataBase database, Table tabella) {
		List<Enumeration> enumList = new ArrayList<>();
		HashMap<String, List<String>> enums = Utils.filterEnumeration(tabella.getNomeTabella(), null);
		if(database != null && database.getEnumeration()!=null) {
			for(String key: database.getEnumeration().keySet()) {
				if( enums.keySet().contains(key) ) {
					enumList.add( new Enumeration(key, database.getEnumeration().get(key)) );
				}
			}
		}
		if (enumList.size()>0) {
			if (Utils.LOG_DEBUG_GENERATOR) {
				System.out.println("## Entity/Shared - Enumeration for table: " + tabella.getNomeTabella() +"  _Size: "+enumList.size());
			}
		}
		return enumList;
	}
	
	public static String getIInterfaceTypology(Column column) {
		String filterTypology = "";
		Class<?> filterType = column.getTypeColumn();
		//System.out.println("filterType: "+filterType);
		if (filterType.getName().equals("java.lang.String")) {
			filterTypology = "string";
		} else if(filterType.getName().equals("java.lang.Long")) {
			filterTypology = "number";
		} else if(filterType.getName().equals("java.lang.Integer")) {
			filterTypology = "number";
		} else if(filterType.getName().equals("java.lang.Boolean")) {
			filterTypology = "boolean";
		} else if(filterType.getName().equals("java.sql.Date") 
				|| filterType.getName().equals("java.time.LocalDate") ) {
			filterTypology = "Moment";
		} else if(filterType.getName().equals("java.lang.Float")) {
			filterTypology = "number";
		} else if(filterType.getName().equals("java.math.BigDecimal")) {
			filterTypology = "number";
		} else if(filterType.getName().equals("java.sql.Timestamp") 
				|| filterType.getName().equals("java.time.Instant") 
				|| filterType.getName().equals("java.time.ZonedDateTime") 
				) {
			filterTypology = "Moment";
		} else if(filterType.getName().equals("java.sql.Clob") 
				|| filterType.getName().equals("java.sql.Blob") ) {
			filterTypology = "any";
			//FIX for ID (primary key of table)
		} else if( isPrimaryKeyID(column) ) {
			filterTypology = "number";
		} else {
			filterTypology = filterType.getName(); //FIX FOR MANY2MANY
		}
		return filterTypology;
	}
	
	/**
	 * @param column Column
	 * @return TRUE if field is DATE, FALSE otherwise
	 */
	public static boolean isDateField(Column column) {
		boolean idDateField = false;
		Class<?> filterType = column.getTypeColumn();
		if(	filterType.getName().equals("java.sql.Date") || 
			filterType.getName().equals("java.util.Date") || 
			filterType.getName().equals("java.time.LocalDate") ||
			filterType.getName().equals("java.sql.ZonedDateTime") ||
			filterType.getName().equals("java.sql.Timestamp") || 
			filterType.getName().equals("java.time.Instant") ) {
				idDateField = true;
		} 
		return idDateField;
	}

	/**
	 * @param column Column
	 * @return TRUE if field is TEXT, FALSE otherwise
	 */
	public static boolean isTextField(Column column) {
		boolean idTxtField = false;
		Class<?> filterType = column.getTypeColumn();
		if(	filterType.getName().equals("java.lang.String") ) {
			idTxtField = true;
		} 
		return idTxtField;
	}
	
	/**
	 * @param column Column
	 * @return TRUE if field is NUMERIC, FALSE otherwise
	 */
	public static boolean isNumericField(Column column) {
		boolean isNumericField = false;
		Class<?> filterType = column.getTypeColumn();
		if(	filterType.getName().equals("java.lang.Float") || 
				filterType.getName().equals("java.lang.Long") || 
				filterType.getName().equals("java.lang.Integer") ||
				filterType.getName().equals("java.math.BigDecimal")  ) {
			isNumericField = true;
		} 
		return isNumericField;
	}
	
	/**
	 * @param column Column
	 * @return TRUE if field is LocalDate, FALSE otherwise
	 */
	public static boolean isLocalDate(Column column) {
		boolean isLocalDate = false;
		Class<?> filterType = column.getTypeColumn();
		if(	filterType.getName().equals("java.time.LocalDate") ) {
			isLocalDate = true;
		} 
		return isLocalDate;
	}

	/**
	 * If Column is ID ==> PRIMARY KEY
	 * @param column Column
	 * @return TRUE for ID PrimaryKey, FALSE otherwise
	 */
	public static boolean isPrimaryKeyID(Column column) {
		return "ID".equals(column.getName().toUpperCase());
	}

	/**
	 * @param column Column
	 * @return TRUE for BigDecimal, FALSE otherwise
	 */
	public static boolean isBigDecimal(Column column) {
		boolean isBigDecimal = false;
		 if(column.getTypeColumn().getName().equals("java.math.BigDecimal")) {
			 isBigDecimal = true;
		 }
		 return isBigDecimal;
	}
	
	/**
	 * @param column Column
	 * @return TRUE for Blob, FALSE otherwise
	 */
	public static boolean isBlob(Column column) {
		boolean isBlob = false;
		if(column!=null && column.getTypeColumn() != null 
				&& "java.sql.Blob".equals(column.getTypeColumn().getName())) {
			isBlob = true;
		}
		return isBlob;
	}
	
	/**
	 * @param column Column
	 * @return TRUE for Enumeration, FALSE otherwise
	 */
	public static boolean isEnumeration(Column column) {
		boolean isEnumeration = false;
		if(column!=null && column.getEnumeration() != null && column.getEnumeration().length()>0 ) {
			isEnumeration = true;
		}
		return isEnumeration;
	}
	
	/**
	 * @param column Column
	 * @return TRUE for Clob, FALSE otherwise
	 */
	public static boolean isClob(Column column) {
		boolean isClob = false;
		if(column!=null && column.getTypeColumn() != null 
				&& "java.sql.Clob".equals(column.getTypeColumn().getName())) {
			isClob = true;
		}
		return isClob;
	}
	
	public static String generaFieldFilter(Column column) {
		return generaFieldFilter(column, true);
	}

	public static String generaFieldFilter(Column column, boolean lowerCase) {
		return "\n\tprivate "+ getFilterTypology(column)  + " "+getFieldName(column, lowerCase)+";";
	}
	
	public static String generaField(Column column) {
		return generaField(column, true);
	}
	
	public static String generaField(Column column, boolean toLower) {
		String ret = "";
		if( isBlob(column) ) {
			ret += "\n\t@Lob";
			ret += "\n\tprivate byte[] "+getFieldNameForMethodReplace(column.getName(), true, toLower)+";";
			ret += "\n\tprivate String "+getFieldNameForMethodReplace(column.getName(), true, toLower)+"ContentType;";
		} else {
			ret += "\n\tprivate "+(isPrimaryKeyID(column) ? "Long" : getTipoCampo(column))+ " "+getFieldNameForMethodReplace(column.getName(), true, toLower)+";";
		}
		return ret;
	}
	
	public static String generaFieldExt(Column column) {
		String ret = "";
		
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		boolean IS_ORACLE = conf.isOracle();
		
		//FIXME TODO Move this into DB / JSON
		String PRECISION = "10";
		String SCALE = "2";

		if( isBlob(column) ) {
			ret += "\n\t@Lob";
			ret += "\n\t@Column(name = \""+column.getName()+"\")";
			ret += "\n\tprivate byte[] "+getFieldNameForMethodReplace(column.getName(), true)+";";
			ret += "\n";
			ret += "\n\t@Column(name = \""+getFieldNameContentType(column)+"\")";
			ret += "\n\tprivate String "+getFieldNameForMethodReplace(column.getName()+"_"+"content_type", true)+";";
		} else if( isPrimaryKeyID(column) ) {
			ret += "\n\t@Id";
			if( IS_ORACLE ) {
				//Oracle
				ret += "\n\t@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = \"sequenceGenerator\")\r\n";
				ret += "\n\t@SequenceGenerator(name = \"sequenceGenerator\")\r\n";
			} else {
				//MySQL
				ret += "\n\t@GeneratedValue(strategy = GenerationType.IDENTITY)";
			}
			ret += "\n\t@Column(name = \""+column.getName()+"\""+ (isBigDecimal(column)? ", precision = "+PRECISION+", scale = "+SCALE : "") +")";
			ret += "\n\tprivate Long "+getFieldNameForMethodReplace(column.getName(), true)+";";
			
			
		} else {
			//isEnumeration
			ret += isEnumeration(column) ? "\n\t@Enumerated(EnumType.STRING)" : "";
			ret += "\n\t@Column(name = \""+column.getName()+"\""+ (isBigDecimal(column)? ", precision = "+PRECISION+", scale = "+SCALE : "") +")";
			ret += "\n\tprivate " + getTipoCampo(column) + " " + getFieldNameForMethodReplace(column.getName(), true) + ";";
		}
		return ret;
	}
	
	public static String generateJson(Column column) {
		String ret = "";
		String ColumnName = getFieldNameForMethod(column);
		String columnname = getFieldName(column);
		ret += "\t\t\t\""+columnname+"\": \""+splitCamelCase(ColumnName)+"\"";
		return ret;
	}

	public static String generateJsonUppercase(Column column) {
		String ret = "";
		String ColumnName = getFieldNameForMethod(column);
		String columnname = getFieldName(column);
		ret += "\t\t\t\""+columnname.toUpperCase()+"\": \""+splitCamelCase(ColumnName)+"\"";
		return ret;
	}

	public static String splitCamelCase(String column) {
		String result = StringUtils.capitalize(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(column), " "));
		result = result.replaceAll(" _ ", " "); //RAPID FIX
		return result;
	}
	
	public static String generaGetAndSetForForm(Column column) {
		String ret  = "\n";
		ret += "\n\tpublic String get"+getFieldNameForMethod(column)+"(){";
		ret += "\n\t\treturn this."+getFieldName(column)+";";
		ret += "\n\t}";
		ret += "\n\t";
		ret += "\n\tpublic void set"+getFieldNameForMethod(column)+"(String "+getFieldName(column)+"){";
		ret += "\n\t\tthis."+getFieldName(column)+" = "+getFieldName(column)+";";
		ret += "\n\t}";
		return ret;
	}
	
	public static String generateHashCode(Table tabella) {
		String ret = "\t@Override\r\n";
		ret += "\tpublic int hashCode() {\r\n";
		ret += "\t\treturn Objects.hashCode(getId());\r\n";
		ret +="\t}\r\n";
		return ret;
	}
	
	public static String generateToString(Table tabella, String className) {
		String ret ="\n";
		ret += "\t@Override\r\n";
		ret += "\tpublic String toString() {\r\n";
		ret += "\t\treturn \""+className+"{\" + \r\n";
		Set<?> set = tabella.getColumnNames();
		boolean isFirst = true;
		for (Iterator<?> iter = set.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			Column column = tabella.getColumn(key);
			if(isFirst){
				ret += "\t\t\"" + getFieldNameForMethodReplace(column.getName(), true) + "='\" + " + getFieldNameForMethodReplace(column.getName(), true) + " + \"'\" + \n";
				isFirst = false;
			}else{
				ret += "\t\t\"," + getFieldNameForMethodReplace(column.getName(), true) + "='\" + " + getFieldNameForMethodReplace(column.getName(), true) + " + \"'\" + \n";
			}
		}
		ret += "\t\t\"}\";\r\n";
		ret += "\t}\r\n";
		return ret;
	}
	
	public static String generaParametri1to1(String label,Table table2){
		String body = "";
		Set<?> set2 = table2.getColumnNames();
		boolean first = true;
		for (Iterator<?> iter = set2.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			Column column = table2.getColumn(key);
			if(column.isKey()){
				if(first){
					body += label+".get"+getFieldNameForMethod(column)+"()";
					first = false;
				}
				else{
					body += ","+label+".get"+Utils.getFieldNameForMethod(column)+"()";;
				}
			}
		}
		return body;
	}
	
	public static String generaParametri1toN(String label,Table table1,Table table2){
		String body ="";
		Set<?> set = table2.getColumnNames();
		boolean first = true;
		for (Iterator<?> iter = set.iterator(); iter.hasNext();) {
			String columnName = (String) iter.next();
			Column column = table1.getColumn(columnName);
			if(column!=null && column.isKey()){
				if(first){
					body += label+".get"+getFieldNameForMethod(column)+"()";
					first = false;
				} else{
					body += ","+label+".get"+Utils.getFieldNameForMethod(column)+"()";;
				}
			}else{
				if(first){
					body += "null";
					first = false;
				} else {
					body += ",null";
				}	
			}
		}
		return body;
	}
	
	public static String getCurrentDate(int digit) {
		Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String formatted = format.format(curDate);
		String result = formatted + String.format("%05d", digit);
		return result;
	}
	
	public static boolean isKeyForSecondTable(Column column,Table table2){
		Set<?> set = table2.getColumnNames();
		for (Iterator<?> iter = set.iterator(); iter.hasNext();) {
			String columnName = (String) iter.next();
			Column columnCurr = table2.getColumn(columnName);
			if(columnCurr.isKey() && columnCurr.getName().equals(column.getName()))
				return true;
		}
		return false;
	}
	
	public static String generaParametriNto1(String label,Table table2){
		return generaParametri1to1(label,table2);
	}
	
	public static List<Table> getTables(DataBase database) {
		//Table Names to SKIP
		String PREFIX_JHI = "JHI_";
		String PREFIX_LIQUIDBASE = "DATABASECHANGELOG";
	
		int sort = 1;
		List<Table> tables = new ArrayList<>();
		Set<?> setTabNames = database.getTableName();
		for (Iterator<?> iter = setTabNames.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			if( key.toUpperCase().startsWith(PREFIX_JHI) || key.toUpperCase().startsWith(PREFIX_LIQUIDBASE) ) {
				//System.out.println("# Skipping table : "+key);
			} else {
				//System.out.println("# Generating table : "+key);
				Table tabella =  database.getTables(key);
				tabella.setSort(sort++);
				tables.add(tabella);
			}
		}
		return tables;
	}
	
	/**
	 * Return current ProjectEntity By Table Name passed.
	 * 
	 * @param ccp ConfigCreateProject
	 * @param nameTable Table Name
	 * @return ProjectEntity
	 */
	public static ProjectEntity getProjectEntityByName(ConfigCreateProject ccp, String nameTable) {
		ProjectEntity curEntity = null;
		List<ProjectEntity> entities = ccp.getProjectEntities();
		for (ProjectEntity projectEntity : entities) {
			if(nameTable.toLowerCase().equals(projectEntity.getName().toLowerCase())) {
				curEntity = projectEntity;
			}
		}
		return curEntity;
	}
	
	/**
	 * Filter map based on tableName passed.
	 * 
	 * @param nameTable
	 * @return Map
	 */
	public static HashMap<String, List<String>> filterEnumeration(String nameTable, String columnName) {
		HashMap<String, List<String>> result = new HashMap<>();
		ConfigCreateProject ccp = ConfigCreateProject.getIstance();
		
		ProjectEntity curEntity = Utils.getProjectEntityByName(ccp, nameTable);
		if(curEntity!=null) {
			for (Field field : curEntity.getFields()) {
				for (ProjectEnum cenum : ccp.getEnumerations()) {
					if(field.getFtype().equalsIgnoreCase(cenum.getName())) {
						String[] vect = cenum.getValues().split("#");
						if(columnName==null || (columnName!=null && columnName.equalsIgnoreCase(field.getFname()))) {
							result.put(cenum.getName(), Arrays.asList(vect));
						}
					}
				}
			}
		}
		if(result.size()>0) {
			if (Utils.LOG_DEBUG_GENERATOR) {
				System.out.println("- NameTable.ColumnName: "+nameTable+"."+columnName+" - Build enums with Size>0 ==> " + result.size());
			}
		}
		return result;
	}
	
	/**
	 * @param conf ConfigCreateProject
	 * @param nomeSelectSx
	 * @param nomeTabellaDx
	 * @return int resultType  (Return type of field ==> Geolocalizzazione.immobile that is String)
	 */
	public static int getTypeColumnFromRelation(ConfigCreateProject conf, String nomeSelectSx, String nomeTabellaDx) {
		int resultType = -9999;
		for(ProjectEntity entity : conf.getProjectEntities()) {
			if(nomeTabellaDx.equals(entity.getName())) {
				for(Field field : entity.getFields()) {
					if(nomeSelectSx.equals(field.getFname())) {
						resultType = Column.corvertModelType(field.getFtype());
						return resultType;
					}
				}
			}
		}
		return resultType;
	}
	
	/**
	 * Check if table having constraints
	 * 
	 * @param conf
	 * @param table
	 * @return boolean 
	 */
	public static boolean havingConstraints(ConfigCreateProject conf, Table table) {
		boolean havingConstrains = false;

		//Relations management
		if(!CollectionUtils.isEmpty(conf.getProjectRelations())) {
			for(ProjectRelation rel: conf.getProjectRelations()) {
				String relationType = rel.getType();
				String nomeTabellaSx = rel.getSxTable();
				//String nomeRelazioneSx = rel.getSxName();
				//String nomeRelazioneDx = rel.getDxName();
				String nomeTabellaDx = rel.getDxTable();
				String nomeTabella = table.getNomeTabella().toLowerCase();
				
				if(nomeTabellaSx!=null && nomeTabellaDx != null) { 
					if (relationType.equals(Utils.OneToMany) && nomeTabellaDx.toLowerCase().equals(nomeTabella)) {
						havingConstrains = true;
						return havingConstrains;
					}
					if (relationType.equals(Utils.ManyToMany) && nomeTabellaSx.toLowerCase().equals(nomeTabella)) {
						havingConstrains = true;
						return havingConstrains;
					}
				}
            
			}
		}
		return havingConstrains;
	}
	
	
	/**
	 * Print body interpolating the Map with relations
	 * 
	 * @param res
	 * @param relMap
	 * @return res
	 */
	public static  String printRelationMap(String res, Map<String, String> relMap) {
		if(relMap!= null && !relMap.isEmpty()) {
			for (Map.Entry<String,String> entry : relMap.entrySet())  {
				res +=  entry.getValue();
				//System.out.println("#Map ManyToMany : Key = " + entry.getKey() +  ", Value = " + entry.getValue()); 
			} 
		}
		return res;
	}
	
	/**
	 * @param colonne List<Column>
	 * @return TRUE if Table contains a column of type BLOB | CLOB
	 */
	public static boolean hasColumnAttachment(List<Column> colonne) {
		boolean hasColumnAttachment = false;
		for (Column column : colonne) {
			hasColumnAttachment =  Utils.isClob(column) || Utils.isBlob(column);
			if(hasColumnAttachment) {
				break;
			}
		}
		return hasColumnAttachment;
	}
	
	/**
	 * Return Table's Roles.
	 * 
	 * @param tabella Table
	 * @param aroundChar Char to put before and after ROLE
	 * @return String
	 */
	public static String getAuthorities(Table tabella, String aroundChar) {
		String result = "";
		if(tabella.getProfiles()!=null && tabella.getProfiles().size()>0) {
			int i = 0;
			int size = tabella.getProfiles().size();
			for(String profile: tabella.getProfiles()) {
				result += ""+aroundChar+""+profile+""+aroundChar+""+ (i+1<size?", ":"");
				i++;
			}
		} else {
			result += aroundChar+"ROLE_ADMIN"+aroundChar+", "+aroundChar+"ROLE_USER"+aroundChar;
		}
		return result;
	}

	/**
	 * Return Global's Roles.
	 * 
	 * @param conf
	 * @param aroundChar
	 * @return String
	 */
	public static String getGlobalAuthorities(ConfigCreateProject conf, String aroundChar) {
		String result = "";
		String[] profiles = conf.getProfiles();
		if(profiles!=null && profiles.length>0) {
			int size = profiles.length;
			for(int i=0; i<size; i++) {
				String profile = profiles[i];
				result += ""+aroundChar+""+profile+""+aroundChar+""+ (i+1<size?", ":"");
			}
		} else {
			result += aroundChar+"ROLE_ADMIN"+aroundChar+", "+aroundChar+"ROLE_USER"+aroundChar;
		}
		return result;
	}
	
	/**
	 * Return authorities credentials.
	 * 
	 * @param conf
	 * @return List<String>
	 */
	public static List<String> getGlobalAuthoritiesCredential(ConfigCreateProject conf) {
		List<String> result = new ArrayList<>();
		if(conf.getProfiles()!=null && conf.getProfiles().length>0) {
			for(int i=0; i<conf.getProfiles().length; i++) {
				String profileRole = conf.getProfiles()[i];
				String profile = profileRole.replace("ROLE_", "").toLowerCase();
				result.add(profile);
			}
		} else {
			result.add("user");
			result.add("admin");
		}
		return result;
	}

	/**
	 * @return Random Blob
	 */
	public static String getRandomBlob() {
        return "../data/blob/hipster.png";
    }
	
	/**
	 * @return Random Blob Content Type
	 */
	public static String getRandomBlobContentType() {
		return "image/png";
	}
	
	/**
	 * @return Random Boolean
	 */
	public static boolean getRandomBoolean() {
		return Math.random() < 0.5;
	}
	
	/**
	 * @return Random Number
	 */
	public static double getRandomNumber() {
		if (getRandomBoolean()) {
			return Math.random();
		} else {
			return Math.random();
		}
	}

	/**
	 * @return Random Date
	 */
	public static String getRandomDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN_LIQUIBASE);
		if (getRandomBoolean()) {
			return simpleDateFormat.format(new Date());
		} else {
			return simpleDateFormat.format(new Date());
		}
	}
	
	/**
	 * Generate a random String
	 * @param column Column
	 * @return String 
	 */
	public static String getRandomString(Column column) {
		String result = "";
		String[] mail = {"mail", "email"};
		String[] phone = {"phone", "telephone", "telefono", "cellulare", "mobile"};
		String[] name = {"firstname", "nome", "name", "surname", "cognome"};
		String[] city = {"city", "città"};
		String[] province = {"province", "provincia"};
		String[] region = {"region", "regione"};
		
		if ( Arrays.asList(mail).contains(column.getName().toLowerCase()) ) {
			result = getRandomBoolean() ? "mail@libero.it": "email@gmail.com";
		}
		else if ( Arrays.asList(phone).contains(column.getName().toLowerCase()) ) {
			result = getRandomBoolean() ? "+39 3201271829": "091 78291279";
		}
		else if ( Arrays.asList(name).contains(column.getName().toLowerCase()) ) {
			result = getRandomBoolean() ? "Marco": "Alessia";
		}
		else if ( Arrays.asList(city).contains(column.getName().toLowerCase()) ) {
			result = getRandomBoolean() ? "Palermo": "Torino";
		}
		else if ( Arrays.asList(region).contains(column.getName().toLowerCase()) ) {
			result = getRandomBoolean() ? "Sicilia": "Piemonte";
		}
		else if ( Arrays.asList(province).contains(column.getName().toLowerCase()) ) {
			result = getRandomBoolean() ? "PA": "TO";
		}
		else {
			result = getRandomBoolean() ? "Demo test1": "Demo Test2";
		}
		if (result.length() > column.getColumnSize()) {
			result = result.substring(0, column.getColumnSize());
		}
		return result;
	}
	
	/**
	 * @param max
	 * @return A Random Number from 1 to max
	 */
	public static int getRandomMax(int max){
        return (int) (Math.random()*max);
	}
	
	/**
	 * @param column
	 * @param table
	 * @param db
	 * @return Random Enumeration
	 */
	public static String getRandomEnumeration(Column column, Table table, DataBase db) {
		String res = null;
		List<Enumeration> enumList = Utils.getEnumerationsByDbAndTable(db, table);
		int enumSize = enumList.size();
		int random = getRandomMax(enumSize+1);
		int k = 1;
		for(Enumeration e: enumList) {
			if ( column.getEnumeration()!=null && column.getEnumeration().equals(e.getNomeEnumeration()) ) { 
				for(String vEnum : e.getValoriEnumeration()) {
					if (random+1 == k) {
						res = vEnum;
					}
					k++;
				}
			}
		}
		return res;
	}
	
}