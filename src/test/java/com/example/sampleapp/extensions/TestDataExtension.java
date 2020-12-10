package com.example.sampleapp.extensions;

import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonDocument;
import org.bson.Document;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.test.context.junit.jupiter.SpringExtension.getApplicationContext;

/**
 * @author walid.sewaify
 * @since 13-Nov-20
 */
@Slf4j
public class TestDataExtension implements BeforeAllCallback, AfterAllCallback {
    private static final String DATA_PATH = "src/test/resources/data";

    private MongoCollection<Document> collection;

    @Override
    @SuppressWarnings("unchecked")
    public void beforeAll(ExtensionContext context) throws IOException {
        MongoTemplate repository = getApplicationContext(context).getBean(MongoTemplate.class);
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**.json");
        Files.walk(Paths.get(DATA_PATH)).filter(Files::isRegularFile).
            filter(matcher::matches).forEach(file -> {
            String fileName = removeExtension(file.getFileName().toString());
            collection = repository.getCollection(fileName);
            try {
                log.info("loading testing data...");
                String data = new String(Files.readAllBytes(file));
                List<Document> records = (List<Document>) Document.parse(data).get("_records");
                collection.insertMany(records);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    private String removeExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int index = filename.lastIndexOf('.');
        if (index == -1) {
            return filename;
        } else {
            return filename.substring(0, index);
        }
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        collection.deleteMany(new BsonDocument());
    }
}
