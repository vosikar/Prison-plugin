package me.tox1que.prison.utils.ItemBuilder;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.tox1que.prison.Prison;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder{

    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private ArrayList<ItemProperty> properties = new ArrayList<>();
    private String hdb = null;

    public ItemBuilder(Material material){
        this(material, 1, (byte) 0);
    }

    public ItemBuilder(Material material, int amount){
        this(material, amount, (byte) 0);
    }

    public ItemBuilder(ItemStack stack){
        itemStack = stack;
    }

    public ItemBuilder(Material material, int amount, byte data){
        itemStack = new ItemStack(material, amount, data);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, byte data){
        this(material, 1, data);
    }

    public ItemBuilder setSkullOwner(Player owner){
        return setSkullOwner(owner.getName());
    }

    public ItemBuilder setSkullOwner(String owner){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta instanceof SkullMeta){
            //((SkullMeta) itemMeta).setOwningPlayer(Bukkit.getOfflinePlayer(owner));
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public ItemBuilder setColor(Color color){
        if(itemStack.getType().toString().contains("LEATHER")){
            LeatherArmorMeta armorMeta = (LeatherArmorMeta) getItemMeta();
            armorMeta.setColor(color);
            this.itemMeta = armorMeta;
        }
        return this;
    }

    public ItemBuilder setCMD(int data){
        getItemMeta().setCustomModelData(data);
        return this;
    }

    public ItemBuilder removeProperty(ItemProperty property){
        properties.remove(property);
        return this;
    }

    public ArrayList<ItemProperty> getProperties(){
        return properties;
    }

    public ItemBuilder setHDB(String id){
        this.hdb = id;
        return this;
    }

    public ItemBuilder setData(byte data){
        itemStack.setDurability(data);
        return this;
    }

    public ItemBuilder setMaterial(Material material){
        itemStack.setType(material);
        return this;
    }

    public ItemBuilder setAmount(int amount){
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        getItemMeta().addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment){
        getItemMeta().removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder hideEnchants(){
        addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder showEnchants(){
        removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... itemFlags){
        getItemMeta().addItemFlags(itemFlags);
        return this;
    }

    public ItemBuilder addPersistentData(String key, PersistentDataType type, Object value){
        if(!getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Prison.getInstance(), key), type)){
            getItemMeta().getPersistentDataContainer().set(new NamespacedKey(Prison.getInstance(), key), type, value);
        }
        return this;
    }

    public ItemBuilder setDisplayName(String displayName){
        getItemMeta().setDisplayName(CustomChatColor.getFinalMessage(displayName));
        return this;
    }

    public ItemBuilder addLore(String text){
        if(text == null)
            return this;
        List<String> itemLores = new ArrayList<>();
        if(getItemMeta().getLore() != null && getItemMeta().getLore().size() > 0){
            itemLores = getItemMeta().getLore();
        }
        itemLores.add(CustomChatColor.getFinalMessage(text));
        getItemMeta().setLore(itemLores);
        return this;
    }

    public ItemBuilder addLore(String... lore){
        for(String s : lore){
            addLore(s);
        }
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable){
        getItemMeta().setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder setLores(String... lores){
        getItemMeta().setLore(Arrays.asList(lores));
        return this;
    }

    public ItemBuilder removeItemFlags(ItemFlag... itemFlag){
        getItemMeta().removeItemFlags(itemFlag);
        return this;
    }

    public ItemBuilder setGlowing(boolean glowing){
        if(glowing){
            addEnchantment(Enchantment.LURE, 1);
            hideEnchants();
        }else{
            removeEnchantment(Enchantment.LURE);
            hideEnchants();
        }
        return this;
    }

    public ItemMeta getItemMeta(){
        return itemMeta;
    }

    public ItemStack getItemStack(){
        return build();
    }

    public ItemStack build(){
        if(hdb != null){
            ItemStack head = new HeadDatabaseAPI().getItemHead(hdb);
            ItemMeta meta = head.getItemMeta();
            if(meta.getDisplayName() != null)
                meta.setDisplayName(getItemMeta().getDisplayName());
            if(getItemMeta().getLore() != null)
                meta.setLore(getItemMeta().getLore());
            if(getItemMeta().hasCustomModelData())
                meta.setCustomModelData(getItemMeta().getCustomModelData());
            head.setAmount(itemStack.getAmount());
            head.setItemMeta(meta);
            return head;
        }
        itemStack.setItemMeta(getItemMeta());
        return itemStack;
    }

    public ItemBuilder giveItem(Inventory inv){
        inv.addItem(getItemStack());
        return this;
    }

    public ItemBuilder giveItem(Inventory inv, int position){
        inv.setItem(position, getItemStack());
        return this;
    }

    public ItemBuilder fakeEnchant(){
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.SOUL_SPEED, 1, true);
        getItemStack().setItemMeta(meta);
        return this;
    }
}

