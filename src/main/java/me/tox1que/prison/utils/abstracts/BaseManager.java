package me.tox1que.prison.utils.abstracts;

import me.tox1que.prison.Prison;

public abstract class BaseManager{

    protected final Prison main;

    public BaseManager(){
        this.main = Prison.getInstance();
    }

    public abstract void load();
}
