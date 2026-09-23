package com.kaueadriano.novici.service;

import com.kaueadriano.novici.dto.UserResponse;
import com.kaueadriano.novici.exception.UserNotFoundException;
import com.kaueadriano.novici.model.User;
import com.kaueadriano.novici.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserResponse> listAll(){
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail()))
                .toList();
    }
    public UserResponse listOne(Integer id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
    public void delete(Integer id){
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
}
