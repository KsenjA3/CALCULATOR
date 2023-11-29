
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.text.*;


class CalculateFace extends JFrame   {

    // меню
    JMenuBar jmb;
    JPopupMenu jpu;

    JFrame frame;

    //панели, колода карт
    CardLayout cardTypeCalc;
    JPanel cardPanel, commonPanel, engineerPanel, itPanel;
    JPanel textPanel, keyPanel, container;

    //ОКНО ВЫВОДА
    JLabel textRezult;                     //текстовые области вывода
    JScrollPane scrollinput, scrollLog;
    JTextPane textInput, textLog;


                        //ОКНО ВВОДА
                                                //button simple calculation
    JButton b, b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bPoint;
    JButton bPlus, bMinus, bDivide, bMultiply, bPercent, bRadical, bResult;
    JButton bMemoryAdd, bMemoryDel, bMemoryHold, bClear, bDel;

    private int N;                                  // restriction amount  input figures to number

    private String strNumber;               //inner number
    private Double dNumber;
    private Double dResult;                 //result of calculation
    private String  strInput;               // for output creation  to input window
    private String  strResult;              // result String content  for result window
    private calculate func;                 //type function

    private String nameSign;                // % and  divide for 0, input number after %
    private String strInputFormerSign;      //for % because number changed after %
    private double dResultPercent;          // for %, result before former sign

    private Double memory;
    private CalculateCurrentInput calculateCurrent;

    private StringBuffer sbLog;


                        // ВИД
    GridBagLayout gbag;
    GridBagConstraints gbc;
    Color paneColor, bColor, signColor, bMColor;    //used colors
    Border borderButton, borderText;                //borders used by elements

                            //используемые шрифты
    Font ButtonFont, ButtonFontM,  MenuFont, InputFont, ResultFont, LogFont;
    SimpleAttributeSet textInputAttributes;         //для JTextPane
    static final String FRONT_NAME_TEXT_INPUT = "Arial";
    static final String FRONT_NAME_TEXT_LOG = "Arial";
    static final int FRONT_TEXT_LOG = 18;
    static final int FRONT_TEXT_INPUT = 24 ;
    static final int FRONT_TEXT_RESULT = 20 ;

    //measure windows calculators
    int   widthSizeText, heightSizeMain, heightSizeText;
                            //height text windows
    static final int SIZE_TEXT_RESULT = 28;
    static final int SIZE_TEXT_INPUT = 103;
    static final int SIZE_TEXT_LOG= 192;
                            //height Keys Window
    static final int HIEGHT_SIZE_KEY = 260;
                            //width windows calculators
    static final int WIDTH_SIZE_SIMPLE = 260+20;
    static final int WIDTH_SIZE_ENGINEER = 600;
    static final int WIDTH_SIZE_IT = 150;

    CalculateFace() {

         N = 0;
         strNumber = "0";
         dNumber=0.0;
         dResult=0.0;
         strInput ="   ";
         strResult=" ";
         func=null;
         sbLog = new StringBuffer();

         nameSign="";                 // % и деление на 0, ввод числа после %
         strInputFormerSign="";             //for % because number changed after %
         dResultPercent=0.0;          //for % under mltidigited number

                        //цветовая гамма
        paneColor = new Color(231, 223, 232);
        bColor = new Color(236, 231, 237);
        signColor = new Color(213, 199, 214);
        bMColor = new Color(201, 184, 203);
                        //шрифт
        ButtonFont= new Font("Franklin Gothic Medium", Font.PLAIN, 30);
        ButtonFontM= new Font("Cambria", Font.PLAIN, 30);
        MenuFont =new Font("Arial", Font.PLAIN, 15);
        InputFont=new Font("Arial", Font.PLAIN, FRONT_TEXT_INPUT);
        ResultFont= new Font("Arial", Font.PLAIN, FRONT_TEXT_RESULT);
        LogFont =new Font("Arial", Font.PLAIN, 18);

                        //для сеточно-контейнерной компановки keyPanel и textPanel
        gbag = new GridBagLayout();
        gbc = new GridBagConstraints();

                        //создание корневой панели
/*
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());
        setContentPane(container);
*/

        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame();
        frame.setTitle("КАЛЬКУЛЯТОР");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        calculateCurrent = new CalculateCurrentInput ();

        makeButtons();                      //buttons calculator

         makeCard();

        jmb= new JMenuBar();
            makeViewMenu();                     // меню Вид
            makeCorrectMenu();                  //меню Правка
            makebriefMenu();                    //меню Справка
            makePopupMenu();                    // всплывающее меню
        frame.setJMenuBar(jmb);

 /*
        makeCommonCalculate();              //types calculator
        makeEngineerCalculate();            //after buttons because buttons on screen
        makeITCalculate();

        makeTextPanel();                     //input panel

        container.add(textPanel);
        container.add(keyPanel);
            // container.add(Box.createGlue());
*/

        frame.pack();

        keyPanel.requestFocusInWindow();
        ignoreLettersInput();

        frame.setVisible(true);
    }


    //create calculation Buttons all types
    void makeButtons() {

        // SIMPLE

                    //Numbers

        b1 = createNumberButton( new CreateActionNumberButton("1"), "1", KeyStroke.getKeyStroke('1'));
        b2 = createNumberButton(  new CreateActionNumberButton("2"), "2", KeyStroke.getKeyStroke('2'));
        b3 = createNumberButton( new CreateActionNumberButton("3"), "3", KeyStroke.getKeyStroke('3'));
        b4 = createNumberButton( new CreateActionNumberButton("4"), "4", KeyStroke.getKeyStroke('4'));
        b5 = createNumberButton(new CreateActionNumberButton("5"), "5", KeyStroke.getKeyStroke('5'));
        b6 = createNumberButton(new CreateActionNumberButton("6"), "6", KeyStroke.getKeyStroke('6'));
        b7 = createNumberButton(new CreateActionNumberButton("7"), "7", KeyStroke.getKeyStroke('7'));
        b8 = createNumberButton(new CreateActionNumberButton("8"), "8", KeyStroke.getKeyStroke('8'));
        b9 = createNumberButton(new CreateActionNumberButton("9"), "9", KeyStroke.getKeyStroke('9'));
        b0 = createNumberButton(new CreateActionNumberButton("0"), "0", KeyStroke.getKeyStroke('0'));
        bPoint = createNumberButton(new CreateActionNumberButton("."), ".", KeyStroke.getKeyStroke('.'));

                    //Operations

        bPlus = createSignButton(new CreateSignButton (" + "), " + ", KeyStroke.getKeyStroke('+'));
        bMinus = createSignButton(new CreateSignButton (" - "), " - ", KeyStroke.getKeyStroke('-'));
        bDivide = createSignButton(new CreateSignButton (" / "), " / ", KeyStroke.getKeyStroke('/'));
        bMultiply = createSignButton(new CreateSignButton (" * "), " * ", KeyStroke.getKeyStroke('*'));
        bPercent = createSignButton(new CreateSignButton (" % "), " % ", KeyStroke.getKeyStroke('%'));
        bResult = createSignButton(new CreateSignButton (" = "), " = ", KeyStroke.getKeyStroke('='));
        bRadical = createSignButton(new CreateSignButton (" √ "), " √ ", KeyStroke.getKeyStroke("sqrt"));

                    //Delete and Memory
        bClear = createWorkButton(new CreateWorkButton ("AC"), "AC", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        bDel = createWorkButton(new CreateWorkButton ("C"), "C", KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0));
        bMemoryAdd = createWorkButton(new CreateWorkButton ("M+"), "M+", KeyStroke.getKeyStroke(KeyEvent.VK_ADD,
                                                                                                        InputEvent.CTRL_DOWN_MASK));
        bMemoryDel = createWorkButton(new CreateWorkButton ("M-"), "M-", KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT,
                                                                                                        InputEvent.CTRL_DOWN_MASK));
        bMemoryHold = createWorkButton(new CreateWorkButton ("MR"), "MR", KeyStroke.getKeyStroke(KeyEvent.VK_M, 0));

        Service.blockedAll(bMemoryHold);
    }


    //create number Buttons
    JButton createNumberButton(Action bAction, String name, KeyStroke keyStroke) {
        b = new JButton(bAction);
        b.getInputMap  (JComponent.WHEN_IN_FOCUSED_WINDOW).put (keyStroke, name);
        b.getActionMap().put(name,bAction);

        b.setBackground(bColor);
        b.setFont(ButtonFont);
        borderButton = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        b.setBorder(borderButton);

        return b;
    }

   //create Actions for number Buttons
    class CreateActionNumberButton extends AbstractAction {

        CreateActionNumberButton (String nameButton) {
            super(nameButton);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = e.getActionCommand();
                                            //возврат шрифта после его изменений в =
            textRezult.setFont(ResultFont);
            StyleConstants.setFontSize(textInputAttributes,FRONT_TEXT_INPUT);
            textInput.setParagraphAttributes(textInputAttributes, true);

            if (N<15) { N++;

                strNumber = strNumber + name;             // формируем вводимое число типа String
                dNumber = Double.parseDouble(strNumber);  // из String в Double


                if (strNumber.equals("0.")&& name.equals("."))     //вывод на экран ввода в начале ввода
                    textInput.setText(strInput=strInput+strNumber);
                else
                    textInput.setText(strInput = strInput+name);

                // деление на 0 исключить
                if ((dNumber == 0.0) && (nameSign.equals(" / "))) {
                    strResult = "деление на 0 не возможно";
                    Service.blockedAll(b1, b2, b3, b4, b5, b6, b7, b8, b9, b0,
                            bPlus, bMinus, bDivide, bMultiply, bPercent, bRadical,
                            bResult, bMemoryAdd, bMemoryDel, bMemoryHold);
                } else {

                    dResult= calculateCurrent.calculateInput(strInput);

                    strResult =  "=" + Service.printNumber(dResult);
                    Service.unblockedAll(bPercent);       // работа % без ошибок
                }

                if (name.equals(".")) {
                    Service.blockedAll(bPoint);   //в числе не м.б. две точки
                    // разблокировка клавиш при попытке деления на 0
                    Service.unblockedAll( b1, b2, b3, b4, b5, b6, b7, b8, b9, b0,
                            bResult, bMemoryAdd, bMemoryDel,   bDel,
                            bPlus, bMinus, bDivide, bMultiply, bPercent, bRadical);
                }
                textRezult.setText(strResult);                 //запись на экран результата
            }
        }
    }


    //create  operations Buttons
    JButton createSignButton(Action bAction, String name, KeyStroke keyStroke) {
        b = new JButton(bAction);
        b.getInputMap  (JComponent.WHEN_IN_FOCUSED_WINDOW).put (keyStroke, name);
        b.getActionMap().put(name,bAction);

        if (name.equals(" = ")) {
            b.getInputMap  (JComponent.WHEN_IN_FOCUSED_WINDOW).put (KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0), "name=");
            b.getActionMap().put("name=",bAction);
        }

        b.setBackground(signColor);
        b.setFont(ButtonFont);
        borderButton = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        b.setBorder(borderButton);

        return b;
    }

    //create operations for number Buttons
    class CreateSignButton extends AbstractAction {
        String name;
        CreateSignButton (String nameButton) {
            super(nameButton);
            name = nameButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            //возврат шрифта после его изменений в =
            textRezult.setFont(ResultFont);
            StyleConstants.setFontSize(textInputAttributes,FRONT_TEXT_INPUT);
            textInput.setParagraphAttributes(textInputAttributes, true);

            strNumber="0";                      //подготовка для ввода очередного числа
            N=0;

            Service.unblockedAll(bPoint);       // разрешение на дробные числа
            Service.blockedAll(bPercent);       // работа % без ошибок

            //запись в в окно ввода
            if (!name.equals(" % "))       // delete % in input screen
                if (!name.equals(" = "))    // delete = in input screen

                    if (func==null)           // начало работы, после АС, после =

                        if (nameSign.equals(" √ "))
                            if (strInput.substring(strInput.length() - 3).equals(" √ "))
                                if (name.equals(" √ ")) {
                                    //несколько sqrt подряд
                                    textInput.setText(strInput = strInput + name);

                                } else          //замена sqrt другим знаком
                                    textInput.setText(strInput = strInput.substring(0, strInput.length() - 3) + name);
                            else {              // после числа знак
                                textInput.setText(strInput = strInput + name);

                            }
                        else
                        if (name.equals(" √ ")) {

                            switch (strInput.charAt(strInput.length() - 1)) {
                                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'->
                                        textInput.setText(strInput =strInput+ name); // ввод Number*sqrt (Number)

                                default ->
                                        textInput.setText(strInput = name);      // начало ввада с sqrt
                            }
                        } else              // начало ввода с [-+*/] или число [-+*/]
                            textInput.setText(strInput=Service.printNumber(dResult)+name);

                    else // func!=null
                        switch (strInput.substring(strInput.length() - 3)) {
                            case  " √ " -> {
                                if (name.equals(" √ ")) {
                                    textInput.setText(strInput = strInput + name);
                                }else
                                    //если два знака подряд + sqrt(*)  ->  на *
                                    switch (strInput.substring(strInput.length() - 6,strInput.length() - 3)) {
                                        case " + ", " - ", " * ", " / " ->  //замена двух знаков одним
                                                textInput.setText(strInput = strInput.substring(0, strInput.length() - 6) + name);
                                        default ->                          // замена одного знака другим
                                                textInput.setText(strInput = strInput.substring(0, strInput.length() - 3) + name);
                                    }
                            }
                            case " + ", " - ", " * ", " / " -> {
                                if (name.equals(" √ "))           // записываем sqrt после +-*/
                                    textInput.setText(strInput = strInput + name);
                                else                              // замена +-/* на +-*/
                                    textInput.setText(strInput = strInput.substring(0, strInput.length() - 3) + name);
                            }
                            default ->         // для иных знаков
                                    textInput.setText(strInput = strInput + name);
                        }

                else             //если после знака =, то знак стирается в строке ввода
                    switch (strInput.substring(strInput.length()-3)) {
                        case " + ", " - ", " * ", " / ", " √ " ->
                                textInput.setText(strInput = strInput.substring(0, strInput.length() - 3));
                    }


            switch (name) {

                case " + " -> {
                    func = Operations::plus;
                    dResultPercent = dResult;
                }
                case " - " ->  {
                    func= Operations::minus;
                    dResultPercent = dResult;
                }
                case " * " -> {
                    func= Operations::multiply;
                    dResultPercent = dResult;
                }
                case " / " ->  {
                    func = Operations::divide;
                    dResultPercent = dResult;
                }
                case " % " -> {
                    Service.unblockedAll(bPercent);       // работа % без ошибок

                    if (func == null) {
                        dResult = calculateCurrent.calculatePersent(func, nameSign,
                                dResultPercent, dNumber, dResult);
                        textInput.setText(Service.printNumber(dResult));
                    } else {
                        dResult = calculateCurrent.calculatePersent(func, nameSign,
                                dResultPercent, dNumber, dResult);

                        textInput.setText(strInput = strInputFormerSign + Service.printNumber(
                                calculateCurrent.calculateInput(Service.printNumber(dResult)+
                                        " - "+Service.printNumber(dResultPercent))));
                    }
                    strResult="=" +Service.printNumber(dResult);
                    textRezult.setText( strResult);

                    func = null;
                    strInput= "   ";            // ввод числа после %
                }
                case " = " -> {
                    textRezult.setFont(InputFont);          //смена шрифта
                    textRezult.setText(strResult);
                    StyleConstants.setFontSize(textInputAttributes,FRONT_TEXT_RESULT);
                    textInput.setParagraphAttributes(textInputAttributes, true);
                    textInput.setText(strInput);

                    sbLog.append(strInput).append("\n").append(strResult).append("\n");
                    textLog.setText(sbLog.toString());

                    Service.unblockedAll(bPercent);       // работа % без ошибок

                    strNumber = "0";                      // если после = начнется ввод с "."
                    func= null;
                    strInput= "   ";                      // ввод числа после =
                    //                  nameSign=" ";
                }
            }
            nameSign = name;
            strInputFormerSign=strInput;


        }
    }

    //creat Buttons for remember and delete
    JButton createWorkButton(Action bAction, String name, KeyStroke keyStroke) {
        b = new JButton(bAction);
        b.getInputMap  (JComponent.WHEN_IN_FOCUSED_WINDOW).put (keyStroke, name);
        b.getActionMap().put(name,bAction);

        b.setBackground(bMColor);
        b.setFont(ButtonFontM);
        borderButton = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        b.setBorder(borderButton);

        return b;
    }


    class CreateWorkButton extends AbstractAction {
        String name;

        CreateWorkButton  (String nameButton) {
            super(nameButton);
            name = nameButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            switch (name) {

                case "M+" -> {
                    memory=dResult;
                    Service.unblockedAll(bMemoryHold);
                }
                case "M-" -> {
                    memory=null;
                    Service.blockedAll(bMemoryHold);
                }
                case "MR" -> {
                    dNumber=memory;
                                                // вывод на экран ввода
                    switch (strInput.substring(strInput.length() - 1)) {
                                                // если до MR было число
                        case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "." -> {
                            boolean isFormerNumber=true;

                            while (isFormerNumber) {
                                strInput=strInput.substring(0, strInput.length() - 1);

                                switch (strInput.substring(strInput.length() - 1)) {
                                    case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "." ->
                                        isFormerNumber=true;
                                    default ->
                                        isFormerNumber=false;
                                }
                            }
                            textInput.setText(strInput = strInput+Service.printNumber(memory));
                        }
                                            // если до MR был знак
                        default ->
                            textInput.setText(strInput = strInput+Service.printNumber(memory));
                    }
                                            // деление на 0 исключить
                    if ((dNumber == 0.0) && (nameSign.equals(" / "))) {
                        strResult = "деление на 0 не возможно";
                        Service.blockedAll(b1, b2, b3, b4, b5, b6, b7, b8, b9, b0,
                            bPlus, bMinus, bDivide, bMultiply, bPercent, bRadical,
                            bResult, bMemoryAdd, bMemoryDel, bMemoryHold);
                    } else {
                        dResult= calculateCurrent.calculateInput(strInput);
                        strResult = "=" + Service.printNumber(dResult);
                    }

                    textRezult.setText(strResult);           //запись на экран результата

                    Service.unblockedAll(bPercent);       // работа % без ошибок

                }
                case "AC" -> {
                    Service.unblockedAll( b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bPoint,
                        bMemoryAdd, bMemoryDel,  bDel,
                        bPlus, bMinus, bDivide, bMultiply, bPercent, bRadical);

                    if (memory!=null)
                        Service.unblockedAll(  bMemoryHold);

                    dNumber=0.0;
                    strNumber="0";

                    textRezult.setText("0");
                    textInput.setText(strInput=" ");

                    func=null;
                    dResult=0.0;                // знак после АС
                    strInput= "   ";            //цифра после АС
                    strResult="0";              // AC затем =, textRez
                    nameSign=" ";               //после sqrt
                }
                case "C" -> {
                                    // окно ввовда
                    switch (strInput.charAt(strInput.length() - 1)) {
                        case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'-> {
                            textInput.setText(strInput = strInput.substring(0,strInput.length() - 1));
                            dResult= calculateCurrent.calculateInput(strInput);
                            if (strNumber.length()>1)
                                strNumber=strNumber.substring(0,strNumber.length() - 1);
                        }
                        default ->{
                            textInput.setText(strInput = strInput.substring(0, strInput.length() - 3));
                            dResult= calculateCurrent.calculateInput(strInput);

                        }
                    }
                    strResult = "=" + Service.printNumber(dResult);
                    textRezult.setText(strResult);

                    Service.unblockedAll( b1, b2, b3, b4, b5, b6, b7, b8, b9, b0,bPoint,
                        bMemoryAdd, bMemoryDel, bMemoryHold,  bDel,
                        bPlus, bMinus, bDivide, bMultiply, bPercent, bRadical );
                }
            }
        }
    }

    class FocusKeyPanel extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            String str =textInput.getText();

            System.out.println(str);

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

            textInput.setText(strInput=str);
            System.out.println(str);

            dResult= calculateCurrent.calculateInput(strInput);

            strResult = "=" + Service.printNumber(dResult);
            textRezult.setText(strResult);

            keyPanel.requestFocusInWindow();
        }
    }


    void ignoreLetter (char ... var){
         for (char c: var) {
            textInput.getInputMap().put(KeyStroke.getKeyStroke(c), "none");
    }

}
    void ignoreLettersInput() {
        var  KeyInputAction = new FocusKeyPanel();
        textInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "focusKeyPanel");
        textInput.getActionMap().put("focusKeyPanel",KeyInputAction );

        textInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), "correctInput");
        textInput.getActionMap().put("correctInput",KeyInputAction );

        ignoreLetter(
            'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m',
                 'Q','W','E','R','T','Y','U','I','O','P','A','S','D','F','G','H','J','K','L','Z','X','C','V','B','N','M',
                '<','>','?','!','@','#','$','%','^','&','(',')',':',';','"',',','[',']','{','}','`','~',
        'ё','й','ц','у','к','е','н','г','ш','щ','з','х','ъ','ф','ы','в','а','п','р','о','л','д','ж','э','я','ч','с','м','и','т','ь','б','ю',
        'Ё','Й','Ц','У','К','Е','Н','Г','Ш','Щ','З','Х','Ъ','Ф','Ы','В','А','П','Р','О','Л','Д','Ж','Э','Я','Ч','С','М','И','Т','Ь','Б','Ю'
        );
    }



    //создание  экрана  ввода-вывода
    void makeTextPanel() {

        textPanel = new JPanel();
            textPanel.setBackground(paneColor);
            textPanel.setLayout(gbag);
        // textPanel.setPreferredSize(new Dimension(WidthSize,HeightSizeText)); автоматически
        borderText = BorderFactory.createLineBorder(Color.BLACK, 2);
            textPanel.setBorder(borderText);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());

        textInput = new JTextPane();
            textInput.setBackground(paneColor);
            textInputAttributes =  new SimpleAttributeSet();
                StyleConstants.setAlignment(textInputAttributes, StyleConstants.ALIGN_RIGHT);
                StyleConstants.setFontFamily(textInputAttributes,FRONT_NAME_TEXT_INPUT);
                StyleConstants.setFontSize(textInputAttributes,FRONT_TEXT_INPUT);
            textInput.setParagraphAttributes(textInputAttributes, true);
        scrollinput= new JScrollPane(textInput,
                                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollinput.setPreferredSize(new Dimension(widthSizeText,SIZE_TEXT_INPUT));
            scrollinput.setBorder(null);

        textRezult = new JLabel("0");
            textRezult.setFont(ResultFont);
        JPanel labPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            labPanel.setBackground(bColor);
            labPanel.setPreferredSize(new Dimension(widthSizeText, SIZE_TEXT_RESULT));
        labPanel.add(textRezult);

        textLog= new JTextPane();
            textLog.setBackground(bColor);
            var  textLogAttributes =  new SimpleAttributeSet();
                StyleConstants.setAlignment(textLogAttributes, StyleConstants.ALIGN_RIGHT);
                StyleConstants.setFontFamily(textLogAttributes,FRONT_NAME_TEXT_LOG);
                StyleConstants.setFontSize(textLogAttributes,FRONT_TEXT_LOG);
                StyleConstants.setForeground(textLogAttributes, Color.GRAY);
            textLog.setParagraphAttributes(textLogAttributes, true);
        scrollLog= new JScrollPane(textLog,
                                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollLog.setPreferredSize(new Dimension(widthSizeText,SIZE_TEXT_LOG));
            scrollLog.setBorder(null);


        textPanel.add(scrollLog);
        textPanel.add(scrollinput );
        textPanel.add(labPanel, Component.RIGHT_ALIGNMENT);
    }





    // создание простого калькулятора
    void makeCommonCalculate() {
        // размеры всех окон

 //       heightSizeText = SIZE_TEXT_INPUT+SIZE_TEXT_RESULT;
        heightSizeText = SIZE_TEXT_INPUT+SIZE_TEXT_RESULT+SIZE_TEXT_LOG;
        heightSizeMain = heightSizeText+HIEGHT_SIZE_KEY;

        // создание keyPanel
        keyPanel = new JPanel();
        keyPanel.setBackground(paneColor);
        keyPanel.setPreferredSize (new Dimension(WIDTH_SIZE_SIMPLE,HIEGHT_SIZE_KEY));


        // сеточно-контейнерная компоновка keyPanel
        keyPanel.setLayout(gbag);
        gbc.fill = GridBagConstraints.CENTER;
        gbc.weightx = 100;
        gbc.weighty = 100;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.ipady = 0;
        gbc.ipadx = 10;
        gbc.gridy = 0;
        gbc.gridx = 0;
        keyPanel.add(bClear, gbc);
        gbc.ipadx = 29;
        gbc.gridx = 1;
        keyPanel.add(bDel, gbc);
        gbc.ipadx = 1;
        gbc.gridx = 2;
        keyPanel.add(bMemoryHold, gbc);
        gbc.ipadx = 4;
        gbc.gridx = 3;
        keyPanel.add(bMemoryAdd, gbc);
        gbc.ipadx = 11;
        gbc.gridx = 4;
        keyPanel.add(bMemoryDel, gbc);

        gbc.ipady = 0;
        gbc.ipadx = 20;
        gbc.gridy = 1;
        gbc.gridx = 0;
        keyPanel.add(b7, gbc);
        gbc.gridx = 1;
        keyPanel.add(b8, gbc);
        gbc.gridx = 2;
        keyPanel.add(b9, gbc);
        gbc.ipadx = 8;
        gbc.gridx = 3;
        keyPanel.add(bDivide, gbc);
        gbc.ipadx = 0;
        gbc.gridx = 4;
        keyPanel.add(bPercent, gbc);

        gbc.ipadx = 20;
        gbc.gridy = 2;
        gbc.gridx = 0;
        keyPanel.add(b4, gbc);
        gbc.gridx = 1;
        keyPanel.add(b5, gbc);
        gbc.gridx = 2;
        keyPanel.add(b6, gbc);
        gbc.ipadx = 6;
        gbc.gridx = 3;
        keyPanel.add(bMultiply, gbc);
        gbc.gridx = 4;
        gbc.ipadx = 8;
        keyPanel.add(bRadical, gbc);

        gbc.ipadx = 20;
        gbc.gridy = 3;
        gbc.gridx = 0;
        keyPanel.add(b1, gbc);
        gbc.gridx = 1;
        keyPanel.add(b2, gbc);
        gbc.gridx = 2;
        keyPanel.add(b3, gbc);
        gbc.ipadx = 6;
        gbc.gridx = 3;
        keyPanel.add(bPlus, gbc);

        gbc.ipadx = 6;
        gbc.ipady = 53;
        gbc.gridx = 4;
        gbc.gridheight = 2;
        keyPanel.add(bResult, gbc);

        gbc.ipadx = 71;
        gbc.ipady = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.gridy = 4;
        gbc.gridx = 0;
        keyPanel.add(b0, gbc);

        gbc.ipadx = 30;
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        keyPanel.add(bPoint, gbc);

        gbc.ipadx = 16;
        gbc.ipady = 0;
        gbc.gridx = 3;
        keyPanel.add(bMinus, gbc);


    }

    void makeEngineerCalculate() {
        widthSizeText = WIDTH_SIZE_ENGINEER;

        //       heightSizeText = SIZE_TEXT_INPUT+SIZE_TEXT_RESULT;
        heightSizeText = SIZE_TEXT_INPUT+SIZE_TEXT_RESULT+SIZE_TEXT_LOG;
        heightSizeMain = heightSizeText+HIEGHT_SIZE_KEY;

        // создание keyPanel
        keyPanel = new JPanel();
        keyPanel.setBackground(paneColor);
        keyPanel.setPreferredSize (new Dimension(WIDTH_SIZE_ENGINEER,HIEGHT_SIZE_KEY));

    }

    void makeITCalculate() {
        widthSizeText = WIDTH_SIZE_IT;

        //       heightSizeText = SIZE_TEXT_INPUT+SIZE_TEXT_RESULT;
        heightSizeText = SIZE_TEXT_INPUT+SIZE_TEXT_RESULT+SIZE_TEXT_LOG;
        heightSizeMain = heightSizeText+HIEGHT_SIZE_KEY;

        // создание keyPanel
        keyPanel = new JPanel();
        keyPanel.setBackground(paneColor);
        keyPanel.setPreferredSize (new Dimension(WIDTH_SIZE_IT,HIEGHT_SIZE_KEY));

    }

    //создание колоды карт
    void makeCard() {

        cardTypeCalc = new CardLayout();            //компоновка
        cardPanel = new JPanel();                   //колода
        cardPanel.setLayout(cardTypeCalc);          //компоновка колоды

        //карты в колоде
        commonPanel = new JPanel();
                commonPanel.setLayout(new BoxLayout(commonPanel, BoxLayout.Y_AXIS));
                add(Box.createVerticalGlue());
                setContentPane(commonPanel);
            makeCommonCalculate();
            widthSizeText=WIDTH_SIZE_SIMPLE;
            makeTextPanel();
        commonPanel.add(textPanel);
        commonPanel.add(keyPanel);


        engineerPanel = new JPanel();
                engineerPanel.setLayout(new BoxLayout(engineerPanel, BoxLayout.Y_AXIS));
                add(Box.createVerticalGlue());
                setContentPane(engineerPanel);
            makeEngineerCalculate();
            widthSizeText=WIDTH_SIZE_ENGINEER;
            makeTextPanel();
        engineerPanel.add(textPanel);
        engineerPanel.add(keyPanel);



        itPanel = new JPanel();
                itPanel.setLayout(new BoxLayout(itPanel, BoxLayout.Y_AXIS));
                add(Box.createVerticalGlue());
                setContentPane(itPanel);
            makeITCalculate();
            widthSizeText=WIDTH_SIZE_IT;
            makeTextPanel();
        itPanel.add(textPanel);
        itPanel.add(keyPanel);


        //формирование колоды
        cardPanel.add(commonPanel, "Simple");
        cardPanel.add(engineerPanel, "Engineer");
        cardPanel.add(itPanel, "ITcalc");

        frame.add(cardPanel);                         //ввод колоды в главный фрейм
    }


    // создание меню Вид
    // с мнемониками и оперативными клавишами и всплывающими подсказками
    void makeViewMenu() {
        JMenu jmView = new JMenu("Вид");
            jmView.setFont(MenuFont);

        var jmiCommon = new JRadioButtonMenuItem("Обычный");
            jmiCommon.setFont(MenuFont);
            jmiCommon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.ALT_DOWN_MASK));
        var jmiEngineer = new JRadioButtonMenuItem("Инженерный");
            jmiEngineer.setFont(MenuFont);
            jmiEngineer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.ALT_DOWN_MASK));
        var jmiIT = new JRadioButtonMenuItem("IT");
            jmiIT.setFont(MenuFont);
            jmiIT.setMnemonic('I');
            jmiIT.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.ALT_DOWN_MASK));
        var bg = new ButtonGroup();
            bg.add(jmiCommon);
            bg.add(jmiEngineer);
            bg.add(jmiIT);

        var jchbLog = new JCheckBoxMenuItem("Журнал");
            jchbLog.setFont(MenuFont);
            jchbLog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.ALT_DOWN_MASK));
        var jchbGroupDigit = new JCheckBoxMenuItem("Числовые разряды");
            jchbGroupDigit.setFont(MenuFont);
            jchbGroupDigit.setToolTipText("Группировка цифр по разрядам");

        jmView.add(jmiCommon);
        jmView.add(jmiEngineer);
        jmView.add(jmiIT);
        jmView.addSeparator();
        jmView.add(jchbLog);
        jmView.add(jchbGroupDigit);

        jmb.add(jmView);

        jmiCommon.addActionListener((ae)->{
            cardTypeCalc.show(cardPanel,"Simple");
            frame.setPreferredSize(new Dimension(WIDTH_SIZE_SIMPLE, heightSizeMain));
            frame.pack();
        });
        jmiEngineer.addActionListener((ae)->{
            cardTypeCalc.show(cardPanel,"Engineer");
            frame.setPreferredSize(new Dimension(WIDTH_SIZE_ENGINEER, heightSizeMain));
            frame.pack();
        });
        jmiIT.addActionListener((ae)->{
            cardTypeCalc.show(cardPanel,"ITcalc");
            frame.setPreferredSize(new Dimension(WIDTH_SIZE_IT, heightSizeMain));
            frame.pack();
        });
        //       jchbLog.addActionListener(this);
        //       jchbGroupDigit.addActionListener(this);

    }


    void makeCorrectMenu() {
        JMenu jmCorrect = new JMenu("Правка");

    }

    void makebriefMenu() {

    }

    void makePopupMenu() {
        jpu = new JPopupMenu();

    }

    // установить размеры окна
  //  public Dimension getPreferredSize() { return new Dimension(widthSizeMain, heightSizeMain); }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculateFace();
            }
        });
    }

}



