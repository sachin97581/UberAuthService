package com.example.uberAuthService.filters;

import com.example.uberAuthService.service.JwtService;
import com.example.uberAuthService.service.UserDetailServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    private final UserDetailServiceImp userDetailService;

    private final JwtService jwtService;

//    private final RequestMatcher uriMatcher = new AntPathRequestMatcher("api/v1/auth/validate" , HttpMethod.GET.name());

    public JwtAuthFilter(JwtService jwtService , UserDetailServiceImp userDetailService){
        this.jwtService = jwtService;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("JwtToken")) {
                    token = cookie.getValue();
                }
            }
        }

        if (token != null) {
            String email = jwtService.extractEmail(token);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailService.loadUserByUsername(email);
                if (jwtService.validateToken(token, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }


//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = null;
//        if (request.getCookies() != null){
//            for (Cookie cookie : request.getCookies()){
//                if (cookie.getName().equals("JwtToken")){
//                    token = cookie.getValue();
//                }
//            }
//        }
//
//        if (token == null){
//            // user has not provided any token means request has not go forward
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//
//        String email = jwtService.extractEmail(token);
//
//        if (email != null){
//            UserDetails userDetails  = userDetailService.loadUserByUsername(email);
////            if (jwtService.validateToken(token , userDetails.getUsername()));
////            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails , null);
////            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            if (jwtService.validateToken(token, userDetails.getUsername())) {
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//
//        }
//
//        filterChain.doFilter(request , response);
//    }


//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request){
//        RequestMatcher matcher = new NegatedRequestMatcher(uriMatcher);
//        return matcher.matches(request);
//    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) {
//        String path = request.getRequestURI();
//        return path.equals("/api/v1/auth/validate") && request.getMethod().equalsIgnoreCase("GET");
//    }

@Override
protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    return (path.startsWith("/api/v1/auth/signin") ||
            path.startsWith("/api/v1/auth/signup") ||
            path.equals("/api/v1/auth/validate"));
}


}
