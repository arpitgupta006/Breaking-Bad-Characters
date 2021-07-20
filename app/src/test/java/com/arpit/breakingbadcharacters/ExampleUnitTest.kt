package com.arpit.breakingbadcharacters

import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    val client = Client
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun getCharacter(){
        runBlocking {
            val character = client.api.getInfo()
            assertNotNull(character.body())
        }
    }
}