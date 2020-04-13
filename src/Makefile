all:
	java -cp ./javacc.jar javacc Jagger.jj
	javac *.java -d out/
	java -cp ./out/ src.Jagger

check:
	java -cp ./javacc.jar javacc Jagger.jj
	javac *.java -d out/
	java -cp ./out/ src.Jagger ../tests/test.txt
	make clear

clear:
	rm Token*.java
	rm Jagger*.java
	rm SimpleCharStream.java
	rm ParseException.java
	rm -r out/