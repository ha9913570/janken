package oit.is.z3368.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3368.kaizi.janken.model.Match;
import oit.is.z3368.kaizi.janken.model.MatchInfo;
import oit.is.z3368.kaizi.janken.model.MatchInfoMapper;
import oit.is.z3368.kaizi.janken.model.User;
import oit.is.z3368.kaizi.janken.model.UserMapper;
import oit.is.z3368.kaizi.janken.model.MatchMapper;

@Controller
public class JankenController {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MatchMapper matchMapper;

  @Autowired
  private MatchInfoMapper matchInfoMapper;

  @GetMapping("/janken")
  public String showJanken(ModelMap model) {
    ArrayList<User> users = userMapper.selectAllUsers();
    ArrayList<Match> matches = matchMapper.selectAllMatches();
    ArrayList<MatchInfo> activeMatchInfos = matchInfoMapper.selectActiveMatchInfos();
    model.addAttribute("users", users);
    model.addAttribute("matches", matches);
    model.addAttribute("activeMatchInfos", activeMatchInfos);

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
    MatchInfo matchInfo = new MatchInfo();
    matchInfo.setUser1(user1.getId());
    matchInfo.setUser2(user2.getId());
    matchInfo.setUser1Hand(hand);
    matchInfo.setIsActive(true);

    matchInfoMapper.insertMatchInfo(matchInfo);

    return "wait.html";
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
