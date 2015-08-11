public class Function {
  final static String ooo = "NEQGA0"; // the order of operations (Number, Exponent, Quotient, Geometric, Arithmetic) 
  private String string;
  
  
  
  public Function(String func) { // copies an existing fuction code
    string = func;
  }
  
  
  public Function(int diff) { // calls for a random function given the difficulty
    do
      string = randomFunc(diff);
    while (diff != 1 && string.equals("Nxxx"));
  }
  
  
  public Function(String expression, boolean standardize) { // reads a given mathematical expression
    if (standardize) {
      expression.replace("C", "0");
      expression.replace(" ", "");
      expression.replace("{", "(");
      expression.replace("}", ")");
      expression.replace("[", "(");
      expression.replace("]", ")"); // standardizes some notation
    }
    
    string =  read(expression);
  }
  
  
  
  public String getString() {
    return string;
  }
  
  
  public double of(double x) { // returns the value of this at x
    return eval(string, x);
  }
  
  
  public String ofX() { // returns the standard mathematical notation of this
    return print(string, "A");
  }
  
  
  public Function prime() {
    return new Function(differentiate(string));
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
      if (!arg("F000"+term,1).contains("Nxxx") && arg("F000"+term,1).length() > 4) { // evaluates constants, assuming the result is an integer
        double result = eval(term,0);
        if ((int)result == result) {
          if (result >= 0) {
            string = string.replace(arg("F000"+term,1), literalToCode((int)result));
            i = 0;
          }
          else {
            string = string.replace(arg("F000"+term,1), "Fneg"+literalToCode(-(int)result));
            i = 0;
          }
        }
      }
      if (term.substring(0,4).equals("Aadd")) {
        if (new Function(arg(term, 1)).equals(new Function(arg(term, 2)))) { // u + u = 2u
          string = string.substring(0,i) + "GtmsN002" + arg(term, 1) + getAfter(string, i);
          i = 0;
        }
        if (arg(term, 2).substring(0,4).equals("Fneg")) { // u + -v = u - v
          string = string.substring(0,i) + "Amns" + arg(term, 1) + arg(term,2).substring(4) + getAfter(string, i);
          i = 0;
        }
        if (arg(term,1).equals("N000")) { // u + 0 = u
          string = string.substring(0,i) + arg(term,2) + getAfter(string, i);
          i = 0;
        }
        if (arg(term,2).equals("N000")) { // u + 0 = u
          string = string.substring(0,i) + arg(term,1) + getAfter(string, i);
          i = 0;
        }
      }
      if (term.substring(0,4).equals("Amns")) {
        if (new Function(arg(term, 1)).equals(new Function(arg(term, 2)))) { // u - u = 0
          string = string.substring(0,i) + "N000" + getAfter(string, i);
          i = 0;
        }
      }
      if (term.substring(0,4).equals("Fneg") && term.substring(4,8).equals("Fneg")) { // --u = u
        string = string.substring(0,i) + string.substring(i+8);
        i = 0;
      }
      if (term.substring(0,4).equals("Fneg") && term.substring(4,8).equals("N000")) { // -0 = 0
        string = string.substring(0,i) + string.substring(i+4);
        i = 0;
      }
      if (term.substring(0,4).equals("Gtms")) {
        if (arg(term,1).equals("N000") || arg(term,2).equals("N000")) { // u*0 = 0
          string = string.substring(0,i) + "N000" + getAfter(string, i);
          i = 0;
        }
      }
    }
  }
   
  
  public static String print(String func, String outsideOpr) { // gives the form in which a function should be printed (fst and outsideOpr are for order of operations)
    String output = "";
    String fst = func.substring(0,1);
    if (ooo.indexOf(outsideOpr) < ooo.indexOf(fst))
      output = "(";
    
    if (func.substring(0,4).equals("Nxxx"))
      output += "x";
    else if (func.substring(0,4).equals("N00e"))
      output += "e";
    else if (func.substring(0,4).equals("N0pi"))
      output += "pi";
    else if (func.substring(0,4).equals("Ntau"))
      output += "2pi";
    else if (func.substring(0,1).equals("N"))
      output += getLiteral(func);
    else if (func.substring(0,4).equals("Fneg"))
      output += "-"+print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Aadd"))
      output += print(arg(func,1), fst) +"+"+ print(arg(func,2), fst);
    else if (func.substring(0,4).equals("Amns"))
      output += print(arg(func,1), fst) +"-"+ print(arg(func,2), "0"); // subtraction has somewhat special rules regarding parentheses
    else if (func.substring(0,4).equals("Gtms"))
      output += print(arg(func,1), fst) + "(" + print(arg(func,2), fst) + ")";
    else if (func.substring(0,4).equals("Qdvd"))
      output += print(arg(func,1), "G") +"/"+ print(arg(func,2), fst); // so does division
    else if (func.substring(0,4).equals("Epow"))
      output += print(arg(func,1), fst) +"^"+ print(arg(func,2), fst);
    else if (func.substring(0,4).equals("Fsqr"))
      output += print(arg(func,1), fst) +"^2";
    else if (func.substring(0,4).equals("Drut"))
      output += print(arg(func,1), fst) +"?"+ print(arg(func,2), fst);
    else if (func.substring(0,4).equals("Fsrt"))
      output += "?"+ print(arg(func,1), fst);
    else if (func.substring(0,4).equals("Dlog"))
      output += "log"+ print(arg(func,1), fst) +" "+ print(arg(func,2), fst);
    else if (func.substring(0,4).equals("F0ln"))
      output += "ln"+ print(arg(func,1), fst);
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
      output += "ERROR "+func;
    
    if (output.indexOf("(") == 0)
      output += ")";
    
    return output;
  }
  
  
  public static double eval(String func, double x) { // evaluates a given function at a given x
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
      return Math.log(Math.abs(eval(arg(func,1), x)));
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
  
  
  public static String differentiate(String func) {
    String output = "";

    if (func.substring(0,4).equals("Nxxx")) // d/dx x = 1
      output += "N001";
    else if (func.substring(0,1).equals("N")) // d/dx C = 0
      output += "N000";
    else if (func.substring(0,4).equals("Fneg")) // d/dx -x = -1
      output += "Fneg"+differentiate(arg(func,1));
    else if (func.substring(0,4).equals("Aadd")) // d/dx u+v = du+dv
      output += "Aadd"+differentiate(arg(func,1))+differentiate(arg(func,2));
    else if (func.substring(0,4).equals("Amns")) // d/dx u-v = du-dv
      output += "Amns"+differentiate(arg(func,1))+differentiate(arg(func,2));
    else if (func.substring(0,4).equals("Gtms")) // d/dx uv = duv+dvu
      output += "AaddGtms"+differentiate(arg(func,1))+arg(func,2) + "Gtms"+differentiate(arg(func,2))+arg(func,1);
    else if (func.substring(0,4).equals("Qdvd")) // d/dx u/v = (duv-dvu)/v^2
      output += "QdvdAmnsGtms"+differentiate(arg(func,1))+arg(func,2)+"Gtms"+differentiate(arg(func,2))+arg(func,1)+"Epow"+arg(func,2)+"N002";
    else if (func.substring(0,4).equals("Epow")) // d/dx u^v = u^v * (dvlnu + v*du/u)
      output += "GtmsEpow"+arg(func,1)+arg(func,2)+"AaddGtmsF0ln"+arg(func,1)+differentiate(arg(func,2))+"QdvdGtms"+arg(func,2)+differentiate(arg(func,1))+arg(func,1);
    else if (func.substring(0,4).equals("Fsqr")) // d/dx u^2 = 2*u*du
      output += "GtmsGtmsN002"+arg(func,1)+differentiate(arg(func,1));
    else if (func.substring(0,4).equals("Drut")) // d/dx u root (v) = d/dx v ^ 1/u
      output += differentiate("Epow"+arg(func,2)+"QdvdN001"+arg(func,1));
    else if (func.substring(0,4).equals("Fsrt")) // d/dx sqrt(u) = -du / 2sqrt(u)
      output += "FnegQdvd"+differentiate(arg(func,1))+"GtmsN002Fsrt"+arg(func,1);
    else if (func.substring(0,4).equals("Dlog")) // d/dx logu v  = udv/vdu
      output += "QdvdGtms"+arg(func,1)+differentiate(arg(func,2))+"Gtms"+arg(func,2)+differentiate(arg(func,1));
    else if (func.substring(0,4).equals("F0ln")) // d/dx lnu = du/u
      output += "Qdvd"+differentiate(arg(func,1))+arg(func,1);
    else if (func.substring(0,4).equals("Fsin")) // d/dx sinu = cosudu
      output += "GtmsFcos"+arg(func,1)+differentiate(arg(func,1));
    else if (func.substring(0,4).equals("Fcos")) // d/dx cosu = -sinudu
      output += "FnegGtmsFsin"+arg(func,1)+differentiate(arg(func,1));
    else if (func.substring(0,4).equals("Ftan")) // d/dx tanu = sec^2u du
      output += "GtmsEpowFsec"+arg(func,1)+"N002"+differentiate(arg(func,1));
    else if (func.substring(0,4).equals("Fsec")) // d/dx secu = tanu secu du
      output += "GtmsGtmsFtan"+arg(func,1)+"Fsec"+arg(func,1)+differentiate(arg(func,1));
    else if (func.substring(0,4).equals("Fcsc")) // d/dx cscu = -cotu cscu du
      output += "FnegGtmsGtmsFcot"+arg(func,1)+"Fcsc"+arg(func,1)+differentiate(arg(func,1));
    else if (func.substring(0,4).equals("Fcot")) // d/dx cotu = -csc^2u du
      output += "FnegGtmsEpowFcsc"+arg(func,1)+"N002"+differentiate(arg(func,1));
    else if (func.substring(0,4).equals("Fasn")) // d/dx arcsinu = du/sqrt(1-u^2)
      output += "Qdvd"+differentiate(arg(func,1))+"FsrtAmnsN001Epow"+arg(func,1)+"N002";
    else if (func.substring(0,4).equals("Facs")) // d/dx arccosu = -du/sqrt(1-u^2)
      output += "FnegQdvd"+differentiate(arg(func,1))+"FsrtAmnsN001Epow"+arg(func,1)+"N002";
    else if (func.substring(0,4).equals("Fatn")) // d/dx arctanu = du/(1+u^2)
      output += "Qdvd"+differentiate(arg(func,1))+"AaddN001Epow"+arg(func,1)+"N002";
    else if (func.substring(0,4).equals("Fasc")) // d/dx arcsecu = du/(|u|*sqrt(u^2-1)) = du/sqrt(u^4-u^2)
      output += "Qdvd"+differentiate(arg(func,1))+"FsrtAmnsEpow"+arg(func,1)+"N004Epow"+arg(func,1)+"N002";
    else if (func.substring(0,4).equals("Facc")) // d/dx arccscu = -du/sqrt(u^4-u^2)
      output += "FnegQdvd"+differentiate(arg(func,1))+"FsrtAmnsEpow"+arg(func,1)+"N004Epow"+arg(func,1)+"N002";
    else if (func.substring(0,4).equals("Fact")) // d/dx arccotu = -du/(1+u^2)
      output += "FnegQdvd"+differentiate(arg(func,1))+"AaddN001Epow"+arg(func,1)+"N002";
    else if (func.substring(0,4).equals("Fsih")) // d/dx sinhu = coshu du
      output += "GtmsFcoh"+arg(func,1)+differentiate(arg(func,1));
    else if (func.substring(0,4).equals("Fcoh")) // d/dx coshu = sinhu du
      output += "GtmsFcoh"+arg(func,1)+differentiate(arg(func,1));
    else if (func.substring(0,4).equals("Ftah")) // d/dx tanhu = du-tanh^2u du
      output += "Amns"+differentiate(arg(func,1))+"GtmsEpowFtah"+arg(func,1)+"N002"+differentiate(arg(func,1));
    else
      output += "ERROR: "+func;
    
    if (output.indexOf("(") == 0)
      output += ")";
    
    return output;
  }
  
  
  public static String read(String exp) { // converts a mathematical expression (i.e. 2x) to code (i.e. GtmsN002Nxxx)
    while (exp.charAt(0) == '(')
      exp = exp.substring(1,exp.length()-1); // removes any parentheses that may be surrounding this expression
    
    int ptsLvl = 0; // the number of parentheses pairs we are inside
    for (int o = 0; o < 3; o ++) { // index of the operations we are looking for
      for (int i = exp.length()-1; i >=0; i --) { // index of the character at which we look
        if (exp.charAt(i) == ')')
          ptsLvl ++;
        else if (exp.charAt(i) == '(') // keeps track of whether we are in parentheses
          ptsLvl --;
        if (ptsLvl != 0) // does not read anything inside parentheses yet
          continue;
        
        switch (o) {
          case 0: // arithmetic
            if (exp.charAt(i) == '+')
              return "Aadd" + read(exp.substring(0,i)) +  read(exp.substring(i+1, exp.length()));
            else if (exp.charAt(i) == '-')
              return "Amns" + read(exp.substring(0,i)) +  read(exp.substring(i+1, exp.length()));
            break;
            
          case 1: // geometric
            if (exp.charAt(i) == '*')
              return "Gtms" + read(exp.substring(0,i)) +  read(exp.substring(i+1, exp.length()));
            else if (exp.charAt(i) == '/')
              return "Qdvd" + read(exp.substring(0,i)) +  read(exp.substring(i+1, exp.length()));
            break;
            
          case 2: // exponential
            if (exp.charAt(i) == '^')
              return "Epow" + read(exp.substring(0,i)) +  read(exp.substring(i+1, exp.length()));
            break;
        }
      }
    }
    
    if (exp.indexOf("-") == 0)
      return "Fneg"+read(exp.substring(1));
    else if (exp.indexOf("ln") == 0)
      return "F0ln"+read(exp.substring(2));
    else if (exp.indexOf("log") == 0)
      return "DlogN010"+read(exp.substring(3));
    else if (exp.indexOf("sin") == 0)
      return "Fsin"+read(exp.substring(3));
    else if (exp.indexOf("cos") == 0)
      return "Fcos"+read(exp.substring(3));
    else if (exp.indexOf("tan") == 0)
      return "Ftan"+read(exp.substring(3));
    else if (exp.indexOf("csc") == 0)
      return "Fcsc"+read(exp.substring(3));
    else if (exp.indexOf("sec") == 0)
      return "Fsec"+read(exp.substring(3));
    else if (exp.indexOf("cot") == 0)
      return "Fcot"+read(exp.substring(3));
    else if (exp.indexOf("arccot") == 0)
      return "Fact"+read(exp.substring(4));
    else if (exp.indexOf("sinh") == 0)
      return "Fsih"+read(exp.substring(4));
    else if (exp.indexOf("cosh") == 0)
      return "Fcoh"+read(exp.substring(4));
    else if (exp.indexOf("tanh") == 0)
      return "Ftah"+read(exp.substring(4));
    else if (exp.indexOf("arcsin") == 0)
      return "Fasn"+read(exp.substring(6));
    else if (exp.indexOf("arccos") == 0)
      return "Facs"+read(exp.substring(6));
    else if (exp.indexOf("arctan") == 0)
      return "Fatn"+read(exp.substring(6));
    else if (exp.indexOf("arccsc") == 0)
      return "Facc"+read(exp.substring(6));
    else if (exp.indexOf("arcsec") == 0)
      return "Fasc"+read(exp.substring(6));
    else if (exp.equals("x"))
      return "Nxxx";
    else if (exp.equals("pi"))
      return "N0pi";
    else if (exp.equals("tau") || exp.equals("2pi"))
      return "Ntau";
    else if (exp.equals("e"))
      return "N00e";
    else {
      try {
        return literalToCode(Integer.parseInt(exp));
      } catch (NumberFormatException e) {
        return "%ERROR!%";
      }
    }
  }
  
  
  private static String arg(String func, int argNum) { // finds the given argument of a function
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
    switch (r%28) {
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
        return "ERROR - Math is broken"; // should not trigger
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
      number = Integer.toString((int)(Math.pow(Math.random()*(Math.cbrt(max)-Math.cbrt(min))+Math.cbrt(min), 3)), 16); // finds a random number in the range (biased toward smaller numbers)
    while (number.equals("0")); // avoids picking 0
    
    boolean negative = Integer.parseInt(number,16) < 0;
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
  
  
  private static String literalToCode(int n) { // converts a number literal to N0... code
    boolean negative = n < 0;
    if (negative)  n = -n; // checks for negatives and makes number positive
    
    String number = Integer.toString(n, 16);
    while (number.length() != 3)
      number = "0" + number;
    
    if (negative)  return "FnegN" + number;
    else           return "N"+number;
  }
  
  
  private static int getLiteral(String code) { //gets a number literal from N0... code
    code = code.substring(1);
    while (code.substring(0,1).equals("0") && code.length() > 1)
      code = code.substring(1);
    return Integer.parseInt(code, 16);
  }
  
  
  private static String getAfter(String func, int argStart) { // finds the whole of a term with a given index and returns everything after that term
    if (argStart == 0 || argStart == func.length()-4)
      return "";
    
    return func.substring(argStart + arg("F000"+func.substring(argStart), 1).length()); // uses arg to parse the whole term and returns what comes after it
  }
  
  
  public boolean isValid() {
    return string.indexOf("%ERROR!%") == -1;
  }
}



// Key: N-Number   A-Arithmetic   G-Geometric   Q-Quotient   E-Exponential   F-Function   D-Double parameter function
// Functions: Fneg Aadd Amns Gtms Qdvd Epow Fsqr Drut Fsrt Dlog F0ln Fsin Fcos Ftan Fsec Fcsc Fcot Fasn Facs Fatn Fcoh Fsih Ftah Fseh
// Literals*:  N001...N010 N00e N0pi Ntau
// Variables: Nxxx
// *literals stored in hexadecimal