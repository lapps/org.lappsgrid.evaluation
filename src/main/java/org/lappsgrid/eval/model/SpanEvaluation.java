package org.lappsgrid.eval.model;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.*;

public class SpanEvaluation<OUTCOME_TYPE extends Comparable<? super OUTCOME_TYPE>> {

    public Multiset<OUTCOME_TYPE> referenceOutcomes;

    public Multiset<OUTCOME_TYPE> predictedOutcomes;

    private Multiset<OUTCOME_TYPE> correctOutcomes;

    public SpanEvaluation() {
        super();
        referenceOutcomes = HashMultiset.create();
        predictedOutcomes = HashMultiset.create();
        correctOutcomes = HashMultiset.create();
    }

    public <ANNOTATION_TYPE, SPAN_TYPE> void add(
            Collection<? extends ANNOTATION_TYPE> referenceAnnotations,
            Collection<? extends ANNOTATION_TYPE> predictedAnnotations,
            Function<ANNOTATION_TYPE, SPAN_TYPE> annotationToSpan,
            Function<ANNOTATION_TYPE, OUTCOME_TYPE> annotationToOutcome) {
        // map gold spans to their outcomes
        Map<SPAN_TYPE, OUTCOME_TYPE> referenceSpanOutcomes = new HashMap<SPAN_TYPE, OUTCOME_TYPE>();
        for (ANNOTATION_TYPE ann : referenceAnnotations) {
            referenceSpanOutcomes.put(annotationToSpan.apply(ann), annotationToOutcome.apply(ann));
        }

        // map system spans to their outcomes
        Map<SPAN_TYPE, OUTCOME_TYPE> predictedSpanOutcomes = new HashMap<SPAN_TYPE, OUTCOME_TYPE>();
        for (ANNOTATION_TYPE ann : predictedAnnotations) {
            predictedSpanOutcomes.put(annotationToSpan.apply(ann), annotationToOutcome.apply(ann));
        }
        add(referenceSpanOutcomes, predictedSpanOutcomes);
    }

    public <ANNOTATION_TYPE, SPAN_TYPE> void add(
            Map<SPAN_TYPE, OUTCOME_TYPE> referenceSpanOutcomes,
            Map<SPAN_TYPE, OUTCOME_TYPE> predictedSpanOutcomes) {

        // update the gold and system outcomes
        this.referenceOutcomes.addAll(referenceSpanOutcomes.values());
        this.predictedOutcomes.addAll(predictedSpanOutcomes.values());

        // determine the outcomes that were correct
        Set<SPAN_TYPE> intersection = new HashSet<SPAN_TYPE>();
        intersection.addAll(referenceSpanOutcomes.keySet());
        intersection.retainAll(predictedSpanOutcomes.keySet());
        for (SPAN_TYPE span : intersection) {
            OUTCOME_TYPE goldOutcome = referenceSpanOutcomes.get(span);
            OUTCOME_TYPE systemOutcome = predictedSpanOutcomes.get(span);
            if (Objects.equal(goldOutcome, systemOutcome)) {
                this.correctOutcomes.add(goldOutcome);
            }
        }

        // update the confusion matrix
        Set<SPAN_TYPE> union = new HashSet<SPAN_TYPE>();
        union.addAll(referenceSpanOutcomes.keySet());
        union.addAll(predictedSpanOutcomes.keySet());
        for (SPAN_TYPE span : union) {
            OUTCOME_TYPE goldOutcome = referenceSpanOutcomes.get(span);
            OUTCOME_TYPE systemOutcome = predictedSpanOutcomes.get(span);
        }
    }

    public int countCorrectOutcomes() {
        return this.correctOutcomes.size();
    }

    public int countCorrectOutcomes(OUTCOME_TYPE outcome) {
        return this.correctOutcomes.count(outcome);
    }

    public int countPredictedOutcomes() {
        return this.predictedOutcomes.size();
    }

    public int countPredictedOutcomes(OUTCOME_TYPE outcome) {
        return this.predictedOutcomes.count(outcome);
    }

    public int countReferenceOutcomes() {
        return this.referenceOutcomes.size();
    }

    public int countReferenceOutcomes(OUTCOME_TYPE outcome) {
        return this.referenceOutcomes.count(outcome);
    }

    public int countFalseNegatives(OUTCOME_TYPE... positiveOutcomes) {
        int numReferenceOutcomes = this.countReferenceOutcomes();
        int numPredictedOutcomes = this.countPredictedOutcomes();
        if (numReferenceOutcomes != numPredictedOutcomes) {
            throw new IllegalStateException(
                    String.format(
                            "Expected number equal number of references outcomes and predicted outcomes.  Had reference outcomes=%d, predicted outcomes=%d",
                            numReferenceOutcomes, numPredictedOutcomes, this.countPredictedOutcomes())
            );
        }
        int totalFalseNegatives = 0;
        for (OUTCOME_TYPE positiveOutcome : positiveOutcomes) {
            totalFalseNegatives += this.countReferenceOutcomes(positiveOutcome)
                    - this.countCorrectOutcomes(positiveOutcome);
        }
        return totalFalseNegatives;
    }

    public int countFalsePositives(OUTCOME_TYPE... positiveOutcomes) {
        int numReferenceOutcomes = this.countReferenceOutcomes();
        int numPredictedOutcomes = this.countPredictedOutcomes();
        if (numReferenceOutcomes != numPredictedOutcomes) {
            throw new IllegalStateException(
                    String.format(
                            "Expected number equal number of references outcomes and predicted outcomes.  Had reference outcomes=%d, predicted outcomes=%d",
                            numReferenceOutcomes, numPredictedOutcomes, this.countPredictedOutcomes())
            );
        }
        int totalFalsePositives = 0;
        for (OUTCOME_TYPE positiveOutcome : positiveOutcomes) {
            totalFalsePositives += this.countPredictedOutcomes(positiveOutcome)
                    - this.countCorrectOutcomes(positiveOutcome);
        }

        return totalFalsePositives;
    }

    public int countTrueNegatives(OUTCOME_TYPE... positiveOutcomes) {
        int numReferenceOutcomes = this.countReferenceOutcomes();
        int numPredictedOutcomes = this.countPredictedOutcomes();
        if (numReferenceOutcomes != numPredictedOutcomes) {
            throw new IllegalStateException(
                    String.format(
                            "Expected number equal number of references outcomes and predicted outcomes.  Had reference outcomes=%d, predicted outcomes=%d",
                            numReferenceOutcomes, numPredictedOutcomes, this.countPredictedOutcomes())
            );
        }
        int totalTrueNegatives = this.countCorrectOutcomes();

        for (OUTCOME_TYPE positiveOutcome : positiveOutcomes) {
            totalTrueNegatives -= this.countCorrectOutcomes(positiveOutcome);
        }

        return totalTrueNegatives;

    }

    public int countTruePositives(OUTCOME_TYPE... positiveOutcomes) {
        int numReferenceOutcomes = this.countReferenceOutcomes();
        int numPredictedOutcomes = this.countPredictedOutcomes();
        if (numReferenceOutcomes != numPredictedOutcomes) {
            throw new IllegalStateException(
                    String.format(
                            "Expected number equal number of references outcomes and predicted outcomes.  Had reference outcomes=%d, predicted outcomes=%d",
                            numReferenceOutcomes, numPredictedOutcomes, this.countPredictedOutcomes())
            );
        }

        int totalTruePositives = 0;
        for (OUTCOME_TYPE positiveOutcome : positiveOutcomes) {
            totalTruePositives += this.countCorrectOutcomes(positiveOutcome);
        }
        return totalTruePositives;
    }


    public double precision() {
        int nSystem = this.countPredictedOutcomes();
        return nSystem == 0 ? 1.0 : ((double) this.countCorrectOutcomes()) / nSystem;
    }

    public double precision(OUTCOME_TYPE outcome) {
        int nSystem = this.countPredictedOutcomes(outcome);
        return nSystem == 0 ? 1.0 : ((double) this.countCorrectOutcomes(outcome)) / nSystem;
    }

    public double recall() {
        int nGold = this.countReferenceOutcomes();
        return nGold == 0 ? 1.0 : ((double) this.countCorrectOutcomes()) / nGold;
    }

    public double recall(OUTCOME_TYPE outcome) {
        int nGold = this.countReferenceOutcomes(outcome);
        return nGold == 0 ? 1.0 : ((double) this.countCorrectOutcomes(outcome)) / nGold;
    }

    public double f(double beta) {
        double p = this.precision();
        double r = this.recall();
        double num = (1 + beta * beta) * p * r;
        double den = (beta * beta * p) + r;
        return den == 0.0 ? 0.0 : num / den;
    }

    public double f(double beta, OUTCOME_TYPE outcome) {
        double p = this.precision(outcome);
        double r = this.recall(outcome);
        double num = (1 + beta * beta) * p * r;
        double den = (beta * beta * p) + r;
        return den == 0.0 ? 0.0 : num / den;
    }

    public double f1() {
        return this.f(1.0);
    }

    public double f1(OUTCOME_TYPE outcome) {
        return f(1.0, outcome);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("P\tR\tF1\t#gold\t#system\t#correct\n");
        result.append(String.format("%.3f\t%.3f\t%.3f\t%d\t%d\t%d\tOVERALL\n", this.precision(),
                this.recall(), this.f1(), this.referenceOutcomes.size(), this.predictedOutcomes.size(),
                this.correctOutcomes.size()));
        List<OUTCOME_TYPE> outcomes = new ArrayList<OUTCOME_TYPE>(this.referenceOutcomes.elementSet());
        if (outcomes.size() > 1) {
            Collections.sort(outcomes);
            for (OUTCOME_TYPE outcome : outcomes) {
                result.append(String.format("%.3f\t%.3f\t%.3f\t%d\t%d\t%d\t%s\n", this.precision(outcome),
                        this.recall(outcome), this.f1(outcome), this.referenceOutcomes.count(outcome),
                        this.predictedOutcomes.count(outcome), this.correctOutcomes.count(outcome),
                        outcome));
            }
        }
        return result.toString();
    }

}
