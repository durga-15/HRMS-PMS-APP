package com.hrms.pms.pms_app.pms.security;

import com.hrms.pms.pms_app.pms.helper.UserHelper;
import com.hrms.pms.pms_app.pms.repositories.UserRepository;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String header = request.getHeader("Authorization");
//        logger.info("Authorization header : {}", header);
//        if(header.startsWith("Bearer ")){
//
//            String token = header.substring(7);
//
//
//
//            try {
//
//                if(!jwtService.isAccessToken(token)){
//                    filterChain.doFilter(request, response);
//                    return;
//                }
//
//                Jws<Claims> parse = jwtService.parse(token);
//                Claims payload = parse.getPayload();
//                String userId = payload.getSubject();
//                UUID userUuid = UserHelper.parseUUID(userId);
//
//                userRepository.findById(userUuid)
//                        .ifPresent(user -> {
//
//                            if(!user.isStatus()){
//                                try {
//                                    filterChain.doFilter(request, response);
//                                } catch (IOException | ServletException e) {
//                                    throw new RuntimeException(e);
//                                }
//                                return;
//                            }
////                            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
//                            if (user.isStatus()){
//                                List<GrantedAuthority> authorities = user.getRoles() == null ? List.of() : user.getRoles().stream()
//                                        .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//
//                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                                        user.getEmail(),
//                                        null,
//                                        authorities
//                                );
//
//                                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                                //To set the authentication to security context
//                                if (SecurityContextHolder.getContext().getAuthentication() == null)
//                                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                            }
//                        });
//
//
//
//            }catch (ExpiredJwtException e){
//                request.setAttribute("error", "Token Expired");
////                e.printStackTrace();
//            }catch (MalformedJwtException e){
//                request.setAttribute("error", "Invalid Token");
////                e.printStackTrace();
//            } catch (Exception e){
//                request.setAttribute("error", "Invalid Token");
////                e.printStackTrace();
//            }
//        }
//
//        filterChain.doFilter(request, response);

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            try {

                if (!jwtService.isAccessToken(token)) {
                    filterChain.doFilter(request, response);
                    return;
                }

                Claims payload = jwtService.parse(token).getPayload();
                UUID userUuid = UserHelper.parseUUID(payload.getSubject());

                userRepository.findById(userUuid).ifPresent(user -> {

                    if (!user.isStatus()) return;

                    Object rolesObj = payload.get("roles");

                    List<String> rolesFromToken = new ArrayList<>();

                    if (rolesObj instanceof List<?>) {
                        for (Object role : (List<?>) rolesObj) {
                            rolesFromToken.add(role.toString());
                        }
                    }

                    List<GrantedAuthority> authorities = rolesFromToken.stream()
                            .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userUuid.toString(),
                                    null,
                                    authorities
                            );

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });

            } catch (ExpiredJwtException e) {
                request.setAttribute("error", "Token Expired");
            } catch (Exception e) {
                request.setAttribute("error", "Invalid Token");
            }
        }
        filterChain.doFilter(request, response);
    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return request.getRequestURI().startsWith("/api/auth");
//    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Skip JWT filter for OPTIONS and auth endpoints
        return "OPTIONS".equalsIgnoreCase(request.getMethod()) || request.getRequestURI().startsWith("/api/auth");
    }
}
