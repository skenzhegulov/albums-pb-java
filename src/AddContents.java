import com.example.music.AlbumsProtos.Albums;
import com.example.music.AlbumsProtos.Album;
import com.google.protobuf.TextFormat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;

class AddContents {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: AddContents ALBUMS_FILE NEW_ALBUMS_FILE");
            System.exit(-1);
        }

        Albums.Builder albums = Albums.newBuilder();

        // Try to populate using text proto file
        try {
            InputStream inputStream = new FileInputStream(args[0]);
            InputStreamReader reader = new InputStreamReader(inputStream);
            TextFormat.merge(reader, albums);
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            System.exit(-1);
        }

        albums.addAlbum(GenerateAlbum());

        System.out.println(albums.build().toString());
        try (PrintStream output = new PrintStream(new FileOutputStream(args[1]))) {
            output.print(albums.build().toString());
        }
    }

    static Album GenerateAlbum() {
        Album.Review reviews[] = {
                Album.Review.newBuilder()
                        .setReviewer("Metacritic")
                        .setScore(2.5f)
                        .setMaxValue(5f)
                        .setType(Album.Review.Type.STARS)
                        .build(),
                Album.Review.newBuilder()
                        .setReviewer("Independent")
                        .setScore(9f)
                        .setMaxValue(10f)
                        .setType(Album.Review.Type.NUMBER)
                        .build()
        };

        Album.Track tracks[] = {
                Album.Track.newBuilder()
                        .setTitle("Believe")
                        .setDuration(315)
                        .build(),
                Album.Track.newBuilder()
                        .setTitle("River")
                        .setFeaturing("Ed Sheeran")
                        .setDuration(341)
                        .build(),
                Album.Track.newBuilder()
                        .setTitle("Walk on Water")
                        .setFeaturing("Beyonce")
                        .setDuration(304)
                        .build(),
                Album.Track.newBuilder()
                        .setTitle("Need Me")
                        .setFeaturing("P!nk")
                        .setDuration(265)
                        .build()
        };

        return Album.newBuilder()
                .setName("Revival")
                .setReleaseYear(2017)
                .setArtistName("Eminem")
                .setGenre(Album.Genre.RAP)
                .addAllReview(Arrays.asList(reviews))
                .addAllTrack(Arrays.asList(tracks))
                .build();
    }
}
