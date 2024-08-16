
#!/bin/bash

# Define the input and output files
input_file="../data/user-store.txt"
output_file="../data/user_report.csv"
formatted_output_file="../data/formatted_output.csv"

# Read the user store file and convert it to a CSV format
# The user store is assumed to be in the format: email;password;role;optional_fields...
# The CSV will contain the same data with the columns separated by commas

# Write the header to the CSV file
echo "Email,Password,Role,UUID,firstname,lastname,dateofbirth," > "$output_file"

# Convert the semicolon-separated values to comma-separated values
#awk 'BEGIN { FS = ";" ; OFS = "," } { print $0 }' "$input_file" >> "$output_file"
awk 'BEGIN { FS = ";" ; OFS = "," } { $2=""; sub(/,,/, ","); print $0}' "$input_file" >> "$output_file"
# Format CSV into aligned columns
column -s, -t "$output_file" > "$formatted_output_file"

echo "CSV file generated successfully: $formatted_output_file"
