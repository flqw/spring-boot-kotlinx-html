package de.flqw.templates

import de.flqw.templates.BootstrapClass.*
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.util.UriComponentsBuilder
import java.io.StringWriter
import java.text.DecimalFormat
import javax.servlet.http.HttpServletRequest
import kotlin.system.measureNanoTime

val menu = Menu {
    link("Home", "/")
    link("About", "/about")
    separator()
    dropDown("Dropdown") {
        link("Test 1", "/test1")
        link("Test 2", "/test2")
        separator()
        link("Test 3", "/test3")
    }
}

fun wrapper(pageTitle: String, block: DIV.() -> Unit) = StringWriter().appendHTML().html {
    head {
        title {
            +pageTitle
        }
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
        link(href = url("https://maxcdn.bootstrapcdn.com/bootswatch/3.3.7/flatly/bootstrap.min.css"), rel = "stylesheet")
        script(src = url("https://code.jquery.com/jquery-3.1.1.min.js"))
        script(src = url("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"))
    }
    body {
        val time = measureNanoTime {
            navbar(menu)
            div(classes = "$container_fluid") {
                block()
            }
        }
        footer {
            div(classes = "$text_center $text_muted") {
                small {
                    +"Rendered in ${DecimalFormat("0.00").format(time / 1000000.0)} ms"
                }
            }
        }
    }
}.toString()


fun url(path: String): String {
    return UriComponentsBuilder.fromPath(currentRequest()?.contextPath).path(path).build().toUriString()
}

fun currentRequest(): HttpServletRequest? {
    return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
}