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

  Set LogFile = FileSystemObj.OpenTextFile(InstallDir & "logs\InstallerScript.log", 8, true)

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



  'Back up the batch-event-parse-formats-system.xml file
  If (FileSystemObj.FileExists(ConfigDir & "batch-event-parse-formats-system.xml")) then
    If (LogFile) then
      LogFile.WriteLine ConfigDir & "-> batch-event-parse-formats-system.xml file exists, copying it"
    End If
    FileSystemObj.CopyFile ConfigDir & "batch-event-parse-formats-system.xml", ConfigDir & "batch-event-parse-formats-system.xml.backup", true
    FileSystemObj.DeleteFile ConfigDir & "batch-event-parse-formats-system.xml"
  End If  

  'Back up the event-release.xml file
  If (FileSystemObj.FileExists(ConfigDir & "event-release.xml")) then
    If (LogFile) then
      LogFile.WriteLine ConfigDir & "-> event-release.xml file exists, copying it"
    End If
    FileSystemObj.CopyFile ConfigDir & "event-release.xml", ConfigDir & "event-release.xml.backup", true
    FileSystemObj.DeleteFile ConfigDir & "event-release.xml"
  End If  

  'Back up the mua-core.xml file
  If (FileSystemObj.FileExists(ConfigDir & "mua-core.xml")) then
    If (LogFile) then
      LogFile.WriteLine ConfigDir & "mua-core.xml file exists, copying it"
    End If
    FileSystemObj.CopyFile ConfigDir & "mua-core.xml", ConfigDir & "mua-core.xml.backup", false
    FileSystemObj.DeleteFile ConfigDir & "mua-core.xml"
  End If  

  'Back up the statistical-processors.xml file
  If (FileSystemObj.FileExists(ConfigDir & "statistical-processors.xml")) then
    If (LogFile) then
      LogFile.WriteLine ConfigDir & "statistical-processors.xml file exists, copying it"
    End If
    FileSystemObj.CopyFile ConfigDir & "statistical-processors.xml", ConfigDir & "statistical-processors.xml.backup", true
    FileSystemObj.DeleteFile ConfigDir & "statistical-processors.xml"
  End If  

  'Back up the statistical-units-system.xml file
  If (FileSystemObj.FileExists(ConfigDir & "statistical-units-system.xml")) then
    If (LogFile) then
      LogFile.WriteLine ConfigDir & "statistical-units-system.xml file exists, copying it"
    End If
    FileSystemObj.CopyFile ConfigDir & "statistical-units-system.xml", ConfigDir & "statistical-units-system.xml.backup", true
    FileSystemObj.DeleteFile ConfigDir & "statistical-units-system.xml"
  End If  

  'Back up the web.xml file
  If (FileSystemObj.FileExists(ConfigDir & "web.xml")) then
    If (LogFile) then
      LogFile.WriteLine ConfigDir & "web.xml file exists, copying it"
    End If
    FileSystemObj.CopyFile ConfigDir & "web.xml", ConfigDir & "web.xml.backup", true
    FileSystemObj.DeleteFile ConfigDir & "web.xm"
  End If  

  If (LogFile) then
     LogFile.WriteLine "Finished backing up Config Files."
     LogFile.WriteLine " "
  End If

End If