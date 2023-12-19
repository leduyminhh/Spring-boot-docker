package com.leduyminh.aspose;

import com.aspose.words.DataRow;
import com.aspose.words.DataTable;
import com.aspose.words.Document;

import java.io.File;
import java.util.*;

import static java.io.File.createTempFile;

public class AsposeWordUtil {
    private List<TableCustom> tables = new ArrayList<TableCustom>();
    private Map<String, Object> parameters = new HashMap<String, Object>();

    public File export(String template) throws Exception {
        aspose.total.product.License.apply();
        File result = createTempFile("export", ".docx");
        Document document = new Document(template);

        // Bien doi cac gia tri TRUE/FALSE ve checkBox
        document.getMailMerge().setFieldMergingCallback(new HandleMergeField());

        if (!parameters.isEmpty()) {
            document.getMailMerge().execute(parameters.keySet().toArray(new String[0]), parameters.values().toArray());
        }
        if (tables.size() > 0) {
            for (TableCustom table : tables) {
                DataTable data = new DataTable(table.getName());
                for (String column : table.getColumns()) {
                    data.getColumns().add(column);
                }

                int size = table.getVariables().size();
                for (int count = 0; count < size; count++) {
                    Map<Integer, String> map = table.getVariables().get(count);
                    Iterator<Integer> iterator = map.keySet().iterator();
                    DataRow row = data.newRow();

                    while (iterator.hasNext()) {
                        int position = iterator.next();
                        row.set(position, map.get(position));
                    }

                    data.getRows().add(row);
                }

                document.getMailMerge().executeWithRegions(data);
            }
        }
        document.save(result.getPath());

        return result;
    }

    public void addParam(String key, Object value) throws Exception {
        if (key == null || key.isEmpty() || value == null) throw new NullPointerException();
        this.parameters.put(key, value);
    }

    public void addTable(TableCustom tableCustom) {
        this.tables.add(tableCustom);
    }
}
