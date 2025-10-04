![simpay-java-sdk](https://socialify.git.ci/ZaliczTo-PL/simpay-java-sdk/image?custom_description=Unofficial+SDK+for+simpay.pl+created+for+the+usages+at+ZaliczTo.PL&description=1&font=Inter&name=1&owner=1&pattern=Circuit+Board&stargazers=1&theme=Dark)
# SimPay Java SDK

(c) 2025 Stanisław Botwina



## Installation

The lastest version can be aquired by looking at the releases tab and checking the lastest build.

### Gradle
```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://repo.zaliczto.pl") }
}
dependencies {
    implementation("pl.zaliczto:simpay-sdk:version")
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
```

```xml
<dependency>
    <groupId>pl.zaliczto</groupId>
    <artifactId>simpay-sdk</artifactId>
    <version>version</version>
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

## License
See [LICENSE](LICENSE) for details.
