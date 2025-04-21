# Лабораторная работа 1
Выполнено студентом: Gachayev Dmitrii I2302

Работа представляет собой Spring Boot приложение для управления библиотекой. Приложение поддерживает CRUD-операции с использованием REST API и взаимодействует с базой данных через JPA. API работает с DTO-объектами, а не напрямую с  entities.

---

## Описание приложения

Приложение реализовано согласно следующим условиям и содержит:

- **3 контроллера**
- **3 сервиса**
- **5 взаимосвязанных сущностей** (Author, Publisher, Book, Category, Library)

---

## Структура и взаимосвязи сущностей:

- **Author**
    - Один автор может написать несколько книг (One-to-Many с Book).

- **Publisher**
    - Один издатель может издать несколько книг (One-to-Many с Book).

- **Book**
    - Каждая книга принадлежит одному автору (Many-to-One с Author).
    - Каждая книга имеет одного издателя (Many-to-One с Publisher).
    - Каждая книга может принадлежать нескольким категориям (Many-to-Many с Category).

- **Category**
    - Одна категория может содержать несколько книг (Many-to-Many с Book).

- **Library (Библиотека)**
    - Содержит коллекцию книг (ElementCollection).

---

## CRUD:

Приложение предоставляет следующий REST API:

- **Authors**:
    - Создание автора
    - Получение списка авторов
    - Обновление информации об авторе
    - Удаление автора

- **Publishers**:
    - Создание издателя
    - Получение списка издателей
    - Обновление информации об издателе
    - Удаление издателя

- **Books**:
    - Создание книги
    - Получение списка книг
    - Получение детальной информации о книге
    - Обновление информации о книге
    - Удаление книги

- **Categories**:
    - Создание категории
    - Получение списка категорий
    - Обновление информации о категории
    - Удаление категории

- **Library**:
    - Получение списка книг библиотеки
    - Добавление и удаление книг из библиотеки

---

## Описание структуры:

1. **Сущности**:
- Это классы, которые представляют структуру таблиц в базе данных. Entity-классы описывают поля, типы данных, ограничения и связи между таблицами.
- С помощью аннотаций JPA (`@Entity`, `@Table`, `@Column`, `@Id`, и т.д.) Java-классы связываются с таблицами базы данных. JPA автоматически генерирует SQL-запросы и управляет сохранением и получением данных.

Пример:
```java
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;

    public Author() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
```
2. **DTO (Data Transfer Object)**:
- Это объекты для передачи данных между клиентом и сервером. Они обеспечивают слой абстракции, скрывая внутреннюю структуру сущностей.
- Они используются для того, чтобы клиент не взаимодействовал напрямую с сущностями. Сервисный слой преобразует entity в DTO и наоборот

Пример:
```java
public class AuthorDTO {
    private Long id;
    private String name;

    public AuthorDTO() {}

    public AuthorDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```
3. **Сервисы**
- Классы, которые содержат бизнес-логику приложения. Сервисы обрабатывают данные и управляют транзакциями.
- Сервисы принимают запросы от контроллеров, взаимодействуют с репозиториями (базой данных), выполняют бизнес-логику и возвращают результаты обратно контроллерам.

Пример:
```java
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDTO> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(author -> new AuthorDTO(author.getId(), author.getName()))
                .collect(Collectors.toList());
    }

    public AuthorDTO getById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return new AuthorDTO(author.getId(), author.getName());
    }

    public AuthorDTO create(AuthorDTO dto) {
        Author author = new Author();
        author.setName(dto.getName());
        Author saved = authorRepository.save(author);
        return new AuthorDTO(saved.getId(), saved.getName());
    }

    public AuthorDTO update(Long id, AuthorDTO dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        author.setName(dto.getName());
        Author updated = authorRepository.save(author);
        return new AuthorDTO(updated.getId(), updated.getName());
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
```

---

## Тестирование
1. Запускаю приложение через `LibraryApplication` и перехожу на `localhost:8080`
2. Для отправки HTTP запросов использую vs-code расширение `REST Client`

### Проверяю CRUD операции сервиса `AuthorService`:
1. `CREATE`:
```
POST http://localhost:8080/authors
Content-Type: application/json

{
  "name": "Author1 ",
  "id": "2"
}
```

2. `READ`:
```
GET http://localhost:8080/authors
Accept: application/json
```
3. `UPDATE`:
```
PUT http://localhost:8080/authors/2
Content-Type: application/json

{
  "name": "Author1234"
}
```
4. `DELETE`:
```
DELETE http://localhost:8080/authors/1
```

Весь функционал работает. Изменения также можно наблюдать на `http://localhost:8080/authors`

### Проверяю CRUD операции сервиса `CategoryService`:
1. `CREATE`:
```
POST http://localhost:8080/categories
Content-Type: application/json

{
  "name": "Technology"
}
```

2. `READ`:
```
GET http://localhost:8080/categories
Accept: application/json
```

3. `UPDATE`:
```
PUT http://localhost:8080/categories/1
Content-Type: application/json

{
  "name": "Programming"
}
```

4. `DELETE`:
```
DELETE http://localhost:8080/categories/1
```

Весь функционал работает. Изменения также можно наблюдать на `http://localhost:8080/categories`

### Проверяю CRUD операции сервиса `BookService`:
1. `CREATE` (для выполнения этого запроса `authorId`,`publisherId`,`categoryIds` уже должны быть в бд. Т.к у меня нет сервиса и контроллера под `Publisher` пришлось их создать):
```
POST http://localhost:8080/books
Content-Type: application/json

{
  "title": "Test Book",
  "authorId": 1,
  "publisherId": 1,
  "categoryIds": [1, 3]
}
```
2. `READ`:
```
GET http://localhost:8080/books
Accept: application/json
```
3. `UPDATE`:
```
PUT http://localhost:8080/books/1
Content-Type: application/json

{
  "title": "Test put request",
  "authorId": 1,
  "publisherId": 1,
  "categoryIds": [1,2]
}
```
4. `DELETE`:
```
DELETE http://localhost:8080/books/1
```
есь функционал работает. Изменения также можно наблюдать на `http://localhost:8080/books`