package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Entity
public class Song {

    private String trackId;
    private String title;
    private String genre;
    private int releaseYear;
    @ManyToMany
    private List<Artist> performers;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Album album;
    public Song (String trackId, String title, String genre, int releaseYear){
        this.trackId = trackId;
        this.title = title;
        this. genre = genre;
        this.releaseYear = releaseYear;
        album = null;
    }

    public Song() {
    }
}
