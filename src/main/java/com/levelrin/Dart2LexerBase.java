package com.levelrin;

import org.antlr.v4.runtime.*;

/**
 * I got this code from https://github.com/antlr/grammars-v4/blob/master/dart2/Java/Dart2LexerBase.java.
 */
public abstract class Dart2LexerBase extends Lexer {

    protected Dart2LexerBase(CharStream input) {
        super(input);
    }

    protected boolean CheckNotOpenBrace() {
        return _input.LA(1) != '{';
    }

}
