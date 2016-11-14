# Braids-Java-Utilities

Herein lie miscellaneous Java utilities, including some handy bits for porting Python code to Java.  (Sorry, Guido.)

## Developer instructions

To make a jar file, run:

gradle build uploadArchives

If you don't want to install Gradle, that's fine; you can use gradlew instead.  From the some directory as this README:

./gradlew build uploadArchives

Gradle places the result in the repos/ directory. 