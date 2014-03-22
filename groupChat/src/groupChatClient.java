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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		parseargs(args); // arguments supplied on the commandline invocation

		//myServerHostName = java.net.InetAddress.getLocalHost().getHostName(); // get my hostname
		//myServerIPAddress = java.net.InetAddress.getLocalHost().getHostAddress().toString(); // my IP address
		System.out.println("Server connection Port: " + serverPort);
		
		//System.out.println("Hostname " + myServerHostName +", IP Address " + myServerIPAddress +", Port " + serverPort);
		System.out.println("Started...");
		
		
		
		
		
		
	}

	/**
	 * Sends a control message from the chat client to the server
	 * for instance quit, 
	 */
	public void sendMessageToServer() {
		
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
