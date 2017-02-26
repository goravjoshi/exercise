package algos.iq;

public class StackWithMax {

	private double max = Double.MIN_VALUE;
	
	
	private double[] storage;
	private int top = -1;
	
	public StackWithMax(int size) {
		storage = new double[size];
	}
	
	public void push(double element) {
		max = max > element?max:element;
		storage[++top] = element;		
	}
	
	public double pop() {
		if(top == -1) {
			throw new IllegalStateException("Stack Empty");
		}
		double toPop = storage[top--];
		if(toPop == max) {
			max = Double.MIN_VALUE;
			//find max in array
		}
		return toPop;
	}
	
}
