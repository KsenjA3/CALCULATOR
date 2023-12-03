import java.awt.*;

enum MyFontNames {
     FRONT_NAME_TEXT_INPUT ("Arial"),
     FRONT_NAME_TEXT_LOG ("Arial") ;

    private String fontName;

    private  MyFontNames (String fontName){
        this.fontName= fontName;
    }

    public String get (){
        return fontName;
    }

}
