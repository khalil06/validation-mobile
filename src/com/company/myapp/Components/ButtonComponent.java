package com.company.myapp.Components;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.plaf.Style;

public class ButtonComponent {
    public com.codename1.ui.Button getButton(String buttonText){
        com.codename1.ui.Button btn = new com.codename1.ui.Button(buttonText);
/*        Style btnStyle= new Style();
        btnStyle.setBgColor(ColorUtil.rgb(68, 184, 149));
        btn.getUnselectedStyle().setBgColor(ColorUtil.rgb(104, 222, 218),true);
        btn.setUnselectedStyle(btnStyle);
        btn.getUnselectedStyle().setAlignment(Component.CENTER);
        btn.getUnselectedStyle().setMargin(100, 0, 200, 200);
        btn.getUnselectedStyle().setPadding(5, 5, 1, 1);
        btn.getUnselectedStyle().setFgColor(ColorUtil.WHITE);*/
        return btn;
    }
}
