package br.com.agreedpurchase.adapter.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.service.BuyServiceImpl;
import br.com.agreedpurchase.utils.FileUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest({BuyController.class})
@AutoConfigureMockMvc
class BuyControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  BuyServiceImpl buyService;

  final String PATH = "/buy";

  @Test
  void shouldBuyWithSuccess() throws Exception {

    Buy buy = Buy.builder()
        .fee(new BigDecimal(10))
        .date(new Date())
        .discount(new BigDecimal(5))
        .items(new HashSet<>())
        .build();

    when(buyService.buy(any())).thenReturn(buy);

    mvc.perform(
            post(PATH)
                .content(FileUtils.loadRequest("buy"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  void shouldBuyWithError() throws Exception {
    Buy buy = Buy.builder().build();
    when(buyService.buy(any())).thenReturn(buy);

    mvc.perform(
            post(PATH)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

}