#!/bin/bash

cp mem.circ part1-tests/addi-tests/circ_files
cp alu.circ part1-tests/addi-tests/circ_files
cp regfile.circ part1-tests/addi-tests/circ_files
cp cpu.circ part1-tests/addi-tests/circ_files
cd part1-tests/addi-tests/circ_files
./addi_test.py single
cd ../../../
