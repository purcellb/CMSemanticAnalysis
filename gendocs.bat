@echo off
dir /s /b *.java > file.lst

javadoc -d ./docs @file.lst
