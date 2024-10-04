package me.tox1que.prison.managers;

import me.tox1que.prison.utils.abstracts.BaseManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager extends BaseManager{

    private YamlConfiguration database;
    private YamlConfiguration mines;

    public FileManager(){
        super();
    }

    @Override
    public void load(){
        File pluginsFolder = main.getDataFolder();
        if(!pluginsFolder.exists()){
            pluginsFolder.mkdir();
        }

        File databaseFile = new File(pluginsFolder, "database.yml");
        if(!databaseFile.exists()){
            try{
                databaseFile.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        this.database = YamlConfiguration.loadConfiguration(databaseFile);

        File minesFile = new File(pluginsFolder, "mines.yml");
        if(!minesFile.exists()){
            try{
                minesFile.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        this.mines = YamlConfiguration.loadConfiguration(minesFile);
    }

    public YamlConfiguration getDatabaseConfig(){
        return database;
    }
}
