package com.example.ktorgradlemultimodules

import Hello
import org.springframework.stereotype.Service

@Service
class Runner {
    init {
        val hello = Hello("aka")
        hello.sayHello()
    }
}