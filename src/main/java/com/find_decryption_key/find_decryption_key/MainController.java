package com.find_decryption_key.find_decryption_key;

import com.find_decryption_key.find_decryption_key.model.EncryptionData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index(Model model) {
        EncryptionData encryptionData = new EncryptionData();
        model.addAttribute("encryptionData", encryptionData);
        return "index.html";
    }

}
