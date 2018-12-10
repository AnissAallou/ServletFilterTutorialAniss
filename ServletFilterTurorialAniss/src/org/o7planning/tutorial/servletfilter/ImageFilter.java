package org.o7planning.tutorial.servletfilter;

import java.io.File;
import java.io.IOException;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = { "*.png", "*.jpg", "*.gif" }, initParams = {
        @WebInitParam(name = "notFoundImage", value = "/images/image-not-found.png") })
public class ImageFilter implements Filter {
	
	 private String notFoundImage;
	 
	    public ImageFilter() {
	    }
	 
	    @Override
	    public void init(FilterConfig fConfig) throws ServletException {
	 
	        // ==> /images/image-not-found.png
	        notFoundImage = fConfig.getInitParameter("notFoundImage");
	    }
	 
	    @Override
	    public void destroy() {
	    }
	 
	    @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	            throws IOException, ServletException {
	 
	        HttpServletRequest req = (HttpServletRequest) request;
	 
	        // ==> /images/path/my-image.png
	        // ==> /path1/path2/image.pngs
	        String servletPath = req.getServletPath();
	 
	        // Le chemin d'accès absolu du répertoire racine WebApp (WebContent).
	        String realRootPath = request.getServletContext().getRealPath("");
	 
	        // Le chemin absolu de l'image.
	        String imageRealPath = realRootPath + servletPath;
	 
	        System.out.println("imageRealPath = " + imageRealPath);
	 
	        File file = new File(imageRealPath);
	 
	        // Vérifiez l'existence de l'image.
	        if (file.exists()) {
	 
	            // Permettez la demande de passer à l'élément suivant (filtre ou servlet) dans la chaîne
	            // (Allez vers le fichier de l'image demandé).
	            chain.doFilter(request, response);
	 
	        } else if (!servletPath.equals(this.notFoundImage)) {
	 
	            // Redirect (Réorienter) vers le fichier d'image 'image not found'.
	            HttpServletResponse resp = (HttpServletResponse) response;
	 
	            // ==> /ServletFilterTutorial + /images/image-not-found.png
	            resp.sendRedirect(req.getContextPath() + this.notFoundImage);
	 
	        }
	 
	    }

}
