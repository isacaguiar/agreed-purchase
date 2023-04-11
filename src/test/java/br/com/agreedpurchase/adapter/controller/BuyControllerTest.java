package br.com.agreedpurchase.adapter.controller;

import static br.com.agreedpurchase.domain.utils.ConstantsUtils.PERCENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.service.impl.BuyServiceImpl;
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
  void buyWithSuccess() throws Exception {

    Buy buy = BuilderUtils.loadBuyWithMapPersonAndFee(PERCENT);

    when(buyService.buy(any())).thenReturn(buy);

    MvcResult result = mvc.perform(
            post(PATH)
                .content(FileUtils.loadRequest("buy"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    String content = result.getResponse().getContentAsString();

    Buy buyResponse =
        JSONUtils.toPurchaseDataResponse(content);

    assertNotNull(buyResponse);
    assertNotNull(buyResponse.getDiscountType());
    assertEquals(2, buyResponse.getMapPerson().size());
    assertEquals(2, buyResponse.getMapPersonAddFee().size());
  }

  @Test
  void buyWithError() throws Exception {
    when(buyService.buy(any())).thenThrow(BusinessException.class);

    mvc.perform(
            post(PATH)
                .content(FileUtils.loadRequest("buy"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

}