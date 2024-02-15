package de.execlLoad;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;

public class ImportFile {
    public static void main(String[] args) {
        ImportWunschSchuler();
       // ImportUnternehemenList();



    }

    private static void ImportUnternehemenList() {
        try {
            File f = new File("H:\\SUD\\BOT2_Wahl.xlsx");
            Workbook wb = new XSSFWorkbook(f);
            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t");
                            break;
                        default:
                            System.out.print("•\t");
                    }
                }
                System.out.println();
            }
            wb.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void ImportWunschSchuler(){
        try {
            File f = new File("H:\\SUD\\BOT2_Wahl.xlsx");
            Workbook wb = new XSSFWorkbook(f);
            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t");
                            break;
                        default:
                            System.out.print("•\t");
                    }
                }
                System.out.println();
            }
            wb.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
