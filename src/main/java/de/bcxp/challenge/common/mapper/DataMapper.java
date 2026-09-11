package de.bcxp.challenge.common.mapper;

public interface DataMapper<F, T> {
    T map(F record);
}
