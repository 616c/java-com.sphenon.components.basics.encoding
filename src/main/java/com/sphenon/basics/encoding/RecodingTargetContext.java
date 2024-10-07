package com.sphenon.basics.encoding;

/****************************************************************************
  Copyright 2001-2024 Sphenon GmbH

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

public class RecodingTargetContext {

    public RecodingTargetContext(CallContext context) {
        this.beginning_of_line = true;
    }

    protected boolean beginning_of_line;

    public boolean getBeginningOfLine (CallContext context) {
        return this.beginning_of_line;
    }

    public void setBeginningOfLine (CallContext context, boolean beginning_of_line) {
        this.beginning_of_line = beginning_of_line;
    }

    public<T extends CharSequence> T processOutput(CallContext context, T string) {
        int len;
        if (   string != null
            && (len = string.length()) > 0) {
            this.beginning_of_line = (string.charAt(len-1) == '\n');
        }
        return string;
    }

    public Appendable getOutputProcessor(CallContext context, final Appendable appendable) {
        return new Appendable() {
            public Appendable append(char c) throws IOException {
                beginning_of_line = (c == '\n');
                appendable.append(c);
                return this;
            }
            public Appendable append(CharSequence csq) throws IOException {
                int len;
                if (    csq != null
                     && (len = csq.length()) > 0) {
                    beginning_of_line = (csq.charAt(len-1) == '\n');
                }
                appendable.append(csq);
                return this;
            }
            public Appendable append(CharSequence csq, int start, int end) throws IOException {
                int len;
                if (    csq != null
                     && (len = csq.length()) > 0) {
                    if (len > end) { len = end; }
                    beginning_of_line = (csq.charAt(len-1) == '\n');
                }
                appendable.append(csq, start, end);
                return this;
            }
        };
    }
}
