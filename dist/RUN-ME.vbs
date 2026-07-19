' SMELLY DIHH CLIENT - launcher (double-click THIS if .cmd flashes closed)
Set WshShell = CreateObject("WScript.Shell")
Set fso = CreateObject("Scripting.FileSystemObject")
strFolder = fso.GetParentFolderName(WScript.ScriptFullName)
strCmd = "cmd.exe /k cd /d """ & strFolder & """ && ONE-CLICK.cmd"
WshShell.CurrentDirectory = strFolder
WshShell.Run strCmd, 1, False
