package de.flqw.templates

import de.flqw.templates.BootstrapClass.*
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.util.UriComponentsBuilder
import java.io.StringWriter
import java.text.DecimalFormat
import kotlin.system.measureNanoTime

fun wrapper(pageTitle: String, block: DIV.() -> Unit) = StringWriter().appendHTML().html {
    head {
        title {
            +pageTitle
        }
        link(href = url("/css/bootstrap.min.css"), rel = "stylesheet")
    }
    body {
        div(classes = "$container") {
            val time = measureNanoTime { block() }
            footer {
                div(classes = "$text_center $text_muted") {
                    small {
                        +"Rendered in ${DecimalFormat("0.00").format(time / 1000000.0)} ms"
                    }
                }
            }
        }
    }
}.toString()


fun url(path: String): String {
    val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
    return UriComponentsBuilder.fromPath(request.contextPath).path(path).build().toUriString()
}