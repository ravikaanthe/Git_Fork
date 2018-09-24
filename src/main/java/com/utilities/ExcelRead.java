package com.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class ExcelRead {

	static String filePath = System.getProperty("user.dir") ;
	static String fileName = "DRLTestData.xlsx";
	static File file;
	static FileInputStream inputStream;
	static Workbook excelWB = null;
	static String fileExtensionName = "";
	static int rowCount1 = 0;
	static int colCount = 0;
	static String[][] testData = null;
	static String sheetName = "";

	@SuppressWarnings("deprecation")
	@DataProvider(name = "TestData")
	public static String[][] readExcel(Method m) throws IOException {
		sheetName = m.getName();

		System.out.println("Test Method:" + sheetName);

		try {
			file = new File(filePath + "//" + fileName);
			inputStream = new FileInputStream(file);

			fileExtensionName = fileName.substring(fileName.indexOf("."));

			if (fileExtensionName.equals(".xlsx")) {
				excelWB = new XSSFWorkbook(inputStream);
			} else if (fileExtensionName.equals(".xls")) {
				excelWB = new HSSFWorkbook(inputStream);
			}

			Sheet excelSheet = excelWB.getSheet(sheetName);
			rowCount1 = excelSheet.getLastRowNum();
			int totalRows = rowCount1;
			colCount = excelSheet.getRow(0).getLastCellNum();
			testData = new String[rowCount1][colCount];
			System.out.println("Total Number of Rows: " + totalRows);

			for (int i = 0; i <= totalRows - 1; i++) {
				Row row = excelSheet.getRow(i + 1);
				int colCounts = row.getLastCellNum();
				System.out.println("Total Number of Cols: " + colCounts);

				for (int j = 0; j < colCounts; j++) {
					// Cell cell = row.getCell(j);
					try {
						Cell cell = row.getCell(j);
						if (cell != null) {
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
								testData[i][j] = cell.toString();
								break;
							case Cell.CELL_TYPE_NUMERIC:
								if (DateUtil.isCellDateFormatted(cell)) {
									SimpleDateFormat dateFormat = new SimpleDateFormat("d-MMM-yyyy");
									testData[i][j] = dateFormat.format(cell.getDateCellValue());
								} else {
									Double value = cell.getNumericCellValue();
									Long longValue = value.longValue();
									testData[i][j] = new String(longValue.toString());
								}
								break;
							case Cell.CELL_TYPE_BOOLEAN:
								testData[i][j] = new String(new Boolean(cell.getBooleanCellValue()).toString());
								break;
							case Cell.CELL_TYPE_BLANK:
								testData[i][j] = "";
								break;
							}
						}

					}

					catch (NullPointerException ex) {
						System.out.println("The cell (" + i + "," + j + ") is empty");
						testData[i][j] = "";
					}

					System.out.println(testData[i][j]);

				}

			}
		} catch (FileNotFoundException fne)

		{
			System.out.println("Test Data file not found:" + "" + fne.toString());
			throw (fne);
		} catch (IOException ie)

		{
			System.out.println("Not able to read from Test data file" + "" + ie.toString());
			throw (ie);
		}

		inputStream.close();
		return testData;

	}
}
