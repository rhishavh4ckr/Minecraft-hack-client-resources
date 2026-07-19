@echo off
REM LionClient Redesigned - simple builder
setlocal
cd /d "%~dp0"
echo ========================================
echo  LionClient Redesigned Builder
echo ========================================
echo Working folder: %CD%
echo.

echo [1/5] Looking for javac.exe ...
set JAVAC=
for /f "delims=" %%J in ('where javac 2^>nul') do set JAVAC=%%~fJ
if not "%JAVAC%"=="" goto got_javac
if exist "C:\Program Files\Java\jdk-21\bin\javac.exe" set "JAVAC=C:\Program Files\Java\jdk-21\bin\javac.exe"
if exist "C:\Program Files\Java\jdk-21.0.10\bin\javac.exe" set "JAVAC=C:\Program Files\Java\jdk-21.0.10\bin\javac.exe"
if exist "C:\Program Files\Java\jdk-17\bin\javac.exe" set "JAVAC=C:\Program Files\Java\jdk-17\bin\javac.exe"
if not "%JAVAC%"=="" goto got_javac
echo.
echo ERROR: Could not find javac.exe on your PC.
echo.
echo Please install the JDK (Java Development Kit) 21 from:
echo   https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.exe
echo.
echo Then run this batch file again.
echo.
pause
exit /b 1
:got_javac
echo   Found javac: %JAVAC%
for %%I in ("%JAVAC%") do set JDK_BIN=%%~dpI

echo [2/5] Looking for jar.exe ...
set JAR=
if exist "%JDK_BIN%jar.exe" set "JAR=%JDK_BIN%jar.exe"
if not "%JAR%"=="" goto got_jar
for /d %%D in ("C:\Program Files\Java\jdk*") do if exist "%%D\bin\jar.exe" set "JAR=%%D\bin\jar.exe"
if not "%JAR%"=="" goto got_jar
for /d %%D in ("C:\Program Files\Eclipse Adoptium\jdk*") do if exist "%%D\bin\jar.exe" set "JAR=%%D\bin\jar.exe"
:got_jar
if not "%JAR%"=="" (
  echo   Found jar:   %JAR%
) else (
  echo   jar.exe not found - will use PowerShell zip fallback.
)

echo [3/5] Preparing build folder ...
if exist build rmdir /s /q build
mkdir build
cd build
echo   Extracting LionClient-Redesigned.jar ...
tar xf ..\LionClient-Redesigned.jar
if errorlevel 1 (
  echo ERROR: Could not extract LionClient-Redesigned.jar.
  echo Make sure this BAT file is in the SAME folder as LionClient-Redesigned.jar.
  echo Current folder: %CD%
  cd ..
  pause
  exit /b 1
)

echo [4/5] Compiling UI sources ...
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
"%JAVAC%" -encoding UTF-8 -source 8 -target 8 -d _build\classes @sources.txt
if errorlevel 1 (
  echo.
  echo ERROR: javac reported errors (see above).
  echo.
  cd ..
  pause
  exit /b 1
)
xcopy /E /Y /Q _build\classes\* . >nul

echo [5/5] Packaging client-patched.jar ...
if not "%JAR%"=="" goto do_jar
goto do_pszip
:do_jar
"%JAR%" cfm client-patched.jar META-INF\MANIFEST.MF .
if errorlevel 1 (
  echo ERROR: jar.exe failed - falling back to PowerShell zip.
  goto do_pszip
)
goto packed
:do_pszip
if exist client-patched.jar del /q client-patched.jar
if exist client-patched.zip del /q client-patched.zip
powershell -NoProfile -ExecutionPolicy Bypass -Command "Compress-Archive -Path '*' -DestinationPath 'client-patched.zip' -Force"
if errorlevel 1 (
  echo ERROR: PowerShell zip failed.
  cd ..
  pause
  exit /b 1
)
move /y client-patched.zip client-patched.jar >nul
:packed

cd ..
echo.
echo ========================================
echo  BUILD SUCCESSFUL!
echo ========================================
echo.
echo   Patched client: %CD%\build\client-patched.jar
echo.
echo   Drop this file into your Minecraft mods\ folder,
echo   or run from CMD:  java -jar "%CD%\build\client-patched.jar"
echo.
echo   Press RIGHT_SHIFT in-game to open the new ClickGUI.
echo.
pause
endlocal
