package game.sfs;

import game.service.user.UserProfile;

import com.smartfoxserver.bitswarm.sessions.ISession;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.xingcloud.framework.orm.session.PersistenceSession;
import com.xingcloud.framework.orm.session.SessionFactory;
import com.xingcloud.service.user.UserFactory;

public class SFSLevelUpRequestHandler extends BaseClientRequestHandler
{
	public void handleClientRequest(User user, ISFSObject sfsObject)
	{
		//init PersistenceSession
		ISession session = user.getSession();
		PersistenceSession persistenceSession = (PersistenceSession) session.getProperty("persistenceSession");
		if(persistenceSession == null){
			persistenceSession = SessionFactory.openSession();
			session.setProperty("persistenceSession", persistenceSession);
		}
		//check uid
		if(!sfsObject.containsKey("uid")){
			//TODO throw exception or return error
		}
		//update UserProfile
		try {
			//get UserProfile
			UserProfile userProfile = (UserProfile) UserFactory.getInstance().get(
					persistenceSession, sfsObject.getUtfString("uid"));
			if(userProfile == null){
				//TODO throw exception or return error
			}
//			userProfile.setLevel(userProfile.getLevel() + 1);
			persistenceSession.put(userProfile);
			persistenceSession.flush();
			session.setProperty("persistenceSession", persistenceSession);
		} catch (Exception e) {
			//TODO throw exception or return error
		}
	} 
}