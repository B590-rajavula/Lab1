package iu.b590.spring2025.day7

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        launch{
            for (k in 1  .. 4){
                println("hello $k")
            }
        }
        milkMeasurement().collect { x -> println(x) }
    }

}

fun milkMeasurement(): Flow<Float> =
    flow {
        for (i in arrayOf(180F, 76F, 10F, 92F)){
            emit(i)
        }
    }