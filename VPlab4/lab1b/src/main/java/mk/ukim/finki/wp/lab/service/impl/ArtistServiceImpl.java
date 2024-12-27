package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.repository.ArtistRepositoryJPA;
import mk.ukim.finki.wp.lab.service.ArtistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService{
    private final ArtistRepositoryJPA artistRepository;

    public ArtistServiceImpl(ArtistRepositoryJPA artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Artist> listArtists(){
        return artistRepository.findAll();
    }

    @Override
    public Optional<Artist> findArtistById(Long id){
        return artistRepository.findById(id);
    }

}
