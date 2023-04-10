package br.com.agreedpurchase.adapter.controller;

import static br.com.agreedpurchase.domain.utils.ConstantsUtils.PERCENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.agreedpurchase.adapter.controller.response.PurchaseDataResponse;
import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.service.impl.BuyServiceImpl;
import br.com.agreedpurchase.utils.BuilderUtils;
import br.com.agreedpurchase.utils.FileUtils;
import br.com.agreedpurchase.utils.JSONUtils;
import java.util.List;
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
  void deleteWithSuccess() throws Exception {
    doNothing().when(buyService).delete(any());
    mvc.perform(
            delete(PATH.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }
  @Test
  void deleteWithError() throws Exception {
    doThrow(BusinessException.class).when(buyService).delete(any());

    mvc.perform(
            delete(PATH.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }
  @Test
  void loadAllWithSuccess() throws Exception {
    List<Buy> buy = BuilderUtils.loadPurchases();
    when(buyService.loadAll()).thenReturn(buy);
    MvcResult result = mvc.perform(
            get(PATH)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful())
        .andReturn();
    String content = result.getResponse().getContentAsString();

    assertNotNull(content);
  }

  @Test
  void loadAllWithError() throws Exception {
    when(buyService.loadAll()).thenThrow(BusinessException.class);
    mvc.perform(
            get(PATH)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  void loadByIdWithSuccess() throws Exception {
    Long id = 1L;
    Buy buy = BuilderUtils.loadBuyWithMapPersonAndFee(PERCENT);
    when(buyService.loadById(any())).thenReturn(buy);
    mvc.perform(
            get(PATH.concat("/").concat(id.toString()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  void loadByIdWithError() throws Exception {
    doThrow(BusinessException.class).when(buyService).loadById(any());
    mvc.perform(
            get(PATH.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

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

    PurchaseDataResponse purchaseDataResponse =
        JSONUtils.toPurchaseDataResponse(content);

    assertNotNull(purchaseDataResponse);
    assertNotNull(purchaseDataResponse.getDiscountType());
    assertEquals(2, purchaseDataResponse.getConsumerDataResponses().size());
    assertEquals(2, purchaseDataResponse.getAmountToPayResponses().size());
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