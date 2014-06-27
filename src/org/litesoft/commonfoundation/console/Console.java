package org.litesoft.commonfoundation.console;

public interface Console {
    void print( String pText );

    void println( String pLine );

    void close();

    static final Console NULL = new Console() {
        @Override
        public void print( String pText ) {
            // Do Nothing
        }

        @Override
        public void println( String pLine ) {
            // Do Nothing
        }

        @Override
        public void close() {
            // Do Nothing
        }
    };
}
