package org.litesoft.commonfoundation.csv;

import org.litesoft.commonfoundation.annotations.*;
import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.issue.*;
import org.litesoft.commonfoundation.iterators.*;
import org.litesoft.commonfoundation.typeutils.*;

import java.util.*;

public class TitledCsvParser extends Iterators.AbstractReadOnly<Row> {

    public static Builder with( @NotNull Columns pColumns ) {
        Integers.assertPositive( "Columns", pColumns.getMinimumTitleCells() );
        return new Builder( pColumns );
    }

    public static class Builder {
        private final Columns mColumns;

        private Builder( Columns pColumns ) {
            mColumns = pColumns;
        }

        public Builder2 from( @NotNull Source pSource, @NotNull DescriptiveIterator<String> pLines ) {
            return new Builder2( this,
                                 Confirm.isNotNull( "Source", pSource ),
                                 Confirm.isNotNull( "Lines", pLines ) );
        }
    }

    public static class Builder2 {
        private final Columns mColumns;
        private final Source mSource;
        private final DescriptiveIterator<String> mLines;

        private Builder2( Builder pBuilder, Source pSource, DescriptiveIterator<String> pLines ) {
            mColumns = pBuilder.mColumns;
            mSource = pSource;
            mLines = pLines;
        }

        /**
         * @return null if the Column row was not the first non-empty row (reported as an Error into the IssueTracker)
         */
        public TitledCsvParser validateColumnsRowExistsAndBuild() {
            CsvIterator zLines = new CsvIterator( mLines );
            for ( Row zRow; zLines.hasNext(); ) {
                if ( null == (zRow = Row.build( mSource, zLines )) ) {
                    return null;
                }
                if ( !zRow.isEmptyRow() ) {
                    if ( mColumns.getMinimumTitleCells() <= zRow.getCellCount() ) {
                        if ( mColumns.validateColumnTitles( zRow ) ) {
                            return new TitledCsvParser( mSource, zLines );
                        }
                    }
                    return zRow.error( "NotTitle" );
                }
            }
            mSource.addError( Issue.of( CsvIterator.CSV, "NoRows" ) );
            return null;
        }
    }

    private final Source mSource;
    private final CsvIterator mLines;
    private Row mNext;

    private TitledCsvParser( Source pSource, CsvIterator pLines ) {
        mSource = pSource;
        mLines = pLines;
        mNext = populateNext();
    }

    private Row populateNext() {
        for ( Row zRow; mLines.hasNext(); ) {
            if ( null == (zRow = Row.build( mSource, mLines )) ) {
                return null;
            }
            if ( !zRow.isEmptyRow() ) {
                return zRow;
            }
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        return (mNext != null);
    }

    @Override
    public Row next() {
        if ( hasNext() ) {
            Row zNext = mNext;
            mNext = populateNext();
            return zNext;
        }
        throw new NoSuchElementException();
    }

//    @SuppressWarnings("UnusedDeclaration")
//    abstract public Collector loadFile( String pInputFileCSV );
//
//    protected Collector load( Collector pCollector, String pInputFileCSV ) {
//        return load( pCollector, pInputFileCSV, 1, FileUtils.loadTextFile( new File( pInputFileCSV ) ) ); // TODO: MutationFileData!!!!
//    }
//
//    protected Collector load( Collector pCollector, String pLineSource, int pLineIndexAdjustment, String[] pInputLines ) {
//        CsvIterator zIterator = new CsvIterator(
//                new DescriptiveArrayIterator<String>( pLineSource + " line: ", pLineIndexAdjustment, pInputLines ) );
//        if ( !zIterator.hasNext() ) {
//            throw new IllegalStateException( "No 1st line (Column Titles) in: " + pLineSource );
//        }
//        mColumnsToValidate.validateColumnTitles( pLineSource, zIterator.next() );
//        while ( zIterator.hasNext() ) {
//            String zSource = zIterator.toString(); // MUST be called before .next()!
//            Entry zEntry = populate( zSource, zIterator.next() );
//            pCollector.add( zEntry );
//        }
//        return pCollector;
//    }
//
//    protected abstract Entry populate( String pSource, String[] pFields );
//
//    protected String checkMinColumns( String[] pFields ) {
//        return (pFields.length < mColumnsToValidate.length()) ? "Too Few Columns, Need At Least: " + mColumnsToValidate.length() : null;
//    }
}
