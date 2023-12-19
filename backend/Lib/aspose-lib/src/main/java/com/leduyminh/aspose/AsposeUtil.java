package com.leduyminh.aspose;

import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.leduyminh.aspose.enums.ExportType;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

public class AsposeUtil {

    public static <T> void generateFileByTemplate(HttpServletResponse response,
                                                  InputStream inputStream, Map<String, Object> datas, ExportType docType, String fileName) throws Exception {
        aspose.total.product.License.apply();
        switch (docType) {
            case EXCEL: {
                Workbook workbook = new Workbook(inputStream);
                WorkbookDesigner designer = new WorkbookDesigner(workbook);
                for (Map.Entry<String, Object> entry : datas.entrySet()) {
                    designer.setDataSource(entry.getKey(), entry.getValue());
                }
                designer.process();
                if(StringUtils.isNullOrEmpty(fileName)) fileName = "download.xlsx";
                response.setContentType("application/vnd.ms-excel");
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {
                    workbook.save(bos, FileFormatType.XLSX);
                    response.getOutputStream().write(bos.toByteArray());
                    response.setHeader("Content-disposition", "attachment; filename=" + fileName);
                } finally {
                    bos.close();
                }

                break;
            }
            case WORD: {
                //TODO
                break;
            }
            case PDF:
               //TODO
                break;
            default:
                throw new UnsupportedOperationException("Document type not support");
        }

    }

    public static byte[] generateFileByTemplate(InputStream inputStream, Map<String, Object> datas, ExportType docType) throws Exception {
        aspose.total.product.License.apply();
        switch (docType) {
            case EXCEL: {
                Workbook workbook = new Workbook(inputStream);
                WorkbookDesigner designer = new WorkbookDesigner(workbook);
                for (Map.Entry<String, Object> entry : datas.entrySet()) {
                    designer.setDataSource(entry.getKey(), entry.getValue());
                }
                designer.process();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {
                    workbook.save(bos, FileFormatType.XLSX);
                    return bos.toByteArray();
                } finally {
                    bos.close();
                }
            }
            case WORD: {
                //TODO
                return null;
            }
            case PDF:
                //TODO
                return null;
            default:
                throw new UnsupportedOperationException("Document type not support");
        }

    }
}

