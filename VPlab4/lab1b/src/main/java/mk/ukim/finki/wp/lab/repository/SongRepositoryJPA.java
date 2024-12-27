package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepositoryJPA extends JpaRepository<Song, Long> {
    List<Song> findAllByAlbum_Id(Long album_id);
    Optional<Song> findByTrackId(String id);
}
