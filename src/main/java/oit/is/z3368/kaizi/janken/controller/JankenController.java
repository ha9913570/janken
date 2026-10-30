package oit.is.z3368.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z3368.kaizi.janken.model.Match;
import oit.is.z3368.kaizi.janken.model.MatchInfo;
import oit.is.z3368.kaizi.janken.model.MatchInfoMapper;
import oit.is.z3368.kaizi.janken.model.User;
import oit.is.z3368.kaizi.janken.model.UserMapper;
import oit.is.z3368.kaizi.janken.service.AsyncKekka;
import oit.is.z3368.kaizi.janken.model.MatchMapper;

@Controller
public class JankenController {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MatchMapper matchMapper;

  @Autowired
  private MatchInfoMapper matchInfoMapper;

  @Autowired
  private AsyncKekka asyncKekka;

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

    MatchInfo activeMatchInfo = matchInfoMapper.selectActiveMatchInfoByUser2(user2.getId());
    if (activeMatchInfo == null) {
      activeMatchInfo = new MatchInfo();
      activeMatchInfo.setUser1(user1.getId());
      activeMatchInfo.setUser2(user2.getId());
      activeMatchInfo.setUser1Hand(hand);
      activeMatchInfo.setIsActive(true);

      matchInfoMapper.insertMatchInfo(activeMatchInfo);
    } else {
      Match match = new Match();
      match.setUser1(activeMatchInfo.getUser1());
      match.setUser2(activeMatchInfo.getUser2());
      match.setUser1Hand(activeMatchInfo.getUser1Hand());
      match.setUser2Hand(hand);
      match.setIsActive(true);

      matchMapper.insertMatch(match);
      matchInfoMapper.updateMatchInfoNotActive(activeMatchInfo.getId());
    }
    model.addAttribute("matchInfo", activeMatchInfo);
    return "wait.html";
  }

  @GetMapping("/fight/stream")
  public SseEmitter showFightStream() {
    final SseEmitter emitter = new SseEmitter(60000L);
    try {
      asyncKekka.updateKekka(emitter);
    } catch (Exception e) {
      emitter.complete();
    }
    return emitter;
  }
}
