package com.foobar.zalenium

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.concurrent.*

class SimpleTest : TestBase() {
    @Test
    fun runTest(){
        val exec = Executors.newFixedThreadPool(env[Commons.threadNum])
        val list = mutableListOf<Callable<Long>>()

        for (i in 1 .. (env[Commons.threadNum])){
            list.add(Google(env, i))
        }

        val res = exec.invokeAll(list)
        exec.shutdown()
        val terminated = exec.awaitTermination(30, TimeUnit.MINUTES)

        assertTrue(terminated, "Timeout in execution")

        for (j in res){
            assertTrue((j.get() >= 0), "Error in execution")
        }
    }
}