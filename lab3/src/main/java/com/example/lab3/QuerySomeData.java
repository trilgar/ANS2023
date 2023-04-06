package com.example.lab3;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.client.RestClients;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static com.example.lab3.UploadData.INDEX_NAME;
import static com.example.lab3.UploadData.clientConfiguration;

public class QuerySomeData {
    public static void main(String[] args) {
        performQuery(QueryBuilders.matchAllQuery(), "matchAll.json");
        performQuery(QueryBuilders.matchQuery("title", "Cooler Master"), "containsField.json");
    }

    private static void performQuery(QueryBuilder query, String fileName) {
        try(RestHighLevelClient client = RestClients.create(clientConfiguration).rest()){
            SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(query);
            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            SearchHits hits = searchResponse.getHits();

            File file = new File(fileName);
            PrintStream stream = new PrintStream(file);
            System.setOut(stream);

            for (SearchHit hit : hits.getHits()) {
                String sourceAsString = hit.getSourceAsString();
                System.out.println(sourceAsString);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
