package com.indekos.controller;

import com.indekos.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.indekos.services.TransactionService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/transaksi")
public class TransaksiController {
    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity create(){

        return new ResponseEntity(HttpStatus.OK);
    }
}
