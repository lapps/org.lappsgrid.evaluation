/*
 * Copyright 2014 The Language Application Grid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
