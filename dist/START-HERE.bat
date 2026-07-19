@echo off
REM ================================================================
REM  SMELLY DIHH CLIENT - START HERE (double-click this)
REM  Keeps the window open even on errors.
REM ================================================================
setlocal
cd /d "%~dp0"
echo.
echo ########################################
echo #   SMELLY DIHH CLIENT  -  START HERE  #
echo ########################################
echo.
echo Working folder: %CD%
echo.
echo Contents of this folder:
dir /b
echo.
echo Running BUILD.BAT ...
echo ================================================================
call "%~dp0BUILD.BAT"
set RC=%ERRORLEVEL%
echo ================================================================
echo.
echo BUILD.BAT exited with code %RC%.
echo (Window kept open so you can read messages above.)
echo.
pause
endlocal
exit /b %RC%
