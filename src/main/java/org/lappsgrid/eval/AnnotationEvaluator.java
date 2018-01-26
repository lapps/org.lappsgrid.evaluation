/*-
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
import org.lappsgrid.discriminator.Discriminators;
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
 * @author Alexandru Mahmoud
 */

public class AnnotationEvaluator implements WebService {
//    private static final Logger logger = LoggerFactory.getLogger(AnnotationEvaluator.class);

    public static final String DISCRIMINATOR = "http://vocab.lappsgrid.org/ns/media/jsonld#document-list";

    public static final String VERSION = Version.getVersion();

    public static final String EVALUATION_CONFIGURATION_NAME = "evaluation-configuration";


    static public String evaluate(Container predictedData, Container goldData, EvaluationConfig evalConfig) throws IOException,
            ParseException {
        List<Annotation> goldAnnotations = findAnnotations(predictedData,
                evalConfig.getGoldAnnotationType(),
                evalConfig.getGoldAnnotationProducer(), evalConfig.getGoldAnnotationFeature());

        List<Annotation> testAnnotations = findAnnotations(goldData,
                evalConfig.getTestAnnotationType(),
                evalConfig.getTestAnnotationProducer(), evalConfig.getTestAnnotationFeature());

        Map<Span, String> goldSpanOutMap = getSpanOutcomeMap(goldAnnotations, evalConfig.getGoldAnnotationFeature());
        Map<Span, String> testSpanOutMap = getSpanOutcomeMap(testAnnotations, evalConfig.getTestAnnotationFeature());


        Reporter reporter;
        String result = null;
        switch (evalConfig.getOutputFormat()) {
            // For JSON, the reporter will return the JSON string of the confusion matrix,
            // which will be put into the metadata
            case "json":
                reporter = new JsonReporter(goldSpanOutMap, testSpanOutMap);
                String reporterResult = reporter.report();
                Map predictedMetadata = predictedData.getMetadata();
                predictedMetadata.put("confusion-matrix", reporterResult);
                predictedData.setMetadata(predictedMetadata);
                result = new Data(Discriminators.Uri.LIF, predictedData).asJson();
                break;

            // For the HTML, return the report directly
            case "html":
                reporter = new HtmlReporter(goldSpanOutMap, testSpanOutMap, predictedData.getText(), evalConfig);
                result = reporter.report();
                break;

            default:
                reporter = new HtmlReporter(goldSpanOutMap, testSpanOutMap, predictedData.getText(), evalConfig);
                result = reporter.report();
                break;
        }

        return result;
    }


    static List<Annotation> findAnnotations(Container container, String type, String producer, String feature) {
        List<Annotation> annotations = new ArrayList<>();

        List<View> views = container.findViewsThatContainBy(type, producer);
        for (View view : views) {
            List<Annotation> viewAnnotations = view.getAnnotations();
            for (Annotation viewAnnotation : viewAnnotations) {
                if (viewAnnotation.getAtType().equals(type)) {
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
    public String execute(String input)
    {
        //input = setDefaultEvalConfig(input); //for testing example
//        logger.info("AnnotationEvaluator started... with input size: {}", input.length());
        //System.out.println("AnnotationEvaluator started... with input size: " + input.length());
        
        String eval_result = "";
        try
        {

            Data<Object> data = Serializer.parse(input, Data.class);
            String discriminator = data.getDiscriminator();

            // Return ERRORS back
            if (Discriminators.Uri.ERROR.equals(discriminator))
            {
                return input;
            }


            // If the input discriminator is wrong, return an error
            else if(!Discriminators.Uri.LAPPS.equals(discriminator))
            {
                return new Data(Discriminators.Uri.ERROR, "Invalid input").asJson();
            }

            Map payloadMap = (Map) data.getPayload();
            Container goldData = new Container((Map) payloadMap.get("gold"));
            Container predictedData = new Container((Map) payloadMap.get("predicted"));

            Map<String, String> evalConfigMap = (Map<String, String>) predictedData.getMetadata(EVALUATION_CONFIGURATION_NAME);

            ObjectMapper mapper = new ObjectMapper();
            EvaluationConfig evalConfig = mapper.convertValue(evalConfigMap, EvaluationConfig.class);

            if (evalConfig == null) { evalConfig = new EvaluationConfig(); } // use default

            eval_result = evaluate(predictedData, goldData, evalConfig);
            //System.out.println("AnnotationEvaluator ended with output size: " + eval_result.length());


        }
        catch (Exception e) { e.printStackTrace(); }

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
