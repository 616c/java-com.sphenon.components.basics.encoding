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
import com.sphenon.basics.exception.*;
import com.sphenon.basics.customary.*;

import java.io.Reader;
import java.io.IOException;

public class CharSourceReader implements  CharSource {
    protected Reader reader;

    public CharSourceReader(CallContext context, Reader reader) {
        this.reader = reader;
    }

    public int read(CallContext context) {
        try {
            return reader.read();
        } catch (IOException ioe) {
            CustomaryContext.create((Context)context).throwEnvironmentFailure(context, ioe, "Could not recode char sequence, reading from reader failed");
            throw (ExceptionEnvironmentFailure) null; // compiler insists
        }
    }

    public int length(CallContext context) {
        return -1;
    }

    public void appendTo(CallContext context, Appendable appendable) throws IOException {
        int i;
        while ((i = read(context)) != -1) {
            appendable.append((char) i);
        }
    }
}
