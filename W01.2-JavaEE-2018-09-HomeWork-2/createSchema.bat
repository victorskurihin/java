@ECHO OFF
set CP="target\classes;target\lib\*;target\*"
java -cp %CP% ru.otus.CreateAndFillTables
