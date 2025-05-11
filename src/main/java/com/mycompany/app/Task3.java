package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Task3 {
    public static void start() {
        var webDriver = new ChromeDriver();
        try {
            webDriver.get("https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms");

            var elem = webDriver.findElement(By.tagName("pre"));
            var jsonStr = elem.getText();

            var parser = new JSONParser();
            var obj = (JSONObject) parser.parse(jsonStr);

            var hourly = (JSONObject) obj.get("hourly");
            var times = (JSONArray) hourly.get("time");
            var temperatures = (JSONArray) hourly.get("temperature_2m");
            var rains = (JSONArray) hourly.get("rain");

            try (var w = new PrintWriter(new FileWriter("./result/forecast.txt"))) {
                w.printf("| № | Дата/время | Температура | Осадки (мм) |\n");

                for (int i = 0; i < times.size(); i++) {
                    var time = (String) times.get(i);
                    var temp = (double) temperatures.get(i);
                    var rain = (double) rains.get(i);
                    w.printf("| %d | %s | %f | %f |\n", i + 1, time, temp, rain);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            webDriver.quit();
        }
    }
}
