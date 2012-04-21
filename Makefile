build:
	ant gui
	ant server
	ant client
	
runServerGui:
	java -jar gui.jar

clean:
	rm -rf *.jar