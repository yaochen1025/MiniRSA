build:
	ant gui
	ant server
	ant client
	ant encrypt
	ant decrypt
	ant cracker

runGui:
	java -jar gui.jar

runTerminalServer:
	java -jar server.jar

runTerminalClient:
	java -jar client.jar

testLocalGui:
	java -jar gui.jar

testEncrypt:
	java -jar encrypt.jar

testDecrypt:
	java -jar decrypt.jar

testCracker:
	java -jar cracker.jar
	
clean:
	rm -rf *.jar