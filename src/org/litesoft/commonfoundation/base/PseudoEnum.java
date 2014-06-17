package org.litesoft.commonfoundation.base;

public interface PseudoEnum {
    Class<? extends PseudoEnum> getPseudoEnumClass();
    String name();
}
