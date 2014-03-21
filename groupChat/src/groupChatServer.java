import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * CS475 Secure Group Chat Server
 * Ken Fox, Gavin Rapp, Andrea Pavia
 * 
 */

/**
 * @author kenfox
 *
 */
public class groupChatServer {
	
	
	// hashmap of threads
	// hashmap of sessions 
	HashMap <String, serverSession> chatSessions = new HashMap<String, serverSession>();
	
	// arraylist of listeners
	
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
	 * Data structure for parent to manage threads with
	 * because I instantiate the various objects with arguments and so
	 * forth before I make them into threads and start them 
	 */
	private static volatile List<Thread> threads = new ArrayList<Thread>();
	

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		myServerHostName = java.net.InetAddress.getLocalHost().getHostName(); // get my hostname
		myServerIPAddress = java.net.InetAddress.getLocalHost().getHostAddress().toString(); // my IP address
		System.out.println("Hostname " + myServerHostName +", IP Address " + myServerIPAddress +", Port " + serverPort);
		System.out.println("Started...");
		
		//threads.add(new Thread(srv) ) ; 
		
		/**
		 * Start the server threads
		 */
		for (Thread t : threads) {
			t.start();
		}
	System.out.println("Server shutdown completed");
	
	}

	// listen on a socket
	public void waitForConnection() {
		
	}
	
	public void clientConnect () {
		
	// client connects, create new client instance, assign ID.
		serverClient client = new serverClient();
		client.setOnline(false);
	// while not connected	
		while (! client.isOnline()  ) {
			// ask for Session Name
			String sessionName = "";
			String sessionId = "";
			// does Session exist?
				sessionId = lookupSessionID(sessionName);
				if (! sessionId.isEmpty() ) {
				// yes 
					 clientEnterExistingSession();
					 
				} else {	
				// no
					// need a timeout to prevent DOS's
					// Ask client "do you want to create a new Session with name?"
					String question = "Do you want to create a new Session named "+ sessionName + "(Y/N) ?" ;
					String answer = serverQuerysClient (client, question );
					
					while ( ! ( answer.toUpperCase().equals("Y") ||
							 answer.toUpperCase().equals("N") ) ) { 
						answer = serverQuerysClient (client, question );
						// need a timeout to prevent DOS's
					
					}
					if ( answer.toUpperCase().equals("Y") ) {
						clientEnterNewSession();
						client.setOnline(true);
					}
				}	
		}
		
	}
	
	public void clientEnterExistingSession () {
		// set up a countdown timer to disconect unconnected client after some period of time
		// get session information
		// create a new acceptance list object
		// get nick from client
		// check for existing nick
		// if nick in use, ask for another nick
		// get new user's public key
		// populate acceptance list by iterating over clients already in session and adding
		// them to the pending list.
		// send a message to all clients in the pending list telling them 
		//    user with nick "nick" and public key "KY" wants to join session.
		// wait for responses from existing clients or timeout
		// if time out, dispose of new client object and disconnect them
		// if accepted add them to the session, and set thier online status to true
			// addClientToSession(client);
			// client.setOnline(true);
		
		
	}
	
	
	public void clientEnterNewSession() {
		// anyone can create a new room
		
		
		
	}
	
	public String lookupSessionID (String name) {
		String response = "";
		
		
		
		return response;
		
	}
	
	
	
	/**
	 * routine to ask the client a question and get a response
	 * 
	 * @return
	 */
	public String serverQuerysClient (serverClient client, String message) {
		String response = "";
		// send query to client, wait for response, return response
		
		
		return response;
	}
	
	/** 
	 * Send a status message to a given client
	 * @param client
	 * @param message
	 */
	public void serverSendMessageToClient(serverClient client, String message){
		
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
		System.out.println("USAGE: <JAVA> <JAVARGS> groupChatServer <ServerHostname>  -P <nnn> -HELP");
		System.out.println("");
		System.out.println("-P <number> (1 - 65535)  [OPTIONAL] post number to connect to IRC server on.");
	    System.out.println("");
		System.out.println("ServerHostname  the name or IP Adress of the IRC Server");
	    System.out.println("");
		System.out.println("-HELP   prints this help screen on the console.");
		System.out.println("");
		System.exit(0); // force program termination
	}

	
}
