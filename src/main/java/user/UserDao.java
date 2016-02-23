package user;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * @author Russ Forstall
 */
@RegisterMapper(UserMapper.class)
public interface UserDao extends AutoCloseable {
    @SqlUpdate("insert into User (name, username, email, phone, website) values (:name, :username, :email, :phone, :website);")
    void createUser(@Bind("name") String name, @Bind("username") String username, @Bind("email") String email, @Bind("phone") String phone, @Bind("website") String website);

	@SqlUpdate("insert into User (name, username, email, phone, website) values (:name, :username, :email, :phone, :website);")
    void createUser(@BindBean User user);
	
    @SqlQuery("SELECT * FROM User WHERE email = :email;")
    User getUserByEmail(@Bind("email") String email);

	//http://jdbi.org/sql_object_api_argument_binding/
    @SqlQuery("SELECT * FROM User WHERE id = :it;")
    User getUserById(@Bind int userId);

    @SqlQuery("SELECT * FROM User;")
    List<User> getUsers();
}