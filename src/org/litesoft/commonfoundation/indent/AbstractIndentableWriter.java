package org.litesoft.commonfoundation.indent;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.typeutils.*;

import java.util.*;

public abstract class AbstractIndentableWriter implements IndentableWriter {
    private final String mDefaultIndentWith;
    private final List<String> mIndents = Lists.newArrayList();
    private int mCurrentLineLength = 0;

    protected AbstractIndentableWriter( String pDefaultIndentWith ) {
        mDefaultIndentWith = Confirm.isNotNull( "DefaultIndentWith", pDefaultIndentWith );
    }

    @Override
    public String getDefaultIndentWith() {
        return mDefaultIndentWith;
    }

    @Override
    public void indent() {
        indent( mDefaultIndentWith );
    }

    @Override
    public void indent( String pIndentWith ) {
        mIndents.add( Confirm.isNotNull( "IndentWith", pIndentWith ) );
    }

    @Override
    public void outdent() {
        int zLastEntry = mIndents.size() - 1;
        if ( 0 <= zLastEntry ) {
            mIndents.remove( zLastEntry );
        }
    }

    @Override
    public void close() {
    }

    @Override
    public final int currentLineOffset() {
        return mCurrentLineLength;
    }

    @Override
    public void print( Object pToPrint0, Object... pToPrintN ) {
        add( pToPrint0 );
        addArray( pToPrintN );
    }

    @Override
    public void printLn( Object... pToPrint ) {
        addArray( pToPrint );
        addNewLine();
    }

    @Override
    public IndentableWriter applyToAndClose( Indentable pIndentable ) {
        if ( pIndentable != null ) {
            pIndentable.appendTo( this );
        }
        close();
        return this;
    }

    private void addArray( Object[] pToAdd ) {
        if ( (pToAdd != null) && (pToAdd.length > 0) ) {
            add( pToAdd[0] );
            for ( int i = 1; i < pToAdd.length; i++ ) {
                addWithCheck( pToAdd[i] );
            }
        }
    }

    private void add( Object pToAdd ) {
        if ( currentLineOffset() == 0 ) {
            for ( String zIndent : mIndents ) {
                addWithCheck( zIndent );
            }
        }
        addWithCheck( pToAdd );
    }

    private void addNewLine() {
        newLine();
        mCurrentLineLength = 0;
    }

    abstract protected void newLine();

    private void addChar( char c ) {
        addIndentIgnorant( c );
        mCurrentLineLength++;
    }

    abstract protected void addIndentIgnorant( char pToAdd );

    private void addWithCheck( Object pToAdd ) {
        String[] zLines = Strings.toLines( ConstrainTo.firstNonNull( (pToAdd != null) ? pToAdd.toString() : null, "null" ) );
        addWithCheckNonNull( zLines[0] );
        for ( int i = 1; i < zLines.length; i++ ) {
            addNewLine();
            addWithCheckNonNull( zLines[i] );
        }
    }

    private void addWithCheckNonNull( String pText ) {
        for ( int i = 0; i < pText.length(); i++ ) {
            char c = pText.charAt( i );
            if ( ' ' <= c ) {
                addChar( c );
            } else {
                handleAsciiCtrl( c );
            }
        }
    }

    private void handleAsciiCtrl( char c ) {
        if ( c != 9 ) { // Not a Tab
            addWithCheckNonNull( "-<" + Characters.cvtCharForDisplay( c ) + ">-" );
            return;
        }
        do {
            addChar( ' ' );
        } while ( (mCurrentLineLength & 7) != 0 );
    }
}
