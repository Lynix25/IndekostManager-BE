package com.indekos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indekos.services.TransactionService;

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
