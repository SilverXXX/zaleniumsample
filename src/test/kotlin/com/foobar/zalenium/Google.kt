package com.foobar.zalenium

import com.natpryce.konfig.EnvironmentVariables
import org.openqa.selenium.*
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

class Google(env: EnvironmentVariables, num: Int) : SeleniumWorkflow, Callable<Long> {
    override fun call(): Long {

        val driver = Commons.getDriver(envVars, "googleClient-$number", "build-" + DateTimeFormatter.ISO_INSTANT.format(
            Instant.now()))
        return runWorkflow(driver)
    }

    val envVars = env
    val number = num
    override fun runWorkflow(driver: RemoteWebDriver) : Long {
        var time = 0L
        try {
            time = measureTimeMillis {
                driver.manage()?.timeouts()?.implicitlyWait(10, TimeUnit.SECONDS)
                driver.manage()?.window()?.maximize()

                //first create a new dvo link from https://adminriconoscimento.aruba.it/DriverDVO/CreateDVO.aspx
                driver.navigate().to("https://google.com")
                var cookie = Cookie("zaleniumMessage", "Call Google")
                driver.manage().addCookie(cookie)
                Thread.sleep(2500)
            }

            val cookie = Cookie("zaleniumTestPassed", "true")
            driver.manage().addCookie(cookie)
        } catch (ee: Exception) {
            val cookie = Cookie("zaleniumTestPassed", "false")
            driver.manage().addCookie(cookie)
            System.out.println("GOOGLE $number: " + ee)
        } finally {
            driver.quit()
        }
        return time
    }
}