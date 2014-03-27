Function ReadFile( filePath )
   Dim theFile

   'OpenTextFile args: <path>, 1 = ForReading
   'If you read an empty file, VBScript throws an error for some reason
   if (FileSystemObj.FileExists(filePath)) then
     Set theFile = FileSystemObj.GetFile(filePath)
     if (theFile.size > 0) then
       Set theFile = FileSystemObj.OpenTextFile(filePath, 1)
       ReadFile = theFile.ReadAll
     else
       ReadFile = ""
     end if
   else
     ReadFile = ""
   end if
End Function

Sub WriteFile( filePath, contents )
   Dim theFile

   'OpenTextFile args: <path>, 2 = ForWriting, True = create if not exist
   Set theFile = FileSystemObj.OpenTextFile(filePath, 2, True)
   theFile.Write contents
End Sub

Sub ReplaceInFile( filePath, lookForStr, replaceWithStr )
  Dim buffer

  buffer = ReadFile(filePath)
  if (buffer <> "") then
    buffer = Replace(buffer, lookForStr, replaceWithStr)
    WriteFile filePath, buffer
  end if
End Sub


Dim FileSystemObj, ConvertedDir, ConfigFile, XMLDir, WshShell
Dim customData, msiProperties, InstallDir, LogFile

on error resume next
Set FileSystemObj = CreateObject("Scripting.FileSystemObject")
if (Err = 0) then

  'Get the INSTALLDIR
  customData = Session.Property("CustomActionData")
  msiProperties = split(customData,";@;")
  InstallDir = msiProperties(0)

  'Set LogFile = FileSystemObj.OpenTextFile(InstallDir & "logs\InstallerScript.log", 8, true)

  'Remove all trailing backslashes to normalize
  do while (mid(InstallDir,Len(InstallDir),1) = "\")
    InstallDir = mid(InstallDir,1,Len(InstallDir)-1)
  loop
  ConvertedDir = Replace(InstallDir, "\", "/")
  ConfigDir = InstallDir & "\conf\"
  DistDir = ConfigDir & "dist\"

  If (LogFile) then
     LogFile.WriteLine "Backing up Config Files from " & ConfigDir
  End If



  'Back up the server.properties file


  'If (FileSystemObj.FileExists(ConfigDir & "metadata.xml")) then
  ''  If (LogFile) then
  ''    LogFile.WriteLine "-> metadata.xml file exists, copying"
  ''  End If
  ''  FileSystemObj.CopyFile ConfigDir & "metadata.xml", ConfigDir & "metadata.xml.backup", false
  'End If  

'Last End If
End If