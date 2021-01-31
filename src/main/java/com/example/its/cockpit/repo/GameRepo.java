package com.example.its.cockpit.repo;

import com.example.its.cockpit.model.Game;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Repository
public class GameRepo {

    private Map<String, Pair<Game, Lock>> store = new HashMap<>();

    public String add(Game game) {
        String uuid = UUID.randomUUID().toString();
        game.setId(uuid);
        store.put(uuid, new Pair<>(game, new ReentrantLock()));
        return uuid;
    }

    public List<Game> all() {
        return store.values().stream().map(p -> p.first).collect(Collectors.toList());
    }

    public Game read(String id) {
        return store.get(id).first;
    }

    public Game readLocked(String id) {
        Pair<Game, Lock> p = store.get(id);
        p.second.lock();
        return p.first;
    }

    public void release(String id) {
        store.get(id).second.unlock();
    }

    public void writeBack(Game game) {
        Pair<Game, Lock> p = store.get(game.getId());
        p.first = game;
        p.second.unlock();
    }


    private class Pair<K, V> {
        K first;
        V second;

        public Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }
    }
}
