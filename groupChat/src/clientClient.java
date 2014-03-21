/**
 * This is the extension of the clientObject that is used in the chat client 
 * because it contains fields that are not common to both the client and server
 * programs.
 * 
 *  
 */

/**
 * @author kenfox
 *
 */
public class clientClient extends clientObject {
	private String keyPrivate;
	private String keyPublic;
	// the superclass has the client's nick name, id, and accepted status
	
	
	clientClient() {
		super();
	}

	/**
	 * @return the keyPrivate
	 */
	protected String getKeyPrivate() {
		return keyPrivate;
	}

	/**
	 * @param keyPrivate the keyPrivate to set
	 */
	protected void setKeyPrivate(String keyPrivate) {
		this.keyPrivate = keyPrivate;
	}

	/**
	 * @return the keyPublic
	 */
	protected String getKeyPublic() {
		return keyPublic;
	}

	/**
	 * @param keyPublic the keyPublic to set
	 */
	protected void setKeyPublic(String keyPublic) {
		this.keyPublic = keyPublic;
	};
	
	
	
}
