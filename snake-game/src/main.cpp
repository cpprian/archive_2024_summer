#include "game_logic/logic.h"

int main() {
    auto *hello = new Logic();
    hello->setup();

    delete hello;
}
