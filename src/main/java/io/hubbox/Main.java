package io.hubbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hubbox.api.MainApi;
import io.hubbox.bot.CameraBot;
import io.hubbox.model.Camera;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fatih
 */
public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        serverTest();
        Camera camera = new Camera();
        camera.setIp("192.168.1.112");
        camera.setPort(1212);
        camera.setUsername("masa");
        camera.setPassword("8ASD871HG");

        Camera camera2 = new Camera();
        camera2.setIp("192.168.1.222");
        camera2.setPort(1212);
        camera2.setUsername("masa2");
        camera2.setPassword("2232asdfgfds");

        List<Camera> cameraList = new ArrayList<>();
        cameraList.add(camera);
        cameraList.add(camera2);
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(cameraList));

    }

    private static void serverTest() {
        MainApi mainApi = new MainApi();
        mainApi.onStart();
    }

    private static void cameraBotTest() {
        CameraBot cameraBot = new CameraBot();
        cameraBot.login("admin", "a1234567", "http://10.0.0.67/");
//        cameraBot.addWhiteListTab(new WhiteListData("42CDU850", "Fatih", date, date));
//        cameraBot.importWhiteList("/home/fatih/Videos/test.csv");
        System.out.println(cameraBot.importBlackList("/home/fatih/Videos/blackListTemplate.csv"));
    }
}
