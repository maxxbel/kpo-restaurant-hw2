Домашнее задание выполнено на последней версии Java,
использует PostgresSQL 16 для хранения данных.

Программа является консольным приложением.

Дополнительных заданий не делал. Сдал на 1 день позже дедлайна.

Настройка:
1.Зайдите в файл src/main/resources/application.properties и
установите расположение, логин и пароль вашей бд.

2.Затем вы можете зайти в Main и раскоментировать строку
initialize() при первом запуске. Эта функция создает таблицы
и заселяет их тестовыми данными, для удобства отладки и проверки.
Если вы сделали этот шаг, то существует пользователь
admin, с паролем 1111, с правами администратора.

Как пользоваться:
В этой программе я сделал интерактивный help.
Введите help для получения списка доступных команд,
введите help <команда> для получения более подробных пояснений.

Какими шаблонами пользовался:
Чтобы создать большое количество консольных команд я воспользовался
шаблоном Шаблонный метод, так как в каждой команде требовалась валидация
и вызов функций из классов-фасадов для базы данных.

Чтобы не писать sql запросы повсеместно, я сделал несколько классов-фасадов
для базы данных. По-хорошему, логику из них надо было положить в отдельные
классы, но увы.

Программа обрабатывает заказы в тред-пуле из 2 тредов, как бы эмулируя работу
2 поваров. Пока заказ обрабатывается, в него можно добавить блюдо. Выбирайте блюда
покороче, чтобы долго не тестировать.

admin может редактировать меню, добавлять и убирать блюда, менять количество блюд.
Менять другие поля нельзя, можно удалить блюдо и создать заново.

При составлении заказа количество блюд меняется соответствующе. При отмене заказа
блюда возвращаются в меню. Если заказ готов, то блюда уже в меню не возвращаются.

В бд хранится все, кроме неоплаченных заказов, так как это не требуется и в этом
немного смысла. Хранятся уже оплаченные заказы в укороченном виде.