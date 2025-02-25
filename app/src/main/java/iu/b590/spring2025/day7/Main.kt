package iu.b590.spring2025.day7

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

suspend fun main(){
    println("in main before coroutineScope")
    runBlocking {
        val job1 = launch{
            milkCows()
        }
        val job2 = launch {
            feedChickens()
        }
        job1.join()
        job2.join()
        println("in main in coroutineScope after the two launches")
    }
    println("in main after coroutineScope")
}

suspend fun milkCows(){
    var cow = 1
    println("Milking cow #${cow}")
    yield()
    cow+=1
    println("Milking cow #${cow}")
    yield()
    cow+=1
    println("Milking cow #${cow}")
    yield()
    cow+=1
    println("Milking cow #${cow}")
    yield()
    cow+=1
}

suspend fun feedChickens(){
    var chicken = 1
    println("Feeding chicken #${chicken}")
    yield()
    chicken +=1
    println("Feeding chicken #${chicken}")
    yield()
    chicken +=1
    println("Feeding chicken #${chicken}")
    yield()
    chicken +=1
    println("Feeding chicken #${chicken}")
    yield()
    chicken +=1

}

