#!/usr/bin/env bash

echo "Starting ARES..."

mvn spring-boot:run -pl backend &
xdg-open frontend/index.html &