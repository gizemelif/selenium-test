import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadExcel {
    private String vNo;

    public String getvNo() {
        return vNo;
    }

    public void setvNo(String vNo) {
        this.vNo = vNo;
    }

    public List<String> readExcel() throws IOException {
        File excelFile = new File("C:\\Users\\geatalay\\Desktop\\Vergi_numarası_kontrol\\vergi_no_kayseri.xlsx");
        FileInputStream fis = new FileInputStream(excelFile);

        List<String> vNoList = Lists.newArrayList();

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        // we iterate on rows
        Iterator<Row> rowIt = sheet.iterator();

        /*if (rowIt.hasNext()) {
            rowIt.next();
        }*/
        while (rowIt.hasNext()) {
            Row row = rowIt.next();

            // iterate on cells for the current row
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell1 = cellIterator.next();
                cell1.setCellType(CellType.STRING);

                if (cell1 == null) {
                    continue;
                }else if(cell1.getStringCellValue().length() != 10){
                    continue;
                }
                else{
                    this.vNo = cell1.getStringCellValue();
                }
                System.out.println("Vergi numarası: "+this.vNo);
                System.out.println("Vergi numarası: " + new Long(new Double(cell1.getNumericCellValue()).longValue()).toString());

            }
            vNoList.add(this.vNo);
        }

        workbook.close();
        fis.close();

        return vNoList;
    }
}
