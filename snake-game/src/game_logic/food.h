#ifndef SNAKE_GAME_FOOD_H
#define SNAKE_GAME_FOOD_H

#include "body.h"
#include <iostream>
#include <memory>

class Food {
private:
    bool isEaten;
    std::shared_ptr<Body> foodPoint;

public:
    Food():
        isEaten{true},
        foodPoint{std::make_unique<Body>()} {
        foodPoint->body = "@";
    };

    bool getIsEaten();
    void setIsEaten(bool f);
    void put_food();
    std::shared_ptr<Body> get_food();
};


#endif //SNAKE_GAME_FOOD_H
