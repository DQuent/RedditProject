package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.api.datastore.Query;


public class GetVotedTopicsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession s = req.getSession();
		resp.getWriter().println("Topics sur lequels "+ s.getAttribute("currentUser") +" a voté");

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Filter propertyFilter = new FilterPredicate("voters", FilterOperator.IN, s.getAttribute("currentUser"));
		Query q = new Query("Topic").setFilter(propertyFilter);
		PreparedQuery pq= ds.prepare(q);
	    List<Entity> results=pq.asList(FetchOptions.Builder.withLimit(10));
		for (Entity r : results) {
			resp.getWriter().println("<li>:"+r);
		}
		
	}
}
