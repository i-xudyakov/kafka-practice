import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.avro.Schema
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties

fun main() {

    val schemaString = """
        {
          "type": "record",
          "name": "UserEvent",
          "namespace": "demo",
          "fields": [
            {
              "name": "id",
              "type": "long"
            },
            {
              "name": "name",
              "type": "string"
            },
            {
              "name": "age",
              "type": "int"
            }
          ]
        }
    """.trimIndent()

    val schema = Schema.Parser().parse(schemaString)

    val user = GenericData.Record(schema)

    user.put("id", 1L)
    user.put("name", "Ivan")
    user.put("age", 20)

    val props = Properties()

    props["bootstrap.servers"] = "localhost:9092"

    props["key.serializer"] =
        StringSerializer::class.java.name

    props["value.serializer"] =
        KafkaAvroSerializer::class.java.name

    props["schema.registry.url"] =
        "http://localhost:8081"

    props["request.timeout.ms"] = "30000"
    props["delivery.timeout.ms"] = "60000"
    props["max.block.ms"] = "60000"

    val producer = KafkaProducer<String, GenericRecord>(props)

    val record =
        ProducerRecord<String, GenericRecord>(
            "users",
            "user-1",
            user
        )

    try {

        println("Try to send message...")

        producer.send(record).get()

        println("Successfully sent message...!")

    } catch (e: Exception) {

        println("Error sending message...:")
        e.printStackTrace()

    } finally {

        producer.close()
    }
}