package Evolution;

import Genome.*;

public class Agent implements WeightedRandom{
    /**
     *
     */
    private NN genome;

    /**
     *
     */
    private double score;

    public Agent(){
        this.score=0;
        this.genome = Innovation.getDefaultNode();
    }

    /** Resets the score of this Agent */
    public void reset(){
        score=0;
    }

    /** Returns the score of this Agent as input to the NEAT algorithm */
    @Override
    public double getScore() {
        return score;
    }

    /**
     * Removes the genome of this Agent.
     * @returns whether this Agent had a genome to begin with or not.
     */
    public boolean removeGenome() {
        if(!hasGenome()) return false;
        genome = null;
        return true;
    }

    /**
     * Repopulates the current Agent's genome with the crossover result of {@code parent1} and {@code parent2}
     * @throws RuntimeException if parent 1 or 2 are missing genome or child already has genome
     */
    public static void crossover(Agent parent1, Agent parent2, Agent child) {
        if(!parent1.hasGenome() || !parent2.hasGenome() || child.hasGenome()) throw new RuntimeException("Genome Exception");
        child.genome = NN.crossover(parent1.genome,parent2.genome,parent1.score,parent2.score);
    }

    /** Returns whether this Agent has a genome or not */
    public boolean hasGenome() {
        return genome != null;
    }

    /** Mutates the genome of this Agent */
    public void mutate() {
        genome.mutate();
    }

    /**
     * Compares the genome of both Agents
     * @return the value of the comparison
     * @throws RuntimeException when either Agent is missing a genome
     */
    public double compare(Agent newAgent) {
        if(!hasGenome() || !newAgent.hasGenome()) throw new RuntimeException("Genome Exception");
        return genome.compare(newAgent.genome);
    }

    /** Calculates the weighted output of the values using the Neural Network currently in this Agent */
    public double[] calculateWeightedOutput(double[] input) {
        return genome.calculateWeightedOutput(input);
    }

    @Override
    public String toString(){
        return genome.toString();
    }

}