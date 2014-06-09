package org.litesoft.commonfoundation.indent;

public interface IndentableWriter {
    void indent();

    void outdent();

    void print( Object pToPrint0, Object... pToPrintN );

    void printLn( Object... pToPrint );

    void close();

    int currentLineOffset();
}
