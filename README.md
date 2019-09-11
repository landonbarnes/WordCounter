# WordCounter
Counts occurrences of words contained in a paragraph in a JSON Object key: "para"

# Prerequisites
Java and Javac command line path/classpath set up

json-simple-1.1.1.jar from https://code.google.com/archive/p/json-simple/downloads  
junit-4.13-beta-3.jar from https://github.com/junit-team/junit4/wiki/Download-and-Install  
hamcrest-core-1.3.jar from https://github.com/junit-team/junit4/wiki/Download-and-Install  

# Compiling
When compiling WordCounter.java ensure that the json-simple-1.1.1.jar is included in the class path  
When compiling WordCounterTest.java ensure that all of the prerequisite listed jar files are included in the class path.

# Run
To run the WordCounter enter one string argument such as: "{ \\"para\" : \"beta alpha gamma delta alpha\" }"
