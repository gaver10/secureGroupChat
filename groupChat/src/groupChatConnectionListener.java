/**
 * This is a class that will run as its own thread and listen on the
 * inbound connections port, spawning new Client objects as needed.
 * 
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import org.omg.CORBA.portable.InputStream;




/**
 * @author kenfox
 *
 */
public class groupChatConnectionListener implements Runnable{
	private boolean EXIT = false; 
	private int serverPort; 
	// Master Tables
	
	private List<Thread> threads = new ArrayList<Thread>();

	/**
	 * groupChatConnectionListener Constructor
	 * 
	 * @param port port number this server listens on
	 */
	groupChatConnectionListener( int port) {
		this.serverPort = port;
	}
	
	@Override
	public void run() {
  	  while (! EXIT) {
	      try {
              @SuppressWarnings("resource")
              SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
              SSLServerSocket sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(serverPort);
              SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();

              InputStream inputstream = (InputStream) sslsocket.getInputStream();
              InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
              BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

              String string = null;
              
              while ((string = bufferedreader.readLine()) != null) {
                  System.out.println(string);
                  System.out.flush();   
              
                
              
         //     Thread t = new Thread(c);
         //     threads.add(t);
         //     t.start();
              }
          } catch (IOException e) {
        	//  logger.errorMessage("IO Exception caught when trying to listen on port "
             //     + serverPort + " or listening for a connection");
        	 // logger.errorMessage(e.getMessage());
        	  e.printStackTrace(); 
          } catch (Exception e) {
          	//  logger.errorMessage("General Exception caught when trying to listen on port "
              //     + serverPort + " or listening for a connection");
         	 // logger.errorMessage(e.getMessage());
        	  e.printStackTrace();
          }
  	  }//while 
  	  
  	  // Join the threads for cleanup
  	  for (Thread t : threads){
  		  try {
  			  t.join();
  		  } catch (InterruptedException e) { 
  			//  logger.errorMessage("Exception caught when trying to join a thread ");
        	//  logger.errorMessage(e.getMessage());
        	  e.printStackTrace();
  			  
  		  }
  	  }	// for   
  	  
  
	}// run()

	Client createClient(Klogger logger, Socket socket){
		Client c = new Client(logger, socket);
		c.setClientList(clientList);
		c.setClientsByNick(clientsByNick) ;
		c.setClientsByUser(clientsByUser) ;
		c.setClientsByHostName(clientsByHostName);
		c.setSocket(socket);
		return c;
	}
	
	
	
	public void shutdown(){
		EXIT = true;
	}

	/**
	 * @return the logger
	 */
	public Klogger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Klogger logger) {
		this.logger = logger;
	}

	/**
	 * @return the eXIT
	 */
	public boolean isEXIT() {
		return EXIT;
	}

	/**
	 * @param eXIT the eXIT to set
	 */
	public void setEXIT(boolean eXIT) {
		EXIT = eXIT;
	}

	/**
	 * @return the clientList
	 */
	public List<Client> getClientList() {
		return clientList;
	}

	/**
	 * @param clientList the clientList to set
	 */
	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}

	/**
	 * @return the clientsByNick
	 */
	public Map<String, Client> getClientsByNick() {
		return clientsByNick;
	}

	/**
	 * @param clientsByNick the clientsByNick to set
	 */
	public void setClientsByNick(Map<String, Client> clientsByNick) {
		this.clientsByNick = clientsByNick;
	}

	/**
	 * @return the clientsByUser
	 */
	public Map<String, Client> getClientsByUser() {
		return clientsByUser;
	}

	/**
	 * @param clientsByUser the clientsByUser to set
	 */
	public void setClientsByUser(Map<String, Client> clientsByUser) {
		this.clientsByUser = clientsByUser;
	}

	/**
	 * @return the clientsByHostName
	 */
	public Map<String, Client> getClientsByHostName() {
		return clientsByHostName;
	}

	/**
	 * @param clientsByHostName the clientsByHostName to set
	 */
	public void setClientsByHostName(Map<String, Client> clientsByHostName) {
		this.clientsByHostName = clientsByHostName;
	}
	
	
	

}
