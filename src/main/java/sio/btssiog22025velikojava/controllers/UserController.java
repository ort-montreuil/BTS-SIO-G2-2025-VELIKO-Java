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
}