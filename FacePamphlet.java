/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		JLabel nameLabel = new JLabel("Name: ");
		add(nameLabel, NORTH);
		
		nameInput.addActionListener(this);
		nameInput.setActionCommand("Add");
		add(nameInput, NORTH);
		
		JButton addButton = new JButton("Add");
		add(addButton, NORTH);
		
		JButton deleteButton = new JButton("Delete");
		add(deleteButton, NORTH);
		
		JButton lookupButton = new JButton("Lookup");
		add(lookupButton, NORTH);
		
		changeStatusInput.addActionListener(this);
		changeStatusInput.setActionCommand("Change Status");
		add(changeStatusInput, WEST);
		JButton changeStatusButton = new JButton("Change Status");
		add(changeStatusButton, WEST);
		
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		
		changePicInput.addActionListener(this);
		changePicInput.setActionCommand("Change Picture");
		add(changePicInput, WEST);
		JButton changePicButton = new JButton("Change Picture");
		add(changePicButton, WEST);
		
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		
		addFriendInput.addActionListener(this);
		addFriendInput.setActionCommand("Add Friend");
		add(addFriendInput, WEST);
		JButton addFriendButton = new JButton("Add Friend");
		add(addFriendButton, WEST);
		
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		
		deleteFriendInput.addActionListener(this);
		deleteFriendInput.setActionCommand("Delete Friend");
		add(deleteFriendInput, WEST);
		JButton deleteFriendButton = new JButton("Delete Friend");
		add(deleteFriendButton, WEST);
		
		addActionListeners();
		
		database = new FacePamphletDatabase();
    }
    
	public FacePamphlet(){
		 graph = new FacePamphletCanvas();
		add(graph);
	}
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    	String cmd = e.getActionCommand();
    	// when addButton is clicked
    	if(cmd.equals("Add")) {
    		String str = nameInput.getText();
    		if(!str.equals("")) {
    			// check whether the database has this profile, if not, create a new profile
    			if (!database.containsProfile(str)) {
    				FacePamphletProfile profile = new FacePamphletProfile(str);
    				database.addProfile(profile);
    				currentProfile = profile;
    				String msg = "New profile created";
    				graph.displayProfile(currentProfile);
    				graph.showMessage(msg);
    			} else {
    				currentProfile = database.getProfile(str);
    				String msg ="Profile for "+str+" already exists!";
    				graph.displayProfile(currentProfile);
    				graph.showMessage(msg);
    			}
    		}
    		
    	//when delete Button is clicked	
    	} else if(cmd.equals("Delete")) {
    		String str = nameInput.getText();
    		if(!str.equals("")) {
    			currentProfile = null;
    			/*
    			 * check whether the database has this profile
    			 * if yes, then delete it
    			 */
    			if (database.containsProfile(str)) {
    				database.deleteProfile(str);
    				String msg = "Profile of: "+str+" deleted";
    				graph.displayProfile(currentProfile);
    				graph.showMessage(msg);
    			} else {
    				String msg = "A profile for "+str+" does not exists";
    				graph.displayProfile(currentProfile);
    				graph.showMessage(msg);
    			}
    		}
    		
    		// when lookup button is clicked 
    	} else if(cmd.equals("Lookup")) {
    		String str = nameInput.getText();
    		if(!str.equals("")) {
    			/*
    			 * check whether the database has this profile
    			 * if contains, display it
    			 */
    			if (database.containsProfile(str)) {
    				currentProfile = database.getProfile(str);
    				String msg = "Displaying "+ str;
    				graph.displayProfile(currentProfile);
    				graph.showMessage(msg);
    			} else {
    				currentProfile = null;
    				String msg = "A profile for "+str+" does not exists";
    				graph.displayProfile(currentProfile);
    				graph.showMessage(msg);
    			}
    		}
    		
    		//when change status button is clicked 
    	} else if(cmd.equals("Change Status")) {
    		String str = changeStatusInput.getText();
    		if (!str.equals("")) {
    			/*
    			 * check whether there is a current profile
    			 * if yes, then change the status
    			 */
    			if (currentProfile!=null) {
    				currentProfile.setStatus(str);
    				String msg = "Status updated to"+ str;
    				graph.displayProfile(currentProfile);
    				graph.showMessage(msg);
    			} else{
    				String msg = "Please select a profile!";
    				graph.displayProfile(currentProfile);
    				graph.showMessage(msg);
    			}
    		}
    		
    		//when clicked change picture button
    	} else if(cmd.equals("Change Picture")) {
    		String str = changePicInput.getText();
    		if (!str.equals("")){
    			/*
    			 * check whether there is a current profile
    			 * if yes, then load the image(if cannot load, prompt an notice)
    			 * then change the profile image
    			 */
    			if(currentProfile!=null){
    				GImage image = null;
    				try{
    					image = new GImage(str);
    					currentProfile.setImage(image);
    					String msg = "Picture updated";
    					graph.displayProfile(currentProfile);
        				graph.showMessage(msg);
    				} catch (ErrorException ex){
    					String msg = "please enter a valid file name!";
    					graph.displayProfile(currentProfile);
        				graph.showMessage(msg);
    				}
    			} else {
    				String msg = "Please select a profile!";
    				graph.displayProfile(currentProfile);
    				graph.showMessage(msg);
    			}
    		}
    		
    		//when add friend button is clicked 
    	} else if(cmd.equals("Add Friend")) {
    		String str = addFriendInput.getText();
    		if(!str.equals("")) {
    			//check whether has a current profile
    			if(currentProfile!=null){
    				//check whether the database contains the name
    				if(database.containsProfile(str)) {
    					//check whether the name is current profile itself
    					if(!str.equals(currentProfile.getName())){
    						addFriends(str);
        					String msg = str + " added as a friend";
        					graph.displayProfile(currentProfile);
            				graph.showMessage(msg);
    					}
    				} else {
    					String msg = "Please enter a valid name!";
    					graph.displayProfile(currentProfile);
        				graph.showMessage(msg);
    				}
    			} else {
    				String msg = "Please select a profile!";
    				graph.displayProfile(currentProfile);
    				graph.showMessage(msg);
    			}
    		}
    		
    		//when delete button is clicked
    	} else if(cmd.equals("Delete Friend")) {
    		String str = deleteFriendInput.getText();
    		if(!str.equals("")) {
    			//check whether has a current profile
    			if(currentProfile!=null){
    				////check whether the database contains the name
    				if(database.containsProfile(str)) {
    					deleteFriends(str);
    					String msg = "Deleted "+str + " as a friend";
    					graph.displayProfile(currentProfile);
        				graph.showMessage(msg);
    				} else {
    					String msg = "Please enter a valid name!";
        				graph.displayProfile(currentProfile);
        				graph.showMessage(msg);
    				}
    			} else {
    				String msg = "Please select a profile!";
    				graph.displayProfile(currentProfile);
    				graph.showMessage(msg);
    			}
    		}
    	}
	}
    
    /**
     * actual add friends procedure, currentProfile will add name to his friends list
     * and profile with "name" will add current profile to his friends list 
     * if they are already friends with each other, then prompt an notice 
     * @param name friends that will be added to currentProfile
     */
    private void addFriends(String name) {
    	if(currentProfile.addFriend(name)) {
    		database.getProfile(name).addFriend(currentProfile.getName());
    		println(name+" are friends with "+currentProfile.getName()+" now");
    	} else {
    		println(name+" are already friends with "+ currentProfile.getName());
    	}
    }
    
    /**
     * actual delete friends procedure, currentProfile will remove name from his friends list
     * and profile with "name" will remove current profile from his friends list 
     * if they are already NOT friends with each other, prompt an notice
     * @param name friends that will be added to currentProfile
     */
    private void deleteFriends(String name) {
    	if(currentProfile.removeFriend(name)) {
    		database.getProfile(name).removeFriend(currentProfile.getName());
    		println(name+" are NOT friends with "+currentProfile.getName()+" now");
    	} else {
    		println(name+" are already NOT friends with "+ currentProfile.getName());
    	}
    }

    /*
     * all the instance variables
     */
    private JTextField nameInput = new JTextField(TEXT_FIELD_SIZE);
    private JTextField changeStatusInput = new JTextField(TEXT_FIELD_SIZE);
    private JTextField changePicInput = new JTextField(TEXT_FIELD_SIZE);
    private JTextField addFriendInput = new JTextField(TEXT_FIELD_SIZE);
    private JTextField deleteFriendInput = new JTextField(TEXT_FIELD_SIZE);
    private FacePamphletCanvas graph;
    private FacePamphletProfile currentProfile = null;
    // database of the program
    private FacePamphletDatabase database;
}
