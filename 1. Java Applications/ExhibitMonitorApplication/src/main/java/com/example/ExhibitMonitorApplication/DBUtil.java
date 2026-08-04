package com.example.ExhibitMonitorApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Database Utility Class
 *
 * Responsibilities: 1. Create SQLite connection. 2. Create required tables
 * during application startup. 3. Support checkpoint recovery. 4. Store valid
 * and invalid records.
 */
public class DBUtil {

	private static final String DB_URL = "jdbc:sqlite:exhibit.db";

	/**
	 * Returns SQLite database connection.
	 */
	public static Connection getConnection() throws Exception {

		return DriverManager.getConnection(DB_URL);
	}

	/**
	 * Initializes all required database tables.
	 *
	 * Tables: 1. valid_records 2. invalid_records 3. file_checkpoint
	 */
	public static void init() {

		try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

			/*
			 * Stores all valid records.
			 *
			 * file_name: Example: loan-Pune.csv loan-Delhi.csv
			 *
			 * json_data: Entire row stored as JSON string.
			 */
			stmt.execute("""
					CREATE TABLE IF NOT EXISTS valid_records
					(
					    id INTEGER PRIMARY KEY AUTOINCREMENT,

					    file_name TEXT,

					    json_data TEXT,

					    created_at DATETIME
					    DEFAULT CURRENT_TIMESTAMP
					)
					""");

			/*
			 * Stores all rejected records.
			 *
			 * reason: Validation failure reason.
			 */
			stmt.execute("""
					CREATE TABLE IF NOT EXISTS invalid_records
					(
					    id INTEGER PRIMARY KEY AUTOINCREMENT,

					    file_name TEXT,

					    raw_data TEXT,

					    reason TEXT,

					    created_at DATETIME
					    DEFAULT CURRENT_TIMESTAMP
					)
					""");

			/*
			 * Supports restart recovery.
			 *
			 * Example:
			 *
			 * loan-Pune.csv line 120000
			 *
			 * After restart processing starts from line 120001.
			 */
			stmt.execute("""
					CREATE TABLE IF NOT EXISTS file_checkpoint
					(
					    file_name TEXT PRIMARY KEY,

					    last_line INTEGER
					)
					""");

			System.out.println("[DB] Initialized Successfully");

		} catch (Exception e) {

			System.out.println("[DB ERROR] " + e.getMessage());

			e.printStackTrace();
		}
	}

	/**
	 * Clears checkpoints after business-day processing is completed.
	 *
	 * Optional utility method.
	 */
	public static void clearCheckpoints() {

		try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

			stmt.executeUpdate("DELETE FROM file_checkpoint");

			System.out.println("[DB] Checkpoints Cleared");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Clears all records.
	 *
	 * Useful for testing/demo runs.
	 */
	public static void clearAllTables() {

		try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

			stmt.executeUpdate("DELETE FROM valid_records");

			stmt.executeUpdate("DELETE FROM invalid_records");

			stmt.executeUpdate("DELETE FROM file_checkpoint");

			System.out.println("[DB] All Tables Cleared");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}