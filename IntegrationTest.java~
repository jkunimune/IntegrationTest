public class IntegrationTest {
  public static void main(String[] args) {
    for (int i = 1; i <= 20; i +=2) {
      Function f = new Function(i);
      System.out.print("f(x) = "+f.ofX());
      f.simplify();
      System.out.print(" = "+f.ofX());
      System.out.println(" and f(0) = "+f.of(0));
      
      Function g = f.prime();
      System.out.print("f'(x) = "+g.ofX());
      f.simplify();
      System.out.print(" = "+g.ofX());
      System.out.println(" and f'(0) = "+g.of(0));
      
      System.out.println();
    }
  }
}