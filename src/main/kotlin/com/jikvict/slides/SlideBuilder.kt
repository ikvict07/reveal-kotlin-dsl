package com.jikvict.slides

import kotlinx.html.*

sealed class SizeModifier {
    data class Fill(val paddingPx: Int = 16) : SizeModifier()
    data class Fraction(val fraction: Float, val paddingPx: Int = 16) : SizeModifier()
}

class SlideBuilder {
    private val content = mutableListOf<SlideContent>()
    private var background: Background? = null
    private var transition: Transition? = null
    private var notes: String? = null
    private var attributes = mutableMapOf<String, String>()
    private var nextSize: SizeModifier? = null

    fun fill(paddingPx: Int = 16) { nextSize = SizeModifier.Fill(paddingPx) }
    fun size(fraction: Float, paddingPx: Int = 16) { nextSize = SizeModifier.Fraction(fraction, paddingPx) }
    private fun consumeSize(): SizeModifier? = nextSize.also { nextSize = null }
    private fun sizeStyle(size: SizeModifier?): String? {
        return when (size) {
            null -> null
            is SizeModifier.Fill -> {
                val p = size.paddingPx
                "width: calc(100vw - ${'$'}{2*p}px); height: calc(100vh - ${'$'}{2*p}px); padding: ${'$'}p" + "px; box-sizing: border-box; margin: 0 auto;"
            }
            is SizeModifier.Fraction -> {
                val p = size.paddingPx
                val f = size.fraction
                "width: calc(100vw * ${'$'}f - ${'$'}{2*p}px); height: calc(100vh * ${'$'}f - ${'$'}{2*p}px); padding: ${'$'}p" + "px; box-sizing: border-box; margin: 0 auto;"
            }
        }
    }

    
    fun h1(text: String, classes: String? = null) {
        content.add(SlideContent.Header(1, text, classes, consumeSize()))
    }

    fun h2(text: String, classes: String? = null) {
        content.add(SlideContent.Header(2, text, classes, consumeSize()))
    }

    fun h3(text: String, classes: String? = null) {
        content.add(SlideContent.Header(3, text, classes, consumeSize()))
    }

    fun h4(text: String, classes: String? = null) {
        content.add(SlideContent.Header(4, text, classes, consumeSize()))
    }

    fun p(text: String, classes: String? = null) {
        content.add(SlideContent.Paragraph(text, classes, consumeSize()))
    }

    fun ul(init: ListBuilder.() -> Unit) {
        val list = ListBuilder()
        list.init()
        content.add(SlideContent.List(list.items, false, consumeSize()))
    }

    fun ol(init: ListBuilder.() -> Unit) {
        val list = ListBuilder()
        list.init()
        content.add(SlideContent.List(list.items, true, consumeSize()))
    }

    
    fun code(
        language: String,
        code: String,
        highlightLines: String? = null,
        showLineNumbers: Boolean = true,
        dataId: String? = null
    ) {
        content.add(SlideContent.Code(language, code, highlightLines, showLineNumbers, dataId, consumeSize()))
    }

    
    fun kt(code: String, highlightLines: String? = null, showLineNumbers: Boolean = true, dataId: String? = null) =
        code("kotlin", code, highlightLines, showLineNumbers, dataId)

    fun js(code: String, highlightLines: String? = null, showLineNumbers: Boolean = true, dataId: String? = null) =
        code("javascript", code, highlightLines, showLineNumbers, dataId)

    fun html(code: String, highlightLines: String? = null, showLineNumbers: Boolean = true, dataId: String? = null) =
        code("html", code, highlightLines, showLineNumbers, dataId)

    
    fun codeSteps(language: String, code: String, vararg steps: String, showLineNumbers: Boolean = true, dataId: String? = null) =
        code(language, code, steps.joinToString("|").ifBlank { null }, showLineNumbers, dataId)

    fun ktSteps(code: String, vararg steps: String, showLineNumbers: Boolean = true, dataId: String? = null) =
        codeSteps("kotlin", code, *steps, showLineNumbers = showLineNumbers, dataId = dataId)

    fun jsSteps(code: String, vararg steps: String, showLineNumbers: Boolean = true, dataId: String? = null) =
        codeSteps("javascript", code, *steps, showLineNumbers = showLineNumbers, dataId = dataId)

    fun htmlSteps(code: String, vararg steps: String, showLineNumbers: Boolean = true, dataId: String? = null) =
        codeSteps("html", code, *steps, showLineNumbers = showLineNumbers, dataId = dataId)

    
    fun code(
        language: String,
        code: String,
        highlight: HighlightSteps,
        showLineNumbers: Boolean = true,
        dataId: String? = null
    ) = code(language, code, highlightLines = highlight.toAttribute(), showLineNumbers = showLineNumbers, dataId = dataId)

    fun kt(code: String, highlight: HighlightSteps, showLineNumbers: Boolean = true, dataId: String? = null) =
        code("kotlin", code, highlight, showLineNumbers, dataId)

    fun js(code: String, highlight: HighlightSteps, showLineNumbers: Boolean = true, dataId: String? = null) =
        code("javascript", code, highlight, showLineNumbers, dataId)

    fun html(code: String, highlight: HighlightSteps, showLineNumbers: Boolean = true, dataId: String? = null) =
        code("html", code, highlight, showLineNumbers, dataId)

    
    fun math(formula: String, display: Boolean = true) {
        content.add(SlideContent.Math(formula, display, consumeSize()))
    }

    
    fun image(src: String, alt: String = "", width: String? = null, height: String? = null) {
        content.add(SlideContent.Image(src, alt, width, height, consumeSize()))
    }

    fun video(src: String, width: String = "400", height: String = "300", controls: Boolean = true) {
        content.add(SlideContent.Video(src, width, height, controls, consumeSize()))
    }

    fun gif(src: String, alt: String = "", width: String? = null, height: String? = null) {
        content.add(SlideContent.Image(src, alt, width, height, consumeSize()))
    }

    
    fun fragment(content: String, animation: FragmentAnimation = FragmentAnimation.FADE_IN, index: Int? = null) {
        this.content.add(SlideContent.Fragment(content, animation, index, consumeSize()))
    }

    
    fun columns(init: ColumnsBuilder.() -> Unit) {
        val columns = ColumnsBuilder()
        columns.init()
        content.add(SlideContent.Columns(columns.columns, consumeSize()))
    }

    
    fun background(color: String) {
        background = Background.Color(color)
    }

    fun backgroundGradient(gradient: String) {
        background = Background.Gradient(gradient)
    }

    
    fun backgroundGradient(angleDeg: Int, vararg colors: String) {
        background = Background.Gradient(linearGradient(angleDeg, *colors))
    }

    fun backgroundImage(url: String) {
        background = Background.Image(url)
    }

    fun backgroundVideo(url: String) {
        background = Background.Video(url)
    }

    
    fun transition(transition: Transition) {
        this.transition = transition
    }

    
    fun notes(text: String) {
        this.notes = text
    }

    
    fun attribute(name: String, value: String) {
        attributes[name] = value
    }

    fun render(container: FlowContent) {
        container.section {
            
            background?.let { bg ->
                when (bg) {
                    is Background.Color -> attributes["data-background"] = bg.color
                    is Background.Gradient -> attributes["data-background-gradient"] = bg.gradient
                    is Background.Image -> attributes["data-background"] = bg.url
                    is Background.Video -> attributes["data-background-video"] = bg.url
                }
            }

            
            transition?.let { 
                attributes["data-transition"] = it.value
            }

            
            attributes.forEach { (name, value) ->
                this.attributes[name] = value
            }

            
            content.forEach { item ->
                when (item) {
                    is SlideContent.Header -> {
                        val styleText = sizeStyle(item.size)
                        when (item.level) {
                            1 -> h1(item.classes) { styleText?.let { style = it }; +item.text }
                            2 -> h2(item.classes) { styleText?.let { style = it }; +item.text }
                            3 -> h3(item.classes) { styleText?.let { style = it }; +item.text }
                            4 -> h4(item.classes) { styleText?.let { style = it }; +item.text }
                        }
                    }
                    is SlideContent.Paragraph -> {
                        val styleText = sizeStyle(item.size)
                        p(item.classes) { styleText?.let { style = it }; +item.text }
                    }
                    is SlideContent.List -> {
                        val styleText = sizeStyle(item.size)
                        if (item.ordered) {
                            ol {
                                styleText?.let { style = it }
                                item.items.forEach { li { +it } }
                            }
                        } else {
                            ul {
                                styleText?.let { style = it }
                                item.items.forEach { li { +it } }
                            }
                        }
                    }
                    is SlideContent.Code -> {
                        pre {
                            sizeStyle(item.size)?.let { style = it }
                            
                            item.dataId?.let { attributes["data-id"] = it }

                            code(classes = "hljs ${item.language}") {
                                
                                attributes["data-trim"] = ""

                                
                                if (item.showLineNumbers) {
                                    if (item.highlightLines != null) {
                                        attributes["data-line-numbers"] = item.highlightLines
                                    } else {
                                        attributes["data-line-numbers"] = ""
                                    }
                                }

                                
                                
                                if (item.code.contains('<')) {
                                    script {
                                        type = "text/template"
                                        unsafe { +item.code }
                                    }
                                } else {
                                    +item.code
                                }
                            }
                        }
                    }
                    is SlideContent.Math -> {
                        if (item.display) {
                            div("math-display") { sizeStyle(item.size)?.let { style = it }; +("\\[${item.formula}\\]") }
                        } else {
                            span("math-inline") { sizeStyle(item.size)?.let { style = it }; +("\\(${item.formula}\\)") }
                        }
                    }
                    is SlideContent.Image -> {
                        img {
                            sizeStyle(item.size)?.let { style = it }
                            src = item.src
                            alt = item.alt
                            item.width?.let { width = it }
                            item.height?.let { height = it }
                        }
                    }
                    is SlideContent.Video -> {
                        video {
                            sizeStyle(item.size)?.let { style = it }
                            src = item.src
                            width = item.width
                            height = item.height
                            if (item.controls) {
                                controls = true
                            }
                        }
                    }
                    is SlideContent.Fragment -> {
                        p("fragment ${item.animation.cssClass}") {
                            sizeStyle(item.size)?.let { style = it }
                            item.index?.let { 
                                attributes["data-fragment-index"] = it.toString()
                            }
                            +item.content
                        }
                    }
                    is SlideContent.Columns -> {
                        div("r-hstack") {
                            sizeStyle(item.size)?.let { style = it }
                            item.columns.forEach { column ->
                                div {
                                    column.width?.let { style = "width: $it;" }
                                    column.content.forEach { columnContent ->
                                        p { +columnContent }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            
            notes?.let { noteText ->
                aside("notes") { +noteText }
            }
        }
    }
}


class LinesSpec internal constructor(internal val parts: MutableList<IntRange>) {
    infix fun and(other: LinesSpec): LinesSpec = LinesSpec((this.parts + other.parts).toMutableList())
    internal fun toSegment(): String = parts.joinToString(",") { r -> if (r.first == r.last) "${r.first}" else "${r.first}-${r.last}" }
}

class HighlightSteps internal constructor(internal val steps: MutableList<LinesSpec>) {
    fun toAttribute(): String = steps.joinToString(separator = "|", prefix = "|") { it.toSegment() }
}

fun Lines(vararg lines: Int): LinesSpec = LinesSpec(lines.map { it..it }.toMutableList())
fun Lines(range: IntRange): LinesSpec = LinesSpec(mutableListOf(range))

operator fun LinesSpec.plus(other: LinesSpec): HighlightSteps = HighlightSteps(mutableListOf(this, other))
operator fun HighlightSteps.plus(other: LinesSpec): HighlightSteps = this.apply { steps.add(other) }
operator fun HighlightSteps.plus(other: HighlightSteps): HighlightSteps = this.apply { steps.addAll(other.steps) }


fun linearGradient(angleDeg: Int, vararg colors: String): String =
    "linear-gradient(${angleDeg}deg, ${colors.joinToString(", ")})"

class VerticalSlidesBuilder {
    val slides = mutableListOf<SlideBuilder>()

    fun slide(init: SlideBuilder.() -> Unit) {
        val slide = SlideBuilder()
        slide.init()
        slides.add(slide)
    }
}

class ListBuilder {
    val items = mutableListOf<String>()

    fun item(text: String) {
        items.add(text)
    }

    operator fun String.unaryPlus() {
        items.add(this)
    }
}

class ColumnsBuilder {
    val columns = mutableListOf<Column>()

    fun column(width: String? = null, init: ColumnBuilder.() -> Unit) {
        val builder = ColumnBuilder()
        builder.init()
        columns.add(Column(builder.content, width))
    }
}

class ColumnBuilder {
    val content = mutableListOf<String>()

    fun p(text: String) {
        content.add(text)
    }

    operator fun String.unaryPlus() {
        content.add(this)
    }
}

data class Column(val content: List<String>, val width: String? = null)

sealed class SlideContent {
    data class Header(val level: Int, val text: String, val classes: String? = null, val size: SizeModifier? = null) : SlideContent()
    data class Paragraph(val text: String, val classes: String? = null, val size: SizeModifier? = null) : SlideContent()
    data class List(val items: kotlin.collections.List<String>, val ordered: Boolean, val size: SizeModifier? = null) : SlideContent()
    data class Code(val language: String, val code: String, val highlightLines: String? = null, val showLineNumbers: Boolean = true, val dataId: String? = null, val size: SizeModifier? = null) : SlideContent()
    data class Math(val formula: String, val display: Boolean = true, val size: SizeModifier? = null) : SlideContent()
    data class Image(val src: String, val alt: String, val width: String? = null, val height: String? = null, val size: SizeModifier? = null) : SlideContent()
    data class Video(val src: String, val width: String, val height: String, val controls: Boolean = true, val size: SizeModifier? = null) : SlideContent()
    data class Fragment(val content: String, val animation: FragmentAnimation, val index: Int? = null, val size: SizeModifier? = null) : SlideContent()
    data class Columns(val columns: kotlin.collections.List<Column>, val size: SizeModifier? = null) : SlideContent()
}

sealed class Background {
    data class Color(val color: String) : Background()
    data class Gradient(val gradient: String) : Background()
    data class Image(val url: String) : Background()
    data class Video(val url: String) : Background()
}

enum class FragmentAnimation(val cssClass: String) {
    FADE_IN(""),
    FADE_OUT("fade-out"),
    FADE_UP("fade-up"),
    FADE_DOWN("fade-down"),
    FADE_LEFT("fade-left"),
    FADE_RIGHT("fade-right"),
    GROW("grow"),
    SHRINK("shrink"),
    HIGHLIGHT_RED("highlight-red"),
    HIGHLIGHT_GREEN("highlight-green"),
    HIGHLIGHT_BLUE("highlight-blue")
}