// Way1
public class TestEquation {
	public static void main(String args[]) {
		// (a+b)Square = ((a*a)+2ab+(b*b))

		int a = 25;
		float b = 42.159f;
		double value = (a * a + 2 * (a * b) + b * b);
		// int value = (int) (a * a + 2 * (a * b) + b * b);
		System.out.println("Value of the equation is " + value);
	}
}


// Way2
public class TestEquation {

	public static void main(String[] args) {
		// (a+b)Square = ((a*a)+2ab+(b*b))

		int a = 25;
		double b = 42.159000000000d;
		double value = (double) ((a * a + 2 * (a * b) + b * b));
		// int value = (int) (a * a + 2 * (a * b) + b * b);
		System.out.println("Value of the equation is " + value);

		double add = (double) (a + b);
		double value1 = (double) (add * add);
		System.out.println(value1 + " " + value);
	}
}