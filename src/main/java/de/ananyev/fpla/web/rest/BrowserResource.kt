package de.ananyev.fpla.web.rest

import de.ananyev.fpla.domain.Browser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * Created by Ilya Ananyev on 15.11.16.
 */
@RestController
@RequestMapping("/api/browsers")
open class BrowserResource {
    var browsers = ArrayList<Browser>()
    var logger : Logger = LoggerFactory.getLogger(ScriptResource::class.java)

    @GetMapping()
    fun getAll() : ArrayList<Browser> = browsers

    @GetMapping("/{id}")
    fun getOne(@PathVariable id : Int) : Browser = browsers.first { it.id == id }

    @PostMapping()
    fun create(@RequestBody browser : Browser) : Unit {
        browser.id = browsers.size
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id : Int) = browsers.removeIf { it.id == id }

    @GetMapping("/{browserId}/run/{scriptId}")
    fun run(@PathVariable browserId : Int, @PathVariable scriptId : Int) { /* todo implement */ }

    @GetMapping("/{id}/status")
    fun status(@PathVariable browserId: Int) { /* todo implement */ }
}
