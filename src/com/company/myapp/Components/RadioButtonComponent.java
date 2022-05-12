package com.company.myapp.Components;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.RadioButton;
import com.codename1.ui.plaf.Style;

public class RadioButtonComponent {
    public RadioButton getRadioButtonComponent(String text){
        Style radioStyle = new Style();
        RadioButton radioButton=new RadioButton(text);
        radioStyle.setBgColor(ColorUtil.rgb(104, 222, 218));
        radioButton.setUnselectedStyle(radioStyle);

        return radioButton;

    }
}
