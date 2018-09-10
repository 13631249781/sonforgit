package com.hkt.rms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import com.hkt.rms.utils.FileHandler;
import com.hkt.rms.utils.UtilBase;
import com.hkt.rms.utils.poi.POIExcelHandler;
import com.hkt.rms.utils.poi.POIExcelWriter;

/*import com.hkt.opg.persist.FileHandler;
import com.hkt.opg.util.UtilBase;
import com.hkt.opg.util.poi.POIExcelHandler;
import com.hkt.opg.util.poi.POIExcelWriter;*/

/**
 * This class is used to handle writing processes of Excel files of the SQL loader.
 * 
 * @author Kevin K.
 */
public class SQLLoader extends POIExcelWriter
{
	private final static int MaxRowsOfSelection = 500;
	private final static String ExcelFileNamePrefix = "result";

	private static void createSheets(JdbcTemplate jdbcTemplate, HSSFWorkbook wb, String[] sqls) throws SQLException
	{
		int numberOfProcessedSelectSql = 0;

		for (String sql : sqls)
		{
			sql = sql.trim();
			// Process a SELECT SQL only
			if (!sql.equals("") && sql.toUpperCase().startsWith("SELECT"))
			    POIExcelWriter.createSheet(jdbcTemplate, wb, "Sheet" + (++numberOfProcessedSelectSql), sql, null);
		}
	}

	public static void createExcelFile(JdbcTemplate jdbcTemplate, HttpServletResponse response, String[] sqls) throws Exception
	{
		String excel_file_name = ExcelFileNamePrefix + POIExcelHandler.ExcelFileExtension;
		HSSFWorkbook wb = new HSSFWorkbook();

		try
		{
			createSheets(jdbcTemplate, wb, sqls);
			FileHandler.writeExcelWorkbookToFile(wb, excel_file_name, response); // Write the workbook to a file
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Process the input SQLs
	 * 
	 * @param request
	 *        The HTTP servlet request
	 * @param response
	 *        The HTTP servlet response
	 * @param sql
	 *        The input SQLs
	 * @throws SQLException
	 */
	public static ArrayList<String> processSQLs(JdbcTemplate jdbcTemplate, String sqls)
	{
		// Note that SQLs are separated by a semicolon
		String[] sqlArray = sqls.split(";");
		ArrayList<String> result = new ArrayList<String>();

			// Process the SQLs
			for (int i = 0; i < sqlArray.length; i++)
			{
				String sqlToBeProcessed = sqlArray[i].trim();

				if (!sqlToBeProcessed.equals(""))
				{
					if (sqlToBeProcessed.toUpperCase().startsWith("SELECT"))
						result.add(processSelectSQL(jdbcTemplate, sqlToBeProcessed));
					else result.add(processUpdateSQL(jdbcTemplate, sqlToBeProcessed));
				}
			}
		return result;
	}

	/**
	 * Process a select SQL
	 * 
	 * @param conn
	 *        The connection
	 * @param request
	 *        The HTTP servlet request
	 * @param response
	 *        The HTTP servlet response
	 * @param sql
	 *        The sql to be processed
	 * @param return_message_array_list
	 *        The array list of the return message
	 * @throws SQLException
	 */
	private static String processSelectSQL(JdbcTemplate jdbcTemplate, String sql)
	{
		int processedRowCounter = 0;
		boolean addedTitleRow = false;
		StringBuffer sb = new StringBuffer();

			/*ps = conn.prepareStatement(sql);*/
		/*	rs = ps.executeQuery();*/
			SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);

			sb.append("<p><table cellpadding=2 STYLE=\"border-collapse: collapse;\" border=\"1\" bordercolor=\"black\">");

			// Convert result set to arraylist
			while (sqlRowSet.next())
			{
				if (processedRowCounter == MaxRowsOfSelection) break;

				SqlRowSetMetaData rsmd = sqlRowSet.getMetaData();

				// Add the title row for once
				if (!addedTitleRow)
				{
					String titleRow = "<tr>";
					for (int i = 1; i <= rsmd.getColumnCount(); i++)
					{
						String title_row_field_content_str = rsmd.getColumnName(i);
						if (title_row_field_content_str.equals("")) title_row_field_content_str = "&nbsp;";

						titleRow += "<th>" + title_row_field_content_str + "</th>";
					}
					titleRow += "</tr>";
					sb.append(titleRow);
					addedTitleRow = true;
				}

				// Add a data row
				String data_row = "<tr class=" + (processedRowCounter % 2 == 0 ? "tableRowEven" : "tableRowOdd") + ">";
				for (int i = 1; i <= rsmd.getColumnCount(); i++)
				{
					String data_row_field_content_str = UtilBase.mapNull2Empty(sqlRowSet.getString(i)).trim();
					if (data_row_field_content_str.equals("")) data_row_field_content_str = "&nbsp;";
					data_row += "<td>" + data_row_field_content_str + "</td>";
				}
				data_row += "</tr>";
				sb.append(data_row);

				processedRowCounter++;
			}

			if (!addedTitleRow) return "<p><table><tr><td>No row selected.</td></tr></table></p>";

			sb.append("</table></p>");

		return sb.toString();
	}

	/**
	 * Process a update SQL
	 * 
	 * @param conn
	 *        The connection
	 * @param request
	 *        The HTTP servlet request
	 * @param response
	 *        The HTTP servlet response
	 * @param sql
	 *        The sql to be processed
	 * @param return_message_array_list
	 *        The array list of the return message
	 * @throws SQLException
	 */
	private static String processUpdateSQL(JdbcTemplate jdbcTemplate, String sql) 
	{
		        int return_int = jdbcTemplate.update(sql);
			return "<p><table><tr><td>" + return_int + " row(s) is/are affected.</td></tr></table></p>";
	}
}