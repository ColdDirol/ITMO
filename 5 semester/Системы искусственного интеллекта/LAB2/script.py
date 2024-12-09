from pyswip import Prolog

# Инициализация базы знаний Prolog
prolog = Prolog()
prolog.consult("knowledge-base.pl")  # Загрузка базы знаний

def humanize_name(name):
    """
    Преобразует строку в человекочитаемый вид (с заглавными буквами).
    """
    return " ".join(word.capitalize() for word in name.replace("_", " ").split())

def machinize_name(name):
    """
    Преобразует строку в формат first_second (строчные буквы с подчеркиваниями).
    """
    return "_".join(name.lower().split())

def get_user_preferences():
    """
    Сбор информации о предпочтениях пользователя через диалог.
    """
    print("Введите жанры, которые вам нравятся (через запятую):")
    genres = input("Пример: RPG, Action, Survival: ").strip().lower().split(", ")
    print("Введите платформы, которые вы предпочитаете (через запятую):")
    platforms = input("Пример: PlayStation, PC, Xbox: ").strip().lower().split(", ")
    return genres, platforms

def find_games_by_preferences(genres, platforms):
    """
    Поиск игр по жанрам и платформам.
    """
    recommendations = []
    for genre in genres:
        for platform in platforms:
            query = f"game_genre(Game, {machinize_name(genre)}), game_platform(Game, {machinize_name(platform)})"
            results = list(prolog.query(query))
            for result in results:
                recommendations.append(result["Game"])
    return recommendations

def find_top_games_on_platform(platform):
    """
    Поиск всех игр на заданной платформе.
    """
    query = f"game_platform(Game, {machinize_name(platform)})"
    results = list(prolog.query(query))
    return [result["Game"] for result in results]

def find_popular_games():
    """
    Поиск всех популярных игр.
    """
    query = "popular_game(Game)"
    results = list(prolog.query(query))
    return [result["Game"] for result in results]

def find_top_characters():
    """
    Поиск всех персонажей.
    """
    query = "character(X)"
    results = list(prolog.query(query))
    return [result["X"] for result in results]

def get_all_platforms():
    """
    Получение всех доступных платформ в системе.
    """
    query = "game_platform(_, Platform)"
    results = list(prolog.query(query))
    platforms = set(result["Platform"] for result in results)
    return platforms

def get_all_genres():
    """
    Получение всех доступных жанров в системе.
    """
    query = "game_genre(_, Genre)"
    results = list(prolog.query(query))
    genres = set(result["Genre"] for result in results)
    return genres

def main():
    """
    Основная функция программы.
    """
    print("Добро пожаловать в рекомендательную систему для выбора видеоигр!")
    print("Выберите действие:")
    print("1. Найти игру по предпочтениям")
    print("2. Топ игр на платформе")
    print("3. Показать популярные игры")
    print("4. Топ персонажей")
    print("5. Вывод всех доступных платформ")
    print("6. Вывод всех доступных жанров")

    action = input("Введите номер действия (1-6): ").strip()

    if action == "1":
        genres, platforms = get_user_preferences()
        recommendations = find_games_by_preferences(genres, platforms)
        if not recommendations:
            print("К сожалению, мы не нашли игр по вашим предпочтениям.")
        else:
            print("\nНайдены игры по вашим предпочтениям:")
            for game in recommendations:
                humanized_game = humanize_name(game)
                print(f" - {humanized_game}")

    elif action == "2":
        platform = input("Введите платформу для поиска (например, playstation): ").strip().lower()
        top_games = find_top_games_on_platform(platform)
        if not top_games:
            print(f"Не найдены игры для платформы {humanize_name(platform)}.")
        else:
            print(f"\nТоп игр на платформе {humanize_name(platform)}:")
            for game in top_games:
                humanized_game = humanize_name(game)
                print(f" - {humanized_game}")

    elif action == "3":
        popular_games = find_popular_games()
        if not popular_games:
            print("Нет популярных игр.")
        else:
            print("\nПопулярные игры:")
            for game in popular_games:
                humanized_game = humanize_name(game)
                print(f" - {humanized_game}")

    elif action == "4":
        top_characters = find_top_characters()
        if not top_characters:
            print("Нет персонажей.")
        else:
            print("\nТоп персонажей:")
            for character in top_characters:
                print(f" - {humanize_name(character)}")

    elif action == "5":
        platforms = get_all_platforms()
        if not platforms:
            print("Нет доступных платформ.")
        else:
            print("\nДоступные платформы:")
            for platform in platforms:
                print(f" - {humanize_name(platform)}")

    elif action == "6":
        genres = get_all_genres()
        if not genres:
            print("Нет доступных жанров.")
        else:
            print("\nДоступные жанры:")
            for genre in genres:
                print(f" - {humanize_name(genre)}")

    else:
        print("Неверный выбор. Пожалуйста, выберите номер от 1 до 6.")

if __name__ == "__main__":
    main()
