package com.example.chessenginegame.model;

import java.util.HashSet;
import java.util.Set;

// strictly 1 -> many
public class MoveTreeNode {
    private final Set<MoveTreeNode> kids;
    private MoveTreeNode dad;
    public final Move move;

    public MoveTreeNode() {
        this.move = null;
        kids = new HashSet<>();
        dad = null;
    }

    public MoveTreeNode(Move move) {
        this.move = move;
        kids = new HashSet<>();
        dad = null;
    }
    public MoveTreeNode(Move move, MoveTreeNode dad) {
        this.move = move;
        kids = new HashSet<>();
        setDad(dad);
    }
    public Set<MoveTreeNode> getKids() {
        return kids;
    }
    public MoveTreeNode addKid(MoveTreeNode kid) {
        kids.add(kid);
        return kid;
    }
    public void setDad(MoveTreeNode newDad) {
        dad = newDad;
        dad.addKid(this);
    }
}
