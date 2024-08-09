#!/bin/bash

# Read the email from the session file (this should be the first line)
email=$(head -n 1 session.txt)

# Find user info in user-store.txt
user_info=$(grep "$email" user-store.txt | tr -d '\r\n')  # Ensure no newlines

# Print raw user_info for debugging
//echo "Raw user info: $user_info"

# Check if user info was found
if [ -z "$user_info" ]; then
    echo "User not found."
else
    # Split user info into an array using ';' as the delimiter
    IFS=';' read -r -a user_array <<< "$user_info"

    # Debugging: Print the array contents
    echo "Array contents:"
    for i in "${!user_array[@]}"; do
        echo "user_array[$i]: ${user_array[$i]}"
    done

    # Output the user info based on the correct order of fields in the text file
    echo "Email: ${user_array[0]}"            # Email
    echo "First Name: ${user_array[3]}"       # First Name
    echo "Last Name: ${user_array[4]}"        # Last Name
    echo "UUID: ${user_array[5]}"             # UUID
    echo "Date of Birth: ${user_array[6]}"    # Date of Birth
    echo "HIV Status: ${user_array[7]}"       # HIV Status
    echo "Date of Diagnosis: ${user_array[8]}" # Date of Diagnosis
    echo "ART Status: ${user_array[9]}"       # ART Status
    echo "Date of ART: ${user_array[10]}"     # Date of ART
    echo "Country ISO: ${user_array[11]}"     # Country ISO
fi
