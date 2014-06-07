// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

import java.sql.*;
import java.text.*;

public class Timestamps {
    public static Timestamp now() {
        return new Timestamp( System.currentTimeMillis() );
    }

    public static String nowToYMDHMS4FileName() {
        return new SimpleDateFormat( "yyyyMMdd-HHmmss" ).format( now() );
    }

    public static String nowToYMDHMS() {
        return new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" ).format( now() );
    }
}
