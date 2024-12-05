#ifndef SNAKE_GAME_SNAKE_H
#define SNAKE_GAME_SNAKE_H

#include "body.h"
#include <memory>
#include <vector>

class Snake {
private:
    const int setX = 15;
    const int setY = 15;
    const int minSize = 5;

    int velocity;
    std::shared_ptr<std::vector<Body>> snake_body;

public:
    Snake():
        velocity{0},
        snake_body{std::make_shared<std::vector<Body>>()} {};

    void create_snake();
    std::shared_ptr<std::vector<Body>> get_snake_body();
    int getVelocity();
    void setVelocity(const int& v);
    void grow_snake();
    void shrink_snake();
    void accelerate_snake();
    void change_direction(Body::Point point);
};


#endif //SNAKE_GAME_SNAKE_H
