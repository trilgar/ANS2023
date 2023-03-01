$taskName = "Rss Retrieval task"
$action = New-ScheduledTaskAction -Execute "powershell.exe" -Argument "-ExecutionPolicy Bypass -File D:\desktop\ASN\labs\lab1\retrieve.ps1"
$trigger = New-ScheduledTaskTrigger -Daily -At "2:02 PM"
Register-ScheduledTask -TaskName $taskName -Action $action -Trigger $trigger -RunLevel Highest