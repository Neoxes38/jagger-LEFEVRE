all:
	java -cp ./src/javacc.jar javacc src/Jagger.jj
	javac src/*.java -d out/
	java -cp ./out/ src.Jagger

check:
	java -cp ./src/javacc.jar javacc src/Jagger.jj
	javac */*.java -d out/
	java -cp ./out/ tests.JaggerTest

clear:
	rm -r out/