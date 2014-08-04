// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.problems;

import org.litesoft.commonfoundation.indent.*;
import org.litesoft.commonfoundation.typeutils.*;

import java.io.*;
import java.util.*;

public class ProblemCollector implements Serializable {
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
    public String toString() {
        if ( isEmpty() ) {
            return "No Problems";
        }
        StringIndentableWriter zWriter = new StringIndentableWriter( "    " );
        Problem[] zProblems = problems();
        zWriter.printLn( "Problems (", zProblems.length, "):" );
        zWriter.indent();
        for ( Problem zProblem : zProblems ) {
            zProblem.append( zWriter );
        }
        zWriter.outdent();
        return zWriter.toString();
    }
}
