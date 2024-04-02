package com.example.splitwise1.services;

import com.example.splitwise1.exceptions.UserNotFoundException;
import com.example.splitwise1.models.User;
import com.example.splitwise1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UpdateProfileService {
    UserRepository userRepository;
    @Autowired
    public UpdateProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User updateProfile(String email, String newPassword) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        User user = userOptional.get();
        user.setPassword(newPassword);
        return userRepository.save(user);
    }
}
