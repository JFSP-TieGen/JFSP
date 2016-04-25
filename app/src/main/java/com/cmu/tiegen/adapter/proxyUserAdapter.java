package adapter;

import entity.User;

public abstract class proxyUserAdapter {
	public boolean verifyUserName(String userName){
		return true;
	}
	public boolean login(User user){
		return true;
	}
	public void signUp(User user){
		
	}
	public void signOut(){
		
	}
	
}
