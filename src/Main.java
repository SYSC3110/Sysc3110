import java.util.*;
public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		ArrayList<Features> features = new ArrayList<>();
		System.out.println("Welcome to OAA group project. \n"
				+ "1) Find output via kNN input"
				+ "2) Add features to structure"
				+ "3) Change metric feature"
				+ "4) run test cases");
		
		System.out.println("Please enter the topic:");
		String topic = in.nextLine();
		System.out.println("Please enter the amount of features:");
		int featureAmt = in.nextInt();
		
		for (int i=0; i<featureAmt; i++) {
			System.out.println("Feature:");
			String input = in.nextLine();
			features.add(new Features(input));
		}
		
		System.out.println("Enter K value:");
		int k = in.nextInt();
		
		
	}
}
