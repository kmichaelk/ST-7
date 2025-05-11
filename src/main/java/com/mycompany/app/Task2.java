package com.mycompany.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class Task2 {
    public static void start() {
        var webDriver = new ChromeDriver();
        try {
            webDriver.get("https://api.ipify.org/?format=json");

            var elem = webDriver.findElement(By.tagName("pre"));

            var jsonStr = elem.getText();

            var parser = new JSONParser();
            var obj = (JSONObject) parser.parse(jsonStr);

            var ip = (String) obj.get("ip");
            System.out.println(ip);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            webDriver.quit();
        }
    }
}
