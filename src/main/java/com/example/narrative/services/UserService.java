package com.example.narrative.services;

import com.example.narrative.authentications.UserContext;
import com.example.narrative.entities.User;
import com.example.narrative.exceptions.NotFoundException;
import com.example.narrative.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id + " does not exist"));
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name).orElseThrow(() -> new NotFoundException(name + " does not exist"));
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(User otherUser, String id) {
        User existingUser = getUser(id);
        updateWith(otherUser, existingUser);
        return userRepository.save(existingUser);
    }

    public User updateByName(User otherUser, String name) {
        User existingUser = getUser(name);
        updateWith(otherUser, existingUser);
        return userRepository.save(existingUser);
    }

    public void updateWith(User otherUser, User existingUser) {
        existingUser.setName(otherUser.getName());
        existingUser.setAccessCode(otherUser.getAccessCode());
        existingUser.setPassword(otherUser.getPassword());
        existingUser.setMasterAccount(otherUser.getMasterAccount());
    }

    public String getUserId() {
        return getUserByName(UserContext.loggedInUsername()).getId();
    }
}
