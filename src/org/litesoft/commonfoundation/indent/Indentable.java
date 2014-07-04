package org.litesoft.commonfoundation.indent;

import org.litesoft.commonfoundation.annotations.*;

public interface Indentable {
    void appendTo( @NotNull IndentableWriter pWriter );
}
