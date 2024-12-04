package sio.btssiog22025velikojava.repositories;

import sio.btssiog22025velikojava.models.User;
import sio.btssiog22025velikojava.tools.DataSourceProvider;
import sio.btssiog22025velikojava.tools.PasswordHasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRepository
{
    private Connection connection;

    private PasswordHasher passwordHasher;

    public UserRepository(){this.connection = DataSourceProvider.getCnx();}

    public ArrayList<User> allUser() throws SQLException
    {
        ArrayList<User> lesUsers = new ArrayList<>();
        PreparedStatement ps = this.connection.prepareStatement("SELECT name, first_name, email, statut\n" +
                "FROM `user` WHERE user.roles != '[\"ROLE_ADMIN\"]'");
        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            User user = new User(rs.getString("name"), rs.getString("first_name"), rs.getString("email"), rs.getBoolean("statut")) ;
            lesUsers.add(user);
        }
        return lesUsers;
    }

    public boolean checkCredentials(String email, String enteredPassword) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("SELECT email, password\n" +
                "FROM `user` \n" +
                "WHERE roles LIKE '%ROLE_ADMIN%' AND email = ?");
        ps.setString(1, email);
        boolean result = false;
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            if (PasswordHasher.verifyPassword(rs.getString("password"), enteredPassword))
            {
                result = true;
            }
        }

        ps.close();
        rs.close();
        return result;
    }
}