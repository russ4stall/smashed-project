import static spark.Spark.*;

import com.russ4stall.spark.TemplateModelMapImpl;
import com.russ4stall.spark.ViewModelTransformer;
import com.russ4stall.spark.ViewModelTransformerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import freemarker.template.Configuration;
import spark.template.freemarker.FreeMarkerEngine;
import spark.template.velocity.VelocityTemplateEngine;
import user.User;
import user.UserDao;
import org.flywaydb.core.Flyway;
import user.UsersViewModel;
import websockets.SimpleSocket;
import static utils.DbiFactory.*;
import static utils.AppConfig.*;

/**
 * Created by russellf on 2/22/2016.
 */
public class Main {
	
    public static void main(String[] args) throws IOException {
		////SETUP
		//final Logger logger = LoggerFactory.getLogger(Main.class);
		staticFileLocation("/public"); // Static files
		Flyway flyway = new Flyway();
        flyway.setValidateOnMigrate(false);
        flyway.setDataSource(DB_URL, DB_USER, DB_PASSWORD);
        flyway.migrate();

		ViewModelTransformer velocityTransformer = new ViewModelTransformer(
				new VelocityTemplateEngine(),
				"templates/",
				".vm",
				new TemplateModelMapImpl()
		);

		Configuration freemarkerConfig = new Configuration();
		freemarkerConfig.setClassForTemplateLoading(Classpath.class, "templates/");

		ViewModelTransformer freemarkerTransformer = new ViewModelTransformer(
				new FreeMarkerEngine(freemarkerConfig),
				"",
				".ftl",
				new TemplateModelMapImpl()
		);

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

			return model;
		}, velocityTransformer);

		get("/russell", (req, res) -> {

			RussellViewModel viewModel = new RussellViewModel("Russell Forstall", 26, "Russ", "Ashlyn Forstall");

			return viewModel;
		}, freemarkerTransformer);

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
