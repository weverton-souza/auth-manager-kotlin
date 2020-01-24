package auth.manager.resource

import auth.manager.domain.Profile
import auth.manager.dto.ProfileDTO
import auth.manager.dto.UserLogin
import auth.manager.generics.Response
import auth.manager.security.configuration.UserService
import auth.manager.service.ProfileService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Authorization
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@CrossOrigin
@RequestMapping("/profiles")
class ProfileResource(val profileService: ProfileService) {

    @PostMapping
    fun save(@RequestBody profileDTO: ProfileDTO): ProfileDTO = this.profileService.saveOrUpdate(profileDTO)

    @PutMapping
    fun update(@RequestBody profileDTO: ProfileDTO): ProfileDTO = this.profileService.saveOrUpdate(profileDTO)

    @GetMapping

    fun findAll(): MutableList<Profile> = this.profileService.findAll()

}
