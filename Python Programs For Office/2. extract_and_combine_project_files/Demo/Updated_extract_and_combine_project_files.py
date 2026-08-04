#!/usr/bin/env python3
"""
Recursive file collector with true duplicate prevention.

Uses (st_dev, st_ino) pairs — the most reliable method to ensure
a file is processed only once, even if reached multiple times via:
- symlinks
- junctions
- alias paths
- hard links
"""
import os
import sys
import logging
from datetime import datetime

ROOT_DIR = r"C:/Users/mujangid/OneDrive - Capgemini/Desktop/Project/New folder/GameScheduler"    # <- change this
OUTPUT_FILE = r"C:/Users/mujangid/OneDrive - Capgemini/Desktop/Project/New folder/GameScheduler.txt"  # <- change this
TARGET_EXTENSIONS = {".java", ".xml", ".JAVA", ".XML", ".properties", ".jsp", "JSP", ".htm", "HTM", ".html", "HTML"}  # case-insensitive matching
APPEND_MODE = False
FOLLOW_LINKS = False

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s | %(levelname)s | %(message)s",
    handlers=[logging.StreamHandler(sys.stdout)]
)


def read_file_with_fallback(path):
    encodings = ["utf-8", "latin-1"]
    for e in encodings:
        try:
            with open(path, "r", encoding=e, errors="replace") as f:
                return f.read(), e
        except Exception:
            continue
    raise IOError(f"Cannot read: {path}")


def consolidate_files(root, out_file, extensions, append=False, follow_links=False):

    processed_files = set()      # Stores (st_dev, st_ino)
    mode = "a" if append else "w"

    with open(out_file, mode, encoding="utf-8") as out:

        for dirpath, _, filenames in os.walk(root, followlinks=follow_links):

            for filename in filenames:
                ext = os.path.splitext(filename)[1].lower()
                if ext not in extensions:
                    continue

                file_path = os.path.join(dirpath, filename)

                try:
                    stat = os.stat(file_path)
                except Exception:
                    logging.error(f"Cannot stat: {file_path}")
                    continue

                file_id = (stat.st_dev, stat.st_ino)  # Unique physical file

                if file_id in processed_files:
                    logging.warning(f"⚠ Duplicate file avoided: {file_path}")
                    continue

                processed_files.add(file_id)

                try:
                    content, enc = read_file_with_fallback(file_path)
                except Exception as e:
                    logging.error(f"Failed to read {file_path}: {e}")
                    continue

                separator = (
                    "\n" + "=" * 80 + "\n"
                    f"FILE: {os.path.realpath(file_path)}\n"
                    f"SIZE: {stat.st_size} bytes\n"
                    f"ENCODING: {enc}\n"
                    f"MODIFIED: {datetime.fromtimestamp(stat.st_mtime)}\n"
                    + "=" * 80 + "\n\n"
                )

                out.write(separator)
                out.write(content)
                out.write("\n\n")

                logging.info(f"✔ Processed: {file_path}")


if __name__ == "__main__":
    exts = {("." + e.lower().lstrip(".")) for e in TARGET_EXTENSIONS}

    consolidate_files(
        root=ROOT_DIR,
        out_file=OUTPUT_FILE,
        extensions=exts,
        append=APPEND_MODE,
        follow_links=FOLLOW_LINKS
    )