begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Nizar N. Batada, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref.export.layout
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
package|;
end_package

begin_import
import|import
name|wsi
operator|.
name|ra
operator|.
name|types
operator|.
name|StringInt
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_comment
comment|/**  * DOCUMENT ME!  *  * @author $author$  * @version $Revision$  */
end_comment

begin_class
DECL|class|LayoutHelper
specifier|public
class|class
name|LayoutHelper
block|{
comment|//~ Static fields/initializers /////////////////////////////////////////////
DECL|field|IS_LAYOUT_TEXT
specifier|public
specifier|static
specifier|final
name|int
name|IS_LAYOUT_TEXT
init|=
literal|1
decl_stmt|;
DECL|field|IS_SIMPLE_FIELD
specifier|public
specifier|static
specifier|final
name|int
name|IS_SIMPLE_FIELD
init|=
literal|2
decl_stmt|;
DECL|field|IS_FIELD_START
specifier|public
specifier|static
specifier|final
name|int
name|IS_FIELD_START
init|=
literal|3
decl_stmt|;
DECL|field|IS_FIELD_END
specifier|public
specifier|static
specifier|final
name|int
name|IS_FIELD_END
init|=
literal|4
decl_stmt|;
DECL|field|IS_OPTION_FIELD
specifier|public
specifier|static
specifier|final
name|int
name|IS_OPTION_FIELD
init|=
literal|5
decl_stmt|;
DECL|field|IS_GROUP_START
specifier|public
specifier|static
specifier|final
name|int
name|IS_GROUP_START
init|=
literal|6
decl_stmt|;
DECL|field|IS_GROUP_END
specifier|public
specifier|static
specifier|final
name|int
name|IS_GROUP_END
init|=
literal|7
decl_stmt|;
DECL|field|currentGroup
specifier|private
specifier|static
name|String
name|currentGroup
init|=
literal|null
decl_stmt|;
comment|//~ Instance fields ////////////////////////////////////////////////////////
comment|//public static final int IS_OPTION_FIELD_PARAM = 6;
DECL|field|_in
specifier|private
name|PushbackReader
name|_in
decl_stmt|;
DECL|field|parsedEntries
specifier|private
name|Vector
name|parsedEntries
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
comment|//private HashMap _meta;
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
comment|//~ Constructors ///////////////////////////////////////////////////////////
DECL|method|LayoutHelper (Reader in)
specifier|public
name|LayoutHelper
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
comment|//~ Methods ////////////////////////////////////////////////////////////////
DECL|method|getLayoutFromText (String classPrefix)
specifier|public
name|Layout
name|getLayoutFromText
parameter_list|(
name|String
name|classPrefix
parameter_list|)
throws|throws
name|Exception
block|{
name|parse
argument_list|()
expr_stmt|;
name|StringInt
name|si
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|parsedEntries
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|si
operator|=
operator|(
name|StringInt
operator|)
name|parsedEntries
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|si
operator|.
name|i
operator|==
name|IS_SIMPLE_FIELD
operator|)
operator|||
operator|(
name|si
operator|.
name|i
operator|==
name|IS_FIELD_START
operator|)
operator|||
operator|(
name|si
operator|.
name|i
operator|==
name|IS_FIELD_END
operator|)
operator|||
operator|(
name|si
operator|.
name|i
operator|==
name|IS_GROUP_START
operator|)
operator|||
operator|(
name|si
operator|.
name|i
operator|==
name|IS_GROUP_END
operator|)
condition|)
block|{
name|si
operator|.
name|s
operator|=
name|si
operator|.
name|s
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
block|}
block|}
name|Layout
name|layout
init|=
operator|new
name|Layout
argument_list|(
name|parsedEntries
argument_list|,
name|classPrefix
argument_list|)
decl_stmt|;
return|return
name|layout
return|;
block|}
DECL|method|getCurrentGroup ()
specifier|public
specifier|static
name|String
name|getCurrentGroup
parameter_list|()
block|{
return|return
name|currentGroup
return|;
block|}
DECL|method|setCurrentGroup (String newGroup)
specifier|public
specifier|static
name|void
name|setCurrentGroup
parameter_list|(
name|String
name|newGroup
parameter_list|)
block|{
name|currentGroup
operator|=
name|newGroup
expr_stmt|;
block|}
comment|/**      *      */
DECL|method|getBracketedField (int _field)
specifier|private
name|String
name|getBracketedField
parameter_list|(
name|int
name|_field
parameter_list|)
throws|throws
name|IOException
block|{
name|StringBuffer
name|buffer
init|=
literal|null
decl_stmt|;
name|int
name|previous
init|=
operator|-
literal|1
decl_stmt|;
name|int
name|c
decl_stmt|;
name|boolean
name|start
init|=
literal|false
decl_stmt|;
while|while
condition|(
operator|!
name|_eof
condition|)
block|{
name|c
operator|=
name|read
argument_list|()
expr_stmt|;
comment|//System.out.println((char)c);
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
if|if
condition|(
name|buffer
operator|!=
literal|null
condition|)
block|{
comment|//myStrings.add(buffer.toString());
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|buffer
operator|.
name|toString
argument_list|()
argument_list|,
name|_field
argument_list|)
argument_list|)
expr_stmt|;
comment|//System.out.println("\nbracketedEOF: " + buffer.toString());
block|}
comment|//myStrings.add(buffer.toString());
comment|//System.out.println("aha: " + buffer.toString());
return|return
literal|null
return|;
block|}
if|if
condition|(
operator|(
name|c
operator|==
literal|'{'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'}'
operator|)
condition|)
block|{
if|if
condition|(
name|c
operator|==
literal|'}'
condition|)
block|{
if|if
condition|(
name|buffer
operator|!=
literal|null
condition|)
block|{
comment|//myStrings.add(buffer.toString());
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|buffer
operator|.
name|toString
argument_list|()
argument_list|,
name|_field
argument_list|)
argument_list|)
expr_stmt|;
comment|//System.out.println("\nbracketed: " + buffer.toString());
return|return
literal|null
return|;
block|}
block|}
else|else
block|{
name|start
operator|=
literal|true
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|buffer
operator|==
literal|null
condition|)
block|{
name|buffer
operator|=
operator|new
name|StringBuffer
argument_list|(
literal|100
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|start
condition|)
block|{
if|if
condition|(
name|c
operator|==
literal|'}'
condition|)
block|{                     }
else|else
block|{
name|buffer
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
block|}
block|}
name|previous
operator|=
name|c
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
comment|/**      *      */
DECL|method|getBracketedOptionField (int _field)
specifier|private
name|String
name|getBracketedOptionField
parameter_list|(
name|int
name|_field
parameter_list|)
throws|throws
name|IOException
block|{
name|StringBuffer
name|buffer
init|=
literal|null
decl_stmt|;
name|int
name|previous
init|=
operator|-
literal|1
decl_stmt|;
name|int
name|c
decl_stmt|;
name|boolean
name|start
init|=
literal|false
decl_stmt|;
name|String
name|option
init|=
literal|null
decl_stmt|;
name|String
name|tmp
decl_stmt|;
while|while
condition|(
operator|!
name|_eof
condition|)
block|{
name|c
operator|=
name|read
argument_list|()
expr_stmt|;
comment|//System.out.println((char)c);
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
if|if
condition|(
name|buffer
operator|!=
literal|null
condition|)
block|{
comment|//myStrings.add(buffer.toString());
if|if
condition|(
name|option
operator|!=
literal|null
condition|)
block|{
name|tmp
operator|=
name|buffer
operator|.
name|toString
argument_list|()
operator|+
literal|"\n"
operator|+
name|option
expr_stmt|;
block|}
else|else
block|{
name|tmp
operator|=
name|buffer
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|tmp
argument_list|,
name|IS_OPTION_FIELD
argument_list|)
argument_list|)
expr_stmt|;
comment|//System.out.println("\nbracketedOptionEOF: " + buffer.toString());
block|}
return|return
literal|null
return|;
block|}
if|if
condition|(
operator|(
name|c
operator|==
literal|'{'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'}'
operator|)
operator|||
operator|(
name|c
operator|==
literal|']'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'['
operator|)
condition|)
block|{
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
literal|']'
operator|)
condition|)
block|{
comment|// changed section start - arudert
comment|// buffer may be null for parameters
comment|//if (buffer != null)
comment|//{
if|if
condition|(
name|c
operator|==
literal|']'
operator|&&
name|buffer
operator|!=
literal|null
condition|)
block|{
comment|// changed section end - arudert
name|option
operator|=
name|buffer
operator|.
name|toString
argument_list|()
expr_stmt|;
name|buffer
operator|=
literal|null
expr_stmt|;
name|start
operator|=
literal|false
expr_stmt|;
block|}
comment|//myStrings.add(buffer.toString());
comment|//System.out.println("\nbracketedOption: " + buffer.toString());
comment|// changed section begin - arudert
comment|// bracketed option must be followed by an (optionally empty) parameter
comment|// if empty, the parameter is set to " " (whitespace to avoid that the tokenizer that
comment|// splits the string later on ignores the empty parameter)
comment|//if (buffer != null)
elseif|else
if|if
condition|(
name|c
operator|==
literal|'}'
condition|)
block|{
name|String
name|parameter
init|=
name|buffer
operator|==
literal|null
condition|?
literal|" "
else|:
name|buffer
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|option
operator|!=
literal|null
condition|)
block|{
name|tmp
operator|=
name|parameter
operator|+
literal|"\n"
operator|+
name|option
expr_stmt|;
block|}
else|else
block|{
name|tmp
operator|=
name|parameter
expr_stmt|;
block|}
comment|//System.out.println("FORMAT: '"+tmp+"'");
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|tmp
argument_list|,
name|IS_OPTION_FIELD
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|// changed section end - arudert
comment|// changed section start - arudert
comment|// }
comment|// changed section end - arudert
block|}
else|else
block|{
name|start
operator|=
literal|true
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|buffer
operator|==
literal|null
condition|)
block|{
name|buffer
operator|=
operator|new
name|StringBuffer
argument_list|(
literal|100
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|start
condition|)
block|{
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
literal|']'
operator|)
condition|)
block|{                     }
else|else
block|{
comment|// changed section begin - arudert
comment|// keep the backslash so we know wether this is a fieldname or an ordinary parameter
comment|//if (c != '\\')
comment|//{
name|buffer
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
comment|//}
comment|// changed section end - arudert
block|}
block|}
block|}
name|previous
operator|=
name|c
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
DECL|method|parse ()
specifier|private
name|Object
name|parse
parameter_list|()
throws|throws
name|IOException
block|{
comment|//_meta = new HashMap(); // Metadata in comments for Bibkeeper.
name|skipWhitespace
argument_list|()
expr_stmt|;
name|int
name|c
decl_stmt|;
name|StringBuffer
name|buffer
init|=
literal|null
decl_stmt|;
name|int
name|previous
init|=
operator|-
literal|1
decl_stmt|;
name|boolean
name|justParsedTag
init|=
literal|false
decl_stmt|;
while|while
condition|(
operator|!
name|_eof
condition|)
block|{
name|c
operator|=
name|read
argument_list|()
expr_stmt|;
comment|//System.out.println((char)c);
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
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|buffer
operator|.
name|toString
argument_list|()
argument_list|,
name|IS_LAYOUT_TEXT
argument_list|)
argument_list|)
expr_stmt|;
comment|//System.out.println("aha: " + buffer.toString());
return|return
literal|null
return|;
block|}
if|if
condition|(
operator|(
name|c
operator|==
literal|'\\'
operator|)
operator|&&
operator|(
name|peek
argument_list|()
operator|!=
literal|'\\'
operator|)
operator|&&
operator|(
name|justParsedTag
operator|||
operator|(
name|previous
operator|!=
literal|'\\'
operator|)
operator|)
condition|)
block|{
if|if
condition|(
name|buffer
operator|!=
literal|null
condition|)
block|{
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|buffer
operator|.
name|toString
argument_list|()
argument_list|,
name|IS_LAYOUT_TEXT
argument_list|)
argument_list|)
expr_stmt|;
name|buffer
operator|=
literal|null
expr_stmt|;
block|}
name|parseField
argument_list|()
expr_stmt|;
comment|// To make sure the next character, if it is a backslash, doesn't get ignored,
comment|// since "previous" now holds a backslash:
name|justParsedTag
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|justParsedTag
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|buffer
operator|==
literal|null
condition|)
block|{
name|buffer
operator|=
operator|new
name|StringBuffer
argument_list|(
literal|100
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
operator|(
operator|(
name|c
operator|==
literal|'\\'
operator|)
operator|&&
operator|(
name|previous
operator|==
literal|'\\'
operator|)
operator|)
condition|)
block|{
name|buffer
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
block|}
name|previous
operator|=
name|c
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
comment|/**      *      */
DECL|method|parseField ()
specifier|private
name|void
name|parseField
parameter_list|()
throws|throws
name|IOException
block|{
name|int
name|c
decl_stmt|;
name|StringBuffer
name|buffer
init|=
literal|null
decl_stmt|;
name|String
name|name
decl_stmt|;
while|while
condition|(
operator|!
name|_eof
condition|)
block|{
name|c
operator|=
name|read
argument_list|()
expr_stmt|;
comment|//System.out.print((char)c);
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
block|}
if|if
condition|(
operator|!
name|Character
operator|.
name|isLetter
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
condition|)
block|{
name|unread
argument_list|(
name|c
argument_list|)
expr_stmt|;
comment|//System.out.println("\n#" + (char) c);
name|name
operator|=
name|buffer
operator|!=
literal|null
condition|?
name|buffer
operator|.
name|toString
argument_list|()
else|:
literal|""
expr_stmt|;
comment|//System.out.println("NAME:" + name);
name|buffer
operator|=
literal|null
expr_stmt|;
if|if
condition|(
name|name
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'b'
condition|)
block|{
if|if
condition|(
name|name
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"begin"
argument_list|)
condition|)
block|{
comment|// get field name
name|getBracketedField
argument_list|(
name|IS_FIELD_START
argument_list|)
expr_stmt|;
return|return;
block|}
elseif|else
if|if
condition|(
name|name
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"begingroup"
argument_list|)
condition|)
block|{
comment|// get field name
name|getBracketedField
argument_list|(
name|IS_GROUP_START
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
elseif|else
if|if
condition|(
name|name
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'f'
condition|)
block|{
if|if
condition|(
name|name
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"format"
argument_list|)
condition|)
block|{
if|if
condition|(
name|c
operator|==
literal|'['
condition|)
block|{
comment|// get format parameter
comment|// get field name
name|getBracketedOptionField
argument_list|(
name|IS_OPTION_FIELD
argument_list|)
expr_stmt|;
return|return;
block|}
else|else
block|{
comment|// get field name
name|getBracketedField
argument_list|(
name|IS_OPTION_FIELD
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|name
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'e'
condition|)
block|{
if|if
condition|(
name|name
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"end"
argument_list|)
condition|)
block|{
comment|// get field name
name|getBracketedField
argument_list|(
name|IS_FIELD_END
argument_list|)
expr_stmt|;
return|return;
block|}
elseif|else
if|if
condition|(
name|name
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"endgroup"
argument_list|)
condition|)
block|{
comment|// get field name
name|getBracketedField
argument_list|(
name|IS_GROUP_END
argument_list|)
expr_stmt|;
block|}
block|}
comment|// for all other cases
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|name
argument_list|,
name|IS_SIMPLE_FIELD
argument_list|)
argument_list|)
expr_stmt|;
comment|//System.out.println(name);
return|return;
block|}
else|else
block|{
if|if
condition|(
name|buffer
operator|==
literal|null
condition|)
block|{
name|buffer
operator|=
operator|new
name|StringBuffer
argument_list|(
literal|100
argument_list|)
expr_stmt|;
block|}
name|buffer
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
block|{
name|line
operator|++
expr_stmt|;
block|}
comment|//System.out.print((char) c);
return|return
name|c
return|;
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
block|{
name|unread
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
break|break;
block|}
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
block|{
name|line
operator|--
expr_stmt|;
block|}
name|_in
operator|.
name|unread
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
comment|//
comment|//	private String parseFieldContent() throws IOException
comment|//	{
comment|//		skipWhitespace();
comment|//		consume('=');
comment|//		skipWhitespace();
comment|//		StringBuffer value = new StringBuffer();
comment|//		int c, j = '.';
comment|//
comment|//		while (((c = peek()) != ',')&& (c != '}')&& (c != ')'))
comment|//		{
comment|//
comment|//			if (_eof)
comment|//			{
comment|//				throw new RuntimeException(
comment|//					"Error in line " + line + ": EOF in mid-string");
comment|//			}
comment|//			if (c == '"')
comment|//			{
comment|//				// value is a string
comment|//				consume('"');
comment|//
comment|//				while (!((peek() == '"')&& (j != '\\')))
comment|//				{
comment|//					j = read();
comment|//					if (_eof || (j == -1) || (j == 65535))
comment|//					{
comment|//						throw new RuntimeException(
comment|//							"Error in line " + line + ": EOF in mid-string");
comment|//					}
comment|//
comment|//					value.append((char) j);
comment|//				}
comment|//
comment|//				consume('"');
comment|//
comment|//			}
comment|//			skipWhitespace();
comment|//		}
comment|//		//Util.pr("Returning field content: "+value.toString());
comment|//		return value.toString();
comment|//
comment|//	}
comment|//
comment|//	private StringBuffer parseBracketedText() throws IOException
comment|//	{
comment|//		//Util.pr("Parse bracketed text");
comment|//		StringBuffer value = new StringBuffer();
comment|//
comment|//		consume('{');
comment|//
comment|//		int brackets = 0;
comment|//
comment|//		while (!((peek() == '}')&& (brackets == 0)))
comment|//		{
comment|//
comment|//			int j = read();
comment|//			if ((j == -1) || (j == 65535))
comment|//			{
comment|//				throw new RuntimeException(
comment|//					"Error in line " + line + ": EOF in mid-string");
comment|//			}
comment|//			else if (j == '{')
comment|//				brackets++;
comment|//			else if (j == '}')
comment|//				brackets--;
comment|//
comment|//			// If we encounter whitespace of any kind, read it as a
comment|//			// simple space, and ignore any others that follow immediately.
comment|//			if (Character.isWhitespace((char) j))
comment|//			{
comment|//				value.append(' ');
comment|//				skipWhitespace();
comment|//			}
comment|//			else
comment|//				value.append((char) j);
comment|//
comment|//		}
comment|//
comment|//		consume('}');
comment|//
comment|//		return value;
comment|//	}
comment|//	private void consume(char expected) throws IOException
comment|//	{
comment|//		int c = read();
comment|//
comment|//		if (c != expected)
comment|//		{
comment|//			throw new RuntimeException(
comment|//				"Error in line "
comment|//					+ line
comment|//					+ ": Expected "
comment|//					+ expected
comment|//					+ " but received "
comment|//					+ (char) c);
comment|//		}
comment|//
comment|//	}
comment|//
comment|//	private void consumeUncritically(char expected) throws IOException
comment|//	{
comment|//		int c;
comment|//		while (((c = read()) != expected)&& (c != -1)&& (c != 65535));
comment|//		if ((c == -1) || (c == 65535))
comment|//			_eof = true;
comment|//	}
comment|//
comment|//	private void consume(char expected1, char expected2) throws IOException
comment|//	{
comment|//		// Consumes one of the two, doesn't care which appears.
comment|//
comment|//		int c = read();
comment|//
comment|//		if ((c != expected1)&& (c != expected2))
comment|//		{
comment|//			throw new RuntimeException(
comment|//				"Error in line "
comment|//					+ line
comment|//					+ ": Expected "
comment|//					+ expected1
comment|//					+ " or "
comment|//					+ expected2
comment|//					+ " but received "
comment|//					+ (int) c);
comment|//		}
comment|//
comment|//	}
block|}
end_class

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_comment
comment|//  END OF FILE.
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

end_unit

