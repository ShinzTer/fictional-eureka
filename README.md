# fictional-eureka
Тема курсового проекта (Разработка REST-сервиса Организация банковой системы)
Описание схемы данных
User (id, fio, passport, phNum), Bank (id, name, cntOfMoney, cntEmpl), Transaction (id, cntOfMoney, bank, user)
Связи таблиц User 1—M Transaction, Bank 1—M Transaction
2 Эндпоинта:
    Вычисляемое поле (сумма долга пользователя банку)
@GetMapping("/duty/{userId}")
public ResponseEntity<String> getDutyByUserId(@PathVariable Long userId).
    Сложный метод с использованием данных из нескольких таблиц 
    (получаение пользователей у которых сумма задолженности больше или равно введенному значению)
@GetMapping("/usersWithBigDuty/{bankId}/{sum}")
public ResponseEntity<List<UserDTO>> getUsersWithDutyMoreThenSum(@PathVariable Long bankId, @PathVariable Long sum)