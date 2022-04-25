package com.michael.appverse.commons.utils

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.quanti.quase.loremkotlinum.Lorem

object RandomDataGenerator {

@Entity
data class User(val firstName: String, val lastName: String, @PrimaryKey val number: Int, val age: Int, val image:String, val email: String)



    private fun getName() = Lorem.name()
    private fun getNumber() = (9999..99999).random()
    private fun getImage() = "https://randomuser.me/api/portraits/lego/${(0..100).random()}.jpg"
    private fun getAge() = (18..60).random()
    fun getRandomWallPaperPage() = (1..31).random()
    private fun getGender() = (0..1).random()
    private fun getEmail() = Lorem.email()
    private fun getFirstName() = Lorem.firstName()
    private fun getLastName() = Lorem.lastName()
    fun getRandomString() = Lorem.word()


//    fun getUsers(count: Int): List<User> {
//        val users = mutableListOf<User>()
//        for (i in 0 until count) {
//            users.add(User(getName(), getNumber(), getImage()))
//        }
//        return users
//    }

    fun getUsers(count: Int) = (0..count).map { getUser() }


    fun getUser(): User = User(getFirstName(), getLastName(), getAge(), getNumber(), getImage(), getEmail())

}