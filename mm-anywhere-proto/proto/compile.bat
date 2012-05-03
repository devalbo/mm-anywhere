rmdir /Q /S ..\java
rmdir /Q /S ..\python

mkdir ..\java
mkdir ..\python

xcopy /E google-src\java\* ..\java
xcopy /E google-src\python\* ..\python

protoc --java_out=../java --python_out=../python descriptor.proto
protoc --java_out=../java --python_out=../python MmAnywhere.proto