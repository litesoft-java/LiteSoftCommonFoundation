package org.litesoft.commonfoundation.typeutils;

public class Bits
{
    /**
     * Calculate the new "AccumulatedBitFlags" by Overriding the appropriate bit flags in
     * pPreviousAccumulatedBitFlags (based on the bits in pValidBitsOfNewBitFlags) from the pNewBitFlags.
     *
     * @param pPreviousAccumulatedBitFlags Bit Flags to be selectively overriden.
     * @param pValidBitsOfNewBitFlags      The '1' bits will indicate which bids to override.
     * @param pNewBitFlags                 The source of the override Bit Flags.
     *
     * @return The new "AccumulatedBitFlags".
     */
    public static int mutateAccumulatedBitFlags( int pPreviousAccumulatedBitFlags, int pValidBitsOfNewBitFlags, int pNewBitFlags )
    {
        return (pPreviousAccumulatedBitFlags & (~pValidBitsOfNewBitFlags)) //
               | (pValidBitsOfNewBitFlags & pNewBitFlags);
    }
}
