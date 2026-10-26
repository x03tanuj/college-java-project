@echo off
REM Windows build script for CLI Flash Card Quiz (pure javac/java)
echo === Building Flash Card Quiz ===
if not exist out mkdir out

dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
del sources.txt

echo Compilation successful!
echo Run using: run.bat or java -cp out com.flashquiz.Main
