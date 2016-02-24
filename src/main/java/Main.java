import static spark.Spark.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import user.User;
import user.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.flywaydb.core.Flyway;
import static utils.DbiFactory.*;
import static utils.AppConfig.*;

/**
 * Created by russellf on 2/22/2016.
 */
public class Main {
	
    public static void main(String[] args) {
		////SETUP
		//final Logger logger = LoggerFactory.getLogger(Main.class);		
		Flyway flyway = new Flyway();
        flyway.setValidateOnMigrate(false);
        flyway.setDataSource(DB_URL, DB_USER, DB_PASSWORD);
        flyway.migrate();
	
		////ROUTES
        get("/users", (req, res) -> {
			//logger.info("route /users was requested.");
			List<User> users = new ArrayList<User>();
			
			try (UserDao userDao = getDbi().open(UserDao.class)) {
				users = userDao.getUsers();				
			}
			
			Gson gson = new Gson();
			return gson.toJson(users);
		});

		get("/users/:id", (req, res) -> {
			User user;
			try (UserDao userDao = getDbi().open(UserDao.class)) {
				user = userDao.getUserById(Integer.parseInt(req.params(":id")));				
			}
			
			Gson gson = new Gson();
			return gson.toJson(user);
		});	
    }
}
