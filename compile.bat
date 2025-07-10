@echo off
echo 正在编译 LoginManager 插件...

REM 创建输出目录
if not exist "build\classes\java\main" mkdir "build\classes\java\main"
if not exist "build\libs" mkdir "build\libs"

REM 下载 Spigot API
if not exist "lib" mkdir "lib"
if not exist "lib\spigot-api-1.16.5-R0.1-SNAPSHOT.jar" (
    echo 正在下载 Spigot API...
    powershell -Command "Invoke-WebRequest -Uri 'https://hub.spigotmc.org/nexus/content/repositories/snapshots/org/spigotmc/spigot-api/1.16.5-R0.1-SNAPSHOT/spigot-api-1.16.5-R0.1-SNAPSHOT.jar' -OutFile 'lib\spigot-api-1.16.5-R0.1-SNAPSHOT.jar'"
)

REM 编译Java源代码
echo 正在编译Java源代码...
javac -cp "lib\spigot-api-1.16.5-R0.1-SNAPSHOT.jar" -d "build\classes\java\main" -encoding UTF-8 src\main\java\org\nanfans\*.java

if %ERRORLEVEL% neq 0 (
    echo 编译失败！
    pause
    exit /b 1
)

REM 复制资源文件
echo 正在复制资源文件...
xcopy "src\main\resources\*" "build\classes\java\main\" /E /Y

REM 创建JAR文件
echo 正在创建JAR文件...
cd "build\classes\java\main"
jar cf "..\..\..\..\build\libs\LoginManager-1.34.jar" .
cd "..\..\..\.."

echo 编译完成！JAR文件位于: build\libs\LoginManager-1.34.jar
pause
