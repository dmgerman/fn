begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 David Weitzman, Nizar N. Batada, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PushbackReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Reader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringTokenizer
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|*
import|;
end_import

begin_class
DECL|class|BibtexParser
specifier|public
class|class
name|BibtexParser
block|{
DECL|field|_in
specifier|private
name|PushbackReader
name|_in
decl_stmt|;
DECL|field|_db
specifier|private
name|BibtexDatabase
name|_db
decl_stmt|;
DECL|field|_meta
specifier|private
name|HashMap
name|_meta
decl_stmt|;
DECL|field|_eof
specifier|private
name|boolean
name|_eof
init|=
literal|false
decl_stmt|;
DECL|field|line
specifier|private
name|int
name|line
init|=
literal|1
decl_stmt|;
DECL|method|BibtexParser (Reader in)
specifier|public
name|BibtexParser
parameter_list|(
name|Reader
name|in
parameter_list|)
block|{
if|if
condition|(
name|in
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|NullPointerException
argument_list|()
throw|;
block|}
name|_in
operator|=
operator|new
name|PushbackReader
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
DECL|method|skipWhitespace ()
specifier|private
name|void
name|skipWhitespace
parameter_list|()
throws|throws
name|IOException
block|{
name|int
name|c
decl_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
name|c
operator|=
name|read
argument_list|()
expr_stmt|;
if|if
condition|(
operator|(
name|c
operator|==
operator|-
literal|1
operator|)
operator|||
operator|(
name|c
operator|==
literal|65535
operator|)
condition|)
block|{
name|_eof
operator|=
literal|true
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|Character
operator|.
name|isWhitespace
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
condition|)
block|{
continue|continue;
block|}
else|else
comment|// found non-whitespace char
comment|//Util.pr("SkipWhitespace, stops: "+c);
name|unread
argument_list|(
name|c
argument_list|)
expr_stmt|;
comment|/*	    try { 		Thread.currentThread().sleep(500); 		} catch (InterruptedException ex) {}*/
break|break;
block|}
block|}
DECL|method|parse ()
specifier|public
name|ParserResult
name|parse
parameter_list|()
throws|throws
name|IOException
block|{
name|_db
operator|=
operator|new
name|BibtexDatabase
argument_list|()
expr_stmt|;
comment|// Bibtex related contents.
name|_meta
operator|=
operator|new
name|HashMap
argument_list|()
expr_stmt|;
comment|// Metadata in comments for Bibkeeper.
name|ParserResult
name|_pr
init|=
operator|new
name|ParserResult
argument_list|(
name|_db
argument_list|,
name|_meta
argument_list|)
decl_stmt|;
name|skipWhitespace
argument_list|()
expr_stmt|;
try|try
block|{
while|while
condition|(
operator|!
name|_eof
condition|)
block|{
name|consumeUncritically
argument_list|(
literal|'@'
argument_list|)
expr_stmt|;
name|skipWhitespace
argument_list|()
expr_stmt|;
name|String
name|entryType
init|=
name|parseTextToken
argument_list|()
decl_stmt|;
name|BibtexEntryType
name|tp
init|=
name|BibtexEntryType
operator|.
name|getType
argument_list|(
name|entryType
argument_list|)
decl_stmt|;
if|if
condition|(
name|tp
operator|!=
literal|null
condition|)
block|{
comment|//Util.pr("Found: "+tp.getName());
name|BibtexEntry
name|be
init|=
name|parseEntry
argument_list|(
name|tp
argument_list|)
decl_stmt|;
name|boolean
name|duplicateKey
init|=
name|_db
operator|.
name|insertEntry
argument_list|(
name|be
argument_list|)
decl_stmt|;
if|if
condition|(
name|duplicateKey
condition|)
name|_pr
operator|.
name|addWarning
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Duplicate BibTeX key"
argument_list|)
operator|+
literal|": "
operator|+
name|be
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entryType
operator|.
name|toLowerCase
argument_list|()
operator|.
name|equals
argument_list|(
literal|"preamble"
argument_list|)
condition|)
block|{
name|_db
operator|.
name|setPreamble
argument_list|(
name|parsePreamble
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entryType
operator|.
name|toLowerCase
argument_list|()
operator|.
name|equals
argument_list|(
literal|"string"
argument_list|)
condition|)
block|{
name|BibtexString
name|bs
init|=
name|parseString
argument_list|()
decl_stmt|;
try|try
block|{
name|_db
operator|.
name|addString
argument_list|(
name|bs
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{
name|_pr
operator|.
name|addWarning
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Duplicate string name"
argument_list|)
operator|+
literal|": "
operator|+
name|bs
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
comment|//ex.printStackTrace();
block|}
block|}
elseif|else
if|if
condition|(
name|entryType
operator|.
name|toLowerCase
argument_list|()
operator|.
name|equals
argument_list|(
literal|"comment"
argument_list|)
condition|)
block|{
name|StringBuffer
name|comment
init|=
name|parseBracketedText
argument_list|()
decl_stmt|;
comment|/** 		     * 		     * Metadata are used to store Bibkeeper-specific 		     * information in .bib files. 		     * 		     * Metadata are stored in bibtex files in the format 		     * @comment{jabref-meta: type:data0;data1;data2;...} 		     * 		     * Each comment that starts with the META_FLAG is stored 		     * in the meta HashMap, with type as key. 		     * Unluckily, the old META_FLAG bibkeeper-meta: was used 		     * in JabRef 1.0 and 1.1, so we need to support it as 		     * well. At least for a while. We'll always save with the 		     * new one. 		     */
if|if
condition|(
name|comment
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|GUIGlobals
operator|.
name|META_FLAG
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|equals
argument_list|(
name|GUIGlobals
operator|.
name|META_FLAG
argument_list|)
operator|||
name|comment
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|GUIGlobals
operator|.
name|META_FLAG_OLD
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|equals
argument_list|(
name|GUIGlobals
operator|.
name|META_FLAG_OLD
argument_list|)
condition|)
block|{
name|String
name|rest
decl_stmt|;
if|if
condition|(
name|comment
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|GUIGlobals
operator|.
name|META_FLAG
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|equals
argument_list|(
name|GUIGlobals
operator|.
name|META_FLAG
argument_list|)
condition|)
name|rest
operator|=
name|comment
operator|.
name|substring
argument_list|(
name|GUIGlobals
operator|.
name|META_FLAG
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|rest
operator|=
name|comment
operator|.
name|substring
argument_list|(
name|GUIGlobals
operator|.
name|META_FLAG_OLD
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|pos
init|=
name|rest
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
decl_stmt|;
if|if
condition|(
name|pos
operator|>
literal|0
condition|)
name|_meta
operator|.
name|put
argument_list|(
name|rest
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|pos
argument_list|)
argument_list|,
name|rest
operator|.
name|substring
argument_list|(
name|pos
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|//else
comment|//    throw new RuntimeException("Unknown entry type: "+entryType);
name|skipWhitespace
argument_list|()
expr_stmt|;
block|}
return|return
name|_pr
return|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|kce
parameter_list|)
block|{
comment|//kce.printStackTrace();
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Duplicate ID in bibtex file: "
operator|+
name|kce
operator|.
name|toString
argument_list|()
argument_list|)
throw|;
block|}
block|}
DECL|method|peek ()
specifier|private
name|int
name|peek
parameter_list|()
throws|throws
name|IOException
block|{
name|int
name|c
init|=
name|read
argument_list|()
decl_stmt|;
name|unread
argument_list|(
name|c
argument_list|)
expr_stmt|;
return|return
name|c
return|;
block|}
DECL|method|read ()
specifier|private
name|int
name|read
parameter_list|()
throws|throws
name|IOException
block|{
name|int
name|c
init|=
name|_in
operator|.
name|read
argument_list|()
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|'\n'
condition|)
name|line
operator|++
expr_stmt|;
return|return
name|c
return|;
block|}
DECL|method|unread (int c)
specifier|private
name|void
name|unread
parameter_list|(
name|int
name|c
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|c
operator|==
literal|'\n'
condition|)
name|line
operator|--
expr_stmt|;
name|_in
operator|.
name|unread
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
DECL|method|parseString ()
specifier|public
name|BibtexString
name|parseString
parameter_list|()
throws|throws
name|IOException
block|{
comment|//Util.pr("Parsing string");
name|skipWhitespace
argument_list|()
expr_stmt|;
name|consume
argument_list|(
literal|'{'
argument_list|,
literal|'('
argument_list|)
expr_stmt|;
comment|//while (read() != '}');
name|skipWhitespace
argument_list|()
expr_stmt|;
comment|//Util.pr("Parsing string name");
name|String
name|name
init|=
name|parseTextToken
argument_list|()
decl_stmt|;
comment|//Util.pr("Parsed string name");
name|skipWhitespace
argument_list|()
expr_stmt|;
comment|//Util.pr("Now the contents");
name|consume
argument_list|(
literal|'='
argument_list|)
expr_stmt|;
name|String
name|content
init|=
name|parseFieldContent
argument_list|()
decl_stmt|;
comment|//Util.pr("Now I'm going to consume a }");
name|consume
argument_list|(
literal|'}'
argument_list|,
literal|')'
argument_list|)
expr_stmt|;
comment|//Util.pr("Finished string parsing.");
name|String
name|id
init|=
name|Util
operator|.
name|createNeutralId
argument_list|()
decl_stmt|;
return|return
operator|new
name|BibtexString
argument_list|(
name|id
argument_list|,
name|name
argument_list|,
name|content
argument_list|)
return|;
block|}
DECL|method|parsePreamble ()
specifier|public
name|String
name|parsePreamble
parameter_list|()
throws|throws
name|IOException
block|{
return|return
name|parseBracketedText
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|parseEntry (BibtexEntryType tp)
specifier|public
name|BibtexEntry
name|parseEntry
parameter_list|(
name|BibtexEntryType
name|tp
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|id
init|=
name|Util
operator|.
name|createId
argument_list|(
name|tp
argument_list|,
name|_db
argument_list|)
decl_stmt|;
name|BibtexEntry
name|result
init|=
operator|new
name|BibtexEntry
argument_list|(
name|id
argument_list|,
name|tp
argument_list|)
decl_stmt|;
name|skipWhitespace
argument_list|()
expr_stmt|;
name|consume
argument_list|(
literal|'{'
argument_list|,
literal|'('
argument_list|)
expr_stmt|;
name|skipWhitespace
argument_list|()
expr_stmt|;
name|String
name|key
init|=
literal|null
decl_stmt|;
name|boolean
name|doAgain
init|=
literal|true
decl_stmt|;
while|while
condition|(
name|doAgain
condition|)
block|{
name|doAgain
operator|=
literal|false
expr_stmt|;
try|try
block|{
if|if
condition|(
name|key
operator|!=
literal|null
condition|)
name|key
operator|=
name|key
operator|+
name|parseKey
argument_list|()
expr_stmt|;
comment|//parseTextToken(),
else|else
name|key
operator|=
name|parseKey
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoLabelException
name|ex
parameter_list|)
block|{
comment|// This exception will be thrown if the entry lacks a key
comment|// altogether, like in "@article{ author = { ...".
comment|// It will also be thrown if a key contains =.
name|char
name|c
init|=
operator|(
name|char
operator|)
name|peek
argument_list|()
decl_stmt|;
if|if
condition|(
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
argument_list|)
operator|||
operator|(
name|c
operator|==
literal|'{'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'\"'
operator|)
condition|)
block|{
name|String
name|cont
init|=
name|parseFieldContent
argument_list|()
decl_stmt|;
name|result
operator|.
name|setField
argument_list|(
name|ex
operator|.
name|getMessage
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|cont
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|key
operator|!=
literal|null
condition|)
name|key
operator|=
name|key
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
operator|+
literal|"="
expr_stmt|;
else|else
name|key
operator|=
name|ex
operator|.
name|getMessage
argument_list|()
operator|+
literal|"="
expr_stmt|;
name|doAgain
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|key
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|key
operator|=
literal|null
expr_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
name|result
operator|.
name|setField
argument_list|(
name|GUIGlobals
operator|.
name|KEY_FIELD
argument_list|,
name|key
argument_list|)
expr_stmt|;
name|skipWhitespace
argument_list|()
expr_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
name|int
name|c
init|=
name|peek
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|c
operator|==
literal|'}'
operator|)
operator|||
operator|(
name|c
operator|==
literal|')'
operator|)
condition|)
block|{
break|break;
block|}
comment|//if (key != null)
name|consume
argument_list|(
literal|','
argument_list|)
expr_stmt|;
name|skipWhitespace
argument_list|()
expr_stmt|;
name|c
operator|=
name|peek
argument_list|()
expr_stmt|;
if|if
condition|(
operator|(
name|c
operator|==
literal|'}'
operator|)
operator|||
operator|(
name|c
operator|==
literal|')'
operator|)
condition|)
block|{
break|break;
block|}
name|parseField
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
name|consume
argument_list|(
literal|'}'
argument_list|,
literal|')'
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
DECL|method|parseField (BibtexEntry entry)
specifier|private
name|void
name|parseField
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|key
init|=
name|parseTextToken
argument_list|()
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
comment|//Util.pr("_"+key+"_");
name|skipWhitespace
argument_list|()
expr_stmt|;
name|consume
argument_list|(
literal|'='
argument_list|)
expr_stmt|;
name|String
name|content
init|=
name|parseFieldContent
argument_list|()
decl_stmt|;
if|if
condition|(
name|content
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|entry
operator|.
name|getField
argument_list|(
name|key
argument_list|)
operator|==
literal|null
condition|)
name|entry
operator|.
name|setField
argument_list|(
name|key
argument_list|,
name|content
argument_list|)
expr_stmt|;
else|else
block|{
comment|// The following hack enables the parser to deal with multiple author or
comment|// editor lines, stringing them together instead of getting just one of them.
comment|// Multiple author or editor lines are not allowed by the bibtex format, but
comment|// at least one online database exports bibtex like that, making it inconvenient
comment|// for users if JabRef didn't accept it.
if|if
condition|(
name|key
operator|.
name|equals
argument_list|(
literal|"author"
argument_list|)
operator|||
name|key
operator|.
name|equals
argument_list|(
literal|"editor"
argument_list|)
condition|)
name|entry
operator|.
name|setField
argument_list|(
name|key
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|key
argument_list|)
operator|+
literal|" and "
operator|+
name|content
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|parseFieldContent ()
specifier|private
name|String
name|parseFieldContent
parameter_list|()
throws|throws
name|IOException
block|{
name|skipWhitespace
argument_list|()
expr_stmt|;
name|StringBuffer
name|value
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|int
name|c
decl_stmt|,
name|j
init|=
literal|'.'
decl_stmt|;
while|while
condition|(
operator|(
operator|(
name|c
operator|=
name|peek
argument_list|()
operator|)
operator|!=
literal|','
operator|)
operator|&&
operator|(
name|c
operator|!=
literal|'}'
operator|)
operator|&&
operator|(
name|c
operator|!=
literal|')'
operator|)
condition|)
block|{
if|if
condition|(
name|_eof
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Error in line "
operator|+
name|line
operator|+
literal|": EOF in mid-string"
argument_list|)
throw|;
block|}
if|if
condition|(
name|c
operator|==
literal|'"'
condition|)
block|{
comment|// value is a string
name|consume
argument_list|(
literal|'"'
argument_list|)
expr_stmt|;
while|while
condition|(
operator|!
operator|(
operator|(
name|peek
argument_list|()
operator|==
literal|'"'
operator|)
operator|&&
operator|(
name|j
operator|!=
literal|'\\'
operator|)
operator|)
condition|)
block|{
name|j
operator|=
name|read
argument_list|()
expr_stmt|;
if|if
condition|(
name|_eof
operator|||
operator|(
name|j
operator|==
operator|-
literal|1
operator|)
operator|||
operator|(
name|j
operator|==
literal|65535
operator|)
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Error in line "
operator|+
name|line
operator|+
literal|": EOF in mid-string"
argument_list|)
throw|;
block|}
name|value
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|j
argument_list|)
expr_stmt|;
block|}
name|consume
argument_list|(
literal|'"'
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'{'
condition|)
block|{
comment|// Value is a string enclosed in brackets. There can be pairs
comment|// of brackets inside of a field, so we need to count the brackets
comment|// to know when the string is finished.
name|value
operator|.
name|append
argument_list|(
name|parseBracketedText
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|Character
operator|.
name|isDigit
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
condition|)
block|{
comment|// value is a number
name|String
name|numString
init|=
name|parseTextToken
argument_list|()
decl_stmt|;
name|int
name|numVal
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|numString
argument_list|)
decl_stmt|;
name|value
operator|.
name|append
argument_list|(
operator|(
operator|new
name|Integer
argument_list|(
name|numVal
argument_list|)
operator|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|//entry.setField(key, new Integer(numVal));
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'#'
condition|)
block|{
comment|//value.append(" # ");
name|consume
argument_list|(
literal|'#'
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|textToken
init|=
name|parseTextToken
argument_list|()
decl_stmt|;
if|if
condition|(
name|textToken
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Error in line "
operator|+
name|line
operator|+
literal|" or above: "
operator|+
literal|"Empty text token.\nThis could be caused "
operator|+
literal|"by a missing comma between two fields."
argument_list|)
throw|;
name|value
operator|.
name|append
argument_list|(
literal|"#"
operator|+
name|textToken
operator|+
literal|"#"
argument_list|)
expr_stmt|;
comment|//Util.pr(parseTextToken());
comment|//throw new RuntimeException("Unknown field type");
block|}
name|skipWhitespace
argument_list|()
expr_stmt|;
block|}
comment|//Util.pr("Returning field content: "+value.toString());
return|return
name|value
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * This method is used to parse string labels, field names, entry      * type and numbers outside brackets.      */
DECL|method|parseTextToken ()
specifier|private
name|String
name|parseTextToken
parameter_list|()
throws|throws
name|IOException
block|{
name|StringBuffer
name|token
init|=
operator|new
name|StringBuffer
argument_list|(
literal|20
argument_list|)
decl_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
name|int
name|c
init|=
name|read
argument_list|()
decl_stmt|;
comment|//Util.pr(".. "+c);
if|if
condition|(
name|c
operator|==
operator|-
literal|1
condition|)
block|{
name|_eof
operator|=
literal|true
expr_stmt|;
return|return
name|token
operator|.
name|toString
argument_list|()
return|;
block|}
if|if
condition|(
name|Character
operator|.
name|isLetterOrDigit
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
operator|||
operator|(
name|c
operator|==
literal|':'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'-'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'_'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'*'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'+'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'.'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'/'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'\''
operator|)
condition|)
block|{
name|token
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|unread
argument_list|(
name|c
argument_list|)
expr_stmt|;
comment|//Util.pr("Pasted text token: "+token.toString());
return|return
name|token
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
block|}
comment|/**      * This method is used to parse the bibtex key for an entry.      */
DECL|method|parseKey ()
specifier|private
name|String
name|parseKey
parameter_list|()
throws|throws
name|IOException
throws|,
name|NoLabelException
block|{
name|StringBuffer
name|token
init|=
operator|new
name|StringBuffer
argument_list|(
literal|20
argument_list|)
decl_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
name|int
name|c
init|=
name|read
argument_list|()
decl_stmt|;
comment|//Util.pr(".. "+c);
if|if
condition|(
name|c
operator|==
operator|-
literal|1
condition|)
block|{
name|_eof
operator|=
literal|true
expr_stmt|;
return|return
name|token
operator|.
name|toString
argument_list|()
return|;
block|}
comment|// Ikke: #{}\uFFFD~\uFFFD
comment|//
comment|// G\uFFFDr:  $_*+.-\/?"^
if|if
condition|(
name|Character
operator|.
name|isLetterOrDigit
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
operator|||
operator|(
operator|(
name|c
operator|!=
literal|'#'
operator|)
operator|&&
operator|(
name|c
operator|!=
literal|'{'
operator|)
operator|&&
operator|(
name|c
operator|!=
literal|'}'
operator|)
operator|&&
operator|(
name|c
operator|!=
literal|'\uFFFD'
operator|)
operator|&&
operator|(
name|c
operator|!=
literal|'~'
operator|)
operator|&&
operator|(
name|c
operator|!=
literal|'\uFFFD'
operator|)
operator|&&
operator|(
name|c
operator|!=
literal|','
operator|)
operator|&&
operator|(
name|c
operator|!=
literal|'='
operator|)
operator|)
condition|)
block|{
name|token
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|c
operator|==
literal|','
condition|)
block|{
name|unread
argument_list|(
name|c
argument_list|)
expr_stmt|;
return|return
name|token
operator|.
name|toString
argument_list|()
return|;
comment|//} else if (Character.isWhitespace((char)c)) {
comment|//throw new NoLabelException(token.toString());
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'='
condition|)
block|{
comment|// If we find a '=' sign, it is either an error, or
comment|// the entry lacked a comma signifying the end of the key.
comment|//unread(c);
throw|throw
operator|new
name|NoLabelException
argument_list|(
name|token
operator|.
name|toString
argument_list|()
argument_list|)
throw|;
block|}
else|else
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Error in line "
operator|+
name|line
operator|+
literal|":"
operator|+
literal|"Character '"
operator|+
operator|(
name|char
operator|)
name|c
operator|+
literal|"' is not "
operator|+
literal|"allowed in bibtex keys."
argument_list|)
throw|;
block|}
block|}
block|}
DECL|class|NoLabelException
specifier|private
class|class
name|NoLabelException
extends|extends
name|Exception
block|{
DECL|method|NoLabelException (String hasRead)
specifier|public
name|NoLabelException
parameter_list|(
name|String
name|hasRead
parameter_list|)
block|{
name|super
argument_list|(
name|hasRead
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|parseBracketedText ()
specifier|private
name|StringBuffer
name|parseBracketedText
parameter_list|()
throws|throws
name|IOException
block|{
comment|//Util.pr("Parse bracketed text");
name|StringBuffer
name|value
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|consume
argument_list|(
literal|'{'
argument_list|)
expr_stmt|;
name|int
name|brackets
init|=
literal|0
decl_stmt|;
while|while
condition|(
operator|!
operator|(
operator|(
name|peek
argument_list|()
operator|==
literal|'}'
operator|)
operator|&&
operator|(
name|brackets
operator|==
literal|0
operator|)
operator|)
condition|)
block|{
name|int
name|j
init|=
name|read
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|j
operator|==
operator|-
literal|1
operator|)
operator|||
operator|(
name|j
operator|==
literal|65535
operator|)
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Error in line "
operator|+
name|line
operator|+
literal|": EOF in mid-string"
argument_list|)
throw|;
block|}
elseif|else
if|if
condition|(
name|j
operator|==
literal|'{'
condition|)
name|brackets
operator|++
expr_stmt|;
elseif|else
if|if
condition|(
name|j
operator|==
literal|'}'
condition|)
name|brackets
operator|--
expr_stmt|;
comment|// If we encounter whitespace of any kind, read it as a
comment|// simple space, and ignore any others that follow immediately.
if|if
condition|(
name|Character
operator|.
name|isWhitespace
argument_list|(
operator|(
name|char
operator|)
name|j
argument_list|)
condition|)
block|{
name|value
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
name|skipWhitespace
argument_list|()
expr_stmt|;
block|}
else|else
name|value
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|j
argument_list|)
expr_stmt|;
block|}
name|consume
argument_list|(
literal|'}'
argument_list|)
expr_stmt|;
return|return
name|value
return|;
block|}
DECL|method|consume (char expected)
specifier|private
name|void
name|consume
parameter_list|(
name|char
name|expected
parameter_list|)
throws|throws
name|IOException
block|{
name|int
name|c
init|=
name|read
argument_list|()
decl_stmt|;
if|if
condition|(
name|c
operator|!=
name|expected
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Error in line "
operator|+
name|line
operator|+
literal|": Expected "
operator|+
name|expected
operator|+
literal|" but received "
operator|+
operator|(
name|char
operator|)
name|c
argument_list|)
throw|;
block|}
block|}
DECL|method|consumeUncritically (char expected)
specifier|private
name|void
name|consumeUncritically
parameter_list|(
name|char
name|expected
parameter_list|)
throws|throws
name|IOException
block|{
name|int
name|c
decl_stmt|;
while|while
condition|(
operator|(
operator|(
name|c
operator|=
name|read
argument_list|()
operator|)
operator|!=
name|expected
operator|)
operator|&&
operator|(
name|c
operator|!=
operator|-
literal|1
operator|)
operator|&&
operator|(
name|c
operator|!=
literal|65535
operator|)
condition|)
empty_stmt|;
if|if
condition|(
operator|(
name|c
operator|==
operator|-
literal|1
operator|)
operator|||
operator|(
name|c
operator|==
literal|65535
operator|)
condition|)
name|_eof
operator|=
literal|true
expr_stmt|;
block|}
DECL|method|consume (char expected1, char expected2)
specifier|private
name|void
name|consume
parameter_list|(
name|char
name|expected1
parameter_list|,
name|char
name|expected2
parameter_list|)
throws|throws
name|IOException
block|{
comment|// Consumes one of the two, doesn't care which appears.
name|int
name|c
init|=
name|read
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|c
operator|!=
name|expected1
operator|)
operator|&&
operator|(
name|c
operator|!=
name|expected2
operator|)
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Error in line "
operator|+
name|line
operator|+
literal|": Expected "
operator|+
name|expected1
operator|+
literal|" or "
operator|+
name|expected2
operator|+
literal|" but received "
operator|+
operator|(
name|int
operator|)
name|c
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

