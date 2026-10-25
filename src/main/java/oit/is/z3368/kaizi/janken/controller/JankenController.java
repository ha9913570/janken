package oit.is.z3368.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3368.kaizi.janken.model.Match;
import oit.is.z3368.kaizi.janken.model.User;
import oit.is.z3368.kaizi.janken.model.UserMapper;
import oit.is.z3368.kaizi.janken.model.MatchMapper;

@Controller
public class JankenController {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MatchMapper matchMapper;

  @GetMapping("/janken")
  public String showJanken(ModelMap model) {
    ArrayList<User> users = userMapper.selectAllUsers();
    ArrayList<Match> matches = matchMapper.selectAllMatches();
    model.addAttribute("users", users);
    model.addAttribute("matches", matches);

    return "janken.html";
  }

  @GetMapping("/match")
  public String showMatch(@RequestParam Integer id, ModelMap model) {
    User user2 = userMapper.selectUserById(id);
    model.addAttribute("user2", user2);

    return "match.html";
  }

  @GetMapping("/fight")
  public String showFight(@RequestParam Integer id, @RequestParam String hand, Principal prin, ModelMap model) {
    User user1 = userMapper.selectUserByName(prin.getName());
    User user2 = userMapper.selectUserById(id);
    Match match = new Match();
    match.setUser1(user1.getId());
    match.setUser2(user2.getId());
    match.setUser1Hand(hand);
    matchMapper.insertMatch(match);

    model.addAttribute("user2", user2);
    model.addAttribute("result", JankenJudge(hand, match.getUser2Hand()));
    model.addAttribute("match", match);

    return "match.html";
  }

  // じゃんけんの勝敗を判定する関数
  private String JankenJudge(String hand1, String hand2) {
    if (hand1.equals(hand2)) {
      return "Draw";
    } else if ((hand1.equals("Gu") && hand2.equals("Choki")) ||
        (hand1.equals("Choki") && hand2.equals("Pa")) ||
        (hand1.equals("Pa") && hand2.equals("Gu"))) {
      return "Win";
    } else {
      return "Lose";
    }
  }
}
