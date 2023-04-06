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

public class UploadData {
    public static final String FILE_PATH = "src/main/java/com/example/lab3/anandtech.json";
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
            return objectMapper.readValue(new File(filePath), InfoItem[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}