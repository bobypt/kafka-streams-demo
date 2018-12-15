package org.bpt.demo;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.*;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

	private static final String TEST_TOPIC_IN = "data-in";
	private static final String TEST_TOPIC_RESULT = "data-text";

	@ClassRule
	public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1);

	@Autowired
	private KafkaTemplate<Integer, String> template;

	@Autowired
	private KafkaProperties properties;


	@BeforeClass
	public static void setup() {
		System.setProperty("spring.kafka.bootstrap-servers", embeddedKafka.getBrokersAsString());
		System.setProperty("spring.cloud.stream.kafka.binder.brokers", embeddedKafka.getBrokersAsString());
		System.setProperty("spring.cloud.stream.kafka.streams.binder.brokers", embeddedKafka.getBrokersAsString());
	}

	@AfterClass
	public static void tearDown() throws Exception {
		embeddedKafka.destroy();
	}




	@Test
	public void testSendReceive() throws InterruptedException {
		KafkaTemplate kafkaTemplate = kafkaTemplate();
		kafkaTemplate.send(TEST_TOPIC_IN, "Key", "hellooo");
		kafkaTemplate.flush();

		CountDownLatch stateLatch = new CountDownLatch(1);
		stateLatch.await(10, TimeUnit.SECONDS);

		Map<String, Object> configs = properties.buildConsumerProperties();
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "test0544");
		configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

		ConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(configs);
		Consumer<String, String> consumer = cf.createConsumer();
		consumer.subscribe(Collections.singleton(TEST_TOPIC_RESULT));
		ConsumerRecords<String, String> records = consumer.poll(10_000);
		consumer.commitSync();
		assertThat(records.count()).isEqualTo(1);
		assertThat(new String(records.iterator().next().value())).isEqualTo("hellooo");


	}


	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafka.getBrokersAsString());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		// See https://kafka.apache.org/documentation/#producerconfigs for more properties
		return props;
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<String, String>(producerFactory());
	}

}
