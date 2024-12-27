package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.AlbumRepositoryJPA;
import mk.ukim.finki.wp.lab.repository.ArtistRepositoryJPA;
import mk.ukim.finki.wp.lab.repository.SongRepositoryJPA;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DataHolder {

    public static List<Artist> artists = null;
    public static List<Song> songs = null;

    public static List<Album> albums = null;

    private final AlbumRepositoryJPA albumRepository;
    private final ArtistRepositoryJPA artistRepository;
    private final SongRepositoryJPA songRepository;

    public DataHolder(AlbumRepositoryJPA albumRepository, ArtistRepositoryJPA artistRepository, SongRepositoryJPA songRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }

    @PostConstruct
    public void init(){
        artists = new ArrayList<>();
        artists.add(new Artist(1L, "Cacko", "Cacko","11111"));
        artists.add(new Artist(2L, "Backo", "Backo","22222"));
        artists.add(new Artist(3L, "Macko", "Macko","33333"));
        artists.add(new Artist(4L, "Stole", "Stole","44444"));
        artists.add(new Artist(5L, "Bole", "Bole","44444"));

        albums = new ArrayList<>();
        albums.add(new Album("album1", "rock", "2014"));
        albums.add(new Album("album2", "rap", "2004"));
        albums.add(new Album("album3", "R&B", "2010"));
        albums.add(new Album("album4", "pop", "1980"));
        albums.add(new Album("album5", "turbo", "2002"));
        albumRepository.saveAll(albums);
        List<Album> temp_albums = albumRepository.findAll();

        IntStream.range(0, songs.size()).forEach(i->{
            songs.get(i).setAlbum(temp_albums.get(i));
            songRepository.save(songs.get(i));
        });

        songs = new ArrayList<>();
        songs.add(new Song( "A","pesna1", "pop", 2018));
        songs.add(new Song( "B","pesna2", "rock", 2015));
        songs.add(new Song( "V","pesna3", "R&B", 1980));
        songs.add(new Song("G","pesna4", "rap", 2010));
        songs.add(new Song("D","pesna5", "turbo", 2014));

    }
}
