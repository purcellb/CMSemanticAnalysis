@ECHO OFF
title A batch to facilitate the complex process of compiling the parser

call cup-run -cp "%CLASSPATH%;.;bin" parser.Driver %*
