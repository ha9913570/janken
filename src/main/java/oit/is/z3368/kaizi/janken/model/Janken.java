package oit.is.z3368.kaizi.janken.model;

public class Janken {
  String userHand;
  String cpuHand;

  public Janken(String userHandString) {
    this.userHand = userHandString;
    cpuHand = "Gu";
  }

  public String getUserHand() {
    return userHand;
  }

  public String getCpuHand() {
    return cpuHand;
  }

  public String getResult() {
    if (userHand.equals(cpuHand)) {
      return "Draw";
    } else if ((userHand.equals("Gu") && cpuHand.equals("Choki")) ||
        (userHand.equals("Choki") && cpuHand.equals("Pa")) ||
        (userHand.equals("Pa") && cpuHand.equals("Gu"))) {
      return "You Win!";
    } else {
      return "You Lose!";
    }
  }
}
