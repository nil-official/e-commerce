package com.ecommerce.controller;

import com.ecommerce.exception.InvalidTokenException;
import com.ecommerce.exception.TokenExpiredException;
import com.ecommerce.exception.UserNotVerifiedException;
import com.ecommerce.model.Role;
import com.ecommerce.model.VerifyToken;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.VerifyTokenRepository;
import com.ecommerce.request.RegisterRequest;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.security.CustomUserDetailsService;
import com.ecommerce.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.config.JwtTokenProvider;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.request.LoginRequest;
import com.ecommerce.response.AuthResponse;
import com.ecommerce.service.CartService;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private VerifyTokenRepository verifyTokenRepository;
    private EmailService emailService;
    private JwtTokenProvider jwtTokenProvider;
    private CustomUserDetailsService customUserDetails;
    private CartService cartService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signupHandler(@Valid @RequestBody RegisterRequest registerRequest, final HttpServletRequest request) throws UserException, MessagingException {

        // Check if user with the given email already exists
        User isEmailExist = userRepository.findByEmail(registerRequest.getEmail());
        if (isEmailExist != null) {
            throw new UserException("Email Is Already Used With Another Account");
        }

        // Creating a User object
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Adding roles
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));
        roles.add(userRole);
        user.setRoles(roles);

        // Saving the object into DB
        User savedUser = userRepository.save(user);

        // Creating a cart for the new user
        cartService.createCart(savedUser);

        // Creating an email verification token and saving it into DB
        String token = UUID.randomUUID().toString();
        VerifyToken verifyToken = new VerifyToken();
        verifyToken.setToken(token);
        verifyToken.setUser(savedUser);
        verifyToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        verifyTokenRepository.save(verifyToken);

        // Sending the email verification link
        emailService.sendVerificationEmail(savedUser.getEmail(), token, request);

        ApiResponse apiResponse = new ApiResponse("User Registered Successfully! Please verify your email to login!", true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signinHandler(@RequestBody LoginRequest loginRequest) {

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        User existingUser = userRepository.findByEmail(loginRequest.getEmail());

        if (!existingUser.isVerified()) {
            throw new UserNotVerifiedException("User is not verified!");
        }

        System.out.println(username + " ----- " + password);

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setStatus(true);
        authResponse.setJwt(token);
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        System.out.println("sign in userDetails - " + userDetails);

        if (userDetails == null) {
            System.out.println("sign in userDetails - null " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("sign in userDetails - password not match " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse> verifyHandler(@RequestParam String token) {

        // Finding the provided token from DB
        VerifyToken verificationToken = verifyTokenRepository.findByToken(token);

        // Handling the exception when no token found
        if (verificationToken == null) {
            throw new InvalidTokenException("Token not found.");
        }

        // Handling the exception when token expired
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token got expired.");
        }

        // Marking the user as verified and deleting the token from DB
        User user = verificationToken.getUser();
        user.setVerified(true);
        userRepository.save(user);
        verifyTokenRepository.delete(verificationToken);

        ApiResponse apiResponse = new ApiResponse("Email Verified Successfully! Please login to continue..", true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }
}
