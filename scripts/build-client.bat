@echo off
REM =====================================================================
REM  One-click Windows builder for LionClient-Redesigned
REM  Run this from the folder containing LionClient-Redesigned.zip
REM =====================================================================

setlocal ENABLEDELAYEDEXPANSION

echo [1/7] Locating JDK ...
for /f "delims=" %%J in ('where javac 2^>nul') do set JAVAC=%%~fJ
if not defined JAVAC (
    echo ERROR: javac not found on PATH. Install a JDK 8+ and try again.
    pause
    exit /b 1
)
echo     javac = %JAVAC%

REM JDK bin is the directory of javac; jar.exe lives next to it
for %%I in ("%JAVAC%") do set JDK_BIN=%%~dpI
set JAR="%JDK_BIN%jar.exe"
if not exist %JAR% (
    echo ERROR: jar.exe not found next to javac in %JDK_BIN%
    echo Looking for jar.exe manually...
    for /f "delims=" %%K in ('dir /s /b "C:\Program Files\Java\jar.exe" "C:\Program Files\Eclipse Adoptium\jar.exe" "C:\Program Files\Microsoft\jdk-*\bin\jar.exe" 2^>nul') do set JAR="%%K"
)
echo     jar   = %JAR%

echo [2/7] Cleaning old build folder ...
if exist LionClient-Redesigned rmdir /s /q LionClient-Redesigned
if exist build              rmdir /s /q build

echo [3/7] Extracting zip ...
mkdir LionClient-Redesigned
tar -xf LionClient-Redesigned.zip -C LionClient-Redesigned
if errorlevel 1 (
    echo ERROR: tar failed to extract LionClient-Redesigned.zip
    pause
    exit /b 1
)

echo [4/7] Locating inner jar ...
REM The zip contains a LionClient/ folder with LionClient-Redesigned.jar inside
set INNER_JAR=
if exist "LionClient-Redesigned\LionClient\LionClient-Redesigned.jar" set INNER_JAR=LionClient-Redesigned\LionClient\LionClient-Redesigned.jar
if exist "LionClient-Redesigned\LionClient-Redesigned.jar"        set INNER_JAR=LionClient-Redesigned\LionClient-Redesigned.jar
if not defined INNER_JAR (
    echo ERROR: Could not find LionClient-Redesigned.jar inside the extracted zip.
    dir /s /b LionClient-Redesigned\*.jar
    pause
    exit /b 1
)
echo     inner jar = %INNER_JAR%

echo [5/7] Extracting jar into build\ ...
mkdir build
cd build
tar -xf ..\%INNER_JAR%
if errorlevel 1 (
    echo ERROR: tar failed to extract inner jar
    cd ..
    pause
    exit /b 1
)

echo [6/7] Compiling sources ...
mkdir _build\classes 2>nul
dir /s /b net\hackclient\*.java org\json\*.java net\java\h.java net\java\i.java net\java\r.java net\java\s.java net\java\ag.java mod_d.java > sources.txt
javac -encoding UTF-8 -d _build\classes --release 8 @sources.txt
if errorlevel 1 (
    echo.
    echo ERROR: Compilation failed. Copy-paste the errors above.
    cd ..
    pause
    exit /b 1
)
xcopy /E /Y /Q _build\classes\* . >nul

echo [7/7] Packaging client-patched.jar ...
%JAR% cfm client-patched.jar META-INF\MANIFEST.MF .
if errorlevel 1 (
    echo ERROR: jar packaging failed
    cd ..
    pause
    exit /b 1
)

cd ..
echo.
echo =====================================================================
echo  DONE.
echo  Your patched client is at:
echo     %CD%\build\client-patched.jar
echo  Drop it into your mods/ folder or run: java -jar build\client-patched.jar
echo =====================================================================
pause
