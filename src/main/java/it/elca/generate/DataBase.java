package it.elca.generate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataBase {
    public Map<String, Table> tabelle;
    public Map<String, List<String>> enumeration;

    public DataBase() {
        tabelle = new HashMap<>();
        enumeration = new HashMap<>();
    }

    public Table getTables(String tabellaName) {
        return tabelle.get(tabellaName);
    }

    public Map<String, List<String>> getEnumeration() {
        return enumeration;
    }

    public Set<String> getTableName() {
        return tabelle.keySet();
    }

    public void addTable(String tableName, Table table) {
        tabelle.put(tableName, table);
    }

    public void addEnumeration(String name, List<String> values) {
        enumeration.put(name, values);
    }

    @Override
    public String toString() {
        String ret = "";
        Set<String> set = tabelle.keySet();
        for (String tablename : set) {
            Table table = tabelle.get(tablename);
            ret += "\n" + table;
        }
        return ret;
    }
}
