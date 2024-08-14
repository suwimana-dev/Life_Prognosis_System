# Function to register a patient

email=$1  # First argument: patient's first name
password=$2  # Second argument: patient's last name
firstName=$3  # Third argument: patient's email
uuid=$4  # Fourth argument: UUID
lastName=$5  # Fifth argument: password
dateBirth=$6  # Sixth argument: date of birth
statusHiv=$7  # Seventh argument: HIV status (true/false)
dateDiagnosis=$8  # Eighth argument: date of HIV diagnosis
statusArt=$9  # Ninth argument: ART status (true/false)
dateArt=${10}  # Tenth argument: date of ART initiation
countryISO=${11}  # Eleventh argument: country ISO code


# firstName=$1  # First argument: patient's first name
# lastName=$2  # Second argument: patient's last name
# email=$3  # Third argument: patient's email
# uuid=$4  # Fourth argument: UUID
# password=$5  # Fifth argument: password
# dateBirth=$6  # Sixth argument: date of birth
# statusHiv=$7  # Seventh argument: HIV status (true/false)
# dateDiagnosis=$8  # Eighth argument: date of HIV diagnosis
# statusArt=$9  # Ninth argument: ART status (true/false)
# dateArt=${10}  # Tenth argument: date of ART initiation
# countryISO=${11}  # Eleventh argument: country ISO code
# Append patient details to user-store.txt file, separated by semicolons

#echo "$firstName    $lastName   $email  $uuid   $password   PATIENT $dateBirth  $statusHiv  $dateDiagnosis  $statusArt  $dateArt    $countryISO" >> user-store.txt
#echo "$firstName;$lastName;$password;PATIENT;$dateBirth;$statusHiv;$dateDiagnosis;$statusArt;$dateArt;$countryISO" >> user-store.txt
#echo "Patient registered successfully."  # Print confirmation message


#TRYING TO APPEND ONLY NEW STUFF TO LINE IN USER-STORE.TXT
file="../data/user-store.txt"
existing_parameters="$email $uuid   $password"
append_parameters="$firstName    $lastName   $email  $uuid   $password   PATIENT $dateBirth  $statusHiv  $dateDiagnosis  $statusArt  $dateArt    $countryISO"
line=$(grep -m 1 "$existing_parameters" "$file")
for existing_parameters in $append_parameters;
do
    if [[ "$line" != *"$existing_parameters"*]]; then
        line="$line $existing_parameters"
    fi
done
#directly modifies without creating or redirecting
sed -i "|$existing_parameters.*|$line|" "$file"

echo "Patient registered successfully."  # Print confirmation message