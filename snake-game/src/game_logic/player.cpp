#include "player.h"

int Player::getScore() {
    return *score;
}

void Player::setScore(const int& s) {
    *score = s;
}