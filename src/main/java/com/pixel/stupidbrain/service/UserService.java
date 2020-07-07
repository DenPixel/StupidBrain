package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.Role;
import com.pixel.stupidbrain.entity.User;
import com.pixel.stupidbrain.entity.request.SaveUserRequest;
import com.pixel.stupidbrain.entity.response.UserResponse;
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
    public UserResponse create(SaveUserRequest request) {
        if (request == null) throw new RequestIsEmptyException();

        String username = request.getUsername();
        if (username == null || username.isEmpty()) throw new FieldIsEmptyException("Username");
        if (userRepository.existsByUsername(username)) throw new UsernameAlreadyExistsException(username);

        String email = request.getEmail();
        if (email == null || email.isEmpty()) throw new FieldIsEmptyException("Email");
        if (userRepository.existsByEmail(email)) throw new EmailAlreadyExistsException(email);

        String password = request.getPassword();
        String rePassword = request.getRePassword();
        if (password == null || password.isEmpty()) throw new FieldIsEmptyException("Password");
        if (rePassword == null || rePassword.isEmpty()) throw new FieldIsEmptyException("RePassword");
        if (!password.equals(rePassword)) throw new PasswordMismatchException();

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(getRolesByNames(request.getRoles()));
        user.getRoles().forEach(role -> role.addUser(user));

        return UserResponse.fromUser(userRepository.save(user));
    }

    @Override
    public UserResponse getById(UUID id) {
        if (id == null) throw new FieldIsEmptyException("ID");

        return UserResponse.fromUser(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id)));
    }

    @Override
    public void update(UUID id, SaveUserRequest request) {
        if (id == null) throw new FieldIsEmptyException("ID");
        if (request == null) throw new RequestIsEmptyException();

        User userExisting = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        String email = request.getEmail();
        if (email == null || email.isEmpty()) throw new FieldIsEmptyException("Email");
        if (!userExisting.getEmail().equals(email)
                && userRepository.existsByEmail(email)) throw new EmailAlreadyExistsException(email);

        String username = request.getUsername();
        if (username == null || username.isEmpty()) throw new FieldIsEmptyException("Username");
        if (!userExisting.getUsername().equals(username)
                && userRepository.existsByUsername(username)) throw new UsernameAlreadyExistsException(username);

        String password = request.getPassword();
        if (password == null || password.isEmpty())
            throw new FieldIsEmptyException("Password");

        userExisting.setEmail(email);
        userExisting.setUsername(username);
        userExisting.setPassword(passwordEncoder.encode(password));
        userExisting.setRoles(getRolesByNames(request.getRoles()));

        userRepository.save(userExisting);
    }

    @Override
    public void deleteById(UUID id) {
        if (id == null) throw new FieldIsEmptyException("ID");

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.deleteById(user.getId());
    }

    @Override
    public List<UserResponse> getAll() {
        return UserResponse.fromUsers(userRepository.findAll());
    }

    @Override
    public boolean existByUsername(String username) {
        if (username == null || username.isEmpty()) throw new FieldIsEmptyException("Username");

        return userRepository.existsByUsername(username);
    }

    @Override
    public UserResponse getByUsername(String username) {
        if (username == null || username.isEmpty()) throw new FieldIsEmptyException("Username");

        return UserResponse.fromUser(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
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
