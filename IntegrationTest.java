import java.util.*;



public class IntegrationTest {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    System.out.println("Welcome to the integration test. Please select a difficulty level between 1 and 10.");
    int difficulty = in.nextInt()<<3; // the player's level of skill, from 0 to Infinity
    final int startingDiff = difficulty;
    int right = 0; // the number of problems the player has solved correctly
    int wrong = 0; // the number of problems solved incorrectly
    System.out.println("You will be shown a number of mathematical functions. Your job is to determine the antiderivative of each. Go!");
    long start = System.currentTimeMillis();
    
    for (int i = 1; i <= 10; i ++) {
//      System.out.println(difficulty);
      Function g = new Function(difficulty);
      g.simplify();
      g.addC();
      Function f = g.prime();
      f.simplify();
      System.out.print("\n"+i+": f(x) = "+f.ofX()+". What is the antiderivative of f?\nF(x) =");
      
      String response = in.nextLine();
      Function h = new Function(response, true);
      while (!h.isValid()) {
        if (response.length() > 0)
          System.out.print("Sorry; \""+response+"\" is not a valid function. Remember not to use any decimals, mixed numbers, roots, or non-standard log bases. Use capital C for integration constants and lowercase letters for all functions.\ng(x) =");
        response = in.nextLine();
        h = new Function(response, true);
      }
      
      if (((response.length() > 2 && response.indexOf("+C") == response.length()-2) || // the answer is correct if the antiderivatives match
           (response.length() > 3 && response.indexOf("+ C") == response.length()-3)) && h.matches(g)) { // and it ends in "+C"
        System.out.println("Correct!");
        right ++;
        difficulty *= Math.pow(1.5,1.0/i);
        continue;
      }
      else {
        System.out.println("Sorry; the correct antiderivative was "+g.ofX()+"+C.");
        wrong ++;
        difficulty /= Math.pow(1.5,1.0/i);
        continue;
      }
    }
    
    System.out.println("\nYou completed 10 problems at difficulty "+startingDiff+" in "+(int)((System.currentTimeMillis()-start)/60000)+
                       " min, "+(int)((System.currentTimeMillis()-start)/1000%60)+" sec, with a raw score of "+right+"/10.");
    System.out.println("Your final grade is "+(right*10*Math.pow(Math.E,-Math.sqrt(System.currentTimeMillis()-start)/100/difficulty))+
    ".\nBetter luck next time.");
  }
}