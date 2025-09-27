# SimPay Java SDK

(c) 2025 Stanisław Botwina


## Installation

### Gradle
```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://repo.zaliczto.pl") }
}
dependencies {
    implementation("pl.zaliczto:simpay-sdk:1.0.1")
}
```

### Maven
```xml
<repositories>
    <repository>
        <id>zaliczto-repo</id>
        <url>https://repo.zaliczto.pl</url>
    </repository>
</repositories>

<dependency>
    <groupId>pl.zaliczto</groupId>
    <artifactId>simpay-sdk</artifactId>
    <version>1.0.1</version>
</dependency>
```

## Usage
If you use Spring Boot, you can configure the SDK using application properties, it will automatically create a bean for you.

```properties
simpay.sdk.bearer-token=your_bearer_token
simpay.sdk.base-url=https://api.simpay.pl/
simpay.sdk.payment-service-id=your_payment_service_id
simpay.sdk.direct-billing-service-id=your_direct_billing_service_id
simpay.sdk.sms-service-id=your_sms_service_id
simpay.sdk.payment-hash=your_payment_hash
simpay.sdk.direct-billing-hash=your_direct_billing_hash
```

If you don't use Spring Boot, you can create the SDK instance manually:

```java
SimPayClient simPayClient = SimPayClient("your_bearer_token")
    .paymentServiceId("your_payment_service_id")
    .directBillingServiceId("your_direct_billing_service_id")
    .smsServiceId("your_sms_service_id")
    .paymentHash("your_payment_hash")
    .directBillingHash("your_direct_billing_hash")
    .baseUrl("https://api.simpay.pl/");
```

<br>

## Documentation 

In order to view the documentation, please refer to the [Javadoc](https://repo.zaliczto.pl/simpay-sdk-javadoc/).

### Handling Payment IPN (v2)

Example signature validation of incoming Payment IPN (see https://docs.simpay.pl/notifications/payment):

```java
@PostMapping(path = "/simpay/ipn", consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<String> handleIpn(@RequestBody Map<String,Object> payload) {
    if(!simPayClient.getPaymentService().ipnSignatureValid(payload)) {
        return ResponseEntity.status(403).body("INVALID_SIGNATURE");
    }
    // process event
    return ResponseEntity.ok("OK"); // MUST return plain text OK with 200
}
```

## License
See [LICENSE](LICENSE) for details.