package api.utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {

    private static final String filePath = "testData/user_dummy_data.xlsx";
    private static final int sheetIndex = 0;

    // Method to get all data from the Excel file
    @DataProvider(name = "Data")
    public static String[][] getAllData() {
        XLUtility xlUtility = new XLUtility(filePath, sheetIndex);
        int rowCount = xlUtility.getRowCount();
        int cellCount = xlUtility.getCellCount(0);

        // Initialize a 2D array to hold all data except headers
        String[][] allData = new String[rowCount - 1][cellCount];

        for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
            for (int colIndex = 0; colIndex < cellCount; colIndex++) {
                allData[rowIndex - 1][colIndex] = xlUtility.getCellData(rowIndex, colIndex);
            }
        }

        xlUtility.closeWorkbook();
        return allData;
    }

    // Method to get only usernames from the Excel file
    @DataProvider(name = "UserNames")
    public static String[] getUserNames() {
        XLUtility xlUtility = new XLUtility(filePath, sheetIndex);
        int rowCount = xlUtility.getRowCount();
        
        // Initialize an array to hold usernames
        String[] userNames = new String[rowCount - 1];

        for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
            userNames[rowIndex - 1] = xlUtility.getCellData(rowIndex, 1); // Username is in the 2nd column (index 1)
        }

        xlUtility.closeWorkbook();
        return userNames;
    }
    
    public static void main(String args[]) {
		// TODO Auto-generated method stub
    	for(String a: getUserNames()) {
    		System.out.println(a);
    	}
	}
}
