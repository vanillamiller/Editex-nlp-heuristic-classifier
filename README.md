# Editex-nlp-heuristic-classifier

This package attempts to identify blend words from normalised string data using editex and various heuristics.

A blend word is defined in this case as something like <i>brunch</i> which consists of <i>breakfast</i> and <i>lunch</i>

It uses Editex phonetic string similarity and various heuristics to speed up classification

## Getting Started
To run the system, compile using your Java compiler and then run 
`java Main`

this would typically look something like this:
```bash
javac *.java
java Main
```
### Params.java

please input candidates, dictionary and test set into Params.candidatesPath, Params.dictionaryPath and Params.blendPath respectively.

Defaults as seen below

```java
class Params {

    public static final String candidatesPath="./2019S2-COMP90049_proj1-data_windows/candidates.txt";

    public static final String dictionaryPath="./2019S2-COMP90049_proj1-data_windows/dict.txt";

    public static final String blendsPath="./2019S2-COMP90049_proj1-data_windows/blends.txt";

}```
