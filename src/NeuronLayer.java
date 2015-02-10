import java.util.ArrayList;


/**
 * @author Arpit Phanda
 */
public class NeuronLayer {
	
	//number of neurons in the layer
	private int numberOfNeurons;
	
	//getter function
	public int getNumberOfNeurons() {
		return numberOfNeurons;
	}

	//arraylist to hold neurons
	private ArrayList<Neuron> neuronVector;
	
	//getter function to get the neuron arraylist
	public ArrayList<Neuron> getNeuronVector() {
		return neuronVector;
	}

	
	//constructor; takes numberOfNeurons as input and initializes the neuronLayer 
	public NeuronLayer(int numberOfNeurons, int numberOfNeuronsInNextLayer) {
		
		this.numberOfNeurons = numberOfNeurons;
		this.neuronVector = new ArrayList<Neuron>();

		System.out.println("++Inside neuron Layer");
		System.out.println("\t++Number of neurons to create: "+this.numberOfNeurons);
		
		int i;
		for( i=0; i<numberOfNeurons ; i++)
		{
			//i is the position in current layer needed for updation
			Neuron neuron = new Neuron(numberOfNeuronsInNextLayer, i); 
			System.out.println("++Added a neuron\n");
			neuronVector.add(neuron);
		}
		
		//setting bias value
		neuronVector.get(i-1).setOutputVal(1.0);
	}
}
