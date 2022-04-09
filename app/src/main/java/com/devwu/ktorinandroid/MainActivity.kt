package com.devwu.ktorinandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.engine.sslConnector
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.httpsredirect.HttpsRedirect
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.slf4j.LoggerFactory
import java.security.KeyStore
import kotlin.coroutines.EmptyCoroutineContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val keyStore = KeyStore.getInstance("PKCS12").apply {
            load(assets.open("keystore.jks"), "qwe123".toCharArray())
        }
        // Exception:
        // 1. java.lang.NoSuchMethodException: sun.misc.Unsafe.copyMemory [class java.lang.Object, long, class java.lang.Object, long, long]
        // 2. No implementation found for int io.netty.channel.kqueue.Native.sizeofKEvent()
        // 3. No implementation found for int io.netty.channel.epoll.Native.offsetofEpollData()
        // 4. java.lang.IllegalArgumentException: Failed to load any of the given libraries: [netty_tcnative_linux_aarch_64, netty_tcnative_linux_aarch_64_fedora, netty_tcnative_aarch_64, netty_tcnative]
        // 5. No implementation found for int io.netty.internal.tcnative.Library.aprMajorVersion() (tried Java_io_netty_internal_tcnative_Library_aprMajorVersion and Java_io_netty_internal_tcnative_Library_aprMajorVersion__)
        // 6. Failed to initialize netty-tcnative; OpenSslEngine will be unavailable. See https://netty.io/wiki/forked-tomcat-native.html for more information.
        // 7. java.lang.ClassNotFoundException: java.lang.management.ManagementFactory. Could not invoke ManagementFactory.getRuntimeMXBean().getName(); Android?
        // 8. Failed to get SOMAXCONN from sysctl and file /proc/sys/net/core/somaxconn. Default: 128
        embeddedServer(Netty, applicationEngineEnvironment {
            parentCoroutineContext = EmptyCoroutineContext + parentCoroutineContext
            log = LoggerFactory.getLogger("ktor.application")
            connector {
                port = 8080
            }
            sslConnector(
                keyStore,
                keyAlias = "samplealias",
                keyStorePassword = { "qwe123".toCharArray() },
                privateKeyPassword = { "qwe123".toCharArray() }
            ) {
                port = 8443
            }
            module {
                install(CallLogging)
                install(HttpsRedirect) {
                    permanentRedirect = false
                    sslPort = 8443
                }


                routing {
                    get("/") { call.respondText { "Hello World" } }
                }
            }
        }).start(false)

//        embeddedServer(CIO, applicationEngineEnvironment {
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
    }
}