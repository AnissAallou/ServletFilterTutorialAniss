package org.o7planning.tutorial.servletfilter.conn;

import java.io.IOException;
import java.sql.Connection;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
 
@WebFilter(urlPatterns = { "/*" })
public class JDBCFilter implements Filter {
	
	 public JDBCFilter() {
	    }
	 
	    @Override
	    public void init(FilterConfig fConfig) throws ServletException {
	 
	    }
	 
	    @Override
	    public void destroy() {
	 
	    }
	 
	    @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	            throws IOException, ServletException {
	 
	        HttpServletRequest req = (HttpServletRequest) request;
	 
	        // 
	        String servletPath = req.getServletPath();
	 
	        // Ouvrez uniquement Connection (Connexion) pour des demandes ayant le chemin particulier
	        // (Par exemple: des chemins vont envers servlet, jsp, ..)
	        // Évitez le cas d'ouverture Connection pour des demandes normales
	        // (Par exemple: image, css, javascript,... )
	        if (servletPath.contains("/specialPath1") || servletPath.contains("/specialPath2")) {
	            Connection conn = null;
	            try {
	                // Créez l'objet Connection pour se connecter à la base de données.
	                conn = ConnectionUtils.getConnection();
	                // Définissez automatiquement commit = false.
	                conn.setAutoCommit(false);
	 
	                // Enregistrez la connexion dans un attribut (attribute) de demande.
	                MyUtils.storeConnection(request, conn);
	 
	                // Permettez la demande de passer à l'élément suivant (filtre ou cible) dans la chaîne.
	                chain.doFilter(request, response);
	 
	                // Appelez commit() pour terminer la transaction (transaction) avec DB.
	                conn.commit();
	            } catch (Exception e) {
	                ConnectionUtils.rollbackQuietly(conn);
	                throw new ServletException();
	            } finally {
	                ConnectionUtils.closeQuietly(conn);
	            }
	        }
	        // Pour des demandes normales.
	        else {
	            // Autorisez de la demande de passer à l'élément suivant (Passer ce filtre).
	            chain.doFilter(request, response);
	        }
	 
	    }

}
