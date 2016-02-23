package user;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Date: 10/15/14
 * Time: 1:53 PM
 *
 * @author Russ Forstall
 */
public class UserMapper implements ResultSetMapper<User> {
    public User map(int i, ResultSet r, StatementContext statementContext) throws SQLException {
        return new User(r.getInt("id"),
                r.getString("name"),
                r.getString("username"),
                r.getString("email"),
                r.getString("phone"),
                r.getString("website")
        );
    }
}