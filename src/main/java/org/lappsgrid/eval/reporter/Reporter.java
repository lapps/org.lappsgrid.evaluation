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

package org.lappsgrid.eval.reporter;

import org.lappsgrid.eval.model.Span;
import org.lappsgrid.eval.model.SpanEvaluation;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Map;

public abstract class Reporter {

    static DecimalFormat threePlace = new DecimalFormat("#0.000");

    SpanEvaluation<String> eval;
    Map<Span, String> goldSpanOut;
    Map<Span, String> predictSpanOut;

    public Reporter(Map<Span, String> goldSpanOut, Map<Span, String> predictSpanOut) {
        super();
        this.goldSpanOut = goldSpanOut;
        this.predictSpanOut = predictSpanOut;
        eval = new SpanEvaluation<>();
        eval.add(goldSpanOut, predictSpanOut);
    }

    abstract public String report();

    public void reportToFile(String filepath) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(filepath);
        out.println(report());
        out.close();
    }
}
