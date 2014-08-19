package org.litesoft.commonfoundation.indent;

public interface IndentableWriter {
    static final String DEFAULT_INDENT_WITH = "    ";

    String getDefaultIndentWith();

    void indent();

    void indent( String pPrefix );

    void outdent();

    void print( Object pToPrint0, Object... pToPrintN );

    void printLn( Object... pToPrint );

    void close();

    int currentLineOffset();

    IndentableWriter applyToAndClose(Indentable pIndentable);

    static final IndentableWriter NULL = new IndentableWriter() {

        @Override
        public String getDefaultIndentWith() {
            return "";
        }

        @Override
        public void indent() {
            // Do Nothing
        }

        @Override
        public void indent( String pIndentWith ) {
            // Do Nothing
        }

        @Override
        public void outdent() {
            // Do Nothing
        }

        @Override
        public void print( Object pToPrint0, Object... pToPrintN ) {
            // Do Nothing
        }

        @Override
        public void printLn( Object... pToPrint ) {
            // Do Nothing
        }

        @Override
        public void close() {
            // Do Nothing
        }

        @Override
        public int currentLineOffset() {
            return 0;
        }

        @Override
        public IndentableWriter applyToAndClose( Indentable pIndentable ) {
            return this;
        }
    };
}
