package br.com.agreedpurchase.adapter.controller;

import static br.com.agreedpurchase.domain.utils.ConstantsUtils.PERCENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.agreedpurchase.adapter.pix.payload.PixRequest;
import br.com.agreedpurchase.adapter.pix.payload.PixResponse;
import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.service.ChargeService;
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
@WebMvcTest({ChargeController.class})
@AutoConfigureMockMvc
class ChargeControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private ChargeService chargeService;

  final String PATH = "/charge";

  @Test
  void chargeWithSuccess() throws Exception {

    String copyPaste =
        "00020126510014br.gov.bcb.pix0120isacaugiar@gmail.com0205Teste520400005303986540510.005802BR5911Isac Aguiar6008Salvador62100506string6304D141";

    PixResponse pix = BuilderUtils.getPixResponse(copyPaste);

    when(chargeService.charge(any())).thenReturn(pix);

    MvcResult result = mvc.perform(
            post(PATH)
                .content(FileUtils.loadRequest("charge"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful())
        .andReturn();
    String content = result.getResponse().getContentAsString();

    PixResponse pixResponse =
        JSONUtils.toPixResponse(content);

    assertNotNull(pixResponse);
    assertNotNull(pixResponse.getCopyPaste());
  }

  @Test
  void chargeWithError() throws Exception {
    when(chargeService.charge(any())).thenThrow(BusinessException.class);

    mvc.perform(
            post(PATH)
                .content(FileUtils.loadRequest("charge"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

}