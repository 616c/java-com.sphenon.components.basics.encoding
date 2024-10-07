package com.sphenon.basics.encoding.test;

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
import com.sphenon.basics.monitoring.*;
import com.sphenon.basics.notification.*;
import com.sphenon.basics.customary.*;
import com.sphenon.basics.testing.*;

import com.sphenon.basics.encoding.*;

import java.io.StringWriter;

public class Test_Basics extends com.sphenon.basics.testing.classes.TestBase {

    public Test_Basics (CallContext context) {
    }

    public String getId (CallContext context) {
        if (this.id == null) {
            this.id = "EncodingBasics";
        }
        return this.id;
    }

    public TestResult perform (CallContext context, TestRun test_run) {

        try {

            String string;
            String recoding;
            String result;

//             string = "%00, %01, %02, %03, %04, %05, %06, %07";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%08, %09, %0A, %0B, %0C, %0D, %0E, %0F";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%10, %11, %12, %13, %14, %15, %16, %17";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%18, %19, %1A, %1B, %1C, %1D, %1E, %1F";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

            string = "%20, %21, %22, %23, %24, %25, %26, %27";
            result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

            string = "%28, %29, %2A, %2B, %2C, %2D, %2E, %2F";
            result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

            string = "%30, %31, %32, %33, %34, %35, %36, %37";
            result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

            string = "%38, %39, %3A, %3B, %3C, %3D, %3E, %3F";
            result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

            string = "%40, %41, %42, %43, %44, %45, %46, %47";
            result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

            string = "%48, %49, %4A, %4B, %4C, %4D, %4E, %4F";
            result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

            string = "%50, %51, %52, %53, %54, %55, %56, %57";
            result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

            string = "%58, %59, %5A, %5B, %5C, %5D, %5E, %5F";
            result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

            string = "%60, %61, %62, %63, %64, %65, %66, %67";
            result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

            string = "%68, %69, %6A, %6B, %6C, %6D, %6E, %6F";
            result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

            string = "%70, %71, %72, %73, %74, %75, %76, %77";
            result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

            string = "%78, %79, %7A, %7B, %7C, %7D, %7E, %7F";
            result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

            for (int a=0; a<16; a++) {
                string = "";
                String what = "";
                for (int b=0; b<16; b++) {
                    string += new Character((char)(a*16+b));
                    what += "\\x" + Encoding.hex[(a*16+b)];
                }
                result = Encoding.recode(context, string, Encoding.UTF8, Encoding.JAVA);
                CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", what, "result", result);
            }

            string = "\u0123\u1234\u5678\uA000\uB000\uFFFF";
            String what = "\\u0123\\u1234\\u5678\\uA000\\uB000\\uFFFF";
            result = Encoding.recode(context, string, Encoding.UTF8, Encoding.JAVA);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", what, "result", result);

//             string = "%80, %81, %82, %83, %84, %85, %86, %87";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%88, %89, %8A, %8B, %8C, %8D, %8E, %8F";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%90, %91, %92, %93, %94, %95, %96, %97";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%98, %99, %9A, %9B, %9C, %9D, %9E, %9F";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%A0, %A1, %A2, %A3, %A4, %A5, %A6, %A7";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%A8, %A9, %AA, %AB, %AC, %AD, %AE, %AF";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%B0, %B1, %B2, %B3, %B4, %B5, %B6, %B7";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%B8, %B9, %BA, %BB, %BC, %BD, %BE, %BF";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%C0, %C1, %C2, %C3, %C4, %C5, %C6, %C7";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%C8, %C9, %CA, %CB, %CC, %CD, %CE, %CF";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%D0, %D1, %D2, %D3, %D4, %D5, %D6, %D7";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%D8, %D9, %DA, %DB, %DC, %DD, %DE, %DF";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%E0, %E1, %E2, %E3, %E4, %E5, %E6, %E7";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%E8, %E9, %EA, %EB, %EC, %ED, %EE, %EF";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%F0, %F1, %F2, %F3, %F4, %F5, %F6, %F7";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

//             string = "%F8, %F9, %FA, %FB, %FC, %FD, %FE, %FF";
//             result = Encoding.recode(context, string, Encoding.URI, Encoding.UTF8);
//             CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

            string = "%78, %79, %7A, %7B, %7C, %7D, %7E, %7F";
            recoding = "URI/UTF8";
            result = Encoding.recodeByString(context, string, recoding);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' =='%(recoding)'==> '%(result)'", "string", string, "result", result, "recoding", recoding);

            string = "%78, %79, %7A, %7B, %7C, %7D, %7E, %7F";
            recoding = "URI/UTF8/URI";
            result = Encoding.recodeByString(context, string, recoding);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' =='%(recoding)'==> '%(result)'", "string", string, "result", result, "recoding", recoding);

            string = "%78, %79, %7A, %7B, %7C, %7D, %7E, %7F";
            recoding = "URI/UTF8/ABBREV(8)";
            result = Encoding.recodeByString(context, string, recoding);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' =='%(recoding)'==> '%(result)'", "string", string, "result", result, "recoding", recoding);

            string = "%78, %79, %7A, %7B, %7C, %7D, %7E, %7F";
            recoding = "URI/UTF8//UTF8/ABBREV(8)";
            result = Encoding.recodeByString(context, string, recoding);
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' =='%(recoding)'==> '%(result)'", "string", string, "result", result, "recoding", recoding);

            {
                EncodingStep[] encoding_steps = {
                    new EncodingStep(context, Encoding.UTF8),
                    new EncodingStep(context, Encoding.INDENT, "    ", 1)
                };
                StringWriter string_writer = new StringWriter();
                EncodingWriter encoding_writer = new EncodingWriter(context, string_writer);
                encoding_writer.setEncodingSteps(context, encoding_steps);
                encoding_writer.write("Hallihallo,\nHallihallo,\nHallihallo!\n");
                encoding_writer.write("Hallihallo,\nHallihallo,\nHalli");
                encoding_writer.write("hallo,\nHallihallo,\nHallihallo!\nHallihallo!");
                encoding_writer.write("\nHallihallo,\nHallihallo!\nHallihallo!\n");
                encoding_writer.flush();

                CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Encoding writer produces: '%(result)'", "result", string_writer.toString());
            }

            {
                long start = System.currentTimeMillis();
                for (int i=0; i<1000; i++) {
                    StringWriter string_writer = new StringWriter();
                    for (int j=0; j<1000; j++) {
                        string_writer.write("Hallihallo, Hallihallo, Hallihallo!\n");
                    }
                }
                long stop  = System.currentTimeMillis();

                CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Normal writer, 1000 x 1000 passes: %(duration) ms", "duration", stop-start);
            }

            {
                long start = System.currentTimeMillis();
                for (int i=0; i<1000; i++) {
                    StringWriter string_writer = new StringWriter();
                    EncodingWriter encoding_writer = new EncodingWriter(context, string_writer);
                    for (int j=0; j<1000; j++) {
                        encoding_writer.write("Hallihallo, Hallihallo, Hallihallo!\n");
                    }
                }
                long stop  = System.currentTimeMillis();

                CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Encoding writer, no encoding, 1000 x 1000 passes: %(duration) ms", "duration", stop-start);
            }

            {
                long start = System.currentTimeMillis();
                EncodingStep[] encoding_steps = {
                    new EncodingStep(context, Encoding.UTF8),
                    new EncodingStep(context, Encoding.INDENT, "    ", 1)
                };
                for (int i=0; i<1000; i++) {
                    StringWriter string_writer = new StringWriter();
                    EncodingWriter encoding_writer = new EncodingWriter(context, string_writer);
                    encoding_writer.setEncodingSteps(context, encoding_steps);
                    for (int j=0; j<1000; j++) {
                        encoding_writer.write("Hallihallo, Hallihallo, Hallihallo!\n");
                    }
                }
                long stop  = System.currentTimeMillis();

                CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Encoding writer, no encoding, 1000 x 10000 passes: %(duration) ms", "duration", stop-start);
            }

            string = "Hallo, das ist ja Mist";
            result = Encoding.recode(context, string, Encoding.UTF8, Encoding.REGEXP, "Mist", "super toll!");
            CustomaryContext.create((Context)context).sendTrace(context, Notifier.CHECKPOINT, "Recoding '%(string)' => '%(result)'", "string", string, "result", result);

        } catch (Throwable t) {
            return new TestResult_ExceptionRaised(context, t);
        }
        
        return TestResult.OK;
    }
}

