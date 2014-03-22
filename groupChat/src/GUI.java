import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String server;
	public GUI() {
        	// set up window
	       setTitle("Simple example");
	       setSize(550, 500);
	       setLocationRelativeTo(null);
	       setDefaultCloseOperation(EXIT_ON_CLOSE); 
	       
	       //Set up chat window
	       final JTextArea chatArea = new JTextArea(40,40);
	       JScrollPane chatScroll = new JScrollPane (chatArea);
	       chatScroll.setPreferredSize(new Dimension(500, 300));
	       chatArea.setLineWrap(true);
	       chatArea.setWrapStyleWord(true);
	       chatArea.setEditable(false);
	       chatScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	       
	       //set up chat input box
	       final JTextField chatInput = new JTextField(40);
	       
	       
	       
	       ActionListener AL = new ActionListener(){
	    	   public void actionPerformed(ActionEvent event){
	    		   String input = chatInput.getText();
	    		   
	    		   if(input != null){
	    			   
	    			   
	    			   chatArea.append(input+"\n");
	    			   /*do stuff with input
	    			   
	    			   chatArea.append("");
	    			   chatArea.setCaretPosition(chatArea.getDocument().getLength());
						*/
						
	    			   //clear box
	    			   chatInput.setText("");
	    		   }
	    	   }
	       };
	       
	       chatInput.addActionListener(AL);
	       JButton button = new JButton("Send");
	       button.addActionListener(AL);
	       
	       name = JOptionPane.showInputDialog("Enter Name: ");
	       server = JOptionPane.showInputDialog("Enter Server: ");
	       this.setLayout(new FlowLayout());
	       this.add(chatScroll, SwingConstants.CENTER);
	       this.add(chatInput);
	       this.add(button);




	    }
	
	public static void main(String[] args){
		
		
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI ex = new GUI();
                ex.setVisible(true);
            }
        });
		
	}
	
}
