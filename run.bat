@echo off


where java 1>nul 2>nul || (
   echo no java installed
   echo install it from : https://www.oracle.com/in/java/technologies/javase/javase-jdk8-downloads.html
   Start https://www.oracle.com/in/java/technologies/javase/javase-jdk8-downloads.html
   exit /b 2
)

java -jar run.jar

