import zipfile
import os
import shutil

# Directory containing the ZIP files
zip_dir = '.'  # Current directory
extract_dir = 'temp_extracted'

# File extensions and specific filenames to include
target_extensions = ['.java']
target_filenames = ['pom.xml', 'application.properties']

# Process each ZIP file in the directory
for zip_filename in os.listdir(zip_dir):
    if zip_filename.endswith('.zip'):
        # Define output file name based on ZIP file name
        base_name = os.path.splitext(zip_filename)[0]
        output_file = f"{base_name}.txt"

        # Extract the ZIP file
        with zipfile.ZipFile(zip_filename, 'r') as zip_ref:
            zip_ref.extractall(extract_dir)

        # Open the output file for writing
        with open(output_file, 'w', encoding='utf-8') as outfile:
            # Walk through the extracted directory
            for root, dirs, files in os.walk(extract_dir):
                for file in files:
                    # Check if the file matches the target extensions or specific filenames
                    if any(file.endswith(ext) for ext in target_extensions) or file in target_filenames:
                        file_path = os.path.join(root, file)
                        try:
                            with open(file_path, 'r', encoding='utf-8', errors='replace') as f:
                                content = f.read()
                                outfile.write(f"\n--- Contents of {file_path} ---\n")
                                outfile.write(content + '\n')
                        except Exception as e:
                            print(f"Failed to read {file_path}: {e}")

        # Clean up the extracted directory
        shutil.rmtree(extract_dir)

print("All ZIP files have been processed.")