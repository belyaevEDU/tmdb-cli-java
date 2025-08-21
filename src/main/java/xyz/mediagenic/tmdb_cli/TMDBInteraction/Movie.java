package xyz.mediagenic.tmdb_cli.TMDBInteraction;

public record Movie(
        String title,
        String overview,
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

        builder.append("Vote average: ");
        builder.append(voteAverage);
        return builder.toString();
    }
}
