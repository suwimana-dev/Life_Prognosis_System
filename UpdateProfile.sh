#!/bin/bash

# Read the logged-in user's email from the temporary file
email=$(head -n 1 session.txt)

# Check if the temporary file was read successfully
if [ -z "$email" ]; then
    echo "No user is currently logged in."
    exit 1
fi

# Fetch user information from the user-store.txt based on the email
user_info=$(grep "$email" user-store.txt)

# Check if the user info was found
if [ -z "$user_info" ]; then
    echo "User profile not found."
    exit 1
fi

# Display the existing user profile information for reference
IFS=';' read -r -a user_array <<< "$user_info"
echo "Updating Profile for: $email"
echo "Leave blank to keep current values."

# Prompt for new values, leaving blank to keep current ones
read -p "First Name [${user_array[0]}]: " firstName
firstName=${firstName:-${user_array[0]}}

read -p "Last Name [${user_array[1]}]: " lastName
lastName=${lastName:-${user_array[1]}}

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

# Replace the user's information in user-store.txt with the updated values
sed -i "/$email/c\\$firstName;$lastName;$email;${user_array[3]};${user_array[4]};${user_array[5]};$dateBirth;$statusHiv;$dateDiagnosis;$statusArt;$dateArt;$countryISO" user-store.txt

echo "Profile updated successfully."
