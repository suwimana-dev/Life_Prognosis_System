#!/bin/bash


# firstName=$1  # First argument: patient's first name
#     lastName=$2  # Second argument: patient's last name
#     email=$3  # Third argument: patient's email
#     uuid=$4  # Fourth argument: UUID
#     password=$5  # Fifth argument: password
#     dateBirth=$6  # Sixth argument: date of birth
#     statusHiv=$7  # Seventh argument: HIV status (true/false)
#     dateDiagnosis=$8  # Eighth argument: date of HIV diagnosis
#     statusArt=$9  # Ninth argument: ART status (true/false)
#     dateArt=${10}  # Tenth argument: date of ART initiation
#     countryISO=${11}  # Eleventh argument: country ISO code

# "$firstName;$lastName;$email;$uuid;$password;PATIENT;$dateBirth;$statusHiv;$dateDiagnosis;$statusArt;$dateArt$countryISO" >> user-store.txt
#date_onboard = read 

echo 'fname sname age gender 2022-01-03 ' >> textfile.txt
echo "This is the test to retrieve 2022 of date parameter"
test_word= sed -n '1p' textfile.txt |awk -F'[ -]' '{print $5}'
#test_word=$(sed -n '3p' textfile.txt |cut -d ' ' -f3)
echo "$test_word"


