#!/bin/bash
cd ../..
echo "Making..."
make all

echo "Make complete... moving on to testing!"

export AGENT=randomAgentJava
export TRAINER=consoleTrainerJava
export RUNAGENT=agents/$AGENT/run.bash
export ENV=mountaincar

#check java random agent with java console trainer on MC
export AGENT=randomAgentJava
echo "load $Agent"
cd agents/$AGENT/
make
./run.bash &

export TRAINER=system/testing/consoleTrainerTesting
echo "Testing $AGENT on $TRAINER with env $ENV"
cd ../../$TRAINER
make
./run.bash $ENV

#check java random agent with java console trainer on Heli
export AGENT=randomAgentJava
echo "load $Agent"
cd ../../../agents/$AGENT/
make
./run.bash &

export ENV=helicopter
export TRAINER=system/testing/consoleTrainerTesting
echo "Testing $AGENT on $TRAINER with env $ENV"
cd ../../$TRAINER
make
./run.bash $ENV

#check java random agent with java console trainer on Tetris
export AGENT=randomAgentJava
echo "load $Agent"
cd ../../../agents/$AGENT/
make
./run.bash &

export ENV=tetris
export TRAINER=system/testing/consoleTrainerTesting
echo "Testing $AGENT on $TRAINER with env $ENV"
cd ../../$TRAINER
make
./run.bash $ENV

#check java tetris agent with java console trainer on Tetris
export AGENT=tetrisAgentJava
echo "load $Agent"
cd ../../../agents/$AGENT/
make
./run.bash &

export TRAINER=system/testing/consoleTrainerTesting
echo "Testing $AGENT on $TRAINER with env $ENV"
cd ../../$TRAINER
make
./run.bash $ENV

killall RL_glue
#check mountaincar with java console trainer
export AGENT=mountainCarAgentCPP
echo "load $Agent"
cd ../../../agents/$AGENT/
make
./run.bash &

echo "Testing $AGENT on $TRAINER"
cd ../../$TRAINER
make
./run.bash

#check mountaincar CPP agent with console CPP trainer 
echo "load $Agent"
cd ../../../agents/$AGENT/
make
./run.bash &

export TRAINER=consoleTrainerCPP
echo "Testing $AGENT on $TRAINER"
cd ../../trainers/$TRAINER
make
./run.bash

#check random java agent with CPP console trainer 
export AGENT=randomAgentJava
echo "load $Agent"
cd ../../agents/$AGENT/
make
./run.bash &

echo "Testing $AGENT on $TRAINER"
cd ../../trainers/$TRAINER
make
./run.bash

#check random java agent with java gui trainer
cd ../../agents/$AGENT/
make
./run.bash &

export TRAINER=guiTrainerJava
echo "Testing $AGENT on $TRAINER"
cd ../../trainers/$TRAINER
make
./run.bash