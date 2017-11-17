package io.audium.audiumbackend.controllers;

import com.google.gson.JsonObject;
import io.audium.audiumbackend.services.AdminService;
import io.audium.audiumbackend.services.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

  @Autowired
  public  VerificationService verificationService;
  @Autowired
  private AdminService        adminService;

  /* Bcrypt hashing test function */
  @GetMapping(value = "/bcrypt/{rawData}")
  public ResponseEntity testBcrypt(@PathVariable String rawData) {
    return ResponseEntity.status(HttpStatus.OK).body(adminService.testBcrypt(rawData).toString());
  }

  /* AES encrypting test function */
  @GetMapping(value = "/aes/encrypt/{accountId}/{rawData}")
  public ResponseEntity testAesEncrypt(@PathVariable long accountId, @PathVariable String rawData) {
    String salt          = adminService.getAccountSalt(accountId);
    String encryptedData = verificationService.aesEncrypt(accountId, rawData).toString();
    System.out.println("AES encryption test:\n\taccountId=" + accountId + "\n\tRaw data=\"" + rawData + "\"\n\tSalt=\"" + salt + "\"\n\tEncrypted data=\"" + encryptedData + "\"");
    JsonObject response = new JsonObject();
    response.addProperty("accountId", accountId);
    response.addProperty("rawData", rawData);
    response.addProperty("salt", salt);
    response.addProperty("encryptedData", encryptedData);
    return ResponseEntity.status(HttpStatus.OK).body(response.toString());
  }

  /* AES decrypting test function */
  @GetMapping(value = "/aes/decrypt/{accountId}/{encryptedData}")
  public ResponseEntity testAesDecrypt(@PathVariable long accountId, @PathVariable String encryptedData) {
    String salt          = adminService.getAccountSalt(accountId);
    String decryptedData = verificationService.aesDecrypt(accountId, encryptedData).toString();
    System.out.println("AES decryption test:\n\taccountId=" + accountId + "\n\tRaw data=\"" + decryptedData + "\"\n\tSalt=\"" + salt + "\"\n\tEncrypted data=\"" + encryptedData + "\"");
    JsonObject response = new JsonObject();
    response.addProperty("accountId", accountId);
    response.addProperty("salt", salt);
    response.addProperty("encryptedData", encryptedData);
    response.addProperty("decryptedData", decryptedData);
    return ResponseEntity.status(HttpStatus.OK).body(response.toString());
  }
}
