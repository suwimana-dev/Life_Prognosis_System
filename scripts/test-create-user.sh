email=$1  # First argument: user's email
uuid=$2  # Second argument: user's UUID
echo "$email;$uuid" >> ../data/user-store.txt  # Append email and UUID to user-store.txt file, separated by a semicolon
echo "User onboarded with UUID: $uuid"  # Print confirmation message