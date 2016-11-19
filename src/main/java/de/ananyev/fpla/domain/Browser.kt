package de.ananyev.fpla.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.openqa.selenium.WebDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.DesiredCapabilities
import java.io.Serializable

/**
 * Created by Ilya Ananyev on 15.11.16.
 */
class Browser : Serializable {
    var id: Int
    @JsonIgnore
    var driver: WebDriver
    var type: BrowserType

    constructor() {
        val caps = DesiredCapabilities()
        caps.isJavascriptEnabled = true
        caps.setCapability("takesScreenshot", true)
        caps.setCapability(
            PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
            "/home/ilya/Programming/fpla/node_modules/phantomjs-prebuilt/lib/phantom/bin/phantomjs"
        )
        driver = PhantomJSDriver(caps)
        id = 0
        type = BrowserType.phantomjs
    }
}
