package Genome;

import Evolution.Constants;
import Genome.enums.Activation;

import java.util.ArrayList;
import java.util.List;

/** The Genome representation of a node */
//todo made public for testing
public class node extends Gene {
    /*
     * must contain:
     * - innovation number:
     *      - should be the same as synapse split if hidden node ( >= 0)
     *      - if input/output node, should range from -(inputNum+outputNum) to -1
     * - ActivationFunction enum:
     *      - should be null if it's input or output
     *      - can be any hiddenAF enums if its hidden, outputAF are reserved to used in Constants file
     * - bias
     * - boolean activated: true if this node's output is > 0, false otherwise
     * - int array of outgoing connections (index of edges)
     * - int array of incoming connections (index of edges)
     */

    /** The Activation Function of this neuron */
    Activation activationFunction;

    /** The bias value of this neuron */
    private double bias;

    /**
     * True if neuron's output is > 0, false otherwise.
     * <br>Used in visualizing individual neuron firing
     */
    private boolean activated;

    /** A list of local indices of incoming/outgoing edges to this node */
    private final List<Integer> incomingConnections = new ArrayList<>(),
            outgoingConnections = new ArrayList<>();

    double x,y;

    public node(int innovationID, Activation activationFunction, double bias) {
        this.innovationID = innovationID;
        this.activationFunction = activationFunction;
        this.bias = bias;
        this.activated = false;
        this.x = 0;
        this.y = 0;
    }

    /**
     * Applies the bias and activation function to the given {@code input}.<br>
     * Changes the {@code activated} var to the appropriate value
     */
    @Override
    double calculateOutput(double input) {
        double output = activationFunction.calculate(input + bias);
        activated = output > 0;
        return output;
    }

    @Override
    void addValue(double deltaValue) {
        this.bias += deltaValue;
    }

    /** Returns true if the last output of this neuron is > 0, false otherwise */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Returns the local indices of all edges pointing into this node.
     * <br>Used for calculating derivative terms in back-propagation
     */
    List<Integer> getIncomingEdgeIndices() {
        return incomingConnections;
    }

    /**
     * Returns the local indices of all edges pointing out from this node.
     * <br>Used for feed-forward calculation
     */
    List<Integer> getOutgoingEdgeIndices() {
        return outgoingConnections;
    }

    /**
     * Attempts to add {@code index} to the array of outgoing edge indices.
     * Doesn't do anything if the array already contains the index
     */
//todo made public for testing
    public void addOutgoingEdgeIndex(int index) {
        if (outgoingConnections.contains(index)) return;
        outgoingConnections.add(index);
    }

    /**
     * Attempts to add {@code index} to the array of incoming edge indices.
     * Doesn't do anything if the array already contains the index
     */
//todo made public for testing
    public void addIncomingEdgeIndex(int index) {
        if (incomingConnections.contains(index)) return;
        incomingConnections.add(index);
    }

    /** Returns the bias of this node */
    //todo made public for testing
    public double getBias(){return bias;}

    /**
     * Shifts the Bias of this node by a random amount
     */
    public void shiftBias(Constants Constants) {
        this.bias += Constants.mutationBiasShiftStrength * (Math.random() * 2 - 1);
    }

    @Override
    public node clone() {
        return new node(innovationID, activationFunction, bias);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof node && ((node) obj).innovationID == innovationID;
    }

    boolean identical(node other){
        return other.innovationID == innovationID && other.activationFunction == activationFunction &&
                other.activated == activated && other.x == x && other.y == y &&
                other.bias == bias && other.incomingConnections.equals(incomingConnections) &&
                other.outgoingConnections.equals(outgoingConnections);
    }
}
