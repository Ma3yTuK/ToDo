package com.todo.todo_back.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.todo_back.entities.User;
import com.todo.todo_back.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
    
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findUserByUsername(String username) {
        Optional<User> userFromDb = userRepository.findByUsername(username);
        return userFromDb; 
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userFromDb = userRepository.findByUsername(username);

        if (userFromDb.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return userFromDb.get();
    }

    public Optional<User> findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb;
    }

    public void encryptPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

}
