@echo off
REM =========================================================================
REM  SMELLY DIHH CLIENT - ONE CLICK BUILD
REM  This is the EASIEST way. Double-click this file.
REM  It will:
REM    1. Create a build\ folder right next to this .bat
REM    2. Extract SMELLY-DIHH-CLIENT.jar into build\src\
REM    3. Find javac and jar on your PC automatically
REM    4. Compile the UI + bootstrap stubs
REM    5. Produce build\client-patched.jar (drop in mods\)
REM  If it fails, the window STAYS OPEN so you can read the error.
REM =========================================================================
@echo off
setlocal
cd /d "%~dp0"
color 0B
cls
echo.
echo  #####################################################
echo  #     ***   SMELLY DIHH CLIENT  -  BUILDER   ***    #
echo  #####################################################
echo.
echo  Working in: %CD%
echo.

REM -------- Locate javac.exe --------
echo  [1/6] Searching for javac.exe ...
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
        "%PROGRAMFILES%\Java\jdk-%%V*"
    ) do (
      for /d %%X in (%%~D) do if exist "%%X\bin\javac.exe" if not defined JAVAC set "JAVAC=%%X\bin\javac.exe"
    )
  )
)
if not defined JAVAC (
  color 0C
  echo.
  echo  *********************************************************************
  echo   ERROR : javac.exe NOT FOUND on your PC.
  echo.
  echo   You need the JDK (Java Development Kit), not just the JRE.
  echo   You said you have JDK 21 installed at
  echo     C:\Program Files\Common Files\Oracle\Java\javapath\
  echo   but that folder only has the SHORTCUT, not the full JDK.
  echo.
  echo   Please open this in your browser:
  echo     https://adoptium.net/temurin/releases/?version=21^&os=windows^&arch=x64^&package=jdk
  echo   Download the .msi, run it, click NEXT/NEXT/FINISH, then
  echo   double-click this ONE-CLICK.bat again.
  echo  *********************************************************************
  echo.
  pause
  exit /b 1
)
for %%I in ("%JAVAC%") do set "JDK_BIN=%%~dpI"
echo       OK  -^> %JAVAC%

REM -------- Locate jar.exe --------
echo  [2/6] Searching for jar.exe ...
set JAR=
if exist "%JDK_BIN%jar.exe" set "JAR=%JDK_BIN%jar.exe"
if not defined JAR (
  for %%D in (
      "C:\Program Files\Java\jdk*"
      "C:\Program Files\Eclipse Adoptium\jdk*"
      "C:\Program Files\Microsoft\jdk*"
      "C:\Program Files\Zulu\zulu*"
      "C:\Program Files\Amazon Corretto\jdk*"
  ) do (
    for /d %%X in (%%~D) do if exist "%%X\bin\jar.exe" if not defined JAR set "JAR=%%X\bin\jar.exe"
  )
)
if defined JAR (
  echo       OK  -^> %JAR%
  set "PACK=jar"
) else (
  echo       NOT FOUND - will use Windows PowerShell zip fallback
  set "PACK=ps"
)

REM -------- Verify source jar is here --------
echo  [3/6] Checking for SMELLY-DIHH-CLIENT.jar ...
set "SRCJAR=%CD%\SMELLY-DIHH-CLIENT.jar"
if not exist "%SRCJAR%" (
  color 0C
  echo.
  echo  *********************************************************************
  echo   ERROR : SMELLY-DIHH-CLIENT.jar is not next to this .bat file!
  echo.
  echo   Files I see in this folder (%CD%):
  dir /b "%CD%"
  echo.
  echo   Make sure you EXTRACTED THE ZIP into a folder (don't run the bat
  echo   from inside the zip), and that SMELLY-DIHH-CLIENT.jar is in the
  echo   same folder as ONE-CLICK.bat.
  echo  *********************************************************************
  echo.
  pause
  exit /b 1
)
echo       OK  -^> %SRCJAR%

REM -------- Extract --------
echo  [4/6] Extracting + compiling ...
if exist build rmdir /s /q build
mkdir build\src
echo       Extracting SMELLY-DIHH-CLIENT.jar into build\src ...
tar xf "%SRCJAR%" -C build\src
if errorlevel 1 (
  echo       tar failed - trying PowerShell Expand-Archive ...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Expand-Archive -LiteralPath '%SRCJAR%' -DestinationPath 'build\src' -Force"
  if errorlevel 1 (
    color 0C
    echo  ERROR: Could not extract the jar. Delete build\ and try again.
    pause
    exit /b 1
  )
)
echo       Collecting .java files ...
if exist build\src\_build rmdir /s /q build\src\_build
mkdir build\src\_build\classes
cd /d build\src
dir /s /b *.java > sources.txt
set /a COUNT=0
for /f %%N in ('type sources.txt ^| find /c /v ""') do set COUNT=%%N
echo       Found %COUNT% .java files. Compiling ...
echo.
"%JAVAC%" -encoding UTF-8 -source 8 -target 8 -d _build\classes @sources.txt
if errorlevel 1 (
  color 0C
  echo.
  echo  *********************************************************************
  echo   COMPILATION FAILED.
  echo.
  echo   Scroll up and read the RED error messages, then take a screenshot
  echo   of this window and send it. I'll fix the code.
  echo  *********************************************************************
  echo.
  cd /d "%~dp0"
  pause
  exit /b 1
)
echo.
echo       Copying compiled .class files into place ...
xcopy /E /Y /Q _build\classes\* . >nul

REM -------- Package --------
echo  [5/6] Packaging build\client-patched.jar ...
if exist client-patched.jar del /q client-patched.jar
if "%PACK%"=="jar" (
  "%JAR%" cfm client-patched.jar META-INF\MANIFEST.MF .
  if errorlevel 1 set "PACK=ps"
)
if not "%PACK%"=="jar" (
  echo       (using PowerShell Compress-Archive)
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Compress-Archive -Path '*' -DestinationPath 'client-patched.zip' -Force"
  if errorlevel 1 (
    color 0C
    echo  ERROR: zip failed.
    cd /d "%~dp0"
    pause
    exit /b 1
  )
  move /y client-patched.zip client-patched.jar >nul
)
cd /d "%~dp0"
move /y build\src\client-patched.jar build\client-patched.jar >nul

REM -------- Done! --------
echo  [6/6] Done.
echo.
color 0A
echo  #####################################################
echo  #     BUILD SUCCESSFUL!  (SMELLY DIHH CLIENT)       #
echo  #####################################################
echo.
echo   Your new client is at:
echo     %CD%\build\client-patched.jar
echo.
echo   Drop this file into your Minecraft mods\ folder, OR
echo   run it from a Command Prompt:
echo     java -jar "%CD%\build\client-patched.jar"
echo.
echo   Press RIGHT_SHIFT in-game to open the ClickGUI.
echo.
pause
endlocal
