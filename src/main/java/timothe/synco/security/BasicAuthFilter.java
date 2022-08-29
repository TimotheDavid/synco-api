package timothe.synco.security;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import timothe.synco.model.Link;
import timothe.synco.service.ClickedService;
import timothe.synco.service.LinkService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@NoArgsConstructor
@Order(2)
public class BasicAuthFilter implements Filter {

    private LinkService linkService;

    private ClickedService clickedService;
    public BasicAuthFilter(LinkService linkService, ClickedService clickedService) {
        this.linkService = linkService;
        this.clickedService = clickedService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        if(linkService == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            linkService = webApplicationContext.getBean(LinkService.class);
        }

        if(clickedService == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            clickedService = webApplicationContext.getBean(ClickedService.class);
        }

        if(request.getRequestURL().toString().contains("verify")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        int lastSlash = request.getRequestURL().lastIndexOf("/");
        String parseCode = request.getRequestURL().substring(lastSlash + 1);

        Link linkObject = Link.builder().shortUrl(parseCode).build();
        Link link = linkService.getLinkByShortUrlWithoutUser(linkObject);


        if(link != null && link.getPassword() != null) {

            response.setHeader(HttpHeaders.LOCATION, link.getLoginUrl());

            String url = null;
            url = link.getLoginUrl() + "?name=" + link.getShortUrl();
            response.sendRedirect(url);
            return;

        }

        filterChain.doFilter(request, servletResponse);
    }

}
