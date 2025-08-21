package xyz.mediagenic.tmdb_cli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.mediagenic.tmdb_cli.TMDBInteraction.QueryType;
import xyz.mediagenic.tmdb_cli.TMDBInteraction.TMDBQuery;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class TmdbCliApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmdbCliApplication.class, args);
    }



}
