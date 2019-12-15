package com.xebia.fs101.writerpad.services.domain;

import com.xebia.fs101.writerpad.domain.User;
import com.xebia.fs101.writerpad.exceptions.UserNotFoundException;
import com.xebia.fs101.writerpad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public User find(String username) {
        Optional<User> optionalUser = this.userRepository.findByUsername(username);
        if(!optionalUser.isPresent())
            throw new UserNotFoundException();
        return optionalUser.get();
    }
}
