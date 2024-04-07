package dao;

import models.User;
import java.util.List;
import java.util.ArrayList;

public class UserDao {
    private static List<User> users = new ArrayList<>();

    public User read(String firstName, String lastName) {
        if(!users.isEmpty()) {
            for(User u : users) {
                if(u.getFirstName().equals(firstName) && u.getLastName().equals(lastName)) {
                    return u;
                }
            }
        }
        return null;
    }

    public void delete(User user) {
        users.remove(user);
    }

    public void create(User user) {
        users.add(user);
    }
}
