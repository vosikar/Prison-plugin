package me.tox1que.prison.managers;

import me.tox1que.prison.Prison;
import me.tox1que.prison.utils.abstracts.BaseManager;
import me.tox1que.prison.utils.users.Rank;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class RankManager extends BaseManager{

    private final Map<Integer, Rank> ranks;

    public RankManager(){
        ranks = new HashMap<>();
    }

    @Override
    public void load(){
        main.getDatabaseManager().loadRanks(result -> {
            while(result.next()){
                Rank rank = new Rank(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("price")
                );
                ranks.put(rank.getId(), rank);
            }
        });
    }

    public @Nullable Rank getRank(int id){
        return ranks.getOrDefault(id, null);
    }
}
