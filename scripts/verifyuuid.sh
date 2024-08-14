# Function to verify UUID

uuid=$1  # First argument: UUID to verify
# Use grep to search for the UUID in uuid.txt and cut to extract the email part
email=$(grep "$uuid" ../data/user-store.txt | cut -d ';' -f1)
if [ -z "$email" ]; then  # Check if the email variable is empty (UUID not found)
    echo "Invalid UUID."  # Print error message
else
    echo "$email"  # Print the email associated with the UUID
fi