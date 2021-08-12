package com.example.marketplace.controller;

import com.example.marketplace.payload.request.LoginRequest;
import com.example.marketplace.payload.request.RegistrationRequest;
import com.example.marketplace.payload.request.TokenRefreshRequest;
import com.example.marketplace.payload.response.JwtResponse;
import com.example.marketplace.payload.response.RegistrationResponse;
import com.example.marketplace.payload.response.TokenRefreshResponse;
import com.example.marketplace.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "authentication", description = "The authentication API")
@RequestMapping("/authentication")
public class AuthController {
    @Autowired
    AuthenticationService authenticationService;

    @Operation(summary = "Login an existing user", description = "Login an existing user", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
    }
    )
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authenticationService.authenticateUser(loginRequest), HttpStatus.OK);
    }

    @Operation(summary = "Create a new user", description = "Create a new user", responses = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(schema = @Schema(implementation = RegistrationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "User already exists",
                    content = @Content)
    }
    )
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RegistrationResponse> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return new ResponseEntity<>(authenticationService.registerUser(registrationRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Get new refresh token", description = "Generate new refresh token", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    }
    )
    @PostMapping(value = "/refresh", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TokenRefreshResponse> refreshUserToken(@Valid @RequestBody TokenRefreshRequest request) {
        return new ResponseEntity<>(authenticationService.refreshUserToken(request), HttpStatus.OK);
    }

    /*@Operation(summary = "Logout user", description = "Logout user", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    }
    )
    @GetMapping("/logout")
    public void logoutUser() {
        authenticationService.logoutUser();
    }*/

}
