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

# email=$1  # First argument: user's email
# password=$2  # Second argument: user's password

# # Simulate user authentication by checking if the email and password pair exists in user-store.txt
# result=$(grep "^$email.*$password$" user-store.txt)  # Ensure exact match with start (^) and end ($) anchors

# if [ -n "$result" ]; then
#     echo "Authentication successful"  # Print success message if authentication is successful
# else
#     echo "Authentication failed"  # Print failure message if authentication fails
# fi



# #!/bin/bash

# # Get email and password from user-supplied arguments
# email=$1
# password=$2

# # Search for the user record in user-store.txt
# user_record=$(grep "^$email;$password;" user-store.txt)

# # Check if the record exists
# if [ -n "$user_record" ]; then
#     echo "Successful"
# else
#     echo "error"
# fi


#!/bin/bash

# Get email and password from user-supplied arguments
email=$1
password=$2

# Search for the user record in user-store.txt
user_record=$(grep "^$email;$password;" user-store.txt)

# Check if the record exists
if [ -n "$user_record" ]; then
    # Extract the user type (3rd field) from the user record
    user_type=$(echo "$user_record" | awk -F ';' '{print $3}')

    echo "$email" > session.txt
    echo "$user_type"
else
    echo "error"
fi

