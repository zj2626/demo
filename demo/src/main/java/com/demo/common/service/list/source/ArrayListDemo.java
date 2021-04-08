package com.demo.common.service.list.source;

/*
 * Copyright (c) 1997, 2017, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

import java.util.ArrayList;
import java.util.Collection;

public class ArrayListDemo<E> extends ArrayList<E> {

    public ArrayListDemo() {
        super();
    }

    public ArrayListDemo(int initialCapacity) {
        super(initialCapacity);
    }

    public ArrayListDemo(Collection<? extends E> c) {
        super(c);
    }
}
