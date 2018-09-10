package com.hkt.rms.utils;

import java.util.StringTokenizer;

/**
 * This class is used to parse the information of a returned String from the 'LIST' command.<br> It parse the following information from the list:<br> 1)
 * Name<br> 2) Permission<br> 3) Type<br> 4) Owner<br> 5) Group<br> 6) Size<p> Sample code for using this class:<br><b> while(ftplist.next())<br> {<br>
 * System.out.println(ftplist.getName());<br> }</b><br> <br> Normally, we can see two formats returned by the ftp LIST command.<br> One is the standard UNIX
 * format, and another one is the MS-DOS format (rarely see).<br> This class can parse both formats. Unfortunely, the following information are missed in<br>
 * the MS-DOS format:<br> 1) Owner of file<br> note: getOwner() method returns "" all the time.<br> 2) Group whihc own the file<br> note: getGroup() method
 * returns "" all the time.<br> 3) File permission<br> note: getPermission() method returns "" all the time.<br> Those methods like getOwnerReadable() returns
 * true all the time.
 */

public class FtpListResult
{
	// For determine the file type for each row
	public final static int DIRECTORY = 1, FILE = 2, LINK = 3, OTHERS = 4;

	// Contain file type for each row
	private int[] type;

	// Contain file size for each row
	private long[] size;

	// Contain permission, owner, group and file name for each row
	private String[] permission, owner, group, name;

	// Current row number
	private int index = -1;

	// True to turn on DEBUG mode
	private final static boolean DEBUG = false;

	/**
	 * Get the name of the group which own the file, directory or link.
	 */
	public String getGroup()
	{
		return group[index];
	}

	/**
	 * Get name.
	 */
	public String getName()
	{
		return name[index];
	}

	/**
	 * Get the user which own the file, directory or link.
	 */
	public String getOwner()
	{
		return owner[index];
	}

	/**
	 * Get permission.
	 */
	public String getPermission()
	{
		return permission[index];
	}

	/**
	 * Get size.
	 */
	public long getSize()
	{
		return size[index];
	}

	/**
	 * Get the type.
	 * 
	 * @return the int DIRECTORY,FILE or LINK.
	 */
	public int getType()
	{
		return type[index];
	}

	/**
	 * Whether it is readable by owner.
	 * 
	 * @return True if readable by owner.
	 */
	public boolean ownerReadable()
	{
		if (permission[index].equals("")) return true;
		if (permission[index].charAt(0) == '-') return false;
		return true;
	}

	/**
	 * Whether it is writable by owner.
	 * 
	 * @return True if writable by owner.
	 */
	public boolean ownerWritable()
	{
		if (permission[index].equals("")) return true;
		if (permission[index].charAt(1) == '-') return false;
		return true;
	}

	/**
	 * Whether it is executable by owner.
	 * 
	 * @return True if executable by owner.
	 */
	public boolean ownerExecutable()
	{
		if (permission[index].equals("")) return true;
		if (permission[index].charAt(2) == '-') return false;
		return true;
	}

	/**
	 * Whether it is readable by group.
	 * 
	 * @return True if readable by group.
	 */
	public boolean groupReadable()
	{
		if (permission[index].equals("")) return true;
		if (permission[index].charAt(3) == '-') return false;
		return true;
	}

	/**
	 * Whether it is writable by group.
	 * 
	 * @return True if writable by group.
	 */
	public boolean groupWritable()
	{
		if (permission[index].equals("")) return true;
		if (permission[index].charAt(4) == '-') return false;
		return true;
	}

	/**
	 * Whether it is executable by group.
	 * 
	 * @return True if executable by group.
	 */
	public boolean groupExecutable()
	{
		if (permission[index].equals("")) return true;
		if (permission[index].charAt(5) == '-') return false;
		return true;
	}

	/**
	 * Whether it is global readable.
	 * 
	 * @return True if global readable.
	 */
	public boolean globalReadable()
	{
		if (permission[index].equals("")) return true;
		if (permission[index].charAt(6) == '-') return false;
		return true;
	}

	/**
	 * Whether it is global writable.
	 * 
	 * @return True if global writable.
	 */
	public boolean globalWritable()
	{
		if (permission[index].equals("")) return true;
		if (permission[index].charAt(7) == '-') return false;
		return true;
	}

	/**
	 * Whether it is global executable.
	 * 
	 * @return True if global executable.
	 */
	public boolean globalExecutable()
	{
		if (permission[index].equals("")) return true;
		if (permission[index].charAt(8) == '-') return false;
		return true;
	}

	/**
	 * A FtpListResult is initially positioned before its first row. the first call to next makes the first row the current row; the second call makes the
	 * second row the current row, etc
	 * 
	 * @return true if the new current row is valid; false if there are no more rows.
	 */
	public boolean next()
	{
		if (name == null || index >= name.length - 1) return false;
		index++;
		return true;
	}

	/**
	 * Parse the information from the string list
	 */
	protected void parseList(String strlist, String system_type)
	{

		if (DEBUG) // Debug message
		{
			System.out.println("FtpListResult: Remote system type is " + system_type);
			System.out.println("FtpListResult: Parsing list ....... ");
		}

		if (strlist.length() <= 0)
		{
			if (DEBUG) // Debug message
			    System.out.println("FtpListResult: Length of String == 0, return.");
			return;
		}

		// There are two main type of list formats.
		// One is the UNIX format as the most popular one.
		// Another is the MS-DOS format which is rarely see.
		// Here try to figure out the string is which format.
		if (system_type.toLowerCase().indexOf("windows") == -1)
		{
			// Remote system is UNIX
			// In some UNIX list format, the first line is something like
			// 'total 10'
			// In this case, skip the first line as it is useless for most.
			char first_char = strlist.charAt(0);
			if (first_char != 'd' && first_char != '-' && first_char != 'l')
			{
				// Parse for UNIX format with skipping the first line.
				parseUnixList(strlist, 1);
			}
			else
			{
				// Parse for UNIX format with skip any line.
				parseUnixList(strlist, 0);
			}
		}
		else
		{
			// Remote system is windows type.
			// For the UNIX like format, its first word should be
			// 'd', '-' or 'l'. Otherwise, it is supposed to be
			// in MS-DOS format.
			// Note that for the UNIX format of windows, it never
			// have the line like 'total 10' (as i observed).
			// So it don't need to skip line if it is UNIX format
			// on a windows machine.
			char first_char = strlist.charAt(0);
			if (first_char != 'd' && first_char != '-' && first_char != 'l')
			{
				// parse for MS-DOS format.
				parseDosList(strlist);
			}
			else
			{
				// Parse for UNIX format without skip any line.
				parseUnixList(strlist, 0);
			}
		}
	}

	/*
	 * Parse a UNIX format list. With skipping number of lines. The columns of a typical UNIX format list is like: drwxr-xr-x 6 appli appli 1024 May 17 20:33
	 * java -rw------- 1 appli appli 1005 May 1 21:21 mp3list drwxr-xr-x 5 appli appli 1024 May 17 16:56 perl drwxr-xr-x 6 appli appli 1024 Apr 30 23:18
	 * public_html @strlist The list to be parsed. @skip The number of lines to be skipped.
	 */
	private void parseUnixList(String strlist, int skip)
	{
		int num_lines = countLine(strlist) - skip;

		if (DEBUG) // Debug message
		    System.out.println("FtpListResult: Parse UNIX format list ...... skip " + skip + " line");
		type = new int[num_lines];
		permission = new String[num_lines];
		owner = new String[num_lines];
		group = new String[num_lines];
		size = new long[num_lines];
		name = new String[num_lines];
		String temp, line;

		int start = 0, end = 0;
		if (skip == 1) start = strlist.indexOf('\n') + 1;

		for (int i = 0; i < num_lines; i++)
		{
			end = strlist.substring(start).indexOf('\n');
			line = strlist.substring(start, start + end);
			start += end + 1;
			int skip_cols = 3;

			StringTokenizer st = new StringTokenizer(line, " ");
			temp = st.nextToken();
			type[i] = parseType(temp);
			permission[i] = parsePermission(temp);
			// skip coloumn
			st.nextToken();
			owner[i] = st.nextToken();
			group[i] = st.nextToken();

			// Some UNIX format list doesn't contain the column for group like this:
			// -rw-r--r-- 1 daemon 0 Dec 7 10:15 .notar
			// -r--r--r-- 1 daemon 828 Mar 30 1995 HOW_TO_SUBMIT
			// -r--r--r-- 1 daemon 627 Mar 30 1995 README
			// -r--r--r-- 1 daemon 876 Mar 30 1995 WELCOME
			// drwxr-xr-x 2 ftp 512 Oct 13 1999 bin
			// It is easy to see that the column for group is missed.
			// So a NumberFormatException is threw as it try to convert words like
			// 'Dec', 'Mar' to long. And the column for size is assigned to the
			// group[i] array. So in here, take the content inside the group[i]
			// element in to the size[i] array (converted to long first). Then
			// assign a "" String to the group[i] element, as the list doesn't
			// provide the group information.
			try
			{
				size[i] = Long.parseLong(st.nextToken());
			}
			catch (NumberFormatException e)
			{
				size[i] = Long.parseLong(group[i]);
				group[i] = "";
				skip_cols = 2;
			}

			// skip 2 or 3 columns which i considered to be useless.
			for (int j = 0; j < skip_cols; j++)
				st.nextToken();
			name[i] = st.nextToken();
			while (st.hasMoreTokens())
				name[i] = name[i].concat(" ").concat(st.nextToken());
		}

		if (DEBUG) // Debug message
		    System.out.println("FtpListResult: Parse list finished.");
	}

	/*
	 * Parse a MS-DOS format list output.
	 */
	private void parseDosList(String strlist)
	{
		if (DEBUG) // Debug message
		    System.out.println("FtpListResult: Parsing MS-DOS list ..... ");

		int num_lines = countLine(strlist);

		type = new int[num_lines];
		permission = new String[num_lines];
		owner = new String[num_lines];
		group = new String[num_lines];
		size = new long[num_lines];
		name = new String[num_lines];

		String temp, line;
		int start = 0, end = 0;

		for (int i = 0; i < num_lines; i++)
		{
			end = strlist.substring(start).indexOf('\n');
			line = strlist.substring(start, start + end);
			start += end + 1;

			// These information are not availible for MS-DOS format list
			permission[i] = "";
			owner[i] = "";
			group[i] = "";

			StringTokenizer st = new StringTokenizer(line, " ");

			// skip first two cols
			for (int j = 0; j < 2; j++)
				st.nextToken();

			// Start to parse info
			temp = st.nextToken();
			if (temp.equals("<DIR>"))
			{
				type[i] = DIRECTORY;
				size[i] = 0;
			}
			else
			{
				type[i] = FILE;
				size[i] = Long.parseLong(temp);
			}

			// Get name
			name[i] = st.nextToken();
			while (st.hasMoreTokens())
				name[i] = name[i].concat(" ").concat(st.nextToken());
		}

		if (DEBUG) // Debug message
		    System.out.println("FtpListResult: Parse finished.");
	}

	/*
	 * Determine the file type from a String and return as an int
	 */
	private int parseType(String str)
	{
		char c = str.charAt(0);
		int type = 0;
		if (c == 'd')
			type = DIRECTORY;
		else if (c == '-')
			type = FILE;
		else if (c == 'l')
			type = LINK;
		else type = OTHERS;
		return type;
	}

	/*
	 * Determine the permission
	 */
	private String parsePermission(String str)
	{
		return str.substring(1, str.length());
	}

	/*
	 * Count the number of lines in a String
	 */
	private int countLine(String str)
	{
		int index = 0, temp, num_of_lines = 0;
		while ((temp = str.substring(index).indexOf("\n")) != -1)
		{
			num_of_lines++;
			index += temp + 1;
		}
		if (str.substring(index).length() > 0) num_of_lines++;

		if (DEBUG) // Debug message
		    System.out.println("FtpListResult: " + num_of_lines + " number of lines counted.");

		return num_of_lines;
	}
}
