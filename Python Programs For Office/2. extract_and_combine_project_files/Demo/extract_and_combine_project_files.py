import zipfile
import os
import shutil

# Define the ZIP file name and extraction directory
zip_filename = '*.zip'
extract_dir = 'Output-Extracted_App'
output_file = 'Output-Combined_Contents.txt'

# Extract the ZIP file
with zipfile.ZipFile(zip_filename, 'r') as zip_ref:
    zip_ref.extractall(extract_dir)

# File extensions to include
target_extensions = ['.java', '.properties']
# Specific file name to include
target_filenames = ['pom.xml']

# Open the output file for writing
with open(output_file, 'w', encoding='utf-8') as outfile:
    # Walk through the extracted directory
    for root, dirs, files in os.walk(extract_dir):
        for file in files:
            # Check if the file matches the target extensions or specific filenames
            if any(file.endswith(ext) for ext in target_extensions) or file in target_filenames:
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        content = f.read()
                        outfile.write(f"\n--- Contents of {file_path} ---\n")
                        outfile.write(content + '\n')
                except Exception as e:
                    print(f"Failed to read {file_path}: {e}")

print(f"Contents of .java, application.properties, and pom.xml files have been saved to {output_file}.")