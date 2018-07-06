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

import java.io.Writer;
import java.io.IOException;

public class EncodingWriter extends Writer {

    protected Writer                writer;
    protected CallContext           context;
    protected EncodingStep[]        encoding_steps;
    protected RecodingTargetContext recoding_target_context;

    public EncodingWriter (CallContext context, Writer writer) {
        this.context                 = context;
        this.writer                  = writer;
        this.recoding_target_context = new RecodingTargetContext(context);
    }

    public void setEncodingSteps(CallContext context, EncodingStep[] encoding_steps) {
        this.encoding_steps = encoding_steps;
        EncodingStep previous = null;
        boolean necessary = false;
        if (this.encoding_steps != null) {
            for (EncodingStep step : this.encoding_steps) {
                if (   previous != null
                    && previous.getEncoding(context) != step.getEncoding(context)) {
                    necessary = true;
                    break;
                }
                previous = step;
            }
            if ( ! necessary) {
                this.encoding_steps = null;
            }
        }
    }

    public void setContext(CallContext context) {
        this.context = context;
    }

    public Writer append(char c) throws IOException {
        if (this.encoding_steps == null) {
            this.writer.append(c);
        } else {
            this.writer.append(Encoding.recode(context, c, this.encoding_steps, this.recoding_target_context));
        }
        return this;
    }

    public Writer append(CharSequence csq) throws IOException {
        if (this.encoding_steps == null) {
            this.writer.append(csq);
        } else {
            this.writer.append(Encoding.recode(context, csq, this.encoding_steps, this.recoding_target_context));
        }
        return this;
    }

    public Writer append(CharSequence csq, int start, int end) throws IOException {
        if (this.encoding_steps == null) {
            this.writer.append(csq, start, end);
        } else {
            this.writer.append(Encoding.recode(context, csq.subSequence(start, end), this.encoding_steps, this.recoding_target_context));
        }
        return this;
    }

    public void close() throws IOException {
        this.writer.close();
    }

    public void flush() throws IOException {
        this.writer.flush();
    }

    public void write(char[] cbuf) throws IOException {
        if (this.encoding_steps == null) {
            this.writer.write(cbuf);
        } else {
            this.writer.append(Encoding.recode(context, new String(cbuf), this.encoding_steps, this.recoding_target_context));
        }
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        if (this.encoding_steps == null) {
            this.writer.write(cbuf, off, len);
        } else {
            this.writer.append(Encoding.recode(context, new String(cbuf, off, len), this.encoding_steps, this.recoding_target_context));
        }
    }

    public void write(int c) throws IOException {
        if (this.encoding_steps == null) {
            this.writer.write(c);
        } else {
            this.writer.append(Encoding.recode(context, (char) c, this.encoding_steps, this.recoding_target_context));
        }
    }

    public void write(String str) throws IOException {
        if (this.encoding_steps == null) {
            this.writer.write(str);
        } else {
            this.writer.append(Encoding.recode(context, str, this.encoding_steps, this.recoding_target_context));
        }
    }

    public void write(String str, int off, int len) throws IOException {
        if (this.encoding_steps == null) {
            this.writer.write(str, off, len);
        } else {
            this.writer.append(Encoding.recode(context, str.substring(off, off+len), this.encoding_steps, this.recoding_target_context));
        }
    }
}
