package com.hkt.rms.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.hkt.rms.configuration.Constants;

public class UtilBase
{
	private UtilBase()
	{}

	public static String getCurrTime()
	{
		return getTodayDate("HH:mm");
	}

	public static String getTodayDateTime()
	{
		return getTodayDate(Constants.SIMPLEDATETIME_FORMAT);
	}

	public static String getTodayDate(String dateFormat)
	{
		return dateFormat(new Date(), dateFormat);
	}

	public static String dateFormat(Timestamp time, String dateFormat)
	{
		return time != null ? dateFormat(time.getTime(), dateFormat) : "";
	}

	public static String dateFormat(long time, String dateFormat)
	{
		return dateFormat(new Date(time), dateFormat);
	}

	public static String dateFormat(Date date, String dateFormat)
	{
		return new SimpleDateFormat(dateFormat).format(date);
	}

	public static Date getDate(String dateString, String dateformat)
	{
		try
		{
			return new SimpleDateFormat(dateformat).parse(dateString);
		}
		catch (Exception e)
		{}

		return null;
	}

	public static String mapNull2Empty(String value)
	{
		return value == null ? "" : value;
	}

	public static String mapNull2Default(String value, String defaultValue)
	{
		return isEmptyValue(value) ? defaultValue : value;
	}

	public static boolean isEmptyValue(String value)
	{
		return value == null || value.trim().length() == 0;
	}

	public static boolean isNotEmptyValue(String value)
	{
		return value != null && !"".equals(value.trim());
	}

	public static String removeNonNumeric(String input)
	{
		return input.replaceAll("[^0-9]", "");
	}

	public static int toInt(String val)
	{
		return toInt(val, 0);
	}

	public static int toInt(String val, int defaultInt)
	{
		try
		{
			return Integer.parseInt(val.trim());
		}
		catch (Exception e)
		{}
		return defaultInt;
	}

	public static long toLong(String string)
	{
		try
		{
			return Long.parseLong(string.trim());
		}
		catch (Exception ex)
		{}
		return 0;
	}

	public static long toLong(String string, long defaultLong)
	{
		try
		{
			return Long.parseLong(string.trim());
		}
		catch (Exception ex)
		{}
		return defaultLong;
	}

	public static String padLeft(String str, String pad, int total_Length)
	{
		StringBuffer padString = new StringBuffer();

		if (str == null) str = "";

		if (str.length() < total_Length)
		{
			int padLength = total_Length - str.length();

			for (int i = 0; i < padLength; i++)
				padString.append(pad);

			str = padString.append(str).toString();
		}

		return str;
	}

	public static String padRight(String str, String pad, int total_Length)
	{
		if (str == null) str = "";

		StringBuffer padString = new StringBuffer(str);

		while (padString.length() < total_Length)
			padString.append(pad);

		return padString.toString();
	}

	public static String appendWhereCause(String whereCause, String expression, int value)
	{
		return appendWhereCause(whereCause, expression, value > 0);
	}

	public static String appendWhereCause(String whereCause, String expression, long value)
	{
		return appendWhereCause(whereCause, expression, value > 0);
	}

	public static String appendWhereCause(String whereCause, String expression, Date value)
	{
		return appendWhereCause(whereCause, expression, value != null);
	}

	public static String appendWhereCause(String whereCause, String expression, String value)
	{
		return appendWhereCause(whereCause, expression, !isEmptyValue(value));
	}

	public static String appendWhereCause(String whereCause, String expression, boolean isAppend)
	{
		if (isAppend)
		{
			if (whereCause.length() > 0) whereCause += " AND ";
			whereCause += expression;
		}

		return whereCause;
	}

	public static String appendWhereCauseWithOr(String whereCause, String expression, String value)
	{
		if (isNotEmptyValue(value))
		{
			if (whereCause.length() > 0) whereCause += " OR ";
			whereCause += expression;
		}

		return whereCause;
	}

	public static String appendStringWithDelimiter(String string, String value, String delimiter, String format)
	{
		if (isEmptyValue(value)) return string;

		if (isNotEmptyValue(string)) string += delimiter;
		return string += String.format(format, value);
	}

	public static String getHostName()
	{
		try
		{
			return InetAddress.getLocalHost().getHostName();
		}
		catch (UnknownHostException e)
		{}

		return "";
	}

	public static int convertNetmaskToCIDR(String subnetmask)
	{
		int cidr = 0;

		try
		{
			InetAddress netmask = InetAddress.getByName(subnetmask);
			byte[] netmaskBytes = netmask.getAddress();
			boolean zero = false;
			for (byte b : netmaskBytes)
			{
				int mask = 0x80;

				for (int i = 0; i < 8; i++)
				{
					int result = b & mask;
					if (result == 0)
						zero = true;
					else if (zero)
						throw new IllegalArgumentException("Invalid netmask.");
					else cidr++;
					mask >>>= 1;
				}
			}
		}
		catch (UnknownHostException e)
		{}
		return cidr;
	}

	public static String join(String[] fields, String fieldSuffix, char delimiter)
	{
		return join(fields, "", fieldSuffix, delimiter);
	}

	public static String join(String[] fields, String fieldPrefix, String fieldSuffix, char delimiter)
	{
		if (fields == null) return "";

		int len = fields.length;

		if (len > 1)
			len -= 1;
		else if (len == 1)
			return fieldPrefix + fields[0] + fieldSuffix;
		else return "";

		StringBuilder sb = new StringBuilder();
		int i = 0;

		for (i = 0; i < len; i++)
			sb.append(fieldPrefix).append(fields[i]).append(fieldSuffix).append(delimiter);

		sb.append(fieldPrefix).append(fields[i]).append(fieldSuffix);

		return sb.toString();
	}

	public static String delimitedString(String string, char delimiter, int count)
	{
		if (count > 1)
			count -= 1;
		else return "";

		StringBuilder sb = new StringBuilder();
		int i = 0;

		for (i = 0; i < count; i++)
			sb.append(string).append(delimiter);

		sb.append(string);

		return sb.toString();
	}

	public static byte[] zipBytes(String folderName, Map<String, byte[]> map) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);

		if (!UtilBase.isEmptyValue(folderName)) folderName = folderName + "/";

		for (Entry<String, byte[]> entry : map.entrySet())
		{
			ZipEntry zipEntry = new ZipEntry(folderName + entry.getKey());
			zipEntry.setSize(entry.getValue().length);
			zos.putNextEntry(zipEntry);
			zos.write(entry.getValue());
			zos.closeEntry();
		}
		zos.close();
		return baos.toByteArray();
	}

	public static boolean checkCJK(String str)
	{
		for (int i = 0; i < str.length(); i++)
			if (isCJK(str.charAt(i))) return true;
		return false;
	}

	public static String replaceCJK(String str)
	{
		return replaceCJK(str, ' ');
	}

	public static String replaceCJK(String str, Character ch)
	{
		if (str == null) return "";

		String result = "";
		for (int i = 0; i < str.length(); i++)
		{
			char tempCh = str.charAt(i);
			result += isCJK(tempCh) ? ch : tempCh;
		}
		return result;
	}

	private static boolean isCJK(Character ch)
	{
		Character.UnicodeBlock block = Character.UnicodeBlock.of(ch);

		return Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(block) || Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS.equals(block)
		        || Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A.equals(block);
	}
}