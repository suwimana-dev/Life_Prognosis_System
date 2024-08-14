input_file="../data/analytics.csv"
output_file="../data/download_analytics.csv"
formatted_output_file="../data/formatted_analytics.csv"


# The CSV will contain the same data with the columns separated by commas

# Write the header to the CSV file
echo "Email,Age,Lifespan" > "$output_file"

# Convert the semicolon-separated values to comma-separated values
awk 'BEGIN { FS = ";" ; OFS = "," } { print $0 }' "$input_file" >> "$output_file"
# Format CSV into aligned columns
column -s, -t "$output_file" > "$formatted_output_file"

echo "CSV file generated successfully: $formatted_output_file"
