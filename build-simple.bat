@echo off
echo Building LoginManager plugin without external dependencies...

REM Create output directories
if not exist "build\classes\java\main" mkdir "build\classes\java\main"
if not exist "build\libs" mkdir "build\libs"

REM Copy resource files first
echo Copying resource files...
xcopy "src\main\resources\*" "build\classes\java\main\" /E /Y

REM Create a simple JAR with just the resources for now
echo Creating JAR file...
cd "build\classes\java\main"
jar cf "..\..\..\..\build\libs\LoginManager-1.34-resources-only.jar" .
cd "..\..\..\.."

echo Build complete! Resource-only JAR file is at: build\libs\LoginManager-1.34-resources-only.jar
echo.
echo To compile the Java sources, you need to:
echo 1. Download Spigot API JAR file
echo 2. Place it in the lib\ directory
echo 3. Run: javac -cp "lib\spigot-api.jar" -d "build\classes\java\main" -encoding UTF-8 src\main\java\org\nanfans\*.java
echo 4. Then create the final JAR with both classes and resources
echo.
pause
