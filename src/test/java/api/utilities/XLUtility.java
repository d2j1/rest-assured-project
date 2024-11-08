package api.utilities;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class XLUtility {
    private Workbook workbook;
    private Sheet sheet;

    // Constructor to initialize the workbook and sheet
    public XLUtility(String filePath, int sheetIndex) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            workbook = WorkbookFactory.create(fileInputStream);
            sheet = workbook.getSheetAt(sheetIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get the row count
    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    // Method to get the cell count in a given row
    public int getCellCount(int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            return 0;
        }
        return row.getPhysicalNumberOfCells();
    }

    // Method to get the cell data from a given row and column
    public String getCellData(int rowIndex, int colIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            return "";
        }
        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            return "";
        }
        return getCellValue(cell);
    }

 // Helper method to extract cell value
    private String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // Check if the number is an integer (no decimal part)
                if (cell.getNumericCellValue() == Math.floor(cell.getNumericCellValue())) {
                    return String.valueOf((int) cell.getNumericCellValue()); // Cast to int if it's a whole number
                } else {
                    return String.valueOf(cell.getNumericCellValue()); // Keep decimal value as is
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }


    // Close the workbook when done
    public void closeWorkbook() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = "testData/user_dummy_data.xlsx";
        XLUtility xlUtility = new XLUtility(filePath, 0); // First sheet (index 0)

        // Get row count
        int rowCount = xlUtility.getRowCount();
        System.out.println("Total Rows: " + rowCount);

        // Get cell count in the first row
        int cellCount = xlUtility.getCellCount(0);
        System.out.println("Total Cells in first row: " + cellCount);

        // Get data from each cell in the first row (headers)
        System.out.print("Headers: ");
        for (int i = 0; i < cellCount; i++) {
            System.out.print(xlUtility.getCellData(0, i) + "\t");
        }
        System.out.println();

        // Get data from all rows and columns
        System.out.println("\nData from all rows:");
        for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
            for (int colIndex = 0; colIndex < cellCount; colIndex++) {
                System.out.print(xlUtility.getCellData(rowIndex, colIndex) + "\t");
            }
            System.out.println();
        }

        // Get data from a specific cell (3rd row, 2nd column)
        System.out.println("\nData from 3rd row and 2nd column: " +
                xlUtility.getCellData(2, 1));

        // Close workbook
        xlUtility.closeWorkbook();
    }

}
