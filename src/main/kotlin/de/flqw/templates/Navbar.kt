package de.flqw.templates

import de.flqw.templates.BootstrapClass.*
import kotlinx.html.*
import org.springframework.util.AntPathMatcher

class Menu(
        val items: MutableList<MenuItem> = mutableListOf(),
        block: Menu.() -> Unit
) {
    init {
        block(this)
    }
}

fun Menu.link(title: String, link: String) {
    items.add(Link(title, link))
}

fun Menu.separator() {
    items.add(Separator())
}

fun Menu.dropDown(title: String, block: DropDown.() -> Unit) {
    val dropDown = DropDown(title)
    block(dropDown)
    items.add(dropDown)
}

fun DropDown.link(title: String, link: String) {
    items.add(Link(title, link))
}

fun DropDown.separator() {
    items.add(Separator())
}


interface MenuItem
interface SubMenuitem
class Separator : MenuItem, SubMenuitem

class Link(
        val title: String,
        val link: String
) : MenuItem, SubMenuitem

class DropDown(
        val title: String,
        val items: MutableList<SubMenuitem> = mutableListOf()
) : MenuItem

fun FlowContent.navbar(menu: Menu) {
    nav(classes = "$navbar $navbar_default $navbar_static_top") {
        div(classes = "$container_fluid") {
            div(classes = "$navbar_header") {
                button(classes = "$navbar_toggle collapsed") {
                    attributes["data-toggle"] = "collapse"
                    attributes["data-target"] = "#navbar"
                    attributes["aria-expanded"] = "false"
                    attributes["aria-controls"] = "navbar"
                    span(classes = "$sr_only") {
                        +"Toggle navigation"
                    }
                    span(classes = "$icon_bar")
                    span(classes = "$icon_bar")
                    span(classes = "$icon_bar")
                }
                a(classes = "$navbar_brand") {
                    href = "/"
                    +"Todo"
                }
            }
            div(classes = "$navbar_collapse $collapse") {
                id = "navbar"
                ul(classes = "$nav $navbar_nav") {
                    menu.items.map {
                        menuItem(it)
                    }
                }
            }
        }
    }
}

private fun UL.menuItem(it: MenuItem) {
    when (it) {
        is Separator -> separator()
        is Link -> link(it)
        is DropDown -> dropDown(it)
    }
}

private fun UL.dropDown(it: DropDown) {
    li(classes = "$dropdown ${if (it.items.any { it is Link && isLinkActive(it.link) }) "$active" else ""}") {
        a(classes = "$dropdown_toggle") {
            href = "#"
            role = "button"
            attributes["data-toggle"] = "dropdown"
            attributes["aria-haspopup"] = "true"
            attributes["aria-expanded"] = "false"
            +it.title
            span(classes = "$caret")
            ul(classes = "$dropdown_menu") {
                it.items.map {
                    when (it) {
                        is Link -> link(it)
                        is Separator -> separator()
                    }
                }
            }
        }
    }
}

private fun UL.link(it: Link) {
    li(classes = if (isLinkActive(it.link)) "$active" else null) {
        a {
            href = it.link
            +it.title
        }
    }
}

private fun UL.separator() {
    li(classes = "$divider") {
        role = "separator"
    }
}

fun isLinkActive(link: String): Boolean {
    return AntPathMatcher().match(link, currentRequest()?.requestURI)
}