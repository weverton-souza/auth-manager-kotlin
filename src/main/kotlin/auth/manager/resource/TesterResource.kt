package auth.manager.resource

import auth.manager.service.TesterService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/tests")
@Api(value = "Profile", tags = ["Tester"], description = "Test Resources")
class TesterResource(val testerService: TesterService) {

    @GetMapping("/test-admin")
    @PreAuthorize("hasRole('ADMIN_LEV0')")
    @ApiOperation(value = "Only admins can get phrase.", notes = "Only admins can get phrase.")
    fun adminAccess(): String = this.testerService.adminAccess()

    @GetMapping("/test-regular-user")
    @PreAuthorize("hasRole('USER_LEV0')")
    @ApiOperation(value = "Only regular users can get phrase.", notes = "Only regular users can get phrase.")
    fun userAccess(): String = this.testerService.userAccess()

    @GetMapping("/test-user-admin")
    @PreAuthorize("hasAnyRole('USER_LEV0', 'ADMIN_LEV0')")
    @ApiOperation(value = "Only regular users can get phrase.", notes = "Only regular users can get phrase.")
    fun userAndAdminAccess(): String = this.testerService.userAndAdminAccess()
}

