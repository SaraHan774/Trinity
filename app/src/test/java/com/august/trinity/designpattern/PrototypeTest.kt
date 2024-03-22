package com.august.trinity.designpattern

import org.junit.Test

class PrototypeTest {

    @Test
    fun protoTest() {

    }
}

data class User(
    val name: String,
    val role: Role,
    val permissions: Set<String>,
    // val tasks: List<String>, - User class 바뀔때마다 createUser 함수를 수정해주어야 한다
) {
    fun hasPermission(permission: String) = permission in permissions
}

enum class Role {
    ADMIN, SUPER_ADMIN, REGULAR_USER
}

val allUsers = mutableListOf<User>()

fun createUser(name: String, role: Role) {
    for (u in allUsers) {
        if (u.role == role) {
            allUsers += User(name, role, u.permissions)
            return
        }
    }
}

fun createUser2(_name: String, role: Role) {
    for (u in allUsers) {
        if (u.role == role) {
            allUsers += u.copy(name = _name) // 이름만 갈아끼워 준다
            return
        }
    }
}