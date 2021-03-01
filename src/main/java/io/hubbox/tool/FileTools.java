package io.hubbox.tool;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;

/**
 * @author fatih
 */
public class FileTools {

    public static File JSONtoCSV(String jsonContent) throws IOException {
        String fileName = System.currentTimeMillis() + "";
        JsonNode jsonTree = new ObjectMapper().readTree(jsonContent);
        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        JsonNode firstObject = jsonTree.elements().next();
        firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);
        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        File file = new File(ParentFile.getParenFilePath() + "/upload/" + fileName);
        csvMapper.writerFor(JsonNode.class)
                .with(csvSchema)
                .writeValue(file, jsonTree);
        return file;
    }
}
