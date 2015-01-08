public class Function {
  final String ooo = "NEQGA";
  String string = "";
  
  
  
  public Function(String func) {
    string = func;
  }
  
  
  public Function(int diff) { // calls for a random function given the difficulty
    do
      string = randomFunc(diff);
    while (diff != 1 && string.equals("Nxxx"));
  }
  
  
  
  public double of(double x) { // returns the value of this at x
    return eval(string, x);
  }
  
  
  public String ofX() { // returns the standard mathematical notation of this
    return print(string, "A");
  }
  
  
  public boolean equals(Function that) { // tests for equivalency of functions
    for (int i = -10; i < 11; i ++)
      if (this.of(i) != that.of(i) && this.of(i) == this.of(i) && that.of(i) == that.of(i)) // if the funtions are inequal and real at any point, they are not equivalent
        return false;
    return true;
  }
  
  
  public void simplify() {
    for (int i = 0; i < string.length(); i += 4) { // looks through the function for things that need to be simplified.  Whenever one is found, it starts over
      String term = string.substring(i);
      if (term.substring(0,4).equals("Aadd")) {
        if (new Function(arg(term, 1)).equals(new Function(arg(term, 2)))) { // u + u = 2u
          string = string.substring(0,i) + "GtmsN002" + arg(term, 1) + getAfter(string, i);
          i = 0;
        }
        if (arg(term, 2).substring(0,4).equals("Fneg")) { // u + -v = u - v
          string = string.substring(0,i) + "Amns" + arg(term, 1) + arg(term,2).substring(4) + getAfter(string, i);
          i = 0;
        }
      }
      if (term.substring(0,4).equals("Amns")) {
        if (new Function(arg(term, 1)).equals(new Function(arg(term, 2)))) { // u - u = 0
          string = string.substring(0,i) + "N000" + getAfter(string, i);
          i = 0;
        }
      }
      if (term.substring(0,4).equals("Fneg")) {
        if (term.substring(4,8).equals("Fneg")) { // --u = u
          string = string.substring(0,i) + string.substring(i+8);
          i = 0;
        }
      }
    }
  }
  
  
  private String print(String func, String outsideOpr) { // gives the form in which a function should be printed (fst and outsideOpr are for order of operations)
    String output = "";
    String fst = func.substring(0,1);
    if (ooo.indexOf(outsideOpr) < ooo.indexOf(fst))
      output = "(";
    
    if (func.substring(0,4).equals("Nxxx"))
      output += "x";
    else if (func.substring(0,4).equals("N00e"))
      output += "e";
    else if (func.substring(0,4).equals("N0pi"))
      output += "π";
    else if (func.substring(0,4).equals("Ntau"))
      output += "2π";
    else if (func.substring(0,1).equals("N"))
      output += getLiteral(func);
    else if (func.substring(0,4).equals("Fneg"))
      output += "-"+print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Aadd"))
      output += print(arg(func,1), fst) +"+"+ print(arg(func,2), fst);
    else if (func.substring(0,4).equals("Amns"))
      output += print(arg(func,1), fst) +"-"+ print(arg(func,2), fst);
    else if (func.substring(0,4).equals("Gtms"))
      output += print(arg(func,1), fst) + print(arg(func,2), fst);
    else if (func.substring(0,4).equals("Qdvd"))
      output += print(arg(func,1), fst) +"/"+ print(arg(func,2), fst);
    else if (func.substring(0,4).equals("Epow"))
      output += print(arg(func,1), fst) +"^"+ print(arg(func,2), fst);
    else if (func.substring(0,4).equals("Fsqr"))
      output += print(arg(func,1), fst) +"^2";
    else if (func.substring(0,4).equals("Drut"))
      output += print(arg(func,1), fst) +"√"+ print(arg(func,2), fst);
    else if (func.substring(0,4).equals("Fsrt"))
      output += "√"+ print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Dlog"))
      output += "log"+ print(arg(func,1), fst) +" "+ print(arg(func,2), fst);
    else if (func.substring(0,4).equals("F0ln"))
      output += "ln"+ print(arg(func,2), fst);
    else if (func.substring(0,4).equals("Fsin"))
      output += "sin"+ print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Fcos"))
      output += "cos"+ print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Ftan"))
      output += "tan"+ print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Fsec"))
      output += "sec"+ print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Fcsc"))
      output += "csc"+ print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Fcot"))
      output += "cot"+ print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Fasn"))
      output += "arcsin"+ print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Facs"))
      output += "arccos"+ print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Fatn"))
      output += "arctan"+ print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Fasc"))
      output += "arcsec"+ print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Facc"))
      output += "arccsc"+ print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Fact"))
      output += "arccot"+ print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Fsih"))
      output += "sinh"+ print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Fcoh"))
      output += "cosh"+ print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Ftah"))
      output += "tanh"+ print(arg(func,1), fst);
    else
      output += "ERROR";
    
    if (output.indexOf("(") == 0)
      output += ")";
    
    return output;
  }
  
  
  private double eval(String func, double x) { // evaluates a given function at a given x
    if (func.substring(0,4).equals("Nxxx"))
      return x;
    else if (func.substring(0,4).equals("N00e"))
      return Math.E;
    else if (func.substring(0,4).equals("N0pi"))
      return Math.PI;
    else if (func.substring(0,4).equals("Ntau"))
      return 2*Math.PI;
    else if (func.substring(0,1).equals("N"))
      return getLiteral(func.substring(1));
    else if (func.substring(0,4).equals("Fneg"))
      return -eval(arg(func,1), x);
    else if (func.substring(0,4).equals("Aadd"))
      return eval(arg(func,1), x) + eval(arg(func,2), x);
    else if (func.substring(0,4).equals("Amns"))
      return eval(arg(func,1), x) - eval(arg(func,2), x);
    else if (func.substring(0,4).equals("Gtms"))
      return eval(arg(func,1), x) * eval(arg(func,2), x);
    else if (func.substring(0,4).equals("Qdvd"))
      return eval(arg(func,1), x) / eval(arg(func,2), x);
    else if (func.substring(0,4).equals("Epow"))
      return Math.pow(eval(arg(func,1), x), eval(arg(func,2), x));
    else if (func.substring(0,4).equals("Fsqr"))
      return Math.pow(eval(arg(func,1), x), 2);
    else if (func.substring(0,4).equals("Drut"))
      return Math.pow(eval(arg(func,2), x), 1/eval(arg(func,1), x));
    else if (func.substring(0,4).equals("Fsrt"))
      return Math.pow(eval(arg(func,1), x), .5);
    else if (func.substring(0,4).equals("Dlog"))
      return Math.log(eval(arg(func,2), x)) / Math.log(eval(arg(func,1), x));
    else if (func.substring(0,4).equals("F0ln"))
      return Math.log(eval(arg(func,2), x));
    else if (func.substring(0,4).equals("Fsin"))
      return Math.sin(eval(arg(func,1), x));
    else if (func.substring(0,4).equals("Fcos"))
      return Math.cos(eval(arg(func,1), x));
    else if (func.substring(0,4).equals("Ftan"))
      return Math.tan(eval(arg(func,1), x));
    else if (func.substring(0,4).equals("Fsec"))
      return 1/Math.cos(eval(arg(func,1), x));
    else if (func.substring(0,4).equals("Fcsc"))
      return 1/Math.sin(eval(arg(func,1), x));
    else if (func.substring(0,4).equals("Fcot"))
      return 1/Math.tan(eval(arg(func,1), x));
    else if (func.substring(0,4).equals("Fasn"))
      return Math.asin(eval(arg(func,1), x));
    else if (func.substring(0,4).equals("Facs"))
      return Math.acos(eval(arg(func,1), x));
    else if (func.substring(0,4).equals("Fatn"))
      return Math.atan(eval(arg(func,1), x));
    else if (func.substring(0,4).equals("Fasc"))
      return Math.acos(1/eval(arg(func,1), x));
    else if (func.substring(0,4).equals("Facc"))
      return Math.asin(1/eval(arg(func,1), x));
    else if (func.substring(0,4).equals("Fact"))
      return Math.atan(1/eval(arg(func,1), x));
    else if (func.substring(0,4).equals("Fsih"))
      return Math.sinh(eval(arg(func,1), x));
    else if (func.substring(0,4).equals("Fcoh"))
      return Math.cosh(eval(arg(func,1), x));
    else if (func.substring(0,4).equals("Ftah"))
      return Math.tanh(eval(arg(func,1), x));
    else
      return 9000.0001;
  }
  
  
  private String arg(String func, int argNum) { // finds the given argument of a function
    String output = func; // I was going to just use func but Strings are objects, so I was actually messing with the input function.
    int cutoff = 4;
    
    for (int i = 0; i < argNum; i ++) { // cycles through arguments correct number of times
      output = output.substring(cutoff);
      
      int funcCount = 1;
      cutoff = 0;
      while (funcCount > 0) { // counts Ds and Ns to find the end of the argument
        if (output.substring(cutoff, cutoff+1).equals("N"))
          funcCount --;
        else if (!output.substring(cutoff, cutoff+1).equals("F"))
          funcCount ++;
        cutoff += 4;
      }
    }
    
    output = output.substring(0, cutoff);
    return output;
  }
  
  
  private String randomFunc(int diff) { // recursively generates a random function
    int r = (int)(Math.pow(Math.random(),1.5)*diff);
    switch (r) {
      case 0:
        return "Nxxx"; // x
      case 1:
        return "Aadd" + randomFunc(diff-r) + randomConst(true, false, false, -15, 20); // u+k
      case 2:
        return "Gtms" + randomConst(true, false, true, 2, 10) + randomFunc(diff-r); // ku
      case 3:
        return "Fneg" + randomFunc(diff-r); // -u
      case 4:
        return "Qdvd" + randomFunc(diff-r) + randomConst(true, false, false, 2, 8); // u/k
      case 5:
        return "Qdvd" + randomConst(true, false, false, 1, 8) + randomFunc(diff-r); // k/u
      case 6: 
        return "Aadd" + randomFunc(diff-r/2) + randomFunc(diff-r/2); // u+v
      case 7:
        return "Amns" + randomFunc(diff-r/2) + randomFunc(diff-r/2); // u-v
      case 8:
        return "Epow" + randomFunc(diff-r) + randomConst(false, false, false, 2, 6); // u^k
      case 9:
        return "Drut" + randomConst(false, false, false, 2, 5) + randomFunc(diff-r); // k√u
      case 10:
        return "Epow" + randomConst(false, true, false, 2, 6) + randomFunc(diff-r); // k^u
      case 11:
        return "Fsin" + randomFunc(diff-r); // sin u
      case 12:
        return "Fcos" + randomFunc(diff-r); // cos u
      case 13:
        return "Dlog" + randomConst(false, true, false, 2, 5) + randomFunc(diff-r); // logk u
      case 14:
        return "Gtms" + randomFunc(diff-r/2) + randomFunc(diff-r/2); // u*v
      case 15:
        return "Ftan" + randomFunc(diff-r); // tan u
      case 16:
        return "Fsec" + randomFunc(diff-r); // sec u
      case 17:
        return "Fcsc" + randomFunc(diff-r); // csc u
      case 18:
        return "Fcot" + randomFunc(diff-r); // cot u
      case 19:
        return "Qdvd" + randomFunc(diff-r/2) + randomFunc(diff-r/2); // u/v
      case 20:
        return "Fasn" + randomFunc(diff-r); // arcsin u
      case 21:
        return "Facs" + randomFunc(diff-r); // arccos u
      case 22:
        return "Fatn" + randomFunc(diff-r); // arctan u
      case 23:
        return "Fcoh" + randomFunc(diff-r); // cosh u
      case 24:
        return "Fsih" + randomFunc(diff-r); // sinh u
      case 29:
        return "Epow" + randomFunc(diff-r) + randomFunc(diff-r); // u^v
      case 30:
        return "Dlog" + randomFunc(diff-r) + randomConst(true, false, false, 2, 15); // logu v
      case 25:
        return "Facc" + randomFunc(diff-r); // arccsc u
      case 26:
        return "Fasc" + randomFunc(diff-r); // arcsec u
      case 27:
        return "Fact" + randomFunc(diff-r); // arccot u
      case 28:
        return "Ftah" + randomFunc(diff-r); // tanh u
      default:
        return randomFunc(diff); // if difficulty is too high, none of these might trigger.  In that case, try them all again
    }
  }
  
  
  private String randomConst(boolean root, boolean e, boolean pi, int min, int max) { // gives a random constant based on parameters
    if (e && Math.random() < .5) // uses fundamental constants if applicable
      return "N00e";
    if (pi && Math.random() < .2)
      return "Ntau";
    if (pi && Math.random() < .1)
      return "N0pi";
    
    String number;
    do
      number = Integer.toString((int)(Math.pow(Math.random()*(Math.cbrt(max)-Math.cbrt(min))+Math.cbrt(min), 3))); // finds a random number in the range (biased toward smaller numbers)
    while (number.equals("0")); // avoids picking 0
    
    boolean negative = Integer.parseInt(number) < 0;
    if (negative)  number = number.substring(1); // checks for negatives and makes number positive
    
    while (number.length() != 3)
      number = "0" + number;
    
    if (root && Math.random() < .2) // sometimes returns square roots
      number = "FsrtN" + number;
    else
      number = "N" + number;
    
    if (negative)  return "Fneg" + number;
    else           return number;
  }
  
  
  private int getLiteral(String code) { //gets a number literal from N0... code
    code = code.substring(1);
    while (code.substring(0,1).equals("0") && code.length() > 1)
      code = code.substring(1);
    return Integer.parseInt(code);
  }
  
  
  private String getAfter(String func, int argStart) { // finds the whole of a term with a given index and returns everything after that term
    if (argStart == 0 || argStart == func.length()-4)
      return "";
    
    return func.substring(argStart + arg("F000"+func.substring(argStart), 1).length()); // uses arg to parse the whole term and returns what comes after it
  }
}



// Key: N-Number   A-Arithmetic   G-Geometric   Q-Quotient   E-Exponential   F-Function   D-Double parameter function
// Functions: Fneg Aadd Amns Gtms Qdvd Epow Fsqr Drut Fsrt Dlog F0ln Fsin Fcos Ftan Fsec Fcsc Fcot Fasn Facs Fatn Fcoh Fsih Ftah Fseh
// Literals:  N001...N010 N00e N0pi Ntau
// Variables: Nxxx