package com.jikvict.slides.v2

import com.jikvict.slides.*

fun main() {
    val presentation = presentation {
        config {
            title = "RevealJS Kotlin DSL"
            description = "Type-Safe Presentation Framework with JetBrains Theme"
            author = "JikvictSlides"
            transition = Transition.FADE
            mathEnabled = true
        }

        slide {
            background("#000000")
            h1("RevealJS Kotlin DSL")
            h3("Type-Safe Presentation")
            p("By ikvict")
            notes("Welcome to the RevealJS Kotlin DSL demonstration. This showcases all major features with type-safe API.")
        }

        slide {
            background("#000000")
            h2("DSL Features")
            ul {
                +"Complete type-safe API with Kotlin DSL"
                +"Advanced code highlighting with line animation"
                +"Mathematical text rendering with MathJax"
                +"Vertical scroll support for sub-slides"
                +"GIF and video media support"
                +"JetBrains color scheme integration"
                +"Fragment animations and transitions"
                +"Column layouts and advanced positioning"
            }
        }

        slide {
            h2("Code Highlighting")
            code(
                language = "kotlin",
                highlight = (Lines(4) and Lines(8..10)) + Lines(15) + Lines(19..21),
                code =
                    //language=kotlin
                    """
                    fun main() {
                        println("Hello, World!")
                        
                        val sum = (1..10).reduce { acc, i -> acc + i }
                        println("Sum of 1 to 10 is ${'$'}sum")
                        
                        val items = listOf("Kotlin", "Java", "Python")
                        items.forEach { item ->
                            println("Item: ${'$'}item")
                        }
                        
                        // Demonstrating a simple class
                        class Person(val name: String, val age: Int) {
                            fun greet() {
                                println("Hello, my name is ${'$'}name " +
                                "and I'm ${'$'}age years old.")
                            }
                        }
                        
                        val person = Person("Alice", 30)
                        person.greet()
                    }
                """.trimIndent(),
            )
            notes("Code highlighting supports line-by-line reveals for better presentation flow.")
        }

        // Mathematical text support
        slide {
            h2("Mathematical Text Support")
            p("Inline math example:")
            math("E = mc^2", display = false)
            p("Display math with complex formulas:")
            math("""
                \begin{align}
                \nabla \times \vec{\mathbf{B}} -\, \frac1c\, \frac{\partial\vec{\mathbf{E}}}{\partial t} &= \frac{4\pi}{c}\vec{\mathbf{j}} \\
                \nabla \cdot \vec{\mathbf{E}} &= 4 \pi \rho \\
                \nabla \times \vec{\mathbf{E}}\, +\, \frac1c\, \frac{\partial\vec{\mathbf{B}}}{\partial t} &= \vec{\mathbf{0}} \\
                \nabla \cdot \vec{\mathbf{B}} &= 0 \\
                \vec{\mathbf{B}} \times \vec{\mathbf{E}} &= \vec{\mathbf{0}} \\
                \vec{\mathbf{H}} \times \vec{\mathbf{F}} = -\vec{\mathbf{G}} \\
                \end{align}
            """)
            notes("MathJax integration allows for both inline and display mathematical expressions.")
        }

        slide {
            h2("Fragment Animations")
            p("Content appears step by step:")
            fragment("This content fades in", FragmentAnimation.FADE_IN, 1)
            fragment("This content grows", FragmentAnimation.GROW, 2)
            fragment("This content shrinks", FragmentAnimation.SHRINK, 3)
            fragment("This highlights in red", FragmentAnimation.HIGHLIGHT_RED, 4)
            fragment("This fades up from bottom", FragmentAnimation.FADE_UP, 5)
            notes("Fragment animations help control the flow of information presentation.")
        }

        slide {
            h2("Media Support")
            p("Video support:")
            video(
                src = "https://static.slid.es/site/homepage/v1/homepage-video-editor.mp4",
                width = "400",
                height = "300"
            )
            notes("Full support for images, GIFs, and videos with customizable dimensions.")
        }

        slide {
            h2("Advanced Layouts")
            columns {
                column("50%") {
                    +"Left Column Content"
                    p("This demonstrates the column layout system.")
                    p("Content flows naturally within each column.")
                }
                column("50%") {
                    +"Right Column Content"
                    p("Columns can have custom widths.")
                    p("Perfect for side-by-side comparisons.")
                }
            }
            notes("Column layouts provide flexible content organization.")
        }

        verticalSlides {
            slide {
                h2("Vertical Navigation")
                p("This is the main slide")
                p("↓ Navigate down to see sub-slides")
                notes("Vertical slides create hierarchical navigation structure.")
            }

            slide {
                h3("Sub-slide 1")
                p("This is a vertical sub-slide")
                code("javascript", "console.log('Vertical slide content');")
                notes("First sub-slide with code example.")
            }

            slide {
                h3("Sub-slide 2")
                math("\\sum_{i=1}^{n} x_i = x_1 + x_2 + \\cdots + x_n")
                p("Mathematical content in vertical slides")
                notes("Second sub-slide demonstrating math in vertical navigation.")
            }
        }

        slide {
            backgroundGradient(135, "#3574F0", "#000000")
            h1("Thank You!")
            p("Built with ❤️ using Kotlin DSL")
        }
    }

    presentation.saveToFile()
    println("🎉 Demo presentation generated successfully!")
    println("📂 Open reveal.js/index.html in your browser to view the presentation")
    println("🚀 Run 'gradle serve' to start local development server")
}