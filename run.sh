
#!/bin/bash

# ================================
# ZARA Interpreter — Run Script
# ================================

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}==============================${NC}"
echo -e "${BLUE}   ZARA Interpreter Runner    ${NC}"
echo -e "${BLUE}==============================${NC}"

# Step 1 — Check if Java is installed
if ! command -v javac &> /dev/null; then
    echo -e "${RED}ERROR: Java is not installed!${NC}"
    echo "Please install JDK from https://jdk.java.net"
    exit 1
fi

echo -e "${GREEN}Java found!${NC}"

# Step 2 — Create bin folder if not exists
mkdir -p bin

# Step 3 — Compile all Java files
echo -e "${YELLOW}Compiling...${NC}"
javac -d bin src/*.java

if [ $? -eq 0 ]; then
    echo -e "${GREEN}Compilation Successful!${NC}"
else
    echo -e "${RED}Compilation Failed! Check errors above.${NC}"
    exit 1
fi

# Step 4 — Run the program
if [ -z "$1" ]; then
    echo -e "${YELLOW}No file given. Running test1.zara by default...${NC}"
    java -cp bin Main samples/test1.zara
else
    echo -e "${YELLOW}Running: $1${NC}"
    java -cp bin Main "$1"
fi
