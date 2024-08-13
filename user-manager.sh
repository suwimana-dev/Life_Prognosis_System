#!/bin/bash  # Shebang to specify the script should be run using bash

# Function to onboard a user
onboard_user() {
    email=$1  # First argument: user's email
    uuid=$2  # Second argument: user's UUID
    echo "$email;$uuid" >> user-store.txt  # Append email and UUID to uuid.txt file, separated by a semicolon
    echo "User onboarded with UUID: $uuid"  # Print confirmation message
}

# Function to verify UUID
verify_uuid() {
    uuid=$1  # First argument: UUID to verify
    # Use grep to search for the UUID in uuid.txt and cut to extract the email part
    email=$(grep "$uuid" user-store.txt | cut -d ';' -f1)
    if [ -z "$email" ]; then  # Check if the email variable is empty (UUID not found)
        echo "Invalid UUID."  # Print error message
    else
        echo "$email"  # Print the email associated with the UUID
    fi
}

#Function to harsh password
# harsh_password() {
#     password=$1
#     echo $password | openssl dgst -sha256 | awk '{print $2}'
#     #password=$store_password 
#     #echo $password
# }

# Function to register a patient
register_patient() {
    firstName=$1  # First argument: patient's first name
    lastName=$2  # Second argument: patient's last name
    email=$3  # Third argument: patient's email
    uuid=$4  # Fourth argument: UUID
    password=$5  # Fifth argument: password
    dateBirth=$6  # Sixth argument: date of birth
    statusHiv=$7  # Seventh argument: HIV status (true/false)
    dateDiagnosis=$8  # Eighth argument: date of HIV diagnosis
    statusArt=$9  # Ninth argument: ART status (true/false)
    dateArt=${10}  # Tenth argument: date of ART initiation
    countryISO=${11}  # Eleventh argument: country ISO code

    # encryp_password=`echo -n "$password" | openssl -dgst -sha256
    # Append patient details to user-store.txt file, separated by semicolons
    #echo "$email;$password;PATIENT;$firstName;$lastName;$uuid;$dateBirth;$statusHiv;$dateDiagnosis;$statusArt;$dateArt;$countryISO" >> user-store.txt

    #replace line in userstore with one below i.e., repopulate
    sed -i "/^$email;$uuid/c\\$email;$password;PATIENT;$firstName;$lastName;$uuid;$dateBirth;$statusHiv;$dateDiagnosis;$statusArt;$dateArt;$countryISO" user-store.txt

    #sed 's/.*/$email.*/&(/echo ";/$password;PATIENT;/$firstName;/$lastName;/$dateBirth;/$statusHiv;/$dateDiagnosis;/$statusArt;/$dateArt;/$countryISO)"/' user-store.txt
    echo "Patient registered successfully."  # Print confirmation message
}

# Function to login a user
login_user() {
    #echo "123234"
    email=$1  # First argument: user's email
    password=$2  # Second argument: user's password
    # Simulate user authentication by checking if the email and password pair exists in user-store.txt
    result = grep "$email;$password" user-store.txt
    if [[ -v $result ]]; then
        echo "$result"  # Print success message if authentication is successful
    else
        echo "failure"  # Print failure message if authentication fails
    fi
}

# Function to generate empty CSV files
generate_csv() {
    # Create or overwrite users.csv with header fields
    echo "firstName;lastName;inputEmail;uuid;inputPassword;profile;dateBirth;statusHiv;dateDiagnosis;statusArt;dateArt;countryISO" > users.csv
    # Create or overwrite analytics.csv with placeholder data
    echo "reportData" > analytics.csv
}


# # Function to compute lifespan
# lsp_computation() {
    
#     #pick user input and password as parameters for verification
#     $user_email=$1
#     # $user_password=$2
#     echo "$user_email"

#     user_file="user-store.txt"
#     user_expectancyfile="life-expectancy.csv"
#     #extraction and authentication -- ALTERNATIVELY, WILL HAVE TO COPY LOGIN METHOD TO DO THE VERIFICATION, 
#     #ALSO WHICH USER ACCESSES WHAT, BASE ON SESSION(??) if ADMIN or current user Y
#     email=$(grep "$user_email" $user_file | awk -F';' '{print$1}')
#     # password=$(grep "$user_password" $user_file | awk -F';' '{print$2}')
#     #echo "$email"
#     #if [ "$user_email" = "$email" ] && [ "$user_password" = "$password" ];
#     if [ "$user_email" = "$email" ]; then
#         #implement what you want
#         #user_dob=`awk -F',' '/$password/ {print $7}' $user_file
#         user_dob=$(grep "$email" "$user_file" | awk -F';' '{ print $7 }' | awk -F'-' '{ print $1 }')
#         #user_dob=$(awk '/$email/ && /$password/' "$user_file" | awk -F';' '{ print $7 }' | awk -F'-' '{ print $1 }')
#         user_hiv=$(awk -F';' -v email="$email" '$0 ~ email {print $8}' "$user_file")
#         user_hivdiag=$(grep "$email" $user_file | awk -F';' '{ print $9 }' | awk -F'-' '{ print $1 }')
#         user_art=$(awk -F';' -v email="$email" '$0 ~ email {print $10}' "$user_file")
#         user_artdiag=$(grep "$email" $user_file | awk -F';' '{ print $11 }' | awk -F'-' '{ print $1 }')
#         user_iso=$(awk -F';' -v email="$email" '$0 ~ email {print $12}' "$user_file")
#         user_isoCAP="${user_iso^^}" #make them capital to avoid confusion in search
#         current_date=2024
#         #extract user country expectancy from iso details
#         country_expectancyNR=$(grep "$user_isoCAP" "$user_expectancyfile" | awk -F',' '{ print $8 }')
#         #round it off
#         country_expectancy=$(awk -v num=$country_expectancyNR 'BEGIN { print int(num+0.9999) }')
#         #present user age
#         let "user_age = $current_date - $user_dob"

#         if [ "$user_hiv" = "false" ]; then
#             let "life_expectancy = $country_expectancy - $user_age"
#             echo $life_expectancy

#         elif [ "$user_hiv" = "true" ]; then
#             if [ "$user_art" = "false" ]; then
#                 #calculate expected lifespan when patient off ART
#                 echo "5"
#             elif [ "$user_art" = "true" ]; then
#                 #calculate expected lifespans when patient on ART, 
#                 let "remaining_years = $country_expectancy - $user_age"
#                 let "enrol_diff = $user_artdiag - $user_hivdiag" #let only works on ints
#                 positive_expectancy=$(awk "BEGIN {print $remaining_years * 0.9}") 
#                 if [ "$enrol_diff" = "0" ]; then
#                     echo "$positive_expectancy"
#                 else 
#                     i=1
#                     while [ "$i" -le $enrol_diff ]; do
#                         positive_expectancy=$(awk "BEGIN {print $positive_expectancy * 0.9}")
#                         i=$(i+1)
#                     done
#                     echo "$positive_expectancy"
#                 fi
#             fi
#         fi
        
    
#     else 
#         #echo "Wrong user email and/or password, lifespan computation not done"
#         echo "Wrong user email, lifespan computation not done"     
#         #insert redirection to previous menu

#     fi



# }


# Case statement to handle different script commands
case $1 in
    onboard)
        onboard_user $2 $3  # Call onboard_user function with provided arguments
        ;;
    verify-uuid)
        verify_uuid $2  # Call verify_uuid function with provided UUID
        ;;
    register)
        register_patient $2 $3 $4 $5 $6 $7 $8 $9 ${10} ${11} ${12}  # Call register_patient function with provided arguments
        ;;
    login)
        login_user $2 $3  # Call login_user function with provided email and password
        ;;
    generate-csv)
        generate_csv  # Call generate_csv function to create CSV files
        ;;
    # compute-lsp)
    #     #lsp_computation $1 $2 #Call lsp_computation function
    #     lsp_computation $1
    #     ;;
    *)
        echo "Invalid option."  # Print error message for invalid commands
        ;;
esac

