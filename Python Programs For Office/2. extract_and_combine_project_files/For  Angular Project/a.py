#!/usr/bin/env python3

"""
Angular Project File Consolidator

Features:
- Recursive scan
- Duplicate prevention using (st_dev, st_ino)
- Exclude node_modules, dist, coverage, .git, etc.
- Supports Angular/TypeScript projects
- Encoding fallback support
- Generates a single consolidated text file
"""

import os
import sys
import logging
from pathlib import Path
from datetime import datetime


# ------------------------------------------------------------------
# CONFIGURATION
# ------------------------------------------------------------------

ROOT_DIR = r"C:\Users\mujangid\OneDrive - Capgemini\Desktop\New folder\ANgularDemo4\employee-demo"

OUTPUT_FILE = r"C:\Users\mujangid\OneDrive - Capgemini\Desktop\employee-demo.txt"

APPEND_MODE = False
FOLLOW_LINKS = False

TARGET_EXTENSIONS = {
    # Angular / TypeScript
    ".ts",
    ".tsx",

    # JavaScript
    ".js",
    ".mjs",

    # Templates
    ".html",

    # Styling
    ".css",
    ".scss",
    ".sass",
    ".less",

    # Configuration
    ".json",
    ".yml",
    ".yaml",

    # Documentation
    ".md",
    ".txt",

    # Optional SVG templates/icons
    ".svg"
}

EXCLUDED_DIRS = {
    "node_modules",
    ".git",
    ".angular",
    ".vscode",
    ".idea",
    "dist",
    "coverage",
    ".nx",
    ".cache",
    "tmp",
    "temp"
}

EXCLUDED_SUFFIXES = {
    ".png",
    ".jpg",
    ".jpeg",
    ".gif",
    ".webp",
    ".ico",
    ".woff",
    ".woff2",
    ".ttf",
    ".eot",
    ".map",
    ".zip",
    ".jar",
    ".tar",
    ".gz",
    ".pdf",
    ".mp4",
    ".mp3"
}


# ------------------------------------------------------------------
# LOGGING
# ------------------------------------------------------------------

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s | %(levelname)s | %(message)s",
    handlers=[logging.StreamHandler(sys.stdout)]
)


# ------------------------------------------------------------------
# FILE READING
# ------------------------------------------------------------------

def read_file_with_fallback(path):
    """
    Read file using multiple encodings.
    """
    encodings = [
        "utf-8",
        "utf-8-sig",
        "cp1252",
        "latin-1"
    ]

    for encoding in encodings:
        try:
            with open(
                path,
                "r",
                encoding=encoding,
                errors="replace"
            ) as f:
                return f.read(), encoding
        except Exception:
            continue

    raise IOError(f"Cannot read: {path}")


# ------------------------------------------------------------------
# MAIN LOGIC
# ------------------------------------------------------------------

def consolidate_files(
    root,
    output_file,
    extensions,
    append=False,
    follow_links=False
):
    """
    Consolidate files into a single text file.
    """

    mode = "a" if append else "w"

    extensions = {
        ext.lower() if ext.startswith(".")
        else "." + ext.lower()
        for ext in extensions
    }

    visited = set()

    files_scanned = 0
    files_written = 0
    duplicates_skipped = 0
    errors = 0

    start_time = datetime.now()

    logging.info("Scanning: %s", root)
    logging.info("Writing output to: %s", output_file)

    with open(output_file, mode, encoding="utf-8") as outfile:

        outfile.write("=" * 100 + "\n")
        outfile.write("ANGULAR PROJECT CONSOLIDATED SOURCE\n")
        outfile.write(f"Generated: {start_time}\n")
        outfile.write("=" * 100 + "\n\n")

        for dirpath, dirnames, filenames in os.walk(
                root,
                followlinks=follow_links):

            # Exclude unwanted folders
            dirnames[:] = [
                d for d in dirnames
                if d not in EXCLUDED_DIRS
            ]

            for filename in filenames:

                path = os.path.join(dirpath, filename)

                ext = Path(filename).suffix.lower()

                if ext in EXCLUDED_SUFFIXES:
                    continue

                if ext not in extensions:
                    continue

                files_scanned += 1

                try:
                    stat_info = os.stat(path)

                    unique_key = (
                        stat_info.st_dev,
                        stat_info.st_ino
                    )

                    if unique_key in visited:
                        duplicates_skipped += 1
                        continue

                    visited.add(unique_key)

                    content, encoding_used = (
                        read_file_with_fallback(path)
                    )

                    relative_path = os.path.relpath(path, root)

                    outfile.write("\n")
                    outfile.write("=" * 120 + "\n")
                    outfile.write(
                        f"FILE: {relative_path}\n"
                    )
                    outfile.write(
                        f"ENCODING: {encoding_used}\n"
                    )
                    outfile.write("=" * 120 + "\n")
                    outfile.write(content)
                    outfile.write("\n\n")

                    files_written += 1

                    if files_written % 100 == 0:
                        logging.info(
                            "Processed %s files",
                            files_written
                        )

                except Exception as ex:
                    errors += 1
                    logging.warning(
                        "Failed: %s | %s",
                        path,
                        ex
                    )

    elapsed = datetime.now() - start_time

    logging.info("Done")
    logging.info("Elapsed Time      : %s", elapsed)
    logging.info("Files Scanned     : %s", files_scanned)
    logging.info("Files Written     : %s", files_written)
    logging.info("Duplicates Skipped: %s", duplicates_skipped)
    logging.info("Errors            : %s", errors)


# ------------------------------------------------------------------
# ENTRY POINT
# ------------------------------------------------------------------

if __name__ == "__main__":

    if not os.path.exists(ROOT_DIR):
        logging.error(
            "ROOT_DIR does not exist: %s",
            ROOT_DIR
        )
        sys.exit(1)

    consolidate_files(
        root=ROOT_DIR,
        output_file=OUTPUT_FILE,
        extensions=TARGET_EXTENSIONS,
        append=APPEND_MODE,
        follow_links=FOLLOW_LINKS
    )