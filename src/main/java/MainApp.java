import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

import org.json.JSONObject;

public class MainApp implements Runnable {


    private static void parseJson(String json) {
        double temp;
        double temp_max;
        double temp_min;
        double temp_day;
        double windSpeed;
        int humidity;
        int pressure;
        int clouds;
        int visibility;

        JSONObject rootObject = new JSONObject(json);
        if (rootObject.getInt("cod") == 200) {
            System.out.println(json);

            JSONObject mainObject = rootObject.getJSONObject("main");
            DecimalFormat df = new DecimalFormat("#.##");

            temp = mainObject.getDouble("temp");
            temp_max = mainObject.getDouble("temp_max");
            temp_min = mainObject.getDouble("temp_min");
            temp_day = (((temp_min - 273) + (temp_max -273)) / 2);
            temp = temp - 273;

            humidity = mainObject.getInt("humidity");
            visibility = rootObject.getInt("visibility");

            JSONObject windObject = rootObject.getJSONObject("wind");
            windSpeed = windObject.getDouble("speed");

            pressure = mainObject.getInt("pressure");
            JSONObject cloudsObject = rootObject.getJSONObject("clouds");
            clouds = cloudsObject.getInt("all");

            System.out.println("Temperatura: " + df.format(temp) + " \u00b0C");
            System.out.println("Temperatura maksymalna: " + df.format(temp_max) + " \u00b0C");
            System.out.println("Temperatura średnia: " + df.format(temp_day) + " \u00b0C");
            System.out.println("Wilgotność: " + humidity + " %");
            System.out.println("Zachmurzenie: " + clouds + "%");
            System.out.println("Ciśnienie: " + pressure + " hPa");
            System.out.println("Prędkość wiatru: " + windSpeed + " km/h");
            System.out.println("Ciśnienie: " + pressure + " hPa");
            System.out.println("Widoczność: " + visibility);

        } else {
            System.out.println("Error");
        }
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nazwę miasta: ");

        String cityName = scanner.nextLine();

        try {
            String response = new HttpService().connect(Config.APP_URL + "?q=" + cityName + "&appid=" + Config.APP_ID);
            parseJson(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
