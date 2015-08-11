import java.util.*;



public class IntegrationTest {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    Function f;
    do {
      f = new Function(in.nextLine(), true);
    } while (!f.isValid());
    
    System.out.print("f(x) = "+f.ofX());
    f.simplify();
    System.out.print(" = "+f.ofX());
    System.out.println(" and f(0) = "+f.of(0));
    
    Function g = f.prime();
    System.out.print("f'(x) = "+g.ofX());
    g.simplify();
    System.out.print(" = "+g.ofX());
    System.out.println(" and f'(0) = "+g.of(0));
    
    System.out.println();
  }
}