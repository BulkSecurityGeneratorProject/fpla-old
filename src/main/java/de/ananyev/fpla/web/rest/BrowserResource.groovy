package de.ananyev.fpla.web.rest

import de.ananyev.fpla.domain.Browser
import de.ananyev.fpla.repository.ScriptRepository
import de.ananyev.fpla.web.rest.util.HeaderUtil
import org.openqa.selenium.JavascriptExecutor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import java.awt.TexturePaintContext
import java.net.URI
import java.util.*
import javax.inject.Inject

/**
 * Created by Ilya Ananyev on 15.11.16.
 */
@RestController
@RequestMapping("/api/browsers")
class BrowserResource {

    @Inject
    ScriptRepository scriptRepository

    static browsers = new ArrayList<Browser>()
    static log = LoggerFactory.getLogger(ScriptResource.class)

    @GetMapping()
    ArrayList<Browser> getAll() {
        browsers
    }

    @GetMapping("/{id}")
    ResponseEntity<Browser> getOne(@PathVariable int id ) {
        new ResponseEntity<Browser> (
            browsers.find{ it.id == id
            }, HttpStatus.OK )
    }

    @PostMapping()
    ResponseEntity<Browser> create(@RequestBody Browser browser) {
        if (browser.id != 0) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("script", "idexists", "A new browser cannot already have an ID")).body(null);
        }
        browser.id = browsers.size
        browsers.add(browser)
        return ResponseEntity.created(new URI("/api/scripts/$browser.id"))
            .headers(HeaderUtil.createEntityCreationAlert("script", browser.id.toString()))
            .body(browser)
    }

    @DeleteMapping("/{id}")
    delete(@PathVariable int id) {
        browsers.removeIf {
            it.id == id
        }
    }

    @GetMapping("/{browserId}/run/{scriptId}")
    run(@PathVariable int browserId, @PathVariable Long scriptId) {
        def script = scriptRepository.findOne(scriptId)
        def driver = browsers.find { it.id == browserId }?.driver
        def url = "https://youtube.com"
        //def blba = "advasdv ${url}"
        Runnable runnable = {
            def ignore = (driver as JavascriptExecutor).executeScript(script.getText())
            Thread.sleep(5000)
            log.info("current location: " + driver.currentUrl)
        } as Runnable
        runnable.run()
    }

    @GetMapping("/{id}/status")
    status(@PathVariable int browserId) { /* todo implement */ }
}

