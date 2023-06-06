package com.example.implementation.Repository;

import com.example.implementation.Model.CheckOut;

import java.time.LocalDateTime;
import java.util.List;

public interface ICheckOutRepository {
    void add(CheckOut checkOut);

    List<CheckOut> getCheckOutsAfter(LocalDateTime lastCheckTime);
}
