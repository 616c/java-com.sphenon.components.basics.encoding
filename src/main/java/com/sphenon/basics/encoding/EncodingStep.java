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
import com.sphenon.basics.context.classes.*;
import com.sphenon.basics.configuration.*;
import com.sphenon.basics.message.*;
import com.sphenon.basics.exception.*;
import com.sphenon.basics.notification.*;
import com.sphenon.basics.customary.*;

import java.util.Arrays;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodingStep {

    static public EncodingStep[] build(String... encodings) {
        CallContext context = RootContext.getFallbackCallContext();
        EncodingStep[] steps = new EncodingStep[encodings.length];
        int i=0;
        for (String encoding : encodings) {
            String[] e = encoding.split("/");
            Object[] options = Arrays.copyOfRange(e, 1, e.length);
            Encoding enc = Encoding.getEncoding(context, e[0]);
            steps[i++] = new EncodingStep(context, enc, options);
        }
        return steps;
    }

    static public EncodingStep[] buildFromString(CallContext context, String encodings) {
        String[] encsteps = encodings.split("/",-1);
        EncodingStep[] steps = new EncodingStep[encsteps.length];
        int s=0;
        for (String encstep : encsteps) {
            Object[] options = null;
            if (encstep != null) {
                int pos = encstep.indexOf('(');
                if (pos == -1) { pos = encstep.indexOf('['); }
                if (pos != -1) {
                    int l = encstep.length();
                    Character last = encstep.charAt(l - 1);
                    if (last == ')' || last == ']') {
                        String[] os = encstep.substring(pos+1, l - 1).split(",",-1);
                        options = new Object[os.length];
                        for (int i=0; i<os.length; i++) {
                            if (os[i].matches("[0-9]+")) {
                                options[i] = Integer.parseInt(os[i]);
                            } else if (os[i].matches("\".*\"")) {
                                options[i] = os[i].substring(1,os[i].length()-1);
                            } else {
                                options[i] = os[i];
                            }
                        }
                    }
                }
                Encoding enc = Encoding.getEncoding(context, pos == -1 ? encstep : encstep.substring(0, pos));
                steps[s++] = new EncodingStep(context, enc, options);
            } else {
                steps[s++] = null;
            }
        }
        return steps;
    }

    public EncodingStep (CallContext context, Encoding encoding, Object... options) {
        this.encoding = encoding;
        this.options = options;
    }

    protected Encoding encoding;

    public Encoding getEncoding (CallContext context) {
        return this.encoding;
    }

    public void setEncoding (CallContext context, Encoding encoding) {
        this.encoding = encoding;
    }

    protected Object[] options;

    public Object[] getOptions (CallContext context) {
        return this.options;
    }

    public void setOptions (CallContext context, Object[] options) {
        this.options = options;
    }

}
