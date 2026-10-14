package oit.is.z3368.kaizi.janken.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3368.kaizi.janken.model.Entry;
import oit.is.z3368.kaizi.janken.model.Janken;

@Controller
@RequestMapping("/janken")
public class JankenController {

  @Autowired
  private Entry entry;

  @GetMapping
  public String showJanken(ModelMap model, Principal prin) {
    String loginUser = prin.getName();
    entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);

    return "janken.html";
  }

  @GetMapping(params = "hand")
  public String playJanken(@RequestParam String hand, ModelMap model) {
    Janken janken = new Janken(hand);
    model.addAttribute("janken", janken);
    return "janken.html";
  }

  // @PostMapping
  // public String enterRoom(@RequestParam String userName, ModelMap model) {
  // model.addAttribute("userName", userName);
  // return "janken.html";
  // }
}
