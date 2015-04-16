# org.lappsgrid.evaluation


The `evaluation` module computes the evluation metrics and outputs an HTML report over two sets annotations 
in the LAPPS JSON format.

It reads the "evaluation-configuration" object from the LAPPS JSON object's metadata to locate the right pairs of annotations.
An example configuration json object looks like:
```json
...
   "metadata":{  
      "evaluation-configuration":{  
         "goldAnnotationProducer":"org.anc.lapps.masc.json:2.0.0",
         "testAnnotationProducer":"org.anc.lapps.stanford.Tokenizer:2.0.0",
         "goldAnnotationType":"http://vocab.lappsgrid.org/Token",
         "testAnnotationType":"http://vocab.lappsgrid.org/Token",
         "goldAnnotationFeature":"word",
         "testAnnotationFeature":"word"
      }
   }
...
```
Inside the configuration object, the prefix `gold-` is short for reference outputs, and prefix `test-` is short for predicted outputs.


An example Java code to setup "evaluation-configuration" object:
```java
   private String setEvalConfig(String data) {
        //Create an EvaluationConfig java bean
        EvaluationConfig evalConfig = new EvaluationConfig();
        evalConfig.setGoldAnnotationProducer("org.anc.lapps.masc.json:2.0.0");
        evalConfig.setTestAnnotationProducer("org.anc.lapps.stanford.Tokenizer:2.0.0");
        evalConfig.setGoldAnnotationType("http://vocab.lappsgrid.org/Token");
        evalConfig.setTestAnnotationType("http://vocab.lappsgrid.org/Token");
        evalConfig.setGoldAnnotationFeature("word");
        evalConfig.setGoldAnnotationFeature("word");

        //Parse the input Json data, and get its payload container
        Data<Object> result = Serializer.parse(data, Data.class);
        Object payload = result.getPayload();
        Container container = new Container((Map) payload);

        //Convert the EvaluationConfig bean as Map, and insert into metadata
        ObjectMapper mapper = new ObjectMapper();
        Map evalConfigMap = mapper.convertValue(evalConfig, Map.class);
        container.setMetadata("evaluation-configuration", evalConfigMap);

        //Save the updated Lapps Json object 
        result.setPayload(container);
        return Serializer.toJson(result);
    }
```
