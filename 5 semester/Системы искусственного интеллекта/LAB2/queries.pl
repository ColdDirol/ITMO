% поиск всех персонажей
character(X).

% поиск всех игр для PS
game_platform(Game, playstation).

% популярные игры (правило)
popular_game(Game).

% поиск игр RPG и вышли на PC
game_genre(Game, rpg),
game_platform(Game, pc).

% поиск игр жанра Action на PS (правило)
playstation_action_game(Game).

