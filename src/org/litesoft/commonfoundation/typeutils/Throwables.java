package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.exceptions.*;

public class Throwables
{
    public static String printStackTraceToString( Throwable pThrowable )
    {
        ExceptionStackTracePrintStream ps = new ExceptionStackTracePrintStream();
        pThrowable.printStackTrace( ps );
        return ps.toString();
    }
}
