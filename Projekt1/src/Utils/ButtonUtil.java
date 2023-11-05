package Utils;

import javax.swing.*;

public class ButtonUtil {
    private int allButtonHeight;
    public ButtonUtil(){
        this.allButtonHeight = 0;
    }
    public JButton createNewButton(String name, int width, int gapSize) {
        JButton button = new JButton(name);
        button.setBounds(width - 125, allButtonHeight + gapSize, 100, 30);
        allButtonHeight = allButtonHeight + gapSize + 30;
        return button;
    }
}
