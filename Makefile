all:
	cd src;\
	java -cp ./javacc.jar javacc Jagger.jj;\
	javac *.java -d out/;\
	java -cp ./out/ src.Jagger

check:
	java -cp ./src/javacc.jar javacc src/Jagger.jj
	javac -cp ./src/junit-4.12.jar:. ./tests/JaggerTest.java -d out/
	javac src/*.java -d out/
	java -cp ./src/junit-4.12.jar:./out/ tests.JaggerTest

clean:
	cd src;\
	rm Token*.java;\
	rm Jagger*.java;\
	rm SimpleCharStream.java;\
	rm ParseException.java;\
	rm -r out/