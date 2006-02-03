#!/bin/bash

targetFile=~/generic/testDataWithMoreEvents.sql

# some vars
attId=1000001
eventId=2
maxAttribs=2000000
k=0

# random char vars
MATRIX="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
LENGTH="8"

touch $targetFile

# das selbe wie for (eventId=1; eventId < maxAttribs; eventId++)
while [ $attId -le $maxAttribs ]
do
	PASS=""
	n=1
	while [ $n -le "$LENGTH" ]
	do
		PASS="$PASS${MATRIX:$(($RANDOM%${#MATRIX})):1}"
		n=$((n+1))
	done

	if [ $k -eq 10 ]; then
		k=0
		echo "INSERT INTO events (eventid) VALUES ("$eventId");"  >> $targetFile
		eventId=$((eventId+1))
	else
		k=$((k+1))
	fi

	echo "INSERT INTO eventattributes (eventid, attributeid, attributename, value) VALUES ("$eventId","$attId", '"$PASS"' , '"$PASS"');" >> $targetFile 
	
	attId=$((attId+1))
done
