package com.example.my_calculator.style;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    public SharedPreferences pref;
    public final  String TABLE = "TABLE";
    public static final String SAVED_STYLE = "SAVED_STYLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        pref = getSharedPreferences ( TABLE, MODE_PRIVATE );
        setTheme ( getSavedTheme ( ).getTheme ( ) );
    }

    public void saveTheme(Theme theme) {
        pref.edit ( )
                .putString ( SAVED_STYLE, theme.getKey ( ) )
                .apply ( );
    }

    public Theme getSavedTheme() {
        String key = String.valueOf ( pref.getString ( SAVED_STYLE, Theme.ONE.getKey ( ) ) );
        for (Theme theme : getAll ( )) {
            if (key.equals ( theme.getKey ( ) )) {
                return theme;
            }
        }
        return Theme.ONE;
    }

    public List<Theme> getAll() {
        return Arrays.asList(Theme.values());
    }
}