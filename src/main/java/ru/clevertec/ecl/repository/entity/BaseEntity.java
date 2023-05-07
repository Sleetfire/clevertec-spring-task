package ru.clevertec.ecl.repository.entity;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> {

    T getId();
}
