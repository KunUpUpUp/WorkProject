package com.seasugar.domain;

import lombok.Data;

@Data
public class Test {
    private String name;
    private byte[] myByte = new byte[1 * 1024 * 1024];
}
