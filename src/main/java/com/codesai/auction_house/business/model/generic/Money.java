package com.codesai.auction_house.business.model.generic;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Money {
    public final double amount;
    public final Currency currency;

    private Money(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money of(double amount, Currency currency) {
        return new Money(amount, currency);
    }

    public static Money of(double amount) {
        return new Money(amount, Currency.EUR);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean isGreaterThan(Money money) {
        return this.amount > money.amount;
    }
}

