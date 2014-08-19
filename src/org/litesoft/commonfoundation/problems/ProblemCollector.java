// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.problems;

import org.litesoft.commonfoundation.annotations.*;
import org.litesoft.commonfoundation.indent.*;
import org.litesoft.commonfoundation.typeutils.*;

import java.io.*;
import java.util.*;

public class ProblemCollector implements Serializable, Indentable {
    private static final long serialVersionUID = 1L;

    private /* final */ List<Problem> mProblems = Lists.newArrayList();

    public synchronized boolean isEmpty() {
        return mProblems.isEmpty();
    }

    public boolean hasProblems() {
        return !isEmpty();
    }

    public synchronized Problem[] problems() {
        return mProblems.toArray( new Problem[mProblems.size()] );
    }

    public synchronized ProblemCollector add( Problem... pProblems ) {
        if ( pProblems != null ) {
            for ( Problem zProblem : pProblems ) {
                if ( zProblem != null ) {
                    mProblems.add( zProblem );
                }
            }
        }
        return this;
    }

    @Override
    public IndentableWriter appendTo( @NotNull IndentableWriter pWriter ) {
        Problem[] zProblems = problems();
        pWriter.printLn( "Problems (", zProblems.length, "):" );
        pWriter.indent();
        for ( Problem zProblem : zProblems ) {
            zProblem.appendTo( pWriter );
        }
        pWriter.outdent();
        return pWriter;
    }

    @Override
    public String toString() {
        return isEmpty() ? "No Problems" : StringIndentableWriter.formatWith( this );
    }
}
