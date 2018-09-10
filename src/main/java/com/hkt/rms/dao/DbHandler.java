package com.hkt.rms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import com.hkt.rms.utils.UtilBase;

/**
 * This class is to provide some methods to handle database operations.
 * 
 * @author Kevin K.
 */
public class DbHandler extends DAOBase
{
	// public static int delete(Connection conn, String db_table_name, String condition_clause) throws SQLException
	// {
	// Statement stmt = null;
	// String sql_query = "DELETE FROM " + db_table_name;
	// if (UtilBase.isEmptyValue(condition_clause)) sql_query += " " + condition_clause;
	//
	// try
	// {
	// stmt = conn.createStatement();
	// return stmt.executeUpdate(sql_query);
	// }
	// catch (SQLException sqlex)
	// {
	// throw sqlex;
	// }
	// finally
	// {
	// close(stmt);
	// }
	// }

	/**
	 * Truncate the input database table. Note that a truncated table cannot be rollbacked.
	 * 
	 * @param conn
	 *        The connection with the database
	 * @param db_table_name
	 *        The name of the database table to be truncated
	 * @throws SQLException
	 *         if a sql error occur
	 */
	// public static void truncateDbTable(Connection conn, String db_table_name) throws SQLException
	// {
	// Statement stmt = null;
	// String sql_query = "TRUNCATE TABLE " + db_table_name;
	//
	// try
	// {
	// stmt = conn.createStatement();
	// stmt.executeUpdate(sql_query);
	// }
	// catch (SQLException sqlex)
	// {
	// throw sqlex;
	// }
	// finally
	// {
	// close(stmt);
	// }
	// }

	/**
	 * Retrieve the data from the database by the input query, and return as an array list.
	 * 
	 * @param conn
	 *        The connection with the database
	 * @param sql_query
	 *        The SQL query to be executed
	 * @return An array list of the data retrieved
	 * @throws SQLException
	 *         if a sql error occur
	 */
	public static ArrayList<ArrayList<String>> retrieveData(JdbcTemplate jdbcTemplate,String sql) 
	{
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

		SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);

			boolean addedTitleRow = false;

			// Convert result set to arraylist
			while (sqlRowSet.next())
			{
			           SqlRowSetMetaData rsmd = sqlRowSet.getMetaData();

				// Add the title row for once
				if (!addedTitleRow)
				{
					ArrayList<String> title_row_field_content_str_array_list = new ArrayList<String>();
					for (int i = 1; i <= rsmd.getColumnCount(); i++)
						title_row_field_content_str_array_list.add(rsmd.getColumnName(i));
					result.add(title_row_field_content_str_array_list);
					addedTitleRow = true;
				}

				// Add a data row
				ArrayList<String> data_row_field_content_str_array_list = new ArrayList<String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++)
					data_row_field_content_str_array_list.add(UtilBase.mapNull2Empty(sqlRowSet.getString(i)).trim());
				result.add(data_row_field_content_str_array_list);
			}
		return result;
	}

	/**
	 * Retrieve the data from the input database table, and return as an array list.
	 * 
	 * @param conn
	 *        The connection with the database
	 * @param db_table_name
	 *        The name of the database table to be truncated
	 * @param db_data_field_name_array
	 *        An array of the names of the data fields to be retreived
	 * @param condition_clause
	 *        The condition_clause of the SQL query
	 * @return An array list of the data retrieved from the input database table
	 * @throws SQLException
	 *         if a sql error occur
	 */
/*	public static ArrayList<ArrayList<String>> retrieveDbTable(Connection conn, String db_table_name, String[] db_data_field_name_array,
	        String condition_clause) throws SQLException
	{
		String sql_query = "SELECT ";
		if (db_data_field_name_array != null)
		{
			for (int i = 0; i < db_data_field_name_array.length; i++)
			{
				sql_query += db_data_field_name_array[i];

				if (i != db_data_field_name_array.length - 1)
				{
					// Not the last item
					sql_query += ", ";
				}
			}
		}
		else sql_query += "*";

		sql_query += " FROM " + db_table_name;
		if (condition_clause != null) sql_query += " " + condition_clause;

		return retrieveData(conn, sql_query);
	}*/

	/**
	 * Retrieve the data from the input database table, and return as an array list.
	 * 
	 * @param conn
	 *        The connection with the database
	 * @param db_table_name
	 *        The name of the database table to be truncated
	 * @return An array list of the data retrieved from the input database table
	 * @throws SQLException
	 *         if a sql error occur
	 */
	/*public static ArrayList<ArrayList<String>> retrieveDbTable(Connection conn, String db_table_name) throws SQLException
	{
		return retrieveDbTable(conn, db_table_name, null, null);
	}*/

	/**
	 * Get all content strings of the input database data field of the input database table, and return the content strings as an array list
	 * 
	 * @param conn
	 *        The connection with the database
	 * @param db_table_name
	 *        The name of the database table to be processed
	 * @param db_data_field
	 *        The name of the database data field to be processed
	 * @param condition_clause
	 *        The condition_clause of the SQL query
	 * @param select_distinct
	 *        The boolean that decides if select distinct or not
	 * @return An array list containing the database data field content strings got
	 * @throws SQLException
	 *         if a sql error occur
	 */
	// public static ArrayList<String> getDataFieldContentStrArrayList(Connection conn, String db_table_name, String db_data_field, String condition_clause,
	// boolean select_distinct) throws SQLException
	// {
	// ArrayList<String> array_list = new ArrayList<String>();
	//
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// ConditionClause parsed_condition_clause = null;
	//
	// String sql_query = "SELECT";
	// if (select_distinct) sql_query += " DISTINCT";
	// sql_query += " " + db_data_field + " FROM " + db_table_name;
	//
	// try
	// {
	// if (condition_clause != null)
	// {
	// parsed_condition_clause = ConditionClause.parse(condition_clause);
	// sql_query += " " + parsed_condition_clause.getParsedConditionClauseStr();
	//
	// ps = conn.prepareStatement(sql_query);
	// if (condition_clause != null)
	// {
	// ArrayList<String> parsed_str_array_list = parsed_condition_clause.getParsedStrArrayList();
	// for (int i = 0; i < parsed_str_array_list.size(); i++)
	// {
	// String parsed_str = (String) parsed_str_array_list.get(i);
	// ps.setString(i + 1, parsed_str);
	// }
	// }
	// rs = ps.executeQuery();
	//
	// // Convert result set to arraylist
	// while (rs.next())
	// {
	// String data_field_content_str = UtilBase.mapNull2Empty(rs.getString(db_data_field)).trim();
	//
	// array_list.add(data_field_content_str);
	// }
	// }
	// }
	// catch (SQLException sqlex)
	// {
	// throw sqlex;
	// }
	// finally
	// {
	// close(ps, rs);
	// }
	//
	// return array_list;
	// }

	/**
	 * Get all distinct content strings of the input database data field of the input database table, and return the content strings as an array list
	 * 
	 * @param conn
	 *        The connection with the database
	 * @param db_table_name
	 *        The name of the database table to be processed
	 * @param db_data_field
	 *        The name of the database data field to be processed
	 * @param condition_clause
	 *        The condition_clause of the SQL query
	 * @return An array list containing the database data field content strings got
	 * @throws SQLException
	 *         if a sql error occur
	 */
	// public static ArrayList<String> getDataFieldContentStrArrayList(Connection conn, String db_table_name, String db_data_field,
	// String condition_clause) throws SQLException
	// {
	// return getDataFieldContentStrArrayList(conn, db_table_name, db_data_field, condition_clause, false);
	// }

	/**
	 * Get all distinct content strings of the input database data field of the input database table, and return the content strings as an array list
	 * 
	 * @param conn
	 *        The connection with the database
	 * @param db_table_name
	 *        The name of the database table to be processed
	 * @param db_data_field
	 *        The name of the database data field to be processed
	 * @param condition_clause
	 *        The condition_clause of the SQL query
	 * @return An array list containing the database data field content strings got
	 * @throws SQLException
	 *         if a sql error occur
	 */
	// public static ArrayList<String> getDistinctDataFieldContentStrArrayList(Connection conn, String db_table_name, String db_data_field,
	// String condition_clause) throws SQLException
	// {
	// return getDataFieldContentStrArrayList(conn, db_table_name, db_data_field, condition_clause, true);
	// }

	/**
	 * Get all distinct content strings of the input database data field of the input database table, and return the content strings as an array list
	 * 
	 * @param conn
	 *        The connection with the database
	 * @param db_table_name
	 *        The name of the database table to be processed
	 * @param db_data_field
	 *        The name of the database data field to be processed
	 * @return An array list containing the database data field content strings got
	 * @throws SQLException
	 *         if a sql error occur
	 */
	// public static ArrayList<String> getDistinctDataFieldContentStrArrayList(Connection conn, String db_table_name, String db_data_field) throws SQLException
	// {
	// return getDistinctDataFieldContentStrArrayList(conn, db_table_name, db_data_field, null);
	// }

	/**
	 * Construct the sql query string for inserting the data from the input information and return it
	 * 
	 * @param db_table_name
	 *        The name of the database table to be inserted data
	 * @param db_data_field_name
	 *        An array of the names of the data fields to be inserted
	 * @param IsSysdateTobeUsed
	 *        A boolean that decides if inserting the SYSDATE or the content date of the date fields for the date fields
	 * @param db_date_field_name_array
	 *        An array of the names of the date fields
	 * @return The sql query string for inserting the data
	 */
	// public static String getInsertDataSQL(String db_table_name, String[] db_data_field_name_array, boolean IsSysdateTobeUsed, String[]
	// db_date_field_name_array)
	// {
	// String sql = "INSERT INTO " + db_table_name + " (";
	//
	// for (int i = 0; i < db_data_field_name_array.length; i++)
	// {
	// sql += db_data_field_name_array[i];
	//
	// if (i != db_data_field_name_array.length - 1)
	// {
	// // Not the last item
	// sql += ", ";
	// }
	// }
	//
	// sql += ") VALUES (";
	//
	// for (int i = 0; i < db_data_field_name_array.length; i++)
	// {
	// boolean isStringField = true;
	//
	// if (db_date_field_name_array != null)
	// {
	// for (int j = 0; j < db_date_field_name_array.length; j++)
	// {
	// if (db_data_field_name_array[i].equals(db_date_field_name_array[j]))
	// {
	// // The database data field is a date field
	// if (IsSysdateTobeUsed)
	// sql += "SYSDATE";
	// else sql += "TO_DATE(?, 'YYYY/MM/DD HH24:MI')";
	//
	// isStringField = false;
	// break;
	// }
	// }
	// }
	//
	// // The database data field is a string field
	// if (isStringField) sql += "?";
	//
	// // Not the last item
	// if (i != db_data_field_name_array.length - 1) sql += ", ";
	// }
	//
	// sql += ")";
	//
	// return sql;
	// }

	/**
	 * Construct the sql query string for inserting the data from the input information and return it
	 * 
	 * @param db_table_name
	 *        The name of the database table to be inserted data
	 * @param db_data_field_name
	 *        An array of the names of the data fields to be inserted
	 * @return The sql query string for inserting the data
	 */
	// public static String getInsertDataSQL(String db_table_name, String[] db_data_field_name_array)
	// {
	// return getInsertDataSQL(db_table_name, db_data_field_name_array, true, null);
	// }
}
