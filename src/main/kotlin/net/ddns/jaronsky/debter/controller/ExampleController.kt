package net.ddns.jaronsky.debter.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Wojciech Jaronski
 *
 */

@RestController
class ExampleController {

    var coutner = 0

    @GetMapping("/test")
    public fun test(): String {
        return "Hello ${coutner++}  world!";
    }

}