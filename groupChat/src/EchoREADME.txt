for testing purpuses


to generate the key use this! they PW should ChatProject
keytool -genkey -keystore mySrvKeystore -keyalg RSA


Code needed for Client and Server
java -Djavax.net.ssl.keyStore=mySrvKeystore -Djavax.net.ssl.keyStorePassword=ChatProject EchoServer
java -Djavax.net.ssl.trustStore=mySrvKeystore -Djavax.net.ssl.trustStorePassword=ChatProject EchoClient
