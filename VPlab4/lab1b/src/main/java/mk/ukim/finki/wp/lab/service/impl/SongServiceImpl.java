package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.*;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepositoryJPA songRepository;
    private final ArtistRepositoryJPA artistRepository;
    private final AlbumRepositoryJPA albumRepository;


    public SongServiceImpl(SongRepositoryJPA songRepository, ArtistRepositoryJPA artistRepository, AlbumRepositoryJPA albumRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    public List<Song> listSongs() {
        return songRepository.findAll();
    }
    public List<Song> listSongsByAlbum(Long albumId){
        return songRepository.findAllByAlbum_Id(albumId);
    }
    @Override
    public Optional<Song> findById(long id) {
        return songRepository.findById(id);
    }
    @Override
    public Optional<Song> findByTrackId(String trackId) {
        return songRepository.findByTrackId(trackId);
    }
    public void addArtistToSong(Artist artist, Song song){
        song.getPerformers().add(artist);
        songRepository.save(song);
    }

    @Override
    public void findAndAddArtistToSong(Long artistId, Long songId) {
        Artist artist = artistRepository.findById(artistId).orElseThrow(RuntimeException::new);
        Song song = songRepository.findById(songId).orElseThrow(RuntimeException::new);
        this.addArtistToSong(artist,song);
        songRepository.save(song);
    }

    @Override
    public void save(Long id,String title, String trackId, String genre, int releaseYear, Long albumId) {
        Album album = albumRepository.findById(albumId).orElseThrow();
        if (id==null){
            Song newSong = new Song(trackId,title,genre,releaseYear);
            newSong.setAlbum(album);
            songRepository.save(newSong);
        }else {
            Song song = songRepository.findById(id).orElseThrow();
            song.setAlbum(album);
            song.setTitle(title);
            song.setTrackId(trackId);
            song.setGenre(genre);
            song.setReleaseYear(releaseYear);
            songRepository.save(song);
        }
    }

    @Override
    public void removeArtistFromSong(Long songId, Long performerId) {
        Song song = songRepository.findById(songId).orElseThrow();
        Artist artist = artistRepository.findById(performerId).orElseThrow();
        song.getPerformers().removeIf(found_artist -> found_artist.getId().equals(artist.getId()));
        songRepository.save(song);
    }

    @Override
    public void deleteById(long id) {
        songRepository.deleteById(id);
    }


}
