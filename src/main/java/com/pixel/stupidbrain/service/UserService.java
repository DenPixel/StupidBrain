package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.Role;
import com.pixel.stupidbrain.entity.User;
import com.pixel.stupidbrain.entity.request.SaveUserRequest;
import com.pixel.stupidbrain.exception.*;
import com.pixel.stupidbrain.repository.RoleRepository;
import com.pixel.stupidbrain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class UserService implements UserOperations {

    private final UserRepository userRepository;

    public final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(SaveUserRequest request) {

        String nickname = request.getNickname();
        if (userRepository.existsByNickname(nickname)) {
            throw new UsernameAlreadyExistsException(nickname);
        }

        String email = request.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }

        String login = request.getLogin();
        if (userRepository.existsByLogin(login)) {
            throw new LoginAlreadyExistsException(login);
        }

        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setNickname(nickname);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(getRolesByNames(request.getRoles()));

        return userRepository.save(user);
    }

    @Override
    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public void update(UUID id, SaveUserRequest request) {
        User userExisting = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        String email = request.getEmail();
        String nickname = request.getNickname();

        if (!userExisting.getNickname().equals(nickname) && userRepository.existsByNickname(nickname)) {
            throw new UsernameAlreadyExistsException(nickname);
        }

        if (!userExisting.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }

        userExisting.setEmail(email);
        userExisting.setNickname(nickname);
        userExisting.setPassword(passwordEncoder.encode(request.getPassword()));
        userExisting.setRoles(getRolesByNames(request.getRoles()));

        userRepository.save(userExisting);
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    private Set<Role> getRolesByNames(Set<String> roleNames) {
        Set<Role> roles = new HashSet<>(roleNames.size());

        for (String roleName : roleNames) {
            var role = roleRepository
                    .findByName(roleName)
                    .orElseThrow(() -> new RoleNotFoundException(roleName));
            roles.add(role);
        }
        return roles;
    }

}
