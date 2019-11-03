#!/bin/bash

# Just run this file and you can test your circ files!
# Make sure your files are in the directory above this one though!
# Credits to William Huang

cp alu.circ part1-tests/alu-tests/alu_tests
cd part1-tests/alu-tests
rm -rf student_output
mkdir student_output
./alu-test.py
cd ../../
