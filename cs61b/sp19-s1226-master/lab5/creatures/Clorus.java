package creatures;

import huglife.*;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Clorus extends Creature{
    private int r;
    private int g;
    private int b;
    public Clorus(double e) {
        super ("clorus");
        r = 0;
        b = 0;
        g = 0;
        energy = e;
    }
    public Clorus() {
        this(1);
    }
    public Color color() {
        return color(34, 0, 231);
    }
    public void move() {
        energy -= 0.03;
    }
    public void stay() {
        energy -= 0.01;
    }
    public void attack(Creature c) {
        energy += c.energy();
    }
    public Clorus replicate() {
        energy /= 2;
        return new Clorus(energy);
    }
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plips = new ArrayDeque<>();
        boolean anyPlip = false;
        for (Direction d: neighbors.keySet()) {
            if (neighbors.get(d).name().equals("empty")) {
                emptyNeighbors.addFirst(d);
            }
        }
        for (Direction d: neighbors.keySet()) {
            if (neighbors.get(d).name().equals("plip")) {
                plips.addFirst(d);
                anyPlip = true;
            }
        }
        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }
        if (anyPlip == true) {
            return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(plips));
        }
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(emptyNeighbors));
        }
        return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(emptyNeighbors));
    }
}
