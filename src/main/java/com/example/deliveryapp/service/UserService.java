package com.example.deliveryapp.service;

import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user){
        return userRepository.save(user);
    }

    public User findByLogin(String login){
        return userRepository.findByLogin(login);
    }

    public Optional<User> findById(Integer id){ // for testing
        return userRepository.findById(id);
    }

    public void updateUser(User user){
        userRepository.save(user);
    }
}
