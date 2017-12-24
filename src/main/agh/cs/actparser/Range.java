package agh.cs.actparser;

import java.util.Objects;

/**
 * General purpose range container.
 */
public class Range<T> {
    public final T from;
    public final T to;

    public Range(T from, T to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return from.toString() + ".." + to.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range<?> range = (Range<?>) o;
        return Objects.equals(from, range.from) &&
                Objects.equals(to, range.to);
    }
}

