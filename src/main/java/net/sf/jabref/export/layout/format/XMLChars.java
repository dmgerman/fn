begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_comment
comment|//  Filename: $RCSfile$
end_comment

begin_comment
comment|//  Purpose:  Atom representation.
end_comment

begin_comment
comment|//  Language: Java
end_comment

begin_comment
comment|//  Compiler: JDK 1.4
end_comment

begin_comment
comment|//  Authors:  Joerg K. Wegner, Morten O. Alver
end_comment

begin_comment
comment|//  Version:  $Revision$
end_comment

begin_comment
comment|//            $Date$
end_comment

begin_comment
comment|//            $Author$
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//  Copyright (c) Dept. Computer Architecture, University of Tuebingen, Germany
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//  This program is free software; you can redistribute it and/or modify
end_comment

begin_comment
comment|//  it under the terms of the GNU General Public License as published by
end_comment

begin_comment
comment|//  the Free Software Foundation version 2 of the License.
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//  This program is distributed in the hope that it will be useful,
end_comment

begin_comment
comment|//  but WITHOUT ANY WARRANTY; without even the implied warranty of
end_comment

begin_comment
comment|//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
end_comment

begin_comment
comment|//  GNU General Public License for more details.
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_package
DECL|package|net.sf.jabref.export.layout.format
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
operator|.
name|format
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|Globals
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
name|export
operator|.
name|layout
operator|.
name|LayoutFormatter
import|;
end_import

begin_comment
comment|/**  * Changes {\^o} or {\^{o}} to ?  *   * @author $author$  * @version $Revision$  */
end_comment

begin_class
DECL|class|XMLChars
specifier|public
class|class
name|XMLChars
implements|implements
name|LayoutFormatter
block|{
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
name|fieldText
operator|=
name|firstFormat
argument_list|(
name|fieldText
argument_list|)
expr_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|entry
range|:
name|Globals
operator|.
name|XML_CHARS
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|s
init|=
name|entry
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|String
name|repl
init|=
name|entry
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|repl
operator|!=
literal|null
condition|)
name|fieldText
operator|=
name|fieldText
operator|.
name|replaceAll
argument_list|(
name|s
argument_list|,
name|repl
argument_list|)
expr_stmt|;
block|}
return|return
name|restFormat
argument_list|(
name|fieldText
argument_list|)
return|;
block|}
DECL|method|firstFormat (String s)
specifier|private
name|String
name|firstFormat
parameter_list|(
name|String
name|s
parameter_list|)
block|{
return|return
name|s
operator|.
name|replaceAll
argument_list|(
literal|"&|\\\\&"
argument_list|,
literal|"&#x0026;"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"--"
argument_list|,
literal|"&#x2013;"
argument_list|)
return|;
block|}
DECL|field|forceReplace
name|boolean
index|[]
name|forceReplace
decl_stmt|;
DECL|method|restFormat (String toFormat)
specifier|private
name|String
name|restFormat
parameter_list|(
name|String
name|toFormat
parameter_list|)
block|{
name|String
name|fieldText
init|=
name|toFormat
operator|.
name|replaceAll
argument_list|(
literal|"\\}"
argument_list|,
literal|""
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\{"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
comment|// now some copy-paste problems most often occuring in abstracts when
comment|// copied from PDF
comment|// AND: this is accepted in the abstract of bibtex files, so are forced
comment|// to catch those cases
if|if
condition|(
name|forceReplace
operator|==
literal|null
condition|)
block|{
name|forceReplace
operator|=
operator|new
name|boolean
index|[
literal|126
index|]
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
literal|40
condition|;
name|i
operator|++
control|)
block|{
name|forceReplace
index|[
name|i
index|]
operator|=
literal|true
expr_stmt|;
block|}
name|forceReplace
index|[
literal|32
index|]
operator|=
literal|false
expr_stmt|;
for|for
control|(
name|int
name|i
range|:
operator|new
name|int
index|[]
block|{
literal|44
block|,
literal|45
block|,
literal|63
block|,
literal|64
block|,
literal|94
block|,
literal|95
block|,
literal|96
block|,
literal|124
block|}
control|)
block|{
name|forceReplace
index|[
name|i
index|]
operator|=
literal|true
expr_stmt|;
block|}
block|}
name|StringBuilder
name|buffer
init|=
operator|new
name|StringBuilder
argument_list|(
name|fieldText
operator|.
name|length
argument_list|()
operator|*
literal|2
argument_list|)
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
name|fieldText
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|int
name|code
init|=
operator|(
name|fieldText
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|)
decl_stmt|;
comment|// Checking the case when the character is already escaped
comment|// Just push "&#" to the buffer and keep going from the next char
if|if
condition|(
operator|(
name|code
operator|==
literal|38
operator|)
operator|&&
operator|(
name|fieldText
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|==
literal|35
operator|)
condition|)
block|{
name|i
operator|+=
literal|2
expr_stmt|;
name|buffer
operator|.
name|append
argument_list|(
literal|"&#"
argument_list|)
expr_stmt|;
name|code
operator|=
operator|(
name|fieldText
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|)
expr_stmt|;
block|}
comment|// TODO: Check whether> 125 is correct here or whether it should rather be>=
if|if
condition|(
name|code
operator|>
literal|125
operator|||
name|forceReplace
index|[
name|code
index|]
condition|)
block|{
name|buffer
operator|.
name|append
argument_list|(
literal|"&#"
argument_list|)
operator|.
name|append
argument_list|(
name|code
argument_list|)
operator|.
name|append
argument_list|(
literal|";"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|buffer
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|code
argument_list|)
expr_stmt|;
block|}
block|}
name|fieldText
operator|=
name|buffer
operator|.
name|toString
argument_list|()
expr_stmt|;
comment|// use common abbreviations for<,> instead of code
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|entry
range|:
name|Globals
operator|.
name|ASCII2XML_CHARS
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|s
init|=
name|entry
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|String
name|repl
init|=
name|entry
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|repl
operator|!=
literal|null
condition|)
name|fieldText
operator|=
name|fieldText
operator|.
name|replaceAll
argument_list|(
name|s
argument_list|,
name|repl
argument_list|)
expr_stmt|;
block|}
return|return
name|fieldText
return|;
block|}
block|}
end_class

end_unit

