package com.devwu.ktorinandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.cio.CIO
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.slf4j.LoggerFactory
import kotlin.coroutines.EmptyCoroutineContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        embeddedServer(Netty, applicationEngineEnvironment {
//            parentCoroutineContext = EmptyCoroutineContext + parentCoroutineContext
//            log = LoggerFactory.getLogger("ktor.application")
//            connector {
//                port = 8080
//            }
//            module {
//                routing {
//                    get("/") { call.respondText { "Hello Netty" } }
//                }
//            }
//        }).start(false)

        embeddedServer(CIO, applicationEngineEnvironment {
            parentCoroutineContext = EmptyCoroutineContext + parentCoroutineContext
            log = LoggerFactory.getLogger("ktor.application")
            connector {
                port = 8080
            }
            module {
                routing {
                    get("/") { call.respondText { "Hello CIO" } }
                }
            }
        }).start(false)
    }
}