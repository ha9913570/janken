package oit.is.z3368.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {
  @Select("SELECT * FROM matchinfo WHERE isActive = true;")
  ArrayList<MatchInfo> selectActiveMatchInfos();

  @Select("SELECT * FROM matchinfo WHERE user1 = #{user2} AND isActive = true;")
  MatchInfo selectActiveMatchInfoByUser2(Integer user2);

  @Insert("INSERT INTO matchinfo (user1, user2, user1Hand, isActive) VALUES (#{user1}, #{user2}, #{user1Hand}, #{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchInfo(MatchInfo matchInfo);

  @Update("UPDATE matchinfo SET isActive = false WHERE id = #{id};")
  void updateMatchInfoNotActive(Integer id);
}
