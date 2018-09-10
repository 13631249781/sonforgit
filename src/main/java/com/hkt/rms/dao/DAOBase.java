package com.hkt.rms.dao;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hkt.rms.utils.UtilBase;


public abstract class DAOBase
{
	protected static String getString(ResultSet rs, int index) throws Exception
	{
		return UtilBase.mapNull2Empty(rs.getString(index));
	}

	protected static Timestamp getTimestamp(Date date) throws Exception
	{
		return date == null ? null : new Timestamp(date.getTime());
	}



	/*protected static void exportCsv(OutputStream out, Connection conn, String sql) throws Exception
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		DataOutputStream dos = null;

		try
		{
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();

			dos = new DataOutputStream(out);

			for (int i = 1; i <= columnsNumber; i++)
			{
				dos.writeBytes(UtilBase.mapNull2Empty(rsmd.getColumnName(i)));
				dos.writeBytes(i < columnsNumber ? "," : "\n");
			}

			while (rs.next())
			{
				for (int i = 1; i <= columnsNumber; i++)
				{
					dos.writeBytes(UtilBase.mapNull2Empty(rs.getString(i)));
					dos.writeBytes(i < columnsNumber ? "," : "\n");
				}
			}
			dos.flush();
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			close(stmt, rs);
			try
			{
				if (dos != null) dos.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				if (out != null) out.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}*/
}