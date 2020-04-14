all:
	cd src;\
	java -cp ./javacc.jar javacc Jagger.jj;\
	javac *.java -d out/;\
	java -cp ./out/ src.Jagger

check:
	javac -cp ./tests/junit-4.12.jar:. ./tests/JaggerTest.java -d out/
	java -cp ./tests/junit-4.12.jar:./tests/hamcrest-2.2.jar:./out/ org.junit.runner.JUnitCore tests.JaggerTest

clean:
	cd src;\
	rm Token*.java;\
	rm Jagger*.java;\
	rm SimpleCharStream.java;\
	rm ParseException.java;\
	rm -r out/