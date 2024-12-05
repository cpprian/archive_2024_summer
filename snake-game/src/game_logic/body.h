#ifndef SNAKE_GAME_BODY_H
#define SNAKE_GAME_BODY_H

#include <iostream>

struct Body {
    struct Point {
        int X;
        int Y;

        Point() = default;
        Point(int x, int y): X(x), Y(y) {}
    } point;
    std::string body;

    Body() = default;

    Body(Point p, std::string b):
        point(p.X, p.Y),
        body(std::move(b)) {}
};

#endif