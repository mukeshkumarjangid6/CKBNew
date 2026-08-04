package com.example.ExhibitMonitorApplication;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileValidator
 *
 * Responsibilities: 1. Validate filename format using regex. 2. Check duplicate
 * file processing. 3. Validate SLA deadline and grace window. 4. Validate file
 * belongs to current business date.
 */
public class FileValidator {

	/**
	 * Supported file pattern:
	 *
	 * loan-Pune.csv loan-Delhi.csv loan-Mumbai.csv
	 *
	 * kyc-Pune.csv kyc-Delhi.csv kyc-Mumbai.csv
	 *
	 * newatt-Pune.csv newatt-Delhi.csv newatt-Mumbai.csv
	 */
	private static final Pattern FILE_PATTERN = Pattern.compile("^(loan|kyc|newatt)-([A-Za-z]+)\\.csv$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Validates filename using regex pattern.
	 *
	 * Examples: loan-Pune.csv -> VALID kyc-Mumbai.csv -> VALID newatt-Delhi.csv ->
	 * VALID
	 *
	 * loan.csv -> INVALID abc-Pune.csv -> INVALID test.txt -> INVALID
	 */
	public boolean isValidFile(File file) {

		if (file == null) {
			return false;
		}

		String fileName = file.getName();

		Matcher matcher = FILE_PATTERN.matcher(fileName);

		if (!matcher.matches()) {

			System.out.println("[REJECT] Invalid File Pattern : " + fileName);

			return false;
		}

		String fileType = matcher.group(1).toLowerCase();

		boolean supported = ExhibitUtil.supportedFileTypes.contains(fileType);

		if (!supported) {

			System.out.println("[REJECT] Unsupported File Type : " + fileName);
		}

		return supported;
	}

	/**
	 * Checks whether file has already been processed during the current business
	 * day.
	 */
	public boolean isDuplicateFile(File file) {

		if (file == null) {
			return false;
		}

		String fileName = file.getName().toLowerCase();

		boolean duplicate = ExhibitUtil.arrivalMap.containsKey(fileName);

		if (duplicate) {

			System.out.println("[REJECT] Duplicate File : " + fileName);
		}

		return duplicate;
	}

	/**
	 * Validates:
	 *
	 * 1. File belongs to today's business date. 2. File arrived within allowed SLA
	 * window.
	 *
	 * SLA Window:
	 *
	 * deadline <= arrivalTime <= deadline + grace
	 */
	public boolean isFileOnTime(File file) {

		try {

			String fileType = ExhibitUtil.getFileType(file.getName());

			String deadlineStr = ExhibitUtil.deadlineMap.get(fileType);

			Integer graceMinutes = ExhibitUtil.graceMap.get(fileType);

			if (deadlineStr == null || graceMinutes == null) {

				System.out.println("[REJECT] Missing Schedule Config : " + file.getName());

				return false;
			}

			ZoneId zone = ZoneId.of(ExhibitUtil.timezone);

			/**
			 * Validate business date using file modified date
			 */
			LocalDate fileDate = Instant.ofEpochMilli(file.lastModified()).atZone(zone).toLocalDate();

			LocalDate currentDate = LocalDate.now(zone);

			if (!fileDate.equals(currentDate)) {

				System.out.println("[REJECT] File Date Not Current Business Day : " + file.getName());

				return false;
			}

			/**
			 * Arrival time validation
			 */
			LocalTime arrivalTime = LocalTime.now(zone);

			LocalTime deadline = LocalTime.parse(deadlineStr);

			LocalTime cutoff = deadline.plusMinutes(graceMinutes);

			System.out.println("[SLA CHECK] File      : " + file.getName());

			System.out.println("[SLA CHECK] Arrival   : " + arrivalTime);

			System.out.println("[SLA CHECK] Deadline  : " + deadline);

			System.out.println("[SLA CHECK] Cutoff    : " + cutoff);

			boolean withinWindow = !arrivalTime.isBefore(deadline) && !arrivalTime.isAfter(cutoff);

			if (!withinWindow) {

				System.out.println("[REJECT] Outside SLA Window : " + file.getName());
			}

			return withinWindow;

		} catch (Exception e) {

			System.out.println("[ERROR] File Validation Failed : " + file.getName());

			e.printStackTrace();

			return false;
		}
	}
}