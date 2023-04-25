package com.example.lab3;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class UploadData {
    public static final String FILE_PATH = "src/main/java/com/example/lab3/combined.json";
    public static final String INDEX_NAME = "items";
    public static final ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("localhost:9200").build();

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        // створення індексу
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX_NAME);
        try (RestHighLevelClient client = RestClients.create(clientConfiguration).rest()) {
            if (!client.indices().exists(new GetIndexRequest(INDEX_NAME), RequestOptions.DEFAULT)) {
                client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            }
            BulkRequest bulkRequest = new BulkRequest();
            for (InfoItem infoItem : parseFromJson(FILE_PATH, objectMapper)) {
                IndexRequest indexRequest = new IndexRequest(INDEX_NAME)
                        .source(objectMapper.writeValueAsString(infoItem), XContentType.JSON);
                bulkRequest.add(indexRequest);
            }
            BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static InfoItem[] parseFromJson(String filePath, ObjectMapper objectMapper) {
        try {
            InfoItem[] items = objectMapper.readValue(new File(filePath), InfoItem[].class);
            for (InfoItem item : items) {
                item.setPubDate(generateRandomDate());
            }
            return items;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateRandomDate() {
        LocalDateTime start = LocalDateTime.of(2000, 1, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        long startEpoch = start.toEpochSecond(ZoneOffset.UTC);
        long endEpoch = end.toEpochSecond(ZoneOffset.UTC);
        long randomEpoch = ThreadLocalRandom.current().nextLong(startEpoch, endEpoch);
        return Instant.ofEpochSecond(randomEpoch).toString();
    }
}