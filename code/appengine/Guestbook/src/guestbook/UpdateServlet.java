package guestbook;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateServlet extends HttpServlet
{	
	private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());

	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		if (!Boolean.parseBoolean(req.getHeader("X-AppEngine-Cron"))){
			resp.getWriter().println("only cron call this page !");
			return;
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			String query = "select from " + Greeting.class.getName();
			List<Greeting> greetings = (List<Greeting>) pm.newQuery(query).execute();
			for (Greeting g : greetings) {
				g.setGold(g.getGold()+1);
			}
			System.out.println("update " + greetings.size());
		}catch(Exception err){
			log.warning(err.getMessage());
		}finally{
			pm.close();
		}
	}
	
}
