# #!/bin/bash
# email=$1
# password=$2

# user_reports=$(grep -P "^$email;$password;.*" user-store.txt)

# if [ -n "$user_reports" ]; then

#     echo "$user_reports"
# else
#     echo "error"
# fi



#!/bin/bash

# Define the input and output files
input_file="user-store.txt"
output_file="user_report.csv"

# Read the user store file and convert it to a CSV format
# The user store is assumed to be in the format: email;password;role;optional_fields...
# The CSV will contain the same data with the columns separated by commas

# Write the header to the CSV file
echo "Email,Password,Role,UUID" > "$output_file"

# Convert the semicolon-separated values to comma-separated values
awk 'BEGIN { FS = ";" ; OFS = "," } { print $0 }' "$input_file" >> "$output_file"

echo "CSV file generated successfully: $output_file"
