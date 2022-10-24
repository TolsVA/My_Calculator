package com.example.my_calculator.format;

import com.example.my_calculator.model.CalcImpl;
import com.example.my_calculator.ui.CalcFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CalcFormatImpl implements CalcFormat {

    public String result = "";    // Результат

    public Brackets brackets = new Brackets ( );

    private final CalcImpl calc = new CalcImpl ( );


    @Override
    public String[] formatExpression(String history, String equation, String lexeme) {
        switch (lexeme) {
            case "+":
            case "-":
            case "×":
            case "÷":
                if (equation.length ( ) > 0) {
                    if (isEmpty ( equation )) {
                        equation = equation.substring ( 0, equation.length ( ) - 1 );
                        equation += lexeme;
                    } else if (equation.charAt ( equation.length ( ) - 1 ) == '(' ||
                            equation.charAt ( equation.length ( ) - 1 ) == '√') {
                        break;
                    } else {
                        equation += lexeme;
                    }
                }
                break;
            case "(":
                if (equation.length ( ) > 0) {
                    if (isEmpty ( equation )) {
                        equation += lexeme;
                    } else {
                        if (isNumber ( equation ) || equation.charAt ( equation.length ( ) - 1 ) == ')') {
                            equation += "×(";
                        } else {
                            equation += lexeme;
                        }
                    }
                } else {
                    equation += lexeme;
                }
                break;
            case ")":
                if (equation.equals ( "" )) {
                    break;
                }
                int l = 0;
                int r = 0;
                for (int i = 0; i < equation.length ( ); i++) {
                    if (equation.charAt ( i ) == '(') {
                        l++;
                    }
                    if (equation.charAt ( i ) == ')') {
                        r++;
                    }
                }
                if (l > r) {
                    if (isEmpty ( equation )) {
                        equation = equation.substring ( 0, equation.length ( ) - 1 );
                    }
                    if (equation.charAt ( equation.length ( ) - 1 ) == '(' ||
                            equation.charAt ( equation.length ( ) - 1 ) == '√') {
                        break;
                    }
                    equation += lexeme;
                }
                break;
            case "=":
                if (equation.length () > 0 && !result.equals ( "" )) {
                    while (!isNumber ( equation )) {
                        equation = equation.substring ( 0, equation.length ( ) - 1 );
                    }
                    equation = brackets.start (equation);
                    if (history.equals ( "" )) {
                        history = equation + "=" + result;
                    } else {
                        history += "\n" + equation + "=" + result;
                    }
                    if (result.equals ( "♾" )) {
                        result = "";
                    }
                    equation = result;
                }
                break;
            case "del":
                equation = "";
                result = "";
                break;
            case "back":

                if (equation.length ( ) > 0) {
                    equation = equation.substring ( 0, equation.length ( ) - 1 );
                    if (equation.length () == 0) {
                        result = "";
                        break;
                    }
                    if (equation.charAt ( equation.length ( ) - 1 ) > '9' ||
                            equation.charAt ( equation.length ( ) - 1 ) < '0') {
                        for (int i = equation.length ( ) - 1; i >= 0; i--) {
                            if (equation.charAt ( i ) <= '9' && equation.charAt ( i ) >= '0') {
                                result = calc.calculate ( brackets.start ( equation.substring ( 0, i + 1 ) ) );
                                if (!result.equals ( "♾" )) {
                                    result = cut(result);
                                }
                                break;
                            }
                            if (i == 0) {
                                result = "";
                            }
                        }
                    } else {
                        result = calc.calculate ( brackets.start ( equation ));
                        if (!result.equals ( "♾" )) {
                            result = cut(result);
                        }
                    }
                }
                break;
            case ",":
                if (equation.length () > 0) {
                    if (isNumber ( equation )) {
                        equation += lexeme;
                    }
                }
                break;
            case "√":
                if (equation.length () > 0) {
                    if (isNumber ( equation ) || equation.charAt ( equation.length ( ) - 1 ) == ')') {
                        equation += "×" + lexeme;
                    } else {
                        equation += lexeme;
                    }
                } else {
                    equation += lexeme;
                }
                break;
            case "∧":

                break;
            default:
                equation += lexeme;
                result = calc.calculate ( brackets.start ( equation ) );
                if (!result.equals ( "♾" )) {
                    result = cut ( result );
                }
                break;
        }

        return new String[]{history, equation, result};
    }

    private String cut(String result) {
        BigDecimal decimal = new BigDecimal ( result );
        decimal = decimal.setScale ( 4, RoundingMode.HALF_DOWN );
        return new DecimalFormat ( "#.####" ).format ( decimal );
    }

    private boolean isNumber(String equation) {
        return equation.charAt ( equation.length ( ) - 1 ) <= '9' && equation.charAt ( equation.length ( ) - 1 ) >= '0';
    }

    private boolean isEmpty(String equation) {
        return equation.charAt ( equation.length ( ) - 1 ) == '+' ||
                equation.charAt ( equation.length ( ) - 1 ) == '-' ||
                equation.charAt ( equation.length ( ) - 1 ) == '×' ||
                equation.charAt ( equation.length ( ) - 1 ) == '÷' ||
                equation.charAt ( equation.length ( ) - 1 ) == ',';
    }

}
