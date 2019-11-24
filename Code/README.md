# HOW TO LAUNCH GAME - INSTRUCTIONS

Be sure to have at least [Java JDK 12](https://www.oracle.com/technetwork/java/javase/downloads/index.html) installed and added to your [PATH environement variable](https://docs.oracle.com/en/java/javase/13/install/installation-jdk-microsoft-windows-platforms.html#GUID-C11500A9-252C-46FE-BB17-FC5A9528EAEB).  

## Command-line version

### Unix systems (Linux/OSX/...)
#### Starting server:
1. Open your favorite terminal
2. Navigate to the Server/src folder
3. Type: ./ServerLauncher.sh -> enter 
4. Follow on-screen instructions 

#### Starting client:
1. Open your favorite terminal
2. Navigate to the Client/src folder
3. Type: ./ClientLauncher.sh -> enter 
4. Follow on-screen instructions 

### Windows systems 
#### Beforehand:
1. Be sure to have [git-bash](https://git-scm.com/downloads) installed
2. Add the the folowing system variable to enable full utf-8 support:
    * Variable :    JAVA_TOOL_OPTIONS
    * Value    :    -Dfile.encoding=UTF8
3. Open a Git Bash terminal and change the folowing options:
    * Text      -> Character set : UTF-8
    * Terminal  -> Type : VT100
4. Apply, save and quit terminal 

#### Starting server:
1. Navigate to the Server/src folder
2. Right-click -> Open Git Bash terminal here 
3. Type: ./ServerLauncher.sh -> enter 
4. Follow on-screen instructions 

#### Starting client:
1. Navigate to the Client/src folder
2. Right-click -> Open Git Bash terminal here 
3. Type: ./ClientLauncher.sh -> enter 
4. Follow on-screen instructions 


## GUI version
The GUI version is not yet available.