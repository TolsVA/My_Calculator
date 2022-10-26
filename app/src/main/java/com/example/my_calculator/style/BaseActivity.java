package com.example.my_calculator.style;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.my_calculator.R;

public class BaseActivity extends AppCompatActivity {

    // Имя настроек
    private static final String NAME_SHARED_PREFERENCE = "LOGIN";

    // Имя параметра в настройках
    private static final String SAVED_STYLE = "Saved_Style";

    protected static final int CAL_KEY_CODE_STYLE = 0;
    protected static final int MY_COOL_CODE_STYLE = 1;
    protected static final int APP_THEME_LIGHT_CODE_STYLE = 2;
    protected static final int APP_THEME_DARK_CODE_STYLE  = 3;

    protected static String nameStyle = "nameStyle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Устанавливать тему надо только до установки макета активити
        setTheme(getAppTheme(R.style.CalKey));

        if (nameStyle.equals("nameStyle")) {
            setTitle(getString(R.string.app_name));
        } else {
            setTitle(nameStyle);
        }

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    //Сохранение данных
    @Override
    public void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putString(SAVED_STYLE, nameStyle);
    }

    // Восстановление данных
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
        nameStyle = instanceState.getString(SAVED_STYLE);
    }

    private int getAppTheme(int codeStyle) {
        return codeStyleToStyleId(getCodeStyle(codeStyle));
    }

    // Чтение настроек, параметр «тема»
    protected int getCodeStyle(int codeStyle) {
        // Работаем через специальный класс сохранения и чтения настроек
        SharedPreferences sharedPref = getSharedPreferences(NAME_SHARED_PREFERENCE, MODE_PRIVATE);
        //Прочитать тему, если настройка не найдена - взять по умолчанию
        return sharedPref.getInt(SAVED_STYLE, codeStyle);
    }

    // Сохранение настроек
    protected void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NAME_SHARED_PREFERENCE, MODE_PRIVATE);
        // Настройки сохраняются посредством специального класса editor.
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(SAVED_STYLE, codeStyle);
        editor.apply();
    }

    private int codeStyleToStyleId(int codeStyle){
        switch(codeStyle){
            case CAL_KEY_CODE_STYLE:
                return R.style.CalKey;
            case APP_THEME_LIGHT_CODE_STYLE:
                return R.style.CalKey1_AppThemeLight;
            case APP_THEME_DARK_CODE_STYLE:
                return R.style.AppThemeDark;
            default:
                return R.style.MyCoolStyle;
        }
    }
}
