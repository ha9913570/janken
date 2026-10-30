package oit.is.z3368.kaizi.janken.service;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z3368.kaizi.janken.model.Match;
import oit.is.z3368.kaizi.janken.model.MatchMapper;

@Service
public class AsyncKekka {
  private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

  @Autowired
  MatchMapper matchMapper;

  @Async
  public void updateKekka(SseEmitter emitter) throws Exception {
    emitters.add(emitter);
    try {
      while (true) {// 無限ループ
        synchronized (emitters) {
          ArrayList<Match> matches = matchMapper.selectActiveMatches();
          if (!matches.isEmpty()) {
            for (Match match : matches) {
              for (SseEmitter waitingEmitter : emitters) {
                waitingEmitter.send(match);
              }
              matchMapper.updateMatchInactive(match.getId());
            }
            for (SseEmitter waitingEmitter : emitters) {
              waitingEmitter.complete();
            }
            emitters.clear();
            return;
          }
        }
        TimeUnit.MILLISECONDS.sleep(500);
      }
    } catch (Exception e) {
      emitter.completeWithError(e);
    } finally {
      emitters.remove(emitter);
    }
  }
}
