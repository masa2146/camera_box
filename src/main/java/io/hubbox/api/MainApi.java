package io.hubbox.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hubbox.bot.CameraBot;
import io.hubbox.commons.Database_Helper;
import io.hubbox.commons.ModuleCommons;
import io.hubbox.model.Camera;
import io.hubbox.model.ResponseMessage;
import io.hubbox.tool.FileTools;
import io.hubbox.tool.ParentFile;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.Service;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * @author fatih
 */
public class MainApi implements ModuleCommons {

    private static final String uploadPath = ParentFile.getParenFilePath() + "/upload";
    private File uploadDir = new File(uploadPath);
    private CameraBot cameraBot;
    private Service http;

    @Override
    public void onStart(Service http) {
//        this.http = ignite().port(8282);
        this.http = http;
//        Database_Helper.init("jdbc:h2:~/hubbox");
        uploadDir.mkdir();
        this.http.post("/import", this::importWhiteList);
        this.http.post("/camera", this::addCamera); // add camera
        this.http.delete("/camera/:id", this::deleteCamera); // delete camera
        this.http.get("/camera", this::listCamera); // camera list
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onInit() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStop() {

    }

    private String addCamera(Request request, Response response) throws JsonProcessingException {
        try {
            response.type("application/json");
            checkProperty();
            ObjectMapper objectMapper = new ObjectMapper();
            Camera camera = objectMapper.readValue(request.body(), Camera.class);
            Object[] cameras = Database_Helper.getCustomProperty("cameras");
            List<Camera> cameraList = new ArrayList<>();
            if (cameras != null && cameras[0] != null && cameras[0] != "") {
                cameraList = new ArrayList<>(Arrays.asList(objectMapper.readValue(String.valueOf(cameras[0]), Camera[].class)));
            }
            long maxId = cameraList.stream().mapToLong(Camera::getId).max().orElse(-1);
            camera.setId(maxId + 1);
            cameraList.add(camera);
            Database_Helper.setCustomProperty("cameras", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cameraList));
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(new ResponseMessage("Success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(new ResponseMessage("Failed"));
        }
    }

    private String deleteCamera(Request request, Response response) throws JsonProcessingException {
        try {
            response.type("application/json");
            checkProperty();
            Long id = Long.valueOf(request.params("id"));
            ObjectMapper objectMapper = new ObjectMapper();
            Object[] cameras = Database_Helper.getCustomProperty("cameras");
            List<Camera> cameraList = objectMapper.readValue(String.valueOf(cameras[0]), new TypeReference<>() {
            });

            Camera camera = cameraList.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
            if (camera == null)
                return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(new ResponseMessage("Id " + id + " not found"));
            cameraList.remove(camera);
            Database_Helper.setCustomProperty("cameras", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cameraList));
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(new ResponseMessage("Success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(new ResponseMessage("Failed"));
        }
    }

    private String listCamera(Request request, Response response) throws JsonProcessingException {
        try {
            response.type("application/json");
            checkProperty();
            ObjectMapper objectMapper = new ObjectMapper();
            Object[] cameras = Database_Helper.getCustomProperty("cameras");
            List<Camera> cameraList = Arrays.asList(objectMapper.readValue(String.valueOf(cameras[0]), Camera[].class));
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cameraList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(new ResponseMessage("Failed"));
        }
    }

    private String importWhiteList(Request request, Response response) throws IOException {
        String body = request.body();
        File file = FileTools.JSONtoCSV(body);
        String status = cameraBot.importWhiteList(file.getPath());
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(new ResponseMessage(status));
    }

    private String uploadCSV(Request request, Response response) {
        try {
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            Collection<Part> parts = request.raw().getParts();
            System.out.println(parts.size());
            for (Part p : parts) {
                System.out.println("Name: " + p.getName());
                System.out.println("Size: " + p.getSize());
                System.out.println("File Name: " + p.getSubmittedFileName());
                String fileName = p.getSubmittedFileName();
                Path out = Paths.get(uploadDir.toPath().toString() + "/" + fileName);
                InputStream input = request.raw().getPart(p.getName()).getInputStream();
                Files.copy(input, out, StandardCopyOption.REPLACE_EXISTING);
//                request.raw().getPart(p.getName()).delete();
            }
            return "{ \"message\": \"" + "Success" + "\"}";
        } catch (IOException | ServletException e) {
            e.printStackTrace();
            http.halt(HttpStatus.BAD_GATEWAY_502);
            return "{ \"message\": \"" + "Failed" + "\"}";
        }
    }

    private void checkProperty() {
        if (Database_Helper.getCustomProperty("cameras") == null) {
            Database_Helper.setCustomProperty("cameras", "");
        }
    }
}
