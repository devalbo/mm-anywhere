rmdir /Q /S ..\src\main\java
REM rmdir /Q /S ..\python

mkdir ..\src\main\java
mkdir ..\python

REM xcopy /E google-src\java\* ..\src\main\java
xcopy /E google-src\python\* ..\python

REM protoc --java_out=../src/main/java --python_out=../python descriptor.proto
protoc --java_out=../src/main/java --python_out=../python MmAnywhere.proto