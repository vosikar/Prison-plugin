package me.tox1que.prison.managers;

import me.saves.JARVIS.Jarvis;
import me.saves.JARVIS.ScoreboardAPI.CScoreboardLine;
import me.saves.JARVIS.ScoreboardAPI.PlayerBoard;
import me.tox1que.prison.Prison;
import me.tox1que.prison.utils.Utils;
import me.tox1que.prison.utils.abstracts.BaseManager;
import me.tox1que.prison.utils.users.Rank;
import me.tox1que.prison.utils.users.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager extends BaseManager{

    private me.saves.JARVIS.ScoreboardAPI.ScoreboardManager scoreboardManager;

    private Map<Player, PlayerBoard> appliedBoards;
    private Map<Player, PlayerBoard> scoreboards;
    private Map<Player, Integer> scoreboardsTasks;

    private String TITLE;

    public ScoreboardManager(){
        super();
    }

    @Override
    public void load(){
        this.scoreboardManager = Jarvis.getPlugin().getScoreboardManager();
        this.scoreboards = new HashMap<>();
        this.scoreboardsTasks = new HashMap<>();
        this.TITLE = main.getSecondaryColor()+"§lMineHub.cz";
    }

    public void createPlayerScoreboard(Player player){
        Bukkit.getScheduler().runTaskLater(main, () -> {
            PlayerBoard board = new PlayerBoard(player, TITLE);
            board.setLines(new CScoreboardLine[]{
                    new CScoreboardLine(),
                    new CScoreboardLine("online", board, "§3§l| §3Online: ", "§b0"),
                    new CScoreboardLine("rank", board, "§3§l| §3Rank: ", "§b" + Jarvis.getPlugin().getClientManager().getClient(player).getGroup().getName()),
                    new CScoreboardLine(),
                    new CScoreboardLine("next_level_price", board, "§b$ 0", ""),
                    new CScoreboardLine("next_level_progress", board, Utils.getColoredArrow(0, 1), ""),
                    new CScoreboardLine("level", board, "§3§l| §3Úroveň: ", main.getRankManager().getRank(1).getName()),
                    new CScoreboardLine("prestige", board, "§3§l| §3Prestiž: ", "§b0"),
            });

            board.apply();
            scoreboards.put(player, board);

            int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
                User user = main.getUserManager().getUser(player);
                Rank nextRank = main.getRankManager().getRank(user.getRank().getId()+1);
                String progressArrow;
                String nextPrice;
                if(nextRank != null){
                    progressArrow = user.getRank().getName()+" "+Utils.getColoredArrow((int) Prison.getInstance().getEconomy().getBalance(user.getPlayer()), nextRank.getPrice())+" "+nextRank.getName();
                    nextPrice = "§b$ "+Utils.formatNumber(nextRank.getPrice());
                }else{
                    progressArrow = Utils.getColoredArrow(0, 1);
                    nextPrice = "§b$ -";
                }

                scoreboardManager.updateLine(player, "online", "§3§l| §3Online§3: ", "§b" + main.getServer().getOnlinePlayers().size());
                scoreboardManager.updateLine(player, "next_level_price", Utils.scoreboardTextCenter(nextPrice), "");
                scoreboardManager.updateLine(player, "next_level_progress", Utils.scoreboardTextCenter(progressArrow), "");
                scoreboardManager.updateLine(player, "level", "§3§l| §3Úroveň: ", String.format("§b%s §3[§b%s§3]", user.getRank().getName(), user.getProgress()));
                scoreboardManager.updateLine(player, "prestige", "§3§l| §3Prestiž: ", "§b"+user.getPrestige());
            }, 0, 20 * 3);
            if(scoreboardsTasks.containsKey(player)){
                Bukkit.getScheduler().cancelTask(scoreboardsTasks.get(player));
            }
            scoreboardsTasks.put(player, taskId);
        }, 20);
    }

    public void removePlayer(Player player){
        scoreboards.remove(player);
        if(scoreboardsTasks.containsKey(player))
            Bukkit.getScheduler().cancelTask(scoreboardsTasks.get(player));
        scoreboardsTasks.remove(player);
    }
}
