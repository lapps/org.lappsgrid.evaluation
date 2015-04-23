package org.lappsgrid.eval;

public class EvaluationConfig {

    String goldAnnotationProducer = "org.anc.lapps.stanford.Tokenizer:2.0.0";

    String testAnnotationProducer = "org.anc.lapps.stanford.Tokenizer:2.0.0";

    String goldAnnotationType = "http://vocab.lappsgrid.org/Token";

    String testAnnotationType = "http://vocab.lappsgrid.org/Token";

    String goldAnnotationFeature = "word";

    String testAnnotationFeature = "word";

    String outputFormat = "html"; //or json

    public String getTestAnnotationType() {
        return testAnnotationType;
    }

    public void setTestAnnotationType(String testAnnotationType) {
        this.testAnnotationType = testAnnotationType;
    }

    public String getGoldAnnotationFeature() {
        return goldAnnotationFeature;
    }

    public void setGoldAnnotationFeature(String goldAnnotationFeature) {
        this.goldAnnotationFeature = goldAnnotationFeature;
    }

    public String getGoldAnnotationType() {
        return goldAnnotationType;
    }

    public void setGoldAnnotationType(String goldAnnotationType) {
        this.goldAnnotationType = goldAnnotationType;
    }

    public String getTestAnnotationFeature() {
        return testAnnotationFeature;
    }

    public void setTestAnnotationFeature(String testAnnotationFeature) {
        this.testAnnotationFeature = testAnnotationFeature;
    }

    public String getGoldAnnotationProducer() {
        return goldAnnotationProducer;
    }

    public void setGoldAnnotationProducer(String goldAnnotationProducer) {
        this.goldAnnotationProducer = goldAnnotationProducer;
    }

    public String getTestAnnotationProducer() {
        return testAnnotationProducer;
    }

    public void setTestAnnotationProducer(String testAnnotationProducer) {
        this.testAnnotationProducer = testAnnotationProducer;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

}
