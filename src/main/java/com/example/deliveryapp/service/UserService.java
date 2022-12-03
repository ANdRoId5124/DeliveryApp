package com.example.deliveryapp.service;

import com.example.deliveryapp.enteties.Role;
import com.example.deliveryapp.enteties.User;
import com.example.deliveryapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User register(User user, Role role){
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    public Optional<User> findByLoginAndPassword(String login, String password){
        User user = userRepository.findByLogin(login);
        if(user != null && passwordEncoder.matches(password, user.getPassword())){
            return Optional.of(user);
        }
        return Optional.empty();
    }


}
