package com.example.ExhibitMonitorApplication;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Polar Monitor Thread
 *
 * Responsibilities: 1. Monitor Incoming Folder 2. Validate incoming files 3.
 * Move valid files to Processing Folder 4. Trigger Worker Threads 5. Detect
 * Business Date Change 6. Generate Daily Consolidated Report 7. Maintain
 * restart-safe state
 */
public class Polar implements Runnable {

	private static final int POLL_INTERVAL_MS = 2000;

	private final FileValidator validator = new FileValidator();

	@Override
	public void run() {

		File incomingDir = new File(ExhibitUtil.dirIncoming);

		File processingDir = new File(ExhibitUtil.dirProcessing);

		File processedDir = new File(ExhibitUtil.dirProcessed);

		incomingDir.mkdirs();
		processingDir.mkdirs();
		processedDir.mkdirs();

		System.out.println("[POLAR] Monitoring Started");

		/*
		 * Recover state after restart.
		 */
		syncMapWithProcessingFolder(processingDir);

		while (true) {

			try {

				/*
				 * Handle date change.
				 */
				handleBusinessDateChange(incomingDir, processingDir);

				/*
				 * Cleanup duplicate files.
				 */
				cleanIncomingFolder(incomingDir, processingDir);

				/*
				 * Process newly arrived files.
				 */
				processIncomingFiles(incomingDir, processingDir);

				Thread.sleep(POLL_INTERVAL_MS);

			} catch (Exception e) {

				System.out.println("[POLAR ERROR] " + e.getMessage());

				e.printStackTrace();
			}
		}
	}

	/**
	 * Process incoming files.
	 */
	private void processIncomingFiles(File incomingDir, File processingDir) throws Exception {

		File[] files = incomingDir.listFiles();

		if (files == null) {
			return;
		}

		for (File file : files) {

			/*
			 * Avoid partially copied files.
			 */
			if (!isFileStable(file)) {
				continue;
			}

			String fileName = file.getName().toLowerCase();

			/*
			 * Skip already tracked files.
			 */
			if (ExhibitUtil.arrivalMap.containsKey(fileName)) {

				continue;
			}

			boolean accepted = validator.isValidFile(file) && !validator.isDuplicateFile(file)
					&& validator.isFileOnTime(file);

			if (accepted) {

				acceptFile(file, processingDir);

			} else {

				rejectFile(file);
			}
		}
	}

	/**
	 * Accept valid file.
	 */
	private void acceptFile(File sourceFile, File processingDir) throws Exception {

		String fileName = sourceFile.getName().toLowerCase();

		ExhibitUtil.arrivalMap.put(fileName, LocalDateTime.now());

		ExhibitUtil.serialize();

		File processingFile = new File(processingDir, sourceFile.getName());

		Files.move(sourceFile.toPath(), processingFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

		System.out.println("[ACCEPTED] " + sourceFile.getName());

		Thread workerThread = new Thread(new Worker(processingFile), "Worker-" + sourceFile.getName());

		workerThread.start();
	}

	/**
	 * Reject invalid file.
	 */
	private void rejectFile(File file) throws Exception {

		Files.deleteIfExists(file.toPath());

		System.out.println("[REJECTED] " + file.getName());
	}

	/**
	 * Generate report when business date changes.
	 */
	private void handleBusinessDateChange(File incomingDir, File processingDir) {

		LocalDate currentDate = LocalDate.now(ZoneId.of(ExhibitUtil.timezone));

		if (currentDate.equals(ExhibitUtil.lastReset)) {

			return;
		}

		boolean incomingEmpty = isFolderEmpty(incomingDir);

		boolean processingEmpty = isFolderEmpty(processingDir);

		/*
		 * Do not generate report until all current-day processing finishes.
		 */
		if (!incomingEmpty || !processingEmpty) {

			System.out.println("[DATE CHANGE DETECTED] " + "Waiting for pending processing.");

			return;
		}

		try {

			System.out.println("[BUSINESS DAY CHANGE DETECTED]");

			/*
			 * Generate previous business day report.
			 */
			Worker.generateDailyConsolidatedReport();

			/*
			 * Reset daily tracking.
			 */
			ExhibitUtil.arrivalMap.clear();

			ExhibitUtil.lastReset = currentDate;

			ExhibitUtil.serialize();

			System.out.println("[RESET COMPLETED] " + currentDate);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Check folder status.
	 */
	private boolean isFolderEmpty(File folder) {

		File[] files = folder.listFiles();

		return files == null || files.length == 0;
	}

	/**
	 * Ensure file copy has completed.
	 */
	private boolean isFileStable(File file) {

		try {

			long size1 = file.length();

			Thread.sleep(200);

			long size2 = file.length();

			return size1 == size2;

		} catch (Exception e) {

			return false;
		}
	}

	/**
	 * Removes duplicate files already being processed.
	 */
	private void cleanIncomingFolder(File incomingDir, File processingDir) {

		File[] files = incomingDir.listFiles();

		if (files == null) {
			return;
		}

		for (File file : files) {

			String fileName = file.getName().toLowerCase();

			if (!ExhibitUtil.arrivalMap.containsKey(fileName)) {

				continue;
			}

			File processingFile = new File(processingDir, file.getName());

			if (!processingFile.exists()) {

				continue;
			}

			try {

				Files.deleteIfExists(file.toPath());

				System.out.println("[DUPLICATE CLEANUP] " + file.getName());

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	/**
	 * Recover application state after restart.
	 */
	private void syncMapWithProcessingFolder(File processingDir) {

		File[] files = processingDir.listFiles();

		if (files == null) {
			return;
		}

		/*
		 * Add active processing files.
		 */
		for (File file : files) {

			String fileName = file.getName().toLowerCase();

			ExhibitUtil.arrivalMap.putIfAbsent(fileName, LocalDateTime.now());
		}

		/*
		 * Remove stale entries.
		 */
		ExhibitUtil.arrivalMap.entrySet().removeIf(entry -> {

			File file = new File(processingDir, entry.getKey());

			return !file.exists();
		});

		ExhibitUtil.serialize();

		System.out.println("[SYNC] Processing Folder synchronized.");
	}
}