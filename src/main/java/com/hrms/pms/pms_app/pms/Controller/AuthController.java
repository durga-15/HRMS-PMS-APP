package com.hrms.pms.pms_app.pms.Controller;

import com.hrms.pms.pms_app.pms.dtos.LoginRequest;
import com.hrms.pms.pms_app.pms.dtos.RefreshTokenRequest;
import com.hrms.pms.pms_app.pms.dtos.TokenResponse;
import com.hrms.pms.pms_app.pms.dtos.UserDto;
import com.hrms.pms.pms_app.pms.entities.RefreshToken;
import com.hrms.pms.pms_app.pms.entities.User;
import com.hrms.pms.pms_app.pms.repositories.RefreshTokenRepository;
import com.hrms.pms.pms_app.pms.repositories.UserRepository;
import com.hrms.pms.pms_app.pms.security.CookieService;
import com.hrms.pms.pms_app.pms.security.JwtService;
import com.hrms.pms.pms_app.pms.services.AuthService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final RefreshTokenRepository refreshTokenRepository;

    private final CookieService cookieService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        Authentication authenticate = authenticate(loginRequest);
        User user = userRepository.findByEmailIgnoreCase(loginRequest.email()).orElseThrow(()-> new BadCredentialsException("Invalid Username or Password"));
        if (!user.isStatus()){
            throw new DisabledException("User is Disabled");
        }

        String jti = UUID.randomUUID().toString();
        var refreshTokenOb = RefreshToken.builder()
                .jti(jti)
                .user(user)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(jwtService.getRefreshTtlSeconds()))
                .revoked(false)
                .build();

        //refresh-token save information
        refreshTokenRepository.save(refreshTokenOb);

        //generate access token
        String accessToken = jwtService.generateAccessToken(user);

        //generate refresh token
        String refreshToken = jwtService.generateRefreshToken(user, refreshTokenOb.getJti());

        //use cookie service to attach refresh token in cookie
        cookieService.attachRefreshCookie(response, refreshToken, (int) jwtService.getRefreshTtlSeconds());
        cookieService.noStoreHeaders(response);


        TokenResponse tokenResponse = TokenResponse.of(accessToken, refreshToken, modelMapper.map(user, UserDto.class));
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response){
        readRefreshTokenFromRequest(null, request).ifPresent(token -> {
            try {
                if (jwtService.isRefreshToken(token)){
                    String jti = jwtService.getJti(token);
                    refreshTokenRepository.findByJti(jti).ifPresent(rt -> {
                        rt.setRevoked(true);
                        refreshTokenRepository.save(rt);
                    });
                }
            }catch (JwtException ignored){

            }
        });

        cookieService.clearResponseCookie(response);
        cookieService.noStoreHeaders(response);
        SecurityContextHolder.clearContext();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message", "Logout Successfully"));
    }

    private Authentication authenticate(LoginRequest loginRequest) {

        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        }catch (Exception e){
            throw new BadCredentialsException("Invalid Username or Password !!");

        }
    }

    //access and refresh token renew
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody (required = false) RefreshTokenRequest body,
                                                      HttpServletResponse response,
                                                      HttpServletRequest request){

        String refreshToken = readRefreshTokenFromRequest(body, request).orElseThrow(()-> new BadCredentialsException("Refresh Token is missing"));

        if (!jwtService.isRefreshToken(refreshToken)){
            throw new BadCredentialsException("Invalid Refresh Token Type");
        }

        String jti = jwtService.getJti(refreshToken);
        UUID userId = jwtService.getUserId(refreshToken);

        RefreshToken storedRefreshtoken = refreshTokenRepository.findByJti(jti).orElseThrow(() -> new BadCredentialsException("Refresh Token Not Recognized"));

        if (storedRefreshtoken.isRevoked()){
            throw new BadCredentialsException("Refresh Token Expired or Revoked");
        }

        if (storedRefreshtoken.getExpiresAt().isBefore(Instant.now())){
            throw new BadCredentialsException("Refresh Token Expired");
        }

        if (!storedRefreshtoken.getUser().getId().equals(userId)){
            throw new BadCredentialsException("Refresh token does not belong to this user");
        }

        //rotate Refresh token
        storedRefreshtoken.setRevoked(true);
        String newJti = UUID.randomUUID().toString();
        storedRefreshtoken.setReplacedByToken(newJti);
        refreshTokenRepository.save(storedRefreshtoken);

        User user = storedRefreshtoken.getUser();

        var newRefreshTokenOb = RefreshToken.builder()
                .jti(newJti)
                .user(user)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(jwtService.getRefreshTtlSeconds()))
                .revoked(false)
                .build();

        refreshTokenRepository.save(newRefreshTokenOb);
        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user, newRefreshTokenOb.getJti());

        cookieService.attachRefreshCookie(response, newRefreshToken, (int) jwtService.getRefreshTtlSeconds());
        cookieService.noStoreHeaders(response);
        return ResponseEntity.ok(TokenResponse.of(newAccessToken, newRefreshToken, modelMapper.map(user, UserDto.class)));

    }
    // this method will read refreshTokenfrom request header or body
    private Optional<String> readRefreshTokenFromRequest(RefreshTokenRequest body, HttpServletRequest request) {
            //1. prefer reading refresh token from cookie
        if (request.getCookies() != null){
            Optional<String> fromCookie = Arrays.stream(request.getCookies())
                    .filter(c-> cookieService.getRefreshTokenCookieName().equals(c.getName()))
                    .map(Cookie::getValue)
                    .filter(v -> !v.isBlank())
                    .findFirst();

            if (fromCookie.isPresent()){
                return fromCookie;
            }
        }

        //2.Body
        if (body!=null && body.refreshToken()!= null && !body.refreshToken().isBlank()){
            return Optional.of(body.refreshToken());
        }

        //3. Custom header
        String refreshHeader = request.getHeader("X-Refresh-token");
        if (refreshHeader != null && !refreshHeader.isBlank()){
            return Optional.of(refreshHeader.trim());
        }

        return Optional.empty();
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(userDto));
    }
}
