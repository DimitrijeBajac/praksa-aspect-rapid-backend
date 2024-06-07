package rs.rapidinvest.rapid.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.rapidinvest.rapid.model.User;
import rs.rapidinvest.rapid.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user!=null) {
            User userObj = user.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        } else
            throw new UsernameNotFoundException("Username not found");

    }

    private String[] getRoles(User userObj) {
        if(userObj.getRole() ==null){
            return new String[] {"USER"};
        }
        return userObj.getRole().split(",");
    }
}
