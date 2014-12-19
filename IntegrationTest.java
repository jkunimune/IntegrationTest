public class IntegrationTest {
  public static void main(String[] args) {
    for (int i = 1; i < 100; i ++) {
      Function f = new Function(i);
      System.out.println(i+": "+f.string);
    }
  }
}