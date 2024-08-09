#!/bin/bash

# Read the logged-in user's email from the temporary file
email=$(head -n 1 session.txt)

# Check if the email was read successfully
if [ -z "$email" ]; then
    echo "No user is currently logged in."
    exit 1
fi

# Fetch the existing user information from the user-store.txt based on the email
user_info=$(grep "$email" user-store.txt)

# Check if the user info was found
if [ -z "$user_info" ]; then
    echo "User profile not found."
    exit 1
fi

# Extract user information into an array
IFS=';' read -r -a user_array <<< "$user_info"

# Prompt for new values, leaving blank to keep current ones
echo "Updating Profile for: $email"
echo "Leave blank to keep current values."

read -p "First Name [${user_array[3]}]: " firstName
firstName=${firstName:-${user_array[3]}}

read -p "Last Name [${user_array[4]}]: " lastName
lastName=${lastName:-${user_array[4]}}

read -p "Date of Birth [${user_array[6]}]: " dateBirth
dateBirth=${dateBirth:-${user_array[6]}}

read -p "HIV Status [${user_array[7]}]: " statusHiv
statusHiv=${statusHiv:-${user_array[7]}}

read -p "Date of Diagnosis [${user_array[8]}]: " dateDiagnosis
dateDiagnosis=${dateDiagnosis:-${user_array[8]}}

read -p "ART Status [${user_array[9]}]: " statusArt
statusArt=${statusArt:-${user_array[9]}}

read -p "Date of ART [${user_array[10]}]: " dateArt
dateArt=${dateArt:-${user_array[10]}}

read -p "Country ISO Code [${user_array[11]}]: " countryISO
countryISO=${countryISO:-${user_array[11]}}

# Display the proposed changes
echo "Here are the changes:"
echo "First Name: ${user_array[3]} -> $firstName"
echo "Last Name: ${user_array[4]} -> $lastName"
echo "Date of Birth: ${user_array[6]} -> $dateBirth"
echo "HIV Status: ${user_array[7]} -> $statusHiv"
echo "Date of Diagnosis: ${user_array[8]} -> $dateDiagnosis"
echo "ART Status: ${user_array[9]} -> $statusArt"
echo "Date of ART: ${user_array[10]} -> $dateArt"
echo "Country ISO Code: ${user_array[11]} -> $countryISO"

# Ask for confirmation before applying changes
read -p "Do you want to save these changes? (yes/no): " confirmation

if [[ "$confirmation" == "yes" ]]; then
    # Replace the user's information in user-store.txt with the updated values
    sed -i "/$email/c\\${user_array[0]};${user_array[1]};${user_array[2]};$firstName;$lastName;${user_array[5]};$dateBirth;$statusHiv;$dateDiagnosis;$statusArt;$dateArt;$countryISO" user-store.txt
    echo "Profile updated successfully."
else
    echo "Profile update canceled."
fi

exit 0
