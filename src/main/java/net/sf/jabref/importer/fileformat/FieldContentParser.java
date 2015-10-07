begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fileformat
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_comment
comment|/**  * This class provides the reformatting needed when reading BibTeX fields formatted  * in JabRef style. The reformatting must undo all formatting done by JabRef when  * writing the same fields.  */
end_comment

begin_class
DECL|class|FieldContentParser
class|class
name|FieldContentParser
block|{
DECL|field|multiLineFields
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|multiLineFields
decl_stmt|;
DECL|method|FieldContentParser ()
specifier|public
name|FieldContentParser
parameter_list|()
block|{
name|multiLineFields
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
name|multiLineFields
operator|.
name|add
argument_list|(
literal|"abstract"
argument_list|)
expr_stmt|;
name|multiLineFields
operator|.
name|add
argument_list|(
literal|"review"
argument_list|)
expr_stmt|;
comment|// the file field should not be formatted, therefore we treat it as a multi line field
name|multiLineFields
operator|.
name|add
argument_list|(
literal|"file"
argument_list|)
expr_stmt|;
block|}
comment|/**      * Performs the reformatting      *      * @param content     StringBuffer containing the field to format. bibtexField contains field name according to field      * @param bibtexField      * @return The formatted field content. The StringBuffer returned may or may not be the same as the argument given.      */
DECL|method|format (StringBuffer content, String bibtexField)
specifier|public
name|StringBuffer
name|format
parameter_list|(
name|StringBuffer
name|content
parameter_list|,
name|String
name|bibtexField
parameter_list|)
block|{
comment|// Unify line breaks
name|String
name|text
init|=
name|StringUtil
operator|.
name|unifyLineBreaks
argument_list|(
name|content
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
comment|// Do not format multiline fields
if|if
condition|(
name|multiLineFields
operator|.
name|contains
argument_list|(
name|bibtexField
argument_list|)
condition|)
block|{
return|return
operator|new
name|StringBuffer
argument_list|(
name|text
argument_list|)
return|;
block|}
comment|// 's' matches a space, tab, new line, carriage return.
name|text
operator|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"\\s+"
argument_list|,
literal|" "
argument_list|)
expr_stmt|;
return|return
operator|new
name|StringBuffer
argument_list|(
name|text
argument_list|)
return|;
block|}
block|}
end_class

end_unit

