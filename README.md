Написать набор сервисов для SOA WEB приложения.


Приложение должно реализовывать такие сервисы :


-[x] создание пользователя с (регистрация).
```- POST /register```

-[x] должен создаваться пользователь с именем и паролем.

-[x]  имя должно быть уникальным. 

-[x] сразу после создания текущий пользователь должен авторизоваться в том-же запросе.

-[x] Не зарегистрированный пользователь должен иметь возможность проверить доступность имени через сервис (валидации).
```- GET /checkname/{username}```

-[x] созданный в системе пользователь должен иметь возможность авторизоваться, передав в сервис имя и пароль.

-[x] Количество неудачных попыток авторизации - не должно превышать 10 за 1 час и сбрасываться при успешной авторизации.

Авторизованный пользователь должен иметь возможность

-[x] создавать /редактировать/удалить животных [Вид(из списка-справочника), дата рождения, пол, Кличка(уникальна)] .
```- GET/POST/PUT/DELETE /pets, /pets/{id}```
```- GET /pets_types```

-[x] получить список своих животных.
```- GET /pets/my```

-[x] получить детали любого животного по id.
```- GET /pets/{id}```

-[x] Все взаимодействие должно происходить с использованием JSON формата данных.

-[x] Все ошибки должны иметь номера и текстовую расшифровки.

-[x] ошибки, в случае возникновения, так-же должны передаваться в виде JSON объекта.

В качестве базы данных можно взять PostgreSQL, Mongo или любую InMemory базу (но, тогда jar-ик надо добавить в архив).

Рекомендуется использовать Spring и Hibernate (можно c JPA).

Фронт приложения не нужно