public class Function {
  String string = "";
  
  
  
  public Function(String func) {
    string = func;
  }
  
  
  public Function(int diff) {
    do
      string = randomFunc(diff);
    while (diff != 1 && string.equals("Nxxx"));
  }
  
  
  
  private String randomFunc(int diff) { // recursively generates a random function
    int r = (int)(Math.pow(Math.random(),1.5)*diff);
    switch (r) {
      case 0:
        return "Nxxx"; // x
      case 1:
        return "Fadd" + randomFunc(diff-r) + randomConst(true, false, false, -15, 20); // u+k
      case 2:
        return "Ftms" + randomConst(true, false, true, 2, 10) + randomFunc(diff-r); // ku
      case 3:
        return "Fdvd" + randomFunc(diff-r) + randomConst(true, false, false, 2, 8); // u/k
      case 4:
        return "Fdvd" + randomConst(true, false, false, 1, 8) + randomFunc(diff-r); // k/u
      case 5: 
        return "Fadd" + randomFunc(diff-r) + randomFunc(diff-r); // u+v
      case 6:
        return "Fmns" + randomFunc(diff-r) + randomFunc(diff-r); // u-v
      case 7:
        return "Fpow" + randomFunc(diff-r) + randomConst(false, false, false, 2, 6); // u^k
      case 8:
        return "Frut" + randomConst(false, false, false, 2, 5) + randomFunc(diff-r); // k√u
      case 9:
        return "Fpow" + randomConst(false, true, false, 2, 6) + randomFunc(diff-r); // k^u
      case 10:
        return "Fsin" + randomFunc(diff-r); // sin u
      case 11:
        return "Fcos" + randomFunc(diff-r); // cos u
      case 12:
        return "Flog" + randomConst(false, true, false, 2, 5) + randomFunc(diff-r); // logk u
      case 13:
        return "Ftms" + randomFunc(diff-r) + randomFunc(diff-r); // u*v
      case 14:
        return "Ftan" + randomFunc(diff-r); // tan u
      case 15:
        return "Fsec" + randomFunc(diff-r); // sec u
      case 16:
        return "Fcsc" + randomFunc(diff-r); // csc u
      case 17:
        return "Fcot" + randomFunc(diff-r); // cot u
      case 18:
        return "Fdvd" + randomFunc(diff-r) + randomFunc(diff-r); // u/v
      case 19:
        return "Fasn" + randomFunc(diff-r); // arcsin u
      case 20:
        return "Facs" + randomFunc(diff-r); // arccos u
      case 21:
        return "Fatn" + randomFunc(diff-r); // arctan u
      case 22:
        return "Fcoh" + randomFunc(diff-r); // cosh u
      case 23:
        return "Fsih" + randomFunc(diff-r); // sinh u
      case 24:
        return "Facc" + randomFunc(diff-r); // arccsc u
      case 25:
        return "Fasc" + randomFunc(diff-r); // arcsec u
      case 26:
        return "Fact" + randomFunc(diff-r); // arccot u
      case 27:
        return "Ftah" + randomFunc(diff-r); // tan hu
      case 28:
        return "Fseh" + randomFunc(diff-r); // sech u
      case 29:
        return "Fpow" + randomFunc(diff-r) + randomFunc(diff-r); // u^v
      case 30:
        return "Flog" + randomFunc(diff-r) + randomConst(true, false, false, 2, 15); // logu v
      default:
        return randomFunc(diff);
    }
  }
  
  
  private String randomConst(boolean root, boolean e, boolean pi, int min, int max) {
    if (e && Math.random() < .5)
      return "N00e";
    if (pi && Math.random() < .25)
      return "N0pi";
    
    String number = Integer.toString((int)(Math.pow(Math.random(),2)*(max-min)+min));
    for (int i = 0; i < 4-number.length(); i ++)
      number = "0" + number;
    if (root && Math.random() < .2)
      return "FsrtN" + number;
    else
      return "N" + number;
  }
}

// Functions: Fneg Fadd Fmns Ftms Fdvd Fpow Fsqr Frut Fsrt Flog F0ln Fsin Fcos Ftan Fsec Fcsc Fcot Fasn Facs Fatn Fcoh Fsih Ftah Fseh
// Literals:  N001...N010 N00e N0pi
// Variables: Nxxx