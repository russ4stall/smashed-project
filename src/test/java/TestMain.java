import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

/**
 * Created by russellf on 2/22/2016.
 */
public class TestMain {
    @Test
    public void testGson() {
		String EXPECTED_RESULT = "[\"Russ\",\"Steven\",\"Jason\",\"Logan\",\"Matt\",\"Bryand\"]";
	
        List<String> list = new ArrayList<String>();
		list.add("Russ");
		list.add("Steven");
		list.add("Jason");
		list.add("Logan");
		list.add("Matt");
		list.add("Bryand");
			
		Gson gson = new Gson();
		String result = gson.toJson(list);
		
		assertEquals(result, EXPECTED_RESULT);
    }
}
