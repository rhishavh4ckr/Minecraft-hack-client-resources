#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
mkdir -p out
echo "[ui] Compiling sources..."
javac -d out -encoding UTF-8 $(find src -name "*.java")
echo "[ui] Compilation OK -> ui_framework/out"
echo "[ui] Run preview: java -cp out net.hackclient.ui.preview.Preview"
