package com.example.ExhibitMonitorApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Worker implements Runnable {

	private static final int CHECKPOINT_INTERVAL = 100;

	private final File file;

	public Worker(File file) {
		this.file = file;
	}

	@Override
	public void run() {

		System.out.println("[WORKER] Started Processing : " + file.getName());

		boolean processingSuccessful = false;

		try (BufferedReader br = new BufferedReader(new FileReader(file));

				Connection conn = DBUtil.getConnection()) {

			conn.setAutoCommit(false);

			int lineNumber = 0;

			int lastProcessedLine = getLastProcessedLine(conn, file.getName());

			String logicalFileName = ExhibitUtil.getLogicalFileName(file.getName());

			/*
			 * HEADER VALIDATION
			 */
			String headerLine = br.readLine();

			lineNumber++;

			if (headerLine == null) {

				System.out.println("[ERROR] Empty File : " + file.getName());

				return;
			}

			String[] actualHeaders = headerLine.split(",");

			List<String> expectedHeaders = ExhibitUtil.schemaHeaders.get(logicalFileName);

			if (expectedHeaders == null) {

				System.out.println("[ERROR] Missing Schema : " + logicalFileName);

				return;
			}

			boolean headerMatched = java.util.Arrays.stream(actualHeaders).map(String::trim)
					.collect(java.util.stream.Collectors.toSet())
					.equals(expectedHeaders.stream().map(String::trim).collect(java.util.stream.Collectors.toSet()));

			if (!headerMatched) {

				System.out.println("[ERROR] Header Validation Failed : " + file.getName());

				return;
			}

			/*
			 * CHECKPOINT RECOVERY
			 */
			String line;

			while (lineNumber <= lastProcessedLine && (line = br.readLine()) != null) {

				lineNumber++;
			}

			/*
			 * RECORD PROCESSING
			 */
			while ((line = br.readLine()) != null) {

				lineNumber++;

				String[] parts = line.split(",");

				boolean valid = validateRecord(parts, actualHeaders, logicalFileName);

				if (valid) {

					insertValid(conn, parts, actualHeaders);

				} else {

					insertInvalid(conn, line, "Validation Failed");
				}

				/*
				 * Save checkpoint periodically
				 */
				if (lineNumber % CHECKPOINT_INTERVAL == 0) {

					updateCheckpoint(conn, file.getName(), lineNumber);

					conn.commit();
				}
			}

			/*
			 * Final checkpoint
			 */
			updateCheckpoint(conn, file.getName(), lineNumber);

			conn.commit();

			processingSuccessful = true;

			System.out.println("[WORKER] Processing Complete : " + file.getName());

		} catch (Exception e) {

			System.out.println("[WORKER ERROR] " + file.getName());

			e.printStackTrace();
		}

		/*
		 * IMPORTANT: File move happens OUTSIDE try-with-resources. Reader and DB
		 * connection are already closed.
		 */
		if (processingSuccessful) {

			boolean moved = moveToProcessedFolder();

			if (moved) {

				System.out.println("[WORKER] Completed : " + file.getName());

			} else {

				System.out.println("[WORKER] File Move Failed : " + file.getName());
			}
		}
	}

	private boolean validateRecord(String[] parts, String[] headers, String logicalFileName) {

		Map<String, Map<String, Object>> configMap = ExhibitUtil.schemaColumns.get(logicalFileName);

		if (configMap == null) {
			return false;
		}

		if (parts.length != headers.length) {
			return false;
		}

		for (int i = 0; i < headers.length; i++) {

			String header = headers[i].trim();

			String value = parts[i].trim();

			Map<String, Object> config = configMap.get(header);

			if (config == null) {
				return false;
			}

			boolean required = (Boolean) config.get("required");

			if (required && value.isBlank()) {

				return false;
			}

			String type = (String) config.get("type");

			if ("number".equalsIgnoreCase(type) && !isNumber(value)) {

				return false;
			}

			if ("date".equalsIgnoreCase(type)) {

				String format = (String) config.get("format");

				if (!isValidDate(value, format)) {

					return false;
				}
			}
		}

		return true;
	}

	private boolean isNumber(String value) {

		try {

			Double.parseDouble(value);

			return true;

		} catch (Exception e) {

			return false;
		}
	}

	private boolean isValidDate(String value, String format) {

		try {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

			LocalDate.parse(value, formatter);

			return true;

		} catch (Exception e) {

			return false;
		}
	}

	private void insertValid(Connection conn, String[] parts, String[] headers) throws Exception {

		StringBuilder json = new StringBuilder("{");

		for (int i = 0; i < headers.length; i++) {

			json.append("\"").append(headers[i]).append("\":\"").append(parts[i]).append("\"");

			if (i < headers.length - 1) {
				json.append(",");
			}
		}

		json.append("}");

		String sql = "INSERT INTO valid_records " + "(file_name,json_data) " + "VALUES (?,?)";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, file.getName());

			ps.setString(2, json.toString());

			ps.executeUpdate();
		}
	}

	private void insertInvalid(Connection conn, String rawRecord, String reason) throws Exception {

		String sql = "INSERT INTO invalid_records " + "(file_name,raw_data,reason) " + "VALUES (?,?,?)";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, file.getName());
			ps.setString(2, rawRecord);
			ps.setString(3, reason);

			ps.executeUpdate();
		}
	}

	private int getLastProcessedLine(Connection conn, String fileName) throws Exception {

		String sql = "SELECT last_line " + "FROM file_checkpoint " + "WHERE file_name=?";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, fileName);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				return rs.getInt("last_line");
			}
		}

		return 0;
	}

	private void updateCheckpoint(Connection conn, String fileName, int lineNumber) throws Exception {

		String sql = "INSERT OR REPLACE INTO " + "file_checkpoint " + "(file_name,last_line) " + "VALUES (?,?)";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, fileName);

			ps.setInt(2, lineNumber);

			ps.executeUpdate();
		}
	}

	/**
	 * Move file to Processed Folder
	 */
	private boolean moveToProcessedFolder() {

		try {

			File targetFile = new File(ExhibitUtil.dirProcessed, file.getName());

			Files.move(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			System.out.println("[FILE MOVED] " + file.getName() + " -> Processed Folder");

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			return false;
		}
	}

	/**
	 * Generates previous business day report.
	 */
	public static void generateDailyConsolidatedReport() {

		LocalDate previousBusinessDay = LocalDate.now().minusDays(1);

		try {

			File outputDir = new File(ExhibitUtil.dirOutput);

			outputDir.mkdirs();

			File reportFile = new File(outputDir, "Valid_Records_" + previousBusinessDay + ".csv");

			try (Connection conn = DBUtil.getConnection();

					PreparedStatement ps = conn.prepareStatement("""
							SELECT file_name,
							       json_data
							FROM valid_records
							WHERE DATE(created_at)=?
							""");

					BufferedWriter bw = new BufferedWriter(new FileWriter(reportFile))) {

				ps.setString(1, previousBusinessDay.toString());

				ResultSet rs = ps.executeQuery();

				bw.write("source_file,record");

				bw.newLine();

				while (rs.next()) {

					String sourceFile = rs.getString("file_name");

					String record = rs.getString("json_data").replace(",", ";");

					bw.write(sourceFile + "," + record);

					bw.newLine();
				}
			}

			System.out.println("[REPORT GENERATED] " + reportFile.getAbsolutePath());

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}