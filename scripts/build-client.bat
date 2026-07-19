@echo off
REM =================================================================
REM  LionClient Redesigned - one-click Windows builder
REM  Run this from the LionClient folder you unzipped.
REM  Produces build\client-patched.jar ready to drop into mods/
REM =================================================================
setlocal ENABLEDELAYEDEXPANSION

echo [1/6] Locating JDK ...
set "JAVAC="
set "JAR="

REM 1) JAVA_HOME takes priority
if defined JAVA_HOME (
    if exist "%JAVA_HOME%\bin\javac.exe" set "JAVAC=%JAVA_HOME%\bin\javac.exe"
    if exist "%JAVA_HOME%\bin\jar.exe"   set "JAR=%JAVA_HOME%\bin\jar.exe"
)

REM 2) Look on PATH
if not defined JAVAC (
    for /f "delims=" %%J in ('where javac 2^>nul') do set "JAVAC=%%~fJ"
)

REM 3) If we found javac, try jar.exe next to it (works for a real JDK bin/)
if defined JAVAC if not defined JAR (
    for %%I in ("%JAVAC%") do set "JDK_BIN=%%~dpI"
    if exist "!JDK_BIN!jar.exe" set "JAR=!JDK_BIN!jar.exe"
)

REM 4) Oracle's PATH shim is in Common Files\Oracle\Java\javapath\ and does NOT
REM    contain jar.exe. Walk up from the shim target and search common JDK homes.
if not defined JAR (
    for %%D in (
        "C:\Program Files\Java"
        "C:\Program Files\Eclipse Adoptium"
        "C:\Program Files\Microsoft"
        "C:\Program Files\Zulu"
        "C:\Program Files\Amazon Corretto"
        "C:\Program Files (x86)\Java"
        "%LOCALAPPDATA%\Programs\Eclipse Adoptium"
        "%LOCALAPPDATA%\Programs\Microsoft"
    ) do (
        if exist %%~D (
            for /f "delims=" %%X in ('dir /b /s /a-d "%%~D\bin\jar.exe" 2^>nul') do (
                if not defined JAR set "JAR=%%~fX"
            )
        )
    )
)

REM 5) If jar.exe was found, re-derive JAVAC from that bin folder if needed
if defined JAR (
    for %%I in ("%JAR%") do set "JDK_BIN=%%~dpI"
    if not defined JAVAC if exist "!JDK_BIN!javac.exe" set "JAVAC=!JDK_BIN!javac.exe"
)

if not defined JAVAC (
    echo ERROR: javac.exe not found. Install a JDK (8 or newer) and try again,
    echo        or set the JAVA_HOME environment variable to your JDK folder.
    pause
    exit /b 1
)

echo     javac = %JAVAC%
if defined JAR (
    echo     jar   = %JAR%
    set "PACK_MODE=jar"
) else (
    echo     jar   = jar.exe not found - will use PowerShell Compress-Archive fallback
    set "PACK_MODE=zip"
)

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
"%JAVAC%" -encoding UTF-8 -source 8 -target 8 -d _build\classes @sources.txt
if errorlevel 1 (
    echo.
    echo ERROR: Compilation failed - see errors above.
    cd ..
    pause
    exit /b 1
)
xcopy /E /Y /Q _build\classes\* . >nul

echo [5/6] Packaging client-patched.jar ...
if /i "%PACK_MODE%"=="jar" (
    "%JAR%" cfm client-patched.jar META-INF\MANIFEST.MF .
) else (
    REM Fallback: use PowerShell's built-in zip. A .jar IS a .zip with a
    REM manifest, so this is accepted by Forge/Fabric/java -jar.
    if exist client-patched.jar del /q client-patched.jar
    powershell -NoProfile -Command ^
      "Compress-Archive -Path '*' -DestinationPath 'client-patched.zip' -Force" ^
      2>nul
    if errorlevel 1 (
        echo ERROR: PowerShell zip failed.
        cd ..
        pause
        exit /b 1
    )
    move /y client-patched.zip client-patched.jar >nul
)
if errorlevel 1 (
    echo ERROR: Packaging failed
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
