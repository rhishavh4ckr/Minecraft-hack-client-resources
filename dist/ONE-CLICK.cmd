@echo off
REM ============================================================
REM  SMELLY DIHH CLIENT - ONE CLICK BUILDER
REM  Double-click this. It always runs from its own folder,
REM  keeps the window open on ANY error, and saves a log.
REM ============================================================
title SMELLY DIHH CLIENT BUILDER
setlocal
cd /d "%~dp0"

echo ====================================================
echo  SMELLY DIHH CLIENT BUILDER
echo ====================================================
echo  Working folder: %CD%
echo  Time: %DATE% %TIME%
echo.
echo  Log will also be saved to BUILD-LOG.txt
echo.

echo ==================================================== > BUILD-LOG.txt
echo  SMELLY DIHH CLIENT BUILD LOG >> BUILD-LOG.txt
echo  Folder: %CD% >> BUILD-LOG.txt
echo  Time: %DATE% %TIME% >> BUILD-LOG.txt
echo ==================================================== >> BUILD-LOG.txt

REM ---------- 1) find javac ----------
echo [1/6] Finding javac.exe ...
set JAVAC=
if defined JAVA_HOME if exist "%JAVA_HOME%\bin\javac.exe" set "JAVAC=%JAVA_HOME%\bin\javac.exe"
if not defined JAVAC for /f "delims=" %%J in ('where javac 2^>nul') do set "JAVAC=%%~fJ"
if not defined JAVAC (
  for %%V in (21 20 19 18 17 11 8) do (
    for %%D in (
        "C:\Program Files\Java\jdk-%%V*"
        "C:\Program Files\Java\jdk1.%%V*"
        "C:\Program Files\Eclipse Adoptium\jdk-%%V*"
        "C:\Program Files\Microsoft\jdk-%%V*"
        "C:\Program Files\Zulu\zulu-%%V*"
        "C:\Program Files\Amazon Corretto\jdk*%%V*"
        "%LOCALAPPDATA%\Programs\Eclipse Adoptium\jdk-%%V*"
        "%LOCALAPPDATA%\Programs\Microsoft\jdk-%%V*"
    ) do (
      for /d %%X in (%%~D) do if exist "%%X\bin\javac.exe" if not defined JAVAC set "JAVAC=%%X\bin\javac.exe"
    )
  )
)
if not defined JAVAC goto nojavac
for %%I in ("%JAVAC%") do set "JDK_BIN=%%~dpI"
echo        javac = %JAVAC%
echo [1/6] javac = %JAVAC% >> BUILD-LOG.txt

REM ---------- 2) find jar ----------
echo [2/6] Finding jar.exe ...
set JAR=
if exist "%JDK_BIN%jar.exe" set "JAR=%JDK_BIN%jar.exe"
if not defined JAR (
  for %%D in (
      "C:\Program Files\Java\jdk*"
      "C:\Program Files\Eclipse Adoptium\jdk*"
      "C:\Program Files\Microsoft\jdk*"
  ) do (
    for /d %%X in (%%~D) do if exist "%%X\bin\jar.exe" if not defined JAR set "JAR=%%X\bin\jar.exe"
  )
)
set PACK=ps
if defined JAR (
  echo        jar   = %JAR%
  echo [2/6] jar   = %JAR% >> BUILD-LOG.txt
  set PACK=jar
) else (
  echo        jar.exe not found - using PowerShell zip fallback
  echo [2/6] jar.exe not found - using PowerShell fallback >> BUILD-LOG.txt
)

REM ---------- 3) verify input jar ----------
echo [3/6] Checking SMELLY-DIHH-CLIENT.jar ...
set "SRCJAR=%CD%\SMELLY-DIHH-CLIENT.jar"
if not exist "%SRCJAR%" goto nojar
echo        Found: %SRCJAR%
echo [3/6] Source jar OK >> BUILD-LOG.txt

REM ---------- 4) extract ----------
echo [4/6] Extracting ...
if exist build rmdir /s /q build
mkdir build\src
echo        Extracting to build\src ...
tar xf "%SRCJAR%" -C build\src
if errorlevel 1 (
  echo        tar failed, trying PowerShell Expand-Archive ...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Expand-Archive -LiteralPath '%SRCJAR%' -DestinationPath 'build\src' -Force"
  if errorlevel 1 goto extractfail
)
echo [4/6] Extracted OK >> BUILD-LOG.txt

REM ---------- 5) compile ----------
echo [5/6] Compiling .java sources ...
if exist build\src\_build rmdir /s /q build\src\_build
mkdir build\src\_build\classes
pushd build\src
dir /s /b *.java > sources.txt
set /a COUNT=0
for /f %%N in ('type sources.txt ^| find /c /v ""') do set COUNT=%%N
echo        Found %COUNT% .java files. Compiling ...
"%JAVAC%" -encoding UTF-8 -source 8 -target 8 -d _build\classes @sources.txt
if errorlevel 1 ( popd & goto compilefail )
echo        Copying classes ...
xcopy /E /Y /Q _build\classes\* . >nul
popd
echo [5/6] Compiled OK >> BUILD-LOG.txt

REM ---------- 6) package ----------
echo [6/6] Packaging build\client-patched.jar ...
pushd build\src
if exist client-patched.jar del /q client-patched.jar
if "%PACK%"=="jar" (
  "%JAR%" cfm client-patched.jar META-INF\MANIFEST.MF .
  if errorlevel 1 set PACK=ps
)
if not "%PACK%"=="jar" (
  echo        Using PowerShell Compress-Archive ...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Compress-Archive -Path '*' -DestinationPath 'client-patched.zip' -Force"
  if errorlevel 1 ( popd & goto packfail )
  move /y client-patched.zip client-patched.jar >nul
)
popd
move /y build\src\client-patched.jar build\client-patched.jar >nul
echo [6/6] Packaged OK >> BUILD-LOG.txt

REM ---------- WIN ----------
color 0A
echo.
echo ====================================================
echo  BUILD SUCCESSFUL!
echo ====================================================
echo.
echo  Output: %CD%\build\client-patched.jar
echo.
echo  Drop client-patched.jar into your Minecraft mods\ folder, OR run:
echo    java -jar "%CD%\build\client-patched.jar"
echo.
echo  Press RIGHT_SHIFT in-game to open the ClickGUI.
echo.
echo  Log: %CD%\BUILD-LOG.txt
echo.
pause
exit /b 0

:nojavac
color 0C
echo.
echo ******************************************************
echo  ERROR: javac.exe was not found on your PC.
echo.
echo  The PATH shim at
echo    C:\Program Files\Common Files\Oracle\Java\javapath\
echo  points to a shortcut but the full JDK is missing.
echo.
echo  INSTALL the real JDK 21 (not the JRE):
echo    1. Open: https://adoptium.net/temurin/releases/?version=21^&os=windows^&arch=x64^&package=jdk
echo    2. Download the .msi, run it, click Next/Next/Finish.
echo    3. Close this window and double-click ONE-CLICK.cmd again.
echo ******************************************************
echo.
pause
exit /b 1

:nojar
color 0C
echo.
echo ******************************************************
echo  ERROR: SMELLY-DIHH-CLIENT.jar is not in this folder.
echo.
echo  You must EXTRACT the zip into a real folder first.
echo  DO NOT double-click ONE-CLICK.cmd from inside the zip!
echo.
echo  Right-click SMELLY-DIHH-CLIENT.zip -^> Extract All...
echo  Then open the extracted folder and double-click ONE-CLICK.cmd there.
echo.
echo  Current folder: %CD%
echo  Files here:
dir /b
echo ******************************************************
echo.
pause
exit /b 1

:extractfail
popd
color 0C
echo.
echo  ERROR: Could not extract SMELLY-DIHH-CLIENT.jar.
echo  The file might be corrupted or blocked by antivirus.
echo  Try re-downloading the zip.
echo.
pause
exit /b 1

:compilefail
popd
color 0C
echo.
echo ******************************************************
echo  COMPILATION FAILED.
echo  Scroll up and read the red javac error messages.
echo  Take a screenshot of this window and send it to me.
echo ******************************************************
echo.
pause
exit /b 1

:packfail
popd
color 0C
echo.
echo  ERROR: Could not create the jar.
echo.
pause
exit /b 1
