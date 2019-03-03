package com.neueda.shorturls.dbunit;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableIterator;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.RowOutOfBoundsException;
import org.dbunit.dataset.datatype.DataType;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Util class to load yaml data sets
 */
public class YamlDataSetLoader extends AbstractDataSetLoader {
    @Override
    protected IDataSet createDataSet(Resource resource) throws Exception {
        return new YamlDataSet(resource.getInputStream());
    }


    /**
     * This implementation of {@link IDataSet} is based on {@link com.github.dbunit.rules.api.dataset.YamlDataSet}
     * with table reordering issue fixed by using {@link LinkedHashMap}.
     */
    @SuppressWarnings("JavadocReference")
    private static class YamlDataSet implements IDataSet {
        private Map<String, YamlDataTable> tables = new LinkedHashMap<>();


        YamlDataSet(InputStream source) {
            @SuppressWarnings("unchecked")
            Map<String, List<Map<String, Object>>> data = new Yaml().load(source);
            if (data != null) {
                for (Map.Entry<String, List<Map<String, Object>>> ent : data.entrySet()) {
                    String tableName = ent.getKey();
                    List<Map<String, Object>> rows = ent.getValue();
                    createTable(tableName.toUpperCase(), rows);
                }
            }

        }

        void createTable(String name, List<Map<String, Object>> rows) {
            YamlDataTable table = new YamlDataTable(name,
                    (rows != null && rows.size() > 0) ? new ArrayList<>(rows.get(0).keySet()) : null);
            if (rows != null) {
                for (Map<String, Object> values : rows) {
                    table.addRow(values);
                }
            }
            tables.put(name.toUpperCase(), table);
        }

        public ITable getTable(String tableName) {
            return tables.get(tableName.toUpperCase());
        }

        public ITableMetaData getTableMetaData(final String tableName) {
            YamlDataTable table = tables.get(tableName.toUpperCase());
            if (table != null) {
                return tables.get(tableName.toUpperCase()).getTableMetaData();
            }
            return null;
        }

        public String[] getTableNames() {
            return tables.keySet().toArray(new String[tables.size()]);
        }

        public ITable[] getTables() {
            return tables.values().toArray(new ITable[tables.size()]);
        }

        public ITableIterator iterator() {
            return new DefaultTableIterator(getTables());
        }

        public ITableIterator reverseIterator() {
            return new DefaultTableIterator(getTables(), true);
        }

        public boolean isCaseSensitiveTableNames() {
            return false;
        }

    }

    private static class YamlDataTable implements ITable {
        String name;

        List<Map<String, Object>> data;

        ITableMetaData meta;

        YamlDataTable(String name, List<String> columnNames) {
            this.name = name;
            this.data = new ArrayList<>();
            meta = createMeta(name, columnNames);
        }

        ITableMetaData createMeta(String name, List<String> columnNames) {
            Column[] columns;
            if (columnNames != null) {
                columns = new Column[columnNames.size()];
                for (int i = 0; i < columnNames.size(); i++) {
                    columns[i] = new Column(columnNames.get(i), DataType.UNKNOWN);
                }
            } else {
                columns = new Column[0];
            }
            return new DefaultTableMetaData(name, columns);
        }

        public int getRowCount() {
            return data.size();
        }

        public ITableMetaData getTableMetaData() {
            return meta;
        }

        public Object getValue(int row, String column) throws DataSetException {
            if (data.size() <= row) {
                throw new RowOutOfBoundsException("" + row);
            }
            return data.get(row).get(column.toUpperCase());
        }

        void addRow(Map<String, Object> values) {
            data.add(convertMap(values));
        }

        Map<String, Object> convertMap(Map<String, Object> values) {
            Map<String, Object> ret = new HashMap<>();
            for (Map.Entry<String, Object> ent : values.entrySet()) {
                ret.put(ent.getKey().toUpperCase(), ent.getValue());
            }
            return ret;
        }

    }
}

