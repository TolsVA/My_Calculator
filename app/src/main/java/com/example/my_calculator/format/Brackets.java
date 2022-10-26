package com.example.my_calculator.format;

public class Brackets {
    public String start(String expText) {
        int l = 0;
        int r = 0;
        for (int i = 0; i < expText.length ( ); i++) {
            if (expText.charAt ( i ) == '(') {
                l++;
            }
            if (expText.charAt ( i ) == ')') {
                r++;
            }
        }
        if (l > r) {
            StringBuilder expTextBuilder = new StringBuilder ( expText );
            for (int i = 0; i < l - r; i++) {
                expTextBuilder.append ( ')' );
            }
            expText = expTextBuilder.toString ( );
        }
        while (expText.charAt ( 0 ) == '(' && expText.charAt ( expText.length ( ) - 1 ) == ')') {
            String st = expText.substring ( 1, expText.length ( ) - 1 );
            int in = 0;
            for (int i = 0; i < st.length ( ); i++) {
                if (st.charAt ( i ) == '(') {
                    in++;
                } else if (st.charAt ( i ) == ')') {
                    in--;
                }
                if (in < 0) {
                    return expText;
                }
            }
            expText = expText.substring ( 1, expText.length ( ) - 1 );
        }
        return expText;
    }
}
