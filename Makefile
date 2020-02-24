all:
	java -cp ./javacc.jar javacc Jagger.jj
	javac *.java
	java Jagger

check:
	java -cp ./javacc.jar javacc Jagger.jj
	javac *.java
	java Jagger tests/test.txt
clear_dir:
	rm *.class
	rm *.ctxt
	rm *.swo
	rm *.swp
