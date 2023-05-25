# TextFormat

# CLASS Word 
The given code performs word parsing and normalization on two input files (file1Path and file2Path). 
It creates a wordMap that maps each unique word to the list of file numbers in which it appears. 
The parsed word map is then written to a database file (dbFilePath).
Additionally, the code normalizes the words from the database file and writes them to a new file (newDbFilePath), 
removing any special characters, normalizing unicode characters, converting to lowercase, and removing digits.
Overall, this code helps in parsing and normalizing words from input files and storing them in a database file for further processing or analysis.

# CLASS WordNormalization
This code takes user input for a word, normalizes it by removing the plural "s" if present, and then finds the most similar word in the given words_alpha.txt
file using the Levenshtein distance algorithm. If an exact match is found in the normalization rules, it returns the corresponding normalized word; otherwise,
it returns the most similar word found in the file. The code facilitates word normalization and helps in finding similar words based on the Levenshtein distance.
