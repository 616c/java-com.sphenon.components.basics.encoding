package com.sphenon.basics.encoding;

/****************************************************************************
  Copyright 2001-2018 Sphenon GmbH

  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  License for the specific language governing permissions and limitations
  under the License.
*****************************************************************************/

import com.sphenon.basics.context.*;

import java.io.IOException;

public class CharSourceCharSequence implements  CharSource {
    protected CharSequence sequence;
    protected int length;
    protected int pos;

    public CharSourceCharSequence(CallContext context, CharSequence sequence) {
        this.sequence = sequence;
        this.length = sequence.length();
        this.pos = 0;
    }

    public CharSequence getCharSequence(CallContext context) {
        return sequence;
    }

    public int read(CallContext context) {
        return (this.pos < this.length ? this.sequence.charAt(pos++) : -1);
    }

    public int length(CallContext context) {
        return this.length;
    }

    public void appendTo(CallContext context, Appendable appendable) throws IOException {
        if (this.sequence != null) { appendable.append(this.sequence); }
    }
}