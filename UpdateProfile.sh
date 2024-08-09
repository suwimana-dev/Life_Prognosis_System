#!/bin/bash

# Function to print usage
usage() {
    echo "Usage: $0 read email | write newEmail newPassword newProfile firstName lastName uuid dateBirth statusHiv dateDiagnosis statusArt dateArt countryISO"
    exit 1
}

# Check operation mode
if [ "$1" == "read" ]; then
    email=$2
    # Read user profile information based on email
    grep "^$email;" user-store.txt

elif [ "$1" == "write" ]; then
    # Arguments are provided in the following order
    newEmail=$2
    newPassword=$3
    newProfile=$4
    firstName=$5
    lastName=$6
    uuid=$7
    dateBirth=$8
    statusHiv=$9
    dateDiagnosis=${10}
    statusArt=${11}
    dateArt=${12}
    countryISO=${13}

    # Temporary file to store updated contents
    tempFile=$(mktemp)

    # Read the user-store.txt and update the user profile
    while IFS=';' read -r email password profileType fName lName uuid dob hivStatus diagDate artStatus artDate cISO; do
        if [ "$email" == "$newEmail" ]; then
            echo "$newEmail;$newPassword;$newProfile;$firstName;$lastName;$uuid;$dateBirth;$statusHiv;$dateDiagnosis;$statusArt;$dateArt;$countryISO" >> "$tempFile"
        else
            echo "$email;$password;$profileType;$fName;$lName;$uuid;$dob;$hivStatus;$diagDate;$artStatus;$artDate;$cISO" >> "$tempFile"
        fi
    done < user-store.txt

    # Replace the old file with the updated file
    mv "$tempFile" user-store.txt
    echo "Profile updated successfully."

else
    usage
fi
