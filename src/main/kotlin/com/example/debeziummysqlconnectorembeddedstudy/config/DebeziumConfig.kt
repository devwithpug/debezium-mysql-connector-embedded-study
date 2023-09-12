package com.example.debeziummysqlconnectorembeddedstudy.config

import io.debezium.connector.mysql.MySqlConnector
import io.debezium.embedded.EmbeddedEngine
import io.debezium.engine.DebeziumEngine
import io.debezium.engine.DebeziumEngine.ChangeConsumer
import io.debezium.storage.file.history.FileSchemaHistory
import org.apache.kafka.connect.source.SourceRecord
import org.apache.kafka.connect.storage.FileOffsetBackingStore
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DebeziumConfig {

    @Bean
    fun engine(): EmbeddedEngine {
        return EmbeddedEngine.BuilderImpl()
            .using(embeddedEngineConfig())
            .notifying { records, _ ->
                records.forEach {
                    log.info("\n###event###\n${it.value()}")
                }
            }
            .build()
    }

    private fun embeddedEngineConfig() = io.debezium.config.Configuration.from(
        mapOf(
            "name" to "mysql-connector",
            "connector.class" to MySqlConnector::class.java.name,
            "offset.storage" to FileOffsetBackingStore::class.java.name,
            "offset.storage.file.filename" to "offset.dat",
            "offset.flush.interval.ms" to 60000,
            "database.hostname" to "localhost",
            "database.port" to 3306,
            "database.user" to "root",
            "database.password" to "pwd",
            "database.server.id" to 1,
            "database.serverTimezone" to "+09:00",
            "server.name" to "debezium-mysql-connector-embedded-study",
            "schema.history.internal" to FileSchemaHistory::class.java.name,
            "schema.history.internal.file.filename" to "schemahistory.dat",
            "topic.prefix" to "local-test"
        )
    )

    companion object {
        private val log = LoggerFactory.getLogger(DebeziumConfig::class.java)
    }
}
