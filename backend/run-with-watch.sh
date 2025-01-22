#!/bin/sh

./gradlew -i bootRun  &

while true; do
  inotifywait -e modify,create,delete,move -r ./src/main/java/ && \
  ./gradlew -i assemble
done