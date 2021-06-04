package ecma.ai.ussdapp.component;

import ecma.ai.ussdapp.entity.Details;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class DetailsniExseldanOlishUchun {

    @SneakyThrows
    public void takeExcel(HttpServletResponse response, List<Details> details) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Details");
        XSSFRow row = sheet.createRow(0);


        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();


        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        XSSFCell cell = row.createCell(0);
        cell.setCellValue("№");
        cell.setCellStyle(cellStyle);


        cell = row.createCell(1);
        cell.setCellValue("Action Type");
        cell.setCellStyle(cellStyle);


        cell = row.createCell(1);
        cell.setCellValue("Amount");
        cell.setCellStyle(cellStyle);


        font.setBold(false);
        font.setFontHeight(14);
        cellStyle.setFont(font);

        int rowCount = 1;
        for (Details detail : details) {
            XSSFRow row1 = sheet.createRow(rowCount++);

            XSSFCell xssfCell = row1.createCell(0);
            xssfCell.setCellValue(rowCount);
            sheet.autoSizeColumn(0);
            cell.setCellStyle(cellStyle);

            xssfCell = row1.createCell(1);
            xssfCell.setCellValue(detail.getActionType().name());
            sheet.autoSizeColumn(1);
            cell.setCellStyle(cellStyle);

            xssfCell = row1.createCell(2);
            xssfCell.setCellValue(detail.getAmount());
            sheet.autoSizeColumn(2);
            cell.setCellStyle(cellStyle);
        }
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
