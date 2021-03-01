package io.hubbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hubbox.api.MainApi;
import io.hubbox.bot.BotDriver;
import io.hubbox.bot.CameraBot;
import io.hubbox.model.Camera;
import io.hubbox.model.WhiteListData;
import io.hubbox.tool.ParentFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fatih
 */
public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        serverTest();
        Camera camera = new Camera();
        camera.setIp("192.168.1.112");
        camera.setPort(1212);
        camera.setUsername("xxxxx");
        camera.setPassword("xxxxxxxx");
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(camera));

    }

    private static void serverTest() {
        MainApi mainApi = new MainApi();
        mainApi.onStart(null);
    }

    private static void cameraBotTest() {
        CameraBot cameraBot = new CameraBot();
        cameraBot.login("xxxxxx", "xxxxxx", "http://10.0.0.67/");
//        cameraBot.addWhiteListTab(new WhiteListData("42CDU850", "Fatih", date, date));
//        cameraBot.importWhiteList("/home/fatih/Videos/test.csv");
        System.out.println(cameraBot.importBlackList("/home/fatih/Videos/blackListTemplate.csv"));
    }
}
