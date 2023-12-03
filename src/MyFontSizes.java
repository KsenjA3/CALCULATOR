import java.awt.*;

enum MyFontSizes {

    FRONT_SIZE_TEXT_LOG (18),
    FRONT_SIZE_TEXT_INPUT (24),
    FRONT_SIZE_TEXT_RESULT (20);


    private int fontSize;

    private  MyFontSizes (int fontSize){
        this.fontSize= fontSize;
    }

    public int get (){
        return fontSize;
    }
}
