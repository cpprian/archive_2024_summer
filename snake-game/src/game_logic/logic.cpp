#include <iomanip>
#include "logic.h"
#include <unistd.h>
#include <Windows.h>
#include <chrono>
#include <thread>

void Logic::load_board(Snake* snake, Food* food) {
    int check_frame = 1;
    int x_check;
    int y_check;

    for (auto& x: *board) {
        if (check_frame != 1 && check_frame != 25) {
            x.fill(" ");
            x.front() = "#";
            x.back() = "#";
        } else {
            x.fill("#");
        }

        check_frame++;
    }


    for (auto& x: *(snake->get_snake_body())) {
        x_check = x.point.X;
        y_check = x.point.Y;
        if (board->at(y_check).at(x_check) == "#"
            || board->at(y_check).at(x_check) == "o"
            || board->at(y_check).at(x_check) == "W")
        {
            validateSnake = false;
        }
        board->at(x.point.Y).at(x.point.X) = x.body;
    }

    std::shared_ptr<Body> pFood = food->get_food();
    while (true) {
        x_check = pFood->point.X;
        y_check = pFood->point.Y;
        if (board->at(y_check).at(x_check) == " ") {
            board->at(pFood->point.Y).at(pFood->point.X) = pFood->body;
            break;
        }
        pFood = food->get_food();
    }
}

void Logic::display_game_state() {
    std::system("cls");
    for (auto& x: *board) {
        std::cout << std::setw(20);
        for (auto& y: x) {
            std::cout << y;
        }
        std::cout << "\n";
    }
}

void Logic::move_snake(Player* player, Snake* snake, Food* food) {
    Body::Point p = {snake->get_snake_body()->front().point};
    if (GetAsyncKeyState((unsigned short)'A') & 0x8000) {
        p.X--;
        is_snake_eat(p, food, snake, player);
        snake->change_direction(p);
        dirSnake = LEFT;
    } else if (GetAsyncKeyState((unsigned short)'W') & 0x8000) {
        p.Y--;
        is_snake_eat(p, food, snake, player);
        snake->change_direction(p);
        dirSnake = UP;
    } else if (GetAsyncKeyState((unsigned short)'S') & 0x8000) {
        p.Y++;
        is_snake_eat(p, food, snake, player);
        snake->change_direction(p);
        dirSnake = DOWN;
    } else if (GetAsyncKeyState((unsigned short)'D') & 0x8000) {
        p.X++;
        is_snake_eat(p, food, snake, player);
        snake->change_direction(p);
        dirSnake = RIGHT;
    } else {
        switch(dirSnake) {
            case UP:
                p.Y--;
                is_snake_eat(p, food, snake, player);
                snake->change_direction(p);
                break;
            case DOWN:
                p.Y++;
                is_snake_eat(p, food, snake, player);
                snake->change_direction(p);
                break;
            case LEFT:
                p.X--;
                is_snake_eat(p, food, snake, player);
                snake->change_direction(p);
                break;
            case RIGHT:
                p.X++;
                is_snake_eat(p, food, snake, player);
                snake->change_direction(p);
                break;
        }
    }
}

bool Logic::validate_snake(bool v, Player* player, Snake* snake) {
    if (!v) {
        std::cout << std::setw(50) << "GAME OVER\n";
        std::cout << std::setw(50) << "You score: " << player->getScore() << "\n";
        std::cout << std::setw(50) << "The length of snake " << snake->get_snake_body()->size() << "\n";
        return false;
    }
    return true;
}

void Logic::is_snake_eat(Body::Point point, Food* food, Snake* snake, Player* player) {
    if (point.X == food->get_food()->point.X && point.Y == food->get_food()->point.Y) {
        food->setIsEaten(true);
        snake->grow_snake();
        int newScore = player->getScore();
        player->setScore(newScore+100);
    }
}

void Logic::setup() {
    auto snake = new Snake();
    auto food = new Food();
    auto player = new Player();

    snake->create_snake();
    while(validate_snake(validateSnake, player, snake)) {
        load_board(snake, food);
        display_game_state();
        move_snake(player, snake, food);
        std::this_thread::sleep_for(std::chrono::milliseconds(100));
    }

    delete player;
    delete snake;
    delete food;
}