SET JAVA_HOME=C:\Program Files\Java\jdk1.8.0_74
SET CATALINA_HOME=C:\Users\Benjamin\Google Drive\TECNUN\apache-tomcat-5.5.12
SET PATH=%JAVA_HOME%\bin;%CATALINA_HOME%\bin;C:\Users\Benjamin\Google Drive\TECNUN\Squirrel-sql-2.6.5a;%PATH%
SET CLASSPATH=%CLASSPATH%;%CATALINA_HOME%\common\lib\servlet-api.jar;%CATALINA_HOME%\common\lib\Jama-1.0.2.jar;
javac *.java
cmd /K