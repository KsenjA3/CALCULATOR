import java.awt.*;

enum MySizePanel {
    HIEGHT_SIZE_TEXT_RESULT (28),
    HIEGHT_SIZE_TEXT_INPUT (103),
    HIEGHT_SIZE_TEXT_LOG (192),

    HIEGHT_SIZE_KEY (260),

    WIDTH_SIZE_SIMPLE (260 + 20),
    WIDTH_SIZE_ENGINEER (600),
    WIDTH_SIZE_IT (350);


    private int size;

    private  MySizePanel (int size){
        this.size= size;
    }

    public int get (){
        return size;
    }
}
