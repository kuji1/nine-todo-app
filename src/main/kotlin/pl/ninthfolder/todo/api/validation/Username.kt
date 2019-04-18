package pl.ninthfolder.todo.api.validation

import pl.ninthfolder.todo.domain.user.UserRepository
import java.util.regex.Pattern
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [UsernameValidator::class])
@Target(allowedTargets = [FIELD])
@Retention(RUNTIME)
annotation class Username(
        val message: String = "Username is invalid.",
        val groups: Array<KClass<out Any>> = [],
        val payload: Array<KClass<out Payload>> = []
)

class UsernameValidator(
        private val userRepository: UserRepository
) : ConstraintValidator<Username, String> {

    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
        var isValid = true
        context.disableDefaultConstraintViolation()
        if (userRepository.existsByUsername(value)) {
            isValid = false
            context
                    .buildConstraintViolationWithTemplate("Username must be unique")
                    .addConstraintViolation()
        }
        if (!Pattern.compile("^[a-z-A-Z0-9]+\$").matcher(value).find()) {
            isValid = false
            context
                    .buildConstraintViolationWithTemplate("Username should contain only letters and numbers")
                    .addConstraintViolation()
        }
        return isValid
    }
}