#!/bin/bash
set -e

cd src/main/python
. .venv/bin/activate

pip install -r requirements.txt
export FLASK_APP=app.py
python3 app.py