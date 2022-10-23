package com.example.my_calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.my_calculator.format.CalcFormatImpl;
import com.example.my_calculator.ui.CalcPresenter;
import com.example.my_calculator.ui.CalcView;

import java.util.HashMap;

public class CalculatorActivity extends AppCompatActivity implements CalcView {

    public SharedPreferences pref;

    private CalcPresenter presenter;

    public String history = "";   // История
    public String equation = "";  // Уравнение
    public String result = "";    // Результат

    private final static String KEY_HISTORY = "KEY_HISTORY";
    private final static String KEY_EQUATION = "KEY_EQUATION";
    private final static String KEY_RESULT = "KEY_RESULT";

    private TextView historyView, equationView, resultView;

    HashMap<Integer, String> lexeme = new HashMap<> ( );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_calculator );

        pref = getSharedPreferences ( "TABLE", MODE_PRIVATE );
        history = pref.getString ( KEY_HISTORY, "" );

        presenter = new CalcPresenter ( this, new CalcFormatImpl ( ) );

        initView ( );

        setTextCounter ( historyView, history );

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState ( outState );

        outState.putString ( KEY_EQUATION, equation );
        outState.putString ( KEY_RESULT, result );

        SharedPreferences.Editor editor = pref.edit ( );
        editor.putString ( KEY_HISTORY, history );
        editor.apply ( );
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState ( savedInstanceState );

        equation = savedInstanceState.getString ( KEY_EQUATION );
        result = savedInstanceState.getString (KEY_RESULT);

        setTextCounter ( equationView, equation );
        setTextCounter(resultView, result);
    }

    private void initView() {

        historyView = findViewById ( R.id.history );
        equationView = findViewById ( R.id.equation );
        resultView = findViewById ( R.id.result );

        lexeme.put ( R.id.key_0, "0" );
        lexeme.put ( R.id.key_1, "1" );
        lexeme.put ( R.id.key_2, "2" );
        lexeme.put ( R.id.key_3, "3" );
        lexeme.put ( R.id.key_4, "4" );
        lexeme.put ( R.id.key_5, "5" );
        lexeme.put ( R.id.key_6, "6" );
        lexeme.put ( R.id.key_7, "7" );
        lexeme.put ( R.id.key_8, "8" );
        lexeme.put ( R.id.key_9, "9" );

        lexeme.put ( R.id.key_plus, "+" );
        lexeme.put ( R.id.key_minus, "-" );
        lexeme.put ( R.id.key_multi, "×" );
        lexeme.put ( R.id.key_div, "÷" );
        lexeme.put ( R.id.key_equals, "=" );
        lexeme.put ( R.id.key_dot, "," );
        lexeme.put ( R.id.key_root, "√" );

        lexeme.put ( R.id.key_left_bracket, "(" );
        lexeme.put ( R.id.key_right_bracket, ")" );

        lexeme.put ( R.id.key_del, "del" );
        lexeme.put ( R.id.key_back, "back" );

        findViewById ( R.id.key_0 ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_1 ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_2 ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_3 ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_4 ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_5 ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_6 ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_7 ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_8 ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_9 ).setOnClickListener ( lexemeClickListener );

        findViewById ( R.id.key_plus ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_minus ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_multi ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_div ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_dot ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_equals ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_dot ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_root ).setOnClickListener ( lexemeClickListener );

        findViewById ( R.id.key_left_bracket ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_right_bracket ).setOnClickListener ( lexemeClickListener );

        findViewById ( R.id.key_del ).setOnClickListener ( lexemeClickListener );
        findViewById ( R.id.key_back ).setOnClickListener ( lexemeClickListener );
    }

    View.OnClickListener lexemeClickListener = new View.OnClickListener ( ) {
        @Override
        public void onClick(View view) {
            presenter.onDigitPressed ( history, equation, lexeme.get ( view.getId ( ) )  );
        }
    };

    private void scrollText() {
        NestedScrollView scrollView = findViewById ( R.id.scroll_indicator_down );
        scrollView.fullScroll ( View.FOCUS_DOWN );
    }

    @Override
    public void showResult(String[] data) {
        history = data[0];
        equation = data[1];
        if (!data[2].equals ( "" )) {
            result = "=" + data[2];
        } else {
            result = "";
        }
        setTextCounter ( historyView, history );
        setTextCounter ( equationView, equation );
        setTextCounter ( resultView, result );
        scrollText ();
    }

    private void setTextCounter(TextView textView, String text) {
        textView.setText ( text );
    }
}