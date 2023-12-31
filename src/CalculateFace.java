
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.text.*;


class CalculateFace extends JFrame {

    /**
     * button simple calculation
     */
    private JButton b, b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bPoint;
    private JButton bPlus, bMinus, bDivide, bMultiply, bPercent, bRadical, bResult;
    private JButton bMemoryAdd, bMemoryDel, bMemoryHold, bClear, bDel;

    /**
     * restriction amount  input figures to number
     */
    private int N;

    /**
     * inly number
     */
    private String strNumber;
    private Double dNumber;

    /**
     * result of calculation
     */
    private String strResult;
    private Double dResult;

    /**
     * input String to view window
     */
    private String strInput;

    /**
     * type function
     */
    private calculate func;

    /**
     * for calculate Percent
     *  divide for 0, input number after %
     * number after %
     * result before former sign
     */
    private String nameSign;
    private String strInputFormerSign;
    private double dResultPercent;

    /**
     * to safe into the memory
     */
    private Double memory;

    /**
     * object for calculation
     */
    private CalculateCurrentInput calculateCurrent;

    /**
     * String for log
     */
    private StringBuffer sbLog;

    /**
     * VIEW
     */
    private GridBagLayout gbag;
    private GridBagConstraints gbc;
    private Border borderButton, borderText;                //borders used by elements

    /**
     * MENU
     */
    private JMenuBar jmb;
    private JPopupMenu jpu;
    private JCheckBoxMenuItem jchbLog;
    private JMenuItem jmiShowLogPopup, jmiHideLogPopup, jmiClearLogPopup, jmiCopyLogPopup,
                            jmiClearLog, jmiCopyLog;
    private JRadioButtonMenuItem jmiSimple, jmiEngineer, jmiIT;
    private MakeMenuItem actionCopy, actionPaste, actionClearLog, actionCopyLog,
                 actionBrief, actionSimple, actionEngineer, actionIT;
    private MakeLogMenuItem actionLog, actionShowLogPopup, actionHideLogPopup;

    /**
     * Components
     */
    private JFrame frame;
    private Container container;
    private CardLayout cardTypeCalc;
    private JPanel cardPanel;
    private JPanel keyPanelSimple, keyPanelEngineer, keyPanelIT;
    private JPanel textPanel, labPanel;

    /**
     * Text output components
     */
    private JLabel textRezult;
    private JScrollPane scrollinput, scrollLog;
    private JTextPane textInput, textLog;

    /**
     * FONTs
     */

    private SimpleAttributeSet textInputAttributes;         //for JTextPane



    /**
     * measure windows calculators
     * height frame, result height textPanel
     * selected width
     */
    private int heightSizeMain, heightSizeText;
    private int widthSizeText;

    /**
     * SIZE COMPONENTs
     * height text components
     * height keyPanel
     * width keyPanels
     */

    CalculateFace() {

        N = 0;
        strNumber = "0";
        dNumber = 0.0;
        dResult = 0.0;
        strInput = "   ";
        strResult = " ";
        func = null;
        sbLog = new StringBuffer();

        /*
         * for calculate Percent
         * % and divide for 0, input number after %
         * for % because number changed after %
         * for % under mltidigited number
         */
        nameSign = "";
        strInputFormerSign = "";
        dResultPercent = 0.0;


        //GridBagLayout
        gbag = new GridBagLayout();
        gbc = new GridBagConstraints();

        //create object for calculation
        calculateCurrent = new CalculateCurrentInput();

        //create frame
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame();
        frame.setTitle("КАЛЬКУЛЯТОР");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create Content Pane
        container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        frame.add(Box.createVerticalGlue());
        frame.setContentPane(container);

        //create all buttons
        makeButtons();

        //fill Content Pane
        makeCard();
        makeTextPanel();
            textPanelInputKeys();
        container.add(textPanel);
        container.add(cardPanel);

        //MENU
        jmb = new JMenuBar();
        makeViewMenu();
        makeCorrectMenu();
        makeBriefMenu();
        frame.setJMenuBar(jmb);

        //make and set PopupMenu
        makePopupMenu();
        mouseListenerPopupMenu(textLog,textInput,textRezult);

        /*
         INITIAL calculation
         chose card to init calculator
         widthSizeText = width frame and other components
         setting height textPanel (height keyPanel = const)
         */
        cardTypeCalc.show(cardPanel, "Simple");
        widthSizeText = MySizePanel.WIDTH_SIZE_SIMPLE.get();
        heightSizeText = MySizePanel.HIEGHT_SIZE_TEXT_INPUT.get()
                        + MySizePanel.HIEGHT_SIZE_TEXT_RESULT.get();
            scrollLog.setVisible(false);
        repack();

        frame.setVisible(true);
    }

//BUTTONS

    /**
     * create ALL Buttons
      */
    private void makeButtons() {
        //SIMPLE CALCULATOR

        //Numbers
        b1 = createButton(new CreateActionNumberButton("1"),
                "1", KeyStroke.getKeyStroke('1') ,MyColors.COLOR_BUTTON.get(), MyFonts.FONT_BUTTON.get());
        b2 = createButton(new CreateActionNumberButton("2"),
                "2", KeyStroke.getKeyStroke('2'),MyColors.COLOR_BUTTON.get(), MyFonts.FONT_BUTTON.get());
        b3 = createButton(new CreateActionNumberButton("3"),
                "3", KeyStroke.getKeyStroke('3'),MyColors.COLOR_BUTTON.get(), MyFonts.FONT_BUTTON.get());
        b4 = createButton(new CreateActionNumberButton("4"),
                "4", KeyStroke.getKeyStroke('4'),MyColors.COLOR_BUTTON.get(), MyFonts.FONT_BUTTON.get());
        b5 = createButton(new CreateActionNumberButton("5"),
                "5", KeyStroke.getKeyStroke('5'),MyColors.COLOR_BUTTON.get(), MyFonts.FONT_BUTTON.get());
        b6 = createButton(new CreateActionNumberButton("6"),
                "6", KeyStroke.getKeyStroke('6'),MyColors.COLOR_BUTTON.get(), MyFonts.FONT_BUTTON.get());
        b7 = createButton(new CreateActionNumberButton("7"),
                "7", KeyStroke.getKeyStroke('7'),MyColors.COLOR_BUTTON.get(), MyFonts.FONT_BUTTON.get());
        b8 = createButton(new CreateActionNumberButton("8"),
                "8", KeyStroke.getKeyStroke('8'),MyColors.COLOR_BUTTON.get(), MyFonts.FONT_BUTTON.get());
        b9 = createButton(new CreateActionNumberButton("9"),
                "9", KeyStroke.getKeyStroke('9'),MyColors.COLOR_BUTTON.get(), MyFonts.FONT_BUTTON.get());
        b0 = createButton(new CreateActionNumberButton("0"),
                "0", KeyStroke.getKeyStroke('0'),MyColors.COLOR_BUTTON.get(), MyFonts.FONT_BUTTON.get());
        bPoint = createButton(new CreateActionNumberButton("."),
                ".", KeyStroke.getKeyStroke('.'),MyColors.COLOR_BUTTON.get(), MyFonts.FONT_BUTTON.get());


        //Operations
        bPlus = createButton(new CreateSignButton(" + "),
                " + ", KeyStroke.getKeyStroke('+'), MyColors.COLOR_SIGN.get(), MyFonts.FONT_BUTTON.get());
        bMinus = createButton(new CreateSignButton(" - "),
                " - ", KeyStroke.getKeyStroke('-'), MyColors.COLOR_SIGN.get(), MyFonts.FONT_BUTTON.get());
        bDivide = createButton(new CreateSignButton(" / "),
                " / ", KeyStroke.getKeyStroke('/'), MyColors.COLOR_SIGN.get(), MyFonts.FONT_BUTTON.get());
        bMultiply = createButton(new CreateSignButton(" * "),
                " * ", KeyStroke.getKeyStroke('*'), MyColors.COLOR_SIGN.get(), MyFonts.FONT_BUTTON.get());
        bPercent = createButton(new CreateSignButton(" % "),
                " % ", KeyStroke.getKeyStroke('%'), MyColors.COLOR_SIGN.get(), MyFonts.FONT_BUTTON.get());
        bResult = createButton(new CreateSignButton(" = "),
                " = ", KeyStroke.getKeyStroke('='), MyColors.COLOR_SIGN.get(), MyFonts.FONT_BUTTON.get());
        bRadical = createButton(new CreateSignButton(" √ "),
                " √ ", KeyStroke.getKeyStroke("sqrt"), MyColors.COLOR_SIGN.get(), MyFonts.FONT_BUTTON.get());


        //Delete and Memory
        bClear = createButton(new CreateWorkButton("AC"),
                "AC", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), MyColors.COLOR_BUTTON_MEMORY.get(), MyFonts.FONT_BUTTON_MEMORY.get());
        bDel = createButton(new CreateWorkButton("C"),
                "C", KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), MyColors.COLOR_BUTTON_MEMORY.get(), MyFonts.FONT_BUTTON_MEMORY.get());
        bMemoryAdd = createButton(new CreateWorkButton("M+"),
                "M+", KeyStroke.getKeyStroke(KeyEvent.VK_ADD, InputEvent.CTRL_DOWN_MASK), MyColors.COLOR_BUTTON_MEMORY.get(), MyFonts.FONT_BUTTON_MEMORY.get());
        bMemoryDel = createButton(new CreateWorkButton("M-"),
                "M-", KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, InputEvent.CTRL_DOWN_MASK), MyColors.COLOR_BUTTON_MEMORY.get(), MyFonts.FONT_BUTTON_MEMORY.get());
        bMemoryHold = createButton(new CreateWorkButton("MR"),
                "MR", KeyStroke.getKeyStroke(KeyEvent.VK_M, 0), MyColors.COLOR_BUTTON_MEMORY.get(), MyFonts.FONT_BUTTON_MEMORY.get());

        blockedAll(bMemoryHold);
    }

    /**
     *create Button
     * @param bAction behavior button
     * @param name object to link InputMap with ActionMap
     * @param keyStroke name key linked with button
     * @param color color button
     * @param font font button
     * @return button
     */
    private JButton createButton(Action bAction, String name,
                         KeyStroke keyStroke, Color color,Font font) {
        b = new JButton(bAction);
        b.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
        b.getActionMap().put(name, bAction);
        b.setBackground(color);
        b.setFont(font);
        borderButton = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        b.setBorder(borderButton);

        if (name.equals(" = ")) {
            b.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "name=");
            b.getActionMap().put("name=", bAction);
        }
        return b;
    }

    /**
     * behavior number Buttons
     */
    class CreateActionNumberButton extends AbstractAction {
        String name;
        CreateActionNumberButton(String nameButton) {
            super(nameButton);
            name=nameButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = e.getActionCommand();
                    //return fonts
            setFontBoldInput ();

            if (N < 15) {
                N++;

                strNumber = strNumber + name;             // create input number type String
                dNumber = Double.parseDouble(strNumber);  // from String to Double


                if (strNumber.equals("0.") && name.equals("."))     //output in begining
                    textInput.setText(strInput = strInput + strNumber);
                else
                    textInput.setText(strInput = strInput + name);

                                // except divide for 0
                if ((dNumber == 0.0) && (nameSign.equals(" / "))) {
                    strResult = "деление на 0 не возможно";
                    blockedAll(b1, b2, b3, b4, b5, b6, b7, b8, b9, b0,
                            bPlus, bMinus, bDivide, bMultiply, bPercent, bRadical,
                            bResult, bMemoryAdd, bMemoryDel, bMemoryHold);
                } else {

                    dResult = calculateCurrent.calculateInput(strInput);

                    strResult = "=" + printNumber(dResult);
                    unblockedAll(bPercent);       // work  % without mistakes
                }

                if (name.equals(".")) {
                    blockedAll(bPoint);   //в числе не м.б. две точки
                    // разблокировка клавиш при попытке деления на 0
                    unblockedAll(b1, b2, b3, b4, b5, b6, b7, b8, b9, b0,
                            bResult, bMemoryAdd, bMemoryDel, bDel,
                            bPlus, bMinus, bDivide, bMultiply, bPercent, bRadical);
                }
                textRezult.setText(strResult);                 //запись на экран результата
            }
        }
    }

    /**
     * behavior operation Buttons
     */
    class CreateSignButton extends AbstractAction {
        String name;

        CreateSignButton(String nameButton) {
            super(nameButton);
            name = nameButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

                        //return fonts
            setFontBoldInput ();

            strNumber = "0";                      //подготовка для ввода очередного числа
            N = 0;

            unblockedAll(bPoint);       // разрешение на дробные числа
            blockedAll(bPercent);       // work  % without mistakes

            //запись в в окно ввода
            if (!name.equals(" % "))       // delete % in input screen
                if (!name.equals(" = "))    // delete = in input screen

                    if (func == null)           // начало работы, после АС, после =

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
                        else if (name.equals(" √ ")) {

                            switch (strInput.charAt(strInput.length() - 1)) {
                                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.' ->
                                        textInput.setText(strInput = strInput + name); // ввод Number*sqrt (Number)

                                default -> textInput.setText(strInput = name);      // начало ввада с sqrt
                            }
                        } else              // начало ввода с [-+*/] или число [-+*/]
                            textInput.setText(strInput = printNumber(dResult) + name);

                    else // func!=null
                        switch (strInput.substring(strInput.length() - 3)) {
                            case " √ " -> {
                                if (name.equals(" √ ")) {
                                    textInput.setText(strInput = strInput + name);
                                } else
                                    //если два знака подряд + sqrt(*)  ->  на *
                                    switch (strInput.substring(strInput.length() - 6, strInput.length() - 3)) {
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
                    switch (strInput.substring(strInput.length() - 3)) {
                        case " + ", " - ", " * ", " / ", " √ " ->
                                textInput.setText(strInput = strInput.substring(0, strInput.length() - 3));
                    }


            switch (name) {

                case " + " -> {
                    func = Operations::plus;
                    dResultPercent = dResult;
                }
                case " - " -> {
                    func = Operations::minus;
                    dResultPercent = dResult;
                }
                case " * " -> {
                    func = Operations::multiply;
                    dResultPercent = dResult;
                }
                case " / " -> {
                    func = Operations::divide;
                    dResultPercent = dResult;
                }
                case " % " -> {
                    unblockedAll(bPercent);       // work  % without mistakes

                    if (func == null) {
                        dResult = calculateCurrent.calculatePersent(func, nameSign,
                                dResultPercent, dNumber);
                        textInput.setText(printNumber(dResult));
                    } else {
                        dResult = calculateCurrent.calculatePersent(func, nameSign,
                                dResultPercent, dNumber);

                        textInput.setText(strInput = strInputFormerSign + printNumber(
                                calculateCurrent.calculateInput(printNumber(dResult) +
                                        " - " + printNumber(dResultPercent))));
                    }
                    strResult = "=" + printNumber(dResult);
                    textRezult.setText(strResult);

                    func = null;
                    strInput = "   ";            // input number after %
                }
                case " = " -> {
                    setFontBoldResult ();          //change font
                    textRezult.setText(strResult);
                    textInput.setText(strInput);

                    sbLog.append(strInput).append("\n").append(strResult).append("\n");
                    textLog.setText(sbLog.toString());

                    unblockedAll(bPercent);       // work  % without mistakes

                    strNumber = "0";                      // если после = начнется ввод с "."
                    func = null;
                    strInput = "   ";                      // ввод числа после =
                }
            }
            nameSign = name;
            strInputFormerSign = strInput;


        }
    }

    /**
     * behavior  remember and delete Buttons
     */
    class CreateWorkButton extends AbstractAction {
        String name;

        CreateWorkButton(String nameButton) {
            super(nameButton);
            name = nameButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            switch (name) {

                case "M+" -> {
                    memory = dResult;
                    unblockedAll(bMemoryHold);
                }
                case "M-" -> {
                    memory = null;
                    blockedAll(bMemoryHold);
                }
                case "MR" -> {
                    dNumber = memory;

                    switch (strInput.substring(strInput.length() - 1)) {
                                        // before MR was number
                        case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "." -> {
                            boolean isFormerNumber = true;

                            while (isFormerNumber) {
                                strInput = strInput.substring(0, strInput.length() - 1);

                                switch (strInput.substring(strInput.length() - 1)) {
                                    case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "." -> isFormerNumber = true;
                                    default -> isFormerNumber = false;
                                }
                            }
                            textInput.setText(strInput = strInput + printNumber(memory));
                        }
                                            // before MR was sign
                        default -> textInput.setText(strInput = strInput + printNumber(memory));
                    }
                                // except divide for 0
                    if ((dNumber == 0.0) && (nameSign.equals(" / "))) {
                        strResult = "деление на 0 не возможно";
                        blockedAll(b1, b2, b3, b4, b5, b6, b7, b8, b9, b0,
                                bPlus, bMinus, bDivide, bMultiply, bPercent, bRadical,
                                bResult, bMemoryAdd, bMemoryDel, bMemoryHold);
                    } else {
                        dResult = calculateCurrent.calculateInput(strInput);
                        strResult = "=" + printNumber(dResult);
                    }

                    textRezult.setText(strResult);           //запись на экран результата

                    unblockedAll(bPercent);       // work  % without mistakes

                }
                case "AC" -> {
                    unblockedAll(b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bPoint,
                            bMemoryAdd, bMemoryDel, bDel,
                            bPlus, bMinus, bDivide, bMultiply, bPercent, bRadical);

                    if (memory != null)
                        unblockedAll(bMemoryHold);

                    dNumber = 0.0;
                    strNumber = "0";

                    textRezult.setText("0");
                    textInput.setText(strInput = " ");

                    func = null;
                    dResult = 0.0;                // знак после АС
                    strInput = "   ";            //цифра после АС
                    strResult = "0";              // AC затем =, textRez
                    nameSign = " ";               //после sqrt
                }
                case "C" -> {
                    // окно ввовда
                    switch (strInput.charAt(strInput.length() - 1)) {
                        case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.' -> {
                            textInput.setText(strInput = strInput.substring(0, strInput.length() - 1));
                            dResult = calculateCurrent.calculateInput(strInput);
                            if (strNumber.length() > 1)
                                strNumber = strNumber.substring(0, strNumber.length() - 1);
                        }
                        default -> {
                            textInput.setText(strInput = strInput.substring(0, strInput.length() - 3));
                            dResult = calculateCurrent.calculateInput(strInput);
                                            //begining work
                            if (strInput.length()< 3)
                                func=null;
                        }
                    }
                    strResult = "=" + printNumber(dResult);
                    textRezult.setText(strResult);

                    unblockedAll(b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bPoint,
                            bMemoryAdd, bMemoryDel, bMemoryHold, bDel,
                            bPlus, bMinus, bDivide, bMultiply, bPercent, bRadical);
                }
            }
        }
    }

//Service inputing

    /**
     * block keys on panel calculator
     * @param v blocking keys
     */
    private static void blockedAll(JButton... v) {
        for (JButton b : v)
            b.setEnabled(false);
    }

    /**
     * unblock keys on panel calculator
     * @param v unblocking keys
     */
    private static void unblockedAll(JButton... v) {
        for (JButton b : v)
            b.setEnabled(true);
    }

    /**
     * transform  Double number to String,
     * consider that number can be Integer
     * @param d transforming Double number to String
     * @return String form number
     */
    private static String printNumber (Double d) {
        String str =Double.toString(d);
        if (str.substring(str.indexOf('.')+1).equals("0"))
            return   str= Long.toString(d.longValue());
        else return str;
    }


// Inputing from keyBoard

    /**
     * behavior keys inputing  to textPanel
     */
    private class TextPanelInputKeysAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            String str = textInput.getText();

            str = str.replace("+", " + ");
            str = str.replace("-", " - ");
            str = str.replace("/", " / ");
            str = str.replace("*", " * ");

            while (str.contains("  "))
                str = str.replaceAll("  ", " ");

            for (int i = 0; i < str.length(); i++) {
                switch (str.charAt(i)) {
                    case '+', '-', '/', '*' -> {
                        switch (str.charAt(i + 2)) {
                            case '+', '-', '/', '*' -> str = str.substring(0, i) + str.substring(i + 2);
                        }
                    }
                }
            }
                        //Change FONT
            setFontBoldResult();

            textInput.setText(strInput = str);

            dResult = calculateCurrent.calculateInput(strInput);
            strResult = "=" + printNumber(dResult);
            textRezult.setText(strResult);

            sbLog.append(strInput).append("\n").append(strResult).append("\n");
            textLog.setText(sbLog.toString());

            unblockedAll(bPercent);       // work  % without mistakes
            strNumber = "0";                      // если после = начнется ввод с "."
            func = null;
            strInput = "   ";                      // ввод числа после =


                    //focus to visible keyPenel
            focusVisibleKeyPenel ();

        }
    }

    /**
     * inputing Keys to textPanel
     */
    private void textPanelInputKeys() {
        var textPanelInputKeysAction = new TextPanelInputKeysAction();
        textInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "focusKeyPanel");
        textInput.getActionMap().put("focusKeyPanel", textPanelInputKeysAction);

        textInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), "correctInput");
        textInput.getActionMap().put("correctInput", textPanelInputKeysAction);

        ignoreLetter(
                'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm',
                'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M',
                '<', '>', '?', '!', '@', '#', '$', '%', '^', '&', '(', ')', ':', ';', '"', ',', '[', ']', '{', '}', '`', '~',
                'ё', 'й', 'ц', 'у', 'к', 'е', 'н', 'г', 'ш', 'щ', 'з', 'х', 'ъ', 'ф', 'ы', 'в', 'а', 'п', 'р', 'о', 'л', 'д', 'ж', 'э', 'я', 'ч', 'с', 'м', 'и', 'т', 'ь', 'б', 'ю',
                'Ё', 'Й', 'Ц', 'У', 'К', 'Е', 'Н', 'Г', 'Ш', 'Щ', 'З', 'Х', 'Ъ', 'Ф', 'Ы', 'В', 'А', 'П', 'Р', 'О', 'Л', 'Д', 'Ж', 'Э', 'Я', 'Ч', 'С', 'М', 'И', 'Т', 'Ь', 'Б', 'Ю'
        );
    }

    /**
     * ignor keys inputing to textInputPanel
     * @param var ignoring keys
     */
    private void ignoreLetter(char... var) {
        for (char c : var) {
            textInput.getInputMap().put(KeyStroke.getKeyStroke(c), "none");
        }
    }


 //service surface appearance

    /**
     * setings GridBagConstraints
     * @param gridy row button
     * @param gridx column button
     * @param gridwidth width button
     * @param gridheight height button
     * @param ipady inly filling button along vertical
     * @param ipadx inly filling button along horizontal
     */
    private void makeGridBagConstraints(int gridy, int gridx, int gridwidth, int gridheight, int ipady, int ipadx) {
        gbc.gridy = gridy;
        gbc.gridx = gridx;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.ipady = ipady;
        gbc.ipadx = ipadx;
    }

    /**
     * repack frame with setting new size
     */
    private void repack () {
        //height frame
        heightSizeMain = heightSizeText + MySizePanel.HIEGHT_SIZE_KEY.get();
        setPreferredSizePanels();
        frame.pack();
        //focus necessary panel
        focusVisibleKeyPenel ();
    }

    /**
     * set Preferred Size
     * frame and textPanels
     */
    private void setPreferredSizePanels () {
        frame.setPreferredSize(new Dimension(widthSizeText, heightSizeMain));
        scrollinput.setPreferredSize(new Dimension(widthSizeText, MySizePanel.HIEGHT_SIZE_TEXT_INPUT.get()));
        labPanel.setPreferredSize(new Dimension(widthSizeText, MySizePanel.HIEGHT_SIZE_TEXT_RESULT.get()));
        scrollLog.setPreferredSize(new Dimension(widthSizeText,MySizePanel.HIEGHT_SIZE_TEXT_LOG.get()));
    }

    /**
     *show up Result font
     */
    private void setFontBoldResult (){
        textRezult.setFont(MyFonts.FONT_TEXT_INPUT.get());
        StyleConstants.setFontSize(textInputAttributes, MyFontSizes.FRONT_SIZE_TEXT_RESULT.get());
        textInput.setParagraphAttributes(textInputAttributes, true);
    }

    /**
     *show up InputPanel font
     */
    private void setFontBoldInput (){
        textRezult.setFont(MyFonts.FONT_TEXT_RESULT.get());
        StyleConstants.setFontSize(textInputAttributes,MyFontSizes.FRONT_SIZE_TEXT_INPUT.get());
        textInput.setParagraphAttributes(textInputAttributes, true);
    }

    /**
     * focus to visible keyPenel
     */
    private void focusVisibleKeyPenel () {
        for (Component comp : cardPanel.getComponents()) {
            if (comp.isVisible()) {
                comp.requestFocusInWindow();
            }
        }
    }


//PANELs

    /**
     * create CardLayout
     */
    private void makeCard() {

        cardTypeCalc = new CardLayout();            //компоновка
        cardPanel = new JPanel(new CardLayout());   //колода
        cardPanel.setLayout(cardTypeCalc);          //компоновка колоды

        //карты в колоде
        //keyPanelSimple
        makeSimpleCalculate();
        //keyPanelEngineer
        makeEngineerCalculate();
        // keyPanelIT
        makeITCalculate();

        //формирование колоды
        cardPanel.add(keyPanelSimple, "Simple");
        cardPanel.add(keyPanelEngineer, "Engineer");
        cardPanel.add(keyPanelIT, "ITcalc");
    }

    /**
     * create Simple keyPanel
     */
    private void makeSimpleCalculate() {

        // create keyPanel
        keyPanelSimple = new JPanel();
        keyPanelSimple.setBackground(MyColors.COLOR_PANE.get());
        keyPanelSimple.setPreferredSize(new Dimension(MySizePanel.WIDTH_SIZE_SIMPLE.get(), MySizePanel.HIEGHT_SIZE_KEY.get()));

        // GridBagLayout keyPanel
        keyPanelSimple.setLayout(gbag);
        gbc.fill = GridBagConstraints.CENTER;
        gbc.weightx = 100;
        gbc.weighty = 100;

        // line 1
        makeGridBagConstraints(0, 0, 1, 1, 0, 10);
        keyPanelSimple.add(bClear, gbc);

        makeGridBagConstraints(0, 1, 1, 1, 0, 29);
        keyPanelSimple.add(bDel, gbc);

        makeGridBagConstraints(0, 2, 1, 1, 0, 1);
        keyPanelSimple.add(bMemoryHold, gbc);

        makeGridBagConstraints(0, 3, 1, 1, 0, 4);
        keyPanelSimple.add(bMemoryAdd, gbc);

        makeGridBagConstraints(0, 4, 1, 1, 0, 11);
        keyPanelSimple.add(bMemoryDel, gbc);

        //line 2
        makeGridBagConstraints(1, 0, 1, 1, 0, 20);
        keyPanelSimple.add(b7, gbc);

        makeGridBagConstraints(1, 1, 1, 1, 0, 20);
        keyPanelSimple.add(b8, gbc);

        makeGridBagConstraints(1, 2, 1, 1, 0, 20);
        keyPanelSimple.add(b9, gbc);

        makeGridBagConstraints(1, 3, 1, 1, 0, 8);
        keyPanelSimple.add(bDivide, gbc);

        makeGridBagConstraints(1, 4, 1, 1, 0, 0);
        keyPanelSimple.add(bPercent, gbc);

        //line 3
        makeGridBagConstraints(2, 0, 1, 1, 0, 20);
        keyPanelSimple.add(b4, gbc);

        makeGridBagConstraints(2, 1, 1, 1, 0, 20);
        keyPanelSimple.add(b5, gbc);

        makeGridBagConstraints(2, 2, 1, 1, 0, 20);
        keyPanelSimple.add(b6, gbc);

        makeGridBagConstraints(2, 3, 1, 1, 0, 6);
        keyPanelSimple.add(bMultiply, gbc);

        makeGridBagConstraints(2, 4, 1, 1, 0, 8);
        keyPanelSimple.add(bRadical, gbc);

        //line 4
        makeGridBagConstraints(3, 0, 1, 1, 0, 20);
        keyPanelSimple.add(b1, gbc);

        makeGridBagConstraints(3, 1, 1, 1, 0, 20);
        keyPanelSimple.add(b2, gbc);

        makeGridBagConstraints(3, 2, 1, 1, 0, 20);
        keyPanelSimple.add(b3, gbc);

        makeGridBagConstraints(3, 3, 1, 1, 0, 6);
        keyPanelSimple.add(bPlus, gbc);

        makeGridBagConstraints(3, 4, 1, 2, 53, 6);
        keyPanelSimple.add(bResult, gbc);

        //line 5
        makeGridBagConstraints(4, 0, 2, 1, 0, 71);
        keyPanelSimple.add(b0, gbc);

        makeGridBagConstraints(4, 2, 1, 1, 0, 30);
        keyPanelSimple.add(bPoint, gbc);

        makeGridBagConstraints(4, 3, 1, 1, 0, 16);
        keyPanelSimple.add(bMinus, gbc);
    }

    /**
     * create Engineer keyPanel
     */
    private void makeEngineerCalculate() {
        keyPanelEngineer = new JPanel();
        keyPanelEngineer.setBackground(MyColors.COLOR_PANE.get());
        keyPanelEngineer.setPreferredSize(new Dimension(MySizePanel.WIDTH_SIZE_ENGINEER.get(), MySizePanel.HIEGHT_SIZE_KEY.get()));

    }

    /**
     * create IT keyPanel
     */
    private void makeITCalculate() {
        keyPanelIT = new JPanel();
        keyPanelIT.setBackground(MyColors.COLOR_PANE.get());
        keyPanelIT.setPreferredSize(new Dimension(MySizePanel.WIDTH_SIZE_IT.get(), MySizePanel.HIEGHT_SIZE_KEY.get()));

    }

    /**
     * make TextPanel
     */
    private void makeTextPanel() {

        textPanel = new JPanel();
        textPanel.setBackground(MyColors.COLOR_PANE.get());
        textPanel.setLayout(gbag);
        //textPanel.setPreferredSize(new Dimension(WidthSize,HeightSizeText)); автоматически
        borderText = BorderFactory.createLineBorder(Color.BLACK, 2);
        textPanel.setBorder(borderText);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        textInput = new JTextPane();
        textInput.setBackground(MyColors.COLOR_PANE.get());
        textInputAttributes = new SimpleAttributeSet();
        StyleConstants.setAlignment(textInputAttributes, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setFontFamily(textInputAttributes, MyFontNames.FRONT_NAME_TEXT_INPUT.get());
        scrollinput = new JScrollPane(textInput,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //scrollinput.setPreferredSize(new Dimension(widthSizeText,SIZE_TEXT_INPUT));
        scrollinput.setBorder(null);

        textRezult = new JLabel("0");
        labPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        labPanel.setBackground(MyColors.COLOR_BUTTON.get());
        //labPanel.setPreferredSize(new Dimension(widthSizeText, SIZE_TEXT_RESULT));
        labPanel.add(textRezult);

        //show up InputPanel font
        setFontBoldInput();

        textLog = new JTextPane();
        textLog.setBackground(MyColors.COLOR_BUTTON.get());
        var textLogAttributes = new SimpleAttributeSet();
        StyleConstants.setAlignment(textLogAttributes, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setFontFamily(textLogAttributes, MyFontNames.FRONT_NAME_TEXT_LOG.get());
        StyleConstants.setFontSize(textLogAttributes, MyFontSizes.FRONT_SIZE_TEXT_LOG.get());
        StyleConstants.setForeground(textLogAttributes, Color.GRAY);
        textLog.setParagraphAttributes(textLogAttributes, true);
        scrollLog = new JScrollPane(textLog,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //scrollLog.setPreferredSize(new Dimension(widthSizeText,SIZE_TEXT_LOG));
        scrollLog.setBorder(null);

        textPanel.add(scrollLog);
        textPanel.add(scrollinput);
        textPanel.add(labPanel, Component.RIGHT_ALIGNMENT);
    }

//MENU

    /**
     * behavior MenuItem
     */
    class MakeMenuItem extends AbstractAction {
        MakeMenuItem(String name, KeyStroke accel) {
            super(name);
            putValue(ACCELERATOR_KEY, accel);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Обычный" -> {
                    cardTypeCalc.show(cardPanel, "Simple");
                    widthSizeText = MySizePanel.WIDTH_SIZE_SIMPLE.get();
                    repack();
                }
                case "Инженерный" -> {
                    cardTypeCalc.show(cardPanel, "Engineer");
                    widthSizeText = MySizePanel.WIDTH_SIZE_ENGINEER.get();
                    repack();
                }
                case "IT" -> {
                    cardTypeCalc.show(cardPanel, "ITcalc");
                    widthSizeText = MySizePanel.WIDTH_SIZE_IT.get();
                    repack();
                }
                case "Копировать" -> {

                }
                case "Вставить" -> {

                }
                case "Очистить журнал" -> {

                }
                case "Копировать журнал" -> {

                }
                case "Числовые разряды" -> {

                }
                case "Посмотреть справку" -> {

                }
            }

        }
    }

    /**
     * behavior log MenuItem
     */
    class MakeLogMenuItem extends AbstractAction {
        MakeLogMenuItem(String name, KeyStroke accel) {
            super(name);
            putValue(ACCELERATOR_KEY, accel);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Показать журнал" -> jchbLog.setSelected(true);
                case "Скрыть журнал" -> jchbLog.setSelected(false);
            }

            if(jchbLog.isSelected()) {
                jmiClearLog.setEnabled(true);
                jmiCopyLog.setEnabled(true);
                jmiShowLogPopup.setVisible(false);
                jmiHideLogPopup.setVisible(true);
                jmiClearLogPopup.setVisible(true);
                jmiCopyLogPopup.setVisible(true);

                //height textPanel (height keyPanel = const)
                heightSizeText = MySizePanel.HIEGHT_SIZE_TEXT_INPUT.get()
                        + MySizePanel.HIEGHT_SIZE_TEXT_RESULT.get()
                        + MySizePanel.HIEGHT_SIZE_TEXT_LOG.get();
                scrollLog.setVisible(true);
                repack();
            } else{
                jmiClearLog.setEnabled(false);
                jmiCopyLog.setEnabled(false);
                jmiShowLogPopup.setVisible(true);
                jmiHideLogPopup.setVisible(false);
                jmiClearLogPopup.setVisible(false);
                jmiCopyLogPopup.setVisible(false);

                //height textPanel (height keyPanel = const)
                heightSizeText = MySizePanel.HIEGHT_SIZE_TEXT_INPUT.get()
                        + MySizePanel.HIEGHT_SIZE_TEXT_RESULT.get();
                scrollLog.setVisible(false);
                repack();
            }
        }
    }

    /**
     * make View Menu
     */
    private void makeViewMenu() {
        JMenu jmView = new JMenu("Вид");
            jmView.setFont(MyFonts.FONT_MENU.get());

        actionSimple = new MakeMenuItem("Обычный", KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.ALT_DOWN_MASK));
            jmiSimple = new JRadioButtonMenuItem(actionSimple);
            jmiSimple.setFont(MyFonts.FONT_MENU_ITEM.get());
            jmiSimple.setSelected(true);
        jmView.add(jmiSimple);

        actionEngineer = new MakeMenuItem("Инженерный", KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.ALT_DOWN_MASK));
            jmiEngineer = new JRadioButtonMenuItem(actionEngineer);
            jmiEngineer.setFont(MyFonts.FONT_MENU_ITEM.get());
        jmView.add(jmiEngineer);

        actionIT = new MakeMenuItem("IT", KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.ALT_DOWN_MASK));
            jmiIT = new JRadioButtonMenuItem(actionIT);
            jmiIT.setFont(MyFonts.FONT_MENU_ITEM.get());
        jmView.add(jmiIT);
        jmView.addSeparator();

        var bg = new ButtonGroup();
            bg.add(jmiSimple);
            bg.add(jmiEngineer);
            bg.add(jmiIT);


         actionLog = new MakeLogMenuItem("Журнал", KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.ALT_DOWN_MASK)) ;
         jchbLog = new JCheckBoxMenuItem(actionLog);
            jchbLog.setFont(MyFonts.FONT_MENU_ITEM.get());
        jmView.add(jchbLog);

        var jchbGroupDigit = new JCheckBoxMenuItem("Числовые разряды");
            jchbGroupDigit.setFont(MyFonts.FONT_MENU_ITEM.get());
            jchbGroupDigit.setToolTipText("Группировка цифр по разрядам");
        jmView.add(jchbGroupDigit);

        jmb.add(jmView);
    }


    /**
     * make Correct Menu
     */
    private void makeCorrectMenu() {
        JMenu jmCorrect = new JMenu("Правка");
        jmCorrect.setFont(MyFonts.FONT_MENU.get());

        actionCopy = new MakeMenuItem("Копировать", KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        var jmiCopy = new JMenuItem(actionCopy);
            jmiCopy.setFont(MyFonts.FONT_MENU_ITEM.get());
            jmCorrect.add(jmiCopy);

        actionPaste = new MakeMenuItem("Вставить", KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        var jmiPaste = new JMenuItem(actionPaste);
            jmiPaste.setFont(MyFonts.FONT_MENU_ITEM.get());
            jmCorrect.add(jmiPaste);
        jmCorrect.addSeparator();

        var jmiLog = new JMenu("Журнал");
            jmiLog.setFont(MyFonts.FONT_MENU_ITEM.get());
            jmCorrect.add(jmiLog);
        actionClearLog = new MakeMenuItem("Очистить журнал", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.ALT_DOWN_MASK));
        jmiClearLog = new JMenuItem(actionClearLog);
             jmiClearLog.setFont(MyFonts.FONT_MENU_ITEM.get());
             jmiLog.add(jmiClearLog) ;
        actionCopyLog = new MakeMenuItem("Копировать журнал", KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
        jmiCopyLog = new JMenuItem(actionCopyLog);
             jmiCopyLog.setFont(MyFonts.FONT_MENU_ITEM.get());
             jmiLog.add(jmiCopyLog) ;

        jmb.add(jmCorrect);
    }


    /**
     * make Brief Menu
     */
    private void makeBriefMenu() {
        JMenu jmbrief = new JMenu("Справка");
            jmbrief.setFont(MyFonts.FONT_MENU.get());

        actionBrief = new MakeMenuItem("Посмотреть справку", KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
        var jmiBrief = new JMenuItem(actionBrief);
            jmbrief.add(jmiBrief);
            jmiBrief.setFont(MyFonts.FONT_MENU_ITEM.get());
        jmb.add(jmbrief);
    }


    /**
     * make Popup Menu
     */
    private void makePopupMenu() {
        jpu = new JPopupMenu();
        var jmiCopy = new JMenuItem(actionCopy);
        var jmiPaste = new JMenuItem(actionPaste);

        actionShowLogPopup = new MakeLogMenuItem("Показать журнал", KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_DOWN_MASK));
        jmiShowLogPopup = new JMenuItem(actionShowLogPopup);
        actionHideLogPopup = new MakeLogMenuItem("Скрыть журнал", KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.ALT_DOWN_MASK));
        jmiHideLogPopup = new JMenuItem(actionHideLogPopup);
        jmiClearLogPopup = new JMenuItem(actionClearLog);
        jmiCopyLogPopup = new JMenuItem(actionCopyLog);

        jpu.add (jmiCopy);
        jpu.add (jmiPaste);
        jpu.addSeparator();
        jpu.add (jmiShowLogPopup);
        jpu.add (jmiHideLogPopup);
        jpu.add (jmiClearLogPopup);
        jpu.add (jmiCopyLogPopup);
    }

    /**
     * mouseListener for PopupMenu
     * @param compVal list of components with PopupMenu
     */
    private void mouseListenerPopupMenu (JComponent ... compVal){
        for (JComponent comp :compVal) {
            comp.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if(e.isPopupTrigger())
                        jpu.show(e.getComponent(),e.getX(), e.getY());
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if(e.isPopupTrigger())
                        jpu.show(e.getComponent(),e.getX(), e.getY());
                }
            });
        }

    }

}






