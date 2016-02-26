import static spark.Spark.*;
import spark.ModelAndView;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import user.User;
import user.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.flywaydb.core.Flyway;
import user.UsersViewModel;
import utils.VelocityTemplateEngine;
import websockets.SimpleSocket;

import static utils.DbiFactory.*;
import static utils.AppConfig.*;

/**
 * Created by russellf on 2/22/2016.
 */
public class Main {
	
    public static void main(String[] args) {
		////SETUP
		//final Logger logger = LoggerFactory.getLogger(Main.class);
		staticFileLocation("/public"); // Static files
		Flyway flyway = new Flyway();
        flyway.setValidateOnMigrate(false);
        flyway.setDataSource(DB_URL, DB_USER, DB_PASSWORD);
        flyway.migrate();

		////WEBSOCKETS
        webSocket("/simple", SimpleSocket.class);

		////ROUTES
        get("/users-json", (req, res) -> {
			//logger.info("route /users was requested.");
			List<User> users = new ArrayList<User>();
			
			try (UserDao userDao = getDbi().open(UserDao.class)) {
				users = userDao.getUsers();				
			} catch (Exception e) {
				e.printStackTrace();
			}

			Gson gson = new Gson();
			return gson.toJson(users);
		});

		post("/form", (req, res) -> {
			System.out.println("heilo");

			return "Hello";
		});

		get("/users", (req, res) -> {		
			List<User> users = new ArrayList<User>();
			
			try (UserDao userDao = getDbi().open(UserDao.class)) {
				users = userDao.getUsers();				
			} catch (Exception e) {
				e.printStackTrace();
			}

			UsersViewModel model = new UsersViewModel(users, "russ");

			return new ModelAndView(model, "templates/users.vm");

		}, new VelocityTemplateEngine());

		get("/users/:id", (req, res) -> {
			User user = null;
			try (UserDao userDao = getDbi().open(UserDao.class)) {
				user = userDao.getUserById(Integer.parseInt(req.params(":id")));				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Gson gson = new Gson();
			return gson.toJson(user);
		});	
    }
}
