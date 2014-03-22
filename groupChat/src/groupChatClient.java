import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * CS475 Secure Group Chat Server
 * Ken Fox, Gavin Rapp, Andrea Pavia
 * basic chat client program 
 * will move this code into gui or reference gui from here.
 * 
 */

/**
 * @author kenfox
 *
 */
public class groupChatClient {
	/**
	 * fields for holding the my hostname and IP address
	 */
	private static String myServerHostName = "";//my host name
	private static String myServerIPAddress = ""; //my IP Address
	/**
	 * port number that this server listens on default is 9999
	 * this is ovrridden on the command line by specifying the -P <nnnnn>
	 * switch and value
	 */
	private static int serverPort = 9999; // this is where I listen for connections  by default
		
	/**
	 * structure to keep track of clients I think are in the current session with me
	 * There's a much better way to this in retrospect.
	 */
	List <clientClient> sessionMembers = new ArrayList <clientClient>();
	
	private static clientClient self ;
	static String name="test client";// override out of testing
	protected static String encoding = "UTF-8"; // character encoding for messages
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		parseargs(args); // arguments supplied on the commandline invocation

		//myServerHostName = java.net.InetAddress.getLocalHost().getHostName(); // get my hostname
		//myServerIPAddress = java.net.InetAddress.getLocalHost().getHostAddress().toString(); // my IP address
		System.out.println("Server connection Port: " + serverPort);
		
		//System.out.println("Hostname " + myServerHostName +", IP Address " + myServerIPAddress +", Port " + serverPort);
		System.out.println("Started...");
		
		// create self 
		self = new clientClient();
		self.setNick(name);

		
		
		
	}

	/**
	 * Sends a control message from the chat client to the server
	 * for instance quit, 
	 */
	public void sendMessageToServer() {
		
	}
	
	/** encrypts and sends a message to all of the clients in the
	 * the session
	 */
	public void sendMessageToClients(String message) {
		// convert message from a string to byte for the encryption routine
		byte[] cleartextData = message.getBytes();
		byte[] ciphertext = null;
		// loop over all of the clients in the seesionMembers list
		for (clientClient currClient : sessionMembers ) {
			// and ecncrypt the message
			ciphertext = self.encryptMessage( cleartextData, currClient.getKeyPublic() );
			// send the message to the client through the server
			sendClientMessageThroughServer(currClient,ciphertext);
		}
	}// sendMessageToClients
	
	public void sendClientMessageThroughServer(clientClient client, byte[] ciphertext) {
	
        try {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(myServerHostName, serverPort);

            InputStream inputstream = System.in;
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

            OutputStream outputstream = sslsocket.getOutputStream();
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
            BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);

            // encode message to server 
            String destination = client.getId();
            //convert the encrypted bytes to a string for transmission
            // protocol is destination id is wrapped in parens for simple parsing by server
            String payload = "(" + destination + ")" + new String (ciphertext, encoding);
            bufferedwriter.write(payload );
            bufferedwriter.flush();
        
        } catch (Exception exception) {
            exception.printStackTrace();
        }
		
		
	}
	
	public byte[] receiveClientMessageThroughServer( ){
		
	}
	
	
	/**
	 * takes a stream of bytes that were encoded with my public key,
	 * decryptes them using my private key, and converts the byte array back
	 * into a string to be displayed in the chat window.
	 * @param ciphertext
	 * @param encoding
	 * @return
	 */
	public String receiveClientMessage(byte[] ciphertext, String encoding) {
		String cleartext = "";
		try {
			cleartext = new String( self.decryptMessage( ciphertext ), encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return cleartext;
	}
	
	/**
	 * Command Line Argument Parser - Cheesy	
	 * @param args
	 */
	static void parseargs(String[] args ) {
	
		String argSwitch = ""; // what to look for
		String argValue = ""; // value if any

		// loop through strings found on command line
		for (int i = 0 ; i < args.length; i++ ) {
			argSwitch = "";
			argValue = "";

			// look for our arguments followed by an integer argument
			argSwitch = args[i].toUpperCase();
			argSwitch = argSwitch.substring(1); // strip off leading character
			if (argSwitch.equals("P") ){
				// Check to see if there are any more arguments left and grab the next argument if there is one
				if (i+1 < args.length) {
					argValue = args[i+1];
					// 	check next token to see if it's an integer and if so grab it
					try{
						  // is an integer!
						 serverPort = Integer.parseInt(argValue);
						 if (serverPort < 0 ) { // Port number to connect to
							  System.out.println("The argument to -P(ort) must be a positive integer, not: "+ argValue);
							  System.out.println("");
							  displayUsage();
						 }
					} catch (NumberFormatException e) {
						  System.out.println("The argument supplied to the -P(ort) Switch " + argValue + " is not numeric");
						  System.out.println("");
						  displayUsage();
						}
				} else {
					// throw an error and exit
					  System.out.println("Commandline switch -P(ort) requires a positive numeric argument:  "+ argValue);
					  System.out.println("");
					  displayUsage();
				}
			} else if (argSwitch.equals("H") ){ // Hostanme or IP address
					// Check to see if there are any more arguments left and grab the next argument if there is one
					if (i+1 < args.length) {
						argValue = args[i+1];
						// Error checking on file name and location goes here
						myServerHostName = argValue;
					}
		
					
			} else if (argSwitch.equals("HELP")) {
				displayUsage();
			}// end if block	

		} //for loop

	}// end parseargs
	/**
	 * Displays Command Line USAGE
	 */
	static void displayUsage () {
		System.out.println("");
		System.out.println("");
		System.out.println("USAGE: <JAVA>  -cp . <JAVARGS> groupChatClient -H <ServerHostname>  -P <nnn> -HELP");
		System.out.println("");
		System.out.println("-P <number> (1 - 65535)  [OPTIONAL] port number to listen on.");
	    System.out.println("");
		System.out.println("-H ServerHostname  the name or IP Address of the secureGroupChat Server");
	    System.out.println("");
		System.out.println("-HELP   prints this help screen on the console.");
		System.out.println("");
		System.exit(0); // force program termination
	}
	
}
