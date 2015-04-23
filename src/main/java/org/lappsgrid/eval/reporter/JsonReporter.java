package org.lappsgrid.eval.reporter;

import org.json.simple.JSONObject;
import org.lappsgrid.eval.model.Span;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class JsonReporter extends Reporter {

    public JsonReporter(Map<Span, String> goldSpanOut, Map<Span, String> predictSpanOut) {
        super(goldSpanOut, predictSpanOut);
    }

    @Override
    public String report() {
        JSONObject obj = new JSONObject();
        obj.put("reference-counts", eval.countReferenceOutcomes());
        obj.put("predicted-counts", eval.countPredictedOutcomes());
        obj.put("correct-counts", eval.countCorrectOutcomes());
        obj.put("precision", eval.precision());
        obj.put("recall", eval.recall());
        obj.put("f1", eval.f1());
        return obj.toJSONString();
    }


}
