/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		removeAll();
		noImage = new GCompound();
		GRect rect = new GRect(IMAGE_WIDTH,IMAGE_HEIGHT);
		noImage.add(rect,-IMAGE_WIDTH/2,-IMAGE_HEIGHT/2);
		
		GLabel label = new GLabel("No Image");
		label.setFont(PROFILE_IMAGE_FONT);
		noImage.add(label, -label.getWidth()/2, label.getAscent()/2);
		
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		// You fill this in
		GObject hit = getElementAt(getWidth()/2, getHeight()-BOTTOM_MESSAGE_MARGIN);
		if(hit!=null){
			remove(hit);
		}
		GLabel label = new GLabel(msg);
		label.setFont(MESSAGE_FONT);
		label.setLocation((getWidth()-label.getWidth())/2, getHeight()-BOTTOM_MESSAGE_MARGIN-label.getAscent()/2);
		add(label);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		if(profile!=null) {
			GLabel nameLabel = new GLabel(profile.getName());
			nameLabel.setFont(PROFILE_NAME_FONT);
			add(nameLabel, LEFT_MARGIN, TOP_MARGIN+nameLabel.getAscent());
			
			GImage image = profile.getImage();
			displayImage(image, TOP_MARGIN+nameLabel.getAscent());
			
			String status = profile.getStatus();
			displayStatus(status, profile.getName(), TOP_MARGIN+nameLabel.getAscent()+IMAGE_MARGIN+IMAGE_HEIGHT);
			
			Iterator<String> friends = profile.getFriends();
			displayFriends(friends, TOP_MARGIN+nameLabel.getAscent()+IMAGE_MARGIN);
		}
	}

	private void displayImage(GImage image, double margin) {
		if (image == null) {
			add(noImage, LEFT_MARGIN+IMAGE_WIDTH/2, margin+IMAGE_MARGIN+IMAGE_HEIGHT/2);
		} else {
			image.scale(IMAGE_WIDTH/image.getWidth(), IMAGE_HEIGHT/image.getHeight());
			add(image, LEFT_MARGIN, margin+IMAGE_MARGIN);
		}
	}
	
	private void displayStatus(String status, String name, double margin) {
		GLabel statusLabel;
		if (status.equals("")) {
			statusLabel = new GLabel("No current status");
		} else {
			statusLabel = new GLabel(name+" is "+status);	
		}
		statusLabel.setFont(PROFILE_STATUS_FONT);
		add(statusLabel, LEFT_MARGIN, margin+STATUS_MARGIN+statusLabel.getAscent());
	}
	
	private void displayFriends(Iterator<String> iter, double margin) {
		GLabel label = new GLabel("Friends:");
		label.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(label, getWidth()/2, margin);
		margin = margin+label.getAscent();
		
		while(iter.hasNext()) {
			String str = iter.next();
			GLabel friend = new GLabel(str);
			friend.setFont(PROFILE_FRIEND_FONT);
			margin = margin + friend.getAscent();
			add(friend, getWidth()/2, margin);
		}
	}
	
	private GCompound noImage;
	
}
