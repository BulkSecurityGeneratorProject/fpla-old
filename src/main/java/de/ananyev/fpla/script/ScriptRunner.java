package de.ananyev.fpla.script;

import com.google.common.io.Files;
import de.ananyev.fpla.domain.Script;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;


/**
 * Created by Ilya Ananyev on 09.11.16.
 */
public class ScriptRunner {
    private final Logger log = LoggerFactory.getLogger(ScriptRunner.class);

    private Script script;
    private WebDriver driver;

    public void setScript(Script script) {
        this.script = script;
    }

    public Logger getLog() {
        return log;
    }

    public Script getScript() {
        return script;
    }

    public ScriptRunner(Script script) {
        this.script = script;
        Capabilities caps = new DesiredCapabilities();
        ((DesiredCapabilities) caps).setJavascriptEnabled(true);
        ((DesiredCapabilities) caps).setCapability("takesScreenshot", true);
        ((DesiredCapabilities) caps).setCapability(
            PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
            "node-modules/phantomjs-prebuild/lib/phantomjs/bin/phantomjs"
        );
        driver = new PhantomJSDriver(caps);

    }

    public void run() {
        ((Runnable) () -> {
            try {
                log.debug("script started");
                log.debug(driver.getCurrentUrl());
                log.debug(script.getText());
                Object ignore = ((JavascriptExecutor) driver).executeScript(script.getText());
                Thread.sleep(5000);
                log.info("current location: " + driver.getCurrentUrl());
                log.info("current src: " + driver.getPageSource());
                log.debug("script finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).run();
    }

    public String makeScreenShot() throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = "~/screenshot.jpg";
        FileUtils.copyFile(srcFile, new File(path));
        return path;
    }
}
