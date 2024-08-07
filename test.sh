# email=$1  # First argument: user's email
# password=$2  # Second argument: user's password
# # Simulate user authentication by checking if the email and password pair exists in user-store.txt
# result="$(grep "$email.*$password" user-store.txt)"
# echo "$result"
# if [ -n "$result" ]; then
#     echo "$result"  # Print success message if authentication is successful
# else
#     echo "failure"  # Print failure message if authentication fails
# fi

#!/bin/bash

email=$1  # First argument: user's email
password=$2  # Second argument: user's password

# Simulate user authentication by checking if the email and password pair exists in user-store.txt
result=$(grep "^$email.*$password$" user-store.txt)  # Ensure exact match with start (^) and end ($) anchors

if [ -n "$result" ]; then
    echo "Authentication successful"  # Print success message if authentication is successful
else
    echo "Authentication failed"  # Print failure message if authentication fails
fi
