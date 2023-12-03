import java.awt.*;

 enum MyColors {

    COLOR_PANE (new Color(231, 223, 232)),
    COLOR_BUTTON (new Color(236, 231, 237)),
    COLOR_SIGN(new Color(213, 199, 214)),
    COLOR_BUTTON_MEMORY (new Color(201, 184, 203));

    private Color color;

    private  MyColors (Color color){
        this.color= color;
    }

    public Color get (){
        return color;
    }
}
