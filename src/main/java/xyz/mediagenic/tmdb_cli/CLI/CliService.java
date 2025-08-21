package xyz.mediagenic.tmdb_cli.CLI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mediagenic.tmdb_cli.TMDBInteraction.Movie;
import xyz.mediagenic.tmdb_cli.TMDBInteraction.QueryType;
import xyz.mediagenic.tmdb_cli.TMDBInteraction.TMDBQueryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CliService {
    @Autowired
    private TMDBQueryService tmdbQueryService;

    private final Map<String, QueryType> commandToQueryType;

    public CliService() {
        commandToQueryType = new HashMap<>();
        commandToQueryType.put("playing", QueryType.PLAYING);
        commandToQueryType.put("popular", QueryType.POPULAR);
        commandToQueryType.put("top", QueryType.TOP);
        commandToQueryType.put("upcoming", QueryType.UPCOMING);
    }

    private void printTMDBQuery(QueryType queryType) {
        ArrayList<Movie> movies = this.tmdbQueryService.getParsedResponse(queryType);
        if (movies == null) {
            System.out.println("Response is null.");
            return;
        }

        System.out.println("--------------------");
        for (int i = 0; i < movies.size(); i++) {
            System.out.println(movies.get(i));
            if (i != (movies.size() - 1)) {
                System.out.println();
            }
        }
        System.out.println("--------------------\n");
    }

    public void commandHandler(String command) {
        if (this.commandToQueryType.containsKey(command)) {
            QueryType queryType = this.commandToQueryType.get(command);
            this.printTMDBQuery(queryType);
        } else if (command.equals("quit")) {
        } else {
            System.out.println("Invalid command.\n");
        }
    }
}
