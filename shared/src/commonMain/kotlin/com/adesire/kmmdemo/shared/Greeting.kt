package com.adesire.kmmdemo.shared


class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }

    fun greetingInVariousLanguages(): List<String> {
        return arrayOf("Hola", "Ekaaro", "Ohayo", "Guten morgen").toList()
    }
}
