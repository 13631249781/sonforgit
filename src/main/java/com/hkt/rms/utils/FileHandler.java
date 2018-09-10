package com.hkt.rms.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.tomcat.util.http.fileupload.FileItem;

/**
 * This class is to provide some methods to handle file operations.
 * 
 * @author Kevin K.
 */
public class FileHandler
{
	/**
	 * Close the input input stream and output stream sequentially. Used to solve the problem that failing to clean up streams sequentially.
	 * 
	 * @param inputStream
	 *        The input stream to be closed
	 * @param outputStream
	 *        The output stream to be closed
	 * @throws IOException
	 *         if an I/O error occur
	 */
/*	private static void close(InputStream inputStream, OutputStream outputStream) throws IOException
	{
		try
		{
			if (inputStream != null) inputStream.close();
		}
		catch (IOException ioex)
		{
			throw ioex;
		}
		try
		{
			if (outputStream != null) outputStream.close();
		}
		catch (IOException ioex)
		{
			throw ioex;
		}
	}*/

	/**
	 * Create the input diectory if it does not exist
	 * 
	 * @param directory_string
	 *        An directory
	 */
	public static boolean createDirectoryIfNotExist(String directoryPath)
	{
		File directory = new File(directoryPath);
		if (!directory.exists()) return directory.mkdir();
		return false;
	}

	/**
	 * Copy the input source to the input destination
	 * 
	 * @param source
	 *        The source
	 * @param destination
	 *        The destination
	 * @throws IOException
	 *         if an I/O error occur
	 */
/*	public static void copy(String source, String destination) throws IOException
	{
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try
		{
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(destination);

			byte[] buf = new byte[1024];
			int len;

			// Transfer bytes from in to out
			while ((len = inputStream.read(buf)) > 0)
				outputStream.write(buf, 0, len);
		}
		catch (IOException ioex)
		{
			throw ioex;
		}
		finally
		{
			try
			{
				close(inputStream, outputStream);
			}
			catch (IOException ioex)
			{
				throw ioex;
			}
		}
	}*/

	/**
	 * Delete a file at the ftp server
	 * 
	 * @param file_name
	 *        The name of the file to be deleted
	 * @param ftp_ip
	 *        The ip of the ftp server
	 * @param ftp_port
	 *        The port number used to connect the ftp server
	 * @param ftp_login
	 *        The login used to connect the ftp server
	 * @param ftp_pwd
	 *        The password used to connect the ftp server
	 * @throws FtpException
	 *         if a ftp error occur
	 * @throws IOException
	 *         if an I/O error occur
	 */
/*	public static void deleteFileAtFTPServer(String file_name, String ftp_ip, String ftp_port, String ftp_login,
	        String ftp_pwd) throws FtpException, IOException
	{
		FtpBean ftp_bean = new FtpBean();
		ftp_bean.setPort(Integer.parseInt(ftp_port));

		try
		{
			ftp_bean.ftpConnect(ftp_ip, ftp_login, ftp_pwd);
			ftp_bean.fileDelete(file_name);
		}
		catch (FtpException ftpex)
		{
			throw ftpex;
		}
		catch (IOException ioex)
		{
			throw ioex;
		}
		finally
		{
			try
			{
				if (ftp_bean != null) ftp_bean.close();
			}
			catch (FtpException ftpex)
			{
				throw ftpex;
			}
			catch (IOException ioex)
			{
				throw ioex;
			}
		}
	}
*/
	/**
	 * Delete a file
	 * 
	 * @param file
	 *        The file to be deleted
	 */
/*	public static void deleteFile(File file)
	{
		if (file.exists())
		{
			if (!file.delete()) System.out.println("Fail to delete the file: " + file.toString());
		}
		else System.out.println("The file: " + file.toString() + " does not exist");
	}*/

	/**
	 * Backup the file from the byte array of the file included in the input map entry to the input backup directory
	 * 
	 * @param file_map_entry
	 *        A map entry of the file which key is the name of the file and value is the byte array of the file
	 * @param backup_directory
	 *        The backup directory
	 * @throws IOException
	 *         if an I/O error occur
	 */
/*	public static void backupFile(Map.Entry<String, byte[]> file_map_entry, String backup_directory) throws IOException
	{
		String file_name = (String) file_map_entry.getKey();
		byte[] file_bytes = (byte[]) file_map_entry.getValue();
		String backup_file_path = backup_directory + file_name;
		FileOutputStream fos = null;

		try
		{
			createDirectoryIfNotExist(backup_directory);
			fos = new FileOutputStream(backup_file_path);
			fos.write(file_bytes);
		}
		catch (IOException ioex)
		{
			System.out.println("Fail to backup the file to: " + backup_file_path);
			throw ioex;
		}
		finally
		{
			try
			{
				if (fos != null) fos.close();
			}
			catch (IOException ioex)
			{
				throw ioex;
			}
		}
	}*/

	/**
	 * Backup the input source file to the input backup directory
	 * 
	 * @param source_file
	 *        The source file
	 * @param backup_directory
	 *        The backup directory
	 * @throws IOException
	 *         if an I/O error occur
	 */
/*	public static void backupFile(File source_file, String backup_directory) throws IOException
	{
		String source_file_path = source_file.getCanonicalPath();
		String backup_file_path = backup_directory + source_file.getName();

		try
		{
			createDirectoryIfNotExist(backup_directory);
			copy(source_file_path, backup_file_path);
		}
		catch (IOException ioex)
		{
			System.out.println("Fail to backup the file to: " + backup_file_path);
			throw ioex;
		}
	}*/

	/**
	 * Write the input excel workbook to the input file
	 * 
	 * @param wb
	 *        The excel workbook to be written to the file
	 * @param file_name
	 *        The file name
	 * @param file_directory
	 *        The file directory
	 * @throws IOException
	 *         if an I/O error occur
	 */
/*	public static void writeExcelWorkbookToFile(HSSFWorkbook wb, String file_name, String file_directory) throws IOException
	{
		String file_path = file_directory + file_name;
		FileOutputStream fos = null;

		try
		{
			createDirectoryIfNotExist(file_directory);
			fos = new FileOutputStream(file_path);
			wb.write(fos);
		}
		catch (IOException ioex)
		{
			throw ioex;
		}
		finally
		{
			try
			{
				if (fos != null) fos.close();
			}
			catch (IOException ioex)
			{
				throw ioex;
			}
		}
	}*/

	/**
	 * Write the input excel workbook to the input file Note that the response will be committed after this function is executed
	 * 
	 * @param wb
	 *        The excel workbook to be written to the file
	 * @param file_name
	 *        The file name
	 * @param response
	 *        The HTTP servlet response
	 * @throws IOException
	 *         if an I/O error occur
	 */
	public static void writeExcelWorkbookToFile(HSSFWorkbook wb, String file_name, HttpServletResponse response) throws IOException
	{
		OutputStream os = null;

		try
		{
			os = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "inline; filename=" + file_name);
			wb.write(os);
			os.flush();
		}
		catch (IOException ioex)
		{
			throw ioex;
		}
		finally
		{
			try
			{
				if (os != null) os.close();
			}
			catch (IOException ioex)
			{
				throw ioex;
			}
		}
	}

	/*public static byte[] writeExcelWorkbookToByteArray(HSSFWorkbook wb) throws IOException
	{
		ByteArrayOutputStream baos = null;
		byte[] bytes = null;

		try
		{
			baos = new ByteArrayOutputStream();
			wb.write(baos);
			bytes = baos.toByteArray();
		}
		catch (IOException ioex)
		{
			throw ioex;
		}
		finally
		{
			if (baos != null) baos.close();
		}

		return bytes;
	}
*/
	/**
	 * Upload the input file to the input upload directory
	 * 
	 * @param file_item
	 *        The file item
	 * @param upload_directory
	 *        The directory to where the file is uploaded
	 * @throws Exception
	 *         if an error occur
	 */
	/*public static void uploadFile(FileItem file_item, String upload_directory) throws Exception
	{
		createDirectoryIfNotExist(upload_directory);

		// Some browsers pass the path + filename, and some pass the filename only to the file item
		String source_file_path = file_item.getName();

		String file_name = getFileName(source_file_path);
		String destination_file_path = upload_directory + file_name;

		file_item.write(new File(destination_file_path));
	}
*/
	/**
	 * Get the files of the input directory at the ftp server through ftp, with filenames that are containing the input specific prefix and extension, and
	 * return as a linked hash map which keys are the names of the files and values are the byte arrays of the files
	 * 
	 * @param source_directory
	 *        The source directory
	 * @param file_name_prefix
	 *        A specific file name prefix
	 * @param file_extension
	 *        A specific file extension
	 * @param ftp_ip
	 *        The ip of the ftp server
	 * @param ftp_port
	 *        The port number used to connect the ftp server
	 * @param ftp_login
	 *        The login used to connect the ftp server
	 * @param ftp_pwd
	 *        The password used to connect the ftp server
	 * @return A linked hash map of the files got
	 * @throws FtpException
	 *         if a ftp error occur
	 * @throws IOException
	 *         if an I/O error occur
	 */
	/*public static LinkedHashMap<String, byte[]> getFilesAtFTPServer(String source_directory, String file_name_prefix, String file_extension, String ftp_ip, String ftp_port,
	        String ftp_login, String ftp_pwd) throws FtpException, IOException
	{
		LinkedHashMap<String, byte[]> file_linked_hash_map = new LinkedHashMap<String, byte[]>();

		FtpBean ftp_bean = new FtpBean();
		ftp_bean.setPort(Integer.parseInt(ftp_port));

		try
		{
			ftp_bean.ftpConnect(ftp_ip, ftp_login, ftp_pwd);

			ftp_bean.setDirectory(source_directory);

			FtpListResult ftp_list_result = ftp_bean.getDirectoryContent();
			while (ftp_list_result.next())
			{
				String file_name = ftp_list_result.getName();
				if (file_name.startsWith(file_name_prefix) && file_name.endsWith(file_extension))
				{
					byte[] file_bytes = ftp_bean.getBinaryFile(file_name);
					file_linked_hash_map.put(file_name, file_bytes);
				}
			}
		}
		catch (FtpException ftpex)
		{
			throw ftpex;
		}
		catch (IOException ioex)
		{
			throw ioex;
		}
		finally
		{
			try
			{
				if (ftp_bean != null) ftp_bean.close();
			}
			catch (FtpException ftpex)
			{
				throw ftpex;
			}
			catch (IOException ioex)
			{
				throw ioex;
			}
		}

		return file_linked_hash_map;
	}

	*//**
	 * Get the files of the input directory, while filter the files with filenames that are not containing the input specific prefix and extension, and return
	 * the remaining files as an array of filenames
	 * 
	 * @param source_directory
	 *        The source directory
	 * @param file_name_prefix
	 *        A specific file name prefix
	 * @param file_extension
	 *        A specific file extension
	 * @return An array of filenames of the files finally got
	 * @throws IOException
	 *         if an I/O error occur
	 *//*
	public static File[] getFiles(String source_directory, String file_name_prefix, String file_extension)
	{
		FilenameFilter filename_filter = new FilenamePrefixSuffixFilter(file_name_prefix, file_extension);
		File file = new File(source_directory);
		File[] file_array = file.listFiles(filename_filter);
		return file_array;
	}
*/
	/**
	 * Get the file of the input directory, and return the file as an array with the filename of the file
	 * 
	 * @param source_directory
	 *        The source directory
	 * @param file_name
	 *        The filename of the file
	 * @return An array with the filename of the file finally got
	 * @throws IOException
	 *         if an I/O error occur
	 */
	/*public static File[] getFile(String source_directory, String file_name)
	{
		File file = new File(source_directory, file_name);
		// System.out.println("file.getCanonicalPath(): " + file.getCanonicalPath());

		File[] file_array = new File[]
		{ file };
		// System.out.println("file_array.length: " + file_array.length);

		return file_array;
	}*/

	/**
	 * Read the first line content of the input file and return as a string
	 * 
	 * @param file_name
	 *        The name of a file
	 * @return The first line content string
	 * @throws IOException
	 *         if an I/O error occur
	 */
	/*public static String getFileFirstLineContent(String file_name) throws IOException
	{
		String first_line_content = null;
		BufferedReader br = null;

		try
		{
			FileReader fr = new FileReader(file_name);
			br = new BufferedReader(fr);
			first_line_content = br.readLine();
		}
		catch (IOException ioex)
		{
			throw ioex;
		}
		finally
		{
			try
			{
				if (br != null) br.close();
			}
			catch (IOException ioex)
			{
				throw ioex;
			}
		}

		return first_line_content;
	}*/

	/**
	 * Get the excel workbook from the input byte array of the excel file and return it
	 * 
	 * @param excel_file_bytes
	 *        The byte array of an excel file
	 * @return The excel workbook of the excel file
	 * @throws IOException
	 *         if an I/O error occur
	 */
	/*public static HSSFWorkbook getExcelWorkbook(byte[] excel_file_bytes) throws IOException
	{
		HSSFWorkbook wb = null;
		ByteArrayInputStream bais = null;

		try
		{
			bais = new ByteArrayInputStream(excel_file_bytes);
			POIFSFileSystem poifsfs = new POIFSFileSystem(bais);
			wb = new HSSFWorkbook(poifsfs);
		}
		catch (IOException ioex)
		{
			throw ioex;
		}
		finally
		{
			try
			{
				if (bais != null) bais.close();
			}
			catch (IOException ioex)
			{
				throw ioex;
			}
		}

		return wb;
	}*/

	/**
	 * Get the excel workbook from the input excel file and return it
	 * 
	 * @param excel_file_path
	 *        The path of an excel file
	 * @return The excel workbook of the excel file
	 * @throws IOException
	 *         if an I/O error occur
	 */
	/*public static HSSFWorkbook getExcelWorkbook(String excel_file_path) throws IOException
	{
		HSSFWorkbook wb = null;
		FileInputStream fis = null;

		try
		{
			fis = new FileInputStream(excel_file_path);
			POIFSFileSystem poifsfs = new POIFSFileSystem(fis);
			wb = new HSSFWorkbook(poifsfs);
		}
		catch (IOException ioex)
		{
			throw ioex;
		}
		finally
		{
			try
			{
				if (fis != null) fis.close();
			}
			catch (IOException ioex)
			{
				throw ioex;
			}
		}

		return wb;
	}*/

	/**
	 * Extract the file name from the input file path, and return as a string
	 * 
	 * @param file_path
	 *        The file path
	 * @return The file name extracted
	 */
	/*public static String getFileName(String file_path)
	{
		String file_name = file_path;

		// For windows platform
		file_name = file_name.substring(file_name.lastIndexOf("\\") + 1);
		// For unix-like platform
		file_name = file_name.substring(file_name.lastIndexOf("/") + 1);

		return file_name;
	}*/
}