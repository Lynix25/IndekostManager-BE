package com.indekosservice.services;

import com.indekosservice.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransaksiService {
    @Autowired
    TransaksiRepository transaksiRepository;
}
