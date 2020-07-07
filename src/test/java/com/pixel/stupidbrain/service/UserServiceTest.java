package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.request.SaveUserRequest;
import com.pixel.stupidbrain.entity.response.UserResponse;
import com.pixel.stupidbrain.exception.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserOperations userOperations;

    @Test
    void create() {
        String username = "test";
        String password = "test";
        String rePassword = "test";
        String email = "testemail@gmail.com";
        HashSet<String> roles = new HashSet<>();

        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setRePassword(rePassword);
        userRequest.setEmail(email);
        userRequest.setRoles(roles);

        UserResponse userResponse = userOperations.create(userRequest);

        assertNotNull(userResponse);
        assertNotNull(userResponse.getId());
        assertEquals(username, userResponse.getUsername());
        assertEquals(email, userResponse.getEmail());
        assertEquals(roles, userResponse.getRoles());

        userOperations.deleteById(userResponse.getId());
    }

    @Test()
    void createWithoutUsername(){
        String password = "test1";
        String rePassword = "test";
        String email = "testemail1@gmail.com";
        HashSet<String> roles = new HashSet<>();

        testCreateUserWithoutField(null, password, rePassword, email, roles, "Username");
    }


    @Test()
    void createWithoutPassword(){
        String username = "testsasas1";
        String rePassword = "test";
        String email = "testemail165442@gmail.com";
        HashSet<String> roles = new HashSet<>();

        testCreateUserWithoutField(username, null, rePassword, email, roles, "Password");
    }

    @Test
    void createWithoutRePassword(){
        String username = "test1";
        String password = "test1";
        String email = "testemail1ewrwer@gmail.com";
        HashSet<String> roles = new HashSet<>();

        testCreateUserWithoutField(username, password, null, email, roles, "RePassword");
    }

    @Test
    void createWithoutEmail(){
        String username = "test1bffge";
        String password = "test";
        String rePassword = "test";
        HashSet<String> roles = new HashSet<>();

        testCreateUserWithoutField(username, password, rePassword, null, roles, "Email");
    }

    @Test
    void createWithPasswordMismatch(){
        String username = "test1vjvg";
        String password = "test";
        String rePassword = "test223";
        String email = "testemail14554554@gmail.com";
        HashSet<String> roles = new HashSet<>();

        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setRePassword(rePassword);
        userRequest.setEmail(email);
        userRequest.setRoles(roles);

        try {
            userOperations.create(userRequest);
        } catch (PasswordMismatchException e) {
            assertEquals("Password mismatch", e.getReason());
        }
    }

    @Test
    void createWithUsernameAlreadyExist(){
        String username = "test1dffff4b";
        String password = "test";
        String rePassword = "test";
        String email1 = "testemail154t5t54t5@gmail.com";
        String email2 = "testemairtl23@gmail.com";
        HashSet<String> roles = new HashSet<>();

        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setRePassword(rePassword);
        userRequest.setEmail(email1);
        userRequest.setRoles(roles);

        UserResponse userResponse = userOperations.create(userRequest);

        userRequest.setEmail(email2);

        try {
            userOperations.create(userRequest);
        } catch (UsernameAlreadyExistsException e) {
            assertEquals("Username " + username + " already exists", e.getReason());
        }

        userOperations.deleteById(userResponse.getId());
    }

    @Test
    void createWithEmailAlreadyExist(){
        String password = "test";
        String rePassword = "test";
        String email = "testemail1@gmail.com";
        HashSet<String> roles = new HashSet<>();

        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername("test4");
        userRequest.setPassword(password);
        userRequest.setRePassword(rePassword);
        userRequest.setEmail(email);
        userRequest.setRoles(roles);

        UserResponse userResponse = userOperations.create(userRequest);

        userRequest.setUsername("test5");

        try {
            userOperations.create(userRequest);
        } catch (EmailAlreadyExistsException e) {
            assertEquals("Email " + email + " already exists", e.getReason());
        }

        userOperations.deleteById(userResponse.getId());
    }

    @Test
    void getById() {
        String username = "test2";
        String password = "test";
        String rePassword = "test";
        String email = "testemail2@gmail.com";
        HashSet<String> roles = new HashSet<>();

        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setRePassword(rePassword);
        userRequest.setEmail(email);
        userRequest.setRoles(roles);

        UserResponse userResponse = userOperations.create(userRequest);

        assertNotNull(userResponse);
        assertNotNull(userResponse.getId());

        UserResponse response = userOperations.getById(userResponse.getId());

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(username, response.getUsername());
        assertEquals(email, response.getEmail());
        assertEquals(roles, response.getRoles());
        assertEquals(response, userResponse);

        userOperations.deleteById(response.getId());
    }

    @Test
    void getByRandomId(){
        UUID randomUUID = UUID.randomUUID();

        try{
            userOperations.getById(randomUUID);
        } catch (UserNotFoundException e){
            assertEquals("User with id " + randomUUID + " not found", e.getReason());
        }

    }

    @Test
    void update() {
        String username = "test3545";
        String password = "test3";
        String rePassword = "test3";
        String email = "testemail3tgr@gmail.com";

        String newUsername = "test377";
        String newPassword = "test";
        String newRePassword = "test";
        String newEmail = "testemail35454@gmail.com";

        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setRePassword(rePassword);
        userRequest.setEmail(email);

        UserResponse userResponse = userOperations.create(userRequest);

        UUID id = userResponse.getId();

        userRequest.setUsername(newUsername);
        userRequest.setPassword(newPassword);
        userRequest.setRePassword(newRePassword);
        userRequest.setEmail(newEmail);

        userOperations.update(id, userRequest);

        UserResponse byId = userOperations.getById(id);

        assertEquals(id, byId.getId());
        assertEquals(newUsername, byId.getUsername());
        assertEquals(newEmail, byId.getEmail());

        userOperations.deleteById(id);
    }

    @Test
    void updateWithUsernameIsEmpty(){
        String username = "test353dfdf43";
        String password = "test3";
        String rePassword = "test3";
        String email = "testemail3@gmail.com";

        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setRePassword(rePassword);
        userRequest.setEmail(email);

        UserResponse userResponse = userOperations.create(userRequest);

        UUID id = userResponse.getId();
        userRequest.setUsername(null);

        try {
            userOperations.update(id, userRequest);
        } catch (FieldIsEmptyException e){
            assertEquals("Field \"Username\" is empty", e.getReason());
        }

        userOperations.deleteById(id);
    }

    @Test
    void updateWithEmailIsEmpty(){
        String username = "test3ghbkb";
        String password = "test3";
        String rePassword = "test3";
        String email = "testemail3yuvuyf@gmail.com";

        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setRePassword(rePassword);
        userRequest.setEmail(email);

        UserResponse userResponse = userOperations.create(userRequest);

        UUID id = userResponse.getId();
        userRequest.setEmail(null);

        try {
            userOperations.update(id, userRequest);
        } catch (FieldIsEmptyException e){
            assertEquals("Field \"Email\" is empty", e.getReason());
        }

        userOperations.deleteById(id);
    }

    @Test
    void updateWithPasswordIsEmpty(){
        String username = "test34fd5546";
        String password = "test3";
        String rePassword = "test3";
        String email = "testemail3tgtg45@gmail.com";

        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setRePassword(rePassword);
        userRequest.setEmail(email);

        UserResponse userResponse = userOperations.create(userRequest);

        UUID id = userResponse.getId();
        userRequest.setPassword(null);

        try {
            userOperations.update(id, userRequest);
        } catch (FieldIsEmptyException e){
            assertEquals("Field \"Password\" is empty", e.getReason());
        }

        userOperations.deleteById(id);
    }

    @Test
    void updateWithUsernameAlreadyExist(){
        String username = "test3rr";
        String username2 = "test65";
        String password = "test3";
        String rePassword = "test3";
        String email = "testemail34t4t4t@gmail.com";
        String email2 = "testemail3ger3tt@gmail.com";

        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setRePassword(rePassword);
        userRequest.setEmail(email);

        UserResponse userResponse1 = userOperations.create(userRequest);

        userRequest.setUsername(username2);
        userRequest.setEmail(email2);

        UserResponse userResponse2 = userOperations.create(userRequest);

        UUID id1 = userResponse1.getId();
        UUID id2 = userResponse2.getId();

        assertNotEquals(id1, id2);

        userRequest.setEmail(email);

        try {
            userOperations.update(id1, userRequest);
        } catch (UsernameAlreadyExistsException e) {
            assertEquals("Username " + username2 + " already exists", e.getReason());
        }

        userOperations.deleteById(id1);
        userOperations.deleteById(id2);
    }

    @Test
    void updateWithEmailExist(){
        String username = "test3rr";
        String username2 = "test65";
        String password = "test3";
        String rePassword = "test3";
        String email = "testemail344442@gmail.com";
        String email2 = "testemail3gtt@gmail.com";

        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setRePassword(rePassword);
        userRequest.setEmail(email);

        UserResponse userResponse1 = userOperations.create(userRequest);

        userRequest.setUsername(username2);
        userRequest.setEmail(email2);

        UserResponse userResponse2 = userOperations.create(userRequest);

        UUID id1 = userResponse1.getId();
        UUID id2 = userResponse2.getId();

        assertNotEquals(id1, id2);

        userRequest.setUsername(username);

        try {
            userOperations.update(id1, userRequest);
        } catch (EmailAlreadyExistsException e) {
            assertEquals("Email " + email2 + " already exists", e.getReason());
        }

        userOperations.deleteById(id1);
        userOperations.deleteById(id2);
    }

    @Test
    void deleteById() {
        String username = "test3vrtrr3q";
        String password = "test";
        String rePassword = "test";
        String email = "testemail3f34f5@gmail.com";
        HashSet<String> roles = new HashSet<>();

        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setRePassword(rePassword);
        userRequest.setEmail(email);
        userRequest.setRoles(roles);

        UserResponse userResponse = userOperations.create(userRequest);

        UUID id = userResponse.getId();

        userOperations.deleteById(id);

        try{
            userOperations.getById(id);
        } catch (UserNotFoundException e){
            assertEquals("User with id " + id + " not found", e.getReason());
        }
    }

    @Test
    void deleteByRandomId(){
        UUID randomUUID = UUID.randomUUID();

        try {
            userOperations.deleteById(randomUUID);
        } catch (UserNotFoundException e){
            assertEquals("User with id " + randomUUID + " not found", e.getReason());
        }
    }

    @Test
    void existByUsername() {
        String username = "test434f34";
        String password = "test4";
        String rePassword = "test4";
        String email = "testemail4@gmail.com";
        HashSet<String> roles = new HashSet<>();

        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setRePassword(rePassword);
        userRequest.setEmail(email);
        userRequest.setRoles(roles);

        UserResponse userResponse = userOperations.create(userRequest);

        boolean existByUsername = userOperations.existByUsername(userResponse.getUsername());

        assertTrue(existByUsername);

        userOperations.deleteById(userResponse.getId());
    }

    @Test
    void existByRandomUsername(){
        boolean existByUsername = userOperations.existByUsername("jndbjrnibjigbnbinndb");

        assertFalse(existByUsername);
    }

    @Test
    void getByUsername() {
        String username = "test5";
        String password = "test5";
        String rePassword = "test5";
        String email = "testemail5@gmail.com";
        HashSet<String> roles = new HashSet<>();

        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setRePassword(rePassword);
        userRequest.setEmail(email);
        userRequest.setRoles(roles);

        UserResponse userResponse = userOperations.create(userRequest);

        UserResponse byUsername = userOperations.getByUsername(userResponse.getUsername());

        assertEquals(username, byUsername.getUsername());

        userOperations.deleteById(userResponse.getId());
    }

    @Test
    void getByRandomUsername(){
        String randomUsername = "fgfombotbmtomtzgrzmozg";
        UserResponse byUsername = null;

        try {
            byUsername = userOperations.getByUsername(randomUsername);
        } catch (UsernameNotFoundException e) {
            assertEquals("User with username " + randomUsername + " not found", e.getReason());
        }

        assertNull(byUsername);
    }

    private void testCreateUserWithoutField(String username,
                                            String password,
                                            String rePassword,
                                            String email,
                                            HashSet<String> roles,
                                            String nameField) {
        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setRePassword(rePassword);
        userRequest.setEmail(email);
        userRequest.setRoles(roles);

        UserResponse userResponse = null;

        try {
            userResponse = userOperations.create(userRequest);
        } catch (StupidBrainException e){
            assertEquals("Field \"" + nameField + "\" is empty", e.getReason());
        }

        assertNull(userResponse);
    }
}