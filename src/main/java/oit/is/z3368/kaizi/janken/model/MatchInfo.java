package oit.is.z3368.kaizi.janken.model;

public class MatchInfo {
  private Integer id;
  private int user1;
  private int user2;
  private String user1hand;
  private Boolean isActive;

  // getter
  public Integer getId() {
    return id;
  }

  public int getUser1() {
    return user1;
  }

  public int getUser2() {
    return user2;
  }

  public String getUser1Hand() {
    return user1hand;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  // setter
  public void setId(Integer id) {
    this.id = id;
  }

  public void setUser1(int user1) {
    this.user1 = user1;
  }

  public void setUser2(int user2) {
    this.user2 = user2;
  }

  public void setUser1Hand(String user1hand) {
    this.user1hand = user1hand;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }
}
