package oit.is.z3368.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3368.kaizi.janken.model.Janken;
import oit.is.z3368.kaizi.janken.model.Match;
import oit.is.z3368.kaizi.janken.model.User;
import oit.is.z3368.kaizi.janken.model.UserMapper;
import oit.is.z3368.kaizi.janken.model.MatchMapper;

@Controller
@RequestMapping("/janken")
public class JankenController {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MatchMapper matchMapper;

  @GetMapping
  public String showJanken(ModelMap model, Principal prin) {
    ArrayList<User> users = userMapper.selectAllUsers();
    ArrayList<Match> matches = matchMapper.selectAllMatches();
    model.addAttribute("users", users);
    model.addAttribute("matches", matches);

    return "janken.html";
  }

  @GetMapping(params = "hand")
  public String playJanken(@RequestParam String hand, ModelMap model) {
    Janken janken = new Janken(hand);
    model.addAttribute("janken", janken);
    return "janken.html";
  }
}
