GCC_FLAGS = -g++ -g -Wall -pedantic -std=c++2a
SRC = ./src
SRC_LOGIC = ./src/game_logic
BIN = ./bin

build:
	${GCC_FLAGS} ${SRC}/main.cpp ${SRC_LOGIC}/*.cpp -o ${BIN}/app.out

run:
	${GCC_FLAGS} ${SRC}/main.cpp ${SRC_LOGIC}/*.cpp -o ${BIN}/app.exe
	${BIN}/app.exe

drun:
	docker run -it --rm -v ~/Desktop/snake-game:/snake-game a7a8afcc3dab

dbuild:
	docker build --tag hello_world ~/Desktop/snake-game

clean:
	rm -rf bin/*.out