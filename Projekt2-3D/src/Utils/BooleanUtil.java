package Utils;

public class BooleanUtil {
    public boolean switchValue(boolean input){
        if (!input) {
            input = true;
        } else {
            input = false;
        }

        return input;
    }
}
