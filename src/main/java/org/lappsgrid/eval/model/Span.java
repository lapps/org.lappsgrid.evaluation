package org.lappsgrid.eval.model;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

public class Span implements Comparable<Span> {

    public long start;

    public long end;

    public Span(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (start ^ (start >>> 32));
        result = prime * result + (int) (end ^ (end >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Span other = (Span) obj;
        if (start != other.start)
            return false;
        if (end != other.end)
            return false;
        return true;
    }

    @Override
    public String toString() {
        ToStringHelper helper = Objects.toStringHelper(this);
        helper.add("begin", this.start);
        helper.add("end", this.end);
        return helper.toString();
    }

    @Override
    public int compareTo(Span o) {
        if (start < o.start) {
            return -1;
        } else if (start == o.start) {
            if (end > o.end) {
                return -1;
            } else if (end < o.end) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 1;
        }
    }

}
