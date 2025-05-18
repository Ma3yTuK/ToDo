INSERT INTO place (id, place_id, longitude, latitude, name) VALUES
    (1, 'ChIJVQhKSMDP20YRyAEFmubm8MI', 27.561524, 53.902284, 'Independence Square'),
    (2, 'ChIJKfCTK5TP20YRwGcxtgr_3Gg', 27.576693, 53.900601, 'National Academic Bolshoi Opera and Ballet Theatre'),
    (3, 'ChIJA7W08rjP20YRnFSwrBR3nkw', 27.555985, 53.899405, 'Gorky Park'),
    (4, 'ChIJkWga-rvP20YRfmK6BaPfSzE', 27.586846, 53.917608, 'Victory Square'),
    (5, 'ChIJ_f6IVw7F20YRxMxWXvpBmBk', 27.550708, 53.858997, 'Waterpark Lebyazhy'),
    (6, 'ChIJhVj-nBvP20YR056otTqkGH4', 27.617913, 53.929801, 'Obschaga'),
    (7, 'ChIJS5c4Ha_P20YRE9vghXp8CT0', 27.594460, 53.912401, 'BSUIR 4');

INSERT INTO achievement (id, name) VALUES
    (1, 'U visited my place!'),
    (2, 'U visited bsuir!');

INSERT INTO achievement_part (id, name, place_id) VALUES
    (1, 'Visit my place!', 6),
    (2, 'Visit BSUIR!', 7);

INSERT INTO achievement_and_achievement_part (id, achievement_id, achievement_part_id) VALUES
    (1, 1, 1),
    (2, 2, 2);