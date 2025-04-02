package sio.btssiog22025velikojava.services;

import sio.btssiog22025velikojava.models.User;
import sio.btssiog22025velikojava.repositories.UserRepository;

import javax.swing.text.StyledEditorKit;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserService
{
    private final UserRepository userRepository;

    public UserService() { this.userRepository = new UserRepository(); }

    public ArrayList<User> allUser() throws SQLException {
        return userRepository.allUser();
    }

    public boolean checkCredentials(String email, String enteredPassword) throws SQLException
    {
        return userRepository.checkCredentials(email, enteredPassword);
    }
    public void blockUser(String email) throws SQLException
    {
        userRepository.blockUser(email);
    }
    public void unblockUser(String email) throws SQLException
    {
        userRepository.unblockUser(email);
    }
    public void deleteUser(String email) throws SQLException
    {
        userRepository.deleteUser(email);
    }

    public boolean ChangePassword (User user) throws SQLException
    {
        return userRepository.ChangePassword(user);
    }

    public void forceUser(String email) throws SQLException
    {
        userRepository.forceUser(email);
    }

    public void unForceUser(String email) throws SQLException
    {
        userRepository.unForceUser(email);
    }
    public void editBooleanAdValidation(String email)throws SQLException
    {
        userRepository.editBooleanAdValidation(email);
    }
    public void editBooleanAdValidationTrue(String email)throws SQLException
    {
        userRepository.editBooleanAdValidationTrue(email);
    }
    public ArrayList<User> getUsersWithAdValidationFalse() throws SQLException
    {
        return userRepository.getUsersWithAdValidationFalse();
    }
    public Boolean getBooleanAdValidation(String email) throws SQLException
    {
        return userRepository.getBooleanAdValidation(email);
    }


}