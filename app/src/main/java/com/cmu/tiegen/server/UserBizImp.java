package server;

import entity.User;
import adapter.proxyUserAdapter;

public class UserBizImp extends proxyUserAdapter implements UserBiz{

	@Override
	public boolean verifyUsername(String userName) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
    public boolean login(User user){
	 return true;
    }
	

}
