package org.litesoft.commonfoundation.indent;

import org.litesoft.commonfoundation.annotations.*;

public interface Indentable {
    @NotNull
    IndentableWriter appendTo( @NotNull IndentableWriter pWriter );
}
