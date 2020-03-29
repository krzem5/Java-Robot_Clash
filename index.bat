echo off
echo NUL>_.class&&del /s /f /q *.class
cls
javac com/krzem/robot_clash/Main.java&&java com/krzem/robot_clash/Main
start /min cmd /c "echo NUL>_.class&&del /s /f /q *.class"