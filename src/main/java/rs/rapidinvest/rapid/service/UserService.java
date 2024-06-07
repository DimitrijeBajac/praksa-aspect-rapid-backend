package rs.rapidinvest.rapid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.rapidinvest.rapid.model.User;
import rs.rapidinvest.rapid.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
