package com.foobar.zalenium

import org.openqa.selenium.remote.RemoteWebDriver

interface SeleniumWorkflow {
    fun runWorkflow(driver: RemoteWebDriver) : Long
}