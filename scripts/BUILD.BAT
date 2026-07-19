@echo off
REM ================================================================
REM  SMELLY DIHH CLIENT - Windows builder (SIMPLE & FOOLPROOF)
REM  Double-click START-HERE.bat. This script:
REM   - Always runs from its own folder
REM   - Finds javac and jar automatically (past Oracle javapath shim)
REM   - Extracts SMELLY-DIHH-CLIENT.jar to build\src\
REM   - Compiles all .java files
REM   - Zips back into build\client-patched.jar
REM ================================================================
setlocal
cd /d "%~dp0"
echo ========================================
echo  SMELLY DIHH CLIENT Builder
echo ========================================
echo  Running from: %CD%
echo.

REM -------- [1] Find javac.exe --------
echo [1/5] Locating javac.exe ...
set "JAVAC="
if defined JAVA_HOME if exist "%JAVA_HOME%\bin\javac.exe" set "JAVAC=%JAVA_HOME%\bin\javac.exe"
if not defined JAVAC for /f "delims=" %%J in ('where javac 2^>nul') do set "JAVAC=%%~fJ"
if not defined JAVAC (
  for %%V in (21 20 19 18 17 11 8) do (
    for /d %%D in ("C:\Program Files\Java\jdk*"
                  "C:\Program Files\Eclipse Adoptium\jdk*"
                  "C:\Program Files\Microsoft\jdk*"
                  "C:\Program Files\Zulu\zulu*"
                  "C:\Program Files\Amazon Corretto\jdk*"
                  "%LOCALAPPDATA%\Programs\Eclipse Adoptium\jdk*"
                  "%LOCALAPPDATA%\Programs\Microsoft\jdk*") do (
      if exist "%%~D\bin\javac.exe" if not defined JAVAC set "JAVAC=%%~D\bin\javac.exe"
    )
  )
)
if not defined JAVAC (
  echo.
  echo   ERROR: javac.exe not found. Install a JDK (8+) from https://adoptium.net/
  echo.
  pause
  exit /b 1
)
echo   Found javac: %JAVAC%
for %%I in ("%JAVAC%") do set "JDK_BIN=%%~dpI"

REM -------- [2] Find jar.exe --------
echo [2/5] Locating jar.exe ...
set "JAR="
if exist "%JDK_BIN%jar.exe" set "JAR=%JDK_BIN%jar.exe"
if not defined JAR (
  for /d %%D in ("C:\Program Files\Java\jdk*"
                "C:\Program Files\Eclipse Adoptium\jdk*"
                "C:\Program Files\Microsoft\jdk*") do (
    if exist "%%~D\bin\jar.exe" if not defined JAR set "JAR=%%~D\bin\jar.exe"
  )
)
if defined JAR (
  echo   Found jar:   %JAR%
  set "PACK=jar"
) else (
  echo   jar.exe NOT found - will use PowerShell Compress-Archive fallback.
  set "PACK=ps"
)

REM -------- [3] Find SMELLY-DIHH-CLIENT.jar --------
echo [3/5] Locating SMELLY-DIHH-CLIENT.jar ...
set "SRCJAR="
if exist "%CD%\SMELLY-DIHH-CLIENT.jar" set "SRCJAR=%CD%\SMELLY-DIHH-CLIENT.jar"
if not defined SRCJAR (
  echo.
  echo   ERROR: SMELLY-DIHH-CLIENT.jar is not in %CD%
  echo.
  echo   Your folder should contain:
  echo     - START-HERE.bat
  echo     - BUILD.BAT
  echo     - SMELLY-DIHH-CLIENT.jar
  echo.
  echo   But right now it contains:
  dir /b "%CD%"
  echo.
  echo   Delete this folder, re-download SMELLY-DIHH-CLIENT.zip,
  echo   right-click -^> Extract All into a NEW empty folder, then run START-HERE.bat.
  echo.
  pause
  exit /b 1
)
echo   Found: %SRCJAR%

REM -------- [4] Extract + Compile --------
echo [4/5] Extracting and compiling ...
if exist build rmdir /s /q build
mkdir build\src
echo   Extracting jar ...
tar xf "%SRCJAR%" -C build\src
if errorlevel 1 (
  echo   tar failed, trying PowerShell Expand-Archive ...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Expand-Archive -LiteralPath '%SRCJAR%' -DestinationPath 'build\src' -Force"
  if errorlevel 1 (
    echo   ERROR: Could not extract the jar. It may be corrupted - re-download.
    pause
    exit /b 1
  )
)

echo   Collecting .java sources ...
if exist build\src\_build rmdir /s /q build\src\_build
mkdir build\src\_build\classes
cd /d build\src
dir /s /b *.java > sources.txt
echo   Compiling ...
"%JAVAC%" -encoding UTF-8 -source 8 -target 8 -d _build\classes @sources.txt
if errorlevel 1 (
  echo.
  echo   ERROR: javac reported errors (see above).
  echo   Take a screenshot and send it.
  echo.
  cd /d "%~dp0"
  pause
  exit /b 1
)
echo   Copying compiled classes into place ...
xcopy /E /Y /Q _build\classes\* . >nul

REM -------- [5] Package --------
echo [5/5] Packaging client-patched.jar ...
if exist client-patched.jar del /q client-patched.jar
if "%PACK%"=="jar" (
  "%JAR%" cfm client-patched.jar META-INF\MANIFEST.MF .
  if errorlevel 1 set "PACK=ps"
)
if not "%PACK%"=="jar" (
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Compress-Archive -Path '*' -DestinationPath 'client-patched.zip' -Force"
  if errorlevel 1 (
    echo   ERROR: Packaging failed.
    cd /d "%~dp0"
    pause
    exit /b 1
  )
  move /y client-patched.zip client-patched.jar >nul
)
cd /d "%~dp0"
move /y build\src\client-patched.jar build\client-patched.jar >nul

echo.
echo ========================================
echo  BUILD SUCCESSFUL!
echo ========================================
echo.
echo   Output: %CD%\build\client-patched.jar
echo.
echo   Drop that JAR into your Minecraft mods\ folder, OR run it:
echo     java -jar "%CD%\build\client-patched.jar"
echo.
echo   In-game press RIGHT_SHIFT to open the ClickGUI.
echo.
pause
endlocal
