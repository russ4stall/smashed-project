import static spark.Spark.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import user.User;
import user.UserDao;
import utils.DbiFactory;
import org.flywaydb.core.Flyway;
import static utils.AppConfig.*;

/**
 * Created by russellf on 2/22/2016.
 */
public class Main {
    public static void main(String[] args) {
		Flyway flyway = new Flyway();
        flyway.setValidateOnMigrate(false);
        flyway.setDataSource(DB_URL, DB_USER, DB_PASSWORD);
        flyway.migrate();
	
        get("/users", (req, res) -> {
			List<User> users = new ArrayList<User>();
			
			try (UserDao userDao = new DbiFactory().getDbi().open(UserDao.class)) {
				users = userDao.getUsers();				
			}
			
			Gson gson = new Gson();
			return gson.toJson(users);
		});		
    }
}
