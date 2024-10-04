package me.tox1que.prison.utils.users;

import me.tox1que.prison.Prison;
import me.tox1que.prison.managers.DatabaseManager;
import me.tox1que.prison.utils.PlayerUtils;
import me.tox1que.prison.utils.Utils;
import me.tox1que.prison.managers.RankManager;
import org.bukkit.entity.Player;

public class User{

    private final Player player;
    private int blocksMined;
    private Rank rank;
    private int prestige;

    public User(Player player, int blocksMined, Rank rank, int prestige){
        this.player = player;
        this.blocksMined = blocksMined;
        this.rank = rank;
        this.prestige = prestige;
    }

    public void addBlocksMined(int i){
        blocksMined += i;
    }

    public void levelUp(){
        Rank next = Prison.getInstance().getRankManager().getRank(rank.getId() + 1);
        if(next == null){
            player.sendMessage(Utils.getPluginMessage("Jsi na poslední úrovni, zbývá ti jen zvýšení přestiže :)"));
            return;
        }
        this.rank = next;
        update();
        PlayerUtils.sendMessage(player, "Gratulace! Dostal ses do úrovně %s[pc].", rank.getName());
    }

    public void prestigeUp(){
        prestige++;
        this.rank = Prison.getInstance().getRankManager().getRank(1);
        update();
        PlayerUtils.sendMessage(player, "Gratulace! Dostal ses na prestiž %d!", prestige);
    }

    public void update(){
        Prison.getInstance().getDatabaseManager().updateUser(this);
    }

    public Player getPlayer(){
        return player;
    }

    public int getBlocksMined(){
        return blocksMined;
    }

    public Rank getRank(){
        return rank;
    }

    public int getPrestige(){
        return prestige;
    }

    public String getProgress(){
        Rank next = Prison.getInstance().getRankManager().getRank(rank.getId()+1);
        int nextRequired = next == null ? 0 : next.getPrice();
        return Utils.getPercentage(getBlocksMined()-rank.getPrice(), nextRequired-rank.getPrice());
    }
}
