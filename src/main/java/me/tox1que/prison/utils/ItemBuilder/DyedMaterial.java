package me.tox1que.prison.utils.ItemBuilder;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class DyedMaterial{

    private Material dye;
    private ChatColor chatColor;
    private Material banner;
    private Material wallBanner;
    private Material bed;
    private Material carpet;
    private Material concrete;
    private Material concretePowder;
    private Material terracotta;
    private Material glazedTerracotta;
    private Material shulkerBox;
    private Material stainedGlass;
    private Material stainedGlassPane;
    private Material wool;

    public DyedMaterial(Material dye, ChatColor chatColor){
        this.dye = dye;
        this.chatColor = chatColor;
    }

    public Material getDye(){
        return dye;
    }

    public ChatColor getChatColor(){
        return chatColor;
    }

    public Material getBanner(){
        return banner;
    }

    public void setBanner(Material banner){
        this.banner = banner;
    }

    public Material getWallBanner(){
        return wallBanner;
    }

    public void setWallBanner(Material wallBanner){
        this.wallBanner = wallBanner;
    }

    public Material getBed(){
        return bed;
    }

    public void setBed(Material bed){
        this.bed = bed;
    }

    public Material getCarpet(){
        return carpet;
    }

    public void setCarpet(Material carpet){
        this.carpet = carpet;
    }

    public Material getConcrete(){
        return concrete;
    }

    public void setConcrete(Material concrete){
        this.concrete = concrete;
    }

    public Material getConcretePowder(){
        return concretePowder;
    }

    public void setConcretePowder(Material concretePowder){
        this.concretePowder = concretePowder;
    }

    public Material getTerracotta(){
        return terracotta;
    }

    public void setTerracotta(Material terracotta){
        this.terracotta = terracotta;
    }

    public Material getGlazedTerracotta(){
        return glazedTerracotta;
    }

    public void setGlazedTerracotta(Material glazedTerracotta){
        this.glazedTerracotta = glazedTerracotta;
    }

    public Material getShulkerBox(){
        return shulkerBox;
    }

    public void setShulkerBox(Material shulkerBox){
        this.shulkerBox = shulkerBox;
    }

    public Material getStainedGlass(){
        return stainedGlass;
    }

    public void setStainedGlass(Material stainedGlass){
        this.stainedGlass = stainedGlass;
    }

    public Material getStainedGlassPane(){
        return stainedGlassPane;
    }

    public void setStainedGlassPane(Material stainedGlassPane){
        this.stainedGlassPane = stainedGlassPane;
    }

    public Material getWool(){
        return wool;
    }

    public void setWool(Material wool){
        this.wool = wool;
    }
}
