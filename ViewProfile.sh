#!/bin/bash

# Read user info from the file
email=$1  # First argument: user's email

# Find user info in user-store.txt
user_info=$(grep "$email" user-store.txt)  # Search for user info by email

if [ -z "$user_info" ]; then  # Check if user info is empty
    echo "User not found."
else
    # Extract user info
    IFS=';' read -r -a user_array <<< "$user_info"  # Split user info into array
    first_name=${user_array[0]}  # Extract first name
    last_name=${user_array[1]}  # Extract last name
    uuid=${user_array[3]}  # Extract UUID
    date_birth=${user_array[6]}  # Extract date of birth
    status_hiv=${user_array[7]}  # Extract HIV status
    date_diagnosis=${user_array[8]}  # Extract date of diagnosis
    status_art=${user_array[9]}  # Extract ART status
    date_art=${user_array[10]}  # Extract date of ART
    country_iso=${user_array[11]}  # Extract country ISO

    # Output user info
    echo "First Name: $first_name"
    echo "Last Name: $last_name"
    echo "UUID: $uuid"
    echo "Date of Birth: $date_birth"
    echo "HIV Status: $status_hiv"
    echo "Date of Diagnosis: $date_diagnosis"
    echo "ART Status: $status_art"
    echo "Date of ART: $date_art"
    echo "Country ISO: $country_iso"
fi
