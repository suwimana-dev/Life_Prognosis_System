#!/bin/bash

# Input and output file paths
input_file="data.txt"
output_file="analytics.csv"
column_number=3  # Specify which column to process (1-based index)

# Extract the specified column
numbers=($(awk -v col="$column_number" '{print $col}' "$input_file"))

# Sort the array to prepare for median and percentile calculations
sorted_numbers=($(printf '%s\n' "${numbers[@]}" | sort -n))

# Calculate the number of elements
count=${#sorted_numbers[@]}

# Calculate the sum for the average
sum=0
for num in "${sorted_numbers[@]}"; do
    sum=$(echo "$sum + $num" | bc)
done

# Calculate average
average=$(echo "scale=2; $sum / $count" | bc)

# Calculate median
if (( $count % 2 == 1 )); then
    median=${sorted_numbers[$((count / 2))]}
else
    mid_index=$((count / 2))
    median=$(echo "scale=2; (${sorted_numbers[$mid_index]} + ${sorted_numbers[$((mid_index - 1))]}) / 2" | bc)
fi

# Function to calculate percentile
calculate_percentile() {
    p=$1
    index=$(echo "scale=2; ($p / 100) * ($count - 1)" | bc)
    lower=$(printf "%.0f" $(echo "$index" | cut -d'.' -f1))
    upper=$(($lower + 1))

    if [[ "$index" =~ \. ]]; then
        fractional=$(echo "$index" | cut -d'.' -f2)
        percentile=$(echo "scale=2; ${sorted_numbers[$lower]} + (${sorted_numbers[$upper]} - ${sorted_numbers[$lower]}) * 0.$fractional" | bc)
    else
        percentile=${sorted_numbers[$lower]}
    fi

    echo "$percentile"
}

# Calculate 50th percentile (Median)
p50=$(calculate_percentile 50)

# Calculate 90th percentile
p90=$(calculate_percentile 90)

# Write results to output file
{
    echo "Results for Column $column_number"
    echo "----------------------------"
    echo "Average: $average"
    echo "Median: $median"
    echo "50th Percentile: $p50"
    echo "90th Percentile: $p90"
} > "$output_file"

echo "Results have been written to $output_file"
