package net.ddns.jaronsky.debter.rest.controller

import net.ddns.jaronsky.debter.model.UserDTO
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

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun listUser(): List<UserDTO> {
        return userService.findAll();
    }

    @GetMapping("/name/{user}")
    fun userInfo(@PathVariable("user") username: String): JwtUser {
        return userService.findUserByName(username);
    }

    @GetMapping("/me")
    @PreAuthorize("isFullyAuthenticated()")
    fun currentUserInfo(): JwtUser {
        return userService.infoAboutYourself();
    }

}