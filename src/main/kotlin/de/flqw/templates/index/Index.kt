package de.flqw.templates.index

import de.flqw.controllers.Message
import de.flqw.templates.BootstrapClass.*
import de.flqw.templates.url
import de.flqw.templates.wrapper
import kotlinx.html.*


fun render(title: String, messages: List<Message>) = wrapper(title) {
    div(classes = "$col_xs_12") {
        if (messages.isNotEmpty()) {
            h2 {
                +"Items"
            }
            messageList(messages)
        }

        addForm()
    }
}


fun FlowContent.addForm() {
    form {
        method = FormMethod.post
        action = url("/")
        div(classes = "$form_group") {
            label {
                for_ = "message"
                +"Message"
            }
            div(classes = "$input_group") {
                input(classes = "$form_control") {
                    type = InputType.text
                    name = "message"
                    autoFocus = true
                    id = "message"
                }
                span(classes = "$input_group_btn") {
                    button(classes = "$btn $btn_success") {
                        +"Add"
                    }
                }
            }
            span(classes = "$help_block") {
                +"Type in your message"
            }
        }
    }
}


fun FlowContent.messageList(messages: List<Message>) {
    ul(classes = "$list_group") {
        messages.map { messageItem(it) }
    }
}

var lastId = 0
fun UL.messageItem(message: Message) {
    li(classes = "$list_group_item") {
        a { id = "item-${message.id}" }
        form {
            style = "margin:0"
            method = FormMethod.post
            action = url("/delete?jumpTo=$lastId")
            input {
                type = InputType.hidden
                name = "id"
                value = message.id.toString()
            }
            span {
                +message.message
            }
            button(classes = "$close $pull_right") {
                style = "color:#999"
                unsafe {
                    +"&times;"
                }
            }
        }
        lastId = message.id
    }
}