package Genome.Render;

import Genome.*;

public class Panel {

    private NN NN;

    public Panel() {
    }

    public void setGenome(NN NN) {
        this.NN = NN;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0,0,10000,10000);
        g.setColor(Color.black);
        g.fillRect(0,0,10000,10000);
        double maxBias=1,maxWeight=1;
        for(node n : NN.nodes)if(n.bias>maxBias)maxBias=n.bias;
        for(synapse s : NN.synapses)if(s.weight>maxWeight)maxWeight=s.weight;

        for(synapse s: NN.synapses){
            paintConnection(s, (Graphics2D) g,maxWeight);
        }


        for(node n: NN.nodes) {
            paintNode(n, (Graphics2D) g,maxBias);
        }

    }

    private void paintNode(node n, Graphics2D g, double maxBias){
        g.setColor(Color.gray);
        g.setStroke(new BasicStroke((float) (1+4 * Math.abs(n.bias/maxBias))));
        g.drawOval((int)(this.getWidth() * n.x) - 10,
                (int)(this.getHeight() * n.y) - 10,20,20);
    }

    private void paintConnection(synapse s, Graphics2D g,double maxWeight){
        g.setColor(s.enabled ? (s.weight<0 ? Color.red : Color.green): Color.gray);
        g.setStroke(new BasicStroke((float) (1+4 * Math.abs((s.weight)/maxWeight))));
        g.drawLine(
                (int)(this.getWidth() * s.from.x),
                (int)(this.getHeight() * s.from.y),
                (int)(this.getWidth() * s.to.x),
                (int)(this.getHeight() * s.to.y));
    }

}
