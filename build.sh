#!/bin/bash
# Shell build script for CLI Flash Card Quiz (pure javac/java)
set -e

echo "=== Building Flash Card Quiz ==="
mkdir -p out

# Find all Java source files
find src -name "*.java" > sources.txt

if [ ! -s sources.txt ]; then
    echo "No source files found in src/."
    rm sources.txt
    exit 1
fi

# Compile using javac to out directory
javac -d out @sources.txt
rm sources.txt

echo "Compilation successful!"
echo "Run using: ./run.sh or java -cp out com.flashquiz.Main"
