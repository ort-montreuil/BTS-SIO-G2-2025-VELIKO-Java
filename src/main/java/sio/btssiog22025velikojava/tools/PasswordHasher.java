package sio.btssiog22025velikojava.tools;


import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordHasher {

    // Méthode pour hasher un mot de passe
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Méthode pour vérifier si un mot de passe correspond à son hash
    public static boolean verifyPassword(String hashedPassword, String password) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}

