package br.com.agreedpurchase.adapter.pix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(String args[]) {

    BufferedReader reader;

    try {
      String command = "curl -s -d 'chave=11999998888&tipo=celular&nome=Nome&info=Descri&valor=10.10' https://pix.ae | json_xs";

      Process process = Runtime.getRuntime().exec(command);

      //URL url = new URL("comando");
      //url.openStream()
      //reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));

      //for (String line; (line = reader.readLine()) != null; ) {
      //System.out.println(line);
      //}

      //reader.close();

      InputStream ins = process.getInputStream();// creating a buffered reader
      BufferedReader read = new BufferedReader(new InputStreamReader(ins));
      StringBuilder sb = new StringBuilder();
      read.lines()
          .forEach(line -> {
            log.debug("line>"+line);
            sb.append(line);
          });// close the buffered reader
      read.close();

      System.out.println(sb);

    } catch (IOException ioe) {
      System.out.println(ioe.getMessage());
    }
  }
}
