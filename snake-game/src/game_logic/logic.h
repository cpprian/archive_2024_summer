#ifndef SNAKE_GAME_LOGIC_H
#define SNAKE_GAME_LOGIC_H
#include <iostream>
#include <array>
#include <memory>
#include "body.h"
#include "snake.h"
#include "food.h"
#include "player.h"

enum direction {UP=1, LEFT, DOWN, RIGHT};

class Logic {
private:
    direction dirSnake;
    bool validateSnake;
    static const int BOARD_X = 50;
    static const int BOARD_Y = 25;
    std::unique_ptr<std::array<std::array<std::string, BOARD_X>, BOARD_Y>> board;

public:
    Logic():
        dirSnake{UP},
        validateSnake{true},
        board{std::make_unique<std::array<std::array<std::string, BOARD_X>, BOARD_Y>>()} {}

    void setup();
    void load_board(Snake* snake, Food* food);
    void display_game_state();

    bool validate_snake(bool v, Player* player, Snake* snake);
    void move_snake(Player* player, Snake* snake, Food* food);
    void is_snake_eat(Body::Point point, Food* food, Snake* snake, Player* player);
};


#endif //SNAKE_GAME_LOGIC_H
