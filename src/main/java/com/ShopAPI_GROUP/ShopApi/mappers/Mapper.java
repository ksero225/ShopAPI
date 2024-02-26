package com.ShopAPI_GROUP.ShopApi.mappers;

public interface Mapper<classA, classB> {
    classB mapTo(classA a);

    classA mapFrom(classB b);
}
