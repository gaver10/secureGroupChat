/**
 * CS475 Secure Group Chat
 * Ken Fox, Gavin Rapp, Andrea Pavia
 * 
 * This is the extension of the clientObject that is used in the chat server 
 * because it contains fields that are not common to both the client and server
 * programs. 
 */

/**
 * @author kenfox
 *
 */
public class serverClient extends clientObject {
	protected boolean online;
	// the superclass has the client's nick name, id, and accepted status

	serverClient () {
		super();
	}

	/**
	 * @return the online
	 */
	public boolean isOnline() {
		return online;
	}

	/**
	 * @param online the online to set
	 */
	public void setOnline(boolean online) {
		this.online = online;
	}
	
}
