#!/bin/bash

# Get the current working directory
current_dir=$(pwd)

# Empty the session file
> "$current_dir/session.txt"

# Print a logout message
echo "You have been logged out."

# Exit the script, simulating a Ctrl+C action
exit 0
