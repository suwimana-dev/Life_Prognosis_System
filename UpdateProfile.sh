#!/bin/bash

# Function to display usage information
usage() {
    echo "Usage: $0 {get|update} [args...]"
    exit 1
}

# Check if at least two arguments are provided
if [ "$#" -lt 2 ]; then
    usage
fi

# Extract command and email
command=$1
email=$2

# Check for valid command
if [ "$command" != "get" ] && [ "$command" != "update" ]; then
    usage
fi

# Function to read profile information
read_profile() {
    # Extract user information from the file based on email
    user_info=$(grep "^$email;" user-store.txt)

    if [ -z "$user_info" ]; then
        echo "Error: User profile not found."
        exit 1
    else
        # Display the current profile information
        echo "$user_info" | awk -F ';' '{
            print "Email: " $0;
            print "Password: [hidden]";
            print "Profile Type: " $;
            print "First Name: " $4;
            print "Last Name: " $5;
            print "UUID: " $6;
            print "Date of Birth: " $7;
            print "HIV Status: " $8;
            print "Date of Diagnosis: " $9;
            print "ART Status: " $10;
            print "Date of ART: " $11;
            print "Country ISO: " $12;
        }'
    fi
}

# Function to update profile information
update_profile() {
    # Extract arguments
    newEmail=$3
    oldPassword=$4
    newFirstName=$5
    newLastName=$6
    newDateBirth=$7
    newStatusHiv=$8
    newDateDiagnosis=$9
    newStatusArt=${10}
    newDateArt=${11}
    newCountryISO=${12}

    # Validate old password
    currentPassword=$(grep "^$email;" user-store.txt | awk -F ';' '{print $2}')

    if [ "$oldPassword" != "" ] && [ "$oldPassword" != "$currentPassword" ]; then
        echo "Error: Old password is incorrect."
        exit 1
    fi

    # Prepare a temporary file to hold updated data
    temp_file=$(mktemp)

    # Update user information
    awk -F ';' -v email="$email" -v newEmail="$newEmail" -v newFirstName="$newFirstName" -v newLastName="$newLastName" -v newDateBirth="$newDateBirth" -v newStatusHiv="$newStatusHiv" -v newDateDiagnosis="$newDateDiagnosis" -v newStatusArt="$newStatusArt" -v newDateArt="$newDateArt" -v newCountryISO="$newCountryISO" -v oldPassword="$oldPassword" '
    BEGIN {OFS=";"}
    {
        if ($1 == email) {
            if (newEmail != "") $1 = newEmail
            if (newFirstName != "") $4 = newFirstName
            if (newLastName != "") $5 = newLastName
            if (newDateBirth != "") $7 = newDateBirth
            if (newStatusHiv != "") $8 = newStatusHiv
            if (newDateDiagnosis != "") $9 = newDateDiagnosis
            if (newStatusArt != "") $10 = newStatusArt
            if (newDateArt != "") $11 = newDateArt
            if (newCountryISO != "") $12 = newCountryISO
        }
        print
    }' user-store.txt > "$temp_file"

    # Replace the original file with updated data
    mv "$temp_file" user-store.txt

    echo "Profile updated successfully."
}

# Main script logic
case $command in
    get)
        read_profile
        ;;
    update)
        if [ "$#" -ne 12 ]; then
            usage
        fi
        update_profile
        ;;
esac
