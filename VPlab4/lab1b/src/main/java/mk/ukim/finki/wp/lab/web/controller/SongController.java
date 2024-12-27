package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import mk.ukim.finki.wp.lab.service.AlbumService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("songs")
public class SongController {
    private final SongService songService;
    private final AlbumService albumService;

    public SongController(SongService songService, AlbumService albumService) {
        this.songService = songService;
        this.albumService = albumService;
    }

    @GetMapping
    public String getSongsPage(@RequestParam(required = false) String error,@RequestParam(required = false) Long albumId, Model model){
        if(albumId!=null && albumId!=-1) {
            model.addAttribute("songs",songService.listSongsByAlbum(albumId));
        } else {
            model.addAttribute("songs",songService.listSongs());
        }
        model.addAttribute("error",error);
        model.addAttribute("albums",albumService.findAll());
        return "listSongs";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String saveOrUpdateSong(@RequestParam(required = false) Long id, @RequestParam String title, @RequestParam String trackId, @RequestParam String genre, @RequestParam int releaseYear, @RequestParam Long albumId){

        songService.save(id,title,trackId,genre,releaseYear,albumId);
        return "redirect:/songs";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add-form")
    public String getAddSongPage(Model model){
        model.addAttribute("albums",albumService.findAll());
        return "add-song";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{songId}")
    public String editSong(@PathVariable Long songId,Model model){
        Optional<Song> songToEdit = songService.findById(songId);

        if(songToEdit.isEmpty()) return "redirect:/songs?error=choose a valid song to edit";

        model.addAttribute("song",songToEdit.get());
        model.addAttribute("albums",albumService.findAll());
        return "add-song";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteSong(@PathVariable Long id){
        songService.deleteById(id);
        return "redirect:/songs";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/performers/delete/{songId}/{performerId}")
    public String deletePerformerFromSong(@PathVariable Long songId, @PathVariable Long performerId) {
        songService.removeArtistFromSong(songId, performerId);
        return "redirect:/songs/edit/" + songId;
    }

    @GetMapping("/details/{id}")
    public String songDetails(@PathVariable Long id, Model model){
        Song songToEdit = songService.findById(id).orElseThrow();
        model.addAttribute("song",songToEdit);
        return "songDetails";
    }

}
