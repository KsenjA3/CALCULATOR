
import javax.swing.*;

public class Service {

    //block keys
    public static void blockedAll(JButton... v) {
        for (JButton b : v)
            b.setEnabled(false);
    }

    //unblock keys
    public static void unblockedAll(JButton... v) {
        for (JButton b : v)
            b.setEnabled(true);
    }

    // return String from Double, consider that number can be Integer
    public static String printNumber (Double d) {
        String str =Double.toString(d);
        if (str.substring(str.indexOf('.')+1).equals("0"))
            return   str= Long.toString(d.longValue());
        else return str;
    }




}

