import pip
package = 'docx2pdf'
try:
    __import__(package)
except ImportError:
    pip.main(['install', package])

from docx2pdf import convert
import os
files = os.listdir()
print("********Work is starting********")
filesWithoutEx=[x.split('.')[0] for x in files]
#print(filesWithoutEx)
count=0
if not os.path.exists("PDF"):
    os.makedirs("PDF")

for file in files:
	if file.endswith(".docx") or file.endswith(".doc"):
		#convert(file)
		fileName="PDF/"+filesWithoutEx[count]
		print(fileName)
		convert(file, "PDF/")
		count=count+1
print("********Work Done********")