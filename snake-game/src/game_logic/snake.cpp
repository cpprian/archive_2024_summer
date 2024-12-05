#include "snake.h"

void Snake::create_snake() {
    for (int i = 0; i < minSize; i++) {
        if (i == 0) {
            snake_body->emplace_back(Body::Point{setX, setY}, "W");
        } else {
            snake_body->emplace_back(Body::Point{setX, setY+i}, "o");
        }
    }
}

std::shared_ptr<std::vector<Body>> Snake::get_snake_body() {
    return snake_body;
}

int Snake::getVelocity() {
    return velocity;
}

void Snake::setVelocity(const int& v) {
    velocity = v;
}

void Snake::grow_snake() {
    // copy last snake element with coordinates
    Body coord = snake_body->back();

    snake_body->emplace_back(Body::Point{coord.point.X, coord.point.Y+1}, coord.body);
}

// TODO: implement & food
void Snake::shrink_snake() {
    snake_body->pop_back();
}

void Snake::accelerate_snake() {
    int velocity = getVelocity() + 1;
    setVelocity(velocity);
}

void Snake::change_direction(Body::Point point) {
    Body coord = snake_body->front();
    coord.body = "o";
    Body temp;
    snake_body->front().point = point;

    for (long unsigned int i = 1; i < snake_body->size(); i++) {
        temp = coord;
        coord = snake_body->at(i);
        snake_body->at(i) = temp;
    }
}