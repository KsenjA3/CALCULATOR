import java.util.ArrayList;

class CalculateCurrentInput {
    ArrayList<Double> arrD;
    ArrayList <calculate> arrSign;

     String strNumber;      //inner number
     double dNumber;
     calculate func;
     double  dResult;         //for calculation
     double dNSqrt;              //service sqrt
     int figureSqrt;
     boolean wasNumber;
     boolean wasSqrt;

    // calculate result from string
    double calculateInput (String strInput) {

        func=null;
        strNumber = "0";
        dResult=0.0;
        figureSqrt=1;
        dNSqrt=1;
        wasNumber = false;
        wasSqrt = false;

//        System.out.println("strInput ="+strInput);
                                    // delete space
        strInput= strInput.replaceAll(" ", "");
//        System.out.println(strInput);

        arrD = new ArrayList<>();
        arrSign=new ArrayList<>();

        for (int i=0; i<strInput.length(); i++) {

            switch (strInput.charAt(i)) {
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'-> {
                    //create number
                    strNumber = strNumber + strInput.charAt(i);
  //                  System.out.println("strNumber = " + strNumber);

                    //if sqrt before number
                    if (wasSqrt) {
                        dNumber = Double.parseDouble(strNumber);
                        for (int j = 0; j < figureSqrt; j++){
                            dNumber = Math.sqrt(dNumber);
//                            System.out.println("вычисления корня" + dNumber);
                        }
                        dNumber= dNSqrt * dNumber;
                    } else           // число после -+*/
                        dNumber = Double.parseDouble(strNumber);

                    //write last number
                    if (i == strInput.length() - 1) {
                        arrD.add(dNumber);
  //                      System.out.println("last Number =" + dNumber);
                    }

                    wasNumber = true;
                }case'√'->{

                    if (wasNumber) {
                        //if the last sign sqrt
                        if ((i + 1) == strInput.length()) {
                            arrD.add(dNumber);
  //                          System.out.println(" √ last Number =" + dNumber);
                        }
                        else             // dNumber * √
                            dNSqrt = dNumber;


                    }else           // если знак после sqrt

                        if (wasSqrt) // если несколько sqrt подряд
                            figureSqrt++;

                    wasNumber= false;
                    wasSqrt=true;
                    strNumber=" ";

                }case '+' -> {
                    arrSign.add(Operations::plus);
                    arrD.add(dNumber);
                    strNumber=" ";
                    wasNumber=false;
                    wasSqrt=false;
                    dNSqrt = 1;
                }case '-' -> {
                    arrSign.add(Operations::minus);
                    arrD.add(dNumber);
                    strNumber=" ";
                    wasNumber=false;
                    wasSqrt=false;
                    dNSqrt = 1;
                }case '*' -> {
                    arrSign.add(Operations::multiply);
                    arrD.add(dNumber);
                    strNumber=" ";
                    wasNumber=false;
                    wasSqrt=false;
                    dNSqrt = 1;
                }case '/' -> {
                    arrSign.add(Operations::divide);
                    arrD.add(dNumber);
                    strNumber=" ";
                    wasNumber=false;
                    wasSqrt=false;
                    dNSqrt = 1;
                }

            }
        }


        System.out.println("+Number ="+dNumber);
        System.out.println(func);

        System.out.print("arrD.size()>1= " );
        System.out.println(arrD.size()>1);

        for (int i = 0; i<arrD.size(); i++)
            System.out.println(i+" - "+ arrD.get(i));

        for (int i = 0; i<arrSign.size(); i++)
            System.out.println(i+" - "+ arrSign.get(i));

                            //calculate the resultate
        if (arrD.size()>0)
            dResult = arrD.get(0);

        if (arrD.size()>1){
            int j;
            for (int i = 1; i<arrD.size(); i++) {
                j = i - 1;
                if (j < arrSign.size())
                    dResult = Operations.result(arrSign.get(j), dResult, arrD.get(i));
            }
        }
 //       System.out.println(dResult);
 //       System.out.println();

        return dResult;
    }


    double calculatePersent (calculate funcPerc, String nameSign, double dResultPercent, double dNumberPerc, double dResultPerc) {
        dResult =dResultPerc;
        dNumber=dNumberPerc;
        func=funcPerc;

        if (func == null) {
            dResult = dResult / 100.0;

        } else {
            switch (nameSign) {
                case " + ", " - " -> dNumber = dResultPercent * dNumber / 100.0;
                case " * ", " / " -> dNumber = dNumber / 100.0;
            }
            dResult = Operations.result(func, dResultPercent, dNumber);

        }


        return dResult;
    }









}


