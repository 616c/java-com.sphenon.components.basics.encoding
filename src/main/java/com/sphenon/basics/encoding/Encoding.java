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
import com.sphenon.basics.context.classes.*;
import com.sphenon.basics.configuration.*;
import com.sphenon.basics.message.*;
import com.sphenon.basics.exception.*;
import com.sphenon.basics.notification.*;
import com.sphenon.basics.customary.*;
import com.sphenon.basics.services.*;

import java.util.regex.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
import java.text.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.Reader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.nio.CharBuffer;

public enum Encoding {

    NONE,       // no change in encoding; can be used to concatenate nonrelated
                // encodings, like e.g. MC/LC/NONE/UTF8/JAVASQLID
                // this results in two encodings: MC/LC and NONE/UTF8
    ID,         // general identifier, containing digits, letters and underscore
    TPLPH,      // basic template string, containing placeholders ${...}
    URI,        // Universal Resource Identifier   -  escaping with %xx
    URIFORM,    // Universal Resource Identifier codierung für Formulare
    UTF8,       // UTF-8
    SHA1,       // hexadecimal encoding of SHA1 digest
    VSA,        // Very Safe ASCII - escaping with _xx for all but alphanumeric
    VSAU,       // Rather Safe ASCII - escaping with _xx for all but alphanumeric and underscore
    FILENAME,   // rather safe ASCII, usable as filename
    JAVA,       // content of a string literal in Java, i.e. "...."
    JAVASCRIPT, // LEGACY: please use JSSINGLE/DOUBLE for clarity instead
    JSDOUBLE,   // content of a double quoted string literal in JavaScript, i.e. "...."
    JSSINGLE,   // content of a single quoted string literal in JavaScript, i.e. '....'
    JAVAID,     // identifier in java, i.e. keywords like "interface" are prefixed
    JAVASQLID,  // identifier in java as well as sql, i.e. keywords like "interface" and "procedure" are prefixed
    JAVAPROP,   // plain old Java property file (\ uXXXX encoding)
    CSV,        // content of a double quoted string literal in CSV, i.e. "....""...."
    QUOTEDD,    // content of a general double quoted backslash escaped string literal, i.e. "...\"....\\...."
    QUOTEDS,    // content of a general single quoted backslash escaped string literal, i.e. '...\'....\\....'
    DOCBOOK,    // well, DocBook
    DOCPAGE,    // DocBook, with extended Metadata
    DOCLET,     // Organised, categorised DOCPAGEs
    JAVADOC,    // javadoc plain ASCII comment syntax
    HTML,       // HTML escaped
    HTMLPRE,    // HTML escaped, preformatted
    WIKI,       // Mediawiki page syntax
    XML,        // XML element content
    XMLATT,     // XML attribute content
    XMLITEXT,   // indented xml text, formatted partially to be nice within xml
                // context, and partially for output
    MC,         // a word in MixedCaseSpelling
    MCB,        // a word in Mixed Case Blank Spelling
    LCU,        // a word in lower_case_underscore_spelling
    LCD,        // a word in lower-case-dash-spelling
    LC,         // a word in lowercasespelling
    UCU,        // a word in UPPER_CASE_UNDERSCORE_SPELLING
    UCD,        // a word in UPPER_CASE_DASH_SPELLING
    UC,         // a word in UPPERCASESPELLING
    CB,         // a word in camelBackSpelling
    STUC,       // a word in Stupidcasespelling
    SQL,        // content of a string literal in SQL, i.e. '....'
    SQLID,      // identifier in sql, i.e. keywords like "select" are prefixed
    INDENT,     // a text indented by some amount
    ABBREV,     // an abbreviated text
    INTEGER,    // a text containing of an integer
    FLOAT,      // a text containing of a float
    FORMAT,     // a formatted text, created with printf-like methods
    REGEXP,     // a text substituted with a regular expression pattern and replacement
    REESC,      // a text escaped for use in regexps as a static string match
    JSON,       // JavaScript Object Notation
    TEX,        // (La)TeX text
    BASE64,     // well, sigh
    DSP,        // dot.separated.path
    SSP,        // slash/separated/path
    FIXED      // string with fixed length
    ;

    static public Encoding getEncoding(CallContext context, String encoding) {
        encoding = encoding.replaceFirst("\\(.*","");
        if (encoding.equalsIgnoreCase("NONE"        )) { return NONE        ; }
        if (encoding.equalsIgnoreCase("ID"          )) { return ID          ; }
        if (encoding.equalsIgnoreCase("TPLPH"       )) { return TPLPH       ; }
        if (encoding.equalsIgnoreCase("URI"         )) { return URI         ; }
        if (encoding.equalsIgnoreCase("URIFORM"     )) { return URIFORM     ; }
        if (encoding.equalsIgnoreCase("UTF8"        )) { return UTF8        ; }
        if (encoding.equalsIgnoreCase("SHA1"        )) { return SHA1        ; }
        if (encoding.equalsIgnoreCase("VSA"         )) { return VSA         ; }
        if (encoding.equalsIgnoreCase("VSAU"        )) { return VSAU        ; }
        if (encoding.equalsIgnoreCase("FILENAME"    )) { return FILENAME    ; }
        if (encoding.equalsIgnoreCase("JAVA"        )) { return JAVA        ; }
        if (encoding.equalsIgnoreCase("JAVASCRIPT"  )) { return JAVASCRIPT  ; }
        if (encoding.equalsIgnoreCase("JSSINGLE"    )) { return JSSINGLE    ; }
        if (encoding.equalsIgnoreCase("JSDOUBLE"    )) { return JSDOUBLE    ; }
        if (encoding.equalsIgnoreCase("JAVAID"      )) { return JAVAID      ; }
        if (encoding.equalsIgnoreCase("JAVASQLID"   )) { return JAVASQLID   ; }
        if (encoding.equalsIgnoreCase("JAVAPROP"    )) { return JAVAPROP    ; }
        if (encoding.equalsIgnoreCase("CSV"         )) { return CSV         ; }
        if (encoding.equalsIgnoreCase("QUOTEDD"     )) { return QUOTEDD     ; }
        if (encoding.equalsIgnoreCase("QUOTEDS"     )) { return QUOTEDS     ; }
        if (encoding.equalsIgnoreCase("DOCBOOK"     )) { return DOCBOOK     ; }
        if (encoding.equalsIgnoreCase("DOCPAGE"     )) { return DOCPAGE     ; }
        if (encoding.equalsIgnoreCase("DOCLET"      )) { return DOCLET      ; }
        if (encoding.equalsIgnoreCase("JAVADOC"     )) { return JAVADOC     ; }
        if (encoding.equalsIgnoreCase("HTML"        )) { return HTML        ; }
        if (encoding.equalsIgnoreCase("HTMLPRE"     )) { return HTMLPRE     ; }
        if (encoding.equalsIgnoreCase("WIKI"        )) { return WIKI        ; }
        if (encoding.equalsIgnoreCase("XML"         )) { return XML         ; }
        if (encoding.equalsIgnoreCase("XMLATT"      )) { return XMLATT      ; }
        if (encoding.equalsIgnoreCase("XMLITEXT"    )) { return XMLITEXT    ; }
        if (encoding.equalsIgnoreCase("MC"          )) { return MC          ; }
        if (encoding.equalsIgnoreCase("LCU"         )) { return LCU         ; }
        if (encoding.equalsIgnoreCase("MCB"         )) { return MCB         ; }
        if (encoding.equalsIgnoreCase("LC"          )) { return LC          ; }
        if (encoding.equalsIgnoreCase("UCU"         )) { return UCU         ; }
        if (encoding.equalsIgnoreCase("UC"          )) { return UC          ; }
        if (encoding.equalsIgnoreCase("CB"          )) { return CB          ; }
        if (encoding.equalsIgnoreCase("STUC"        )) { return STUC        ; }
        if (encoding.equalsIgnoreCase("SQL"         )) { return SQL         ; }
        if (encoding.equalsIgnoreCase("SQLID"       )) { return SQLID       ; }
        if (encoding.equalsIgnoreCase("INDENT"      )) { return INDENT      ; }
        if (encoding.equalsIgnoreCase("ABBREV"      )) { return ABBREV      ; }
        if (encoding.equalsIgnoreCase("INTEGER"     )) { return INTEGER     ; }
        if (encoding.equalsIgnoreCase("FLOAT"       )) { return FLOAT       ; }
        if (encoding.equalsIgnoreCase("FORMAT"      )) { return FORMAT      ; }
        if (encoding.equalsIgnoreCase("REGEXP"      )) { return REGEXP      ; }
        if (encoding.equalsIgnoreCase("REESC"       )) { return REESC       ; }
        if (encoding.equalsIgnoreCase("JSON"        )) { return JSON        ; }
        if (encoding.equalsIgnoreCase("TEX"         )) { return TEX        ; }
        if (encoding.equalsIgnoreCase("BASE64"      )) { return BASE64      ; }
        if (encoding.equalsIgnoreCase("DSP"         )) { return DSP         ; }
        if (encoding.equalsIgnoreCase("SSP"         )) { return SSP         ; }
        if (encoding.equalsIgnoreCase("FIXED"       )) { return FIXED       ; }
        return null;
    }

    static protected Pattern optpat;
    static {
        try {
            optpat = Pattern.compile("^[^\\(]*\\(([^\\)]*)\\)$");
        } catch (PatternSyntaxException pse) {
        }
    }

    static public Object[] getOptions(CallContext context, String encoding) {
        Matcher matcher = optpat.matcher(encoding);
        if ( ! matcher.matches()) {
            return null;
        }

        String[] strings = matcher.group(1).split(",");
        Object[] options = new Object[strings.length];
        int i=0;
        for (String string : strings) {
            options[i++] = recode(context, string, Encoding.URI, Encoding.UTF8);
        }
        return options;
    }

    static public interface LinkRecoder {
        public String recode(CallContext context, String link, HashMap<String,String> attributes);
    }

    static protected Pattern uri_escape;
    static protected Pattern vsa_escape;
    static protected Pattern link_pattern;
    static protected Pattern link_att_pattern;
    static protected Pattern medialink_pattern;
    static protected Pattern oorl_pattern;

    static protected boolean initialised;

    static protected void initialise(CallContext context) {
        if (initialised == false) {
            synchronized (Encoding.class) {
                if (initialised == false) {
                    initialised = true;

                    String current_regexp = null;
                    try {

                        current_regexp = "%([A-Fa-f0-9][A-Fa-f0-9])";
                        uri_escape = Pattern.compile(current_regexp);

                        current_regexp = "_([A-Fa-f0-9][A-Fa-f0-9])";
                        vsa_escape = Pattern.compile(current_regexp);

                        current_regexp = "(?:(<(?:link|mediaobject|inlinemediaobject))(?:\\s+([^>]*))?>)|(?:(</(?:link|mediaobject|inlinemediaobject)>))";
                        link_pattern = Pattern.compile(current_regexp);

                        current_regexp = "([A-Za-z0-9_:-]+)=\"([^\">]*)\"\\s*";
                        link_att_pattern = Pattern.compile(current_regexp);

                        current_regexp = "\\s*<imageobject>\\s*<imagedata\\s+fileref\\s*=\\s*\"([^\"]*)\"\\s*/>\\s*</imageobject>\\s*(?:<textobject>([^<]*)</textobject>\\s*)?";
                        medialink_pattern = Pattern.compile(current_regexp);

                        current_regexp = "\"(oorl:[^\"]*)\"";
                        oorl_pattern = Pattern.compile(current_regexp);

                    } catch (PatternSyntaxException pse) {
                        CustomaryContext.create(Context.create(context)).throwAssertionProvedFalse(context, pse, "Syntax error in com.sphenon.basics.encoding.Encoding in regular expression '%(regexp)'", "regexp", current_regexp);
                        throw (ExceptionAssertionProvedFalse) null; // compiler insists
                    }
                }
            }
        }
    }

    // ----------

    static public String recodeByString(CallContext context, Object object, String recoding) {
        EncodingStep[] steps = EncodingStep.buildFromString(context, recoding);
        return recode(context, com.sphenon.basics.message.t.s(context, object), steps, null);
    }

    static public StringBuilder recodeByString(CallContext context, Object object, StringBuilder output, String recoding) {
        EncodingStep[] steps = EncodingStep.buildFromString(context, recoding);
        return recode(context, com.sphenon.basics.message.t.s(context, object), output, steps, null);
    }

    static public StringBuilder recodeByString(CallContext context, Object object, Appendable appendable, String recoding) {
        EncodingStep[] steps = EncodingStep.buildFromString(context, recoding);
        return recode(context, com.sphenon.basics.message.t.s(context, object), appendable, steps, null);
    }

    // ----------

    static public String recode(CallContext context, Object object, EncodingStep[] steps) {
        return recode(context, com.sphenon.basics.message.t.s(context, object), steps, null);
    }

    static public StringBuilder recode(CallContext context, Object object, StringBuilder output, EncodingStep[] steps) {
        return recode(context, com.sphenon.basics.message.t.s(context, object), output, steps, null);
    }

    static public StringBuilder recode(CallContext context, Object object, Appendable appendable, EncodingStep[] steps) {
        return recode(context, com.sphenon.basics.message.t.s(context, object), appendable, steps, null);
    }

    // ----------

    static public String recode(CallContext context, Object object, EncodingStep[] steps, RecodingTargetContext recoding_target_context) {
        return recode(context, com.sphenon.basics.message.t.s(context, object), steps, recoding_target_context);
    }

    static public StringBuilder recode(CallContext context, Object object, StringBuilder output, EncodingStep[] steps, RecodingTargetContext recoding_target_context) {
        return recode(context, com.sphenon.basics.message.t.s(context, object), output, steps, recoding_target_context);
    }

    static public StringBuilder recode(CallContext context, Object object, Appendable appendable, EncodingStep[] steps, RecodingTargetContext recoding_target_context) {
        return recode(context, com.sphenon.basics.message.t.s(context, object), appendable, steps, recoding_target_context);
    }

    // ----------

    static public String recode(CallContext context, String string, EncodingStep[] steps, RecodingTargetContext recoding_target_context) {
        if (steps == null) {
            return recoding_target_context == null ? string : recoding_target_context.processOutput(context, string);
        }
        
        EncodingStep previous = null;
        for (EncodingStep step : steps) {
            if (previous != null && step != null) {
                string = recode(context, string, previous, step, recoding_target_context);
            }
            previous = step;
        }
        return string;
    }

    // only tested in a few situations yet
    static public StringBuilder recode(CallContext context, CharSequence string, StringBuilder output, EncodingStep[] steps, RecodingTargetContext recoding_target_context) {
        if (steps == null || steps.length == 0) {
            return recode(context, string, output, null, null, recoding_target_context);
        }
        
        EncodingStep previous = null;
        EncodingStep last = null;
        for (EncodingStep step : steps) {
            if (previous != null && step != null) {
                last = step;
            }
            previous = step;
        }

        if (last == null) {
            return recode(context, string, output, null, null, recoding_target_context);
        }

        previous = null;
        for (EncodingStep step : steps) {
            if (previous != null && step != null) {
                if (step != last) {
                    string = recode(context, string, null, previous, step, recoding_target_context);
                } else {
                    output = recode(context, string, output, previous, step, recoding_target_context);
                }
            }
            previous = step;
        }

        return output;
    }

    static public StringBuilder recode(CallContext context, CharSource string, Appendable appendable, EncodingStep[] steps, RecodingTargetContext recoding_target_context) {
        if (steps == null || steps.length == 0) {
            return recode(context, string, appendable, null, null, recoding_target_context);
        }

        EncodingStep previous = null;
        EncodingStep last = null;
        for (EncodingStep step : steps) {
            if (previous != null && step != null) {
                last = step;
            }
            previous = step;
        }

        if (last == null) {
            return recode(context, string, appendable, null, null, recoding_target_context);
        }

        StringBuilder new_output = null;
        previous = null;
        for (EncodingStep step : steps) {
            if (previous != null && step != null) {
                if (step != last) {
                    string = new CharSourceCharSequence(context, recode(context, string, (Appendable) null, previous, step, recoding_target_context));
                } else {
                    new_output = recode(context, string, appendable, previous, step, recoding_target_context);
                }
            }
            previous = step;
        }

        return new_output;
    }

    static public StringBuilder recode(CallContext context, CharSequence sequence, Appendable appendable, EncodingStep[] steps, RecodingTargetContext recoding_target_context) {
        return recode(context, sequence == null ? null : new CharSourceCharSequence(context, sequence), appendable, steps, recoding_target_context);
    }

    static public StringBuilder recode(CallContext context, Reader reader, Appendable appendable, EncodingStep[] steps, RecodingTargetContext recoding_target_context) {
        return recode(context, reader == null ? null : new CharSourceReader(context, reader), appendable, steps, recoding_target_context);
    }

    // ----------

    static public String recode(CallContext context, String string, EncodingStep source, EncodingStep target) {
        return recode(context, string, source.getEncoding(context), target.getEncoding(context), target.getOptions(context));
    }

    static public StringBuilder recode(CallContext context, CharSequence string, StringBuilder output, EncodingStep source, EncodingStep target) {
        return recode(context, string, output, source.getEncoding(context), target.getEncoding(context), target.getOptions(context));
    }

    static public StringBuilder recode(CallContext context, CharSource string, Appendable appendable, EncodingStep source, EncodingStep target) {
        return recode(context, string, appendable, source.getEncoding(context), target.getEncoding(context), target.getOptions(context));
    }

    static public StringBuilder recode(CallContext context, CharSequence sequence, Appendable appendable, EncodingStep source, EncodingStep target) {
        return recode(context, sequence == null ? null : new CharSourceCharSequence(context, sequence), appendable, source.getEncoding(context), target.getEncoding(context), target.getOptions(context));
    }

    static public StringBuilder recode(CallContext context, Reader reader, Appendable appendable, EncodingStep source, EncodingStep target) {
        return recode(context, reader == null ? null : new CharSourceReader(context, reader), appendable, source.getEncoding(context), target.getEncoding(context), target.getOptions(context));
    }

    // ----------

    static public String recode(CallContext context, String string, EncodingStep source, EncodingStep target, RecodingTargetContext recoding_target_context) {
        return recode(context, string, source.getEncoding(context), target.getEncoding(context), recoding_target_context, target.getOptions(context));
    }

    static public StringBuilder recode(CallContext context, CharSequence string, StringBuilder output, EncodingStep source, EncodingStep target, RecodingTargetContext recoding_target_context) {
        return recode(context, string, output, source.getEncoding(context), target.getEncoding(context), recoding_target_context, target.getOptions(context));
    }

    static public StringBuilder recode(CallContext context, CharSource string, Appendable appendable, EncodingStep source, EncodingStep target, RecodingTargetContext recoding_target_context) {
        return recode(context, string, appendable, source.getEncoding(context), target.getEncoding(context), recoding_target_context, target.getOptions(context));
    }

    static public StringBuilder recode(CallContext context, CharSequence sequence, Appendable appendable, EncodingStep source, EncodingStep target, RecodingTargetContext recoding_target_context) {
        return recode(context, sequence == null ? null : new CharSourceCharSequence(context, sequence), appendable, source.getEncoding(context), target.getEncoding(context), recoding_target_context, target.getOptions(context));
    }

    static public StringBuilder recode(CallContext context, Reader reader, Appendable appendable, EncodingStep source, EncodingStep target, RecodingTargetContext recoding_target_context) {
        return recode(context, reader == null ? null : new CharSourceReader(context, reader), appendable, source.getEncoding(context), target.getEncoding(context), recoding_target_context, target.getOptions(context));
    }

    // ----------

    static public String recode(CallContext context, String string, Encoding source, Encoding target, Object... options) {
        return recode(context, string, source, target, (RecodingTargetContext) null, options);
    }

    static public StringBuilder recode(CallContext context, CharSequence string, StringBuilder output, Encoding source, Encoding target, Object... options) {
        return recode(context, string, output, source, target, (RecodingTargetContext) null, options);
    }

    static public StringBuilder recode(CallContext context, CharSource string, Appendable appendable, Encoding source, Encoding target, Object... options) {
        return recode(context, string, appendable, source, target, (RecodingTargetContext) null, options);
    }

    static public StringBuilder recode(CallContext context, CharSequence sequence, Appendable appendable, Encoding source, Encoding target, Object... options) {
        return recode(context, sequence == null ? null : new CharSourceCharSequence(context, sequence), appendable, source, target, (RecodingTargetContext) null, options);
    }

    static public StringBuilder recode(CallContext context, Reader reader, Appendable appendable, Encoding source, Encoding target, Object... options) {
        return recode(context, reader == null ? null : new CharSourceReader(context, reader), appendable, source, target, (RecodingTargetContext) null, options);
    }

    // ----------

    static protected<T> T getOption(CallContext context, int index, T default_value, Object[] options) {
        if (options != null && options.length > index) {
            return (T) (options[index]);
        } else {
            return default_value;
        }
    }

    // ----------

    static public String recode(CallContext context, String string, Encoding source, Encoding target, RecodingTargetContext recoding_target_context, Object... options) {
        initialise(context);
        
        if (string == null) {
            return null;
        }

        if (source == target) {
        }
        else if (source == ID       && target == TPLPH     ) { string = recode_ID_TPLPH(context, string, recoding_target_context); }
        else if (source == URI      && target == UTF8      ) { string = recode_URI_UTF8(context, string, recoding_target_context); }
        else if (source == UTF8     && target == URI       ) { string = recode_UTF8_URI(context, string, recoding_target_context); }
        else if (source == URIFORM  && target == UTF8      ) { string = recode_URIFORM_UTF8(context, string, recoding_target_context); }
        else if (source == UTF8     && target == URIFORM   ) { string = recode_UTF8_URIFORM(context, string, recoding_target_context); }
        else if (source == VSA      && target == UTF8      ) { string = recode_VSA_UTF8(context, string, recoding_target_context); }
        else if (source == UTF8     && target == VSA       ) { string = recode_UTF8_VSA(context, string, recoding_target_context); }
        else if (source == VSAU     && target == UTF8      ) { string = recode_VSAU_UTF8(context, string, recoding_target_context); }
        else if (source == UTF8     && target == VSAU      ) { string = recode_UTF8_VSAU(context, string, recoding_target_context); }
        else if (source == UTF8     && target == FILENAME  ) { string = recode_UTF8_FILENAME(context, string, recoding_target_context); }
        else if (source == UTF8     && target == SHA1      ) { string = recode_UTF8_SHA1(context, string, recoding_target_context); }
        else if (source == UTF8     && target == JAVA      ) { string = recode_UTF8_JAVA(context, string, recoding_target_context); }
        else if (source == UTF8     && target == JAVASCRIPT) { string = recode_UTF8_JAVASCRIPT(context, string, recoding_target_context); }
        else if (source == UTF8     && target == JSSINGLE  ) { string = recode_UTF8_JSSINGLE(context, string, recoding_target_context); }
        else if (source == UTF8     && target == JSDOUBLE  ) { string = recode_UTF8_JSDOUBLE(context, string, recoding_target_context); }
        else if (source == UTF8     && target == JAVAID    ) { string = recode_UTF8_JAVAID(context, string, recoding_target_context); }
        else if (source == LCU      && target == JAVAID    ) { string = recode_LCU_JAVAID(context, string, recoding_target_context); }
        else if (source == LC       && target == JAVAID    ) { string = recode_LC_JAVAID(context, string, recoding_target_context); }
        else if (source == MC       && target == JAVAID    ) { string = recode_MC_JAVAID(context, string, recoding_target_context); }
        else if (source == UTF8     && target == JAVASQLID ) { string = recode_UTF8_JAVASQLID(context, string, recoding_target_context); }
        else if (source == UTF8     && target == CSV       ) { string = recode_UTF8_CSV(context, string, recoding_target_context); }
        else if (source == UTF8     && target == QUOTEDD   ) { string = recode_UTF8_QUOTEDD(context, string, recoding_target_context); }
        else if (source == UTF8     && target == QUOTEDS   ) { string = recode_UTF8_QUOTEDS(context, string, recoding_target_context); }
        else if (source == UTF8     && target == XML       ) { string = recode_UTF8_XML(context, string, recoding_target_context); }
        else if (source == UTF8     && target == XMLATT    ) { string = recode_UTF8_XMLATT(context, string, recoding_target_context); }
        else if (source == UTF8     && target == SQL       ) { string = recode_UTF8_SQL(context, string, recoding_target_context); }
        else if (source == UCU      && target == SQLID     ) { string = recode_UCU_SQLID(context, string, recoding_target_context); }
        else if (source == XMLITEXT && target == UTF8      ) { string = recode_XMLITEXT_UTF8(context, string, recoding_target_context); }
        else if (source == XML      && target == UTF8      ) { string = recode_XML_UTF8(context, string, recoding_target_context); }
        else if (source == MC       && target == LCU       ) { string = recode_MC_LCU(context, string, recoding_target_context); }
        else if (source == MC       && target == MCB       ) { string = recode_MC_MCB(context, string, recoding_target_context); }
        else if (source == MC       && target == LC        ) { string = recode_MC_LC(context, string, recoding_target_context); }
        else if (source == MC       && target == UCU       ) { string = recode_MC_UCU(context, string, recoding_target_context); }
        else if (source == MC       && target == UC        ) { string = recode_MC_UC(context, string, recoding_target_context); }
        else if (source == MC       && target == STUC      ) { string = recode_MC_STUC(context, string, recoding_target_context); }
        else if (source == MC       && target == CB        ) { string = recode_MC_CB(context, string, recoding_target_context); }
        else if (source == LCU      && target == MC        ) { string = recode_LCU_MC(context, string, recoding_target_context); }
        else if (source == MCB      && target == MC        ) { string = recode_MCB_MC(context, string, recoding_target_context); }
        else if (source == LC       && target == UC        ) { string = recode_LC_UC(context, string, recoding_target_context); }
        else if (source == LCU      && target == LCD       ) { string = recode_LCU_LCD(context, string, recoding_target_context); }
        else if (source == LCD      && target == LCU       ) { string = recode_LCD_LCU(context, string, recoding_target_context); }
        else if (source == JAVADOC  && target == DOCBOOK   ) { string = recode_JAVADOC_DOCBOOK(context, string, recoding_target_context); }
        else if (source == DOCBOOK  && target == JAVADOC   ) { string = recode_DOCBOOK_JAVADOC(context, string, recoding_target_context); }
        else if (source == DOCBOOK  && target == HTML      ) { string = recode_DOCBOOK_HTML(context, string, recoding_target_context, getOption(context, 0, (LinkRecoder) null, options), getOption(context, 1, (Integer) 3, options)); }
        else if (source == DOCBOOK  && target == HTMLPRE   ) { string = recode_DOCBOOK_HTMLPRE(context, string, recoding_target_context); }
        else if (source == DOCBOOK  && target == JAVA      ) { string = recode_DOCBOOK_JAVA(context, string, recoding_target_context); }
        else if (source == XML      && target == JAVAPROP  ) { string = recode_XML_JAVAPROP(context, string, recoding_target_context); }
        else if (source == DOCBOOK  && target == WIKI      ) { string = recode_DOCBOOK_WIKI(context, string, recoding_target_context); }
        else if (source == DOCPAGE  && target == HTML      ) { string = recode_DOCPAGE_HTML(context, string, recoding_target_context, getOption(context, 0, (LinkRecoder) null, options), getOption(context, 1, (Map) null, options)); }
        else if (source == UTF8     && target == INDENT    ) { string = recode_UTF8_INDENT(context, string, recoding_target_context, getOption(context, 0, " ", options), getOption(context, 1, (Integer) 0, options)); }
        else if (source == UTF8     && target == ABBREV    ) { string = recode_UTF8_ABBREV(context, string, recoding_target_context, getOption(context, 0, (Integer) 32, options), getOption(context, 1, "...", options)); }
        else if (source == INTEGER  && target == FORMAT    ) { string = recode_INTEGER_FORMAT(context, string, recoding_target_context, getOption(context, 0, "", options)); }
        else if (source == FLOAT    && target == FORMAT    ) { string = recode_FLOAT_FORMAT(context, string, recoding_target_context, getOption(context, 0, "", options)); }
        else if (source == UTF8     && target == FORMAT    ) { string = recode_UTF8_FORMAT(context, string, recoding_target_context, getOption(context, 0, "", options)); }
        else if (source == UTF8     && target == REGEXP    ) { string = recode_UTF8_REGEXP(context, string, recoding_target_context, getOption(context, 0, "", options), getOption(context, 1, "", options)); }
        else if (source == UTF8     && target == FIXED     ) { string = recode_UTF8_FIXED(context, string, recoding_target_context, getOption(context, 0, (Integer) 32, options), getOption(context, 1, " ", options), getOption(context, 2, "L", options)); }
        else if (source == UTF8     && target == REESC     ) { string = recode_UTF8_REESC(context, string, recoding_target_context); }
        else if (source == UTF8     && target == JSON      ) { string = recode_UTF8_JSON(context, string, recoding_target_context); }
        else if (source == UTF8     && target == TEX       ) { string = recode_UTF8_TEX(context, string, recoding_target_context); }
        else if (source == BASE64   && target == UTF8      ) { string = recode_BASE64_UTF8(context, string, recoding_target_context); }
        else if (source == DSP      && target == SSP       ) { string = recode_UTF8_REGEXP(context, string, recoding_target_context, "\\.", "/"); }
        else if (source == SSP      && target == DSP       ) { string = recode_UTF8_REGEXP(context, string, recoding_target_context, "/", "."); }

        return recoding_target_context == null ? string : recoding_target_context.processOutput(context, string);
    }

    // new version with StringBuilder, unfinished, to be completed over time
    static public StringBuilder recode(CallContext context, CharSequence string, StringBuilder output, Encoding source, Encoding target, RecodingTargetContext recoding_target_context, Object... options) {
        initialise(context);
        
        if (string == null) {
            // do nothing
        } else if (source == target) {
            output = prepareOutput(context, output, string.length());
            output.append(string);
        }
        else if (source == ID       && target == TPLPH     ) { output = recode_ID_TPLPH(context, string, output, recoding_target_context); }
        else if (source == URI      && target == UTF8      ) { output = recode_URI_UTF8(context, string, output, recoding_target_context); }
        else if (source == UTF8     && target == URI       ) { output = recode_UTF8_URI(context, string, output, recoding_target_context); }
//        else if (source == URIFORM  && target == UTF8      ) { output = recode_URIFORM_UTF8(context, string, output, recoding_target_context); }
//        else if (source == UTF8     && target == URIFORM   ) { output = recode_UTF8_URIFORM(context, string, output, recoding_target_context); }
//        else if (source == VSA      && target == UTF8      ) { output = recode_VSA_UTF8(context, string, output, recoding_target_context); }
//        else if (source == UTF8     && target == VSA       ) { output = recode_UTF8_VSA(context, string, output, recoding_target_context); }
//        else if (source == VSAU     && target == UTF8      ) { output = recode_VSAU_UTF8(context, string, output, recoding_target_context); }
//        else if (source == UTF8     && target == VSAU      ) { output = recode_UTF8_VSAU(context, string, output, recoding_target_context); }
//        else if (source == UTF8     && target == FILENAME  ) { output = recode_UTF8_FILENAME(context, string, output, recoding_target_context); }
//        else if (source == UTF8     && target == SHA1      ) { output = recode_UTF8_SHA1(context, string, output, recoding_target_context); }
        else if (source == UTF8     && target == JAVA      ) { output = recode_UTF8_JAVA(context, string, output, recoding_target_context); }
//        else if (source == UTF8     && target == JAVASCRIPT) { output = recode_UTF8_JAVASCRIPT(context, string, output, recoding_target_context); }
//        else if (source == UTF8     && target == JSSINGLE  ) { output = recode_UTF8_JSSINGLE(context, string, output, recoding_target_context); }
//        else if (source == UTF8     && target == JSDOUBLE  ) { output = recode_UTF8_JSDOUBLE(context, string, output, recoding_target_context); }
//        else if (source == UTF8     && target == JAVAID    ) { output = recode_UTF8_JAVAID(context, string, output, recoding_target_context); }
//        else if (source == LCU      && target == JAVAID    ) { output = recode_LCU_JAVAID(context, string, output, recoding_target_context); }
//        else if (source == LC       && target == JAVAID    ) { output = recode_LC_JAVAID(context, string, output, recoding_target_context); }
//        else if (source == MC       && target == JAVAID    ) { output = recode_MC_JAVAID(context, string, output, recoding_target_context); }
//        else if (source == UTF8     && target == JAVASQLID ) { output = recode_UTF8_JAVASQLID(context, string, output, recoding_target_context); }
//        else if (source == UTF8     && target == CSV       ) { output = recode_UTF8_CSV(context, string, output, recoding_target_context); }
//        else if (source == UTF8     && target == QUOTEDD   ) { output = recode_UTF8_QUOTEDD(context, string, output, recoding_target_context); }
//        else if (source == UTF8     && target == QUOTEDS   ) { output = recode_UTF8_QUOTEDS(context, string, output, recoding_target_context); }
        else if (source == UTF8     && target == XML       ) { output = recode_UTF8_XML(context, string, output, recoding_target_context); }
        else if (source == UTF8     && target == XMLATT    ) { output = recode_UTF8_XMLATT(context, string, output, recoding_target_context); }
//        else if (source == UTF8     && target == SQL       ) { output = recode_UTF8_SQL(context, string, output, recoding_target_context); }
//        else if (source == UCU      && target == SQLID     ) { output = recode_UCU_SQLID(context, string, output, recoding_target_context); }
//        else if (source == XMLITEXT && target == UTF8      ) { output = recode_XMLITEXT_UTF8(context, string, output, recoding_target_context); }
//        else if (source == XML      && target == UTF8      ) { output = recode_XML_UTF8(context, string, output, recoding_target_context); }
//        else if (source == MC       && target == LCU       ) { output = recode_MC_LCU(context, string, output, recoding_target_context); }
//        else if (source == MC       && target == MCB       ) { output = recode_MC_MCB(context, string, output, recoding_target_context); }
//        else if (source == MC       && target == LC        ) { output = recode_MC_LC(context, string, output, recoding_target_context); }
//        else if (source == MC       && target == UCU       ) { output = recode_MC_UCU(context, string, output, recoding_target_context); }
//        else if (source == MC       && target == UC        ) { output = recode_MC_UC(context, string, output, recoding_target_context); }
//        else if (source == MC       && target == CB        ) { output = recode_MC_CB(context, string, output, recoding_target_context); }
//        else if (source == MC       && target == STUC      ) { output = recode_MC_STUC(context, string, output, recoding_target_context); }
//        else if (source == LCU      && target == MC        ) { output = recode_LCU_MC(context, string, output, recoding_target_context); }
//        else if (source == MCB      && target == MC        ) { output = recode_MCB_MC(context, string, output, recoding_target_context); }
//        else if (source == LC       && target == UC        ) { output = recode_LC_UC(context, string, output, recoding_target_context); }
//        else if (source == LCU      && target == LCD       ) { output = recode_LCU_LCD(context, string, output, recoding_target_context); }
//        else if (source == LCD      && target == LCU       ) { output = recode_LCD_LCU(context, string, output, recoding_target_context); }
//        else if (source == JAVADOC  && target == DOCBOOK   ) { output = recode_JAVADOC_DOCBOOK(context, string, output, recoding_target_context); }
//        else if (source == DOCBOOK  && target == JAVADOC   ) { output = recode_DOCBOOK_JAVADOC(context, string, output, recoding_target_context); }
//        else if (source == DOCBOOK  && target == HTML      ) { output = recode_DOCBOOK_HTML(context, string, output, recoding_target_context, getOption(context, 0, (LinkRecoder) null, options), getOption(context, 1, (Integer) 3, options)); }
//        else if (source == DOCBOOK  && target == HTMLPRE   ) { output = recode_DOCBOOK_HTMLPRE(context, string, output, recoding_target_context); }
//        else if (source == DOCBOOK  && target == JAVA      ) { output = recode_DOCBOOK_JAVA(context, string, output, recoding_target_context); }
//        else if (source == XML      && target == JAVAPROP  ) { output = recode_XML_JAVAPROP(context, string, output, recoding_target_context); }
//        else if (source == DOCBOOK  && target == WIKI      ) { output = recode_DOCBOOK_WIKI(context, string, output, recoding_target_context); }
        else if (source == DOCPAGE  && target == HTML      ) { output = recode_DOCPAGE_HTML(context, string, output, recoding_target_context, getOption(context, 0, (LinkRecoder) null, options), getOption(context, 1, (Map) null, options)); }
//        else if (source == UTF8     && target == INDENT    ) { output = recode_UTF8_INDENT(context, string, output, recoding_target_context, getOption(context, 0, " ", options), getOption(context, 1, (Integer) 0, options)); }
//        else if (source == UTF8     && target == ABBREV    ) { output = recode_UTF8_ABBREV(context, string, output, recoding_target_context, getOption(context, 0, (Integer) 32, options), getOption(context, 1, "...", options)); }
//        else if (source == INTEGER  && target == FORMAT    ) { output = recode_INTEGER_FORMAT(context, string, output, recoding_target_context, getOption(context, 0, "", options)); }
//        else if (source == FLOAT    && target == FORMAT    ) { output = recode_FLOAT_FORMAT(context, string, output, recoding_target_context, getOption(context, 0, "", options)); }
//        else if (source == UTF8     && target == FORMAT    ) { output = recode_UTF8_FORMAT(context, string, output, recoding_target_context, getOption(context, 0, "", options)); }
//        else if (source == UTF8     && target == REGEXP    ) { output = recode_UTF8_REGEXP(context, string, output, recoding_target_context, getOption(context, 0, "", options), getOption(context, 1, "", options)); }
//        else if (source == UTF8     && target == FIXED     ) { output = recode_UTF8_FIXED(context, string, output, recoding_target_context, getOption(context, 0, (Integer) 32, options), getOption(context, 1, " ", options), getOption(context, 2, "L", options)); }
        else if (source == UTF8     && target == JSON      ) { output = recode_UTF8_JSON(context, string, output, recoding_target_context); }
        else if (source == UTF8     && target == TEX       ) { output = recode_UTF8_TEX(context, string, output, recoding_target_context); }
        else if (source == BASE64   && target == UTF8      ) { output = recode_BASE64_UTF8(context, string, output, recoding_target_context); }
//        else if (source == DSP      && target == SSP       ) { string = recode_UTF8_REGEXP(context, string, recoding_target_context, "\\.", "/"); }
//        else if (source == SSP      && target == DSP       ) { output = recode_UTF8_REGEXP(context, string, output, recoding_target_context, "/", "."); }
        else {
            CustomaryContext.create((Context)context).throwLimitation(context, "Recoding CharSequence '%(string)' into StringBuilder '%(output)' from '%(source)' to '%(target)' is not implemented yet", "string", string.getClass().getName(), "output", output.getClass().getName(), "source", source, "target", target);
            throw (ExceptionLimitation) null; // compilernsists
        }

        // [Issue:Performance(1) - see above]
        // THIS WILL WORK BUT UTTERLY NON-PERFORMANT, SINCE NOT ONLY NEW OUTPUT
        // IS PROCESSED BUT WHOLE SEQUENCE INSTEAD
        // BETTER: OVERRIDE Appendable and place postprocessing stuff inside there
        // requires refactoring
        return recoding_target_context == null ? output : recoding_target_context.processOutput(context, output);
    }

    // ----------

    // even newer version with Appendable, even more unfinished, even more to be completed over time
    // (returns StringBuilder only of one was created newly)
    static public StringBuilder recode(CallContext context, CharSource string, Appendable appendable, Encoding source, Encoding target, RecodingTargetContext recoding_target_context, Object... options) {
        initialise(context);

        if (string == null) {
            return null;
        } else if (source == target) {
            Output output = prepareOutput(context, appendable, string, recoding_target_context);
            output.append(context, string);
            return output.to_return;
        }
        // else if (source == ID       && target == TPLPH     ) { return recode_ID_TPLPH(context, string, appendable, recoding_target_context); }
        // else if (source == URI      && target == UTF8      ) { return recode_URI_UTF8(context, string, appendable, recoding_target_context); }
        // else if (source == UTF8     && target == URI       ) { return recode_UTF8_URI(context, string, appendable, recoding_target_context); }
        // else if (source == URIFORM  && target == UTF8      ) { return recode_URIFORM_UTF8(context, string, appendable, recoding_target_context); }
        // else if (source == UTF8     && target == URIFORM   ) { return recode_UTF8_URIFORM(context, string, appendable, recoding_target_context); }
        // else if (source == VSA      && target == UTF8      ) { return recode_VSA_UTF8(context, string, appendable, recoding_target_context); }
        // else if (source == UTF8     && target == VSA       ) { return recode_UTF8_VSA(context, string, appendable, recoding_target_context); }
        // else if (source == VSAU     && target == UTF8      ) { return recode_VSAU_UTF8(context, string, appendable, recoding_target_context); }
        // else if (source == UTF8     && target == VSAU      ) { return recode_UTF8_VSAU(context, string, appendable, recoding_target_context); }
        // else if (source == UTF8     && target == FILENAME  ) { return recode_UTF8_FILENAME(context, string, appendable, recoding_target_context); }
        // else if (source == UTF8     && target == SHA1      ) { return recode_UTF8_SHA1(context, string, appendable, recoding_target_context); }
        else if (source == UTF8     && target == JAVA      ) { return recode_UTF8_JAVA(context, string, appendable, recoding_target_context); }
        // else if (source == UTF8     && target == JAVASCRIPT) { return recode_UTF8_JAVASCRIPT(context, string, appendable, recoding_target_context); }
        // else if (source == UTF8     && target == JSSINGLE  ) { return recode_UTF8_JSSINGLE(context, string, appendable, recoding_target_context); }
        // else if (source == UTF8     && target == JSDOUBLE  ) { return recode_UTF8_JSDOUBLE(context, string, appendable, recoding_target_context); }
        // else if (source == UTF8     && target == JAVAID    ) { return recode_UTF8_JAVAID(context, string, appendable, recoding_target_context); }
        // else if (source == LCU      && target == JAVAID    ) { return recode_LCU_JAVAID(context, string, appendable, recoding_target_context); }
        // else if (source == LC       && target == JAVAID    ) { return recode_LC_JAVAID(context, string, appendable, recoding_target_context); }
        // else if (source == MC       && target == JAVAID    ) { return recode_MC_JAVAID(context, string, appendable, recoding_target_context); }
        // else if (source == UTF8     && target == JAVASQLID ) { return recode_UTF8_JAVASQLID(context, string, appendable, recoding_target_context); }
        // else if (source == UTF8     && target == CSV       ) { return recode_UTF8_CSV(context, string, appendable, recoding_target_context); }
        // else if (source == UTF8     && target == QUOTEDD   ) { return recode_UTF8_QUOTEDD(context, string, appendable, recoding_target_context); }
        // else if (source == UTF8     && target == QUOTEDS   ) { return recode_UTF8_QUOTEDS(context, string, appendable, recoding_target_context); }
        // else if (source == UTF8     && target == XML       ) { return recode_UTF8_XML(context, string, appendable, recoding_target_context); }
        else if (source == UTF8     && target == XMLATT    ) { return recode_UTF8_XMLATT(context, string, appendable, recoding_target_context); }
        // else if (source == UTF8     && target == SQL       ) { return recode_UTF8_SQL(context, string, appendable, recoding_target_context); }
        // else if (source == UCU      && target == SQLID     ) { return recode_UCU_SQLID(context, string, appendable, recoding_target_context); }
        // else if (source == XMLITEXT && target == UTF8      ) { return recode_XMLITEXT_UTF8(context, string, appendable, recoding_target_context); }
        // else if (source == XML      && target == UTF8      ) { return recode_XML_UTF8(context, string, appendable, recoding_target_context); }
        // else if (source == MC       && target == LCU       ) { return recode_MC_LCU(context, string, appendable, recoding_target_context); }
        // else if (source == MC       && target == MCB       ) { return recode_MC_MCB(context, string, appendable, recoding_target_context); }
        // else if (source == MC       && target == LC        ) { return recode_MC_LC(context, string, appendable, recoding_target_context); }
        // else if (source == MC       && target == UCU       ) { return recode_MC_UCU(context, string, appendable, recoding_target_context); }
        // else if (source == MC       && target == UC        ) { return recode_MC_UC(context, string, appendable, recoding_target_context); }
        // else if (source == MC       && target == CB        ) { return recode_MC_CB(context, string, appendable, recoding_target_context); }
        // else if (source == MC       && target == STUC      ) { return recode_MC_STUC(context, string, appendable, recoding_target_context); }
        // else if (source == LCU      && target == MC        ) { return recode_LCU_MC(context, string, appendable, recoding_target_context); }
        // else if (source == MCB      && target == MC        ) { return recode_MCB_MC(context, string, appendable, recoding_target_context); }
        // else if (source == LC       && target == UC        ) { return recode_LC_UC(context, string, appendable, recoding_target_context); }
        // else if (source == LCU      && target == LCD       ) { return recode_LCU_LCD(context, string, appendable, recoding_target_context); }
        // else if (source == LCD      && target == LCU       ) { return recode_LCD_LCU(context, string, appendable, recoding_target_context); }
        // else if (source == JAVADOC  && target == DOCBOOK   ) { return recode_JAVADOC_DOCBOOK(context, string, appendable, recoding_target_context); }
        // else if (source == DOCBOOK  && target == JAVADOC   ) { return recode_DOCBOOK_JAVADOC(context, string, appendable, recoding_target_context); }
        // else if (source == DOCBOOK  && target == HTML      ) { return recode_DOCBOOK_HTML(context, string, appendable, recoding_target_context, getOption(context, 0, (LinkRecoder) null, options), getOption(context, 1, (Integer) 3, options)); }
        // else if (source == DOCBOOK  && target == HTMLPRE   ) { return recode_DOCBOOK_HTMLPRE(context, string, appendable, recoding_target_context); }
        // else if (source == DOCBOOK  && target == JAVA      ) { return recode_DOCBOOK_JAVA(context, string, appendable, recoding_target_context); }
        // else if (source == XML      && target == JAVAPROP  ) { return recode_XML_JAVAPROP(context, string, appendable, recoding_target_context); }
        // else if (source == DOCBOOK  && target == WIKI      ) { return recode_DOCBOOK_WIKI(context, string, appendable, recoding_target_context); }
        // else if (source == DOCPAGE  && target == HTML      ) { return recode_DOCPAGE_HTML(context, string, appendable, recoding_target_context, getOption(context, 0, (LinkRecoder) null, options), getOption(context, 1, (Map) null, options)); }
        // else if (source == UTF8     && target == INDENT    ) { return recode_UTF8_INDENT(context, string, appendable, recoding_target_context, getOption(context, 0, " ", options), getOption(context, 1, (Integer) 0, options)); }
        // else if (source == UTF8     && target == ABBREV    ) { return recode_UTF8_ABBREV(context, string, appendable, recoding_target_context, getOption(context, 0, (Integer) 32, options), getOption(context, 1, "...", options)); }
        // else if (source == INTEGER  && target == FORMAT    ) { return recode_INTEGER_FORMAT(context, string, appendable, recoding_target_context, getOption(context, 0, "", options)); }
        // else if (source == FLOAT    && target == FORMAT    ) { return recode_FLOAT_FORMAT(context, string, appendable, recoding_target_context, getOption(context, 0, "", options)); }
        // else if (source == UTF8     && target == FORMAT    ) { return recode_UTF8_FORMAT(context, string, appendable, recoding_target_context, getOption(context, 0, "", options)); }
        // else if (source == UTF8     && target == REGEXP    ) { return recode_UTF8_REGEXP(context, string, appendable, recoding_target_context, getOption(context, 0, "", options), getOption(context, 1, "", options)); }
        // else if (source == UTF8     && target == FIXED     ) { return recode_UTF8_FIXED(context, string, appendable, recoding_target_context, getOption(context, 0, (Integer) 32, options), getOption(context, 1, " ", options), getOption(context, 2, "L", options)); }
        else if (source == UTF8     && target == JSON      ) { return recode_UTF8_JSON(context, string, appendable, recoding_target_context); }
        else if (source == UTF8     && target == TEX       ) { return recode_UTF8_TEX(context, string, appendable, recoding_target_context); }
        // else if (source == BASE64   && target == UTF8      ) { return recode_BASE64_UTF8(context, string, appendable, recoding_target_context); }
        // else if (source == DSP      && target == SSP       ) { string = recode_UTF8_REGEXP(context, string, recoding_target_context, "\\.", "/"); }
        // else if (source == SSP      && target == DSP       ) { return recode_UTF8_REGEXP(context, string, appendable, recoding_target_context, "/", "."); }
        else {
            if (string instanceof CharSourceCharSequence && appendable instanceof StringBuilder) {
                return recode(context, ((CharSourceCharSequence) string).getCharSequence(context), (StringBuilder) appendable, source, target, recoding_target_context, options);
            }
            CustomaryContext.create((Context)context).throwLimitation(context, "Recoding CharSource '%(string)' into Appendable '%(appendable)' from '%(source)' to '%(target)' is not implemented yet", "string", string.getClass().getName(), "appendable", appendable.getClass().getName(), "source", source, "target", target);
            throw (ExceptionLimitation) null; // compilernsists
        }
    }

    static public StringBuilder recode(CallContext context, CharSequence sequence, Appendable appendable, Encoding source, Encoding target, RecodingTargetContext recoding_target_context, Object... options) {
        return recode(context, sequence == null ? null : new CharSourceCharSequence(context, sequence), appendable, source, target, recoding_target_context, options);
    }

    static public StringBuilder recode(CallContext context, Reader reader, Appendable appendable, Encoding source, Encoding target, RecodingTargetContext recoding_target_context, Object... options) {
        return recode(context, reader == null ? null : new CharSourceReader(context, reader), appendable, source, target, recoding_target_context, options);
    }

    // ----------

    static public String recode(CallContext context, String string, String source, String target, RecodingTargetContext recoding_target_context, Object... options) {
        if (options == null || options.length == 0) {
            options = getOptions(context, target);
        }
        return recode(context, string, getEncoding(context, source), getEncoding(context, target), recoding_target_context, options);
    }

    static public StringBuilder recode(CallContext context, CharSource string, StringBuilder output, String source, String target, RecodingTargetContext recoding_target_context, Object... options) {
        if (options == null || options.length == 0) {
            options = getOptions(context, target);
        }
        return recode(context, string, output, getEncoding(context, source), getEncoding(context, target), recoding_target_context, options);
    }

    static public StringBuilder recode(CallContext context, CharSequence sequence, StringBuilder output, String source, String target, RecodingTargetContext recoding_target_context, Object... options) {
        return recode(context, sequence == null ? null : new CharSourceCharSequence(context, sequence), output, source, target, recoding_target_context, options);
    }

    static public StringBuilder recode(CallContext context, Reader reader, StringBuilder output, String source, String target, RecodingTargetContext recoding_target_context, Object... options) {
        return recode(context, reader == null ? null : new CharSourceReader(context, reader), output, source, target, recoding_target_context, options);
    }

    // ----------

    static public String recode(CallContext context, String string, String source, String target, Object... options) {
        return recode(context, string, source, target, (RecodingTargetContext) null, options);
    }

    static public StringBuilder recode(CallContext context, CharSource string, StringBuilder output, String source, String target, Object... options) {
        return recode(context, string, output, source, target, (RecodingTargetContext) null, options);
    }

    static public StringBuilder recode(CallContext context, CharSequence sequence, StringBuilder output, String source, String target, Object... options) {
        return recode(context, sequence == null ? null : new CharSourceCharSequence(context, sequence), output, source, target, (RecodingTargetContext) null, options);
    }

    static public StringBuilder recode(CallContext context, Reader reader, StringBuilder output, String source, String target, Object... options) {
        return recode(context, reader == null ? null : new CharSourceReader(context, reader), output, source, target, options);
    }

    // ----------

    static public StringBuilder prepareOutput(CallContext context, StringBuilder output, int len) {
        if (output == null) {
            output = new StringBuilder(len);
        } else {
            output.ensureCapacity(output.length() + len);
        }
        return output;
    }

    static public class Output {
        public StringBuilder to_return;
        public Appendable appendable;
        public int length;
        public void append(CallContext context, char c) {
            try {
                this.appendable.append(c);
            } catch (IOException ioe) {
                CustomaryContext.create((Context)context).throwEnvironmentFailure(context, ioe, "Could not recode char sequence, writing to appendable failed");
            }
        }            
        public void append(CallContext context, CharSequence cs) {
            try {
                this.appendable.append(cs);
            } catch (IOException ioe) {
                CustomaryContext.create((Context)context).throwEnvironmentFailure(context, ioe, "Could not recode char sequence, writing to appendable failed");
            }
        }            
        public void append(CallContext context, CharSource cs) {
            try {
                cs.appendTo(context, this.appendable);
            } catch (IOException ioe) {
                CustomaryContext.create((Context)context).throwEnvironmentFailure(context, ioe, "Could not recode char sequence, writing to appendable failed");
            }
        }            
    }

    static public Output prepareOutput(CallContext context, Appendable appendable, CharSource string, RecodingTargetContext recoding_target_context) {
        Output output = new Output();
        output.length = string == null ? 0 : string.length(context);
        if (appendable == null) {
            if (output.length >= 0) {
                output.to_return = new StringBuilder(output.length);
            } else {
                output.to_return = new StringBuilder();
            }
            appendable = output.to_return;
        } else if (appendable instanceof StringBuilder && output.length > 0) {
            ((StringBuilder) appendable).ensureCapacity(((StringBuilder) appendable).length() + output.length);
        }
        output.appendable = recoding_target_context == null ? appendable : recoding_target_context.getOutputProcessor(context, appendable);
        return output;
    }

    // ----------

    static public EncodingService getEncodingService(CallContext context, Encoding source, Encoding target) {
        Vector<EncodingService> services = ServiceRegistry.getServices(context, EncodingService.class);
        for (EncodingService service : services) {
            if (service.canRecode(context, source, target)) {
                return service;
            }
        }
        return null;
    }

    // ---------------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------------

    static public String recode_ID_TPLPH(CallContext context, String string) {
        return recode_ID_TPLPH(context, string, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_ID_TPLPH(CallContext context, CharSequence string, StringBuilder output) {
        return recode_ID_TPLPH(context, string, output, (RecodingTargetContext) null);
    }

    static public String recode_ID_TPLPH(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return recode_ID_TPLPH(context, string, null, recoding_target_context).toString();
    }

    static public StringBuilder recode_ID_TPLPH(CallContext context, CharSequence string, StringBuilder output, RecodingTargetContext recoding_target_context) {
        int len = string.length();
        output = prepareOutput(context, output, len + 3);

        StringBuffer sb = new StringBuffer();

        sb.append("${");
        sb.append(string);
        sb.append('}');

        output.append(sb);

        return output;
    }

    // ---------------------------------------------------------------------------------------------------

    // not really for UTF8 (only ASCII), but should
    static public String recode_URI_UTF8(CallContext context, String string) {
        return recode_URI_UTF8(context, string, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_URI_UTF8(CallContext context, CharSequence string, StringBuilder output) {
        return recode_URI_UTF8(context, string, output, (RecodingTargetContext) null);
    }

    static public String recode_URI_UTF8(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return recode_URI_UTF8(context, string, null, recoding_target_context).toString();
    }

    static public StringBuilder recode_URI_UTF8(CallContext context, CharSequence string, StringBuilder output, RecodingTargetContext recoding_target_context) {
        int len = string.length();
        output = prepareOutput(context, output, len);

        Matcher m = uri_escape.matcher(string);
        
        // SUPPORT here %{hex} 
        
        // THIS IS *** ANNOYING ***
        // it is because appendReplacement/appendTail
        // do only support StringBuffer, but neither
        // Appendable nor StringBuilder
        // there are bug requests filed and possible
        // it will be solved in future java releases

        StringBuffer sb = new StringBuffer();

        while (m.find()) {
            char c1 = m.group(1).toUpperCase().charAt(0);
            char c2 = m.group(1).toUpperCase().charAt(1);
            char c = (char) ((c1 - (c1 > 64 ? 55 : 48)) * 16 + (c2 - (c2 > 64 ? 55 : 48)));
            m.appendReplacement(sb, "");
            sb.append(c);
        }
        m.appendTail(sb);

        output.append(sb);

        return output;
    }

    // ---------------------------------------------------------------------------------------------------

    // not really for UTF8 (only ASCII), but should
    static public String recode_UTF8_URI(CallContext context, String string) {
        return recode_UTF8_URI(context, string, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_UTF8_URI(CallContext context, CharSequence string, StringBuilder output) {
        return recode_UTF8_URI(context, string, output, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_URI(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return recode_UTF8_URI(context, string, null, recoding_target_context).toString();
    }

    static public StringBuilder recode_UTF8_URI(CallContext context, CharSequence string, StringBuilder output, RecodingTargetContext recoding_target_context) {
        int len = string.length();
        output = prepareOutput(context, output, len+8);

        for (int i=0; i<len; i++) {
            char c = string.charAt(i);
            if (c < 0) {
                CustomaryContext.create((Context)context).throwAssertionProvedFalse(context, "Expected only positive characters");
                throw (ExceptionAssertionProvedFalse) null; // compiler insists
            }
            if (c > 16777215) {
                output.append("%{" + hex[(c & 0xFF000000) >> 24] + hex[(c & 0xFF0000) >> 16] + hex[(c & 0xFF00) >> 8] + hex[c & 0xFF] + "}");
            } else if (c > 65535) {
                output.append("%{" + hex[(c & 0xFF0000) >> 16] + hex[(c & 0xFF00) >> 8] + hex[c & 0xFF] + "}");
            } else if (c > 255) {
                output.append("%{" + hex[(c & 0xFF00) >> 8] + hex[c & 0xFF] + "}");
            } else {
                int code = (c > 0x007f ? 5 : URICharCode[c]);
                output.append(code >= 2 ? ("%" + hex[c]) : c);
            }
        }

        return output;
    }

    final static public String[] hex =
    {
        "00", "01", "02", "03", "04", "05", "06", "07",
        "08", "09", "0A", "0B", "0C", "0D", "0E", "0F",
        "10", "11", "12", "13", "14", "15", "16", "17",
        "18", "19", "1A", "1B", "1C", "1D", "1E", "1F",
        "20", "21", "22", "23", "24", "25", "26", "27",
        "28", "29", "2A", "2B", "2C", "2D", "2E", "2F",
        "30", "31", "32", "33", "34", "35", "36", "37",
        "38", "39", "3A", "3B", "3C", "3D", "3E", "3F",
        "40", "41", "42", "43", "44", "45", "46", "47",
        "48", "49", "4A", "4B", "4C", "4D", "4E", "4F",
        "50", "51", "52", "53", "54", "55", "56", "57",
        "58", "59", "5A", "5B", "5C", "5D", "5E", "5F",
        "60", "61", "62", "63", "64", "65", "66", "67",
        "68", "69", "6A", "6B", "6C", "6D", "6E", "6F",
        "70", "71", "72", "73", "74", "75", "76", "77",
        "78", "79", "7A", "7B", "7C", "7D", "7E", "7F",
        "80", "81", "82", "83", "84", "85", "86", "87",
        "88", "89", "8A", "8B", "8C", "8D", "8E", "8F",
        "90", "91", "92", "93", "94", "95", "96", "97",
        "98", "99", "9A", "9B", "9C", "9D", "9E", "9F",
        "A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7",
        "A8", "A9", "AA", "AB", "AC", "AD", "AE", "AF",
        "B0", "B1", "B2", "B3", "B4", "B5", "B6", "B7",
        "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF",
        "C0", "C1", "C2", "C3", "C4", "C5", "C6", "C7",
        "C8", "C9", "CA", "CB", "CC", "CD", "CE", "CF",
        "D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7",
        "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF",
        "E0", "E1", "E2", "E3", "E4", "E5", "E6", "E7",
        "E8", "E9", "EA", "EB", "EC", "ED", "EE", "EF",
        "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7",
        "F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF"
    };

    final static public int [] URICharCode = {
        /*
          Character codes according to RFC 2396 - URI Generic Syntax
          see http://www.ics.uci.edu/pub/ietf/uri/rfc2396.txt

          code  charset       escape in URLs

           0     alnum         could
           1     mark          could
           2     reserved      used in URL-part: must
                               as URL seperator: must not
           3     unsafe        should
           4     veryunsafe    must
       */

        4 /* ? 00 */, 4 /* ? 01 */, 4 /* ? 02 */, 4 /* ? 03 */, 4 /* ? 04 */, 4 /* ? 05 */, 4 /* ? 06 */, 4 /* ? 07 */,
        4 /* ? 08 */, 4 /* ? 09 */, 4 /* ? 0A */, 4 /* ? 0B */, 4 /* ? 0C */, 4 /* ? 0D */, 4 /* ? 0E */, 4 /* ? 0F */,
        4 /* ? 10 */, 4 /* ? 11 */, 4 /* ? 12 */, 4 /* ? 13 */, 4 /* ? 14 */, 4 /* ? 15 */, 4 /* ? 16 */, 4 /* ? 17 */,
        4 /* ? 18 */, 4 /* ? 19 */, 4 /* ? 1A */, 4 /* ? 1B */, 4 /* ? 1C */, 4 /* ? 1D */, 4 /* ? 1E */, 4 /* ? 1F */,
        3 /*   20 */, 1 /* ! 21 */, 3 /* " 22 */, 3 /* # 23 */, 2 /* $ 24 */, 3 /* % 25 */, 2 /* & 26 */, 1 /* ' 27 */,
        1 /* ( 28 */, 1 /* ) 29 */, 1 /* * 2A */, 2 /* + 2B */, 2 /* , 2C */, 1 /* - 2D */, 1 /* . 2E */, 2 /* / 2F */,
        0 /* 0 30 */, 0 /* 1 31 */, 0 /* 2 32 */, 0 /* 3 33 */, 0 /* 4 34 */, 0 /* 5 35 */, 0 /* 6 36 */, 0 /* 7 37 */,
        0 /* 8 38 */, 0 /* 9 39 */, 2 /* : 3A */, 2 /* ; 3B */, 3 /* < 3C */, 2 /* = 3D */, 3 /* > 3E */, 2 /* ? 3F */,
        2 /* @ 40 */, 0 /* A 41 */, 0 /* B 42 */, 0 /* C 43 */, 0 /* D 44 */, 0 /* E 45 */, 0 /* F 46 */, 0 /* G 47 */,
        0 /* H 48 */, 0 /* I 49 */, 0 /* J 4A */, 0 /* K 4B */, 0 /* L 4C */, 0 /* M 4D */, 0 /* N 4E */, 0 /* O 4F */,
        0 /* P 50 */, 0 /* Q 51 */, 0 /* R 52 */, 0 /* S 53 */, 0 /* T 54 */, 0 /* U 55 */, 0 /* V 56 */, 0 /* W 57 */,
        0 /* X 58 */, 0 /* Y 59 */, 0 /* Z 5A */, 3 /* [ 5B */, 3 /* \ 5C */, 3 /* ] 5D */, 3 /* ^ 5E */, 1 /* _ 5F */,
        3 /* ` 60 */, 0 /* a 61 */, 0 /* b 62 */, 0 /* c 63 */, 0 /* d 64 */, 0 /* e 65 */, 0 /* f 66 */, 0 /* g 67 */,
        0 /* h 68 */, 0 /* i 69 */, 0 /* j 6A */, 0 /* k 6B */, 0 /* l 6C */, 0 /* m 6D */, 0 /* n 6E */, 0 /* o 6F */,
        0 /* p 70 */, 0 /* q 71 */, 0 /* r 72 */, 0 /* s 73 */, 0 /* t 74 */, 0 /* u 75 */, 0 /* v 76 */, 0 /* w 77 */,
        0 /* x 78 */, 0 /* y 79 */, 0 /* z 7A */, 3 /* { 7B */, 3 /* | 7C */, 3 /* } 7D */, 1 /* ~ 7E */, 4 /*   7F */
    };

    // ---------------------------------------------------------------------------------------------------

    // not really for UTF8 (only ASCII), but should
    static public String recode_BASE64_UTF8(CallContext context, String string) {
        return recode_BASE64_UTF8(context, string, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_BASE64_UTF8(CallContext context, CharSequence string, StringBuilder output) {
        return recode_BASE64_UTF8(context, string, output, (RecodingTargetContext) null);
    }

    static public String recode_BASE64_UTF8(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return recode_BASE64_UTF8(context, string, null, recoding_target_context).toString();
    }

    static public StringBuilder recode_BASE64_UTF8(CallContext context, CharSequence string, StringBuilder output, RecodingTargetContext recoding_target_context) {
        int len = string.length();
        output = prepareOutput(context, output, len);

        // might hopefully be replaced with java.util.Base64 in java 8
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
            output.append(new String(decoder.decodeBuffer(string.toString())));
        } catch (java.io.IOException ioe) {
            System.err.println("error, base64: " + ioe);
            ioe.printStackTrace();
        }

        return output;
    }

    // ---------------------------------------------------------------------------------------------------

    // not really for UTF8 (only ASCII), but should
    static public String recode_VSA_UTF8(CallContext context, String string) {
        return recode_VSA_UTF8(context, string, (RecodingTargetContext) null);
    }

    static public String recode_VSA_UTF8(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        Matcher m = vsa_escape.matcher(string);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            char c1 = m.group(1).toUpperCase().charAt(0);
            char c2 = m.group(1).toUpperCase().charAt(1);
            char c = (char) ((c1 - (c1 > 64 ? 55 : 48)) * 16 + (c2 - (c2 > 64 ? 55 : 48)));
            m.appendReplacement(sb, "");
            sb.append(c);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    // ---------------------------------------------------------------------------------------------------

    // not really for UTF8 (only ASCII), but should
    static public String recode_VSAU_UTF8(CallContext context, String string) {
        return recode_VSAU_UTF8(context, string, (RecodingTargetContext) null);
    }

    static public String recode_VSAU_UTF8(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return recode_VSA_UTF8(context, string, recoding_target_context);
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_JAVASCRIPT(CallContext context, String string) {
        return recode_UTF8_JSDOUBLE(context, string, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_JAVASCRIPT(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return recode_UTF8_JSDOUBLE(context, string, recoding_target_context);
    }

    // ---------------------------------------------------------------------------------------------------

    // JS escapings according to
    // http://ecma-international.org/ecma-262/5.1/#sec-7.8.4

    static public String recode_UTF8_JSSINGLE(CallContext context, String string) {
        return recode_UTF8_JSSINGLE(context, string, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_JSSINGLE(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        if (string == null) { return null; }
        CharacterIterator iter = new StringCharacterIterator(string);
        StringBuffer sb = new StringBuffer();
        for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
            if (c == '\b')              { sb.append("\\b"); continue; } // backspace
            if (c == '\f')              { sb.append("\\f"); continue; } // form feed
            if (c == '\n')              { sb.append("\\n"); continue; } // line feed
            if (c == '\r')              { sb.append("\\r"); continue; } // carriage return
            if (c == '\t')              { sb.append("\\t"); continue; } // horizontal tab
            if (c == '\u000B')          { sb.append("\\v"); continue; } // vertical tab
            if (c == '\'')              { sb.append("\\\'"); continue; }
            if (c == '\\')              { sb.append("\\\\"); continue; }
            if (0x00 <= c && c <= 0x1F) { sb.append("\\u00" + hex[c]); continue; }
            if (0x20 <= c && c <= 0x7F) { sb.append(c); continue; }
            if (0x80 <= c && c <= 0xFF) { sb.append("\\u00" + hex[c]); continue; }
            sb.append("\\u" + hex[(c & 0xFF00) >> 8] + hex[c & 0xFF]);
        }
        return sb.toString();
    }

    // ---------------------------------------------------------------------------------------------------

    // JS escapings according to
    // http://ecma-international.org/ecma-262/5.1/#sec-7.8.4

    static public String recode_UTF8_JSDOUBLE(CallContext context, String string) {
        return recode_UTF8_JSDOUBLE(context, string, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_JSDOUBLE(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        if (string == null) { return null; }
        CharacterIterator iter = new StringCharacterIterator(string);
        StringBuffer sb = new StringBuffer();
        for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
            if (c == '\b')              { sb.append("\\b"); continue; } // backspace
            if (c == '\f')              { sb.append("\\f"); continue; } // form feed
            if (c == '\n')              { sb.append("\\n"); continue; } // line feed
            if (c == '\r')              { sb.append("\\r"); continue; } // carriage return
            if (c == '\t')              { sb.append("\\t"); continue; } // horizontal tab
            if (c == '\u000B')          { sb.append("\\v"); continue; } // vertical tab
            if (c == '"')               { sb.append("\\\""); continue; }
            if (c == '\\')              { sb.append("\\\\"); continue; }
            if (0x00 <= c && c <= 0x1F) { sb.append("\\u00" + hex[c]); continue; }
            if (0x20 <= c && c <= 0x7F) { sb.append(c); continue; }
            if (0x80 <= c && c <= 0xFF) { sb.append("\\u00" + hex[c]); continue; }
            sb.append("\\u" + hex[(c & 0xFF00) >> 8] + hex[c & 0xFF]);
        }
        return sb.toString();
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_JAVA(CallContext context, String string) {
        return recode_UTF8_JAVA(context, string, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_UTF8_JAVA(CallContext context, CharSource string, Appendable output) {
        return recode_UTF8_JAVA(context, string, output, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_UTF8_JAVA(CallContext context, CharSequence sequence, Appendable output) {
        return recode_UTF8_JAVA(context, sequence == null ? null : new CharSourceCharSequence(context, sequence), output, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_UTF8_JAVA(CallContext context, Reader reader, Appendable output) {
        return recode_UTF8_JAVA(context, reader == null ? null : new CharSourceReader(context, reader), output, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_JAVA(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        StringBuilder sb = recode_UTF8_JAVA(context, string, null, recoding_target_context);
        return sb == null ? null : sb.toString();
    }

    static public StringBuilder recode_UTF8_JAVA(CallContext context, CharSequence sequence, Appendable appendable, RecodingTargetContext recoding_target_context) {
        return recode_UTF8_JAVA(context, sequence == null ? null : new CharSourceCharSequence(context, sequence), appendable, recoding_target_context);
    }

    static public StringBuilder recode_UTF8_JAVA(CallContext context, Reader reader, Appendable appendable, RecodingTargetContext recoding_target_context) {
        return recode_UTF8_JAVA(context, reader == null ? null : new CharSourceReader(context, reader), appendable, recoding_target_context);
    }

    static public StringBuilder recode_UTF8_JAVA(CallContext context, CharSource string, Appendable appendable, RecodingTargetContext recoding_target_context) {
        if (string == null) { return null; }

        Output output = prepareOutput(context, appendable, string, recoding_target_context);

        int i;
        while ((i = string.read(context)) != -1) {
            char c = (char) i;
            if (c == '\n')              { output.append(context, "\\n"); continue; }
            if (c == '\r')              { output.append(context, "\\r"); continue; }
            if (c == '\t')              { output.append(context, "\\t"); continue; }
            if (c == '"')               { output.append(context, "\\\""); continue; }
            if (c == '\\')              { output.append(context, "\\\\"); continue; }
            if (0x00 <= c && c <= 0x1F) { output.append(context, "\\u00" + hex[c]); continue; }
            if (0x20 <= c && c <= 0x7F) { output.append(context, c); continue; }
            if (0x80 <= c && c <= 0xFF) { output.append(context, "\\u00" + hex[c]); continue; }
            output.append(context, "\\u" + hex[(c & 0xFF00) >> 8] + hex[c & 0xFF]);
        }
        
        return output.to_return;
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_JAVAID(CallContext context, String string) {
        return recode_UTF8_JAVAID(context, string, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_JAVAID(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        if (string.matches("^(?:(?:interface)|(?:class)|(?:package)|(?:private)|(?:default)|(?:protected)|(?:public)|(?:final)|(?:static)|(?:return)|(?:j_.*))$")) {
            return "j_" + string;
        } else {
            return string;
        }
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_LCU_JAVAID(CallContext context, String string) {
        return recode_LCU_JAVAID(context, string, (RecodingTargetContext) null);
    }

    static public String recode_LCU_JAVAID(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return recode_UTF8_JAVAID(context, string, recoding_target_context);
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_LC_JAVAID(CallContext context, String string) {
        return recode_LC_JAVAID(context, string, (RecodingTargetContext) null);
    }

    static public String recode_LC_JAVAID(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        if (string.matches("^(?:(?:interface)|(?:class)|(?:package)|(?:private)|(?:default)|(?:protected)|(?:public)|(?:j.*))$")) {
            return "j" + string;
        } else {
            return string;
        }
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_MC_JAVAID(CallContext context, String string) {
        return recode_MC_JAVAID(context, string, (RecodingTargetContext) null);
    }

    static public String recode_MC_JAVAID(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        if (string.matches("^(?:(?:Interface)|(?:Class)|(?:Package)|(?:Private)|(?:Default)|(?:Protected)|(?:Public)|(?:J.*))$")) {
            return "J" + string;
        } else {
            return string;
        }
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_JAVASQLID(CallContext context, String string) {
        return recode_UTF8_JAVASQLID(context, string, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_JAVASQLID(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        string = recode_UTF8_JAVAID(context, string, recoding_target_context);
        if (string.matches("^(?:(?i:select)|(?i:update)|(?i:alter)|(?i:procedure)|(?i:class)|(?i:user)|(?i:from)|(?i:s_.*))$")) {
            // well, "class" is not exactly a sql problem, but a java one; if
            // the attribute is called "class" in the db, the corresponding
            // getter will be named "getClass()" by the mapper and this
            // finally conflicts with Object.getClass()
            return "s_" + string;
        } else {
            return string;
        }
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_SQL(CallContext context, String string) {
        return recode_UTF8_SQL(context, string, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_SQL(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return string.replace("'", "''");
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UCU_SQLID(CallContext context, String string) {
        return recode_UCU_SQLID(context, string, (RecodingTargetContext) null);
    }

    static public String recode_UCU_SQLID(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        if (string.matches("^(?:(?:SELECT)|(?:UPDATE)|(?:ALTER)|(?:PROCEDURE)|(?:CLASS)|(?:USER)|(?:FROM)|(?:TO)|(?:CONSTRAINT)|(?:X_.*))$")) {
            return "X_" + string;
        } else {
            return string;
        }
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_CSV(CallContext context, String string) {
        return recode_UTF8_CSV(context, string, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_CSV(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        if (string == null) { return null; }
        CharacterIterator iter = new StringCharacterIterator(string);
        StringBuffer sb = new StringBuffer();
        for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
            if (c == '"')               { sb.append("\"\""); continue; }
            else                        { sb.append(c); continue; }
        }
        return sb.toString();
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_QUOTEDD(CallContext context, String string) {
        return recode_UTF8_QUOTEDD(context, string, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_QUOTEDD(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        if (string == null) { return null; }
        CharacterIterator iter = new StringCharacterIterator(string);
        StringBuffer sb = new StringBuffer();
        for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
            if (c == '"')               { sb.append("\\\""); continue; }
            if (c == '\\')              { sb.append("\\\\"); continue; }
            else                        { sb.append(c); continue; }
        }
        return sb.toString();
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_QUOTEDS(CallContext context, String string) {
        return recode_UTF8_QUOTEDS(context, string, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_QUOTEDS(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        if (string == null) { return null; }
        CharacterIterator iter = new StringCharacterIterator(string);
        StringBuffer sb = new StringBuffer();
        for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
            if (c == '\'')              { sb.append("\\'"); continue; }
            if (c == '\\')              { sb.append("\\\\"); continue; }
            else                        { sb.append(c); continue; }
        }
        return sb.toString();
    }

    // ---------------------------------------------------------------------------------------------------

    // not really for UTF8 (only ASCII), but should
    static public String recode_UTF8_VSA(CallContext context, String string) {
        return recode_UTF8_VSA(context, string, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_VSA(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        byte[] bytes;
        try {
            bytes = string.getBytes("UTF-8");
        } catch (java.io.UnsupportedEncodingException uee) {
            CustomaryContext.create((Context)context).throwLimitation(context, "System (VM) does not support UTF-8 encoding");
            throw (ExceptionLimitation) null;
        }
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (int b : bytes) {
            if (b < 0) { b += 256; }
            int code = (b > 0x007f ? 5 : URICharCode[b]);
            if (first) {
                if (b >= '0' && b <= '9') { code = -1; }
                first = false;
            }
            sb.append(code == 0 ? ((char)b) : ("_" + hex[b]));
        }
        return sb.toString();
    }

    // ---------------------------------------------------------------------------------------------------

    // not really for UTF8 (only ASCII), but should
    static public String recode_UTF8_VSAU(CallContext context, String string) {
        return recode_UTF8_VSAU(context, string, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_VSAU(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        byte[] bytes;
        try {
            bytes = string.getBytes("UTF-8");
        } catch (java.io.UnsupportedEncodingException uee) {
            CustomaryContext.create((Context)context).throwLimitation(context, "System (VM) does not support UTF-8 encoding");
            throw (ExceptionLimitation) null;
        }
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (int b : bytes) {
            if (b < 0) { b += 256; }
            int code = (b > 0x007F ? 5 : b == 0x005F /* _ */ ? 0 : URICharCode[b]);
            if (first) {
                if (b >= '0' && b <= '9') { code = -1; }
                first = false;
            }
            sb.append(code == 0 ? ((char)b) : ("_" + hex[b]));
        }
        return sb.toString();
    }

    // ---------------------------------------------------------------------------------------------------

    final static public int [] FileNameCode = {
        0 /* ? 00 */, 0 /* ? 01 */, 0 /* ? 02 */, 0 /* ? 03 */, 0 /* ? 04 */, 0 /* ? 05 */, 0 /* ? 06 */, 0 /* ? 07 */,
        0 /* ? 08 */, 0 /* ? 09 */, 0 /* ? 0A */, 0 /* ? 0B */, 0 /* ? 0C */, 0 /* ? 0D */, 0 /* ? 0E */, 0 /* ? 0F */,
        0 /* ? 10 */, 0 /* ? 11 */, 0 /* ? 12 */, 0 /* ? 13 */, 0 /* ? 14 */, 0 /* ? 15 */, 0 /* ? 16 */, 0 /* ? 17 */,
        0 /* ? 18 */, 0 /* ? 19 */, 0 /* ? 1A */, 0 /* ? 1B */, 0 /* ? 1C */, 0 /* ? 1D */, 0 /* ? 1E */, 0 /* ? 1F */,
        0 /*   20 */, 0 /* ! 21 */, 0 /* " 22 */, 0 /* # 23 */, 0 /* $ 24 */, 0 /* % 25 */, 0 /* & 26 */, 0 /* ' 27 */,
        0 /* ( 28 */, 0 /* ) 29 */, 0 /* * 2A */, 0 /* + 2B */, 0 /* , 2C */, 1 /* - 2D */, 1 /* . 2E */, 0 /* / 2F */,
        1 /* 0 30 */, 1 /* 1 31 */, 1 /* 2 32 */, 1 /* 3 33 */, 1 /* 4 34 */, 1 /* 5 35 */, 1 /* 6 36 */, 1 /* 7 37 */,
        1 /* 8 38 */, 1 /* 9 39 */, 0 /* : 3A */, 0 /* ; 3B */, 0 /* < 3C */, 0 /* = 3D */, 0 /* > 3E */, 0 /* ? 3F */,
        0 /* @ 40 */, 1 /* A 41 */, 1 /* B 42 */, 1 /* C 43 */, 1 /* D 44 */, 1 /* E 45 */, 1 /* F 46 */, 1 /* G 47 */,
        1 /* H 48 */, 1 /* I 49 */, 1 /* J 4A */, 1 /* K 4B */, 1 /* L 4C */, 1 /* M 4D */, 1 /* N 4E */, 1 /* O 4F */,
        1 /* P 50 */, 1 /* Q 51 */, 1 /* R 52 */, 1 /* S 53 */, 1 /* T 54 */, 1 /* U 55 */, 1 /* V 56 */, 1 /* W 57 */,
        1 /* X 58 */, 1 /* Y 59 */, 1 /* Z 5A */, 0 /* [ 5B */, 0 /* \ 5C */, 0 /* ] 5D */, 0 /* ^ 5E */, 1 /* _ 5F */,
        0 /* ` 60 */, 1 /* a 61 */, 1 /* b 62 */, 1 /* c 63 */, 1 /* d 64 */, 1 /* e 65 */, 1 /* f 66 */, 1 /* g 67 */,
        1 /* h 68 */, 1 /* i 69 */, 1 /* j 6A */, 1 /* k 6B */, 1 /* l 6C */, 1 /* m 6D */, 1 /* n 6E */, 1 /* o 6F */,
        1 /* p 70 */, 1 /* q 71 */, 1 /* r 72 */, 1 /* s 73 */, 1 /* t 74 */, 1 /* u 75 */, 1 /* v 76 */, 1 /* w 77 */,
        1 /* x 78 */, 1 /* y 79 */, 1 /* z 7A */, 1 /* 0 7B */, 1 /* 0 7C */, 1 /* 0 7D */, 1 /* 0 7E */, 0 /*   7F */
    };

    // not really for UTF8 (only ASCII), but should
    static public String recode_UTF8_FILENAME(CallContext context, String string) {
        return recode_UTF8_FILENAME(context, string, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_FILENAME(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        byte[] bytes;
        try {
            bytes = string.getBytes("UTF-8");
        } catch (java.io.UnsupportedEncodingException uee) {
            CustomaryContext.create((Context)context).throwLimitation(context, "System (VM) does not support UTF-8 encoding");
            throw (ExceptionLimitation) null;
        }
        StringBuffer sb = new StringBuffer();
        for (int b : bytes) {
            if (b < 0) { b += 256; }
            int code = (b > 0x007f ? 0 : FileNameCode[b]);
            sb.append(code == 1 ? ((char)b) : "_");
        }
        return sb.toString();
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_SHA1(CallContext context, String string) {
        return recode_UTF8_SHA1(context, string, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_SHA1(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException nsae) {
            CustomaryContext.create(Context.create(context)).throwConfigurationError(context, "MessageDigest algorithm SHA1 not available");
            throw (ExceptionConfigurationError) null; // compiler insists
        }

        byte[] bytes = null;
        try {
            bytes = md.digest(string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException uee) {
        }

        int i, n;
        char[] chars = new char[bytes.length*2];
        for (i = bytes.length - 1; i >= 0; i--) {
            n = (int)bytes[i] & 0xFF;
            chars[i*2]   = HEX_CHARS[n/16];
            chars[i*2+1] = HEX_CHARS[n%16];
        }
        
        return new String(chars);
    }

    private static final char HEX_CHARS[] = new char[] {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    // ---------------------------------------------------------------------------------------------------

    // not really for UTF8 (only ASCII), but should
    static public String recode_UTF8_XML(CallContext context, String string) {
        return recode_UTF8_XML(context, string, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_UTF8_XML(CallContext context, CharSequence string, StringBuilder output) {
        return recode_UTF8_XML(context, string, output, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_XML(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return recode_UTF8_XML(context, string, null, recoding_target_context).toString();
    }

    static public StringBuilder recode_UTF8_XML(CallContext context, CharSequence string, StringBuilder output, RecodingTargetContext recoding_target_context) {
        int len = string.length();
        output = prepareOutput(context, output, len+8);

        char c;
        for (int i = 0; i < len; i++) {
            c = string.charAt(i);
            if (c == '<') { output.append("&lt;"); continue; }
            if (c == '>') { output.append("&gt;"); continue; }
            if (c == '&') { output.append("&amp;"); continue; }
            output.append(c);
        }
        return output;
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_XMLATT(CallContext context, String string) {
        return recode_UTF8_XMLATT(context, string, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_UTF8_XMLATT(CallContext context, CharSource string, Appendable output) {
        return recode_UTF8_XMLATT(context, string, output, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_UTF8_XMLATT(CallContext context, CharSequence sequence, Appendable output) {
        return recode_UTF8_XMLATT(context, sequence == null ? null : new CharSourceCharSequence(context, sequence), output, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_UTF8_XMLATT(CallContext context, Reader reader, Appendable output) {
        return recode_UTF8_XMLATT(context, reader == null ? null : new CharSourceReader(context, reader), output, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_XMLATT(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        StringBuilder sb = recode_UTF8_XMLATT(context, string, null, recoding_target_context);
        return sb == null ? null : sb.toString();
    }

    static public StringBuilder recode_UTF8_XMLATT(CallContext context, CharSequence sequence, Appendable appendable, RecodingTargetContext recoding_target_context) {
        return recode_UTF8_XMLATT(context, sequence == null ? null : new CharSourceCharSequence(context, sequence), appendable, recoding_target_context);
    }

    static public StringBuilder recode_UTF8_XMLATT(CallContext context, Reader reader, Appendable appendable, RecodingTargetContext recoding_target_context) {
        return recode_UTF8_XMLATT(context, reader == null ? null : new CharSourceReader(context, reader), appendable, recoding_target_context);
    }

    static public StringBuilder recode_UTF8_XMLATT(CallContext context, CharSource string, Appendable appendable, RecodingTargetContext recoding_target_context) {
        if (string == null) { return null; }

        Output output = prepareOutput(context, appendable, string, recoding_target_context);

        int i;
        while ((i = string.read(context)) != -1) {
            char c = (char) i;
            if (c == '<')  { output.append(context, "&lt;"); continue; }
            if (c == '>')  { output.append(context, "&gt;"); continue; }
            if (c == '&')  { output.append(context, "&amp;"); continue; }
            if (c == '"')  { output.append(context, "&quot;"); continue; }
            if (c == '\'') { output.append(context, "&apos;"); continue; }
            if (c == '\n') { output.append(context, "&#13;&#10;"); continue; }
            output.append(context, c);
        }

        return output.to_return;
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_XMLITEXT_UTF8(CallContext context, String string) {
        return recode_XMLITEXT_UTF8(context, string, (RecodingTargetContext) null);
    }

    static public String recode_XMLITEXT_UTF8(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        string = string.replaceFirst("^([ \\n]*\\n)", "").replaceFirst("([ \\n]*)$", "");
        CharacterIterator iter = new StringCharacterIterator(string);
        StringBuffer sb = new StringBuffer();
        for (char c = iter.first(); c != CharacterIterator.DONE && c == ' '; c = iter.next()) {
            sb.append(c);
        }
        return string.replaceAll("(^|\\n)" + sb.toString(), "$1");
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_JSON(CallContext context, String string) {
        return recode_UTF8_JSON(context, string, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_UTF8_JSON(CallContext context, CharSource string, Appendable output) {
        return recode_UTF8_JSON(context, string, output, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_UTF8_JSON(CallContext context, CharSequence sequence, Appendable output) {
        return recode_UTF8_JSON(context, sequence == null ? null : new CharSourceCharSequence(context, sequence), output, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_UTF8_JSON(CallContext context, Reader reader, Appendable output) {
        return recode_UTF8_JSON(context, reader == null ? null : new CharSourceReader(context, reader), output, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_JSON(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        StringBuilder sb = recode_UTF8_JSON(context, string, null, recoding_target_context);
        return sb == null ? null : sb.toString();
    }

    static public StringBuilder recode_UTF8_JSON(CallContext context, CharSequence sequence, Appendable appendable, RecodingTargetContext recoding_target_context) {
        return recode_UTF8_JSON(context, sequence == null ? null : new CharSourceCharSequence(context, sequence), appendable, recoding_target_context);
    }

    static public StringBuilder recode_UTF8_JSON(CallContext context, Reader reader, Appendable appendable, RecodingTargetContext recoding_target_context) {
        return recode_UTF8_JSON(context, reader == null ? null : new CharSourceReader(context, reader), appendable, recoding_target_context);
    }

    static public StringBuilder recode_UTF8_JSON(CallContext context, CharSource string, Appendable appendable, RecodingTargetContext recoding_target_context) {
        if (string == null) { return null; }

        Output output = prepareOutput(context, appendable, string, recoding_target_context);

        char         c = 0;
        int          i;
        char         b;
        String       t;
        while ((i = string.read(context)) != -1) {
            b = c;
            c = (char) i;

            switch (c) {
                case '\\':
                case '"':
                    output.append(context, '\\');
                    output.append(context, c);
                    break;
                case '/':
                    if (b == '<') {
                        output.append(context, '\\');
                    }
                    output.append(context, c);
                    break;
                case '\b':
                    output.append(context, "\\b");
                    break;
                case '\t':
                    output.append(context, "\\t");
                    break;
                case '\n':
                    output.append(context, "\\n");
                    break;
                case '\f':
                    output.append(context, "\\f");
                    break;
                case '\r':
                    output.append(context, "\\r");
                    break;
                default:
                    if (c < ' ' || (c >= '\u0080' && c < '\u00a0') ||
                                   (c >= '\u2000' && c < '\u2100')) {
                        t = "000" + Integer.toHexString(c);
                        output.append(context, "\\u" + t.substring(t.length() - 4));
                    } else {
                        output.append(context, c);
                    }
            }
        }

        return output.to_return;
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_TEX(CallContext context, String string) {
        return recode_UTF8_TEX(context, string, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_UTF8_TEX(CallContext context, CharSource string, Appendable output) {
        return recode_UTF8_TEX(context, string, output, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_UTF8_TEX(CallContext context, CharSequence sequence, Appendable output) {
        return recode_UTF8_TEX(context, sequence == null ? null : new CharSourceCharSequence(context, sequence), output, (RecodingTargetContext) null);
    }

    static public StringBuilder recode_UTF8_TEX(CallContext context, Reader reader, Appendable output) {
        return recode_UTF8_TEX(context, reader == null ? null : new CharSourceReader(context, reader), output, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_TEX(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        StringBuilder sb = recode_UTF8_TEX(context, string, null, recoding_target_context);
        return sb == null ? null : sb.toString();
    }

    static public StringBuilder recode_UTF8_TEX(CallContext context, CharSequence sequence, Appendable appendable, RecodingTargetContext recoding_target_context) {
        return recode_UTF8_TEX(context, sequence == null ? null : new CharSourceCharSequence(context, sequence), appendable, recoding_target_context);
    }

    static public StringBuilder recode_UTF8_TEX(CallContext context, Reader reader, Appendable appendable, RecodingTargetContext recoding_target_context) {
        return recode_UTF8_TEX(context, reader == null ? null : new CharSourceReader(context, reader), appendable, recoding_target_context);
    }

    static public StringBuilder recode_UTF8_TEX(CallContext context, CharSource string, Appendable appendable, RecodingTargetContext recoding_target_context) {
        if (string == null) { return null; }

        Output output = prepareOutput(context, appendable, string, recoding_target_context);

        char         c = 0;
        int          i;
        while ((i = string.read(context)) != -1) {
            // ⟦ protected region ⟧
            if (i == 0x27E6) {
                while ((i = string.read(context)) != -1) {
                    if (i == 0X27E7) { break; }
                    c = (char) i;
                    output.append(context, c);
                }
                continue;
            }

            c = (char) i;
            switch (c) {
                case '%':
                case '&':
                case '$':
                case '#':
                case '{':
                case '}':
                case '_':
                    output.append(context, '\\');
                    output.append(context, c);
                    break;
                case '\n':
                    output.append(context, "\\\\");
                    break;
                case '¶':
                    output.append(context, "\\P{}");
                    break;
                case '|':
                    output.append(context, "\\textbar{}");
                    break;
                case '<':
                    output.append(context, "\\textless{}");
                    break;
                case '>':
                    output.append(context, "\\textgreater{}");
                    break;
                case '–':
                    output.append(context, "\\textendash{}");
                    break;
                case '§':
                    output.append(context, "\\S{}");
                    break;
                case '\\':
                    output.append(context, "\\textbackslash{}");
                    break;
                case '—':
                    output.append(context, "\\textemdash{}");
                    break;
                case '^':
                    output.append(context, "\\textasciicircum{}");
                    break;
                case '~':
                    output.append(context, "\\~{}");
                    break;
                default:
                    output.append(context, c);
                    break;
            }
        }

        return output.to_return;
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_LC_UC(CallContext context, String string) {
        return recode_LC_UC(context, string, (RecodingTargetContext) null);
    }

    static public String recode_LC_UC(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return string.toUpperCase();
    }

    // ---------------------------------------------------------------------------------------------------

    static protected Pattern mcp1;
    static protected Pattern mcp2;

    static public String recode_LCU_MC(CallContext context, String string) {
        return recode_LCU_MC(context, string, (RecodingTargetContext) null);
    }

    static public String recode_LCU_MC(CallContext context, String string, RecodingTargetContext recoding_target_context) {

        if (mcp1 == null) {
            try {
                mcp1 = Pattern.compile("_([a-z])");
                mcp2 = Pattern.compile("^([a-z])");
            } catch (PatternSyntaxException pse) {
                CustomaryContext.create(Context.create(context)).throwAssertionProvedFalse(context, pse, "Syntax error in regular expression");
                throw (ExceptionAssertionProvedFalse) null; // compiler insists
            }
        }

        Matcher m1 = mcp1.matcher(string);
        StringBuffer sb1 = new StringBuffer();
        while (m1.find()) {
            m1.appendReplacement(sb1, m1.group(1).toUpperCase());
        }
        m1.appendTail(sb1);

        Matcher m2 = mcp2.matcher(sb1);
        StringBuffer sb2 = new StringBuffer();
        while (m2.find()) {
            m2.appendReplacement(sb2, m2.group(1).toUpperCase());
        }
        m2.appendTail(sb2);

        return sb2.toString();
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_MCB_MC(CallContext context, String string) {
        return recode_MCB_MC(context, string, (RecodingTargetContext) null);
    }

    static public String recode_MCB_MC(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return string.replaceAll(" ","");
    }

    // ---------------------------------------------------------------------------------------------------

    static protected Pattern lcup1;
    static protected Pattern lcup2;
    static protected Pattern lcup3;

    static void init_lcup(CallContext context) {
        if (lcup1 == null) {
            try {
                lcup1 = Pattern.compile("\\B([A-Z])([a-z0-9])");
                lcup2 = Pattern.compile("([a-z0-9])([A-Z]+)");
                lcup3 = Pattern.compile("([A-Z]+)");
            } catch (PatternSyntaxException pse) {
                CustomaryContext.create(Context.create(context)).throwAssertionProvedFalse(context, pse, "Syntax error in regular expression");
                throw (ExceptionAssertionProvedFalse) null; // compiler insists
            }
        }
    }

    static public String recode_MC_LCU(CallContext context, String string) {
        return recode_MC_LCU(context, string, (RecodingTargetContext) null);
    }

    static public String recode_MC_LCU(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        init_lcup(context);

        Matcher m1 = lcup1.matcher(string);
        StringBuffer sb1 = new StringBuffer();
        while (m1.find()) {
            m1.appendReplacement(sb1, "_" + m1.group(1).toLowerCase() + m1.group(2));
        }
        m1.appendTail(sb1);

        Matcher m2 = lcup2.matcher(sb1);
        StringBuffer sb2 = new StringBuffer();
        while (m2.find()) {
            m2.appendReplacement(sb2, m2.group(1) + "_" + m2.group(2).toLowerCase());
        }
        m2.appendTail(sb2);

        Matcher m3 = lcup3.matcher(sb2);
        StringBuffer sb3 = new StringBuffer();
        while (m3.find()) {
            m3.appendReplacement(sb3, m3.group(1).toLowerCase());
        }
        m3.appendTail(sb3);

        return sb3.toString();
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_MC_MCB(CallContext context, String string) {
        return recode_MC_MCB(context, string, (RecodingTargetContext) null);
    }

    static public String recode_MC_MCB(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        init_lcup(context);

        Matcher m1 = lcup1.matcher(string);
        StringBuffer sb1 = new StringBuffer();
        while (m1.find()) {
            m1.appendReplacement(sb1, " " + m1.group(1) + m1.group(2));
        }
        m1.appendTail(sb1);

        Matcher m2 = lcup2.matcher(sb1);
        StringBuffer sb2 = new StringBuffer();
        while (m2.find()) {
            m2.appendReplacement(sb2, m2.group(1) + " " + m2.group(2));
        }
        m2.appendTail(sb2);

        return sb2.toString();
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_MC_LC(CallContext context, String string) {
        return recode_MC_LC(context, string, (RecodingTargetContext) null);
    }

    static public String recode_MC_LC(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return string.toLowerCase();
    }

    // ---------------------------------------------------------------------------------------------------

    static protected Pattern ucup1;
    static protected Pattern ucup2;
    static protected Pattern ucup3;

    static public String recode_MC_UCU(CallContext context, String string) {
        return recode_MC_UCU(context, string, (RecodingTargetContext) null);
    }

    static public String recode_MC_UCU(CallContext context, String string, RecodingTargetContext recoding_target_context) {

        if (ucup1 == null) {
            try {
                ucup1 = Pattern.compile("\\B([A-Z])([a-z0-9])");
                ucup2 = Pattern.compile("([a-z0-9])([A-Z]+)");
                ucup3 = Pattern.compile("([a-z]+)");
            } catch (PatternSyntaxException pse) {
                CustomaryContext.create(Context.create(context)).throwAssertionProvedFalse(context, pse, "Syntax error in regular expression");
                throw (ExceptionAssertionProvedFalse) null; // compiler insists
            }
        }

        Matcher m1 = ucup1.matcher(string);
        StringBuffer sb1 = new StringBuffer();
        while (m1.find()) {
            m1.appendReplacement(sb1, "_" + m1.group(1) + m1.group(2).toUpperCase());
        }
        m1.appendTail(sb1);

        Matcher m2 = ucup2.matcher(sb1);
        StringBuffer sb2 = new StringBuffer();
        while (m2.find()) {
            m2.appendReplacement(sb2, m2.group(1).toUpperCase() + "_" + m2.group(2));
        }
        m2.appendTail(sb2);

        Matcher m3 = ucup3.matcher(sb1);
        StringBuffer sb3 = new StringBuffer();
        while (m3.find()) {
            m3.appendReplacement(sb3, m3.group(1).toUpperCase());
        }
        m3.appendTail(sb3);

        return sb3.toString();
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_MC_UC(CallContext context, String string) {
        return recode_MC_UC(context, string, (RecodingTargetContext) null);
    }

    static public String recode_MC_UC(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return string.toUpperCase();
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_MC_CB(CallContext context, String string) {
        return recode_MC_CB(context, string, (RecodingTargetContext) null);
    }

    static public String recode_MC_CB(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        int len = string.length();
        return (len == 0 ? "" : (string.substring(0,1).toLowerCase() + (len == 1 ? "" : string.substring(1))));
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_MC_STUC(CallContext context, String string) {
        return recode_MC_STUC(context, string, (RecodingTargetContext) null);
    }

    static public String recode_MC_STUC(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        int len = string.length();
        return (len == 0 ? "" : (string.substring(0,1).toUpperCase() + (len == 1 ? "" : string.substring(1))));
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_LCU_LCD(CallContext context, String string) {
        return recode_LCU_LCD(context, string, (RecodingTargetContext) null);
    }

    static public String recode_LCU_LCD(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return string.replace('_','-');
    }
    // ---------------------------------------------------------------------------------------------------

    static public String recode_LCD_LCU(CallContext context, String string) {
        return recode_LCU_LCD(context, string, (RecodingTargetContext) null);
    }

    static public String recode_LCD_LCU(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return string.replace('-','_');
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_INDENT(CallContext context, String string, String indent, int amount) {
        return recode_UTF8_INDENT(context, string, (RecodingTargetContext) null, indent, amount);
    }

    static public String recode_UTF8_INDENT(CallContext context, String string, RecodingTargetContext recoding_target_context, String indent, Integer amount) {
        CharacterIterator iter = new StringCharacterIterator(string);
        StringBuilder sb = new StringBuilder();
        int last_pos = 0;
        int pos = -1;
        int len = string.length();
        while (len > last_pos && (pos = string.indexOf('\n', last_pos)) != -1) {
            if (   recoding_target_context != null
                && recoding_target_context.getBeginningOfLine(context)) {
                for (int i=0; i<amount; i++) { sb.append(indent); }
            }
            sb.append(string.substring(last_pos, pos+1));
            if (recoding_target_context != null) {
                recoding_target_context.setBeginningOfLine(context, true);
            } else {
                for (int i=0; i<amount; i++) { sb.append(indent); }
            }
            last_pos = pos+1;
        }
        if (len > last_pos) {
            if (   recoding_target_context != null
                && recoding_target_context.getBeginningOfLine(context)) {
                for (int i=0; i<amount; i++) { sb.append(indent); }
            }
            sb.append(string.substring(last_pos, len));
            if (recoding_target_context != null) {
                recoding_target_context.setBeginningOfLine(context, false);
            }
        }
        return sb.toString();
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_ABBREV(CallContext context, String string, int limit, String postfix) {
        return recode_UTF8_ABBREV(context, string, (RecodingTargetContext) null, limit, postfix);
    }

    static public String recode_UTF8_ABBREV(CallContext context, String string, RecodingTargetContext recoding_target_context, Integer limit, String postfix) {
        int len = string.length();
        if (len > limit) {
            string = string.substring(0, limit) + postfix;
        }
        return string;
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_REGEXP(CallContext context, String string, String pattern, String replacement) {
        return recode_UTF8_REGEXP(context, string, (RecodingTargetContext) null, pattern, replacement);
    }

    static public String recode_UTF8_REGEXP(CallContext context, String string, RecodingTargetContext recoding_target_context, String pattern, String replacement) {
        return string.replaceAll(pattern, replacement);
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_FIXED(CallContext context, String string, int length, String fill_character, String justification) {
        return recode_UTF8_FIXED(context, string, (RecodingTargetContext) null, length, fill_character, justification);
    }

    static public String recode_UTF8_FIXED(CallContext context, String string, RecodingTargetContext recoding_target_context, int length, String fill_character, String justification) {
        int len = string.length();
        if (len > length) {
            return string.substring(0, length);
        } else if (len == length) {
            return string;
        }
        int diff = length - len;
        StringBuilder sb = new StringBuilder();
        if (justification.matches("[rR]")) {
            for (int i=0; i<diff; i++) {
                sb.append(fill_character);
            }
        } else if (justification.matches("[cC]")) {
            for (int i=0; i<(diff/2); i++) {
                sb.append(fill_character);
            }
        }
        sb.append(string);
        if (justification.matches("[lL]")) {
            for (int i=0; i<diff; i++) {
                sb.append(fill_character);
            }
        } else if (justification.matches("[cC]")) {
            for (int i=0; i<(diff/2)+(diff%2); i++) {
                sb.append(fill_character);
            }
        }
        return sb.toString();
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_REESC(CallContext context, String string) {
        return recode_UTF8_REESC(context, string, (RecodingTargetContext) null);
    }

    static public String recode_UTF8_REESC(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return string.replaceAll("([\\+*.?{}()\\[\\]])", "\\\\$1");
    }

    // ---------------------------------------------------------------------------------------------------

    /**
       Encoding of UTF8 Strings to application/x-www-form-urlencoded via standard method.
       
       Similarly this would work for
       US-ASCII      Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of the Unicode character set
       ISO-8859-1    ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1
       UTF-8         Eight-bit UCS Transformation Format
       UTF-16LE      Sixteen-bit UCS Transformation Format, little-endian byte order
       UTF-16        Sixteen-bit UCS Transformation Format, byte order identified by an optional byte-order mark
       
       @param context
       @param string
       @return
     */
    static public String recode_UTF8_URIFORM(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        initialise(context);
        try {
            return URLEncoder.encode(string, "UTF-8");
        } catch (java.io.UnsupportedEncodingException uee) {
            CustomaryContext.create((Context)context).throwLimitation(context, "System (VM) does not support UTF-8 encoding");
            throw (ExceptionLimitation) null;
        }
    }

    /**
      Decode von UTF8 Strings nach application/x-www-form-urlencoded per Standardfunktion.
      @param context
      @param string
      @return
    */
    static public String recode_URIFORM_UTF8(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        initialise(context);
        try {
            return URLDecoder.decode(string, "UTF-8");
        } catch (java.io.UnsupportedEncodingException uee) {
            CustomaryContext.create((Context)context).throwLimitation(context, "System (VM) does not support UTF-8 encoding");
            throw (ExceptionLimitation) null;
        }
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_JAVADOC_DOCBOOK(CallContext context, String string) {
        return recode_JAVADOC_DOCBOOK(context, string, (RecodingTargetContext) null);
    }

    static public String recode_JAVADOC_DOCBOOK(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return recode_UTF8_XML(context, string, recoding_target_context);
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_DOCBOOK_JAVADOC(CallContext context, String string) {
        return recode_DOCBOOK_JAVADOC(context, string, (RecodingTargetContext) null);
    }

    static public String recode_DOCBOOK_JAVADOC(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return string;
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_DOCBOOK_HTMLPRE(CallContext context, String string) {
        return recode_DOCBOOK_HTMLPRE(context, string, (RecodingTargetContext) null);
    }

    static public String recode_DOCBOOK_HTMLPRE(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return string.replaceAll("(<para[^>]*>)|(</para>)","").replaceAll("&","&amp;").replaceAll(">","&gt;").replaceAll("<","&lt;");
    }
    
    // ---------------------------------------------------------------------------------------------------
    
    static public String recode_XML_JAVAPROP(CallContext context, String string) {
      return recode_XML_JAVAPROP(context, string, (RecodingTargetContext) null);
    }

    static public String recode_XML_JAVAPROP(CallContext context, String string, RecodingTargetContext recoding_target_context) {
      return string.replaceAll("&lt;", "<").replaceAll("&gt;",">").replaceAll("&amp;","&");
    }
    
    // ---------------------------------------------------------------------------------------------------
    
    static public String recode_XML_UTF8(CallContext context, String string) {
        return recode_XML_JAVAPROP(context, string, (RecodingTargetContext) null);
    }

    static public String recode_XML_UTF8(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return string.replaceFirst("^<[CDATA[(.*)]]>","$1").replaceAll("&lt;", "<").replaceAll("&gt;",">").replaceAll("&amp;","&");
    }
    
    // ---------------------------------------------------------------------------------------------------

    static public String recode_DOCBOOK_HTML(CallContext context, String string, LinkRecoder link_recoder, Integer heading_depth) {
        return recode_DOCBOOK_HTML(context, string, (RecodingTargetContext) null, link_recoder, heading_depth);
    }

    static public String recode_DOCBOOK_HTML(CallContext context, String string, RecodingTargetContext recoding_target_context, LinkRecoder link_recoder, Integer heading_depth) {
        string = string.replaceAll("<para[^>]*>","<p>").replaceAll("</para>","</p>")
                       .replaceAll("<section[^>]*>","<div class=\"section\">").replaceAll("</section>","</div>")
                       .replaceAll("<(/?)title[^>]*>","<$1h" + heading_depth + ">")
                       .replaceAll("<(/?)simplelist[^>]*>","<$1ul>")
                       .replaceAll("<(/?)member[^>]*>","<$1li>")
                       .replaceAll("<ulink\\s+url=\"([^\"]*)\"\\s*>","<a href=\"$1\">").replaceAll("</ulink>","</a>")
                       .replaceAll("<programlisting\\s+language=\"([^\"]*)\"\\s*>","<pre class=\"$1\">").replaceAll("<(/?)programlisting[^>]*>","<$1pre>");

        if (link_recoder == null) { return string; }

        Matcher m = link_pattern.matcher(string);
        StringBuffer sb = new StringBuffer();
        boolean outside = true;
        boolean warning = false;
        while (m.find()) {
            String all   = m.group(0);
            String links = m.group(1);
            String atts  = m.group(2);
            String linke = m.group(3);
//             int s = m.start();
//             int e = m.end();
            if (outside && "<link".equals(links) && linke == null) {
                Matcher m2 = link_att_pattern.matcher(atts);
                HashMap<String,String> atth = new HashMap<String,String>();
                while (m2.find()) {
                    String name  = m2.group(1);
                    String value = m2.group(2);
                    atth.put(name, value);
                }
                String recoded_link = link_recoder.recode(context, "link", atth);
                m.appendReplacement(sb, "");
                if (recoded_link != null) {
                    sb.append("<a href=\"" + recoded_link + "\">");
                    warning = false;
                } else {
                    sb.append("<span class=\"state warning\">&lt;link " + atts + "&gt;</span>");
                    warning = true;
                }
                outside = false;
            } else if (outside && ("<mediaobject".equals(links) || "<inlinemediaobject".equals(links)) && linke == null) {
                m.appendReplacement(sb, "");
                outside = false;
            } else if (outside == false && links == null && "</link>".equals(linke)) {
                m.appendReplacement(sb, "");
                if (warning == false) {
                    sb.append("</a>");
                } else {
                    sb.append("<span class=\"state warning\">&lt;/link&gt;</span>");
                }
                warning = false;
                outside = true;
            } else if (outside == false && links == null && ("</mediaobject>".equals(linke) || "</inlinemediaobject>".equals(linke))) {
                StringBuffer lcsb = new StringBuffer();
                m.appendReplacement(lcsb, "");
                String lcs = lcsb.toString();
                Matcher m2 = medialink_pattern.matcher(lcs);
                if (m2.matches() == false) {
                    sb.append("<span class=\"state warning\">&lt;mediaobject&gt;" + recode_UTF8_XML(context, lcs) + "&lt;/mediaobject&gt;</span>");
                    warning = true;
                } else {
                    String ref = m2.group(1);
                    String alt = m2.group(2);

                    HashMap<String,String> atth = new HashMap<String,String>();
                    atth.put("imageobject/imagedata/fileref", ref);

                    String recoded_link = link_recoder.recode(context, "mediaobject", atth);
                    if (recoded_link != null) {
                        sb.append("<img src=\"");
                        sb.append(recoded_link);
                        sb.append("\" alt=\"");
                        sb.append(alt);
                        sb.append("\"/>");
                        warning = false;
                    } else {
                        sb.append("<span class=\"state warning\">&lt;link " + atts + "&gt;</span>");
                        warning = true;
                    }
                }
                outside = true;
            } else {
                m.appendReplacement(sb, "");
                sb.append("<span class=\"state error\">" + recode_UTF8_XML(context, all) + "</span>");
            }
        }
        m.appendTail(sb);
        return sb.toString();

    }

    // ---------------------------------------------------------------------------------------------------

    static public EncodingService encoding_service_DOCPAGE_HTML;
    
    static public String recode_DOCPAGE_HTML(CallContext context, String string, LinkRecoder link_recoder, Map arguments) {
        return recode_DOCPAGE_HTML(context, string, (RecodingTargetContext) null, link_recoder, arguments);
    }

    static public StringBuilder recode_DOCPAGE_HTML(CallContext context, CharSequence string, StringBuilder output, LinkRecoder link_recoder, Map arguments) {
        return recode_DOCPAGE_HTML(context, string, output, (RecodingTargetContext) null, link_recoder, arguments);
    }

    static public String recode_DOCPAGE_HTML(CallContext context, String string, RecodingTargetContext recoding_target_context, LinkRecoder link_recoder, Map arguments) {
        return recode_DOCPAGE_HTML(context, string, null, recoding_target_context, link_recoder, arguments).toString();
    }

    static public StringBuilder recode_DOCPAGE_HTML(CallContext context, CharSequence input, StringBuilder output, RecodingTargetContext recoding_target_context, LinkRecoder link_recoder, Map arguments) {

        /*
          Well, this is somewhat strange.
          Two worlds:
             1. Doclet/documentation
                - DocletRepository
                - DocletXML
                - Doclet....template
                - intern ursprünglich Encoding-DOCBOOK-HTML
                - DocletLinkRecoder
                etc.
             2. doclet/page/html/files-backend
                - doclet2page
                - page2html
                - linkroot
                etc.

          Workaround:
          - link-handling in page2html disableble
          - "doclet2page" not considered
          - here links with two stringbuffers - imperformant

          Think about it more thorough how to unify better

          Plus (horror...):
               "Matcher.append..." akcepts only StringBuffer, not StringBuilder
         */

        int len = input.length();
        output = prepareOutput(context, output, len);

        StringBuffer intermediate = new StringBuffer(len);

        if (encoding_service_DOCPAGE_HTML == null) {
            encoding_service_DOCPAGE_HTML = getEncodingService(context, Encoding.DOCPAGE, Encoding.HTML);
        }

        encoding_service_DOCPAGE_HTML.recode(context, input, intermediate, link_recoder, arguments);

        if (link_recoder == null) {
            output.append(intermediate);
            return output;
        }

        StringBuffer sb = new StringBuffer(len);
        Matcher m = oorl_pattern.matcher(intermediate.toString());
        boolean outside = true;
        boolean warning = false;
        while (m.find()) {
            String all   = m.group(0);
            String link  = m.group(1);
            String recoded_link = link_recoder.recode(context, link, null);
            m.appendReplacement(sb, "");
            if (recoded_link != null) {
                sb.append(recoded_link);
            } else {
                sb.append("javascript:alert('Invalid Link: " + link + "')");
            }
        }
        m.appendTail(sb);
        output.append(sb);
        return output;
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_DOCBOOK_WIKI(CallContext context, String string) {
        return recode_DOCBOOK_WIKI(context, string, (RecodingTargetContext) null);
    }

    static public String recode_DOCBOOK_WIKI(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return string.replaceAll("(<para[^>]*>)|(</para>)","").replaceAll("<(synopsis|code)[^>]*>","§(code§)").replaceAll("</(synopsis|code)>","§(/code§)").replaceAll("<emphasis[^>]*>","'''").replaceAll("</emphasis>","'''").replaceAll("&","&amp;").replaceAll(">","&gt;").replaceAll("<","&lt;").replaceAll("§\\(","<").replaceAll("§\\)",">").replaceAll("\n( *)","\n").replaceFirst("^ *","");
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_DOCBOOK_JAVA(CallContext context, String string) {
        return recode_DOCBOOK_JAVA(context, string, (RecodingTargetContext) null);
    }

    static public String recode_DOCBOOK_JAVA(CallContext context, String string, RecodingTargetContext recoding_target_context) {
        return string.replaceAll("<para[^>]*>","").replaceAll("</para>","\n\n").replaceAll("<(synopsis|code)[^>]*>","'").replaceAll("</(synopsis|code)>","'").replaceAll("</?(literal|informaltable|tgroup|tbody|simplelist|member)[^>]*>","").replaceAll("<entry>","").replaceAll("</entry>[ \n]*</row>","</row>").replaceAll("</row>[ \n]*<row>","</row>").replaceAll("[ \n]*(<row>|</row>)[ \n]*","\n=========================================\n").replaceAll("[ \n]*</entry>[ \n]*","\n  -------------------------------------  \n").replaceAll("<emphasis[^>]*>","*").replaceAll("</emphasis>","*").replaceAll("\n( *)","\n").replaceFirst("^[\n ]*","").replaceFirst("[\n ]*$","").replaceAll(" *\n *(\n *)+","\n\n").replaceAll("\n","\\\\n").replaceAll("\"","\\\\\"");
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_INTEGER_FORMAT(CallContext context, String string, String format) {
        return recode_INTEGER_FORMAT(context, string, (RecodingTargetContext) null, format);
    }

    static public String recode_INTEGER_FORMAT(CallContext context, String string, RecodingTargetContext recoding_target_context, String format) {
        int i = Integer.parseInt(string.replaceFirst("^ *([0-9]+) *$", "$1"));
        return String.format(format, i);
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_FLOAT_FORMAT(CallContext context, String string, String format) {
        return recode_FLOAT_FORMAT(context, string, (RecodingTargetContext) null, format);
    }

    static public String recode_FLOAT_FORMAT(CallContext context, String string, RecodingTargetContext recoding_target_context, String format) {
        float f = Float.parseFloat(string.replaceFirst("^ *([0-9A-Za-z.+-]+) *$", "$1"));
        return String.format(format, f);
    }

    // ---------------------------------------------------------------------------------------------------

    static public String recode_UTF8_FORMAT(CallContext context, String string, String format) {
        return recode_UTF8_FORMAT(context, string, (RecodingTargetContext) null, format);
    }

    static public String recode_UTF8_FORMAT(CallContext context, String string, RecodingTargetContext recoding_target_context, String format) {
        return String.format(format, string);
    }
}
