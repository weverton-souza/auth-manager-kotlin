package auth.manager.resource

import auth.manager.dto.ProfileDTO
import auth.manager.generics.Response
import auth.manager.service.ProfileService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/profiles")
@Api(value="Profile", tags=["Profile"], description="Profile Resources")
class ProfileResource(val profileService: ProfileService) {

    @PostMapping
    @ApiOperation(value = "Save a new profile.", notes = "Any user can save a new profile.")
    fun save(@RequestBody profileDTO: ProfileDTO): Response<ProfileDTO> = this.profileService.saveOrUpdate(profileDTO)

    @PutMapping
    @PreAuthorize("hasRole('ADMIN_LEV0')")
    @ApiOperation(value = "Update a existent profile.", notes = "Only admins can update a profile.")
    fun update(@RequestBody profileDTO: ProfileDTO): Response<ProfileDTO> = this.profileService.saveOrUpdate(profileDTO)

    @GetMapping
    @PreAuthorize("hasRole('ADMIN_LEV0')")
    @ApiOperation(value = "Find all profiles.", notes = "Only admins can get profiles.")
    fun findAll(): Response<MutableList<ProfileDTO>> = this.profileService.findAll()

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_LEV0')")
    @ApiOperation(value = "Delete a profile by id.", notes = "Only admins can delete a profile.")
    fun delete(@PathVariable id: String) = this.profileService.delete(id)
}
