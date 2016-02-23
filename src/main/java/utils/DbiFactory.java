package utils;

import org.skife.jdbi.v2.DBI;
import static utils.AppConfig.*;

/**
 * @author Russ Forstall
 */
public class DbiFactory {
    private DBI dbi;

    public DbiFactory() {
		//TODO: get connection from pool
		//new DBI(ConnectionFactory connFac)
		
		//for now, directly use the DriverManager
        dbi = new DBI(
                DB_URL,
                DB_USER,
                DB_PASSWORD
        );
    }

    public DBI getDbi() {
        return dbi;
    }
}