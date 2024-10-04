package me.tox1que.prison.managers;

import me.tox1que.prison.utils.abstracts.BaseManager;
import me.tox1que.prison.utils.users.User;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserManager extends BaseManager{

    private final List<User> users;

    public UserManager(){
        users = new ArrayList<>();
    }

    @Override
    public void load(){
    }

    public void loadUser(Player player){
        main.getDatabaseManager().loadUser(player, result -> {
            User user;
            if(result.first()){
                user = new User(
                        player,
                        result.getInt("blocks_mined"),
                        main.getRankManager().getRank(result.getInt("rank")),
                        result.getInt("prestige")
                );
            }else{
                user = new User(
                        player,
                        0,
                        main.getRankManager().getRank(1),
                        0
                );
            }
            users.add(user);
            main.getScoreboardManager().createPlayerScoreboard(player);
        });
    }

    public void unloadUser(Player player){
        unloadUser(getUser(player));
    }

    public void unloadUser(User user){
        users.remove(user);
        main.getScoreboardManager().removePlayer(user.getPlayer());
    }

    public @Nullable User getUser(Player player){
        for(User user:users){
            if(user.getPlayer() == player)
                return user;
        }
        return null;
    }
}
