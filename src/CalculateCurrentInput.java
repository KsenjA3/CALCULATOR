import java.util.ArrayList;

class CalculateCurrentInput {
    ArrayList<Double> arrD;
    ArrayList <calculate> arrSign;

    String strNumber;      //inner number
    double dNumber;
    String strSign = " ";        //inner sign
    calculate func=null;
    double  dResult;         //for calculation

    double dNSqrt;              //service sqrt
    int figureSqrt;

    boolean isNumber = false;
    boolean wasSqrt = false;



    // calculate result from string
    double calculateInput (String strInput) {
        strNumber = "0";
        dNumber=0.0;
        dResult=0.0;
        figureSqrt=1;

        System.out.println(strInput);

        arrD = new ArrayList<>();
        arrSign=new ArrayList<>();

        for (int i=0; i<strInput.length(); i++) {

            switch (strInput.charAt(i)) {
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'-> {
                    strNumber=strNumber+strInput.charAt(i);
                    isNumber= true;
                                            //запись последнего числа
                    if (i==strInput.length()-1){
                                            //если последнее число под sqrt
                        if (wasSqrt){
                            dNumber=Double.parseDouble(strNumber);
                            for (int j = 0; j < figureSqrt; j++)
                                dNumber = dNSqrt * Math.sqrt(dNumber);
                            arrD.add(dNumber);
                        }else {             // число после -+*/
                            dNumber=Double.parseDouble(strNumber);
                            arrD.add(dNumber);
                        }

                    }
                }
                default -> {
                                    // получение числа double
                    dNumber=Double.parseDouble(strNumber);
                    strNumber= "0";
                                    //String знака
                    if ((i+2)<strInput.length())
                        strSign = strInput.substring(i, i+3);

                            // если знак sqrt
                    if (strSign.equals(" √ ")){
                        wasSqrt=true;   //маркер sqrt

                        if (isNumber){      //если перед sqrt стоит число
                            dNSqrt=dNumber;
                                            //если число после sqrt
                            if ((i+3)==strInput.length())
                                arrD.add(dNumber);

                            System.out.println("dNSqrt - "+dNSqrt);
                            System.out.println("strInput.length() - "+strInput.length());
                            System.out.println("(i+3) - "+(i+3));
                            System.out.println();

                        } else {            // если несколько sqrt подряд
                            dNSqrt = 1;
                            figureSqrt++;
                        }

                    }       //если знак -+*/
                    else {
                            // расчет нескольких sqrt подряд
                        if (wasSqrt) {
                            for (int j = 0; j < figureSqrt; j++)
                                dNumber = dNSqrt * Math.sqrt(dNumber);
                        }
                            //добавление числа в arrayList
                        arrD.add(dNumber);

                            //добавление знака -+*/ в arrayList
                        switch (strSign) {
                            case " + " ->
                                    func = Operations::plus;

                            case " - " ->
                                    func= Operations::minus;
                            case " * " ->
                                    func= Operations::multiply;

                            case " / " ->
                                    func = Operations::divide;
                        }
                        arrSign.add(func);

                        wasSqrt=false;

                        System.out.println(strSign);
                        System.out.println(func);
                        System.out.println();
                    }
                            //т.к. знак состоит из 3 char
                    i=i+2;
                    isNumber=false;
                }
            }
        }



        for (int i = 0; i<arrD.size(); i++)
            System.out.println(i+" - "+ arrD.get(i));

        for (int i = 0; i<arrSign.size(); i++)
            System.out.println(i+" - "+ arrSign.get(i));




        if (arrD.size()>0)
            dResult = arrD.get(0);

        int j;
        for (int i = 1; i<arrD.size(); i++){
            j=i-1;
                if (j<arrSign.size())
                    dResult = Operations.result(arrSign.get(j), dResult, arrD.get(i));
        }




        return dResult;
    }
}


