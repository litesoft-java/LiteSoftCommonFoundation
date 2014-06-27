package java8.util.function;

/**
 * Stand in for the Java 8 Function Interface - The "default" and "static" methods are NOT supported for obvious reasons!
 */
public interface Function<T, R> {
    R apply(T t);
}
