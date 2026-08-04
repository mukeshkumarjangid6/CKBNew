package com.example.ExhibitMonitorApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yaml.snakeyaml.Yaml;

/**
 * Utility class responsible for:
 *
 * 1. Loading configuration from YAML 2. Maintaining application state 3.
 * Persisting arrival information 4. Managing schema definitions 5. Converting
 * location-based filenames into logical schema names
 */
public class ExhibitUtil {

	/**
	 * Serialized file used for restart recovery.
	 */
	private static final String SERIALIZED_FILE = "arrivalMap.ser";

	/**
	 * Tracks files received for the current business day.
	 *
	 * Example: loan-Pune.csv kyc-Delhi.csv
	 */
	public static Map<String, LocalDateTime> arrivalMap = new HashMap<>();

	/**
	 * Last business-day reset date.
	 */
	public static LocalDate lastReset = LocalDate.now();

	/**
	 * Timezone loaded from YAML.
	 */
	public static String timezone;

	/**
	 * Supported file types.
	 *
	 * loan kyc newatt
	 */
	public static Set<String> supportedFileTypes = new HashSet<>();

	/**
	 * File deadline configuration.
	 *
	 * Example: key -> loan value -> 16:35
	 */
	public static Map<String, String> deadlineMap = new HashMap<>();

	/**
	 * Grace period configuration.
	 *
	 * Example: key -> loan value -> 60
	 */
	public static Map<String, Integer> graceMap = new HashMap<>();

	/**
	 * Folder locations.
	 */
	public static String dirIncoming;

	public static String dirProcessing;

	public static String dirProcessed;

	public static String dirOutput;

	/**
	 * Required headers for every schema.
	 *
	 * Example: loan.csv -> [header1, header2]
	 */
	public static Map<String, List<String>> schemaHeaders = new HashMap<>();

	/**
	 * Column validation configuration.
	 */
	public static Map<String, Map<String, Map<String, Object>>> schemaColumns = new HashMap<>();

	/**
	 * On application startup restore previous state.
	 */
	static {
		deserialize();
	}

	/**
	 * Loads YAML configuration.
	 */
	@SuppressWarnings("unchecked")
	public static void loadYaml() {

		try {

			Yaml yaml = new Yaml();

			Map<String, Object> root = yaml.load(ExhibitUtil.class.getResourceAsStream("/application.yml"));

			/*
			 * Time configuration
			 */
			Map<String, Object> timeConfig = (Map<String, Object>) root.get("time");

			timezone = (String) timeConfig.get("timezone");

			/*
			 * File configuration
			 */
			Map<String, Object> files = (Map<String, Object>) root.get("files");

			List<String> fileTypes = (List<String>) files.get("supported_file_types");

			supportedFileTypes = new HashSet<>(fileTypes);

			/*
			 * Schedule configuration
			 */
			Map<String, Object> schedule = (Map<String, Object>) files.get("schedule");

			schedule.forEach((fileType, cfgObj) -> {

				Map<String, Object> cfg = (Map<String, Object>) cfgObj;

				deadlineMap.put(fileType, (String) cfg.get("deadline"));

				graceMap.put(fileType, (Integer) cfg.get("grace_minutes"));
			});

			/*
			 * Folder configuration
			 */
			Map<String, Object> io = (Map<String, Object>) root.get("io");

			dirIncoming = (String) io.get("incoming");

			dirProcessing = (String) io.get("processingFolder");

			dirProcessed = (String) io.get("processedFolder");

			dirOutput = (String) io.get("output");

			/*
			 * Schema configuration
			 */
			Map<String, Object> schema = (Map<String, Object>) files.get("schema");

			if (schema != null) {

				schema.forEach((fileName, obj) -> {

					Map<String, Object> fileSchema = (Map<String, Object>) obj;

					/*
					 * Required headers
					 */
					List<String> headers = (List<String>) fileSchema.get("required_headers");

					if (headers != null) {

						schemaHeaders.put(fileName, headers);
					}

					/*
					 * Column validation rules
					 */
					Map<String, Map<String, Object>> columns = (Map<String, Map<String, Object>>) fileSchema
							.get("columns");

					if (columns != null) {

						schemaColumns.put(fileName, columns);
					}
				});
			}

			System.out.println("[YAML] Configuration Loaded");

		} catch (Exception e) {

			throw new RuntimeException("Failed to load YAML configuration", e);
		}
	}

	/**
	 * Save arrival map to disk.
	 *
	 * Used for restart recovery.
	 */
	public static void serialize() {

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SERIALIZED_FILE))) {

			oos.writeObject(arrivalMap);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Restore arrival map after application restart.
	 */
	@SuppressWarnings("unchecked")
	public static void deserialize() {

		File file = new File(SERIALIZED_FILE);

		if (!file.exists()) {
			return;
		}

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

			arrivalMap = (Map<String, LocalDateTime>) ois.readObject();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Returns file type.
	 *
	 * Examples:
	 *
	 * loan-Pune.csv -> loan
	 *
	 * kyc-Mumbai.csv -> kyc
	 */
	public static String getFileType(String fileName) {

		fileName = fileName.toLowerCase();

		int dashIndex = fileName.indexOf('-');

		if (dashIndex == -1) {

			return fileName.replace(".csv", "");
		}

		return fileName.substring(0, dashIndex);
	}

	/**
	 * Converts physical file name into logical schema name.
	 *
	 * Examples:
	 *
	 * loan-Pune.csv -> loan.csv
	 *
	 * loan-Delhi.csv -> loan.csv
	 *
	 * newatt-Mumbai.csv -> newatt.csv
	 */
	public static String getLogicalFileName(String fileName) {

		String fileType = getFileType(fileName);

		return fileType + ".csv";
	}
}