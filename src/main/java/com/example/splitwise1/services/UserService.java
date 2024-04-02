package com.example.splitwise1.services;

import com.example.splitwise1.exceptions.UserAlreadyExitsException;
import com.example.splitwise1.models.User;
import com.example.splitwise1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {
    UserRepository userRepository ;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String userName, String password, String email) throws UserAlreadyExitsException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            throw new UserAlreadyExitsException();
        }
        User user = new User();
        user.setEmail(email);
        user.setName(userName);
        user.setPassword(password);
        return userRepository.save(user);

    }
}
