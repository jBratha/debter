package net.ddns.jaronsky.debter.rest.controller

import net.ddns.jaronsky.debter.model.security.User
import net.ddns.jaronsky.debter.rest.model.RegisterUser
import net.ddns.jaronsky.debter.rest.service.UserService
import net.ddns.jaronsky.debter.security.JwtUser
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

/**
 * Created by Wojciech Jaronski
 *
 */
@RestController
@RequestMapping("/api/users")
class UserController (
        private var userService: UserService
){

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    fun listUserForAdmin(): List<User> {
        val u = userService.findAll()
//        u.forEach { user -> user.password = null }
        return u
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    fun listUser(): List<String> {
        return userService.findEnabledUsers()
    }

    @GetMapping("/name/{user}")
    fun userInfo(@PathVariable("user") username: String): JwtUser {
        return userService.findJwtUserByName(username)
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    fun currentUserInfo(): JwtUser {
        return userService.infoAboutYourself()
    }

    @PostMapping("/register")
    fun registerUser(@RequestBody user: RegisterUser) {
        userService.registerUser(user)
    }

}