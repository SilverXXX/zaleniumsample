package com.foobar.zalenium

import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.Key
import com.natpryce.konfig.intType
import com.natpryce.konfig.stringType
import org.openqa.selenium.Platform
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.remote.BrowserType
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

object Commons {

    val gridUrl = Key("grid.url", stringType)

    val threadNum = Key("thread.num", intType)

    val opFile = Key("op.file", stringType)

    fun getDriver(env: EnvironmentVariables, name: String, build: String) : RemoteWebDriver {
        var opts = FirefoxOptions()
        val prof = FirefoxProfile()
        prof.setPreference("media.navigator.permission.disabled", true)
        prof.setPreference("media.navigator.streams.fake", true)
        prof.setPreference("dom.webnotifications.enabled", false)
        prof.setPreference("dom.push.enabled", false)
        prof.setAcceptUntrustedCertificates(true)
        prof.layoutOnDisk()

        opts.profile = prof
        if (env.contains(Commons.gridUrl)) {
            opts.setCapability("name", name)
            opts.setCapability(CapabilityType.BROWSER_NAME, BrowserType.FIREFOX)
            opts.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX)
            opts.setCapability("build", build)
            opts.setCapability("idleTimeout", 250)

            /*
        * desiredCapabilities.setCapability("screenResolution", "1280x720");
        * desiredCapabilities.setCapability("recordVideo", false);
        * */
        }

        return if (env.contains(Commons.gridUrl)) RemoteWebDriver(URL(env[gridUrl]), opts) else FirefoxDriver(opts)
    }
}