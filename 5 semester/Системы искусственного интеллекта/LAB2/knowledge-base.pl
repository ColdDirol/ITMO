% персонажи видеоигр
character(mario).
character(link).
character(masterchief).
character(geralt).
character(ellie).
character(kratos).
character(lara_croft).
character(samus).
character(sonic).
character(aloy).

% жанры видеоигр
genre(platformer).
genre(rpg).
genre(fps).
genre(action_adventure).
genre(stealth).
genre(survival).
genre(open_world).
genre(strategy).
genre(horror).

% игровые платформы
platform(nintendo_switch).
platform(playstation).
platform(xbox).
platform(pc).

% игры по персонажам
game(super_mario_odyssey, mario).
game(the_legend_of_zelda_breath_of_the_wild, link).
game(halo_infinite, masterchief).
game(the_witcher_3_wild_hunt, geralt).
game(the_last_of_us, ellie).
game(god_of_war, kratos).
game(tomb_raider, lara_croft).
game(metroid, samus).
game(sonic_mania, sonic).
game(horizon_zero_dawn, aloy).

% жанры игр
game_genre(super_mario_odyssey, platformer).
game_genre(the_legend_of_zelda_breath_of_the_wild, open_world).
game_genre(halo_infinite, fps).
game_genre(the_witcher_3_wild_hunt, rpg).
game_genre(the_last_of_us, survival).
game_genre(god_of_war, action_adventure).
game_genre(tomb_raider, action_adventure).
game_genre(metroid, platformer).
game_genre(sonic_mania, platformer).
game_genre(horizon_zero_dawn, open_world).

% игры по платформам
game_platform(super_mario_odyssey, nintendo_switch).
game_platform(the_legend_of_zelda_breath_of_the_wild, nintendo_switch).
game_platform(halo_infinite, xbox).
game_platform(the_witcher_3_wild_hunt, pc).
game_platform(the_last_of_us, playstation).
game_platform(god_of_war, playstation).
game_platform(tomb_raider, pc).
game_platform(metroid, nintendo_switch).
game_platform(sonic_mania, pc).
game_platform(horizon_zero_dawn, playstation).

% оценки игры
game_rating(super_mario_odyssey, 9.7).
game_rating(the_legend_of_zelda_breath_of_the_wild, 9.8).
game_rating(halo_infinite, 8.5).
game_rating(the_witcher_3_wild_hunt, 9.6).
game_rating(the_last_of_us, 9.5).
game_rating(god_of_war, 9.4).
game_rating(tomb_raider, 9.0).
game_rating(metroid, 9.3).
game_rating(sonic_mania, 8.9).
game_rating(horizon_zero_dawn, 9.2).



% правило для определения популярности игры (рейтинг выше 9.0 считается популярной)
popular_game(Game) :-
    game_rating(Game, Rating),
    Rating > 9.0.

% правило для поиска персонажей по игре
character_in_game(Game, Character) :-
    game(Game, Character).

% правило для поиска игр по жанру
game_by_genre(Game, Genre) :-
    game_genre(Game, Genre).

% правило для поиска игр по платформе
game_by_platform(Game, Platform) :-
    game_platform(Game, Platform).

% правило для поиска игр, которые являются экшенами на платформе PlayStation
playstation_action_game(Game) :-
    game_platform(Game, playstation),
    game_genre(Game, action_adventure).
