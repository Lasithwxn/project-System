package com.inventory.filter;

import com.inventory.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filter for authentication and role-based access control.
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        // Allow access to login, auth servlet, and static resources
        if (requestURI.endsWith("login.jsp") || requestURI.contains("/auth") ||
            requestURI.contains("/css/") || requestURI.contains("/js/") || requestURI.contains("/images/")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        User loggedInUser = (session != null) ? (User) session.getAttribute("loggedInUser") : null;

        if (loggedInUser == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/jsp/login.jsp");
            return;
        }

        // Role-based access control for admin-only pages
        if (requestURI.contains("users.jsp") || requestURI.contains("/users")) {
            if (loggedInUser.getRole() != User.Role.ADMIN) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. Admin role required.");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
