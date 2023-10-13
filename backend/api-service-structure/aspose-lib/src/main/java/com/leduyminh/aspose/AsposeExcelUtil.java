package com.leduyminh.aspose;

import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;

import java.io.File;
import java.util.*;

import static java.io.File.createTempFile;
import static java.util.Map.Entry;

public class AsposeExcelUtil<E> {
    private List<E> list = new ArrayList<E>();
    private Map<String, Object> parameters = new HashMap<String, Object>();

    public File export(String template) throws Exception {
        aspose.total.product.License.apply();
        File result = createTempFile("export", ".xlsx");
        WorkbookDesigner designer = new WorkbookDesigner();
        designer.setWorkbook(new Workbook(template));
        if (!parameters.isEmpty()) {
            Iterator entries = parameters.entrySet().iterator();
            while (entries.hasNext()) {
                Entry entry = (Entry) entries.next();
                String key = (String) entry.getKey();
                designer.setDataSource(key, entry.getValue());
            }
        }
        designer.setDataSource("list", list);
        designer.process(true);
        designer.getWorkbook().save(result.getPath());
        return result;

    }


    public void addParam(String key, Object value) throws Exception {
        if (key == null || key.isEmpty() || value == null) throw new NullPointerException();
        this.parameters.put(key, value);
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}
