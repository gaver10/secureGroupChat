/**
 * CS475 Secure Group Chat Server
 * Ken Fox, Gavin Rapp, Andrea Pavia
 * 
 * privs container root object for maintaining client instance privleges
 * This is probably not the best thought out set of contents
 */

/**
 * @author kenfox
 *
 */
public class privs {
	private boolean sessionOriginator;
	private boolean sessionOwner;
	
	
	privs () {
		sessionOriginator = false; // default to false
		sessionOwner = false; // default to false
	}


	/**
	 * @return the sessionOwner
	 */
	protected boolean isSessionOwner() {
		return sessionOwner;
	}


	/**
	 * @param sessionOwner the sessionOwner to set
	 */
	protected void setSessionOwner(boolean sessionOwner) {
		this.sessionOwner = sessionOwner;
	}


	/**
	 * @return the sessionOriginator
	 */
	protected boolean isSessionOriginator() {
		return sessionOriginator;
	}


	/**
	 * @param sessionOriginator the sessionOriginator to set
	 */
	protected void setSessionOriginator(boolean sessionOriginator) {
		this.sessionOriginator = sessionOriginator;
	}
	
	
}
