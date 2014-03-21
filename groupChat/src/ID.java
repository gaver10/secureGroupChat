/**
 * ID class
 * Generates a unique identifier and stores it
 * horked from my CS350 survey project
 * @author kenfox
 *  
 */

import java.io.Serializable;
import java.util.Random;

	public class ID implements Serializable {
		/**
		 * Supports Serialization
		 */
		private static final long serialVersionUID = 1L;
		
		private long id ;
		
		ID () {
			// this delay was added because Automated testing was returning
			// multiple instances of the same id value. 
			int i = 10;
			try {
			    Thread.sleep(i);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}	
	
			// perhaps this needs to be more random - not sure how predictable seeding 
			// with the system time will be IRL. 
			Random rnd = new Random();
			rnd.setSeed(System.currentTimeMillis());
			id = rnd.nextInt();
			
		}
		
		public long getID () {
			return id;
		}
		

		public long get_serialVersionUID() {
			return serialVersionUID;
		}
		public String toString() {
			String x = "" + id;
			return x;
		}
		public boolean equals (ID tid) {
			if (tid.getID() == id ) return true;
			return false;
		}
		
		public int hashCode() {
			int result = (int)(id ^ (id>>32));
			if (result <0 ) result = result *-1;
			return result;
		}
		
}
