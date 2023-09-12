package com.example.debeziummysqlconnectorembeddedstudy

import io.debezium.embedded.EmbeddedEngine
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.concurrent.Executors

@SpringBootApplication
class DebeziumMysqlConnectorEmbeddedStudyApplication(private val engine: EmbeddedEngine) {
    @PostConstruct
    fun start() {
        log.info("### start...")
        val threadPool = Executors.newFixedThreadPool(1)
        threadPool.execute(engine)
    }

    companion object {
        private val log = LoggerFactory.getLogger(DebeziumMysqlConnectorEmbeddedStudyApplication::class.java)
    }
}

fun main(args: Array<String>) {
    runApplication<DebeziumMysqlConnectorEmbeddedStudyApplication>(*args)
}
