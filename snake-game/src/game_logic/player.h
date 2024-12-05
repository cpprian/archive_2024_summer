#ifndef SNAKE_GAME_PLAYER_H
#define SNAKE_GAME_PLAYER_H

#include <iostream>
#include <memory>

class Player {
private:
    std::unique_ptr<int> score;

public:
    Player(): score{std::make_unique<int>(0)} {}

public:
    int getScore();
    void setScore(const int& s);
};


#endif //SNAKE_GAME_PLAYER_H
