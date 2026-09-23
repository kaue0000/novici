package com.kaueadriano.novici.service;

import com.kaueadriano.novici.dto.UserRequest;
import com.kaueadriano.novici.dto.UserResponse;
import com.kaueadriano.novici.exception.EmailAlreadyExistsException;
import com.kaueadriano.novici.exception.InvalidCredentialsException;
import com.kaueadriano.novici.model.User;
import com.kaueadriano.novici.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public UserResponse register(UserRequest userRequest){
        var emailAlreadyExists = userRepository.findByEmail(userRequest.getEmail()).isPresent();
        if(emailAlreadyExists)
            throw new EmailAlreadyExistsException("E-mail already registered");

        User user = new User();
        user.setName(userRequest.getName().toUpperCase());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User userSaved = userRepository.save(user);

        return new UserResponse(userSaved.getId(), userSaved.getName(), userSaved.getEmail());
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new InvalidCredentialsException("Invalid e-mail or password"));

        if(!passwordEncoder.matches(password, user.getPassword()))
            throw new InvalidCredentialsException("Invalid e-mail or password");

        return jwtService.generateToken(user.getEmail());
    }
}
