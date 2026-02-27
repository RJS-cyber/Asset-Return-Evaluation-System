#!/usr/bin/env bash

echo "Starting ARES..."

mvn spring-boot:run -pl backend &
open frontend/index.html &