package com.example.marketplace.service;

import com.example.marketplace.exception.ResourceAlreadyExistsException;
import com.example.marketplace.exception.TokenRefreshException;
import com.example.marketplace.mapper.UserMapper;
import com.example.marketplace.model.RefreshToken;
import com.example.marketplace.model.User;
import com.example.marketplace.payload.request.LoginRequest;
import com.example.marketplace.payload.request.RegistrationRequest;
import com.example.marketplace.payload.request.TokenRefreshRequest;
import com.example.marketplace.payload.response.JwtResponse;
import com.example.marketplace.payload.response.RegistrationResponse;
import com.example.marketplace.payload.response.TokenRefreshResponse;
import com.example.marketplace.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return new JwtResponse(jwt, refreshToken.getToken());
    }

    public RegistrationResponse registerUser(RegistrationRequest registrationRequest) {

        boolean usernameExists = userMapper.findByUsername(registrationRequest.getUsername()).isPresent();

        if (usernameExists) {
            throw new ResourceAlreadyExistsException("Username already taken");
        }

        boolean emailExists = userMapper.findByEmail(registrationRequest.getEmail()).isPresent();

        if (emailExists) {
            throw new ResourceAlreadyExistsException("Email already taken");
        }

        User user = new User(
                registrationRequest.getUsername(),
                registrationRequest.getEmail(),
                passwordEncoder.encode(registrationRequest.getPassword())
        );

        return userMapper.save(user);
    }

    public TokenRefreshResponse refreshUserToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserId)
                .map(userId -> {
                    String token = jwtUtils.generateTokenFromId(userId);
                    return new TokenRefreshResponse(token, requestRefreshToken);
                })
                .orElseThrow(() -> new TokenRefreshException("Refresh token is not in database"));
    }

    public void logoutUser() {
        refreshTokenService.deleteByUserId(jwtUtils.getCurrentUser());
    }

}
