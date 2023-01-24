DELETE
FROM exercise;
DELETE
FROM round;
DELETE
FROM workout;
commit;

INSERT INTO exercise (id, title, note)
VALUES ('d7598f73-99e2-4ce9-b625-f25960688c64', 'Жим на горизонтальной скамье',
        'Направлен на пропорциональное развитие всех частей грудных мышц'),
       ('976107ac-61e4-4a77-b665-5a2bdc3d1bcc', 'Жим на скамье с наклоном вниз',
        'Если Вы хотите прокачать именно нижнюю часть грудных мышц, то этот вид жима лежа отлично Вам подойдет'),
       ('e5911a79-8b84-442b-b7b9-7d6599bb16cf', 'Жим на скамье с наклоном вверх', 'Верхняя часть грудных мышц и плечи'),
       ('36039f49-7e33-4143-a5c0-05633cb5c7d8', 'Приседания со штангой на груди',
        'Приседания со штангой на груди, позволяют сконцентрироваться на всех четырех головках квадрицепсов'),
       ('e87e85fb-3438-4bff-b275-fd81dbc425c1', 'Классические приседания со штангой',
        'Приседания со штангой на плечах'),
       ('63331d86-a2ba-4761-9274-d7443958d8e9', 'Приседания со штангой над головой',
        'В данном упражнении, кроме мышц ног, активно работают выпрямители позвоночника, трапеции и дельты'),
       ('c471b42c-c5ac-4a6f-be8c-24f24b631dfe', 'Подтягивания к перекладине',
        'Подтягивания перекладины к подбородку/груди'),
       ('316473c1-bd16-4314-afff-d2bc69357809', 'Упражнения на гребном тренажере', 'Гребем'),
       ('b4a994f8-e4a2-4f4d-8eef-e7912a6201b7', 'Упражнения на беговой дорожке', 'Бежим'),
       ('1cfc8855-c20d-4471-b00c-9a527e23299f', 'Подтягивания в тренажере сидя',
        'Тянем перекладину к груди/подбородку'),
       ('986d28cc-6749-4365-983e-ab18a6d86672', 'Прыжки на скакалке', 'Прыгаем'),
       ('83bdca22-8f10-48be-bff4-393ec1ebee50', 'Поднимание ног к перекладине в висе', 'Пресс'),
       ('08fdc82f-6620-40a9-b73b-7b0bb3439b45', 'Сгибание корпуса на наклонной скамье', 'Пресс'),
       ('5497105c-cb82-4233-aa89-1bd8c9bf38dc', 'Подъем корпуса лежа', 'Пресс'),
       ('84224b7a-d420-48b4-913a-2f86fc33b8e6', 'Отжимания', 'Обыкновенные отжимания'),
       ('07fd6bd9-9d9e-4d3d-b765-fb828177183e', 'Приседания', 'Обыкновенные приседания');

INSERT INTO round (id, workout_schema, title, note)
VALUES ('6398562a-f68c-4a3d-98b6-13fd5fede908',
        '{"workoutElements": [{"note": "Если Вы хотите прокачать именно нижнюю часть грудных мышц, то этот вид жима лежа отлично Вам подойдет", "type": "station", "unit": "MIN", "order": 0, "title": "", "weight": 50, "duration": 0, "exercise": {"id": "d7598f73-99e2-4ce9-b625-f25960688c64", "note": "Направлен на пропорциональное развитие всех частей грудных мышц", "title": "Жим на горизонтальной скамье"}, "repetitions": 12}, {"note": "rest between sets", "type": "rest", "unit": "MIN", "order": 0, "title": "", "duration": 1}, {"note": "Если Вы хотите прокачать именно нижнюю часть грудных мышц, то этот вид жима лежа отлично Вам подойдет", "type": "station", "unit": "MIN", "order": 0, "title": "", "weight": 60, "duration": 0, "exercise": {"id": "d7598f73-99e2-4ce9-b625-f25960688c64", "note": "Направлен на пропорциональное развитие всех частей грудных мышц", "title": "Жим на горизонтальной скамье"}, "repetitions": 12}, {"note": "rest between sets", "type": "rest", "unit": "MIN", "order": 0, "title": "", "duration": 2}, {"note": "Если Вы хотите прокачать именно нижнюю часть грудных мышц, то этот вид жима лежа отлично Вам подойдет", "type": "station", "unit": "MIN", "order": 0, "title": "", "weight": 70, "duration": 0, "exercise": {"id": "d7598f73-99e2-4ce9-b625-f25960688c64", "note": "Направлен на пропорциональное развитие всех частей грудных мышц", "title": "Жим на горизонтальной скамье"}, "repetitions": 12}, {"note": "rest between sets", "type": "rest", "unit": "MIN", "order": 0, "title": "", "duration": 3}, {"note": "Если Вы хотите прокачать именно нижнюю часть грудных мышц, то этот вид жима лежа отлично Вам подойдет", "type": "station", "unit": "MIN", "order": 0, "title": "", "weight": 80, "duration": 0, "exercise": {"id": "d7598f73-99e2-4ce9-b625-f25960688c64", "note": "Направлен на пропорциональное развитие всех частей грудных мышц", "title": "Жим на горизонтальной скамье"}, "repetitions": 12}, {"note": "rest between sets", "type": "rest", "unit": "MIN", "order": 0, "title": "", "duration": 4}, {"note": "Если Вы хотите прокачать именно нижнюю часть грудных мышц, то этот вид жима лежа отлично Вам подойдет", "type": "station", "unit": "MIN", "order": 0, "title": "", "weight": 90, "duration": 0, "exercise": {"id": "d7598f73-99e2-4ce9-b625-f25960688c64", "note": "Направлен на пропорциональное развитие всех частей грудных мышц", "title": "Жим на горизонтальной скамье"}, "repetitions": 10}, {"note": "rest between sets", "type": "rest", "unit": "MIN", "order": 0, "title": "", "duration": 5}, {"note": "Если Вы хотите прокачать именно нижнюю часть грудных мышц, то этот вид жима лежа отлично Вам подойдет", "type": "station", "unit": "MIN", "order": 0, "title": "", "weight": 80, "duration": 0, "exercise": {"id": "d7598f73-99e2-4ce9-b625-f25960688c64", "note": "Направлен на пропорциональное развитие всех частей грудных мышц", "title": "Жим на горизонтальной скамье"}, "repetitions": 10}]}',
        'Морячок Папай', 'Жмем по всякому'),
       ('e9a9defa-6af1-42fe-a52c-2cb52488a0f5',
        '{"workoutElements": [{"note": "Обыкновенные отжимания", "type": "station", "unit": "SEC", "order": 0, "title": "", "weight": 0, "duration": 40, "exercise": {"id": "84224b7a-d420-48b4-913a-2f86fc33b8e6", "note": "Обыкновенные отжимания", "title": "Отжимания "}, "repetitions": 0}, {"note": "rest between sets", "type": "rest", "unit": "SEC", "order": 0, "title": "", "duration": 20}, {"note": "Пресс", "type": "station", "unit": "SEC", "order": 0, "title": "", "weight": 0, "duration": 40, "exercise": {"id": "08fdc82f-6620-40a9-b73b-7b0bb3439b45", "note": "Пресс", "title": "Сгибание корпуса на наклонной скамье"}, "repetitions": 0}, {"note": "rest between sets", "type": "rest", "unit": "SEC", "order": 0, "title": "", "duration": 20}, {"note": "Обыкновенные приседания", "type": "station", "unit": "SEC", "order": 0, "title": "", "weight": 0, "duration": 40, "exercise": {"id": "07fd6bd9-9d9e-4d3d-b765-fb828177183e", "note": "Обыкновенные приседания", "title": "Присядания"}, "repetitions": 0}, {"note": "rest between sets", "type": "rest", "unit": "SEC", "order": 0, "title": "", "duration": 20}, {"note": "Пресс", "type": "station", "unit": "SEC", "order": 0, "title": "", "weight": 0, "duration": 40, "exercise": {"id": "83bdca22-8f10-48be-bff4-393ec1ebee50", "note": "Пресс", "title": "Поднимание ног к перекладине в висе"}, "repetitions": 0}, {"note": "rest between sets", "type": "rest", "unit": "SEC", "order": 0, "title": "", "duration": 20}, {"note": "Подтягивания перекладины к подбородку/груди", "type": "station", "unit": "SEC", "order": 0, "title": "", "weight": 0, "duration": 40, "exercise": {"id": "c471b42c-c5ac-4a6f-be8c-24f24b631dfe", "note": "Подтягивания перекладины к подбородку/груди", "title": "Подтягивания к перекладине"}, "repetitions": 0}, {"note": "rest between sets", "type": "rest", "unit": "SEC", "order": 0, "title": "", "duration": 20}, {"note": "Пресс", "type": "station", "unit": "SEC", "order": 0, "title": "", "weight": 0, "duration": 40, "exercise": {"id": "5497105c-cb82-4233-aa89-1bd8c9bf38dc", "note": "Пресс", "title": "Подъем корпуса лежа"}, "repetitions": 0}]}',
        'Бешеный лось', 'Круговая тренировка на выносливость');

