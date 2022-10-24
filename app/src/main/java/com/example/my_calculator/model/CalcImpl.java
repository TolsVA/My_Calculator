package com.example.my_calculator.model;

import java.util.ArrayList;
import java.util.List;

public class CalcImpl {

    private boolean flag = false;

    public String st;
    public Object view;

    public CalcImpl() {
    }

    public String calculate(String equation) {
        List<Lexeme> lexemes = lexAnalyze(equation);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        double d = expr ( lexemeBuffer );
        if (flag) {
            flag = false;
            return "♾";
        } else {
            return String.valueOf ( d );
        }
    }

    public List<Lexeme> lexAnalyze(String expText) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        while (pos < expText.length()) {
            char c = expText.charAt(pos);
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.OP_PLUS, c));
                    pos++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.OP_MINUS, c));
                    pos++;
                    continue;
                case '×':
                case '*':
                    lexemes.add(new Lexeme(LexemeType.OP_MUL, c));
                    pos++;
                    continue;
                case '÷':
                case '/':
                    lexemes.add(new Lexeme(LexemeType.OP_DIV, c));
                    pos++;
                    continue;
                case '√':
                    lexemes.add(new Lexeme(LexemeType.ROOT, c));
                    pos++;
                    continue;
                case '∧':
                    lexemes.add(new Lexeme(LexemeType.DEGREE, c));
                    pos++;
                    continue;
                default:
                    if (c <= '9' && c >= '0' || c == ',' || c == '.') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            if (c == ',') {
                                sb.append('.');
                            } else {
                                sb.append(c);
                            }
                            pos++;
                            if (pos >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(pos);
                        } while (c <= '9' && c >= '0' || c == ',');
                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                    } else {
                        if (c != ' ') {
                            throw new RuntimeException("Unexpected character: " + c);
                        }
                        pos++;
                    }
            }
        }
        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }

    public Double  expr(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        if (lexeme.type == LexemeType.EOF) {
            return (double) 0;
        } else {
            lexemes.back();
            return plusMinus(lexemes);
        }
    }

    public Double plusMinus(LexemeBuffer lexemes) {
        double value = multiDiv(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_PLUS:
                    value += multiDiv(lexemes);
                    break;
                case OP_MINUS:
                    value -= multiDiv(lexemes);
                    break;
                default:
                    lexemes.back();
                    return value;
            }
        }
    }

    public Double multiDiv(LexemeBuffer lexemes) {
        double value = factor(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_MUL:
                    value *= factor(lexemes);
                    break;
                case OP_DIV:
                    double vel = factor(lexemes);
                    if (vel != 0) {
                        flag = false;
                        value /= vel;
                    } else {
                        flag = true;
                    }
                    break;
                default:
                    lexemes.back();
                    return value;
            }
        }
    }

    public Double factor(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        Double value;
        switch (lexeme.type) {
            case NUMBER:
                return Double.valueOf(lexeme.value);
            case LEFT_BRACKET:
                value = expr(lexemes);
                lexemes.next();
                return value;
            case ROOT:
                value = Math.sqrt(factor (lexemes));
                return value;
            default:
                throw new RuntimeException("Unexpected token: " + lexeme.value
                        + " at position: " + lexemes.getPos());
        }
    }
}