package bearmaps.hw4;
import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static bearmaps.hw4.SolverOutcome.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome = UNSOLVABLE;
    private double solutionWeight = 0;
    private List<Vertex> solution = new ArrayList<>();
    private double timeSpent;
    private int numStatesExplored = 0;
    HashMap<Vertex, Double> distTo = new HashMap<>();
    HashMap<Vertex, Vertex> edgeTo = new HashMap<>();
    DoubleMapPQ<Vertex> pq = new DoubleMapPQ<>();
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        pq.add(start, input.estimatedDistanceToGoal(start, end));
        distTo.put(start, .0);
        while (pq.size() > 0) {
            timeSpent = sw.elapsedTime();
            if (timeSpent > timeout) {
                outcome = TIMEOUT;
                break;
            }
            Vertex smallest = pq.removeSmallest();
            numStatesExplored++;
            if (smallest.equals(end)) {
                solutionWeight = distTo.get(smallest);
                //System.out.println(Collections.singletonList(edgeTo));
                Vertex curr = end;
                while (!curr.equals(start)) {
                    solution.add(curr);
                    curr = edgeTo.get(curr);
                }
                solution.add(start);
                Collections.reverse(solution);
                outcome = SOLVED;
                break;
            }
            for (WeightedEdge<Vertex> v: input.neighbors(smallest)) {
                relax(v, input, end);
            }
        }
        if (!outcome.equals(SOLVED)) {
            solution = new ArrayList<>();
            solutionWeight = 0;
        }
        timeSpent =  sw.elapsedTime();
    }
    private void relax(WeightedEdge<Vertex> e, AStarGraph<Vertex> input, Vertex end) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();
        if (!distTo.containsKey(q)) {
            distTo.put(q, distTo.get(p) + w);
            edgeTo.put(q, p);
            pq.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
        }
        if (distTo.get(p) + w < distTo.get(q)) {
            distTo.put(q, distTo.get(p) + w);
            if (pq.contains(q)) {
                pq.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
            } else {
                pq.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
            }
        }

    }
    @Override
    public SolverOutcome outcome() {
        return outcome;
    }
    @Override
    public List<Vertex> solution() {
        return solution;
    }
    @Override
    public double solutionWeight() {
        return solutionWeight;
    }
    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }
    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
