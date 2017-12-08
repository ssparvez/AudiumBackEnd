@ECHO OFF
REM This script automatically sets the target/classes/application.properties file to use the mysql3.cs.stonybrook.edu:3306 lions MySQL server

CD "%~dp0"
CD ..

ECHO # MySQL Connection setup>src/main/resources/application.properties
ECHO spring.jpa.hibernate.ddl-auto=none>>src/main/resources/application.properties
ECHO spring.jpa.properties.hibernate.dialect=io.audium.audiumbackend.config.AudiumMySQL5Dialect>>src/main/resources/application.properties
ECHO spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl>>src/main/resources/application.properties
ECHO spring.datasource.url=jdbc^:mysql^://mysql3.cs.stonybrook.edu^:3306/lions?verifyServerCertificate=false^&useSSL=false^&requireSSL=false>>src/main/resources/application.properties
ECHO #spring.datasource.url=jdbc^:mysql^://mysql2.cs.stonybrook.edu^:3306/lions?verifyServerCertificate=false^&useSSL=false^&requireSSL=false>>src/main/resources/application.properties
ECHO spring.datasource.username=lions>>src/main/resources/application.properties
ECHO spring.datasource.password=SmolkaDaGod>>src/main/resources/application.properties
ECHO #spring.datasource.url=jdbc:mysql://localhost:3306/cse308?verifyServerCertificate=false^&useSSL=false^&requireSSL=false>>src/main/resources/application.properties
ECHO #spring.datasource.url=jdbc:mysql://localhost:3306/cse308test?verifyServerCertificate=false^&useSSL=false^&requireSSL=false>>src/main/resources/application.properties
ECHO #spring.datasource.url=jdbc:mysql://localhost:3306/cse308test2?verifyServerCertificate=false^&useSSL=false^&requireSSL=false>>src/main/resources/application.properties
ECHO spring.jackson.serialization.fail-on-empty-beans=false>>src/main/resources/application.properties
ECHO spring.datasource.tomcat.max-active=10>>src/main/resources/application.properties
ECHO spring.datasource.tomcat.max-idle=10>>src/main/resources/application.properties
ECHO spring.datasource.tomcat.minEvictableIdleTimeMillis=30000>>src/main/resources/application.properties
ECHO logging.level.org.hibernate.SQL=DEBUG>>src/main/resources/application.properties



ECHO # MySQL Connection setup>target/classes/application.properties
ECHO spring.jpa.hibernate.ddl-auto=none>>target/classes/application.properties
ECHO spring.jpa.properties.hibernate.dialect=io.audium.audiumbackend.config.AudiumMySQL5Dialect>>target/classes/application.properties
ECHO spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl>>target/classes/application.properties
ECHO spring.datasource.url=jdbc^:mysql^://mysql3.cs.stonybrook.edu^:3306/lions?verifyServerCertificate=false^&useSSL=false^&requireSSL=false>>target/classes/application.properties
ECHO #spring.datasource.url=jdbc^:mysql^://mysql2.cs.stonybrook.edu^:3306/lions?verifyServerCertificate=false^&useSSL=false^&requireSSL=false>>target/classes/application.properties
ECHO spring.datasource.username=lions>>target/classes/application.properties
ECHO spring.datasource.password=SmolkaDaGod>>target/classes/application.properties
ECHO #spring.datasource.url=jdbc:mysql://localhost:3306/cse308?verifyServerCertificate=false^&useSSL=false^&requireSSL=false>>target/classes/application.properties
ECHO #spring.datasource.url=jdbc:mysql://localhost:3306/cse308test?verifyServerCertificate=false^&useSSL=false^&requireSSL=false>>target/classes/application.properties
ECHO #spring.datasource.url=jdbc:mysql://localhost:3306/cse308test2?verifyServerCertificate=false^&useSSL=false^&requireSSL=false>>target/classes/application.properties
ECHO spring.jackson.serialization.fail-on-empty-beans=false>>target/classes/application.properties
ECHO spring.datasource.tomcat.max-active=10>>target/classes/application.properties
ECHO spring.datasource.tomcat.max-idle=10>>target/classes/application.properties
ECHO spring.datasource.tomcat.minEvictableIdleTimeMillis=30000>>target/classes/application.properties
ECHO logging.level.org.hibernate.SQL=DEBUG>>target/classes/application.properties



REM ECHO mysql -u root -p cse308
REM ECHO:
REM mysql -u root -p"%local_db_pw%" cse308

IF errorlevel 9009 (
   ECHO:
   ECHO ERROR: Failed to launch MySQL.
   ECHO:
   ECHO This script requires MySQL to be installed on your machine and added to your system path variable.
   ECHO:
   ECHO Download MySQL here:
   ECHO https://dev.mysql.com/downloads/
   ECHO:
   PAUSE
   GOTO end_of_file
)

IF errorlevel 1 (
   CD rsrc
   DEL /f /q rsrc/local_db_password.txt 2> nul > nul
   CD ..
   ECHO:
   ECHO ERROR: Failed to connect to local MySQL server.
   ECHO:
   ECHO Make sure you have the right password.
   ECHO:
   PAUSE
   GOTO end_of_file
)

PAUSE

:end_of_file

