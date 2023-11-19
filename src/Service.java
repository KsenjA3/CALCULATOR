
import javax.swing.*;

public class Service {

    //блокировка клавиш при ошибке ввода
    static void blockedAll(JButton... v) {
        for (JButton b : v)
            b.setEnabled(false);
    }
    //разблокировка клавиш при ошибке ввода
    static void unblockedAll(JButton... v) {
        for (JButton b : v)
            b.setEnabled(true);
    }
    // возвращает String из Double, учитывая что вводимое число м.б. int
    static String printNumber (Double d) {
        String str =Double.toString(d);
        if (str.substring(str.indexOf('.')+1).equals("0"))
            return   str= Long.toString(d.longValue());
        else return str;
    }




}

