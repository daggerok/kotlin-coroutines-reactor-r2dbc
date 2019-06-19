package com.github.daggerok;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SpringBootTest
@DisplayName("Junit 5 Test")
@AllArgsConstructor(onConstructor_ = @Autowired)
class AppTest {

  private GenericApplicationContext ctx;

  @Test
  void main() {
    assertThat(ctx).isNotNull();
  }
}
