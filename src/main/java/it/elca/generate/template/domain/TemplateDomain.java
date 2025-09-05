package it.elca.generate.template.domain;

import it.elca.generate.Column;
import it.elca.generate.ConfigCreateProject;
import it.elca.generate.ProjectRelation;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateDomain extends AbstractTemplate {

    public TemplateDomain(Table tabella) {
        super(tabella);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageClass", conf.getPackageclass());
        data.put("srcDomainFolder", conf.getSrcDomainFolder());
        data.put("className", getClassName());
        data.put("columns", getColumns());
        data.put("relations", getRelations(conf));
        return FreemarkerTemplate.process("domain.java.ftl", data);
    }

    private List<Map<String, String>> getColumns() {
        List<Map<String, String>> columns = new ArrayList<>();
        for (Column column : tabella.getSortedColumns()) {
            Map<String, String> columnData = new HashMap<>();
            columnData.put("field", Utils.generaFieldExt(column));
            columnData.put("getterAndSetter", Utils.generaGetAndSetForBeanExt(column, getClassName()));
            columnData.put("toString", Utils.generaToString(column));
            columns.add(columnData);
        }
        return columns;
    }

    private List<String> getRelations(ConfigCreateProject conf) {
        List<String> relations = new ArrayList<>();
        if (!CollectionUtils.isEmpty(conf.getProjectRelations())) {
            for (ProjectRelation rel : conf.getProjectRelations()) {
                String relationType = rel.getType();
                String nomeTabellaSx = rel.getSxTable();
                String nomeRelazioneSx = rel.getSxName();
                String nomeTabellaDx = rel.getDxTable();
                String nomeRelazioneDx = rel.getDxName();
                String nomeTabella = tabella.getNomeTabella().toLowerCase();

                if (nomeTabellaSx != null && nomeTabellaDx != null
                        && (nomeTabellaSx.toLowerCase().equals(nomeTabella) || nomeTabellaDx.toLowerCase().equals(nomeTabella))) {

                    System.out.println("## relationType: " + relationType + "  nomeTabellaSx: " + nomeTabellaSx + "  nomeTabellaDx: " + nomeTabellaDx + "  nomeTabella: " + nomeTabella);

                    if (Utils.OneToOne.equals(relationType)) {
                        if (nomeTabellaSx.toLowerCase().equals(nomeTabella)) {
                            String body = "\n	@OneToOne(cascade = CascadeType.REMOVE)\n";
                            body += "	@JoinColumn(unique = true)\n";
                            body += "	private " + Utils.getClassNameCamelCase(nomeTabellaDx) + " " + nomeRelazioneSx + ";\n\n";
                            body += "	public " + Utils.getClassNameCamelCase(nomeTabellaDx) + " get" + Utils.getClassNameCamelCase(nomeRelazioneSx) + "() {\n";
                            body += "	    return " + nomeRelazioneSx + ";\n";
                            body += "	}\n\n";
                            body += "	public " + Utils.getClassNameCamelCase(nomeTabellaSx) + " " + nomeRelazioneSx + "(" + Utils.getClassNameCamelCase(nomeTabellaDx) + " " + nomeRelazioneSx + ") {\n";
                            body += "	    this." + nomeRelazioneSx + " = " + nomeRelazioneSx + ";\n";
                            body += "	    return this;\n";
                            body += "	}\n\n";
                            body += "	public void set" + Utils.getClassNameCamelCase(nomeRelazioneSx) + "(" + Utils.getClassNameCamelCase(nomeTabellaDx) + " " + nomeRelazioneSx + ") {\n";
                            body += "	    this." + nomeRelazioneSx + " = " + nomeRelazioneSx + ";\n";
                            body += "	}\n";
                            relations.add(body);
                        } else if (nomeTabellaDx.toLowerCase().equals(nomeTabella)) {
                            String body = "\n	@OneToOne(mappedBy = \"" + nomeRelazioneSx + "\")\r\n";
                            body += "	@JsonIgnore\r\n";
                            body += "	private " + Utils.getClassNameCamelCase(nomeTabellaSx) + " " + nomeRelazioneDx + ";\n\n";
                            body += "	public " + Utils.getClassNameCamelCase(nomeTabellaSx) + " get" + Utils.getClassNameCamelCase(nomeRelazioneDx) + "() {\r\n";
                            body += "	    return " + nomeRelazioneDx + ";\r\n";
                            body += "	}\r\n\n";
                            body += "	public " + Utils.getClassNameCamelCase(nomeTabellaDx) + " " + nomeRelazioneDx + "(" + Utils.getClassNameCamelCase(nomeTabellaSx) + " " + nomeTabellaSx.toLowerCase() + ") {\r\n";
                            body += "	    this." + nomeRelazioneDx + " = " + nomeTabellaSx.toLowerCase() + ";\r\n";
                            body += " 	    return this;\r\n";
                            body += "	}\r\n\n";
                            body += "	public void set" + Utils.getClassNameCamelCase(nomeRelazioneDx) + "(" + Utils.getClassNameCamelCase(nomeTabellaSx) + " " + nomeTabellaSx.toLowerCase() + ") {\r\n";
                            body += "	    this." + nomeRelazioneDx + " = " + nomeTabellaSx.toLowerCase() + ";\r\n";
                            body += "	}\n";
                            relations.add(body);
                        }
                    } else if (Utils.ManyToMany.equals(relationType)) {
                        if (nomeTabellaSx.toLowerCase().equals(nomeTabella)) {
                            String body = "\n	@ManyToMany\r\n";
                            body += "	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)\n";
                            body += "	@JoinTable(	name = \"" + Utils.getFirstLowerCase(nomeTabellaSx) + "_" + Utils.getFirstLowerCase(nomeRelazioneSx) + "\",\n";
                            body += "				joinColumns = @JoinColumn(name = \"" + Utils.getFirstLowerCase(nomeTabellaSx) + "s_id\", referencedColumnName = \"id\"),\n";
                            body += "				inverseJoinColumns = @JoinColumn(name = \"" + Utils.getFirstLowerCase(nomeRelazioneSx) + "s_id\", referencedColumnName = \"id\"))\n";
                            body += "	private Set<" + Utils.getClassNameCamelCase(nomeTabellaDx) + "> " + nomeRelazioneSx + "s = new HashSet<>();\n\n";
                            body += "	public Set<" + Utils.getClassNameCamelCase(nomeTabellaDx) + "> get" + Utils.getFirstUpperCase(nomeRelazioneSx) + "s() {\r\n" +
                                    "		return " + Utils.getFirstLowerCase(nomeRelazioneSx) + "s;\r\n" +
                                    "	}\r\n\n" +
                                    "	public " + Utils.getFirstUpperCase(nomeTabellaSx) + " " + Utils.getFirstLowerCase(nomeRelazioneSx) + "s(Set<" + Utils.getClassNameCamelCase(nomeTabellaDx) + "> " + Utils.getFirstLowerCase(nomeTabellaDx) + "s) {\r\n" +
                                    "		this." + Utils.getFirstLowerCase(nomeRelazioneSx) + "s = " + Utils.getFirstLowerCase(nomeTabellaDx) + "s;\r\n" +
                                    "		return this;\r\n" +
                                    "	}\r\n\n" +
                                    "	public " + Utils.getFirstUpperCase(nomeTabellaSx) + " add" + Utils.getFirstUpperCase(nomeRelazioneSx) + "(" + Utils.getClassNameCamelCase(nomeTabellaDx) + " " + Utils.getFirstLowerCase(nomeTabellaDx) + ") {\r\n" +
                                    "		this." + Utils.getFirstLowerCase(nomeRelazioneSx) + "s.add(" + Utils.getFirstLowerCase(nomeTabellaDx) + ");\r\n" +
                                    "		" + Utils.getFirstLowerCase(nomeTabellaDx) + ".get" + Utils.getFirstUpperCase(nomeRelazioneDx) + "s().add(this);\r\n" +
                                    "		return this;\r\n" +
                                    "	}\r\n\n" +
                                    "	public " + Utils.getFirstUpperCase(nomeTabellaSx) + " remove" + Utils.getFirstUpperCase(nomeRelazioneSx) + "(" + Utils.getClassNameCamelCase(nomeTabellaDx) + " " + Utils.getFirstLowerCase(nomeTabellaDx) + ") {\r\n" +
                                    "		this." + Utils.getFirstLowerCase(nomeRelazioneSx) + "s.remove(" + Utils.getFirstLowerCase(nomeTabellaDx) + ");\r\n" +
                                    "		" + Utils.getFirstLowerCase(nomeTabellaDx) + ".get" + Utils.getFirstUpperCase(nomeRelazioneDx) + "s().remove(this);\r\n" +
                                    "		return this;\r\n" +
                                    "	}\r\n\n" +
                                    "	public void set" + Utils.getFirstUpperCase(nomeRelazioneSx) + "s(Set<" + Utils.getClassNameCamelCase(nomeTabellaDx) + "> " + Utils.getFirstLowerCase(nomeTabellaDx) + "s) {\r\n" +
                                    "		this." + Utils.getFirstLowerCase(nomeRelazioneSx) + "s = " + Utils.getFirstLowerCase(nomeTabellaDx) + "s;\r\n" +
                                    "	}\r\n\n";
                            relations.add(body);
                        } else if (nomeTabellaDx.toLowerCase().equals(nomeTabella)) {
                            String body = "\n	@ManyToMany(mappedBy = \"" + Utils.getFirstLowerCase(nomeRelazioneSx) + "s\")\r\n";
                            body += "	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)\n";
                            body += "	@JsonIgnore\n";
                            body += "	private Set<" + Utils.getClassNameCamelCase(nomeTabellaSx) + "> " + nomeRelazioneDx + "s = new HashSet<>();\n\n";
                            body += "	public Set<" + Utils.getClassNameCamelCase(nomeTabellaSx) + "> get" + Utils.getFirstUpperCase(nomeRelazioneDx) + "s() {\r\n" +
                                    "		return " + Utils.getFirstLowerCase(nomeRelazioneDx) + "s;\r\n" +
                                    "	}\r\n\n" +
                                    "	public " + Utils.getFirstUpperCase(nomeTabellaDx) + " " + Utils.getFirstLowerCase(nomeRelazioneDx) + "s(Set<" + Utils.getClassNameCamelCase(nomeTabellaSx) + "> " + Utils.getFirstLowerCase(nomeTabellaSx) + "s) {\r\n" +
                                    "		this." + Utils.getFirstLowerCase(nomeRelazioneDx) + "s = " + Utils.getFirstLowerCase(nomeTabellaSx) + "s;\r\n" +
                                    "		return this;\r\n" +
                                    "	}\r\n\n" +
                                    "	public " + Utils.getFirstUpperCase(nomeTabellaDx) + " add" + Utils.getFirstUpperCase(nomeRelazioneDx) + "(" + Utils.getClassNameCamelCase(nomeTabellaSx) + " " + Utils.getFirstLowerCase(nomeTabellaSx) + ") {\r\n" +
                                    "		this." + Utils.getFirstLowerCase(nomeRelazioneDx) + "s.add(" + Utils.getFirstLowerCase(nomeTabellaSx) + ");\r\n" +
                                    "		" + Utils.getFirstLowerCase(nomeTabellaSx) + ".get" + Utils.getFirstUpperCase(nomeRelazioneSx) + "s().add(this);\r\n" +
                                    "		return this;\r\n" +
                                    "	}\r\n\n" +

                                    "	public " + Utils.getFirstUpperCase(nomeTabellaDx) + " remove" + Utils.getFirstUpperCase(nomeRelazioneDx) + "(" + Utils.getClassNameCamelCase(nomeTabellaSx) + " " + Utils.getFirstLowerCase(nomeTabellaSx) + ") {\r\n" +
                                    "		this." + Utils.getFirstLowerCase(nomeRelazioneDx) + "s.remove(" + Utils.getFirstLowerCase(nomeTabellaSx) + ");\r\n" +
                                    "		" + Utils.getFirstLowerCase(nomeTabellaSx) + ".get" + Utils.getFirstUpperCase(nomeRelazioneSx) + "s().remove(this);\r\n" +
                                    "		return this;\r\n" +
                                    "	}\r\n\n" +
                                    "	public void set" + Utils.getFirstUpperCase(nomeRelazioneDx) + "s(Set<" + Utils.getClassNameCamelCase(nomeTabellaSx) + "> " + Utils.getFirstLowerCase(nomeTabellaSx) + "s) {\r\n" +
                                    "		this." + Utils.getFirstLowerCase(nomeRelazioneDx) + "s = " + Utils.getFirstLowerCase(nomeTabellaSx) + "s;\r\n" +
                                    "	}\r\n\n";
                            relations.add(body);
                        }
                    } else if (Utils.OneToMany.equals(relationType)) {
                        if (nomeTabellaSx.toLowerCase().equals(nomeTabella)) {
                            String relationSX = Utils.getFirstLowerCase(Utils.getClassNameCamelCase(nomeRelazioneSx));
                            String RelDxUp = Utils.getFirstUpperCase(nomeRelazioneDx);
                            String relDxUp = Utils.getFirstLowerCase(nomeRelazioneDx);

                            String body = "\n	@OneToMany(mappedBy = \"" + relDxUp + "\")\n";
                            body += "	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)\n";
                            body += "	private Set<" + Utils.getClassNameCamelCase(nomeTabellaDx) + "> " + Utils.getFirstLowerCase(nomeRelazioneSx) + "s = new HashSet<>();\n\n";
                            body += "	public Set<" + Utils.getClassNameCamelCase(nomeTabellaDx) + "> get" + Utils.getClassNameCamelCase(nomeRelazioneSx) + "s() {\n";
                            body += "	   return " + Utils.getFirstLowerCase(nomeRelazioneSx) + "s;\n";
                            body += "	}\n\n";
                            body += "	public " + Utils.getFirstUpperCase(nomeTabella) + " " + Utils.getFirstLowerCase(nomeRelazioneSx) + "s(Set<" + Utils.getClassNameCamelCase(nomeTabellaDx) + "> " + Utils.getFirstLowerCase(nomeRelazioneSx) + "s) {\n";
                            body += "	    this." + Utils.getFirstLowerCase(nomeRelazioneSx) + "s = " + Utils.getFirstLowerCase(nomeRelazioneSx) + "s;\n";
                            body += "	    return this;\n";
                            body += "	}\n\n";
                            body += "	public " + Utils.getFirstUpperCase(nomeTabella) + " add" + Utils.getClassNameCamelCase(nomeRelazioneSx) + "(" + Utils.getClassNameCamelCase(nomeTabellaDx) + " " + relationSX + ") {\n";
                            body += "	   this." + Utils.getFirstLowerCase(nomeRelazioneSx) + "s.add(" + relationSX + ");\n";
                            body += "	   " + relationSX + ".set" + RelDxUp + "(this);\n";
                            body += "	   return this;\n";
                            body += "	}\n\n";
                            body += "	public " + Utils.getFirstUpperCase(nomeTabella) + " remove" + Utils.getClassNameCamelCase(nomeRelazioneSx) + "(" + Utils.getClassNameCamelCase(nomeTabellaDx) + " " + relationSX + ") {\n";
                            body += "	    this." + Utils.getFirstLowerCase(nomeRelazioneSx) + "s.remove(" + relationSX + ");\n";
                            body += "	    " + relationSX + ".set" + RelDxUp + "(null);\n";
                            body += "	    return this;\n";
                            body += "	}\n\n";
                            body += "	public void set" + Utils.getClassNameCamelCase(nomeRelazioneSx) + "s(Set<" + Utils.getClassNameCamelCase(nomeTabellaDx) + "> " + Utils.getFirstLowerCase(nomeRelazioneSx) + "s) {\n";
                            body += "	    this." + Utils.getFirstLowerCase(nomeRelazioneSx) + "s = " + Utils.getFirstLowerCase(nomeRelazioneSx) + "s;\n";
                            body += "	}\n\n";
                            relations.add(body);
                        } else if (nomeTabellaDx.toLowerCase().equals(nomeTabella)) {
                            String TblSxUp = Utils.getFirstUpperCase(nomeTabellaSx);
                            String RelDxUp = Utils.getFirstUpperCase(nomeRelazioneDx);
                            String relDxLw = Utils.getFirstLowerCase(nomeRelazioneDx);
                            String TblDxUp = Utils.getFirstUpperCase(nomeTabellaDx);

                            String body = "\n	@ManyToOne\n";
                            body += "	@JsonIgnoreProperties(\"" + Utils.getFirstLowerCase(nomeRelazioneSx) + "s\")\n";
                            body += "	private " + TblSxUp + " " + relDxLw + ";\n\n";

                            body += "	public " + TblSxUp + " get" + RelDxUp + "() {\n";
                            body += "	    return " + relDxLw + ";\n";
                            body += "	}\n\n";

                            body += "	public " + TblDxUp + " " + relDxLw + "(" + TblSxUp + " " + relDxLw + ") {\n";
                            body += "	    this." + relDxLw + " = " + relDxLw + ";\n";
                            body += "	    return this;\n";
                            body += "	}\n\n";

                            body += "	public void set" + RelDxUp + "(" + TblSxUp + " " + relDxLw + ") {\n";
                            body += "	    this." + relDxLw + " = " + relDxLw + ";\n";
                            body += "	}\n\n";
                            relations.add(body);
                        }
                    } else if (Utils.ManyToOne.equals(relationType) && nomeTabellaSx.toLowerCase().equals(nomeTabella)) {
                        String body = "\n	@ManyToOne\n";
                        body += "	@JsonIgnoreProperties(\"\")\n";
                        body += "	private " + Utils.getClassNameCamelCase(nomeTabellaDx) + " " + nomeRelazioneSx + ";\n\n";
                        body += "	public " + Utils.getClassNameCamelCase(nomeTabellaDx) + " get" + Utils.getClassNameCamelCase(nomeRelazioneSx) + "() {\n";
                        body += "	    return " + nomeRelazioneSx + ";\n";
                        body += "	}\n\n";
                        body += "	public " + Utils.getClassNameCamelCase(nomeTabellaSx) + " " + nomeRelazioneSx + "(" + Utils.getClassNameCamelCase(nomeTabellaDx) + " " + nomeRelazioneSx + ") {\n";
                        body += "	    this." + nomeRelazioneSx + " = " + nomeRelazioneSx + ";\n";
                        body += "	    return this;\n";
                        body += "	}\n\n";
                        body += "	public void set" + Utils.getClassNameCamelCase(nomeRelazioneSx) + "(" + Utils.getClassNameCamelCase(nomeTabellaDx) + " " + nomeRelazioneSx + ") {\n";
                        body += "	    this." + nomeRelazioneSx + " = " + nomeRelazioneSx + ";\n";
                        body += "	}\n";
                        relations.add(body);
                    }
                }
            }
        }
        return relations;
    }

    @Override
    public String getClassName() {
        return Utils.getEntityName(tabella);
    }

    @Override
    public String getSourceFolder() {
        return "src/main/java";
    }

    @Override
    public String getTypeTemplate() {
        return Utils.replace(ConfigCreateProject.getIstance().getSrcDomainFolder(), ".", "/");
    }
}
