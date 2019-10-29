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

            JSONObject mainObject = rootObject.getJSONObject("main");
            DecimalFormat df = new DecimalFormat("#.##");

            temp = mainObject.getDouble("temp");
            temp_max = mainObject.getDouble("temp_max");
            temp_min = mainObject.getDouble("temp_min");
            temp_day = (((temp_min - 273) + (temp_max - 273)) / 2);
            temp = temp - 273;

            humidity = mainObject.getInt("humidity");
            //visibility = rootObject.getInt("visibility");

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
            //System.out.println("Widoczność: " + visibility);

        } else {
            System.out.println("Error");
        }
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wciśnij 1, aby podać nazwę miasta");
        System.out.println("Wciśnij 2, aby podać id miasta");
        System.out.println("Wciśnij 3, aby podać kod pocztowy");
        System.out.println("Wciśnij 4, aby podać koordynaty miasta");
        System.out.println("Wciśnij 5, aby zakończyć");
        int liczba = scanner.nextInt();

        switch (liczba) {

            case 1:
                System.out.println("Podaj nazwę miasta");
                Scanner scannerMiasta = new Scanner(System.in);
                String cityName = scannerMiasta.nextLine();
                try {
                    String response = new HttpService().connect(Config.APP_URL + "?q=" + cityName + "&appid=" + Config.APP_ID);
                    parseJson(response);
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    run();


                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;



            case 2:
                System.out.println("Podaj id miasta");
                Scanner scannerID = new Scanner(System.in);
                String cityID = scannerID.nextLine();
                System.out.println(cityID);
                try {
                    String response = new HttpService().connect(Config.APP_URL + "?id=" + cityID + "&appid=" + Config.APP_ID);
                    parseJson(response);
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    run();

                } catch (IOException e) {
                    e.printStackTrace();
                }break;

            case 3:

                System.out.println("Podaj skrót państwa");
                Scanner scannerSkrotPanstwa = new Scanner(System.in);
                String skrotPanstwa = scannerSkrotPanstwa.nextLine();

                System.out.println("Podaj kod pocztowy miasta");
                Scanner scannerZipCode = new Scanner(System.in);
                String cityZipCode = scannerZipCode.nextLine();


                try {
                    String response = new HttpService().connect(Config.APP_URL + "?zip=" + cityZipCode + "," + skrotPanstwa + "&appid=" + Config.APP_ID);
                    parseJson(response);
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    run();

                } catch (IOException e) {
                    e.printStackTrace();
                }break;


            case 4:
                System.out.println("Podaj długość geograficzną");
                Scanner scannerLat = new Scanner(System.in);
                String lat = scannerLat.nextLine();
                Scanner scannerLon = new Scanner(System.in);
                System.out.println("Podaj szerokość geograficzną");
                String lon = scannerLon.nextLine();
                try {
                    String response = new HttpService().connect(Config.APP_URL + "?lat=" + lat + "&lon=" + lon + "&appid=" + Config.APP_ID);
                    parseJson(response);
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    run();

                } catch (IOException e) {
                    e.printStackTrace();
                }break;

            case 5:
                break;
        }
    }
}
