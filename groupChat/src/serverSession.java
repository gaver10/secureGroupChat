/**
 * CS475 Secure Group Chat Server
 * Ken Fox, Gavin Rapp, Andrea Pavia
 * 
 * server session object
 * keeps track of the session by it's name and ID and contains a list of the clients currently in the 
 * chat room or session .
 */

import java.util.HashMap;

/**
 * @author kenfox
 *
 */
public class serverSession {

	protected String name; // test as private if time permits
	protected String id;   // ditto
	protected HashMap<String, serverClient> listClients;
	
	serverSession (){
		// creates a new id string for a client object
		this.id = new ID().toString();
	}

/**
 * add a user to the chat room -- use this if the user has been approved
 * 	
 * @param client the client object to add 
 */
	public void addClientToSession(serverClient client) {
		String id = client.getId();
		if (! listClients.containsKey(id) ) {
			listClients.put(id,client);
		}
	};
	
	public void removeClientFromSession(serverClient client){
		String id = client.getId();
		if (! listClients.containsKey(id) ) {
			listClients.put(id,client);
		}
		
	}
	
	/**
	 * @return the name
	 */
	protected String getName() {
		return name;
	}

	/**
	 * @param name the session name to set
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	protected String getId() {
		return id;
	};
	
	
	
	
}
