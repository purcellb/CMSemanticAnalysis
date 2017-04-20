@ECHO OFF
title A batch to facilitate the complex process of compiling the parser

del .\bin\ast\*.class
del .\bin\parser\*.class
del .\bin\sem\*.class
del .\src\parser\sym.java
del .\src\parser\parser.java
del .\src\parser\lexer.java

call jflex -d src/parser src/specs/lexer.flex
call cup-build -destdir src/parser src/specs/parser.cup
call cup-javac -d bin src/parser/Settings.java src/parser/SrcLoc.java src/parser/sym.java src/parser/LEXSymbol.java src/parser/Lexer.java
call cup-javac -d bin -cp bin/ src/ast/ASTtupletype.java src/ast/ASTtype.java src/ast/ASTfunctype.java src/ast/ASTlist.java src/ast/ASTleaf.java src/ast/ASTnode.java src/sem/*.java 
call cup-javac -d bin -cp bin/ src/ast/*.java
call cup-javac -d bin -cp bin src/parser/parser.java
call cup-javac -d bin -cp bin src/parser/Driver.java
