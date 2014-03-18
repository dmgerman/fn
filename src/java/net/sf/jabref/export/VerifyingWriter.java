begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.export
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
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
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStreamWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|CharsetEncoder
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_comment
comment|/**  * Writer that extends OutputStreamWriter, but also checks if the chosen  * encoding supports all text that is written. Currently only a boolean value is  * stored to remember whether everything has gone well or not.  */
end_comment

begin_class
DECL|class|VerifyingWriter
specifier|public
class|class
name|VerifyingWriter
extends|extends
name|OutputStreamWriter
block|{
DECL|field|encoder
name|CharsetEncoder
name|encoder
decl_stmt|;
DECL|field|couldEncodeAll
specifier|private
name|boolean
name|couldEncodeAll
init|=
literal|true
decl_stmt|;
DECL|field|problemCharacters
specifier|private
name|TreeSet
argument_list|<
name|Character
argument_list|>
name|problemCharacters
init|=
operator|new
name|TreeSet
argument_list|<
name|Character
argument_list|>
argument_list|()
decl_stmt|;
DECL|method|VerifyingWriter (OutputStream out, String encoding)
specifier|public
name|VerifyingWriter
parameter_list|(
name|OutputStream
name|out
parameter_list|,
name|String
name|encoding
parameter_list|)
throws|throws
name|UnsupportedEncodingException
block|{
name|super
argument_list|(
name|out
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
name|encoder
operator|=
name|Charset
operator|.
name|forName
argument_list|(
name|encoding
argument_list|)
operator|.
name|newEncoder
argument_list|()
expr_stmt|;
block|}
DECL|method|write (String str)
specifier|public
name|void
name|write
parameter_list|(
name|String
name|str
parameter_list|)
throws|throws
name|IOException
block|{
name|super
operator|.
name|write
argument_list|(
name|str
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|encoder
operator|.
name|canEncode
argument_list|(
name|str
argument_list|)
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|str
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|encoder
operator|.
name|canEncode
argument_list|(
name|str
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
argument_list|)
condition|)
name|problemCharacters
operator|.
name|add
argument_list|(
name|str
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|couldEncodeAll
operator|=
literal|false
expr_stmt|;
block|}
block|}
DECL|method|couldEncodeAll ()
specifier|public
name|boolean
name|couldEncodeAll
parameter_list|()
block|{
return|return
name|couldEncodeAll
return|;
block|}
DECL|method|getProblemCharacters ()
specifier|public
name|String
name|getProblemCharacters
parameter_list|()
block|{
name|StringBuffer
name|chars
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|Character
name|ch
range|:
name|problemCharacters
control|)
block|{
name|chars
operator|.
name|append
argument_list|(
name|ch
operator|.
name|charValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|chars
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

