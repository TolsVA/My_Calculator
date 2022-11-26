package com.example.my_calculator.style;

import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import com.example.my_calculator.R;

public enum Theme {
    ONE ( R.style.CalKey1_AppThemeLight, R.string.material_light, "0" ),  //Красная тема
    TWO ( R.style.CalKey, R.string.cal_key_style, "1" ),                  //Светлая тема
    THREE ( R.style.MyCoolStyle, R.string.my_cool_style, "2" ),           //Синяя тема
    FOUR ( R.style.AppThemeDark, R.string.material_dark, "3" );           //Темная тема

    @StyleRes
    private final int theme;

    @StringRes
    private final int name;

    private final String key;

    Theme(int theme, int name, String key) {
        this.theme = theme;
        this.name = name;
        this.key = key;
    }

    public int getTheme() {
        return theme;
    }

    public int getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
