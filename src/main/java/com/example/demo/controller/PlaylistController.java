package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Playlist;
import com.example.demo.entity.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;

@Controller
public class PlaylistController {
	@Autowired
	SongService songServices;
	@Autowired
	PlaylistService playService;
	
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		

		
		List<Song> songList=songServices.fetchAllSongs();

		//model.addAttribute("songs", songsList);

		model.addAttribute("songs", songList);
		
	return "createPlayList";
	   }
	
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		
		//update playlist table
		playService.addPlaylist(playlist);
		System.out.println("Playlist");
		
		
		//update song table
		List<Song>songList=playlist.getSong();
		for(Song s: songList) {
			s.getPlaylist().add(playlist);
			songServices.updateSong(s);
			}

		return "adminHome";
		
	
		
	     
	}
	@GetMapping("/viewPlaylist")
	public String viewPlaylist(Model model) {

		List<Playlist> allPlaylists=playService.fetchAllPlaylists();
		model.addAttribute("allPlaylist",allPlaylists);
		return "displayplaylist";
		
	}
		
	

}
