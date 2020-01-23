package auth.manager.resource

import auth.manager.dto.ProfileDTO
import auth.manager.security.configuration.ProfileService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/profiles")
class ProfileResource(val profileService: ProfileService) {

    @PostMapping
    fun save(@RequestBody profileDTO: ProfileDTO): ProfileDTO = this.profileService.saveOrUpdate(profileDTO)

    @PutMapping
    fun update(@RequestBody profileDTO: ProfileDTO): ProfileDTO = this.profileService.saveOrUpdate(profileDTO)
}
