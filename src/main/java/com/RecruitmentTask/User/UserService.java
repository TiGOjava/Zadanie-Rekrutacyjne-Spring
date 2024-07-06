package com.RecruitmentTask.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void saveUser(User user) {
        userRepository.save(user);
    }


    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
