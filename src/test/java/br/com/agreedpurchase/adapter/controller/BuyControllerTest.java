package br.com.agreedpurchase.adapter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.agreedpurchase.adapter.controller.response.PurchaseDataResponse;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.service.BuyServiceImpl;
import br.com.agreedpurchase.utils.BuilderUtils;
import br.com.agreedpurchase.utils.FileUtils;
import br.com.agreedpurchase.utils.JSONUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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

    Buy buy = BuilderUtils.loadBuyWithMapPersonAndFee("PERCENT");

    when(buyService.buy(any())).thenReturn(buy);

    MvcResult result = mvc.perform(
            post(PATH)
                .content(FileUtils.loadRequest("buy"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    String content = result.getResponse().getContentAsString();

    PurchaseDataResponse purchaseDataResponse =
        JSONUtils.toPurchaseDataResponse(content);

    assertNotNull(purchaseDataResponse);
    assertNotNull(purchaseDataResponse.getDiscountType());
    assertEquals(2, purchaseDataResponse.getConsumerDataResponses().size());
    assertEquals(2, purchaseDataResponse.getAmountToPayResponses().size());

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