Environment Set Up:

Assume your JDK home is "C:\Program Files\Java\jdk1.8.0_121\", otherwise replace it in 
all the batch files with your own JDK path.

1. Include the path to your JDK bin directory in the system path if you haven't done so.
2. Move java-cup-11b.jar to the JDK\lib directory.
3. Move java-cup-11b-runtime.jar to the JDK\jre\lib\ext\ directory.
4. Move the batch files in cup-commands to your JDK's bin directory. 
5. In current folder, create a bin folder if it is not there.
6. run "build" to get the parser ready.
7. run "run test/filename" to parse&analyze a single file.
8. run "batch_run" to parse&analyze all files in the test folder.
8. run "batch_run_to_file" to parse&analyze all files in the test folder and have the output saved in output.txt.