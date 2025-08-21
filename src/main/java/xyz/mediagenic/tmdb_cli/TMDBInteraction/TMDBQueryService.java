package xyz.mediagenic.tmdb_cli.TMDBInteraction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
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
public class TMDBQueryService {
    private final Map<QueryType, String> typeToString;
    private String baseUrl = "https://api.themoviedb.org/3/movie/";

    @Value("${spring.TMDB.apiKey}")
    private String apiKey;

    private HttpClient httpClient;

    @Autowired
    public TMDBQueryService() {
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

        return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private Movie getMovieFromJsonObject(JsonObject jsonObject) throws ClassCastException {
        String title = jsonObject.getString("title");
        String overview = jsonObject.getString("overview");
        String releaseDate = jsonObject.getString("release_date");
        double voteAverage = jsonObject.getJsonNumber("vote_average").doubleValue();
        return new Movie(title, overview, releaseDate, voteAverage);
    }

    public ArrayList<Movie> getParsedResponse(QueryType type) {
        ArrayList<Movie> movies = new ArrayList<>();

        HttpResponse<String> response = null;
        try {
            response = this.getResponseFromAPI(type);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        }

        if (response.statusCode() != 200) {
            System.out.println("Bad response status code: " + response.statusCode());
            return null;
        }

        JsonReader reader = Json.createReader(new StringReader(response.body()));
        try {
            JsonArray moviesJsonArray = reader.readObject().getJsonArray("results");
            moviesJsonArray.stream().forEach(
                    c -> movies.add(getMovieFromJsonObject(c.asJsonObject()))
            );
        } catch (ClassCastException e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println("Highest probable cause: change in response format from TMDB.\n");
            return null;
        }
        return movies;
    }

}
