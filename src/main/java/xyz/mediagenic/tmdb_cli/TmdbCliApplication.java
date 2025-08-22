package xyz.mediagenic.tmdb_cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.mediagenic.tmdb_cli.CLI.CliService;

import java.util.Scanner;

@SpringBootApplication
public class TmdbCliApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TmdbCliApplication.class, args);
    }

	@Autowired
	private CliService cliService;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Commands:");
		System.out.println("\"playing\" - get now playing movies");
		System.out.println("\"popular\" - get popular movies");
		System.out.println("\"top\" - get top movies");
		System.out.println("\"upcoming\" - get upcoming movies");
		System.out.println("\"quit\" to quit");
		System.out.println();

		Scanner scanner = new Scanner(System.in);
		String command = scanner.nextLine();
		while (!command.equals("quit")) {
			cliService.commandHandler(command);
			command = scanner.nextLine();
		}
	}
}
