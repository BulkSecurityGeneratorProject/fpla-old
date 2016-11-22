package de.ananyev.fpla.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.DesiredCapabilities

/**
 * Created by Ilya Ananyev on 22.11.16.
 */
class Browser {
    Browser() {
        def caps = new DesiredCapabilities()
        caps.javascriptEnabled = true
        caps.setCapability "takesScreenshot", true
        caps.setCapability(
            PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
            "/home/ilya/Programming/fpla/node_modules/phantomjs-prebuilt/lib/phantom/bin/phantomjs"
        )
        def driver = new PhantomJSDriver(caps)
        id = 0
        type = BrowserType.phantomjs
    }

    int id
    @JsonIgnore
    WebDriver driver
    BrowserType type

}
