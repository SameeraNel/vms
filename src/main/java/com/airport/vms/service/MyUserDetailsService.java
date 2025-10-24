package com.airport.vms.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username)) {
            return new User("admin", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        } else if ("kiosk".equals(username)) {
            return new User("kiosk", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_KIOSK")));
        } else if ("guard".equals(username)) {
            return new User("guard", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_GUARD")));
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
