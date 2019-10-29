import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpServiceTest2 {

    @Test
    public void connect() throws IOException {
        String strUrl = "http://api.openweathermap.org/data/2.5/weather?q=1234&appid=6e88eb72d7dc9bb21be06b73cddc1491";

        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.connect();

            assertEquals(HttpURLConnection.HTTP_OK, urlConn.getResponseCode());
            System.out.println("połączenie poprawne");

        } catch (IOException e) {
            System.err.println("połączenie niepoprawne");
            e.printStackTrace();
            throw e;
        }
    }


    @Test
    public void ifJson() throws IOException {
        URL seatURL = new URL("http://api.openweathermap.org/data/2.5/weather?q=1234&appid=6e88eb72d7dc9bb21be06b73cddc1491");

        BufferedReader br = new BufferedReader(new
                InputStreamReader(seatURL.openStream(),
                Charset.forName("UTF-8")));
        String readAPIResponse = " ";
        StringBuilder jsonString = new StringBuilder();
        while ((readAPIResponse = br.readLine()) != null) {
            jsonString.append(readAPIResponse);
        }
        JSONObject jsonObj = new JSONObject(jsonString.toString());

        assertNotNull(jsonObj);
    }
}
