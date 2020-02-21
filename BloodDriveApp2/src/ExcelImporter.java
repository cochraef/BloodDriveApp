//package net.codejava;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

/**
 * Sample Java program that imports data from an Excel file to MySQL database.
 *
 * @author Nam Ha Minh - https://www.codejava.net
 * 
 * Modified by Michael Kuznicki
 *
 */
public class ExcelImporter {
	

	public static void main(String[] args) {
		String jdbcURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";
		String username = "SodaBaseUsercochraef20";
		String password = "Password123";

		String excelFilePath = "TestSpreadsheet.xlsx";

		int batchSize = 1;

		Connection connection = null;
		
		String serverName = "golem.csse.rose-hulman.edu";
		String databaseName = "BloodDriveDatabase";

		
		String connectionUrl = jdbcURL.replaceFirst("\\$\\{[a-zA-Z]*\\}", serverName);
		connectionUrl = connectionUrl.replaceFirst("\\$\\{[a-zA-Z]*\\}", databaseName);
		connectionUrl = connectionUrl.replaceFirst("\\$\\{[a-zA-Z]*\\}", username);
		connectionUrl = connectionUrl.replaceFirst("\\$\\{[a-zA-Z]*\\}", password);

		try {
			long start = System.currentTimeMillis();

			FileInputStream inputStream = new FileInputStream(excelFilePath);

			Workbook workbook = new XSSFWorkbook(inputStream);

			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = firstSheet.iterator();

			connection = DriverManager.getConnection(connectionUrl, username, password);
			connection.setAutoCommit(false);

			String sql = "INSERT INTO Person (Username, FirstName, LastName, Birthdate, PerAddress, PhoneNumber, Perpasswordsalt, Perpasswordhash, isManagerAccount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);

			rowIterator.next(); // skip the header row

			for(int i = 0; i <= 15; i++) {
				Row nextRow = rowIterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				for(int j = 0; j <= 8; j++) {
					Cell nextCell = cellIterator.next();

					int columnIndex = nextCell.getColumnIndex();

					
					if(columnIndex == 0) {
						String perUsername = nextCell.getStringCellValue();
						statement.setString(1, perUsername);
					}
					if(columnIndex == 1) {
						String fName = nextCell.getStringCellValue();
						statement.setString(2, fName);
					}
					if(columnIndex ==2) {
						String lName = nextCell.getStringCellValue();
						statement.setString(3, lName);
					}
					if(columnIndex == 3) {
						java.util.Date birthdate = DateUtil.getJavaDate(nextCell.getNumericCellValue());
						java.sql.Date date = new java.sql.Date(birthdate.getTime());
						statement.setDate(4, date);
					}
					if(columnIndex == 4) {
						String address = nextCell.getStringCellValue();
						statement.setString(5, address);
					}
					if(columnIndex == 5) {
						String phone = nextCell.getStringCellValue();
						statement.setString(6, phone);
					}
					if(columnIndex == 6) {
						String salt = nextCell.getStringCellValue();
						statement.setString(7, salt);
					}
					if(columnIndex == 7) {
						String hash = nextCell.getStringCellValue();
						statement.setString(8, hash);
					}
					if(columnIndex == 8) {
						if(nextCell.getStringCellValue().compareTo("0")==0) {
							statement.setBoolean(9, false);
						} else if(nextCell.getStringCellValue().compareTo("1")==0) {
							statement.setBoolean(9, true);
						}
					}

				}
				
				statement.addBatch();
				System.out.println(statement);
				statement.executeBatch();
				
			}
			
			connection.commit();
			
			Sheet secondSheet = workbook.getSheetAt(1);
			Iterator<Row> rowIterator2 = secondSheet.iterator();

			String locationSql = "SET IDENTITY_INSERT Location ON INSERT INTO Location (ID, StreetLine1, StreetLine2, City, State, ZipCode, Capacity, ContactName, ContactNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) SET IDENTITY_INSERT Location OFF";
			PreparedStatement locationStatement = connection.prepareStatement(locationSql);
			
			rowIterator2.next(); // skip the header row

			
			for(int i = 0; i <= 4; i++) {
				Row nextRow = rowIterator2.next();
				Iterator<Cell> cellIterator2 = nextRow.cellIterator();

				for(int j = 0; j <= 8; j++) {
					Cell nextCell = cellIterator2.next();

					int columnIndex = nextCell.getColumnIndex();

					
					if(columnIndex == 0) {
						int id = (int) nextCell.getNumericCellValue();
						locationStatement.setInt(1, id);
					}
					if(columnIndex == 1) {
						System.out.println("sl1");
						String streetLine1 = nextCell.getStringCellValue();
						locationStatement.setString(2, streetLine1);
					}
					if(columnIndex == 2) {
						System.out.println("sl2");
						String streetLine2 = nextCell.getStringCellValue();
						locationStatement.setString(3, streetLine2);
					}
					if(columnIndex == 3) {
						System.out.println("ci");
						String city = nextCell.getStringCellValue();
						locationStatement.setString(4, city);
					}
					if(columnIndex == 4) {
						System.out.println("st");
						String state = nextCell.getStringCellValue();
						locationStatement.setString(5, state);
					}
					if(columnIndex == 5) {
						System.out.println("zc");
						int zipcode = (int) nextCell.getNumericCellValue();
						locationStatement.setInt(6, zipcode);
					}
					if(columnIndex == 6) {
						System.out.println("ca");
						int capacity = (int) nextCell.getNumericCellValue();
						locationStatement.setInt(7, capacity);
					}
					if(columnIndex == 7) {
						System.out.println("cna");
						String contactName = nextCell.getStringCellValue();
						locationStatement.setString(8, contactName);
					}
					if(columnIndex == 8) {
						System.out.println("cnu");
						String contactNumber = nextCell.getStringCellValue();
						locationStatement.setString(9, contactNumber);
					}

				}
				
				locationStatement.addBatch();
				System.out.println(locationStatement);
				locationStatement.executeBatch();
				
			}
			
			connection.commit();
			
			Sheet thirdSheet = workbook.getSheetAt(2);
			Iterator<Row> rowIterator3 = thirdSheet.iterator();

			String driveSql = "INSERT INTO BloodDriveEvent (EventDate, LocationID) VALUES (?, ?)";
			PreparedStatement driveStatement = connection.prepareStatement(driveSql);
			
			rowIterator3.next(); // skip the header row

			
			for(int i = 0; i <= 2; i++) {
				Row nextRow = rowIterator3.next();
				Iterator<Cell> cellIterator3 = nextRow.cellIterator();

				for(int j = 0; j <= 1; j++) {
					Cell nextCell = cellIterator3.next();

					int columnIndex = nextCell.getColumnIndex();

					
					if(columnIndex == 0) {
						System.out.println("date");
						java.util.Date drivedate = DateUtil.getJavaDate(nextCell.getNumericCellValue());
						java.sql.Date date = new java.sql.Date(drivedate.getTime());
						driveStatement.setDate(1, date);
					}
					if(columnIndex == 1) {
						System.out.println("id");
						int locId = (int) nextCell.getNumericCellValue();
						driveStatement.setInt(2, locId);
					}

				}
				
				driveStatement.addBatch();
				System.out.println(driveStatement);
				driveStatement.executeBatch();
				
			}
			
			workbook.close();

			// execute the remaining queries
			statement.executeBatch();

			connection.commit();
			connection.close();

			long end = System.currentTimeMillis();
			System.out.printf("Import done in %d ms\n", (end - start));

		} catch (IOException ex1) {
			System.out.println("Error reading file");
			ex1.printStackTrace();
		} catch (SQLException ex2) {
			System.out.println("Database error");
			ex2.printStackTrace();
		}
	}
}