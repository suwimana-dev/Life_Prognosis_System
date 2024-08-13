#!/bin/bash

    user_email=$1
    # $user_password=$2
    #echo $user_email

    user_file="user-store.txt"
    user_expectancyfile="life-expectancy.csv"
    #extraction and authentication -- ALTERNATIVELY, WILL HAVE TO COPY LOGIN METHOD TO DO THE VERIFICATION, 
    #ALSO WHICH USER ACCESSES WHAT, BASE ON SESSION(??) if ADMIN or current user Y
    #echo $user_file
    email=$(grep "$user_email" $user_file | awk -F';' '{print$1}')
    # password=$(grep "$user_password" $user_file | awk -F';' '{print$2}')
    #echo "$email"
    #if [ "$user_email" = "$email" ] && [ "$user_password" = "$password" ];
    if [ "$user_email" = "$email" ]; then
        #implement what you want
        
        user_dob=$(grep "$email" "$user_file" | awk -F';' '{ print $7 }' | awk -F'-' '{ print $1 }')
        user_hiv=$(awk -F';' -v email="$email" '$0 ~ email {print $8}' "$user_file")
        user_hivdiag=$(grep "$email" $user_file | awk -F';' '{ print $9 }' | awk -F'-' '{ print $1 }')
        user_art=$(awk -F';' -v email="$email" '$0 ~ email {print $10}' "$user_file")
        user_artdiag=$(grep "$email" $user_file | awk -F';' '{ print $11 }' | awk -F'-' '{ print $1 }')
        user_iso=$(awk -F';' -v email="$email" '$0 ~ email {print $12}' "$user_file")
        user_isoCAP="${user_iso^^}" #make them capital to avoid confusion in search
        current_date=2024
        #extract user country expectancy from iso details
        country_expectancyNR=$(grep "$user_isoCAP" "$user_expectancyfile" | awk -F',' '{ print $7 }')
        #round it up/off
        country_expectancy=$(awk -v num=$country_expectancyNR 'BEGIN { print int(num+0.9999) }')
        #present user age
        let "user_age = $current_date - $user_dob"
        
        if [ "$user_hiv" = "false" ]; then
            let "life_expectancy = $country_expectancy - $user_age"
            echo $life_expectancy

        elif [ "$user_hiv" = "true" ]; then
            if [ "$user_art" = "false" ]; then
                #calculate expected lifespan when patient off ART
                echo "5"
            elif [ "$user_art" = "true" ]; then
                #calculate expected lifespans when patient on ART, 
                let "remaining_years = $country_expectancy - $user_age"
                let "enrol_diff = $user_artdiag - $user_hivdiag" #let only works on ints
               
                positive_expectancy=$(awk "BEGIN {print $remaining_years * 0.9}") 
                
                if [ "$enrol_diff" = "0" ]; then
                    echo "$positive_expectancy"
                else 
                    i=1
                    while [ "$i" -le $enrol_diff ]; do
                        
                        positive_expectancy=$(awk "BEGIN {print $positive_expectancy * 0.9}")
                        
                        let "i = $i+1"
                    done
                    final_lsp=$(awk -v num=$positive_expectancy 'BEGIN { print int(num+0.9999) }')
                    echo "$final_lsp"
                fi
            fi
        fi
        
    
    else 
        #echo "Wrong user email and/or password, lifespan computation not done"
        echo "Wrong user email, lifespan computation not done"     
        #insert redirection to previous menu

    fi

