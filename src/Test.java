import java.util.ArrayList;

class Test {
    String strInput = "25 +  √ 9 - 2 √ 1";
   static String strNumber = "41";      //inner number
    Double dResult;


     double tt (String strNumber) {
       var  calculateCurrent = new CalculateCurrentInput ();
         dResult= calculateCurrent.calculateInput(strInput);
         return  dResult;
     }

    public static void main(String[] args) {
        Test t = new Test();
        System.out.println(t.tt(strNumber));
    }

}
