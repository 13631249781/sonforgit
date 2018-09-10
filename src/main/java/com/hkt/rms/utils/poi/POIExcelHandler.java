package com.hkt.rms.utils.poi;


/*import com.hkt.opg.configuration.Constants;
import com.hkt.opg.util.UtilBase;*/

/**
 * This class is used to handle processes of Excel files. Note that the Excel spreadsheet must be in correct Excel Binary format so as to to be read via Apache
 * POI API. You can use the Excel program to save the file to Excel Binary format.
 * 
 * @author Kevin K.
 */
public class POIExcelHandler
{
	protected final static String ExcelFileExtension = ".xls";

	/**
	 * Constructor
	 */
	public POIExcelHandler()
	{}

	/**
	 * Get the cell type of the input cell, and return as a string
	 * 
	 * @param cell
	 *        A cell
	 * @return The string of the cell type of the input cell
	 */
/*	public static String getCellTypeStr(HSSFCell cell)
	{
		String cell_type_str = null;
		int cell_type = cell.getCellType();
		switch (cell_type)
		{
			case Cell.CELL_TYPE_STRING:
				cell_type_str = "CELL_TYPE_STRING";
				break;
			case Cell.CELL_TYPE_NUMERIC:
				cell_type_str = "CELL_TYPE_NUMERIC";
				break;
			case Cell.CELL_TYPE_FORMULA:
				cell_type_str = "CELL_TYPE_FORMULA";
				break;
			case Cell.CELL_TYPE_ERROR:
				cell_type_str = "CELL_TYPE_ERROR";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cell_type_str = "CELL_TYPE_BOOLEAN";
				break;
			case Cell.CELL_TYPE_BLANK:
				cell_type_str = "CELL_TYPE_BLANK";
				break;
			default:
				cell_type_str = Integer.toString(cell_type);
				break;
		}

		return cell_type_str;
	}*/

	/**
	 * Get the cell type of the formula evaluation of the input formula cell
	 * 
	 * @param wb
	 *        The excel workbook of the formula cell
	 * @param sheet
	 *        The excel spreadsheet of the formula cell
	 * @param row
	 *        The row of the formula cell
	 * @param cell
	 *        The formula cell
	 * @return The cell type of the formula evaluation of the input formula cell
	 */
/*	public static int getFormulaCellEvaluationCellType(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell)
	{
		HSSFFormulaEvaluator formula_evaluator = new HSSFFormulaEvaluator(wb);
		CellValue cell_value = formula_evaluator.evaluate(cell);
		return cell_value.getCellType();
	}
*/
	/**
	 * Get the cell type of the formula evaluation of the input formula cell, and return as a string
	 * 
	 * @param wb
	 *        The excel workbook of the formula cell
	 * @param sheet
	 *        The excel spreadsheet of the formula cell
	 * @param row
	 *        The row of the formula cell
	 * @param cell
	 *        The formula cell
	 * @return The string of the cell type of the formula evaluation of the input formula cell
	 */
/*	public static String getFormulaCellEvaluationCellTypeStr(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell)
	{
		String cell_type_str = null;
		HSSFFormulaEvaluator formula_evaluator = new HSSFFormulaEvaluator(wb);
		CellValue cell_value = formula_evaluator.evaluate(cell);

		int cell_type = cell_value.getCellType();
		switch (cell_type)
		{
			case Cell.CELL_TYPE_STRING:
				cell_type_str = "CELL_TYPE_STRING";
				break;
			case Cell.CELL_TYPE_NUMERIC:
				cell_type_str = "CELL_TYPE_NUMERIC";
				break;
			case Cell.CELL_TYPE_FORMULA:
				cell_type_str = "CELL_TYPE_FORMULA";
				break;
			case Cell.CELL_TYPE_ERROR:
				cell_type_str = "CELL_TYPE_ERROR";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cell_type_str = "CELL_TYPE_BOOLEAN";
				break;
			case Cell.CELL_TYPE_BLANK:
				cell_type_str = "CELL_TYPE_BLANK";
				break;
			default:
				cell_type_str = Integer.toString(cell_type);
				break;
		}

		return cell_type_str;
	}*/

	/**
	 * Evaluate the formula of the input formula cell, and return the result as a string
	 * 
	 * @param wb
	 *        The excel workbook of the formula cell
	 * @param sheet
	 *        The excel spreadsheet of the formula cell
	 * @param row
	 *        The row of the formula cell
	 * @param cell
	 *        The formula cell
	 * @return The string of the formula evaluation of the input formula cell
	 */
/*	protected static String getFormulaCellEvaluationStr(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell)
	{
		String cell_str = null;
		HSSFFormulaEvaluator formula_evaluator = new HSSFFormulaEvaluator(wb);
		CellValue cell_value = formula_evaluator.evaluate(cell);

		switch (cell_value.getCellType())
		{
			// case Cell.CELL_TYPE_STRING:
			// cell_str = cell_value.getStringValue();
			// break;
			case Cell.CELL_TYPE_NUMERIC:
				cell_str = String.valueOf(cell_value.getNumberValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				cell_str = String.valueOf(cell_value.getErrorValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cell_str = String.valueOf(cell_value.getBooleanValue());
				break;
			default:
				cell_str = cell_value.getStringValue();
				break;
		}

		return cell_str;
	}*/

	/**
	 * Get the contents of the input formula cell, and return as a string
	 * 
	 * @param wb
	 *        The excel workbook of the formula cell
	 * @param sheet
	 *        The excel spreadsheet of the formula cell
	 * @param row
	 *        The row of the formula cell
	 * @param cell
	 *        The formula cell
	 * @return The string of the contents of the formula cell
	 */
/*	protected static String getFormulaCellStr(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell)
	{
		String cell_str = null;

		if (wb != null && sheet != null && row != null)
		{
			// Return the formula evaluation of the formula cell
			cell_str = getFormulaCellEvaluationStr(wb, sheet, row, cell);
		}
		else
		{
			// Return the formula of the formula cell
			cell_str = cell.getRichStringCellValue().toString();
		}

		return cell_str;
	}*/

	/**
	 * Get the contents of the input cell, and return as a string
	 * 
	 * @param wb
	 *        The excel workbook of the cell
	 * @param sheet
	 *        The excel spreadsheet of the cell
	 * @param row
	 *        The row of the cell
	 * @param cell
	 *        The cell
	 * @param IsNumericCell
	 *        A boolean that decides if the cell is a numeric cell or not
	 * @return The string of the contents of the cell
	 */
	/*protected static String getCellStr(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell, boolean IsNumericCell)
	{
		String cell_str = null;

		int cell_type = cell.getCellType();
		switch (cell_type)
		{
			// case Cell.CELL_TYPE_STRING:
			// cell_str = cell.getRichStringCellValue().toString();
			// break;
			case Cell.CELL_TYPE_NUMERIC:
				if (IsNumericCell)
					cell_str = String.valueOf(cell.getNumericCellValue());
				else if (DateUtil.isCellDateFormatted(cell))
					cell_str = UtilBase.dateFormat(cell.getDateCellValue(), Constants.SIMPLEDATETIME_FORMAT);
				else
				{
					// To solve the problem that an integer is changed to be a floating point number
					cell_str = Long.toString((long) cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_FORMULA:
				cell_str = getFormulaCellStr(wb, sheet, row, cell);
				break;
			case Cell.CELL_TYPE_ERROR:
				cell_str = String.valueOf(cell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cell_str = String.valueOf(cell.getBooleanCellValue());
				break;
			default:
				// Default assume this is Cell.CELL_TYPE_STRING.
				cell_str = cell.getRichStringCellValue().toString();
				break;
		}

		return cell_str.trim();
	}*/

	/**
	 * Get the contents of the input cell, and return as a string
	 * 
	 * @param wb
	 *        The excel workbook of the cell
	 * @param sheet
	 *        The excel spreadsheet of the cell
	 * @param row
	 *        The row of the cell
	 * @param cell
	 *        The cell
	 * @return The string of the contents of the cell
	 */
/*	protected static String getCellStr(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell)
	{
		return getCellStr(wb, sheet, row, cell, false);
	}*/

	/**
	 * Get the contents of the input cell, and return as a string
	 * 
	 * @param cell
	 *        A cell
	 * @return The string of the contents of the cell
	 */
/*	protected static String getCellStr(HSSFCell cell)
	{
		return getCellStr(null, null, null, cell);
	}*/
}
