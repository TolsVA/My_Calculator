package com.example.my_calculator.style;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.example.my_calculator.R;

public class SelectThemeActivity extends BaseActivity {

    public static final String EXTRA_THEME = "EXTRA_THEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_select_theme );

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        LinearLayout themeContainer = findViewById ( R.id.themes );

        for (Theme theme : Theme.values ()) {
            View view = LayoutInflater.from ( this ).inflate ( R.layout.item_thema, themeContainer, false );

            view.setOnClickListener ( view1 -> {
                Intent data = new Intent ( );
                data.putExtra ( EXTRA_THEME, theme );
                setResult ( Activity.RESULT_OK, data );
                finish ( );
            } );

            TextView themeItemTitle = view.findViewById ( R.id.theme_item );
            themeItemTitle.setText ( theme.getName ( ) );

            ImageView check = view.findViewById ( R.id.check );
            if (theme.equals ( getSavedTheme () )) {
                check.setColorFilter ( themeItemTitle.getCurrentTextColor () );
                check.setVisibility ( View.VISIBLE );
            } else {
                check.setVisibility ( View.INVISIBLE );
            }
            themeContainer.addView ( view );
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId ( ) == android.R.id.home) {
            onBackPressed ( );
            return true;
        }
        return super.onOptionsItemSelected ( item );
    }

    @Override
    public void onBackPressed() {
        Toast.makeText ( this, "Что тут???", Toast.LENGTH_SHORT ).show ( );
        super.onBackPressed();
    }
}