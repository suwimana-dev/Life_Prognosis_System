#!/bin/bash

# Check operation mode
if [ "$1" == "read" ]; then
    email=$2
    # Read user profile information based on email
    grep "^$email;" user-store.txt

elif [ "$1" == "write" ]; then
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

    # Update the line containing the specified email
    sed -i "/^$newEmail;/c\\$newEmail;$newPassword;$newProfile;$firstName;$lastName;$uuid;$dateBirth;$statusHiv;$dateDiagnosis;$statusArt;$dateArt;$countryISO" user-store.txt
    echo -e "\n"
    echo "Profile updated successfully."

else
    echo
    echo "Invalid operation mode. Use 'read' or 'write'."
    exit 1
fi
