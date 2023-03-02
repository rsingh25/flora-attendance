# Flora

create jar

```
mvnw -Pdev clean verify
```

Create DB

```
/opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P "password@123"
create database FloraAttendance;
go

```
