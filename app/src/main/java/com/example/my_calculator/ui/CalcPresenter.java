package com.example.my_calculator.ui;

public class CalcPresenter {

    private final CalcView view;
    private final CalcFormat format;


    public CalcPresenter(CalcView view, CalcFormat format) {
        this.view = view;
        this.format = format;
    }

    public void onDigitPressed(String history, String equation, String lexeme) {
        view.showResult ( format.formatExpression ( history, equation, lexeme ) );
    }
}
