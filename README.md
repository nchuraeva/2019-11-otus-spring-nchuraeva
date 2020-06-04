Task 10
----
Домашнее задание: Использовать MongoDB и spring-data для хранения информации
-----

Схема данных

##### users - пользователи

| name          | description                               |
|---------------|-------------------------------------------|
| id            | Индентификатор                            |
| login         | Логин                                     |
| password      | Пароль                                    |
| email         | Почта                                     |
| subscriptions | Подписки                                  |

##### subscriptions - подписки

|  name         | description                               |
|---------------|-------------------------------------------|
| id            | Индентификатор                            |
| startedAt     | Время начало                              |
| endedAt       | Время окончания                           |
| price         | Цена                                      |
| notifyTime    | Время уведомления о завершении подписки   |
| service       | Сервис                                    |

##### services - сервисы

|  name         | description                               |
|---------------|-------------------------------------------|
| id            | Индентификатор                            |
| name          | Название                                  |
| link          | Ссылка                                    |
| favicon       | Иконка                                    |
| category      | Категория                                 |

##### categories - категории

|  name         | description                               |
|---------------|-------------------------------------------|
| id            | Индентификатор                            |
| name          | Название                                  |

В базе создается коллекция users
