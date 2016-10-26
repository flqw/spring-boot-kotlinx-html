package de.flqw

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class Application {

    // Put it here so the IntelliJ Spring-Boot Run-Profile works (wouldn't with just a fun main)
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }

}
