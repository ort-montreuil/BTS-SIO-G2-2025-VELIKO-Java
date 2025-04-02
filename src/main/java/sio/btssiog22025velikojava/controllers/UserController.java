package sio.btssiog22025velikojava.controllers;

import sio.btssiog22025velikojava.models.User;
import sio.btssiog22025velikojava.services.UserService;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserController
{
    private final UserService userService;

    public UserController() {this.userService = new UserService();}

    public ArrayList<User> allUsers() throws SQLException
    {
        return userService.allUser();
    }

    public boolean checkCredentials(String email, String enteredPassword) throws SQLException
    {
        return userService.checkCredentials(email, enteredPassword);
    }

    public void blockUser(String email) throws SQLException
    {
        userService.blockUser(email);
    }
    public void unblockUser(String email) throws SQLException
    {
        userService.unblockUser(email);
    }
    public void deleteUser(String email) throws SQLException
    {
        userService.deleteUser(email);
    }

    public boolean ChangePassword(User user) throws SQLException
    {
        return userService.ChangePassword(user);
    }

    public void forceUser(String email) throws SQLException
    {
        userService.forceUser(email);
    }

    public void unForceUser(String email) throws SQLException
    {
        userService.unForceUser(email);
    }
    public void editBooleanAdValidation(String email)throws SQLException
    {
        userService.editBooleanAdValidation(email);
    }
    public void editBooleanAdValidationTrue(String email)throws SQLException
    {
        userService.editBooleanAdValidationTrue(email);
    }
    public ArrayList<User> getUsersWithAdValidationFalse() throws SQLException
    {
        return userService.getUsersWithAdValidationFalse();
    }
    public Boolean getBooleanAdValidation(String email) throws SQLException
    {
        return userService.getBooleanAdValidation(email);
    }

}