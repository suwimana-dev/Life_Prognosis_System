#!/bin/bash

# Read the email from the session file (this should be the first line)
email=$(head -n 1 session.txt)

# Find user info in user-store.txt and output it as a single line
grep "^$email;" user-store.txt
