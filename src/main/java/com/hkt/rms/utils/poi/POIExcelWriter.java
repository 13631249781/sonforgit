package com.hkt.rms.utils.poi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hkt.rms.dao.DbHandler;

//import com.hkt.opg.persist.DbHandler;

/**
 * This class is used to handle writing processes of Excel files.
 * 
 * @author Kevin K.
 */
public class POIExcelWriter extends POIExcelHandler
{
	public POIExcelWriter()
	{}

	/**
	 * Process the received data
	 * 
	 * @param sheet
	 *        The Excel sheet
	 * @param data_field_name_array
	 *        An array of the names of the excel sheet data fields
	 * @param db_table_row_array_list
	 *        An array list of the row data retrieved from the database table
	 */
	private static void processRetrievedData(HSSFSheet sheet, String[] data_field_name_array, ArrayList<ArrayList<String>> db_table_row_array_list)
	{
		for (int i = 0; i < db_table_row_array_list.size(); i++)
		{
			// Process a database table row
			HSSFRow row = sheet.createRow(i);
			ArrayList<String> row_field_content_str_array_list = db_table_row_array_list.get(i);

			for (int j = 0; j < row_field_content_str_array_list.size(); j++)
			{
				// Process a database table row field
				HSSFCell cell = row.createCell(j);

				if (i == 0 && data_field_name_array != null)
					cell.setCellValue(new HSSFRichTextString(data_field_name_array[j])); // Title row field
				else cell.setCellValue(new HSSFRichTextString(row_field_content_str_array_list.get(j))); // Data row field
			}
		}
	}

/*	public static void appendRetrievedData(HSSFSheet sheet, String[] data_field_name_array, ArrayList<ArrayList<String>> db_table_row_array_list)
	{
		// Start at row 0 for new sheet. Otherwise, start a new row by adding 1.
		int startRowIndex = sheet.getLastRowNum() == 0 ? 0 : sheet.getLastRowNum() + 1;

		if (data_field_name_array != null)
		{
			HSSFRow row = sheet.createRow(startRowIndex);
			startRowIndex++;
			
			for (int j = 0; j < data_field_name_array.length; j++)
			{
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(new HSSFRichTextString(data_field_name_array[j])); // Title row field
			}
		}

		for (int i = 0; i < db_table_row_array_list.size(); i++)
		{
			// Process a database table row
			HSSFRow row = sheet.createRow(startRowIndex + i);
			ArrayList<String> row_field_content_str_array_list = db_table_row_array_list.get(i);

			for (int j = 0; j < row_field_content_str_array_list.size(); j++)
			{
				// Process a database table row field
				HSSFCell cell = row.createCell(j);

				// if (i == 0 && data_field_name_array != null)
				// cell.setCellValue(new HSSFRichTextString(data_field_name_array[j])); // Title row field

				cell.setCellValue(new HSSFRichTextString(row_field_content_str_array_list.get(j))); // Data row field
			}
		}
	}*/

	/**
	 * Create an excel sheet of the input excel workbook
	 * 
	 * @param conn
	 *        The connection with the database
	 * @param wb
	 *        The excel workbook
	 * @param sheet_name
	 *        The name of the sheet to be created
	 * @param db_table_name
	 *        The name of the database table
	 * @param data_field_name_array
	 *        An array of the names of the excel sheet data fields
	 * @param db_data_field_name_array
	 *        An array of the names of the database data fields
	 * @param condition_clause
	 *        The condition_clause of the SQL query
	 * @throws SQLException
	 *         if a sql error occur
	 */
	/*protected static void createSheet(Connection conn, HSSFWorkbook wb, String sheet_name, String db_table_name, String[] data_field_name_array,
	        String[] db_data_field_name_array, String condition_clause) throws SQLException
	{
		HSSFSheet sheet = wb.createSheet(sheet_name);

		// Retrieve the data
		ArrayList<ArrayList<String>> db_table_row_array_list = DbHandler.retrieveDbTable(conn, db_table_name, db_data_field_name_array, condition_clause);

		// Process the received data
		processRetrievedData(sheet, data_field_name_array, db_table_row_array_list);
	}
*/
	/**
	 * Create an excel sheet of the input excel workbook
	 * 
	 * @param conn
	 *        The connection with the database
	 * @param wb
	 *        The excel workbook
	 * @param sheet_name
	 *        The name of the sheet to be created
	 * @param sql_query
	 *        The SQL query to be used to retreive the data
	 * @param data_field_name_array
	 *        An array of the names of the excel sheet data fields
	 * @throws SQLException
	 *         SQLException if a sql error occur
	 */
	public static void createSheet(JdbcTemplate jdbcTemplate, HSSFWorkbook wb, String sheet_name, String sql_query, String[] data_field_name_array) throws SQLException
	{
		HSSFSheet sheet = wb.createSheet(sheet_name);

		// Retrieve the data
		ArrayList<ArrayList<String>> db_table_row_array_list = DbHandler.retrieveData(jdbcTemplate, sql_query);

		// Process the received data
		processRetrievedData(sheet, data_field_name_array, db_table_row_array_list);
	}

	/*public static void appendSheet(Connection conn, HSSFSheet sheet, String sql_query, String[] data_field_name_array) throws SQLException
	{
		// Retrieve the data
		ArrayList<ArrayList<String>> db_table_row_array_list = DbHandler.retrieveData(conn, sql_query);

		// Append the received data
		appendRetrievedData(sheet, data_field_name_array, db_table_row_array_list);
	}*/

/*	public static void appendRow(HSSFWorkbook wb, HSSFSheet sheet, String text)
	{
		int startRowIndex = sheet.getLastRowNum() == 0 ? 0 : sheet.getLastRowNum() + 2;

		// Append the received data
		HSSFRow row = sheet.createRow(startRowIndex);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString(text)); // Data row field

		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		cell.setCellStyle(style);
	}*/
}