package org.example.beans;

import org.example.annotations.MyEntity;
import org.example.annotations.MyId;

@MyEntity
public class Account {

    @MyId
    private String account_number;
}
