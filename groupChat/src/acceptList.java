import java.util.ArrayList;

/**
 * * CS475 Secure Group Chat Server
 * Ken Fox, Gavin Rapp, Andrea Pavia
 * 
 * Maintains a list of cliens in the system that have approved the entry 
 * of a new member to an existing chat session. Skipped when new chat room/session is created
 * 
 * an approving client is a client object whose "approval" is needed for another client (new user)
 * to join this chat session. 
 * 
 */

/**
 * @author kenfox
 *
 */
public class acceptList {
	serverClient client;
	private ArrayList<serverClient> pending;
	private ArrayList<serverClient> accepted;
	
	/**
	 * creates a new acceptance list for a client
	 * storing the client for reference purposes 
	 * @param client
	 */
	acceptList(serverClient client ){
		this.client = client;
		pending = new ArrayList<serverClient>();
		accepted = new ArrayList<serverClient>();
	};

	/**
	 * when someone OKs this person to be a participant int he chat session this
	 * gets executed
	 * @param client
	 */
	public void peerApprove (serverClient client) {
		// lookup client in the pending list and move it to the accepted list
		accepted.add(client);
		pending.remove(client);
	}
	
	/**
	 * check to see whether this client has been approved yet.
	 * @return boolean value - true if approved, false if not
	 * 
	 */
	public boolean isApproved(){
		if ( pending.isEmpty() && !accepted.isEmpty())
			return true;
			else return false;
	}
	
	/**
	 * puts an approving client object into the list of clients which need to approve a 
	 * new person
	 * 
	 * @param client
	 */
	public void addPending (serverClient client) {
		// check to see if this client is in the list already before removing
		int indexCheck = pending.indexOf(client);
		if (indexCheck != -1) pending.add(client);
	}
	
	/**
	 * removes an approving  client from the pending list for an approval -- would be used when 
	 * someone who has not approved a person leaves the chat room session
	 * @param client
	 */
	public void removePending(serverClient client){
		// cleans out a client id incase it has been added more than once.
		// removes the 
		while ( pending.indexOf(client) > -1 ) {
				pending.remove(client);
			}
	}
	
	/**
	 * puts a client object into the list of clients that have approved a 
	 * new person
	 * @param client
	 */
	public void addAccepted (serverClient client) {
		// check to see if this client is in the list already before removing
		int indexCheck = accepted.indexOf(client);
		if (indexCheck != -1) accepted.add(client);
		
	}
	
	/**
	 * removes a client from the Accepted list for an approval -- would be used when 
	 * someone who has not approved a person leaves the chat room session
	 * @param client
	 */
	public void removeAccepted(serverClient client){
		// cleans out a client id incase it has been added more than once.
		// removes the 
		while ( accepted.indexOf(client) > -1 ) {
				accepted.remove(client);
			}
		
	}
	
}
