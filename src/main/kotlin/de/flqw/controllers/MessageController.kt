package de.flqw.controllers

import de.flqw.templates.index.render
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

data class Message(val id: Int, var message: String)

@Controller
class TestController {

    var nextId: Int
    val messages: MutableList<Message> = mutableListOf()

    init {
        for (i in 1..3) {
            messages.add(Message(i, """Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed commodo pellentesque
            commodo. Curabitur et mi nec quam imperdiet aliquam id eu velit. Donec ornare dictum diam a consectetur.
            Suspendisse non sagittis dolor. Curabitur sed accumsan ex. Phasellus pretium elit sit amet eros consectetur
            molestie. Proin id nisl nisi. Sed blandit varius fringilla. Vivamus ac iaculis nisl. Nam ac massa ut metus
            iaculis porttitor. Integer tincidunt justo eget sapien suscipit, ac mattis nunc ultricies. Donec non
            libero in risus tristique ultricies id vel tellus. Maecenas id auctor purus. Nam sed purus at quam
            tristique lobortis. Ut sed massa at metus suscipit gravida. Suspendisse mollis lacinia orci, id
            volutpat turpis sollicitudin ac."""))
        }
        nextId = messages.size + 1
    }

    @ResponseBody
    @GetMapping("/")
    fun get(): String = render("Todo", messages)

    @PostMapping("/")
    fun post(@RequestParam message: String): String {
        if (!message.isBlank()) {
            nextId += 1
            messages.add(Message(nextId, message))
        }
        return "redirect:/#item-$nextId"
    }

    @PostMapping("/delete")
    fun delete(@RequestParam id: Int, @RequestParam jumpTo: Int): String {
        messages.removeAll { it.id == id }
        return "redirect:/#item-$jumpTo"
    }

}