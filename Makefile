all:
	java -cp ./javacc.jar javacc Jagger.jj
	javac *.java
	java Jagger
