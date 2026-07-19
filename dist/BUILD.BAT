@echo off
REM =================================================================
REM  LionClient Redesigned - one-click Windows builder
REM  Run this from the LionClient folder you unzipped.
REM  Produces client-patched.jar ready to drop into mods/
REM =================================================================
setlocal ENABLEDELAYEDEXPANSION

echo [1/6] Locating JDK ...
for /f "delims=" %%J in ('where javac 2^>nul') do set JAVAC=%%~fJ
if not defined JAVAC (
    echo ERROR: javac not found on PATH. Install a JDK 8+ and try again.
    pause
    exit /b 1
)
for %%I in ("%JAVAC%") do set JDK_BIN=%%~dpI
set JAR="%JDK_BIN%jar.exe"
if not exist %JAR% (
    echo ERROR: jar.exe not found next to javac in %JDK_BIN%
    pause
    exit /b 1
)
echo     javac = %JAVAC%
echo     jar   = %JAR%

echo [2/6] Cleaning old build ...
if exist build rmdir /s /q build
mkdir build
cd build

echo [3/6] Extracting inner jar (this is normal - the jar doubles as a zip)...
tar xf ..\LionClient-Redesigned.jar
if errorlevel 1 (
    echo ERROR: Could not extract LionClient-Redesigned.jar. Is it in this folder?
    cd ..
    pause
    exit /b 1
)

echo [4/6] Compiling sources ...
mkdir _build\classes 2>nul
dir /s /b net\hackclient\*.java org\json\*.java com\sun\jna\*.java ^
                  net\fabricmc\*.java cpw\mods\fml\common\*.java ^
                  net\minecraftforge\fml\common\*.java ^
                  net\labymod\api\*.java net\labymod\api\addon\*.java ^
                  net\labymod\api\models\addon\annotation\*.java ^
                  net\labymod\api\configuration\loader\property\*.java ^
                  net\java\h.java net\java\i.java net\java\r.java net\java\s.java ^
                  net\java\ag.java net\java\t.java net\java\m.java net\java\l.java ^
                  BaseMod.java mod_d.java > sources.txt
javac -encoding UTF-8 --release 8 -d _build\classes @sources.txt
if errorlevel 1 (
    echo.
    echo ERROR: Compilation failed - see errors above.
    cd ..
    pause
    exit /b 1
)
xcopy /E /Y /Q _build\classes\* . >nul

echo [5/6] Packaging client-patched.jar ...
%JAR% cfm client-patched.jar META-INF\MANIFEST.MF .
if errorlevel 1 (
    echo ERROR: jar packaging failed
    cd ..
    pause
    exit /b 1
)

cd ..
echo [6/6] Done.
echo.
echo =================================================================
echo  Patched client: %CD%\build\client-patched.jar
echo  Drop it into your mods/ folder or run: java -jar build\client-patched.jar
echo =================================================================
pause
