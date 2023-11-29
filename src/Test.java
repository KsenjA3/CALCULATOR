import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Test {

    private JFrame frame;
    int w=200, h=200, wGo=350;
    JCheckBox Log;
    Action  Log1Action, Log2Action;
    JButton  Log1, Log2;

    public static void main(String[] args){

        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                new Test();                                 //Create a Main object, wrapped by SwingUtilities.invokeLater to make it thread safe
            }
        });

    }

    public Test() {                                         //Main's constructor

        frame = new JFrame();                               //Create JFrame
        frame.setTitle("Test Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Log1Action= new SizeFrame("with log");
        Log1 = new JButton(Log1Action);
        Log2Action= new SizeFrame("without log");
        Log2 = new JButton(Log2Action);

        Log = new JCheckBox("Log");

        frame.add(Log1);
        frame.add(Log2);
        frame.add(Log);

 //       init((JPanel)frame.getContentPane());               //'init' frame's JPanel

        frame.setSize(new Dimension(w, h));
        frame.setVisible(true);
    }

/*
    private void setFrameSizeAndPos(JFrame frame,int w1, int h1) {

        //Set JFrame size here! Eg:
        frame.setPreferredSize(new Dimension(w1, h1));
        frame.pack();                                       //Set the frame size, you could change this to set it in a different way.
       // frame.setLocationRelativeTo(null);                  //Place frame in the center of the screen
    }

*/
    class SizeFrame extends AbstractAction {

    int hGo;
        SizeFrame(String name){
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (Log.isSelected()) {

                hGo=800;

            }else {

                hGo=200;

            }

            frame.setPreferredSize(new Dimension(wGo, hGo));
            frame.pack();

        }
    }

  /*
    private void init(JPanel panel) {
        //Setup your GUI here...
        JCheckBox button1 = new JCheckBox("Click me!");         //Create button


        button1.addActionListener((e)->{
            int wGo;
            int hGo;
            if (button1.isSelected()) {
                wGo=600;
                hGo=600;
                setFrameSizeAndPos(frame, wGo,hGo);
            }else {
                wGo=800;
                hGo=200;
                setFrameSizeAndPos(frame, wGo,hGo);
            }
        });

        panel.add(button1);

    }

    */


}