public class IntegrationTest {
  public static void main(String[] args) {
    for (int i = 1; i <= 20; i ++) {
      Function f = new Function(i);
      System.out.println("The function is "+f.string);
      System.out.print("f(x)="+f.ofX());
      System.out.println(" and f(0)="+f.of(0));
      f.simplify();
      System.out.println("The function simplifies to "+f.string+" Now,");
      System.out.print("f(x)="+f.ofX());
      System.out.println(" and f(0)="+f.of(0)+"\n");
    }
  }
}