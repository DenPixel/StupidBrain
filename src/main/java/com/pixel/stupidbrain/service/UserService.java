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
import java.util.List;
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
        if (nickname.isEmpty()) throw new FieldIsEmptyException("Username");
        if (userRepository.existsByNickname(nickname)) throw new UsernameAlreadyExistsException(nickname);

        String email = request.getEmail();
        if (email.isEmpty()) throw new FieldIsEmptyException("Email");
        if (userRepository.existsByEmail(email)) throw new EmailAlreadyExistsException(email);

        String password = request.getPassword();
        String rePassword = request.getRePassword();
        if (password.isEmpty()) throw new FieldIsEmptyException("Password");
        if (rePassword.isEmpty()) throw new FieldIsEmptyException("RePassword");
        if (!password.equals(rePassword)) throw new PasswordMismatchException();

        User user = new User();
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(getRolesByNames(request.getRoles()));
        user.getRoles().forEach(role -> role.addUser(user));

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

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
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
