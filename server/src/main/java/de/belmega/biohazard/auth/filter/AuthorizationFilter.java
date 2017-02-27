package de.belmega.biohazard.auth.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static de.belmega.biohazard.auth.jsf.LoginBean.ATTRIBUTE_USER;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter implements Filter {

    public AuthorizationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession(false);

        String requestURI = httpServletRequest.getRequestURI();
        if (isPlayerPageAndAuthorized(requestURI, session)
                || isAdminPageAndAuthorized(requestURI, session)
                || isPublicPage(requestURI))
            chain.doFilter(request, response);
        else
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.xhtml");
    }

    /**
     * Check if page is accessible for role ApplicationRole.ADMIN and user has that role.
     */
    private boolean isAdminPageAndAuthorized(String requestURI, HttpSession session) {
        boolean isAdminPage = requestURI.contains("/admin/");
        return false;
    }

    /**
     * Check if page is accessible without login.
     */
    private boolean isPublicPage(String requestURI) {
        return requestURI.contains("/login.xhtml")
                || requestURI.contains("/public/")
                || requestURI.contains("javax.faces.resource");
    }

    /**
     * Check if page is accessible for role ApplicationRole.PLAYER and user has that role.
     */
    private boolean isPlayerPageAndAuthorized(String requestURI, HttpSession session) {
        boolean isPlayerPage = requestURI.contains("/player/");

        boolean userLoggedIn = session != null && session.getAttribute(ATTRIBUTE_USER) != null;
        return userLoggedIn && isPlayerPage;
    }

    @Override
    public void destroy() {

    }
}
