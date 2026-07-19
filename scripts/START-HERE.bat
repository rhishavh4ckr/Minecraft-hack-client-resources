@echo off
REM ================================================================
REM  LionClient Redesigned - START-HERE
REM  Double-click THIS file. It opens a Command Prompt that stays
REM  open even if something errors, then runs BUILD.BAT.
REM ================================================================
echo.
echo ========================================
echo  LionClient Redesigned - builder
echo ========================================
echo.
echo Working in: %CD%
echo.
call "%~dp0BUILD.BAT"
echo.
echo ========================================
echo  BUILD.BAT finished (exit code %ERRORLEVEL%).
echo  Window kept open so you can read any errors.
echo  You can close this window now.
echo ========================================
echo.
pause
exit /b %ERRORLEVEL%
