package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Not found!");
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User saveUser(User user) {
        user.setRoles(roleRepo.findRolesByRoleIn(user.getRoles()
                .stream().map(Role::getRole).collect(Collectors.toList())));
       return userRepo.save(user);
    }

    public User findUserById(Long id) {
        return userRepo.getById(id);
    }


    public User userEdit(User user) {
        user.setRoles(roleRepo.findRolesByRoleIn(user.getRoles()
                .stream().map(Role::getRole).collect(Collectors.toList())));
        user.setPassword(passwordEncoder.encode(user.getPasswordNew()));
       return userRepo.save(user);
    }

    public void userDelete(User user) {
      userRepo.delete(user);
    }
}
