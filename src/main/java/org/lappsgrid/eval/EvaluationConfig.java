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
