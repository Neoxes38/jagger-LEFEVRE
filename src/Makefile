all:
	java -cp ./javacc.jar javacc Jagger.jj
	javac *.java -d out/
	java -cp ./out/ src.Jagger

check:
	java -cp ./javacc.jar javacc Jagger.jj
	javac *.java -d out/
	java -cp ./out/ src.Jagger ../tests/test.txt

clear:
	rm -r out/
	rm *.class
	rm *.ctxt