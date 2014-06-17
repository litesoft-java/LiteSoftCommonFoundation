// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.exceptions.*;

import java.util.*;

public class Throwables {
    public static final ExpectedExceptions NONE_EXPECTED = new ExpectedExceptions() {
        @Override
        public boolean isExpected( String pThrowableClassName ) {
            return false;
        }

        @Override
        public String convertExpected( Throwable pThrowable ) {
            return null;
        }
    };

    public static String printStackTraceToString( Throwable pThrowable ) {
        ExceptionStackTracePrintStream ps = new ExceptionStackTracePrintStream();
        pThrowable.printStackTrace( ps );
        return ps.toString();
    }

    public static RuntimeException asRuntimeException( Throwable t ) {
        return t instanceof RuntimeException ? (RuntimeException) t : new RuntimeException( t );
    }

    public static Expected expectedOf( Class<? extends Throwable> pExpectedThrowableClass ) {
        return new Expected( pExpectedThrowableClass );
    }

    public static class Expected implements ExpectedExceptions {
        private static final String OBJECT_SIMPLE_CLASS_NAME = ClassName.simple( Object.class );

        private final Set<String> mExpectedThrowableSimpleClassNames = Sets.newHashSet();

        private Expected( Class<? extends Throwable> pExpectedThrowableClass ) {
            or( pExpectedThrowableClass );
        }

        public Expected or( Class<? extends Throwable> pExpectedThrowableClass ) {
            mExpectedThrowableSimpleClassNames.add(
                    ClassName.simple( Confirm.isNotNull( "ExpectedThrowableClass", pExpectedThrowableClass ) ) );
            return this;
        }

        /**
         * Simple shallow check of the inner Set.
         * <p/>
         * This is normally only used with the result of convertExpected(Throwable);
         */
        public boolean isExpected( String pThrowableSimpleClassName ) {
            return mExpectedThrowableSimpleClassNames.contains( pThrowableSimpleClassName );
        }

        /**
         * Check for the throwable to be in the Expected (both Supers and Causes).
         *
         * @return null if not found, otherwise the name of the class that matched
         */
        public String convertExpected( Throwable throwable ) {
            if ( (throwable != null) && !mExpectedThrowableSimpleClassNames.isEmpty() ) {
                String zSimpleClassName = ClassName.simple( throwable );
                if ( isExpected( zSimpleClassName ) ) { // check
                    return zSimpleClassName; // happy case
                }

                if ( null != (zSimpleClassName = checkSupers( throwable.getClass().getSuperclass() )) ) {
                    return zSimpleClassName;
                }
                // Now look at each Cause to check for one of the Expected(s).
                for ( ThrowableLoopManager zLoopManager = new ThrowableLoopManager( throwable );
                      null != (throwable = throwable.getCause()); zLoopManager.add( throwable ) ) {
                    if ( zLoopManager.alreadySeen( throwable ) ) {
                        break;
                    }
                    if ( null != (zSimpleClassName = checkSupers( throwable.getClass() )) ) {
                        return zSimpleClassName;
                    }
                }
            }
            return null;
        }

        private String checkSupers( Class<?> pClass ) {
            for ( String zSimpleClassName; pClass != null; pClass = pClass.getSuperclass() ) {
                if ( OBJECT_SIMPLE_CLASS_NAME.equals( zSimpleClassName = ClassName.simple( pClass ) ) ) {
                    break;
                }
                if ( isExpected( zSimpleClassName ) ) {
                    return zSimpleClassName;
                }
            }
            return null;
        }

        /**
         * This class is to handle the case when an Exception's cause is itself (or some other cyclical reference).
         */
        private static class ThrowableLoopManager {
            private final Map<Throwable, Throwable> mLoopLimit = new IdentityHashMap<Throwable, Throwable>();

            private ThrowableLoopManager( Throwable pThrowable ) {
                add( pThrowable );
            }

            @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
            public void add( Throwable pThrowable ) {
                mLoopLimit.put( pThrowable, pThrowable );
            }

            public boolean alreadySeen( Throwable pThrowable ) {
                return mLoopLimit.containsKey( pThrowable );
            }
        }
    }
}
