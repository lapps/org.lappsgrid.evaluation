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
