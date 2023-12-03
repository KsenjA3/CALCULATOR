import java.awt.*;

enum MyFonts {

    FONT_BUTTON (new Font("Franklin Gothic Medium", Font.PLAIN, 30)),
    FONT_BUTTON_MEMORY (new Font("Cambria", Font.PLAIN, 30)),
    FONT_TEXT_INPUT(new Font("Arial", Font.PLAIN, MyFontSizes.FRONT_SIZE_TEXT_INPUT.get())),
    FONT_TEXT_RESULT(new Font("Arial", Font.PLAIN, MyFontSizes.FRONT_SIZE_TEXT_RESULT.get())),
    FONT_TEXT_LOG(new Font("Arial", Font.PLAIN, 18)),
    FONT_MENU (new Font("Arial", Font.PLAIN, 15)),
    FONT_MENU_ITEM (new Font("Arial", Font.PLAIN, 13));


    private Font font;

    private  MyFonts (Font font){
        this.font= font;
    }

    public Font get (){
        return font;
    }

}
