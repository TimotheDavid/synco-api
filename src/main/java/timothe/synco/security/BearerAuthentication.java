package timothe.synco.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import timothe.synco.service.LinkService;
import timothe.synco.service.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@Order(1)
public class BearerAuthentication implements Filter {

    private UserService userService;


    public BearerAuthentication(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String headers = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(userService == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            userService = webApplicationContext.getBean(UserService.class);
        }

        if (headers == null) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "forbidden, add a bearer token");
            filterChain.doFilter(request, response);
            return;
        }

        String token = headers.split(" ")[1];

        if(!userService.getByToken(token)) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "forbidden, token not found");
            filterChain.doFilter(request, response);
            return;
        }

        if(userService.getTokenExpired(token)) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "forbidden, token has expired");
            filterChain.doFilter(request, response);
            return;
        }
        response.setHeader(HttpHeaders.AUTHORIZATION, headers);

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
