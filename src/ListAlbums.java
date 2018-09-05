import com.example.music.AlbumsProtos.Album;
import com.example.music.AlbumsProtos.Albums;
import com.google.protobuf.TextFormat;

import java.io.*;

class ListAlbums {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: Albums ALBUMS_TEXT_FILE ALBUMS_BINARY_FILE");
            System.exit(-1);
        }

        Albums.Builder albums = Albums.newBuilder();

        // Try to populate using text proto file
        try {
            InputStream inputStream = new FileInputStream(args[0]);
            InputStreamReader reader = new InputStreamReader(inputStream);
            TextFormat.merge(reader, albums);
        } catch (FileNotFoundException e){
            System.err.println("File not found.");
            System.exit(-1);
        }
        
        //PrintAlbums(albums.build());

        System.out.println(albums.build().toString());

        // Write the new address book back to disk.
        FileOutputStream output = new FileOutputStream(args[1]);
        albums.build().writeTo(output);
        output.close();
    }

    // Iterates through all albums in the Albums and prints info about them.
    static void PrintAlbums(Albums albums) {
        for (Album album : albums.getAlbumList()) {
            System.out.println("Album name: " + (album.hasName() ? album.getName() : "Unspecified"));
            System.out.print("Release year: "); 
            if (album.hasReleaseYear()) {
                System.out.println(album.getReleaseYear());
            } else {
                System.out.println("Unspecified");
            }
            System.out.println("Artist name: " + (album.hasArtistName() ? album.getArtistName() : "Unspecified"));
            
            System.out.print("Genre: ");
            if (album.hasGenre()) {
                switch (album.getGenre()) {
                    case UNSPECIFIED:
                        System.out.println("Unspecified");
                        break;
                    case ROCK:
                        System.out.println("Rock");
                        break;
                    case CLASSICAL:
                        System.out.println("Classical");
                        break;
                    case PUNK:
                        System.out.println("Punk");
                        break;
                    case METAL:
                        System.out.println("Metal");
                        break;
                    case DEATH_METAL:
                        System.out.println("Death Metal");
                        break;
                    case POP:
                        System.out.println("Pop");
                        break;
                    case INDUSTRIAL:
                        System.out.println("Industrial");
                        break;
                }
            } else {
                System.out.println("Unspecified");
            }

            int count = 0;
            for (Album.Review review : album.getReviewList()) {
                System.out.println("Review #: " + count);
                System.out.println("  Reviewer: " + (review.hasReviewer() ? review.getReviewer() : "Unspecified"));
                System.out.print("  Score: ");
                if (review.hasScore() && review.hasMaxValue()) {
                    System.out.println(review.getScore() + "/" + review.getMaxValue());
                } else {
                    System.out.println("Unspecified");
                }
                System.out.print("  Type: ");
                switch (review.getType()) {
                    case NUMBER:
                        System.out.println("NUMBER");
                        break;
                    case STARS:
                        System.out.println("STARS");
                        break;
                }
                count++;
            }
        }
    }

}
