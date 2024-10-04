package me.tox1que.prison.utils.users;

import java.util.List;

public class Gang{

    private final String name;
    private final List<User> members;

    public Gang(String name, List<User> users){
        this.name = name;
        this.members = users;
    }

    public String getName(){
        return name;
    }

    public List<User> getMembers(){
        return members;
    }
}
