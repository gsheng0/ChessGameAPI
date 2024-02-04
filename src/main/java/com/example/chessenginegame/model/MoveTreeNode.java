package com.example.chessenginegame.model;

import jakarta.el.FunctionMapper;

import java.util.*;

// strictly 1 -> many
public class MoveTreeNode {
    private final Set<MoveTreeNode> kids;
    private MoveTreeNode dad;
    public final Move move;
    public int depth;

    public MoveTreeNode() {
        this.move = null;
        kids = new HashSet<>();
        dad = null;
        depth = 0;
    }
    public MoveTreeNode(Move move) {
        this.move = move;
        kids = new HashSet<>();
        dad = null;
        depth = 0;
    }
    public MoveTreeNode(Move move, MoveTreeNode dad) {
        this.move = move;
        kids = new HashSet<>();
        setDad(dad);
        depth += dad.depth;
    }
    @Override
    public String toString() {
        return move.toString();
    }
    public Set<MoveTreeNode> getKids() {
        return kids;
    }
    public MoveTreeNode addKid(MoveTreeNode kid) {
        kids.add(kid);
        kid.depth = depth + 1;
        return kid;
    }
    public void setDad(MoveTreeNode newDad) {
        dad = newDad;
        dad.addKid(this);
        depth = newDad.depth + 1;
    }
    public List<MoveTreeNode> findMoveNodes(String uciMove, int depth) {
        List<MoveTreeNode> foundNodes = new ArrayList<>();
        findMoveNodes(this, uciMove, depth, foundNodes);
        return foundNodes;
    }
    public Map<String, Integer> getLeafNodeCounts() {
        Map<String, Integer> perftNumbers = new HashMap<>();
        for (MoveTreeNode moveNode : getKids()) {
            int count = getLeafNodeCounts(moveNode);
            perftNumbers.put(moveNode.move.getUCINotation(), count);
        }
        return perftNumbers;
    }
    public List<List<String>> getUciMovePathToLeafNode(MoveTreeNode node) {
        List<List<String>> pathList = new ArrayList<>();
        Stack<String> pathStack = new Stack<>();
        pathStack.push(node.move.getUCINotation());
        for(MoveTreeNode kid : node.getKids()) {
            pathStack.push(kid.move.getUCINotation());
            getUciMovePathToLeafNode(kid, pathStack, pathList);
            pathStack.pop();
        }
        return pathList;
    }

    private boolean getUciMovePathToLeafNode(MoveTreeNode node, Stack<String> pathStack, List<List<String>> pathList) {
        if (node.getKids().size() == 0) {
            pathList.add(getListFromStack(pathStack));
            return true;
        }
        for (MoveTreeNode kid : node.getKids()) {
            pathStack.push(kid.move.getUCINotation());
            if (getUciMovePathToLeafNode(kid, pathStack, pathList)) {
                break;
            };
        }
        return false;
    }

    private List<String> getListFromStack(Stack<String> stack) {
        List<String> list = new ArrayList<>();
        for(int i=0; i<stack.size(); i++) {
            list.add(stack.elementAt(i));
        }
        return list;
    }
    private void findMoveNodes(MoveTreeNode node, String uciMove, int depth, List<MoveTreeNode> foundNodes) {
        for (MoveTreeNode moveNode : node.getKids()) {
            if (moveNode.move.getUCINotation().equals(uciMove) && moveNode.depth == depth) {
                foundNodes.add(moveNode);
            }
            findMoveNodes(moveNode, uciMove, depth, foundNodes);
        }
    }
    private int getLeafNodeCounts(MoveTreeNode node) {
        int count = node.getKids().size() == 0 ? 1 : 0;
        for (MoveTreeNode moveNode : node.getKids()) {
             count += getLeafNodeCounts(moveNode);
        }
        return count;
    }
}
