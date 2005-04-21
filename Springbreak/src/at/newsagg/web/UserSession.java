package at.newsagg.web;


import at.newsagg.model.User;

/**
 * @author Szabolcs Rozsnyai
 * @$Id: UserSession.java,v 1.1 2005/04/21 19:41:09 vecego Exp $
 */
public class UserSession {

	private User userData;
	
	public UserSession (User user) {
		this.userData = user;
	}
	
	/**
	 * @return Returns the userData.
	 */
	public User getUserData() {
		return userData;
	}
	/**
	 * @param userData The userData to set.
	 */
	public void setUserData(User userData) {
		this.userData = userData;
	}
}
