package hu.kirdev.webshop.service

import hu.kirdev.webshop.authsch.ProfileResponse
import hu.kirdev.webshop.model.UserEntity
import hu.kirdev.webshop.repo.UserRepo
import org.springframework.stereotype.Service

@Service
open class UserService(
    private val userRepo: UserRepo
) {

    fun setUsername(id: Long, username: String) {
        userRepo.findById(id).ifPresent {
            it.minecraftUsername = username
            userRepo.save(it)
        }
    }

    fun getOrCreateUser(profile: ProfileResponse): Long {
        return userRepo.findByInternalId(profile.internalId)
            .map { it.id }
            .orElseGet {
                val userEntity = UserEntity(
                    internalId = profile.internalId,
                    money = 5000
                )
                userRepo.save(userEntity)
                return@orElseGet userEntity.id
            }
    }

    fun getUserById(id: Long): UserEntity {
        return userRepo.findById(id).orElseThrow()
    }

}