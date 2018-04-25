@ECHO OFF
set DUMPDIR=dumps
set CP="target\classes;target\test-classes;target\libs;target\*"
set DUMP=-XX:+HeapDumpOnOutOfMemoryError
set DUMP=%DUMP% -XX:HeapDumpPath=%DUMPDIR%
mkdir %DUMPDIR% > nul 2> nul
java -cp %CP% %DUMP% com.github.intermon.CreateSchemaMain
