package org.example.beans;

import org.example.annotations.MyBean;
import org.example.annotations.MyEntity;
import org.example.annotations.MyId;

@MyBean
@MyEntity
public class Account {

    @MyId
    private String account_number;
}
