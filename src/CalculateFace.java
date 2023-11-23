
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.text.*;


class CalculateFace extends JFrame  implements ActionListener {

    //размеры окон калькулятора
    int widthSize, widthSizeMain, heightSizeMain, heightSizeText, heightSizeKey;

    // меню
    JMenuBar jmb;
    JPopupMenu jpu;

    //панели, колода карт
    CardLayout cardTypeCalc;
    JPanel cardPanel, commonPanel, engineerPanel, itPanel;
    JPanel textPanel, keyPanel, container;

    //ОКНО ВЫВОДА
    JLabel textRezult, textLog;                     //текстовые области вывода
    JScrollPane scrollinput;
    JTextPane textInput;

    ArrayList <JTextPane> arrLog;                   // для журнала

                        //ОКНО ВВОДА
                                                //button simple calculation
    JButton b, b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bPoint;
    JButton bPlus, bMinus, bDivide, bMultiply, bPercent, bRadical, bResult;
    JButton bMemoryAdd, bMemoryDel, bMemoryHold, bClear, bDel;

    private int N;                                  // ограничение количества вводимых цифр в число

    private String strNumber;                    //вводимое число
    private Double dNumber, dResult;           //для вычислений
    private String  strInput;                     // для формирования вывода на экран ввода
    private String  strResult;                  // конечный текст выводимый в окне результата
    private calculate func;                       //тип функции

    private String nameSign;                // % и деление на 0, ввод числа после %
    private String strLabelSign;            //for % because number changed after %
    private double dResultPercent;         //for % under mltidigited number


    private double dResultFormertMemory;        //for  Memory rolled back numbers, wich in front of Memory
    private Double memory;
    private CalculateCurrentInput calculateCurrent;



                        // ВИД
    GridBagLayout gbag;
    GridBagConstraints gbc;
    Color paneColor, bColor, signColor, bMColor;    //используемые цвета
    Border borderButton, borderText;                //используемые границы элементов

                            //используемые шрифты
    Font ButtonFont, ButtonFontM,  MenuFont, InputFont, ResultFont, LogFont;
    SimpleAttributeSet textInputAttributes;         //для JTextPane
    static final String FRONT_NAME_TEXT_INPUT = "Arial";
    static final int FRONT_TEXT_INPUT = 24 ;
    static final int FRONT_TEXT_RESULT = 20 ;
                            //размеры текстовых областей
    static final int SIZE_TEXT_RESULT = 28;
    static final int SIZE_TEXT_INPUT = 103;



    CalculateFace() {

         N = 0;
         strNumber = "0";             //вводимое число
         dNumber=0.0;                 //для вычислений
         dResult=0.0;
         strInput ="   ";             // для формирования вывода на экран ввода
         strResult=" ";               // конечный текст выводимый в окне результата
         func=null;                   //тип функции

         nameSign="";                 // % и деление на 0, ввод числа после %
         strLabelSign="";             //for % because number changed after %
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
        LogFont =new Font("Arial", Font.PLAIN, 15);

                        //для сеточно-контейнерной компановки keyPanel и textPanel
        gbag = new GridBagLayout();
        gbc = new GridBagConstraints();

                        //создание корневой панели
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());
        setContentPane(container);

        calculateCurrent = new CalculateCurrentInput ();

        makeButtons();                      //кнопки калькулятора

        makeCommonCalculate();              //типы калькуляторов
        makeEngineerCalculate();
        makeITCalculate();

        makeTextPanel();                     //экран ввода

        container.add(textPanel);
        container.add(keyPanel);
            // container.add(Box.createGlue());

        // makeCard();

        jmb= new JMenuBar();
        makeViewMenu();                     // меню Вид
        makeCorrectMenu();                  //меню Правка
        makebriefMenu();                    //меню Справка
        makePopupMenu();                    // всплывающее меню
        setJMenuBar(jmb);

        pack();
    }

    //create calculation Buttons all types
    void makeButtons() {

                // простой
        b1 = createNumberButton("1");
        b2 = createNumberButton("2");
        b3 = createNumberButton("3");
        b4 = createNumberButton("4");
        b5 = createNumberButton("5");
        b6 = createNumberButton("6");
        b7 = createNumberButton("7");
        b8 = createNumberButton("8");
        b9 = createNumberButton("9");
        b0 = createNumberButton("0");
        bPoint = createNumberButton(".");

        bPlus = createSignButton(" + ");
        bMinus = createSignButton(" - ");
        bDivide = createSignButton(" / ");
        bMultiply = createSignButton(" * ");
        bPercent = createSignButton(" % ");
        bRadical = createSignButton(" √ ");
        bResult = createSignButton(" = ");

        bClear = createWorkButton("AC");
        bDel = createWorkButton("C");
        bMemoryAdd = createWorkButton("M+");
        bMemoryDel = createWorkButton("M-");
        bMemoryHold = createWorkButton("MR");
        Service.blockedAll(bMemoryHold);
    }

    //create number Buttons
    JButton createNumberButton(String name) {
        b = new JButton(name);
        b.setBackground(bColor);
        b.setFont(ButtonFont);
        borderButton = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        b.setBorder(borderButton);

        b.addActionListener((e) -> {

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
                            bResult, bMemoryAdd, bMemoryDel, bMemoryHold,  bDel,
                            bPlus, bMinus, bDivide, bMultiply, bPercent, bRadical);
                }
                textRezult.setText(strResult);                 //запись на экран результата
            }
        });
        return b;
    }


    //create  operation Buttons
    JButton createSignButton(String name) {
        b = new JButton(name);
        b.setBackground(signColor);
        b.setFont(ButtonFont);
        borderButton = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        b.setBorder(borderButton);

        b.addActionListener((e) -> {

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
   //                                 figureSqrt++;
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
                        dResult = dResult / 100.0;
                        textInput.setText(Service.printNumber(dResult));
                    } else {
                        switch (nameSign) {
                            case " + ", " - " -> dNumber = dResultPercent * dNumber / 100.0;
                            case " * ", " / " -> dNumber = dNumber / 100.0;
                        }
                        dResult = Operations.result(func, dResultPercent, dNumber);
                        textInput.setText(strInput = strLabelSign + Service.printNumber(dNumber));
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

                    Service.unblockedAll(bPercent);       // работа % без ошибок

                    strNumber = "0";                      // если после = начнется ввод с "."

                    func= null;
                    strInput= "   ";                      // ввод числа после =

  //                  nameSign=" ";

                }
            }
            nameSign = name;
            strLabelSign=strInput;
        });
        return b;
    }


    //creat Buttons for remember and delete
    JButton createWorkButton(String name) {
        b = new JButton(name);
        b.setBackground(bMColor);
        b.setFont(ButtonFontM);
        borderButton = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        b.setBorder(borderButton);

        b.addActionListener((e) -> {

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
                                    bMemoryAdd, bMemoryDel, bMemoryHold,  bDel,
                                    bPlus, bMinus, bDivide, bMultiply, bPercent, bRadical);

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
        );
        return b;
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
        // StyleConstants.setBackground(textInputAttributes, PaneColor); не установился
        textInput.setParagraphAttributes(textInputAttributes, true);
        scrollinput= new JScrollPane(textInput,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollinput.setPreferredSize(new Dimension(widthSize,SIZE_TEXT_INPUT));
        scrollinput.setBorder(null);



        textRezult = new JLabel("0");
        textRezult.setFont(ResultFont);
        JPanel labPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        labPanel.setBackground(bColor);
        labPanel.setPreferredSize(new Dimension(widthSize, SIZE_TEXT_RESULT));
        labPanel.add(textRezult);



        textLog= new JLabel(" ", JLabel.RIGHT);

        textPanel.add(scrollinput );
        textPanel.add(labPanel, Component.RIGHT_ALIGNMENT);


    }

    // создание простого калькулятора
    void makeCommonCalculate() {
        // размеры всех окон
        widthSize = 260;
        heightSizeKey = 260;
        heightSizeText = SIZE_TEXT_INPUT+SIZE_TEXT_RESULT;
        heightSizeMain = heightSizeText+heightSizeKey;
        widthSizeMain=widthSize+20;
        // создание keyPanel
        keyPanel = new JPanel();
        keyPanel.setBackground(paneColor);
        keyPanel.setPreferredSize (new Dimension(widthSize,heightSizeKey));


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

    }

    void makeITCalculate() {

    }

    //создание колоды карт
    void makeCard() {
        cardTypeCalc = new CardLayout();            //компоновка
        cardPanel = new JPanel();                   //колода
        cardPanel.setLayout(cardTypeCalc);          //компоновка колоды

        //карты в колоде
        commonPanel = new JPanel();
        engineerPanel = new JPanel();
        itPanel = new JPanel();
        //формирование колоды
        cardPanel.add(commonPanel, "Обычный");
        cardPanel.add(engineerPanel, "Инженерный");
        cardPanel.add(itPanel, "IT");

        add(cardPanel);                         //ввод колоды в главный фрейм
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

        //  jmiCommon.addActionListener((ae)->cardTypeCalc.show(cardPanel,"Обычный"));
        //  jmiEngineer.addActionListener((ae)->cardTypeCalc.show(cardPanel,"Инженерный"));
        //  jmiIT.addActionListener((ae)->cardTypeCalc.show(cardPanel,"IT"));
        //       jchbLog.addActionListener(this);
        //       jchbGroupDigit.addActionListener(this);

        jmb.add(jmView);
    }

    void makeActions() {


    }

    void makeCorrectMenu() {
        JMenu jmCorrect = new JMenu("Правка");

    }

    void makebriefMenu() {

    }

    void makePopupMenu() {
        jpu = new JPopupMenu();

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    // установить размеры окна
    public Dimension getPreferredSize() {
        return new Dimension(widthSizeMain, heightSizeMain);
    }

}



