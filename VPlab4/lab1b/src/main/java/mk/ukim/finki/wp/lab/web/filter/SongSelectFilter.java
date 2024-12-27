package mk.ukim.finki.wp.lab.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;

@WebFilter(filterName = "select-song-first-filter", urlPatterns = "/artist")
public class SongSelectFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(request.getParameter("songId")!=null)
            filterChain.doFilter(servletRequest,servletResponse);
        else
            response.sendRedirect("/listSongs");
    }
}
