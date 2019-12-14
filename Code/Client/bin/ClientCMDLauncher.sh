#!/bin/bash

printf '\e[8;40;120t'
echo Starting client 
echo ---------------
javac client/*.java
java client/ClientCmd
