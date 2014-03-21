/**
 * base client object 
 * contains field common to all client objects
 * 
 */

/**
 * @author kenfox
 *
 */
public class clientObject {

	protected String nick;
	protected String id;
	protected boolean accepted;
	
	clientObject() {
		// creates a new id string for a client object
		this.id = new ID().toString();
	
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	
	/**
	 * @return the accepted
	 */
	public boolean isAccepted() {
		return accepted;
	}

	/**
	 * @param accepted the accepted to set
	 */
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	};
	
	
}
