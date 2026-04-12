#!/bin/bash

# ================================
# ZARA Interpreter — Run Script
# ================================

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}==============================${NC}"
echo -e "${BLUE}   ZARA Interpreter Runner    ${NC}"
echo -e "${BLUE}==============================${NC}"

# Step 1 — Check Java
if ! command -v javac &> /dev/null; then
    echo -e "${RED}ERROR: Java is not installed!${NC}"
    echo "Please install JDK from https://jdk.java.net"
    exit 1
fi

echo -e "${GREEN}Java found!${NC}"

# Step 2 — Create bin folder
mkdir -p bin

# Step 3 — Compile all Java files from all folders
echo -e "${YELLOW}Compiling all Java files...${NC}"

javac -d bin $(find . -name "*.java" ! -path "./bin/*")

if [ $? -eq 0 ]; then
    echo -e "${GREEN}Compilation Successful!${NC}"
else
    echo -e "${RED}Compilation Failed! Check errors above.${NC}"
    exit 1
fi

# Step 4 — Run the program
if [ -z "$1" ]; then
    echo -e "${YELLOW}No file given. Running test1.zara by default...${NC}"
    # Check samples folder first, then root
    if [ -f "samples/test1.zara" ]; then
        java -cp bin Main samples/test1.zara
    elif [ -f "test1.zara" ]; then
        java -cp bin Main test1.zara
    else
        echo -e "${RED}test1.zara not found!${NC}"
        exit 1
    fi
else
    echo -e "${YELLOW}Running: $1${NC}"
    java -cp bin Main "$1"
fi
