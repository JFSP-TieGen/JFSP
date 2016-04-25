package server;

import entity.User;

public interface UserBiz {
	public boolean verifyUsername(String userName);
	public boolean login(User user);
	public void signUp(User user);
	public void signOut();
}
