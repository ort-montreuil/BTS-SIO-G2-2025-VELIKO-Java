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
                "FROM `user` WHERE user.roles = '[\"ROLE_USER\"]'");
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
    public void blockUser(String email) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("UPDATE `user` SET statut = 1 WHERE email = ?");
        ps.setString(1, email);
        ps.executeUpdate();
        ps.close();
    }
    public void unblockUser(String email) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("UPDATE `user` SET statut = 0 WHERE email = ?");
        ps.setString(1, email);
        ps.executeUpdate();
        ps.close();
    }
    public void deleteUser(String email) throws SQLException
    {
        // Récupérer l'id de l'utilisateur à partir de son email
        PreparedStatement ps = connection.prepareStatement("SELECT id FROM `user` WHERE email = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        int userId = 0;
        if (rs.next()) {

            userId = rs.getInt("id");
        }
        else {
            throw new SQLException("User not found");
        }
        rs.close();
        ps.close();

        // Anonymize the user
        ps = connection.prepareStatement(
            " UPDATE `user` SET name = 'Anonyme', first_name = 'Anonyme',roles = '[\"ROLE_BLOCKED\"]' ,address='Anonymisée',email = CONCAT('anonyme_', id, '@example.com'), password = ? WHERE email = ?");
        ps.setString(1, PasswordHasher.hashPassword("anonyme"));
        ps.setString(2, email);
        ps.executeUpdate();
        ps.close();

        // Delete future reservations
        ps = connection.prepareStatement("DELETE FROM `reservation` WHERE user_email = ? AND date_reservation > NOW()");
        ps.setString(1, email);
        ps.executeUpdate();
        ps.close();

        //Anonymize the user in the past reservations
        ps = connection.prepareStatement("UPDATE `reservation` SET user_email = CONCAT('anonyme_',?, '@example.com') WHERE user_email = ?");
        ps.setInt(1, userId);
        ps.setString(2, email);
        ps.executeUpdate();
        ps.close();

        //Anonymize the user in the past fovorites
        ps = connection.prepareStatement("UPDATE `station_fav` SET user_email = CONCAT('anonyme_',?, '@example.com') WHERE user_email = ?");
        ps.setInt(1, userId);
        ps.setString(2, email);
        ps.executeUpdate();
        ps.close();

    }


    public boolean ChangePassword (User user) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("SELECT must_change_password FROM User where email = ? ");
        ps.setString(1, user.getEmail());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getBoolean("must_change_password");
    }

    public void forceUser(String email) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("UPDATE user \n " +
                "SET must_change_password = 1 \n" +
                " WHERE user.email = ?");
        ps.setString(1, email);
        ps.executeUpdate();
        ps.close();
    }

    public void unForceUser(String email) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("UPDATE user \n " +
                "SET must_change_password = 0 \n" +
                " WHERE user.email = ?");
        ps.setString(1, email);
        ps.executeUpdate();
        ps.close();
    }

    public void editBooleanAdValidation(String email) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("UPDATE `user` SET ad_validation = 0 WHERE email = ?");
        ps.setString(1, email);
        ps.executeUpdate();
        ps.close();
    }
    public void editBooleanAdValidationTrue(String email) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("UPDATE `user` SET ad_validation = 1 WHERE email = ?");
        ps.setString(1, email);
        ps.executeUpdate();
        ps.close();
    }

    public ArrayList<User> getUsersWithAdValidationFalse() throws SQLException
    {
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement ps = this.connection.prepareStatement(
                "SELECT name, first_name, email, statut FROM `user` WHERE ad_validation = 0 AND roles = '[\"ROLE_ADMIN\"]'"
        );
        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            User user = new User(
                    rs.getString("name"),
                    rs.getString("first_name"),
                    rs.getString("email"),
                    rs.getBoolean("statut")
            );
            users.add(user);
        }

        rs.close();
        ps.close();

        return users;
    }
    public Boolean getBooleanAdValidation(String email) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("SELECT `ad_validation` From user WHERE email = ?");
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}