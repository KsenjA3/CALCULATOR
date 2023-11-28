import java.util.ArrayList;

class Test {
    String strInput = "3 + 8 √ 4";
   static String strNumber = "41";      //inner number
    Double dResult;


     double tt (String strNumber) {
       var  calculateCurrent = new CalculateCurrentInput ();
         dResult= calculateCurrent.calculateInput(strInput);
         return  dResult;
     }

    public static void main(String[] args) {
     //  Test t = new Test();
     //   System.out.println(t.tt(strNumber));
        String str ="  5+-    6=";

        str= str.replace("+", " + ");
         str= str.replace("-", " - ");
          str= str.replace("/", " / ");
           str= str.replace("*", " * ");


        while (str.contains("  "))
            str= str.replaceAll("  ", " ");


        for (int i=0; i<str.length();i++) {
            switch (str.charAt(i)) {
                case '+', '-', '/', '*'-> {
                    switch (str.charAt(i+2)) {
                        case '+', '-', '/', '*'-> str= str.substring(0,i) +str.substring(i+2);
                    }
                }
            }
        }

        if( str.endsWith("=")) System.out.println(true);
         System.out.println(str);
    }

}
