@echo off

for /f "usebackq TOKENS=*" %%i in (`dir /s /b test`) do call cup-run -cp "%CLASSPATH%;.;bin" parser.Driver test/%%~nxi