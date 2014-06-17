package org.litesoft.commonfoundation.collections;

import org.litesoft.commonfoundation.annotations.*;

public interface Collector<Entry> {
    /**
     * @return Previous (if exists) Entry (similar to Set's add)
     */
    Entry add(@NotNull Entry pEntry);
}
