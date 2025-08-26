# Kotlin DSL для создания reveal.js презентаций

Этот проект позволяет создавать красивые презентации reveal.js, используя удобный Kotlin DSL.

## Возможности

### ✨ Type-Safe API
- ✅ **Type-safe цвета**: Hex, RGB, RGBA, HSL, предопределенные цвета
- ✅ **Type-safe фоны**: Цвета, линейные и радиальные градиенты, изображения, видео
- ✅ **Type-safe переходы**: Enum-based переходы слайдов и фрагментов
- ✅ **Type-safe темы**: Предопределенные темы с автодополнением
- ✅ **Fragment анимации**: Полная поддержка всех типов анимаций reveal.js

### 🎨 Контент и стилизация
- ✅ Создание слайдов с заголовками (h1, h2, h3)
- ✅ Добавление текстового контента с индивидуальной стилизацией
- ✅ Улучшенные списки с индивидуальными настройками элементов
- ✅ Подсветка синтаксиса для кода (Kotlin, JavaScript и другие языки)
- ✅ Вставка изображений с дополнительными опциями стилизации
- ✅ Таблицы с настройкой внешнего вида
- ✅ Цитаты и блок-цитаты
- ✅ Многоколоночные макеты
- ✅ Разделители и пробелы

### ⚙️ Reveal.js функции
- ✅ Пользовательские фоны и цвета
- ✅ Переходы между слайдами
- ✅ Заметки докладчика
- ✅ Произвольный HTML контент
- ✅ Все темы reveal.js
- ✅ Полная конфигурация reveal.js параметров
- ✅ Auto-slide с настройкой времени
- ✅ Keyboard shortcuts и управление

## Быстрый старт

### 1. Сборка проекта

```bash
./gradlew build
```

### 2. Создание презентации

Отредактируйте файл `src/main/kotlin/com/jikvict/slides/Main.kt` или создайте свой собственный файл с презентацией:

```kotlin
fun main() {
    val myPresentation = presentation {
        presentationTitle = "Моя презентация"
        theme = "moon" // black, white, league, beige, sky, night, serif, simple, solarized, moon
        
        slide {
            h1("Заголовок презентации")
            text("Описание презентации")
            background("#2c3e50")
        }
        
        slide {
            h2("Список возможностей")
            list(
                "Пункт 1",
                "Пункт 2", 
                "Пункт 3"
            )
        }
        
        slide {
            h2("Код")
            code("""
                fun hello() {
                    println("Hello, World!")
                }
            """.trimIndent(), "kotlin")
        }
    }
    
    myPresentation.generateHTML()
}
```

### 3. Генерация презентации

```bash
./gradlew generatePresentation
```

### 4. Просмотр презентации

```bash
cd reveal.js
npm start
```

Или просто откройте файл `reveal.js/index.html` в браузере.

## API DSL

### Презентация

```kotlin
presentation {
    presentationTitle = "Название презентации"
    theme = "moon" // Тема reveal.js
    
    slide { /* содержимое слайда */ }
}
```

### Слайд

```kotlin
slide {
    // Заголовки
    h1("Заголовок первого уровня")
    h2("Заголовок второго уровня") 
    h3("Заголовок третьего уровня")
    
    // Текст
    text("Обычный текст")
    
    // Списки
    list("Пункт 1", "Пункт 2", "Пункт 3")
    list(listOf("Пункт A", "Пункт B"))
    
    // Код с подсветкой
    code("println('Hello')", "kotlin")
    code("console.log('Hello')", "javascript")
    
    // Изображения
    image("path/to/image.jpg", "Описание", width = 400)
    
    // HTML
    html("<strong>Жирный текст</strong>")
    
    // Reveal.js эффекты
    background("#ff0000") // Цвет фона
    backgroundImage("path/to/bg.jpg") // Фон-изображение
    transition("zoom") // Переход слайда
    
    // Заметки докладчика
    notes("Это заметка для докладчика")
}
```

### Доступные темы

- `black` (по умолчанию)
- `white`
- `league`
- `beige`
- `sky`
- `night`
- `serif`
- `simple`
- `solarized`
- `moon`
- `blood`
- `dracula` - темная тема на основе Dracula color scheme
- `jetbrains` - темная тема с фирменными цветами JetBrains

### Управление презентацией

- **Навигация**: стрелки клавиатуры
- **Заметки докладчика**: нажмите `s`
- **Обзор слайдов**: нажмите `Esc`
- **Пауза**: нажмите `b` или `.`
- **Полный экран**: нажмите `f`

## Gradle задачи

- `./gradlew build` - сборка проекта
- `./gradlew generatePresentation` - генерация HTML презентации
- `./gradlew serve` - генерация и запуск локального сервера (требует npm)

## Структура проекта

```
├── build.gradle.kts          # Конфигурация Gradle
├── settings.gradle.kts       # Настройки проекта
├── src/main/kotlin/com/jikvict/slides/
│   ├── Main.kt              # Пример презентации
│   ├── Presentation.kt      # Основной класс DSL
│   ├── Slide.kt             # Класс слайда
│   └── SlideContent.kt      # Типы контента
├── reveal.js/               # Библиотека reveal.js
└── README.md               # Документация
```

## Пример результата

После генерации получится полноценная HTML презентация с:
- Красивыми переходами
- Подсветкой синтаксиса
- Адаптивным дизайном
- Поддержкой заметок докладчика
- Навигацией клавиатурой

Готово! Теперь вы можете создавать презентации на Kotlin! 🎉