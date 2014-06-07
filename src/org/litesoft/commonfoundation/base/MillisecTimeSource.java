// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.base;

public class MillisecTimeSource {
    public static final MillisecTimeSource INSTANCE = new MillisecTimeSource();

    protected MillisecTimeSource() {
    }

    public long now() {
        return System.currentTimeMillis();
    }

    public static MillisecTimeSource deNull( MillisecTimeSource pMillisecTimeSource ) {
        return (pMillisecTimeSource != null) ? pMillisecTimeSource : INSTANCE;
    }
}
