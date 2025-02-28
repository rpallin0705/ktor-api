package com.data.persistence.repository

import com.data.persistence.models.restaurant.RestaurantDao
import com.data.persistence.models.restaurant.RestaurantTable
import com.data.persistence.models.suspendTransaction
import com.domain.mapping.RestaurantDaoToRestaurant
import com.domain.models.Restaurant
import com.domain.repository.RestaurantInterface
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.update

class PersistenceRestaurantRepository : RestaurantInterface {
    override suspend fun findAllRestaurants(): List<Restaurant> {
        return suspendTransaction {
            RestaurantDao.all().map(::RestaurantDaoToRestaurant)
        }
    }

    override suspend fun findRestaurantById(id: Long): Restaurant? {
        return suspendTransaction {
            RestaurantDao.find { RestaurantTable.id eq id }
                .limit(1)
                .map(::RestaurantDaoToRestaurant)
                .firstOrNull()
        }
    }

    override suspend fun insertNewRestaurant(restaurant: Restaurant): Boolean {
        val em = findRestaurantById(restaurant.id!!)
        return if (em == null) {
            suspendTransaction {
                RestaurantDao.new {
                    this.name = restaurant.name
                    this.phone = restaurant.phone
                    this.address = restaurant.address
                    this.rating = restaurant.rating
                    this.description = restaurant.description
                    this.image = restaurant.image
                }
                true
            }
            true
        } else
            false
    }

    override suspend fun deleteRestaurantById(id: Long): Boolean = suspendTransaction {
        val num = RestaurantTable
            .deleteWhere { RestaurantTable.id eq id }
        num == 1
    }

    override suspend fun updateRestaurantById(id: Long, updateRestaurant: Restaurant): Boolean {
        var num = 0
        try {
            suspendTransaction {
                num = RestaurantTable
                    .update({ RestaurantTable.id eq id }) { stm ->
                        updateRestaurant.name?.let { stm[this.name] = it }
                        updateRestaurant.address?.let { stm[this.address] = it }
                        updateRestaurant.phone?.let { stm[this.phone] = it }
                        updateRestaurant.description?.let { stm[this.description] = it }
                        updateRestaurant.rating?.let { stm[this.rating] = it }
                        updateRestaurant.image?.let { stm[this.image] = it }
                    }
            }
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return num == 1
    }
}