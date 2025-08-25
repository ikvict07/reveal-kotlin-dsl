package com.jikvict.slides

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import java.io.File

sealed class SlideElement {
    data class Single(val slide: SlideBuilder) : SlideElement()
    data class Vertical(val slides: List<SlideBuilder>) : SlideElement()
    
    fun render(container: DIV) {
        when (this) {
            is Single -> slide.render(container)
            is Vertical -> {
                container.section {
                    slides.forEach { slide ->
                        slide.render(this)
                    }
                }
            }
        }
    }
}

class PresentationBuilder {
    private val slides = mutableListOf<SlideElement>()
    private var config = PresentationConfig()

    fun config(init: PresentationConfig.() -> Unit) {
        config.init()
    }

    fun slide(init: SlideBuilder.() -> Unit) {
        val slide = SlideBuilder()
        slide.init()
        slides.add(SlideElement.Single(slide))
    }

    fun verticalSlides(init: VerticalSlidesBuilder.() -> Unit) {
        val verticalSlides = VerticalSlidesBuilder()
        verticalSlides.init()
        slides.add(SlideElement.Vertical(verticalSlides.slides))
    }

    fun generateHtml(): String {
        return buildString {
            append("<!doctype html>\n")
            appendHTML().html {
                lang = "en"
                head {
                    meta(charset = "utf-8")
                    title(config.title)
                    meta(name = "description", content = config.description)
                    meta(name = "author", content = config.author)
                    meta(name = "apple-mobile-web-app-capable", content = "yes")
                    meta(name = "apple-mobile-web-app-status-bar-style", content = "black-translucent")
                    meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
                    link(rel = "stylesheet", href = "dist/reset.css")
                    link(rel = "stylesheet", href = "dist/reveal.css")
                    link(rel = "stylesheet", href = "dist/theme/jetbrains.css") {
                        id = "theme"
                    }
                    link(rel = "stylesheet", href = "plugin/highlight/monokai.css")
                    // Ensure content fits the screen without internal scrollbars; responsive typography
                    if (config.mathEnabled) {
                        // Math plugin is loaded below with other plugins; keeping head clean
                    }
                }
                body {
                    div("reveal") {
                        div("slides") {
                            slides.forEach { slide ->
                                slide.render(this)
                            }
                        }
                    }
                    script { src = "dist/reveal.js" }
                    script { src = "plugin/zoom/zoom.js" }
                    script { src = "plugin/notes/notes.js" }
                    script { src = "plugin/search/search.js" }
                    script { src = "plugin/markdown/markdown.js" }
                    script { src = "plugin/highlight/highlight.js" }
                    if (config.mathEnabled) {
                        script { src = "plugin/math/math.js" }
                    }
                    script {
                        unsafe {
                            +"""
                            Reveal.initialize({
                                hash: true,
                                controls: ${config.controls},
                                progress: ${config.progress},
                                center: ${config.center},
                                transition: '${config.transition.value}',
                                plugins: [ RevealZoom, RevealNotes, RevealSearch, RevealMarkdown, RevealHighlight${if (config.mathEnabled) ", RevealMath" else ""} ]
                            });
                            """.trimIndent()
                        }
                    }
                }
            }
        }
    }

    fun saveToFile(filename: String = "reveal.js/index.html") {
        File(filename).writeText(generateHtml())
        println("✅ Presentation saved to: $filename")
    }
}

data class PresentationConfig(
    var title: String = "RevealJS Kotlin DSL",
    var description: String = "Type-safe presentation framework",
    var author: String = "JikvictSlides",
    var controls: Boolean = true,
    var progress: Boolean = true,
    var center: Boolean = true,
    var transition: Transition = Transition.SLIDE,
    var mathEnabled: Boolean = true
)

enum class Transition(val value: String) {
    NONE("none"),
    FADE("fade"),
    SLIDE("slide"),
    CONVEX("convex"),
    CONCAVE("concave"),
    ZOOM("zoom")
}

/**
 * DSL function to create a presentation
 */
fun presentation(init: PresentationBuilder.() -> Unit): PresentationBuilder {
    val presentation = PresentationBuilder()
    presentation.init()
    return presentation
}