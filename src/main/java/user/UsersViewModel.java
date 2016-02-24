package user;

import java.util.List;

/**
 * Created by russellf on 2/24/2016.
 */
public class UsersViewModel {
    List<User> users;
    String name;

    public UsersViewModel(List<User> users, String name) {
        super();
        this.users = users;
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
