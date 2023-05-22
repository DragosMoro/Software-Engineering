package com.example.implementation.Repository;

import com.example.implementation.Model.Boss;

public interface IBossRepository extends IRepository<Boss>{
    Boss findByEmailAndPassword(String email, String password);
}
