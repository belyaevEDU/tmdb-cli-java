package xyz.mediagenic.tmdb_cli.TMDBInteraction;

public record Movie(
        String title,
        String overview,
        String releaseDate,
        double voteAverage
) {
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Title: ");
        builder.append(title);
        builder.append("\n");

        builder.append("Overview: ");
        builder.append(overview);
        builder.append("\n");

        builder.append("Release date: ");
        builder.append(releaseDate);
        builder.append("\n");

        builder.append("Vote average: ");
        builder.append(voteAverage);
        return builder.toString();
    }
}
