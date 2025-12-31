package com.Learnings.practical.Security;

import com.Learnings.practical.Repositry.UserRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class customerUserDetailsService implements UserDetailsService {

    private final UserRepositry userRepositry;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepositry.findByUsername( username).orElseThrow(
        );
    }// now dao filter will use this method to retirieve  users and password

    //earlier when the bean was there and we were using in memory  the load user by username method
    //was  there and now we the user and password are stored in the database so we have to write the
    // load user by username method
}
