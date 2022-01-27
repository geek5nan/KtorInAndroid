package com.devwu.ktorinandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.ktor.server.application.call
import io.ktor.server.cio.CIO
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.slf4j.LoggerFactory
import kotlin.coroutines.EmptyCoroutineContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ERROR: java.lang.NoClassDefFoundError: Failed resolution of: Ljava/lang/management/ManagementFactory;
//        embeddedServer(Netty, applicationEngineEnvironment {
//            parentCoroutineContext = EmptyCoroutineContext + parentCoroutineContext
//            log = LoggerFactory.getLogger("ktor.application")
//            connector {
//                port = 8080
//            }
//            module {
//                routing {
//                    get("/") { call.respondText { "Hello World" } }
//                }
//            }
//        }).start(false)

        // ERROR: call no respond
        embeddedServer(CIO, applicationEngineEnvironment {
            parentCoroutineContext = EmptyCoroutineContext + parentCoroutineContext
            log = LoggerFactory.getLogger("ktor.application")
            connector {
                port = 8080
            }
            module {
                routing {
                    get("/") { call.respondText { "Hello World" } }
                }
            }
        }).start(false)
    }
}