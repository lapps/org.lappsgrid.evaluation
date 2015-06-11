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

package org.lappsgrid.eval;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.ParseException;
import org.lappsgrid.api.WebService;
import org.lappsgrid.eval.reporter.HtmlReporter;
import org.lappsgrid.eval.model.Span;
import org.lappsgrid.eval.reporter.JsonReporter;
import org.lappsgrid.eval.reporter.Reporter;
import org.lappsgrid.serialization.Data;
import org.lappsgrid.serialization.Serializer;
import org.lappsgrid.serialization.lif.Annotation;
import org.lappsgrid.serialization.lif.Container;
import org.lappsgrid.serialization.lif.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Di Wang.
 */

public class AnnotationEvaluator implements WebService {

    public static final String VERSION = "0.0.1-SNAPSHOT";

    public static final String EVALUATION_CONFIGURATION_NAME = "evaluation-configuration";


    static public String evaluate(Container container, EvaluationConfig evalConfig) throws IOException,
            ParseException {

        List<Annotation> goldAnnotations = findAnnotations(container,
                evalConfig.getGoldAnnotationType(),
                evalConfig.getGoldAnnotationProducer(), evalConfig.getGoldAnnotationFeature());

        List<Annotation> testAnnotations = findAnnotations(container,
                evalConfig.getTestAnnotationType(),
                evalConfig.getTestAnnotationProducer(), evalConfig.getTestAnnotationFeature());

        Map<Span, String> goldSpanOutMap = getSpanOutcomeMap(goldAnnotations, evalConfig.getGoldAnnotationFeature());
        Map<Span, String> testSpanOutMap = getSpanOutcomeMap(testAnnotations, evalConfig.getTestAnnotationFeature());

        Reporter reporter;
        switch (evalConfig.getOutputFormat()) {
            case "json":
                reporter = new JsonReporter(goldSpanOutMap, testSpanOutMap);
                break;
            case "html":
                reporter = new HtmlReporter(goldSpanOutMap, testSpanOutMap);
                break;
            default:
                reporter = new HtmlReporter(goldSpanOutMap, testSpanOutMap);
                break;
        }
        return reporter.report();
    }


    static List<Annotation> findAnnotations(Container container, String type, String producer, String feature) {
        List<Annotation> annotations = new ArrayList<>();

        List<View> views = container.findViewsThatContainBy(type, producer);
        for (View view : views) {
            List<Annotation> viewAnnotations = view.getAnnotations();
            for (Annotation viewAnnotation : viewAnnotations) {
                if (viewAnnotation.getLabel().equals(type)) {
                    annotations.add(viewAnnotation);
                }
            }
        }
        return annotations;
    }


    public static Map<Span, String> getSpanOutcomeMap(List<Annotation> annotations, String featureName) {
        Map<Span, String> spanToOutcome = new HashMap<>();
        if (annotations == null) {
            return spanToOutcome;
        }

        for (Annotation annotation : annotations) {
            Span span = createSpan(annotation);
            Object outcome = annotation.getFeature(featureName);
            String featureString = "";
            if (outcome != null) {
                featureString = (String) outcome;
            }
            spanToOutcome.put(span, featureString);
        }
        return spanToOutcome;
    }

    static Span createSpan(Annotation annotation) {
        return new Span(annotation.getStart(), annotation.getEnd());
    }

    @Override
    public String execute(String input) {
        //input = setDefaultEvalConfig(input); //for testing example
        System.out.println("AnnotationEvaluator started... with input size: " + input.length());
        String eval_result = "";
        try {
            Data<Object> result = Serializer.parse(input, Data.class);
            Object payload = result.getPayload();
            Container container = new Container((Map) payload);

            Map<String, String> evalConfigMap = (Map<String, String>) container.getMetadata(EVALUATION_CONFIGURATION_NAME);

            ObjectMapper mapper = new ObjectMapper();
            EvaluationConfig evalConfig = mapper.convertValue(evalConfigMap, EvaluationConfig.class);

            if (evalConfig == null) {
                evalConfig = new EvaluationConfig(); // use default
            }
            eval_result = evaluate(container, evalConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("AnnotationEvaluator ended with output size: " + eval_result.length());
        return eval_result;
    }

    private String setDefaultEvalConfig(String data) {
        return setEvalConfig(data, new EvaluationConfig());
    }

    private String setEvalConfig(String data, EvaluationConfig evalConfig) {

        Data<Object> result = Serializer.parse(data, Data.class);
        Object payload = result.getPayload();
        Container container = new Container((Map) payload);

        ObjectMapper mapper = new ObjectMapper();
        Map evalConfigMap = mapper.convertValue(evalConfig, Map.class);
        container.setMetadata(EVALUATION_CONFIGURATION_NAME, evalConfigMap);

        result.setPayload(container);

        return Serializer.toJson(result);
    }

    @Override
    public String getMetadata() {
        return "TODO";
    }

}
