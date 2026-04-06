# Apache Kafka - Complete Guide

## 📚 Table of Contents
1. [What is Kafka?](#what-is-kafka)
2. [Complete Terminology Guide](#complete-terminology-guide)
3. [Core Concepts](#core-concepts)
4. [Architecture](#architecture)
5. [How Kafka Works](#how-kafka-works)
6. [Use Cases](#use-cases)
7. [Spring Boot Annotations Reference](#spring-boot-annotations-reference)
8. [Spring Boot Integration](#spring-boot-integration)
9. [Code Examples](#code-examples)
10. [Best Practices](#best-practices)

---

## What is Kafka?

Apache Kafka is a **distributed event streaming platform** used for:
- Building real-time data pipelines
- Building real-time streaming applications
- High-throughput, low-latency message processing

**Key Characteristics:**
- Distributed, fault-tolerant
- Horizontally scalable
- High throughput (millions of messages/sec)
- Persistent storage
- Real-time processing

---

## Complete Terminology Guide

### A-Z Kafka Terminologies

#### **A**
- **ACK (Acknowledgment)**: Confirmation from broker that message was received
  - `acks=0`: No acknowledgment (fire and forget)
  - `acks=1`: Leader acknowledgment only
  - `acks=all/-1`: All in-sync replicas must acknowledge

- **Auto Offset Reset**: Behavior when no initial offset or offset out of range
  - `earliest`: Start from beginning
  - `latest`: Start from end
  - `none`: Throw exception

- **At-Most-Once**: Message delivered zero or one time (may lose messages)

- **At-Least-Once**: Message delivered one or more times (may duplicate)

- **Exactly-Once Semantics (EOS)**: Message delivered exactly once (no loss, no duplication)

#### **B**
- **Broker**: Kafka server that stores and serves data

- **Bootstrap Servers**: Initial list of brokers to connect to cluster

- **Batch Size**: Number of bytes to batch before sending to broker

- **Buffer Memory**: Total memory available to producer for buffering

#### **C**
- **Cluster**: Group of Kafka brokers working together

- **Consumer**: Application that reads data from Kafka topics

- **Consumer Group**: Set of consumers sharing workload of reading from topics

- **Consumer Lag**: Difference between last produced message and last consumed message

- **Commit**: Storing consumer's current position (offset) in partition

- **Compaction**: Log cleanup policy that keeps only latest value for each key

- **Controller**: Broker responsible for managing partition leaders

- **Coordinator**: Broker managing consumer group membership and rebalancing

#### **D**
- **Dead Letter Queue (DLQ)**: Topic for messages that failed processing

- **Deserialization**: Converting bytes back to objects

- **Delivery Semantics**: Guarantee about message delivery (at-most-once, at-least-once, exactly-once)

#### **E**
- **Event**: Immutable record of something that happened

- **Event Sourcing**: Storing state changes as sequence of events

- **Event Streaming**: Continuous flow of events through system

#### **F**
- **Follower**: Replica that passively replicates leader

- **Fetch Size**: Amount of data consumer fetches in single request

#### **G**
- **Group Coordinator**: Broker managing specific consumer group

- **Group ID**: Unique identifier for consumer group

#### **H**
- **Heartbeat**: Signal from consumer to coordinator indicating it's alive

- **High Water Mark (HWM)**: Offset of last message replicated to all ISRs

#### **I**
- **Idempotence**: Producing same message multiple times has same effect as once

- **In-Sync Replica (ISR)**: Replica that is fully caught up with leader

- **Isolation Level**: 
  - `read_uncommitted`: Read all messages
  - `read_committed`: Read only committed messages (transactional)

#### **K**
- **Key**: Optional identifier for message, determines partition assignment

- **KRaft**: Kafka Raft - new consensus protocol replacing ZooKeeper

- **Kafka Connect**: Framework for connecting Kafka with external systems

- **Kafka Streams**: Library for building stream processing applications

#### **L**
- **Leader**: Replica handling all reads and writes for partition

- **Leader Epoch**: Version number for partition leader

- **Linger Time**: Time to wait before sending batch (for better batching)

- **Log**: Ordered sequence of records stored on disk

- **Log Compaction**: Retention policy keeping only latest value per key

- **Log End Offset (LEO)**: Offset of last message written to log

- **Log Segment**: File containing portion of partition's log

#### **M**
- **Message**: Unit of data in Kafka (also called record or event)

- **Message Key**: Optional key determining partition assignment

- **Message Value**: Actual payload/data of message

- **Message Header**: Key-value metadata attached to message

- **Max Poll Records**: Maximum records returned in single poll

- **Min In-Sync Replicas**: Minimum ISRs required for write to succeed

#### **O**
- **Offset**: Unique sequential ID for each message in partition

- **Offset Commit**: Storing consumer's position in partition

- **Offset Reset**: Strategy when consumer has no valid offset

#### **P**
- **Producer**: Application that publishes data to Kafka topics

- **Partition**: Ordered, immutable sequence of records within topic

- **Partition Key**: Value used to determine which partition message goes to

- **Partitioner**: Logic determining partition assignment for message

- **Poll**: Consumer operation to fetch records from Kafka

- **Producer Record**: Message sent by producer (key, value, headers, partition, timestamp)

- **Consumer Record**: Message received by consumer (includes offset, partition info)

#### **R**
- **Rebalance**: Reassignment of partitions to consumers in group

- **Record**: Another term for message/event

- **Replica**: Copy of partition stored on different brokers

- **Replication Factor**: Number of copies of each partition

- **Retention**: How long Kafka keeps messages
  - Time-based: Keep for X days
  - Size-based: Keep until X bytes

- **Retry**: Attempting to send/process message again after failure

#### **S**
- **Segment**: File containing part of partition's log

- **Serialization**: Converting objects to bytes for transmission

- **Session Timeout**: Max time consumer can be silent before considered dead

- **Source**: System producing data into Kafka

- **Sink**: System consuming data from Kafka

- **Stream**: Continuous flow of records

- **Stream Processing**: Real-time processing of data streams

#### **T**
- **Topic**: Category/feed name where records are published

- **Transaction**: Atomic write operation across multiple partitions/topics

- **Transactional ID**: Unique ID for transactional producer

- **Tombstone**: Message with null value (used in compaction for deletion)

#### **Z**
- **ZooKeeper**: Coordination service for Kafka (being replaced by KRaft)

---

## Core Concepts

### 1. **Producer**
- Publishes messages to topics
- Decides which partition to send data to
- Can send data synchronously or asynchronously

### 2. **Consumer**
- Reads messages from topics
- Part of a consumer group
- Maintains offset (position in partition)

### 3. **Topic**
- Category/feed name where records are published
- Multi-subscriber (many consumers can read)
- Divided into partitions

### 4. **Partition**
- Ordered, immutable sequence of records
- Each message gets a sequential ID (offset)
- Enables parallelism and scalability

### 5. **Broker**
- Kafka server that stores data
- Handles read/write requests
- Manages partitions

### 6. **Cluster**
- Group of brokers working together
- One broker acts as Controller

### 7. **Offset**
- Unique identifier for each message in partition
- Consumer tracks its position using offset

### 8. **Consumer Group**
- Group of consumers working together
- Each partition consumed by only one consumer in group
- Enables load balancing

### 9. **Replication**
- Copies of partitions across brokers
- Leader handles reads/writes
- Followers replicate data

### 10. **ZooKeeper** (Legacy) / **KRaft** (New)
- Manages cluster metadata
- Leader election
- Configuration management

---

## Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    Kafka Cluster                        │
│                                                         │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐            │
│  │ Broker 1 │  │ Broker 2 │  │ Broker 3 │            │
│  │          │  │          │  │          │            │
│  │ Topic A  │  │ Topic A  │  │ Topic B  │            │
│  │ Part 0   │  │ Part 1   │  │ Part 0   │            │
│  │ (Leader) │  │ (Leader) │  │ (Leader) │            │
│  └──────────┘  └──────────┘  └──────────┘            │
│                                                         │
└─────────────────────────────────────────────────────────┘
         ▲                              │
         │                              │
    ┌────┴────┐                    ┌───▼────┐
    │Producer │                    │Consumer│
    └─────────┘                    └────────┘
```

---

## How Kafka Works

### Message Flow

1. **Producer sends message** → Topic → Partition (based on key/round-robin)
2. **Broker stores message** → Appends to partition log
3. **Consumer reads message** → Polls from partition
4. **Consumer commits offset** → Tracks progress

### Partition Distribution

```
Topic: orders (3 partitions, replication factor: 2)

Broker 1: [P0-Leader] [P1-Follower] [P2-Follower]
Broker 2: [P0-Follower] [P1-Leader] [P2-Follower]
Broker 3: [P0-Follower] [P1-Follower] [P2-Leader]
```

### Consumer Group Behavior

```
Topic: events (4 partitions)
Consumer Group: group-1 (2 consumers)

Consumer-1: reads from [P0, P1]
Consumer-2: reads from [P2, P3]
```

---

## Use Cases

### 1. **Messaging System**
- Decouple microservices
- Asynchronous communication

### 2. **Event Sourcing**
- Store state changes as events
- Rebuild state from event log

### 3. **Log Aggregation**
- Collect logs from multiple services
- Centralized logging

### 4. **Stream Processing**
- Real-time analytics
- Data transformation (Kafka Streams)

### 5. **Metrics & Monitoring**
- Collect application metrics
- System monitoring

### 6. **CDC (Change Data Capture)**
- Track database changes
- Sync data across systems

### 7. **Real-time Applications**
- Fraud detection
- Recommendation engines
- IoT data processing

---

## Spring Boot Annotations Reference

### Complete Annotation Guide

#### 1. **@EnableKafka**
```java
@Configuration
@EnableKafka
public class KafkaConfig {
    // Enables Kafka listener annotated endpoints
}
```
- Enables detection of `@KafkaListener` annotations
- Required on configuration class
- Auto-configured in Spring Boot

#### 2. **@KafkaListener**
```java
@KafkaListener(
    topics = "my-topic",
    groupId = "my-group",
    containerFactory = "kafkaListenerContainerFactory",
    concurrency = "3",
    autoStartup = "true",
    properties = {"max.poll.interval.ms:300000"}
)
public void listen(String message) {
    // Process message
}
```

**Attributes:**
- `id`: Unique identifier for listener
- `topics`: Array of topic names to listen to
- `topicPattern`: Regex pattern for topic names
- `topicPartitions`: Specific partitions to listen to
- `groupId`: Consumer group ID
- `containerFactory`: Custom listener container factory
- `errorHandler`: Custom error handler bean name
- `concurrency`: Number of concurrent consumers
- `autoStartup`: Auto-start listener (default: true)
- `properties`: Additional consumer properties
- `clientIdPrefix`: Prefix for consumer client ID
- `beanRef`: Reference to listener bean

#### 3. **@TopicPartition**
```java
@KafkaListener(
    topicPartitions = @TopicPartition(
        topic = "orders",
        partitions = {"0", "1", "2"},
        partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "100")
    ),
    groupId = "partition-group"
)
public void listenToPartitions(String message) {
    // Process from specific partitions
}
```

**Attributes:**
- `topic`: Topic name
- `partitions`: Array of partition numbers
- `partitionOffsets`: Initial offsets for partitions

#### 4. **@PartitionOffset**
```java
@TopicPartition(
    topic = "events",
    partitionOffsets = {
        @PartitionOffset(partition = "0", initialOffset = "0"),
        @PartitionOffset(partition = "1", initialOffset = "100")
    }
)
```

**Attributes:**
- `partition`: Partition number
- `initialOffset`: Starting offset (number or "earliest"/"latest")
- `relativeToCurrent`: Offset relative to current position

#### 5. **@Header**
```java
@KafkaListener(topics = "my-topic")
public void listen(
    String message,
    @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
    @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
    @Header(KafkaHeaders.OFFSET) long offset,
    @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp,
    @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
    @Header("custom-header") String customHeader
) {
    System.out.println("Topic: " + topic + ", Partition: " + partition);
}
```

**Common Headers:**
- `KafkaHeaders.RECEIVED_TOPIC`: Topic name
- `KafkaHeaders.RECEIVED_PARTITION_ID`: Partition ID
- `KafkaHeaders.OFFSET`: Message offset
- `KafkaHeaders.RECEIVED_TIMESTAMP`: Timestamp
- `KafkaHeaders.RECEIVED_MESSAGE_KEY`: Message key
- `KafkaHeaders.CORRELATION_ID`: Correlation ID
- `KafkaHeaders.REPLY_TOPIC`: Reply topic for request-reply

#### 6. **@Payload**
```java
@KafkaListener(topics = "orders")
public void listen(
    @Payload OrderEvent order,
    @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key
) {
    // @Payload explicitly marks the message payload
}
```

#### 7. **@SendTo**
```java
@KafkaListener(topics = "input-topic")
@SendTo("output-topic")
public String processAndForward(String message) {
    return "Processed: " + message;
    // Result automatically sent to output-topic
}
```

**Use Cases:**
- Request-reply pattern
- Message transformation pipeline
- Forwarding processed messages

#### 8. **@KafkaHandler**
```java
@Component
@KafkaListener(topics = "multi-type-topic", groupId = "handler-group")
public class MultiTypeListener {
    
    @KafkaHandler
    public void handleOrder(OrderEvent order) {
        System.out.println("Handling order: " + order);
    }
    
    @KafkaHandler
    public void handlePayment(PaymentEvent payment) {
        System.out.println("Handling payment: " + payment);
    }
    
    @KafkaHandler(isDefault = true)
    public void handleDefault(Object object) {
        System.out.println("Unknown type: " + object);
    }
}
```

**Purpose:**
- Handle different message types in same listener
- Type-based message routing
- Default handler for unknown types

#### 9. **@EnableKafkaStreams**
```java
@Configuration
@EnableKafkaStreams
public class KafkaStreamsConfig {
    
    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaStreamsConfiguration(props);
    }
}
```

**Purpose:**
- Enable Kafka Streams support
- Configure stream processing

#### 10. **@RetryableTopic**
```java
@RetryableTopic(
    attempts = "4",
    backoff = @Backoff(delay = 1000, multiplier = 2.0),
    autoCreateTopics = "true",
    include = {NetworkException.class, TimeoutException.class},
    exclude = {ValidationException.class}
)
@KafkaListener(topics = "orders")
public void processOrder(OrderEvent order) {
    // Process with automatic retry on failure
}
```

**Attributes:**
- `attempts`: Number of retry attempts
- `backoff`: Backoff configuration
- `autoCreateTopics`: Auto-create retry/DLT topics
- `include`: Exceptions to retry
- `exclude`: Exceptions to not retry
- `dltTopicSuffix`: Dead letter topic suffix
- `retryTopicSuffix`: Retry topic suffix

#### 11. **@DltHandler**
```java
@DltHandler
public void handleDlt(
    OrderEvent order,
    @Header(KafkaHeaders.EXCEPTION_MESSAGE) String exceptionMessage,
    @Header(KafkaHeaders.EXCEPTION_STACKTRACE) String stackTrace
) {
    System.out.println("Message sent to DLT: " + order);
    System.out.println("Error: " + exceptionMessage);
}
```

**Purpose:**
- Handle messages that failed all retries
- Process dead letter queue messages
- Log or alert on persistent failures

#### 12. **@Backoff**
```java
@RetryableTopic(
    backoff = @Backoff(
        delay = 1000,           // Initial delay in ms
        multiplier = 2.0,       // Exponential multiplier
        maxDelay = 10000,       // Max delay between retries
        random = true           // Add randomness to delay
    )
)
```

**Attributes:**
- `delay`: Initial delay in milliseconds
- `multiplier`: Exponential backoff multiplier
- `maxDelay`: Maximum delay between retries
- `random`: Add random jitter to delay

### Advanced Annotation Examples

#### Multiple Topics with Different Handlers
```java
@Component
public class MultiTopicListener {
    
    @KafkaListener(topics = "orders", groupId = "order-group")
    public void handleOrders(OrderEvent order) {
        // Handle orders
    }
    
    @KafkaListener(topics = "payments", groupId = "payment-group")
    public void handlePayments(PaymentEvent payment) {
        // Handle payments
    }
}
```

#### Batch Listener
```java
@KafkaListener(
    topics = "batch-topic",
    groupId = "batch-group",
    containerFactory = "batchFactory"
)
public void processBatch(
    List<OrderEvent> orders,
    @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
    @Header(KafkaHeaders.OFFSET) List<Long> offsets
) {
    for (int i = 0; i < orders.size(); i++) {
        System.out.println("Order: " + orders.get(i) + ", Offset: " + offsets.get(i));
    }
}
```

#### Manual Acknowledgment
```java
@KafkaListener(
    topics = "manual-ack-topic",
    groupId = "manual-group",
    containerFactory = "manualAckFactory"
)
public void processWithManualAck(
    String message,
    Acknowledgment acknowledgment
) {
    try {
        // Process message
        acknowledgment.acknowledge();
    } catch (Exception e) {
        // Don't acknowledge - will be reprocessed
    }
}
```

#### Consumer Record Access
```java
@KafkaListener(topics = "detailed-topic")
public void processWithFullRecord(ConsumerRecord<String, OrderEvent> record) {
    System.out.println("Key: " + record.key());
    System.out.println("Value: " + record.value());
    System.out.println("Partition: " + record.partition());
    System.out.println("Offset: " + record.offset());
    System.out.println("Timestamp: " + record.timestamp());
    
    record.headers().forEach(header -> 
        System.out.println(header.key() + ": " + new String(header.value()))
    );
}
```

#### Filtering Messages
```java
@Configuration
public class FilterConfig {
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> filterFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        
        factory.setRecordFilterStrategy(record -> 
            record.value().contains("IGNORE")
        );
        
        return factory;
    }
}

@KafkaListener(
    topics = "filtered-topic",
    containerFactory = "filterFactory"
)
public void processFiltered(String message) {
    // Only receives messages not containing "IGNORE"
}
```

#### Request-Reply Pattern
```java
// Replying Service
@KafkaListener(topics = "request-topic")
@SendTo // Reply topic from header
public String handleRequest(String request) {
    return "Response to: " + request;
}

// Requesting Service
@Service
public class RequestingService {
    
    @Autowired
    private ReplyingKafkaTemplate<String, String, String> replyTemplate;
    
    public String sendRequest(String request) throws Exception {
        ProducerRecord<String, String> record = 
            new ProducerRecord<>("request-topic", request);
        
        RequestReplyFuture<String, String, String> future = 
            replyTemplate.sendAndReceive(record);
        
        ConsumerRecord<String, String> response = 
            future.get(10, TimeUnit.SECONDS);
        
        return response.value();
    }
}
```

---

## Spring Boot Integration

### Dependencies (Maven)

```xml
<dependencies>
    <!-- Spring Kafka -->
    <dependency>
        <groupId>org.springframework.kafka</groupId>
        <artifactId>spring-kafka</artifactId>
    </dependency>
    
    <!-- Spring Boot Starter -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
</dependencies>
```

### Configuration (application.yml)

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    
    # Producer Configuration
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
      acks: all
      retries: 3
      
    # Consumer Configuration
    consumer:
      group-id: my-consumer-group
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      auto-offset-reset: earliest
      enable-auto-commit: false
      properties:
        spring.json.trusted.packages: "*"
```

---

## Code Examples

### 1. Basic Producer

```java
@Service
public class KafkaProducerService {
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
    
    public void sendMessageWithKey(String topic, String key, String message) {
        kafkaTemplate.send(topic, key, message);
    }
}
```

### 2. Basic Consumer

```java
@Service
public class KafkaConsumerService {
    
    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void consume(String message) {
        System.out.println("Consumed: " + message);
    }
}
```

### 3. Producer with Callback

```java
@Service
public class KafkaProducerWithCallback {
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    public void sendWithCallback(String topic, String message) {
        ListenableFuture<SendResult<String, String>> future = 
            kafkaTemplate.send(topic, message);
        
        future.addCallback(
            result -> System.out.println("Sent: " + message + 
                " Offset: " + result.getRecordMetadata().offset()),
            ex -> System.err.println("Failed: " + ex.getMessage())
        );
    }
}
```

### 4. JSON Message Producer

```java
// Event Model
public class OrderEvent {
    private String orderId;
    private String product;
    private double amount;
    // getters, setters, constructors
}

// Producer
@Service
public class OrderProducer {
    
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    
    public void sendOrder(OrderEvent order) {
        kafkaTemplate.send("orders", order.getOrderId(), order);
    }
}
```

### 5. JSON Message Consumer

```java
@Service
public class OrderConsumer {
    
    @KafkaListener(topics = "orders", groupId = "order-service")
    public void consumeOrder(OrderEvent order) {
        System.out.println("Processing order: " + order.getOrderId());
        // Process order logic
    }
}
```

### 6. Manual Offset Commit

```java
@Service
public class ManualCommitConsumer {
    
    @KafkaListener(topics = "transactions", groupId = "transaction-group")
    public void consume(String message, Acknowledgment ack) {
        try {
            // Process message
            System.out.println("Processing: " + message);
            
            // Manually commit offset
            ack.acknowledge();
        } catch (Exception e) {
            // Don't commit on error - message will be reprocessed
            System.err.println("Error: " + e.getMessage());
        }
    }
}
```

### 7. Multiple Topics Consumer

```java
@Service
public class MultiTopicConsumer {
    
    @KafkaListener(topics = {"topic1", "topic2", "topic3"}, groupId = "multi-group")
    public void consume(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        System.out.println("From " + topic + ": " + message);
    }
}
```

### 8. Partition-Specific Consumer

```java
@Service
public class PartitionConsumer {
    
    @KafkaListener(
        topicPartitions = @TopicPartition(
            topic = "orders",
            partitions = {"0", "1"}
        ),
        groupId = "partition-group"
    )
    public void consumeFromPartitions(String message) {
        System.out.println("From specific partition: " + message);
    }
}
```

### 9. Kafka Configuration Class

```java
@Configuration
public class KafkaConfig {
    
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    
    // Producer Configuration
    @Bean
    public ProducerFactory<String, OrderEvent> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }
    
    @Bean
    public KafkaTemplate<String, OrderEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    
    // Consumer Configuration
    @Bean
    public ConsumerFactory<String, OrderEvent> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "order-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(config);
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderEvent> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
```

### 10. Error Handling

```java
@Configuration
public class KafkaErrorHandlingConfig {
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        
        // Set error handler
        factory.setCommonErrorHandler(new DefaultErrorHandler(
            new FixedBackOff(1000L, 3L) // 3 retries with 1 sec delay
        ));
        
        return factory;
    }
}

@Service
public class ErrorHandlingConsumer {
    
    @KafkaListener(topics = "error-topic", groupId = "error-group")
    public void consume(String message) {
        if (message.contains("error")) {
            throw new RuntimeException("Processing failed");
        }
        System.out.println("Processed: " + message);
    }
}
```

### 11. Batch Consumer

```java
@Service
public class BatchConsumer {
    
    @KafkaListener(topics = "batch-topic", groupId = "batch-group")
    public void consumeBatch(List<String> messages) {
        System.out.println("Received batch of " + messages.size() + " messages");
        messages.forEach(msg -> System.out.println("Message: " + msg));
    }
}
```

### 12. Complete Microservice Example

```java
// Event
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent {
    private String paymentId;
    private String userId;
    private double amount;
    private PaymentStatus status;
    private LocalDateTime timestamp;
}

enum PaymentStatus { PENDING, COMPLETED, FAILED }

// Producer Service
@Service
@Slf4j
public class PaymentEventProducer {
    
    @Autowired
    private KafkaTemplate<String, PaymentEvent> kafkaTemplate;
    
    private static final String TOPIC = "payment-events";
    
    public void publishPaymentEvent(PaymentEvent event) {
        log.info("Publishing payment event: {}", event.getPaymentId());
        
        kafkaTemplate.send(TOPIC, event.getPaymentId(), event)
            .addCallback(
                result -> log.info("Event published successfully: offset={}", 
                    result.getRecordMetadata().offset()),
                ex -> log.error("Failed to publish event", ex)
            );
    }
}

// Consumer Service
@Service
@Slf4j
public class PaymentEventConsumer {
    
    @Autowired
    private PaymentProcessor paymentProcessor;
    
    @KafkaListener(
        topics = "payment-events",
        groupId = "payment-processor-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumePaymentEvent(PaymentEvent event, Acknowledgment ack) {
        try {
            log.info("Received payment event: {}", event.getPaymentId());
            
            // Process payment
            paymentProcessor.process(event);
            
            // Commit offset
            ack.acknowledge();
            
            log.info("Payment processed successfully: {}", event.getPaymentId());
        } catch (Exception e) {
            log.error("Error processing payment: {}", event.getPaymentId(), e);
            // Don't acknowledge - message will be reprocessed
        }
    }
}

// REST Controller
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    @Autowired
    private PaymentEventProducer producer;
    
    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequest request) {
        PaymentEvent event = new PaymentEvent(
            UUID.randomUUID().toString(),
            request.getUserId(),
            request.getAmount(),
            PaymentStatus.PENDING,
            LocalDateTime.now()
        );
        
        producer.publishPaymentEvent(event);
        
        return ResponseEntity.ok("Payment initiated: " + event.getPaymentId());
    }
}
```

### 13. Kafka Streams Example

```java
@Configuration
@EnableKafkaStreams
public class KafkaStreamsConfig {
    
    @Bean
    public KStream<String, OrderEvent> processOrders(StreamsBuilder builder) {
        KStream<String, OrderEvent> stream = builder.stream("orders");
        
        // Filter high-value orders
        stream.filter((key, order) -> order.getAmount() > 1000)
              .to("high-value-orders");
        
        // Aggregate by product
        stream.groupBy((key, order) -> order.getProduct())
              .count()
              .toStream()
              .to("product-counts");
        
        return stream;
    }
}
```

---

## Best Practices

### 1. **Producer Best Practices**
- Use appropriate `acks` setting (all, 1, 0)
- Enable idempotence for exactly-once semantics
- Set proper `batch.size` and `linger.ms` for throughput
- Use compression (snappy, gzip, lz4)
- Handle failures with retries

```java
props.put(ProducerConfig.ACKS_CONFIG, "all");
props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
```

### 2. **Consumer Best Practices**
- Use consumer groups for scalability
- Commit offsets after processing
- Handle rebalancing gracefully
- Set appropriate `max.poll.records`
- Monitor consumer lag

### 3. **Topic Design**
- Use meaningful topic names
- Plan partition count (based on throughput)
- Set appropriate replication factor (min 3)
- Configure retention policy
- Use compacted topics for state

### 4. **Performance Optimization**
- Increase partitions for parallelism
- Tune `batch.size` and `linger.ms`
- Use async sends when possible
- Monitor broker metrics
- Use SSD for storage

### 5. **Error Handling**
- Implement retry logic
- Use Dead Letter Queue (DLQ)
- Log errors with context
- Monitor failed messages
- Implement circuit breakers

### 6. **Security**
- Enable SSL/TLS encryption
- Use SASL authentication
- Implement ACLs
- Encrypt sensitive data
- Audit access logs

### 7. **Monitoring**
- Track consumer lag
- Monitor broker health
- Alert on replication issues
- Track throughput metrics
- Monitor disk usage

---

## Key Takeaways

✅ **Kafka = Distributed Event Streaming Platform**  
✅ **Topics → Partitions → Messages (with offsets)**  
✅ **Producers publish, Consumers subscribe**  
✅ **Consumer Groups enable parallel processing**  
✅ **Replication ensures fault tolerance**  
✅ **Spring Boot makes integration simple**  
✅ **Use for: messaging, event sourcing, stream processing**  
✅ **Design for: scalability, reliability, performance**

---

## Quick Command Reference

```bash
# Start Kafka (with Docker)
docker-compose up -d

# Create topic
kafka-topics --create --topic my-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

# List topics
kafka-topics --list --bootstrap-server localhost:9092

# Describe topic
kafka-topics --describe --topic my-topic --bootstrap-server localhost:9092

# Produce messages
kafka-console-producer --topic my-topic --bootstrap-server localhost:9092

# Consume messages
kafka-console-consumer --topic my-topic --from-beginning --bootstrap-server localhost:9092

# Consumer groups
kafka-consumer-groups --list --bootstrap-server localhost:9092
kafka-consumer-groups --describe --group my-group --bootstrap-server localhost:9092
```

---

**Happy Streaming! 🚀**
