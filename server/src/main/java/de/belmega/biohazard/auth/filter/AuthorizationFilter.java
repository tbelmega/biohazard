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
        if (requestURI.indexOf("/login.xhtml") >= 0
                || (session != null && session.getAttribute(ATTRIBUTE_USER) != null)
                || requestURI.indexOf("/public/") >= 0
                || requestURI.contains("javax.faces.resource"))
            chain.doFilter(request, response);
        else
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.xhtml");
    }

    @Override
    public void destroy() {

    }
}
