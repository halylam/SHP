package vn.shp.app.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 
 * ExcelHelper V0.1
 * 
 * - Xử lý dữ liệu trên workbook template
 * 
 * p/s: sử dụng tốt nhất với excel 2010
 * 
 * @author CongDT
 * @since November 14, 2014
 *
 *        <pre>
 */
public class ExcelHelper {

	private CreationHelper createHelper;

	private Workbook objWorkbook;

	private String datePattern;

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	private String decimalPattern;

	public String getDecimalPattern() {
		return decimalPattern;
	}

	public void setDecimalPattern(String decimalPattern) {
		this.decimalPattern = decimalPattern;
	}

	// Khởi tạo các thành phần liên quan
	private void initApp() {
		createHelper = objWorkbook.getCreationHelper();
		setDatePattern("dd\\/MM\\/yyyy");
		setDecimalPattern("###,##0");
		// setDecimalPattern("_(* #,##0_);_(* (#,##0);_(* \"-\"??_);_(@_)");

	}

	// Khởi tạo workbook từ template
	public ExcelHelper(String templatePath, String templateNameXLSX) throws FileNotFoundException, IOException {
		File excelRoot = new File(templatePath, "/");
		objWorkbook = new XSSFWorkbook(new FileInputStream(excelRoot + templateNameXLSX));
		initApp();
	}

	// Khởi tạo workbook mới
	public ExcelHelper() {
		objWorkbook = new XSSFWorkbook();
		objWorkbook.createSheet("Sheet1");
		initApp();
	}

	// Lấy workbook
	public Workbook getWorkbook() {
		return objWorkbook;
	}

	// Lấy Sheet theo tên
	public Sheet getWorkSheet(String SheetName) {
		Sheet sheet = objWorkbook.getSheet(SheetName);
		return sheet;
	}

	// Lấy Sheet theo STT
	public Sheet getWorkSheet(int SheetSequenceNumber) {
		Sheet sheet = objWorkbook.getSheetAt(SheetSequenceNumber - 1);
		return sheet;
	}

	/* ===== BEGIN ==== fill dữ liệu vào worksheet ===== */
	// Fill dữ liệu vào 1 cell cụ thể, sử dụng cellReff vd: A1 , B55, AZ12
	public Workbook fillCell(Sheet sheet, String cellReff, Object CellValue) {
		CellReference cellReference = new CellReference(cellReff.trim().toUpperCase());
		return fillCell(sheet, cellReference, CellValue, null);
	}

	// Fill dữ liệu vào 1 cell cụ thể cùng với style
	public Workbook fillCell(Sheet sheet, String cellReff, Object CellValue, CellStyle cellStyle) {
		CellReference cellReference = new CellReference(cellReff.trim().toUpperCase());
		return fillCell(sheet, cellReference, CellValue, cellStyle);
	}

	// Fill dữ liệu vào cell theo 1 danh sách đã được định trước
	public Workbook fillCell(Sheet sheet, Map<String, Object> cellRefAndDataMap) {
		for (String key : cellRefAndDataMap.keySet()) {
			fillCell(sheet, key, cellRefAndDataMap.get(key));
		}
		return objWorkbook;
	}

	// Fill dữ liệu vào 1 cell cụ thể, xác định bằng dòng & cột
	public Workbook fillCell(Sheet sheet, int pRow, int pCol, Object CellValue) {
		CellReference cellReference = new CellReference(pRow, pCol);
		return fillCell(sheet, cellReference, CellValue, null);
	}

	// Fill dữ liệu vào 1 cell cụ thể, xác định bằng dòng & cột kèm theo style
	public Workbook fillCell(Sheet sheet, int pRow, int pCol, Object CellValue, CellStyle cellStyle) {
		CellReference cellReference = new CellReference(pRow, pCol);
		return fillCell(sheet, cellReference, CellValue, cellStyle);
	}

	public Workbook fillCell(Sheet sheet, CellReference cellReference, Object CellValue, CellStyle cellStyle) {
		return fillCell(sheet, cellReference, CellValue, cellStyle, null);
	}

	public Workbook fillCell(Sheet sheet, CellReference cellReference, Object CellValue, CellStyle cellStyle, Integer cellType) {

		Row row;
		row = sheet.getRow(cellReference.getRow());
		if (row == null) {
			row = sheet.createRow(cellReference.getRow());
		}

		Cell cell;
		cell = row.getCell((int) cellReference.getCol());
		if (cell == null) {
			cell = row.createCell((int) cellReference.getCol());
		}

		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}

		if (CellValue == null) {

		} else if (CellValue instanceof String) {
			if (cellType != null && cellType == Cell.CELL_TYPE_FORMULA) {
				cell.setCellType(cellType);
				cell.setCellFormula(String.valueOf(CellValue));
			} else {
				cell.setCellValue((String) CellValue);
			}
		} else if (CellValue instanceof Integer) {
			cell.setCellValue((Integer) CellValue);
		} else if (CellValue instanceof Double) {
			cell.setCellValue((Double) CellValue);
		} else if (CellValue instanceof BigDecimal) {
			cell.setCellValue(new BigDecimal(String.valueOf(CellValue)).doubleValue());
		} else if (CellValue instanceof RichTextString) {
			cell.setCellValue((RichTextString) CellValue);
		} else if (CellValue instanceof Date) {
			cell.setCellValue((Date) CellValue);
		} else {
			cell.setCellValue(String.valueOf(CellValue));
		}

		return objWorkbook;
	}

	// Fill nhiều dòng vào một cell
	public void fillCellMultiLine(Sheet sheet, String cellReff, List<String> contents, Boolean isAutoHeight) {

		StringBuffer sbCellContent = new StringBuffer("");
		sbCellContent.append(StringUtils.join(contents, "\n"));
		fillCell(sheet, cellReff, sbCellContent.toString());

		if (isAutoHeight) {
			int rowInCell = contents.size();
			CellReference cellReference = new CellReference(cellReff);
			Row row = sheet.getRow(cellReference.getRow());
			row.setHeightInPoints(row.getHeightInPoints() * rowInCell);
		}
	}

	public static CellStyle getCellStyle(Sheet sheet, String cellReff) {
		CellReference cellReference = new CellReference(cellReff);
		return sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol()).getCellStyle();
	}

	public static String getStringCellValue(Sheet sheet, String cellReff) {
		CellReference cellReference = new CellReference(cellReff);
		return sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol()).getStringCellValue();
	}

	// 4. Static Utils
	public static CellStyle createCellStyle(Workbook workbook) {
		return workbook.createCellStyle();
	}

	public static void fillOneCell(Sheet sheet, String CellReff, Object CellValue) {
		fillOneCell(sheet, CellReff, CellValue, null);
	}

	public static void fillOneCell(Sheet sheet, String CellReff, Object CellValue, CellStyle cellStyle) {
		new ExcelHelper().fillCell(sheet, CellReff, CellValue, cellStyle);
	}

	public static boolean checkIfRowIsEmpty(Row row) {
		if (row == null) {
			return true;
		}
		if (row.getLastCellNum() <= 0) {
			return true;
		}
		boolean isEmptyRow = true;
		for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK && StringUtils.isNotBlank(cell.toString())) {
				isEmptyRow = false;
			}
		}
		return isEmptyRow;
	}

	/**
	 * Inserts A Existing Row Into A New Row, Will Automatically Push Down Any Existing Rows. Copy Is Done Cell By Cell
	 * And Supports, And The Command Tries To Copy All Properties Available (Style, Merged Cells, Values, Etc...) <br>
	 * 
	 * @see http://www.zachhunter.com/2010/05/npoi-copy-row-helper/
	 */

	public void copyRow(Sheet worksheet, int sourceRowNum, int destinationRowNum) {

		Row newRow = worksheet.getRow(destinationRowNum);
		Row sourceRow = worksheet.getRow(sourceRowNum);

		// If the row exist in destination, push down all rows by 1 else create a new row
		if (newRow != null) {
			worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
		} else {
			newRow = worksheet.createRow(destinationRowNum);
		}

		// Loop through source columns to add to new row
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			// Grab a copy of the old/new cell
			Cell oldCell = sourceRow.getCell(i);
			Cell newCell = newRow.createCell(i);

			// If the old cell is null jump to next cell
			if (oldCell == null) {
				newCell = null;
				continue;
			}

			// Copy style from old cell and apply to new cell
			CellStyle newCellStyle = objWorkbook.createCellStyle();
			newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
			newCell.setCellStyle(newCellStyle);

			// If there is a cell comment, copy
			if (oldCell.getCellComment() != null) {
				newCell.setCellComment(oldCell.getCellComment());
			}

			// If there is a cell hyperlink, copy
			if (oldCell.getHyperlink() != null) {
				newCell.setHyperlink(oldCell.getHyperlink());
			}

			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());

			// Set the cell data value
			switch (oldCell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				newCell.setCellValue(oldCell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				newCell.setCellValue(oldCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				newCell.setCellErrorValue(oldCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				newCell.setCellFormula(oldCell.getCellFormula());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				newCell.setCellValue(oldCell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				newCell.setCellValue(oldCell.getRichStringCellValue());
				break;
			}
		}

		// If there are are any merged regions in the source row, copy to new row
		for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
			CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
			if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
				CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
						(newRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())), cellRangeAddress.getFirstColumn(),
						cellRangeAddress.getLastColumn());
				worksheet.addMergedRegion(newCellRangeAddress);
			}
		}
	}

	public static void addRow(Sheet sheet, int createNewRowAt, int numberCell) {
		Row row = sheet.createRow(createNewRowAt);
		for (int i = 0; i < numberCell; i++) {
			row.createCell(i);
		}
	}

	public static void setRowHeight(Sheet sheet, int RowNum, float HeightInPoint) {
		Row row = sheet.getRow(RowNum - 1);
		row.setHeightInPoints(HeightInPoint);
	}

	public static float getRowHeight(Sheet sheet, int RowNum) {
		Row row = sheet.getRow(RowNum - 1);
		return row.getHeightInPoints();
	}

	public static void setCellWrapJustify(Sheet sheet, String CellReff) {
		CellReference cellReference = new CellReference(CellReff.trim().toUpperCase());
		Cell cell = sheet.getRow(cellReference.getRow()).getCell((int) cellReference.getCol());
		CellStyle style = cell.getCellStyle();
		style.setWrapText(true);
		style.setAlignment(CellStyle.ALIGN_JUSTIFY);
	}

	public static void removeRow(Sheet sheet, int rowIndex) {
		int lastRowNum = sheet.getLastRowNum();
		if (rowIndex >= 0 && rowIndex < lastRowNum) {
			sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
		}
		if (rowIndex == lastRowNum) {
			Row removingRow = sheet.getRow(rowIndex);
			if (removingRow != null) {
				sheet.removeRow(removingRow);
			}
		}
	}

}
