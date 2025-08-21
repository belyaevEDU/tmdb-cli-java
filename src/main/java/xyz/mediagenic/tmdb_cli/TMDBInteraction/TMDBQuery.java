package xyz.mediagenic.tmdb_cli.TMDBInteraction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@PropertySource("classpath:application.properties")
public class TMDBQuery {
    private final Map<QueryType, String> typeToString;
    private String baseUrl = "https://api.themoviedb.org/3/movie/";

    @Value("$(spring.TMDB.apiKey)")
    private String apiKey;

    private HttpClient httpClient;

    @Autowired
    public TMDBQuery() {
        this.typeToString = new HashMap<>();
        typeToString.put(QueryType.PLAYING, "now_playing");
        typeToString.put(QueryType.POPULAR, "popular");
        typeToString.put(QueryType.TOP, "top_rated");
        typeToString.put(QueryType.UPCOMING, "upcoming");

        this.httpClient = HttpClient.newBuilder().build();
    }

    private HttpResponse<String> getResponseFromAPI(QueryType type) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(this.baseUrl + this.typeToString.get(type)))
                .timeout(Duration.ofSeconds(10))
                .header("Authorization", "Bearer " + this.apiKey)
                .header("accept", "application/json")
                .GET()
                .build();

        System.out.println(this.apiKey);
//        return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response);
        return response;
    }

    public ArrayList<Movie> getParsedResponse(QueryType type) {
        try {
            HttpResponse<String> response = this.getResponseFromAPI(type);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

}
