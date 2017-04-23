@ECHO OFF
title A batch to facilitate the complex process of compiling the parser

call cup-run -debug -cp "%CLASSPATH%;.;bin" parser.Driver %*> output.txt 2>&1
