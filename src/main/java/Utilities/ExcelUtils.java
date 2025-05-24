package Utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.text.SimpleDateFormat;

public class ExcelUtils {

	// Read data from Excel
	public static String readExcel(String filePath, int sheetIndex, int rowNumber, int columnNumber) {
		try (FileInputStream fileInputStream = new FileInputStream(filePath);
				Workbook workbook = new XSSFWorkbook(fileInputStream)) {
			Sheet sheet = workbook.getSheetAt(sheetIndex - 1);
			Row row = sheet.getRow(rowNumber - 1);
			if (row == null) {
				return "0";
			}
			Cell cell = row.getCell(columnNumber - 1);
			if (cell == null) {
				return "0";
			}
			switch (cell.getCellType()) {
			case NUMERIC:
				double numericValue = cell.getNumericCellValue();
				return (numericValue == (long) numericValue) ? String.valueOf((long) numericValue)
						: String.valueOf(numericValue);
			case STRING:
				return cell.getStringCellValue();
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			default:
				return "";
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "Error reading file";
		}
	}

//	Read Numeric type value & return numeric
	public static Double readNumberExcel(String filePath, int sheetIndex, int rowNumber, int columnNumber) {
		try (FileInputStream fileInputStream = new FileInputStream(filePath);
				Workbook workbook = new XSSFWorkbook(fileInputStream)) {

			Sheet sheet = workbook.getSheetAt(sheetIndex - 1);
			Row row = sheet.getRow(rowNumber - 1);
			if (row == null) {
				return 0.0;
			}

			Cell cell = row.getCell(columnNumber - 1);
			if (cell == null) {
				return 0.0;
			}

			switch (cell.getCellType()) {
			case NUMERIC:
				return (Double) cell.getNumericCellValue();

			case STRING:
				try {
					return Double.parseDouble(cell.getStringCellValue());
				} catch (NumberFormatException e) {
					// Handle the case where the string cannot be parsed as a long
					return 0.0;
				}

			case BOOLEAN:
				// Boolean values can't be directly converted to long; you might need to define
				// a specific convention for your use case.
				return (double) (cell.getBooleanCellValue() ? 1L : 0L);

			default:
				return 0.0;
			}

		} catch (IOException e) {
			e.printStackTrace();
			// Consider returning a default value like 0 if there's an error
			return 0.0;
		}
	}

	// Write data to Excel file
	public static void writeExcel(String filePath, int sheetIndex, int rowNumber, int columnNumber, String data) {
		try (FileInputStream fileInputStream = new FileInputStream(filePath);
				Workbook workbook = new XSSFWorkbook(fileInputStream)) {
			Sheet sheet = workbook.getSheetAt(sheetIndex - 1);
			if (sheet == null) {
				sheet = workbook.createSheet("Sheet" + (sheetIndex + 1));
			}
			Row row = sheet.getRow(rowNumber - 1);
			if (row == null) {
				row = sheet.createRow(rowNumber - 1);
			}
			Cell cell = row.getCell(columnNumber - 1);
			if (cell == null) {
				cell = row.createCell(columnNumber - 1);
			}
			cell.setCellValue(data);
			try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
				workbook.write(fileOutputStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Mark a row with a status
	public static void markRowAs(String filePath, int sheetIndex, int rowNumber, int columnNumber, String status) {
		try (FileInputStream fileInputStream = new FileInputStream(filePath);
				Workbook workbook = new XSSFWorkbook(fileInputStream)) {

			Sheet sheet = workbook.getSheetAt(sheetIndex - 1);
			if (sheet == null) {
				sheet = workbook.createSheet("Sheet" + (sheetIndex + 1));
			}
			Row row = sheet.getRow(rowNumber - 1);
			if (row == null) {
				row = sheet.createRow(rowNumber - 1);
			}
			Cell cell = row.getCell(columnNumber - 1);
			if (cell == null) {
				cell = row.createCell(columnNumber - 1);
			}
			CellStyle style = workbook.createCellStyle();
			switch (status.toLowerCase()) {
			case "success":
				style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				break;
			case "failure":
				style.setFillForegroundColor(IndexedColors.RED.getIndex());
				break;
			default:
				style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				break;
			}
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cell.setCellStyle(style);
			cell.setCellValue(status);
			try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
				workbook.write(fileOutputStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Count total entries in Excel
	public static int countTotalEntriesInExcel(String filePath, int sheetIndex) {
		int rowCount = 0;
		try (FileInputStream fileInputStream = new FileInputStream(filePath);
				Workbook workbook = new XSSFWorkbook(fileInputStream)) {
			Sheet sheet = workbook.getSheetAt(sheetIndex - 1);
			if (sheet == null) {
				return 0;
			}
			for (Row row : sheet) {
				boolean isEmpty = true;
				for (Cell cell : row) {
					if (cell.getCellType() != CellType.BLANK) {
						isEmpty = false;
						break;
					}
				}
				if (!isEmpty) {
					rowCount++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	// Find the first empty cell in a column
	public static int findFirstEmptyCellInColumn(String filePath, int sheetIndex, int columnNumber) {
		try (FileInputStream fileInputStream = new FileInputStream(filePath);
				Workbook workbook = new XSSFWorkbook(fileInputStream)) {

			Sheet sheet = workbook.getSheetAt(sheetIndex - 1);
			if (sheet == null) {
				return -1;
			}
			for (Row row : sheet) {
				Cell cell = row.getCell(columnNumber - 1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				if (cell.getCellType() == CellType.BLANK) {
					return row.getRowNum() + 1; // Convert to 1-based index
				}
			}
			return -1; // No empty cell found
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

//	Create a new workbook for writing large data with SXSSFWorkbook
	public static void createLargeDataWorkbook(String filePath) {
		try (SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook())) {
			// Create a sample sheet
			Sheet sheet = workbook.createSheet("Sample Sheet");
			// Sample data generation for testing
			for (int i = 0; i < 10000; i++) {
				Row row = sheet.createRow(i);
				Cell cell = row.createCell(0);
				cell.setCellValue("Data " + i);
			}
			try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
				workbook.write(fileOutputStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	Read date from excel
	public static String readDateFromExcel(String filePath, int sheetIndex, int rowNumber, int columnNumber) {
		String formattedDate = null;
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheetAt(sheetIndex - 1); // Get the sheet
			Row row = sheet.getRow(rowNumber - 1); // Get the specific row
			if (row != null) {
				Cell cell = row.getCell(columnNumber - 1); // Get the specific cell
				if (cell != null) {
					switch (cell.getCellType()) {
					case STRING:
						SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
						try {
							formattedDate = outputFormat.format(inputFormat.parse(cell.getStringCellValue()));
						} catch (java.text.ParseException e) {
							e.printStackTrace();
						}
						break;

					case NUMERIC:
						// Check if the cell contains a date
						if (DateUtil.isCellDateFormatted(cell)) {
							formattedDate = outputFormat.format(cell.getDateCellValue());
						} else {
							// Check if the numeric value represents a date
							double numericValue = cell.getNumericCellValue();
							if (DateUtil.isValidExcelDate(numericValue)) {
								formattedDate = outputFormat.format(DateUtil.getJavaDate(numericValue));
							}
						}
						break;

					case FORMULA:
						// Evaluate the formula and check if the result is a date
						FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
						CellValue cellValue = evaluator.evaluate(cell);
						if (cellValue.getCellType() == CellType.NUMERIC) {
							if (DateUtil.isCellDateFormatted(cell)) {
								formattedDate = outputFormat.format(cellValue.getStringValue());
							} else {
								double numericValue = cellValue.getNumberValue();
								if (DateUtil.isValidExcelDate(numericValue)) {
									formattedDate = outputFormat.format(DateUtil.getJavaDate(numericValue));
								}
							}
						} else if (cellValue.getCellType() == CellType.STRING) {
							SimpleDateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd");
							try {
								formattedDate = outputFormat.format(inputFormat1.parse(cellValue.getStringValue()));
							} catch (java.text.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					default:
						System.out.println("Cell type not supported for date extraction.");
						break;
					}
				} else {
					System.out.println("Cell not found at index " + columnNumber + 1);
				}
			} else {
				System.out.println("Row not found at index " + rowNumber + 1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return formattedDate;
	}
}
