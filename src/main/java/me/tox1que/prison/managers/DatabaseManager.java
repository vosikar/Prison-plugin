package me.tox1que.prison.managers;

import me.tox1que.prison.Prison;
import me.tox1que.prison.utils.SQL.SQLRunnable;
import me.tox1que.prison.utils.SQL.SQLUtils;
import me.tox1que.prison.utils.abstracts.BaseManager;
import me.tox1que.prison.utils.users.User;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager extends BaseManager{

    private String user;
    private String password;
    private String url;

    public DatabaseManager(){
        super();
    }

    @Override
    public void load(){
        YamlConfiguration config = main.getFileManager().getDatabaseConfig();

        String ip = config.getString("ip");
        String database = config.getString("database");
        this.url = "jdbc:mysql://" + ip + ":3306/" + database + "?autoReconnect=true&allowMultiQueries=true&useUnicode=yes&characterEncoding=UTF-8";
        this.user = config.getString("user");
        this.password = config.getString("password");
    }

    public Connection getNewConnection(){
        try{
            return DriverManager.getConnection(this.url, this.user, this.password);
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void loadUser(Player player, SQLRunnable runnable){
        SQLUtils.selectPlayer(player.getName(), runnable);
    }

    public void updateUser(User user){
        Map<String, Object> data = new HashMap<>();
        data.put("player", user.getPlayer().getName());
        data.put("blocks_mined", user.getBlocksMined());
        data.put("rank", user.getRank().getId());
        data.put("prestige", user.getPrestige());
        SQLUtils.insertOrUpdate("players", data);
    }

    public void loadRanks(SQLRunnable runnable){
        SQLUtils.select("ranks", runnable);
    }
}
