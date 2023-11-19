public class Operations {

    static  double plus (double d1, double d2) {
        return d1+d2;
    }

    static  double minus (double d1, double d2) {
        return d1-d2;
    }

    static  double divide (double d1, double d2) {
        return d1/d2;
    }

    static  double multiply(double d1, double d2) {
        return d1*d2;
    }

    static  double radical (double d1, double d2) { return Math.sqrt(d1);}


    static  double result (calculate f, double d1, double d2) {
        return f.func(d1, d2);
    }

}
