package pl.zaliczto.simpay.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class HashesTest {

    @Test
    void sha256HexIsDeterministic() {
        String a = Hashes.sha256Hex("hello");
        String b = Hashes.sha256Hex("hello");
        assertThat(a).isEqualTo(b);
        assertThat(a).hasSize(64);
    }

    @Test
    void constantTimeEqualsWorks() {
        assertThat(Hashes.constantTimeEquals("abc", "abc")).isTrue();
        assertThat(Hashes.constantTimeEquals("abc", "abcd")).isFalse();
        assertThat(Hashes.constantTimeEquals("abc", "abC")).isFalse();
        assertThat(Hashes.constantTimeEquals(null, "abc")).isFalse();
    }
}
