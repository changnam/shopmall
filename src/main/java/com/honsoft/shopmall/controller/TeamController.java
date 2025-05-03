package com.honsoft.shopmall.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honsoft.shopmall.entity.Player;
import com.honsoft.shopmall.entity.Team;
import com.honsoft.shopmall.repository.PlayerRepository;
import com.honsoft.shopmall.repository.TeamRepository;

@Controller
@RequestMapping("/teams")
public class TeamController {
	private static Logger logger = LoggerFactory.getLogger(TeamController.class);
	
	
	private final TeamRepository teamRepository;
	private final PlayerRepository playerRepository;
	
	public TeamController(TeamRepository teamRepository, PlayerRepository playerRepository) {
		this.teamRepository = teamRepository;
		this.playerRepository = playerRepository;
	}

	@GetMapping
	public String getAllTeams(Model m) {
		logger.info("getAllTeams started.");
		
		Team team = new Team();
		team.setName("축구팀");
		teamRepository.save(team);
		
		Player player = new Player();
		player.setName("홍길동");
		player.setTeam(team);
		
		playerRepository.save(player);
		
		List<Team> teamList = teamRepository.findAll();
		List<Player> playerList = playerRepository.findAll();
		
		m.addAttribute("playerList",playerList);
		m.addAttribute("teamList",teamList);
		
		return "teams/teamList";
	}
}
